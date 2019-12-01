package com.hair_beauty.partner.notification;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.hair_beauty.partner.PinLoginActivity;
import com.hair_beauty.partner.utility.Constants;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService
{

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(Constants.FIREBASE_TOKEN, refreshedToken).apply();
*/
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());
        sharedPreferenceClass.setValue_string(Constants.FIREBASE_TOKEN,refreshedToken);
    }
}
