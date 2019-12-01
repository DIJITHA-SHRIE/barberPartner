package com.hair_beauty.partner.PageIndicator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hair_beauty.partner.LoginScreen;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

/**
 * Created by PCIS-ANDROID on 29-02-2016.
 */

public class Get_starting extends AppTour {
    SharedPreferenceClass sharedPreferenceClass;
    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        int customSlideColor1 = Color.parseColor("#3997d3");
        int customSlideColor2 = Color.parseColor("#FBB871");
        int thirdColor = Color.parseColor("#4EC4D2");
        int forthColor = Color.parseColor("#F48B8B");
        int customSlideColor = Color.parseColor("#EBA6CA");

       /* //Create pre-created fragments
        Fragment firstSlide = MaterialSlide.newInstance(R.drawable.tour_logo1, "Presentations on the go",
                "Get stuff done with or without an internet connection.", Color.WHITE, Color.WHITE);

        Fragment secondSlide = MaterialSlide.newInstance(R.drawable.tour_logo2, "Share and edit together",
                "Write on your own or invite more people to contribute.", Color.WHITE, Color.WHITE);*/

        /*Fragment thirdSlide = MaterialSlide.newInstance(R.drawable.tour_logo3, "Instance Doctors Appointment\nAt your fingertip",
                "Get instant booking of\nProfessional Physicians", Color.WHITE, Color.WHITE);

        Fragment forthSlide = MaterialSlide.newInstance(R.drawable.tour_logo4, "Instance Diagnostic Services \nAt your doorstep",
                "Experience fastest,Reliable\nDiagnostic Servives at your Doorstep", Color.WHITE, Color.WHITE);*/

        //Add slides
        /*addSlide(new CustomSlide1(), customSlideColor1);
        addSlide(new CustomSlide2(), customSlideColor2);
        addSlide(thirdSlide, thirdColor);
        addSlide(forthSlide, forthColor);*/

        //Custom slide
       // addSlide(new CustomSlide(), customSlideColor);

//        if (!sharedPreferenceClass.getValue_string("PINSTATUS").equals("CREATE")){
//            Intent i = new Intent(Get_starting.this,PinLoginActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(i);
//            finish();
//        }
//        else if (!sharedPreferenceClass.getValue_string("PINSTATUS").equals("LOGIN")){
//            Intent i = new Intent(Get_starting.this,NavigationDrawerActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(i);
//            finish();
//        }
//        else{
//            Intent i = new Intent(WelcomeScreen.this,Get_starting.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(i);
//            finish();
//        }

        addSlide(new Custom_fragment1());
        addSlide(new Custom_fragment2());
        addSlide(new Custom_fragment3());
        addSlide(new Custom_fragment4());

        //Customize tour
        setSkipButtonTextColor(Color.WHITE);
        setDoneButtonTextColor(Color.WHITE);
        //(Color.WHITE);
    }

    @Override
    public void onSkipPressed() {
        Intent intent=new Intent(Get_starting.this,LoginScreen.class);
        startActivity(intent);
        // overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }

    @Override
    public void onDonePressed() {
        Intent intent=new Intent(Get_starting.this,LoginScreen.class);
        startActivity(intent);
      // overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }
}
