package com.moaplanet.gosingadmin.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.moaplanet.gosingadmin.constants.GoSingConstants;

public class SharedPreferencesManager {

    private static SharedPreferences pref;

    private static class LazyHolder {
        private static final SharedPreferencesManager INSTANCE = new SharedPreferencesManager();
    }

    public static SharedPreferencesManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setPrefContext(Context prefContext) {
        if (pref == null) {
            pref = prefContext.getSharedPreferences(
                    GoSingConstants.GOSING_ADMIN_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
    }

    public void setIntroType(int type) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(GoSingConstants.INTRO_TYPE, type);
        editor.apply();
    }

    public int getType() {
        return pref.getInt(GoSingConstants.INTRO_TYPE, GoSingConstants.INTRO_TYPE_ERROR);
    }

    public String getEmail() {
        return pref.getString(GoSingConstants.USER_ID, "");
    }

    public String getPw() {
        return pref.getString(GoSingConstants.USER_PW, "");
    }

    public void setLoginInfo(String id, String pw) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(GoSingConstants.USER_ID, id);
        editor.putString(GoSingConstants.USER_PW, pw);
        editor.apply();

    }

}
