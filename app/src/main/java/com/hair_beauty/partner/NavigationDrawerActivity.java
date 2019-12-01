package com.hair_beauty.partner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hair_beauty.partner.Adapter.TodayAppt_adp;
import com.hair_beauty.partner.Model.Myservices_Mod;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;
import com.hair_beauty.partner.utility.SharedPreferenceforLogout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by apple1 on 9/21/17.
 */

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    android.support.v4.app.FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ImageView searchicon;
    private int i = 0;
    String phone;
    TextView tag;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout search_area;
    SharedPreferenceClass sharedPreferenceClass;
    ArrayList<Myservices_Mod> todayArray = new ArrayList<>();
    String getsaloon_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.drawable.logotext);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPreferenceClass = new SharedPreferenceClass(NavigationDrawerActivity.this);
        getsaloon_id = sharedPreferenceClass.getValue_string("BARBERID");

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationDrawerActivity.this, RegistrationPage.class));
            }
        });*/


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView ownerHeadName = (TextView) headerView.findViewById(R.id.ownerName);
        TextView ownerHeadID = (TextView) headerView.findViewById(R.id.OwnerId);
        ownerHeadName.setText(sharedPreferenceClass.getValue_string("BARBER_NAME"));
        ownerHeadID.setText(sharedPreferenceClass.getValue_string("BARBERID"));


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new HomePage()).commit();




    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.home:
                fragment = new HomePage();
                break;
            case R.id.refer_earn:
                startActivity(new Intent(NavigationDrawerActivity.this, ReferActivity.class));
                break;

            case R.id.settings:
                startActivity(new Intent(NavigationDrawerActivity.this, SettingActivity.class));
                break;
           /* case R.id.about_us:
                startActivity(new Intent(NavigationDrawerActivity.this,AboutUsActivity.class));
                break;

            case R.id.Support:
                startActivity(new Intent(NavigationDrawerActivity.this,SupportActivity.class));
                 break;

            case R.id.terms:
                startActivity(new Intent(NavigationDrawerActivity.this,TermsActivity.class));
                break;

            case R.id.privacy:
                startActivity(new Intent(NavigationDrawerActivity.this,Privacyactivity.class));
                break;

            case R.id.cancel:
                startActivity(new Intent(NavigationDrawerActivity.this,CancelpolicyActivity.class));
                break;

            case R.id.faq:
                startActivity(new Intent(NavigationDrawerActivity.this,FaqActivity.class));
                break;*/
            //////////////

           /*
            case R.id.contact_us:

                startActivity(new Intent(NavigationDrawerActivity.this,Contact_us.class));
                break;

            case R.id.share_app:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sendIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.empireshotel.empires&hl=en" );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            case R.id.rate_us:
                RateThisApp.init(new RateThisApp.Config(3, 5));
                RateThisApp.showRateDialog(NavigationDrawerActivity.this);

                *//*startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.empireshotel.empires&hl=en")));*//*


                break;


        */
            case R.id.my_appointment:
                getTodayAppointment();

                break;


            case R.id.services:
                //startActivity(new Intent(NavigationDrawerActivity.this,MyServices.class));
                fragment = new MyServicesFrag();
                break;
            case R.id.setup_business:
                fragment = new HomePage();
                break;
            case R.id.search:
                fragment = new HomePage();
                break;
            case R.id.offers:
                fragment = new HomePage();
                break;




            case R.id.logout:
                SharedPreferenceforLogout sharedPreferenceClassForLogout;
                sharedPreferenceClassForLogout = new SharedPreferenceforLogout(NavigationDrawerActivity.this);
                sharedPreferenceClassForLogout.setValue_string("LOGOUTID","1");
                String USER_PREFS = "SeekingDaddie";
                SharedPreferences preferences = getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerView, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification, menu);
        return true;
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
                            todayArray.add(new Myservices_Mod(row.getString("name"),
                                    row.getString("style_name"), row.getString("start_time")));
                        }
                        if(todayArray.size()>0){
                        Fragment fragment = MyAppointment.newInstance("HOME_TODAY", todayArray);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.containerView, fragment);
                        ft.commit();}
                        else{
                            Fragment fragment = new HomePage();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.containerView, fragment);
                            ft.commit();
                        }

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

                Log.i("saloon_id", getsaloon_id);
                params.put("owned_by", getsaloon_id); //B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(NavigationDrawerActivity.this);
        requestQueue.add(stringRequest);
    }

}