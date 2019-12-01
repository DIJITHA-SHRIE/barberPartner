package com.hair_beauty.partner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.Adapter.MyServAdp;
import com.hair_beauty.partner.Adapter.TodayAppt_adp;
import com.hair_beauty.partner.Model.Myservices_Mod;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyServices extends AppCompatActivity {

    ArrayList<Myservices_Mod> myservices_modArrayList = new ArrayList<>();
    String saloon_name;
    SharedPreferenceClass sharedPreferenceClass;
    MyServAdp adapter;
    @BindView(R.id.rv_my_serv)
    RecyclerView rv_my_serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);
        ButterKnife.bind(this);
        sharedPreferenceClass = new  SharedPreferenceClass(MyServices.this);
        saloon_name =sharedPreferenceClass.getValue_string("BARBER_NAME");

        getmyServicesData();
    }

    private void getmyServicesData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.MY_SERVICES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_my_ser",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i<jsonArray.length();i++){
                            JSONObject row = jsonArray.getJSONObject(i);
                            myservices_modArrayList.add(new Myservices_Mod(row.getString("pac_name"),
                                    row.getString("cat_name"),row.getString("price"),row.getString("times") ));
                        }
                    }
                    adapter = new MyServAdp(myservices_modArrayList,MyServices.this);
                    LinearLayoutManager lm = new LinearLayoutManager(MyServices.this);

                    rv_my_serv.setLayoutManager(lm);
                    rv_my_serv.setAdapter(adapter);

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

                Log.i("SaloonName",saloon_name);
                params.put("saloon_name", saloon_name); //B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MyServices.this);
        requestQueue.add(stringRequest);
    }
}
