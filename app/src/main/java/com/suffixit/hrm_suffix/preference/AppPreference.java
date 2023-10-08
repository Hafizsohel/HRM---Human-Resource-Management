package com.suffixit.hrm_suffix.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private static final String PREF_FILE_NAME = "HrmAppPrefs";
    private static final String KEY_USER_ID = "user_id";

    private SharedPreferences sharedPreferences;

    public AppPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
}
