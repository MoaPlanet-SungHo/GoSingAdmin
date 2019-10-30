package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jiwun on 2019-10-30.
 */
public interface AddressApiService {

    @POST("addrLinkApi.do")
    Call<ResAddressSearchDto> searchAddress(
            @Query("confmKey") String key,
            @Query("currentPage") int currentPage,
            @Query("countPerPage") int countPerPage,
            @Query("keyword") String keyword,
            @Query("resultType") String resultType
    );

}
