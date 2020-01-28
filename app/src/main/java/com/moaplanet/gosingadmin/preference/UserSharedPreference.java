package com.moaplanet.gosingadmin.preference;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.utils.SharedPreferencesUtil;

/**
 * 유저 관리용 SharedPreference
 */
public class UserSharedPreference {

    public void onLogout() {
        SharedPreferencesUtil pref = SharedPreferencesUtil.getInstance();
        pref.setPreference(GoSingConstants.INTRO_TYPE, GoSingConstants.INTRO_TYPE_LOGOUT);
        pref.setPreference(GoSingConstants.USER_ID, "");
        pref.setPreference(GoSingConstants.USER_PW, "");
        pref.setPreference(GoSingConstants.USER_PIN, "");
    }

}
