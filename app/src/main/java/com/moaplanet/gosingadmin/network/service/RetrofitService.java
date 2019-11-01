package com.moaplanet.gosingadmin.network.service;

import android.content.Context;

import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthConfig;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitBuilder;
import com.moaplanet.gosingadmin.network.retrofit.SessionChecker;

public class RetrofitService {

    private MoaAuthConfig moaAuthConfig;
    private SessionChecker sessionChecker;
    private GoSingApiService goSingApiService;
    private AddressApiService addressApiService;

    private static class LazyHolder {
        private static final RetrofitService INSTANCE = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return LazyHolder.INSTANCE;
    }

     synchronized public GoSingApiService getGoSingApiService(Context context) {
        if (goSingApiService == null) {
            goSingApiService =
                    new RetrofitBuilder().init(
                            NetworkConstants.GOSING_ADMIN_BASE_URL,
                            null,
                            GoSingApiService.class, context);
        }
        getMoaAuthConfig();
        getSessionChecker();
        return goSingApiService;
    }

    public AddressApiService getAddressApiService() {
        if (addressApiService == null) {
            addressApiService =
                    new RetrofitBuilder().init(
                            NetworkConstants.ADDRESS_BASE_URL,
                            null,
                            AddressApiService.class, null);
        }
        return addressApiService;
    }

    synchronized public MoaAuthConfig getMoaAuthConfig() {
        if (moaAuthConfig == null) {
            moaAuthConfig = new MoaAuthConfig();
            moaAuthConfig.setLogTagName("moaAuthConfig");
            moaAuthConfig.setTotalRetryCnt(3);
        }
        return moaAuthConfig;
    }

    synchronized public SessionChecker getSessionChecker() {
        if (sessionChecker == null) {
            sessionChecker = new SessionChecker();
            sessionChecker.setMoaAuthConfig(moaAuthConfig);
        }
        return sessionChecker;
    }
}
