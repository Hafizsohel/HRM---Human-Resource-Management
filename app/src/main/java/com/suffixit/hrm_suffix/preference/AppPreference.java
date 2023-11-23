package com.suffixit.hrm_suffix.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.rpc.context.AttributeContext;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.utils.AppConstants;

public class AppPreference {

    private static final String PREF_FILE_NAME = "HrmAppPrefs";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserId(String userId) {
        editor.putString(AppConstants.KEY_USER_ID, userId);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(AppConstants.KEY_USER_ID, null);
    }

    public void putUserName(String name) {
        editor.putString(AppConstants.USER_NAME, name);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(AppConstants.USER_NAME, null);
    }
    public String getPassword() {
        return sharedPreferences.getString(AppConstants.USER_PASSWORD, null);
    }

    public void putLoginResponse(boolean isLogin) {
        editor.putBoolean(AppConstants.IS_LOGIN, isLogin);
        editor.commit();
    }
    public boolean getLoginResponse() {
        return sharedPreferences.getBoolean(AppConstants.IS_LOGIN, false);
    }
/*    public void putLogoutResponse(Boolean isLogout){
        editor.putBoolean(AppConstants.IS_LOGOUT, isLogout);
        editor.commit();
    }
    public boolean getLogoutResponse() {
        return sharedPreferences.getBoolean(AppConstants.IS_LOGOUT, false);
    }
    public boolean isLoggedOut() {
        return sharedPreferences.getBoolean(AppConstants.IS_LOGOUT, false);
    }

    // Clear logout flag
    public void clearLogout() {
        editor.putBoolean(AppConstants.IS_LOGOUT, false);
        editor.apply();
    }
    // Clear saved username
    public void clearUsername() {
        editor.remove(AppConstants.USER_NAME);
        editor.apply();
    }

    // Clear saved password
    public void clearPassword() {
        editor.remove(AppConstants.USER_PASSWORD);
        editor.apply();
    }*/

    public void putLogoutResponse(boolean isLogout) {
        editor.putBoolean(AppConstants.IS_LOGOUT, isLogout);
        editor.apply();
    }

    public boolean getLogoutResponse() {
        return sharedPreferences.getBoolean(AppConstants.IS_LOGOUT, false);
    }

    public void clearUsername() {
        editor.remove(AppConstants.KEY_USER_ID);
        editor.apply();
    }

    public void clearPassword() {
        editor.remove(AppConstants.USER_PASSWORD);
        editor.apply();
    }
}
