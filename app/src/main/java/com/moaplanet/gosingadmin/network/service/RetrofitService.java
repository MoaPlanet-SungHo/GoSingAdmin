package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthConfig;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitBuilder;
import com.moaplanet.gosingadmin.network.retrofit.SessionChecker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private MoaAuthConfig moaAuthConfig;
    private SessionChecker sessionChecker;
    private GoSingApiService goSingApiService;
    private AddressApiService addressApiService;
    private GoSingApiService goSingApi;

    private RetrofitService() {
        getSessionChecker();
        getMoaAuthConfig();
        getAddressApiService();
        getGoSingApiService();
        getGoSingApi();
    }

    private static class LazyHolder {
        private static final RetrofitService INSTANCE = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return LazyHolder.INSTANCE;
    }

    public GoSingApiService getGoSingApi() {
        if (goSingApi == null) {
            goSingApi = new RetrofitBuilder().init(
                    NetworkConstants.GOSING_ADMIN_BASE_URL,
                    GoSingApiService.class);
        }
        return goSingApi;
    }

    public GoSingApiService getGoSingApiService() {
        if (goSingApiService == null) {
            goSingApiService =
                    new RetrofitBuilder().init(
                            NetworkConstants.GOSING_ADMIN_BASE_URL,
                            null,
                            GoSingApiService.class);
        }
        return goSingApiService;
    }

    public AddressApiService getAddressApiService() {
        if (addressApiService == null) {
            addressApiService =
                    new RetrofitBuilder().init(
                            NetworkConstants.ADDRESS_BASE_URL,
                            null,
                            AddressApiService.class);
        }
        return addressApiService;
    }

    public MoaAuthConfig getMoaAuthConfig() {
        if (moaAuthConfig == null) {
            moaAuthConfig = new MoaAuthConfig();
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
