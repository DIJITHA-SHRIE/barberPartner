package com.hair_beauty.partner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.utility.InputValidation;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {

    Button submit,cancel;
    EditText userName,userPassword;
    ProgressDialog progressDialog;
    private int i = 0;
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        progressDialog = new ProgressDialog(LoginScreen.this);
        sharedPreferenceClass = new SharedPreferenceClass(LoginScreen.this);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        userName = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginScreen.this,OtpScreen.class));

                if(!InputValidation.isEditTextHasvalue(userName)){
                    Toast.makeText(LoginScreen.this, "Please enter valid user name.", Toast.LENGTH_SHORT).show();
                }
                else if(!InputValidation.isEditTextHasvalue(userPassword)){
                    Toast.makeText(LoginScreen.this, "Please enter valid Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    getLogin();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private  void getLogin() {
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.LOGIN_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {

                    /*{"status":"SUCCESS",
                    "user_data":{"id":"77","uname":"lucky","email":"lucky@gmail.com","mobno":"9078143068","user_photo":null,"user_pwd":"e10adc3949ba59abbe56e057f20f883e",
                    "wallet_bal":null,"sender_id":"","is_active":"1","is_delete":"0","created_date":"2018-10-15 00:00:00","updated_date":null},
                    "message":"Login Successful"}*/

                    Log.d("Emaillogin response is ", response);

                    //{"message":"Valid"}

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(LoginScreen.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    if (jsonObject.getString("message").equals("Valid")) {

                        getUserData();

                    }
                    else {

                        Toast.makeText(LoginScreen.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                        getLogin();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginScreen.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Log.i("LoginError",error.getMessage());
                    Toast.makeText(LoginScreen.this, error.getMessage()+"loginError", Toast.LENGTH_LONG).show();
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


                params.put("username",userName.getText().toString()); //B7982
                params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginScreen.this);
        requestQueue.add(stringRequest);
    }

    private  void getUserData() {
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.ALL_USERDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                String  specialized_id ="0";

                try {

                    /*{"message":{"id":"10","barber_id":"B79110","barber_name":"Pravakar Panda","father_name":"Pravakar",
                    "email_id":"pravakar.panda@nekss.com","password":"827ccb0eea8a706c4c34a16891f84e7b","phone":"9937677620",
                    "number":"9123456781","pan":"AD23434434","aadhar":"123456789","dob":"1985-05-08","doj":"2018-11-18","gender":"Male",
                    "martial_stauts":"Married","ref_code":"S3NEF6","otp":"7940","pin":"NA","image":"68672_IMG_20170917_204838.jpg",
                    "document_bussiness":"","document_aadhar":"","document_pan":"","document_voterid":"","document_qualification":"",
                    "document_other":"","present_address":"Barmunda","permanent_address":"Barmunda","experience":"0-1","service":"0",
                    "specialized_id":"Haircut,Shaving","status":"1","created_date":"2018-12-11","updated_date":"2018-12-12"}}*/

                    Log.d("Emaillogin response is ", response);

                    //{"message":"Valid"}

                    JSONObject jsonObject = new JSONObject(response);


                    JSONObject jObject = jsonObject.getJSONObject("message");

                    if (jObject.getString("status").equals("1")) {

//                        JSONObject jsonObject1 = jsonObject.getJSONObject("user_data");
//
                        String  userId = jObject.getString("barber_id");
                        String  name = jObject.getString("barber_name");
                        String  father_name = jObject.getString("father_name");
                        String  email = jObject.getString("email_id");
                        String  phoneno = jObject.getString("phone");
                        String  ref_code = jObject.getString("ref_code");
                        String  otp = jObject.getString("otp");
                        String  profilepic = jObject.getString("image");
                        if(jObject.has("specialized_id")){
                            specialized_id= jObject.getString("specialized_id");
                        }



                        sharedPreferenceClass.setValue_string("BARBERID",userId);
                        sharedPreferenceClass.setValue_string("BARBER_NAME",name);
                        sharedPreferenceClass.setValue_string("BARBER_FATHERNAME",father_name);
                        sharedPreferenceClass.setValue_string("BARBER_EMAIL",email);
                        sharedPreferenceClass.setValue_string("BARBER_MOB",phoneno);
                        sharedPreferenceClass.setValue_string("BARBER_PROFILEPIC",profilepic);
                        sharedPreferenceClass.setValue_string("BARBER_OTP",otp);
                        sharedPreferenceClass.setValue_string("BARBER_REFERALCODE",ref_code);
                        sharedPreferenceClass.setValue_string("BARBER_SPECIALIZED",specialized_id);

                        Toast.makeText(LoginScreen.this, otp, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this,OtpScreen.class).putExtra("OTP",otp));
//                        startActivity(new Intent(LoginScreen.this,NavigationDrawerActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(LoginScreen.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                        getUserData();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginScreen.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_LONG).show();
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


                params.put("emp_id",userName.getText().toString()); //B7982
              //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginScreen.this);
        requestQueue.add(stringRequest);
    }
}
