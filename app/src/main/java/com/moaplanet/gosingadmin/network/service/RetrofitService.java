package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthConfig;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitBuilder;
import com.moaplanet.gosingadmin.network.retrofit.SessionChecker;

public class RetrofitService {

    private MoaAuthConfig moaAuthConfig;
    private SessionChecker sessionChecker;
    private GoSingApiService goSingApiService;

    private GoSingApiService getGoSingApiService2() {
        return new RetrofitBuilder()
                .init(
                        NetworkConstants.GOSING_ADDIN_BASE_URL,
                        null,
                        GoSingApiService.class);
    }


    private static class LazyHolder {
        private static final RetrofitService INSTANCE = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return LazyHolder.INSTANCE;
    }

    public GoSingApiService getGoSingApiService() {
        if (goSingApiService == null) {
            goSingApiService = getGoSingApiService2();
        }
        return goSingApiService;
    }

    public MoaAuthConfig getMoaAuthConfig() {
        if (moaAuthConfig == null) {
            MoaAuthConfig moaAuthConfig = new MoaAuthConfig();
            moaAuthConfig.setLogTagName("moaAuthConfig");
            moaAuthConfig.setTotalRetryCnt(3);
        }
        return moaAuthConfig;
    }

    public SessionChecker getSessionChecker() {
        if (sessionChecker == null) {
            sessionChecker = new SessionChecker();
            sessionChecker.setMoaAuthConfig(moaAuthConfig);
        }
        return sessionChecker;
    }
}
