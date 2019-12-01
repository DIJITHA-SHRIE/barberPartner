package com.hair_beauty.partner.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceforLogout {

    private static final String USER_PREFS = "SeekingLogOut";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    // private String user_name = "user_name_prefs";
    // String user_id = "user_id_prefs";

    public SharedPreferenceforLogout(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    public String getValue_string(String stringKeyValue) {
        return appSharedPrefs.getString(stringKeyValue, "");
    }
    public void setValue_string(String stringKeyValue, String _stringValue) {

        prefsEditor.putString(stringKeyValue, _stringValue).commit();

    }
}
