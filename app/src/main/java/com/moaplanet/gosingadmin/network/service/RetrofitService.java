package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitBuilder;

public class RetrofitService {
    public GoSingApiService getGoSingApiService() {
        return new RetrofitBuilder()
                .init(
                        GoSingConstants.GOSING_ADDIN_BASE_URL,
                        null,
                        GoSingApiService.class);
    }
}
