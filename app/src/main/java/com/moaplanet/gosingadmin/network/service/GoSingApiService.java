package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoSingApiService {
    @POST("notLogin/s_join.json")
    Call<ResSignUpDto> signUp(@Query("user_email") String email,
                              @Query("pwd") String pwd,
                              @Query("sales_code") String code,
                              @Query("is_agree_event_noti") String type,
                              @Query("device_type") int deviceType,
                              @Query("signType") int signType);
}
