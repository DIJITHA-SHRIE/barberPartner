package com.hair_beauty.partner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hair_beauty.partner.PageIndicator.Get_starting;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

/**
 * Created by apple1 on 8/16/17.
 */

public class WelcomeScreen extends Activity {
    /** Called when the activity is first created. */
    SharedPreferenceClass sharedPreferenceClass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.custom_slide1);
        sharedPreferenceClass = new SharedPreferenceClass(WelcomeScreen.this);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    // do nothing
                } finally {

                    if (sharedPreferenceClass.getValue_string("PINSTATUS").equals("CREATE")){
                        Intent i = new Intent(WelcomeScreen.this,PinLoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                        finish();
                    }
                    else if (sharedPreferenceClass.getValue_string("PINSTATUS").equals("LOGIN")){
                        Intent i = new Intent(WelcomeScreen.this,NavigationDrawerActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Intent i = new Intent(WelcomeScreen.this,Get_starting.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                        finish();
                    }

                }
            }
        };
        splashThread.start();
    }
}
