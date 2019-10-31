package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressCoordDto;
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

    @POST("addrCoordApi.do")
    Call<ResAddressCoordDto> searchCoord(
            @Query("confmKey") String key,
            @Query("admCd") String admCd,
            @Query("rnMgtSn") String rnMgtSn,
            @Query("udrtYn") String udrtYn,
            @Query("buldMnnm") String buldMnnm,
            @Query("buldSlno") String buldSlno,
            @Query("resultType") String resultType
    );

}
