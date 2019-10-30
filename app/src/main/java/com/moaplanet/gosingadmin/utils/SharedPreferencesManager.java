package com.moaplanet.gosingadmin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.moaplanet.gosingadmin.constants.GoSingConstants;

public class SharedPreferencesManager {

    private Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public void setIntroType(int type) {
        SharedPreferences pref = context.getSharedPreferences(
                GoSingConstants.GOSING_ADMIN_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(GoSingConstants.TYPE_INTRO, type);
        editor.apply();
    }

    public int getType() {
        SharedPreferences pref = context.getSharedPreferences(
                GoSingConstants.GOSING_ADMIN_FILE_NAME,
                Context.MODE_PRIVATE);
        return pref.getInt(GoSingConstants.TYPE_INTRO, 0);
    }

    public String getEmail() {
        SharedPreferences pref = context.getSharedPreferences(
                GoSingConstants.GOSING_ADMIN_FILE_NAME,
                Context.MODE_PRIVATE);
        return pref.getString(GoSingConstants.USER_ID, "");
    }

    public String getPw() {
        SharedPreferences pref = context.getSharedPreferences(
                GoSingConstants.GOSING_ADMIN_FILE_NAME,
                Context.MODE_PRIVATE);
        return pref.getString(GoSingConstants.USER_PW, "");
    }

    public void setLoginInfo(String id, String pw) {
        SharedPreferences pref = context.getSharedPreferences(
                GoSingConstants.GOSING_ADMIN_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(GoSingConstants.USER_ID, id);
        editor.putString(GoSingConstants.USER_PW, pw);
        editor.apply();

    }

}
