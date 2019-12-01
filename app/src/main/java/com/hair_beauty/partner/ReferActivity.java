package com.hair_beauty.partner;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hair_beauty.partner.utility.SharedPreferenceClass;

public class ReferActivity extends AppCompatActivity {

    Button referSubmit;
    EditText name, number, referalCode;
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferenceClass = new SharedPreferenceClass(ReferActivity.this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name = findViewById(R.id.input_name);
        number = findViewById(R.id.input_number);
        referalCode = findViewById(R.id.input_refer);

        if(!sharedPreferenceClass.getValue_string("BARBER_REFERALCODE").equals("") || sharedPreferenceClass.getValue_string("BARBER_REFERALCODE") != null){
            referalCode.setText(sharedPreferenceClass.getValue_string("BARBER_REFERALCODE"));
        }
      //  referalCode.setText("abc56fg");
        referalCode.setAllCaps(true);

        referSubmit = findViewById(R.id.refer_submit);



    }
}
