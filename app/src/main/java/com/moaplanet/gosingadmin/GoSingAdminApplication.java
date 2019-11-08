package com.moaplanet.gosingadmin;

import android.app.Application;

import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.multidex.MultiDexApplication;

//public class GoSingAdminApplication extends Application {
public class GoSingAdminApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
        sharedPreferencesManager.setPrefContext(getApplicationContext());
        //디버그 모드에서만 동작하도록 설정
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
