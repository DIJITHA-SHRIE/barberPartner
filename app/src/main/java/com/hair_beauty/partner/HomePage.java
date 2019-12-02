package com.hair_beauty.partner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.Adapter.HomePageAdapter;
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
import butterknife.OnClick;

public class HomePage extends Fragment {

    @BindView(R.id.rv_today_appt)
    RecyclerView rv_today_appt;
    @BindView(R.id.totalPrice)
    TextView totalPrice_t;
    @BindView(R.id.today_tot_Appt)
    TextView today_tot_Appt;
    @BindView(R.id.totalAppt)
    TextView totalAppt;
    @BindView(R.id.today_details)
    TextView today_details;
    @BindView(R.id.total_appt_det)
    TextView total_appt_det;
    @BindView(R.id.totalearn)
    TextView totalearn;
    TodayAppt_adp adapter;


    SharedPreferenceClass sharedPreferenceClass;

    int[] image = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};

    ArrayList<Myservices_Mod> todayArray = new ArrayList<>();
    ArrayList<Myservices_Mod> total_appt_array = new ArrayList<>();
    ArrayList<Myservices_Mod> incomeArray = new ArrayList<>();
    ArrayList<Myservices_Mod> today_incomeArray = new ArrayList<>();
    String getsaloon_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);
        ButterKnife.bind(this, rootView);
        Switch sb = new Switch(getActivity());
        sb.setTextOff("OFF");
        sb.setTextOn("ON");
        sb.setChecked(true);
        Switch sw = (Switch) rootView.findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String status="1";
                    saloonActive(status);
                    //Toast.makeText(getActivity(),"On",Toast.LENGTH_LONG).show();
                    // The toggle is enabled
                } else {
                    String status="0";
                    saloonActive(status);
                    //Toast.makeText(getActivity(),"Off",Toast.LENGTH_LONG).show();
                    // The toggle is disabled
                }
            }
        });
        sharedPreferenceClass = new SharedPreferenceClass(getActivity());
        getsaloon_id = sharedPreferenceClass.getValue_string("BARBERID");

        getTodayAppointment();
        getTotalAppointment();
        getTodaysIncome();
        getTotalEarning();


        return rootView;

    }

    public void getTodayAppointment() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.TODAY_APPOINTMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_today", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            String user_name = row.getString("user_name");
                            String user_start_time = row.getString("start_time");
                            String user_status = row.getString("status");
                            String user_id = row.getString("id");
                            JSONArray service_json_array = row.getJSONArray("service_name");
                            today_tot_Appt.setText(String.valueOf(service_json_array.length()));
                            Log.i("service_json_array", service_json_array.toString());

                            todayArray.add(new Myservices_Mod(user_name, user_id, service_json_array.toString(), user_status
                                    , user_start_time));

                        } }

                    adapter = new TodayAppt_adp(todayArray,getActivity());
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                    lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rv_today_appt.setLayoutManager(lm);
                    rv_today_appt.setAdapter(adapter);

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

                Log.i("saloon_id", getsaloon_id);
                params.put("owned_by", getsaloon_id); //B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    public void getTotalAppointment() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.Total_APPOINTMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_total", response);
                int services_count = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            String user_name = row.getString("user_name");
                            String user_start_time = row.getString("start_time");
                            String user_status = row.getString("status");
                            String user_id = row.getString("id");
                            JSONArray service_json_array = row.getJSONArray("service_name");
                            services_count = services_count+service_json_array.length();

                            Log.i("service_json_array", service_json_array.toString());

                            total_appt_array.add(new Myservices_Mod(user_name, user_id, service_json_array.toString(), user_status
                                    , user_start_time));


                        }


                        totalAppt.setText(String.valueOf(services_count));






                    }

                    adapter = new TodayAppt_adp(total_appt_array,getActivity());
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                    lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rv_today_appt.setLayoutManager(lm);
                    rv_today_appt.setAdapter(adapter);

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

                Log.i("saloon_id", getsaloon_id);
                params.put("owned_by", getsaloon_id); //B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void getTodaysIncome() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.TODAY_INCOME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_income", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            today_incomeArray.add(new Myservices_Mod(row.getString("total_price")));
                        }
                    }
                    Log.i("TodayIncomeArraySize",today_incomeArray.size()+"");
                        Double totalPrice=0.0;
                    for(int i = 0; i<today_incomeArray.size();i++){
                        Double price = Double.valueOf(today_incomeArray.get(i).getTotal_price());
                        totalPrice=totalPrice+price;
                    }
                    totalPrice_t.setText("INR "+String.valueOf(totalPrice));

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

                Log.i("saloon_id", getsaloon_id);
                params.put("owned_by", getsaloon_id);
                params.put("status", "1");//B7982    // 3 for paid
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    public void getTotalEarning() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.total_earn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_income_total", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            incomeArray.add(new Myservices_Mod(row.getString("total_price")));
                        }
                    }
                    Log.i("TotalIncomeArraySize",incomeArray.size()+"");
                    Double totalPrice=0.0;
                    for(int i = 0; i<incomeArray.size();i++){
                        Double price = Double.valueOf(incomeArray.get(i).getTotal_price());
                        totalPrice=totalPrice+price;
                    }
                    totalearn.setText("INR "+String.valueOf(totalPrice));

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

                Log.i("saloon_id", getsaloon_id);
                params.put("owned_by", getsaloon_id);
                params.put("status", "1");//B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }





    @OnClick(R.id.today_details)
    public void onTodayDet(View view){
       Fragment  fragment=MyAppointment.newInstance("HOME_TODAY",todayArray);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerView, fragment);
        ft.commit();

    }

    @OnClick(R.id.total_appt_det)
    public void onTotalClick(View view){
        Fragment  fragment=MyAppointment.newInstance("HOME_TODAY_TOTAL",total_appt_array);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerView, fragment);
        ft.commit();
    }


    public void saloonActive(final String status) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.STATUS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Staus", response);
                //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
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

                Log.i("saloon_id", getsaloon_id);
                params.put("owned_by", getsaloon_id);
                params.put("status", status);//B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }




}
