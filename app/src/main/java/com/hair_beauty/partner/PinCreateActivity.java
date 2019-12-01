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
import com.hair_beauty.partner.utility.InputValidation;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PinCreateActivity extends AppCompatActivity {
    Button submit,cancel;
    EditText newpin,confirmpin;
    ProgressDialog progressDialog;
    private int i = 0;
    SharedPreferenceClass sharedPreferenceClass;
    String device_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchpin);

        progressDialog = new ProgressDialog(PinCreateActivity.this);
        sharedPreferenceClass = new SharedPreferenceClass(PinCreateActivity.this);

        newpin = findViewById(R.id.newpin);
        confirmpin = findViewById(R.id.confirmpin);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InputValidation.isEditTextHasvalue(newpin)){
                    Toast.makeText(PinCreateActivity.this, "Please generate a PIN", Toast.LENGTH_SHORT).show();
                }
                else if(!InputValidation.isPinLengthCheck(newpin)){
                    Toast.makeText(PinCreateActivity.this, "Please enter minimum 4 numbers.", Toast.LENGTH_SHORT).show();
                }
                else if(!InputValidation.isEditTextHasvalue(confirmpin)){
                    Toast.makeText(PinCreateActivity.this, "Please confirm yourPIN", Toast.LENGTH_SHORT).show();
                }
                else if (!InputValidation.isPinMatches(newpin,confirmpin)){
                    Toast.makeText(PinCreateActivity.this, "PIN does not match.", Toast.LENGTH_SHORT).show();
                }
                else{
                    createPin();
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

    private  void createPin() {
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.CREATE_PIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {

                    /*{"message":"Pin created successfully"}
If the  old pin and given pin are same it shows this : {"message":"Create a new pin , the pin you enter is the old one"}*/

                    Log.d("Emaillogin response is ", response);

                    //{"message":"Valid"}

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(PinCreateActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    if (jsonObject.getString("message").equals("Pin created successfully")) {

                        sharedPreferenceClass.setValue_string("PINSTATUS","CREATE");

                        startActivity(new Intent(PinCreateActivity.this,PinLoginActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(PinCreateActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        createPin();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(PinCreateActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(PinCreateActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

                params.put("emp_id",sharedPreferenceClass.getValue_string("BARBERID")); //B7982
                params.put("m_pin",newpin.getText().toString().trim()); //dipak
                params.put("device_id",device_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PinCreateActivity.this);
        requestQueue.add(stringRequest);
    }
}
