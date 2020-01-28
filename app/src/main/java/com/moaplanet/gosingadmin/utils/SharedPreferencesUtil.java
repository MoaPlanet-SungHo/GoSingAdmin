package com.moaplanet.gosingadmin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.moaplanet.gosingadmin.constants.GoSingConstants;

public class SharedPreferencesUtil {

    private static SharedPreferences pref;

    private static class LazyHolder {
        private static final SharedPreferencesUtil INSTANCE = new SharedPreferencesUtil();
    }

    public static SharedPreferencesUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setPrefContext(Context prefContext) {
        if (pref == null) {
            pref = prefContext.getSharedPreferences(
                    GoSingConstants.GOSING_ADMIN_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
    }

    public void setPreference(String key, Object value) {
        SharedPreferences.Editor edit = pref.edit();
        if (edit != null) {
            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, (Integer) value);
            } else if (value instanceof Float) {
                edit.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                edit.putLong(key, (Long) value);
            }
            edit.apply();
        }
    }

    public void setIntroType(int type) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(GoSingConstants.INTRO_TYPE, type);
        editor.apply();
    }

    public int getType() {
        return pref.getInt(GoSingConstants.INTRO_TYPE, GoSingConstants.INTRO_TYPE_FIRST_START);
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

    public void setPin(String pin) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(GoSingConstants.USER_PIN, pin);
        editor.apply();
    }

    public String getPin() {
        return pref.getString(GoSingConstants.USER_PIN, "");
    }

}
