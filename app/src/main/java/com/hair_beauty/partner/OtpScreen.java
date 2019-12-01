package com.hair_beauty.partner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hair_beauty.partner.utility.InputValidation;
import com.hair_beauty.partner.utility.SharedPreferenceClass;
import com.hair_beauty.partner.utility.SharedPreferenceforLogout;

public class OtpScreen extends AppCompatActivity {

    Button generate,cancel;
    EditText otptext;
    String otpValue, otpText;
    SharedPreferenceforLogout sharedPreferenceClass;
    SharedPreferenceClass sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpscreen);
        sharedPreferenceClass = new SharedPreferenceforLogout(OtpScreen.this);
        sp = new SharedPreferenceClass(OtpScreen.this);
        otptext = findViewById(R.id.otptext);
        generate = findViewById(R.id.generate);
        cancel = findViewById(R.id.cancel);
        otpValue = getIntent().getStringExtra("OTP");

        Log.i("OTPVal",otpValue);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpText = otptext.getText().toString().trim();

                if(!InputValidation.isEditTextHasvalue(otptext)){
                    Toast.makeText(OtpScreen.this, "Please enter your OTP", Toast.LENGTH_SHORT).show();
                }
                else if ( otpText.equals(otpValue)) {
                        Toast.makeText(OtpScreen.this, "OTP matched.", Toast.LENGTH_SHORT).show();
                        if(sharedPreferenceClass.getValue_string("LOGOUTID").equals("1")){
                            sp.setValue_string("PINSTATUS","CREATE");
                            startActivity(new Intent(OtpScreen.this,PinLoginActivity.class));
                            finish();
                        }
                        else{
                        startActivity(new Intent(OtpScreen.this,PinCreateActivity.class));
                            finish();}

                }
                else{
                    Toast.makeText(OtpScreen.this, otptext.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(OtpScreen.this, otpValue, Toast.LENGTH_SHORT).show();
                    Toast.makeText(OtpScreen.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
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
}
