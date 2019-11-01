package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface GoSingApiService {
    // 회원가입
    @POST("notLogin/s_join.json")
    Call<ResSignUpDto> signUp(@Query("user_email") String email,
                              @Query("pwd") String pwd,
                              @Query("sales_code") String code,
                              @Query("is_agree_event_noti") String type,
                              @Query("device_type") int deviceType,
                              @Query("signType") int signType);

    // 로그인
    @POST("notLogin/s_login_action.json")
    Call<ResLoginDto> login(@Query("user_email") String email,
                            @Query("pwd") String pwd,
                            @Query("signType") int signType);

    // 업소 등록
    @Multipart
    @POST("notLogin/s_shop_join_action.json")
    Call<ResStoreRegisterDto> registerStore(
            @Part("shop_info") ReqStoreRegisterDto reqStoreRegisterDto,
            @PartMap Map<String, RequestBody> files);

    // 업소 정보 조회
    @POST
    Call<ResStoreSearchDto> onStoreSearch(@Query("signType") int signType);
}
