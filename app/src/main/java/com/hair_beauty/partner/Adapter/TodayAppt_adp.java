package com.hair_beauty.partner.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.Model.Myservices_Mod;
import com.hair_beauty.partner.R;
import com.hair_beauty.partner.utility.ServerLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayAppt_adp extends RecyclerView.Adapter<TodayAppt_adp.ViewHolder> {
    ArrayList<Myservices_Mod> todayArray = new ArrayList<>();
    Activity context;

    public TodayAppt_adp(ArrayList<Myservices_Mod> todayArray, Activity context) {
        this.todayArray = todayArray;
        this.context = context;
    }

    @NonNull
    @Override
    public TodayAppt_adp.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.todayaapt_adp_lay,viewGroup,false);
        TodayAppt_adp.ViewHolder view_appt = new TodayAppt_adp.ViewHolder(view);
        return view_appt;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAppt_adp.ViewHolder holder, final int i) {
        holder.cus_name.setText(todayArray.get(i).getName());
        holder.user_serv.setText((todayArray.get(i).getServices()).replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"",""));
        holder.user_time.setText(todayArray.get(i).getStart_time());

        if(todayArray.get(i).getStatus().equals("0")){
            holder.accept.setVisibility(View.VISIBLE);
            holder.reject.setVisibility(View.VISIBLE);

        }
        if(todayArray.get(i).getStatus().equals("1")){
            holder.accept.setVisibility(View.VISIBLE);
            holder.accept.setBackgroundColor(Color.GRAY);
            holder.accept.setEnabled(false);
            holder.reject.setVisibility(View.GONE);
        }
        if(todayArray.get(i).getStatus().equals("2")){
            holder.reject.setVisibility(View.VISIBLE);
            holder.reject.setBackgroundColor(Color.GRAY);
            holder.reject.setEnabled(false);
            holder.accept.setVisibility(View.GONE);
        }

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAcceptORrejectService(todayArray.get(i).getId(),"accept");
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAcceptORrejectService(todayArray.get(i).getId(),"reject");
            }
        });

    }

    private void callAcceptORrejectService(final String id,final String actionvalue) {



            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.SERVICE_ACTION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response_serviceAction", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if (message.equals("1")) {
                            Toast.makeText(context,"Services"+actionvalue+" successfully",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(context,"Oops! please try after some time",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Token <token>");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    Log.i("saloon_id", id);
                    params.put("id", id);
                    params.put("tagValue", actionvalue);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }


    @Override
    public int getItemCount() {
        return todayArray.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cus_name)
        TextView cus_name;
        @BindView(R.id.user_serv)
        TextView user_serv;
        @BindView(R.id.user_time)
        TextView user_time;
        @BindView(R.id.accept)
        Button accept;
        @BindView(R.id.reject)
        Button reject;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
