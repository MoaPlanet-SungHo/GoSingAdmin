package com.moaplanet.gosingadmin;

import com.moaplanet.gosingadmin.utils.SharedPreferencesUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.multidex.MultiDexApplication;

//public class GoSingAdminApplication extends Application {
public class GoSingAdminApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();
        sharedPreferencesUtil.setPrefContext(getApplicationContext());
        //디버그 모드에서만 동작하도록 설정
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
