package com.hair_beauty.partner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hair_beauty.partner.Adapter.SettingAdapter;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recycleview;
    SettingAdapter settingAdapter;
    String[] list = {"Set Locations","My Account","About Us","Support","Career","Terms & Conditions","Privacy Policy","Cancelation Policy","FAQ"};
    int[] icons = {R.drawable.location,R.drawable.account,R.drawable.helpcenter1,R.drawable.support1,
            R.drawable.careers1,R.drawable.terms1,R.drawable.privacy,R.drawable.privacy,
    R.drawable.terms1};
    //,R.drawable.signout

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recycleview = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SettingActivity.this, LinearLayoutManager.VERTICAL, false);
        recycleview.setLayoutManager(mLayoutManager);

        settingAdapter= new SettingAdapter(SettingActivity.this,list,icons);
        recycleview.setAdapter(settingAdapter);


    }
}
