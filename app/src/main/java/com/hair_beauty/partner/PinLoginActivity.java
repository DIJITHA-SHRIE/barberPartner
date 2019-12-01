package com.hair_beauty.partner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.utility.Constants;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PinLoginActivity extends AppCompatActivity {

    Button submit,cancel;
    String device_id;
    EditText pin;
    ProgressDialog progressDialog;
    private int i = 0;
    SharedPreferenceClass sharedPreferenceClass;
    String firebase_token="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterpinscreen);

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        pin = findViewById(R.id.pin);

        progressDialog = new ProgressDialog(PinLoginActivity.this);
        sharedPreferenceClass = new SharedPreferenceClass(PinLoginActivity.this);
        firebase_token=sharedPreferenceClass.getValue_string(Constants.FIREBASE_TOKEN);
        Log.i("PinFB_token",firebase_token);

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPin();

                /*startActivity(new Intent(PinLoginActivity.this,NavigationDrawerActivity.class));
                finish();*/
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void loginPin() {
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.LOGIN_PIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {

                    /*{"message":"Pin created successfully"}
                  If the  old pin and given pin are same it shows this : {"message":"Create a new pin , the pin you enter is the old one"}*/

                    Log.d("Emaillogin response is ", response);
                 //   {"message":"Invalid username or password"}
                    //{"message":"Valid"}

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(PinLoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    if (jsonObject.getString("message").equals("Valid")) {

                        sharedPreferenceClass.setValue_string("PINSTATUS","LOGIN");

                        startActivity(new Intent(PinLoginActivity.this,NavigationDrawerActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(PinLoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        loginPin();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(PinLoginActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(PinLoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

                params.put("m_pin",pin.getText().toString().trim()); //dipak
                params.put("device_id",device_id);
                params.put("fb_token",firebase_token);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PinLoginActivity.this);
        requestQueue.add(stringRequest);
    }
}
