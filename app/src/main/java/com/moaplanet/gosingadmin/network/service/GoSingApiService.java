package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResCreateQrCodeDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResPaymentInitDto;
import com.moaplanet.gosingadmin.main.slide_menu.information.model.dto.res.ResInformationDto;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.res.ResNotificationDto;
import com.moaplanet.gosingadmin.main.submenu.point.dto.res.ResPointHistoryDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface GoSingApiService {
    // 회원가입
    @POST("MemberManageCtr/s_join.json")
    Call<ResSignUpDto> onServerSignUp(@Query("user_email") String email,
                              @Query("pwd") String pwd,
                              @Query("sales_code") String code,
                              @Query("is_agree_event_noti") String type,
                              @Query("device_type") int deviceType,
                              @Query("signType") int signType);

    // 로그인
    @POST("MemberManageCtr/s_login_action.json")
    Call<ResLoginDto> onServerLogin(@Query("user_email") String email,
                                    @Query("pwd") String pwd,
                                    @Query("signType") int signType);

    // 업소 등록
    @Multipart
    @POST("session/MemberManageCtr/s_shop_join_action.json")
    Call<ResStoreRegisterDto> onServerRegisterStore(
            @Part("shop_info") ReqStoreRegisterDto reqStoreRegisterDto,
            @PartMap Map<String, RequestBody> files);

    // 업소 정보 조회
    @POST("session/MemberManageCtr/s_shop_join_serch.json")
    Call<ResStoreSearchDto> onServerStoreSearch(@Query("signType") int signType);

    // 알림 리스트
    @POST("session/MemberManageCtr/alert_list_serach.json")
    Call<ResNotificationDto> onServerNotificationList(@Query("alam_type") String alarmType,
                                                      @Query("check_point") String checkPoint);

    // 포인트 내역
    @POST("session/MemberManageCtr/point_page_info_list.json")
    Call<ResPointHistoryDto> onServerPointHistoryList(@Query("start_date") String startDate,
                                                      @Query("end_date") String endDate,
                                                      @Query("search_count")String searchCount);


    // 고씽 포인트 조회
    @POST("session/MemberManageCtr/gossingPointSearch_Init.json")
    Call<ResGoSingPointSearchDto> onServerGoSingPoint();

    // 고씽 내정보 조회
    @POST("session/MemberManageCtr/my_info_search_f.json")
    Call<ResInformationDto> onServerMyInformation();

    // QR코드 초기화 관련
    @POST("session/PaymenyManageCtr/qr_code_page_init.json")
    Call<ResPaymentInitDto> onServerPaymentInit();

    /**
     * QRCode 생성
     *
     * @param qrCodePk       qrCode pk 값
     * @param reservePrice   적립 가격
     * @param noReservePrice 비적립 가격
     */
    @POST("session/PaymenyManageCtr/qrCodeWrite.json")
    Call<ResCreateQrCodeDto> onServerCreateQrCode(
            @Query("shop_qr_save_history_seq") String qrCodePk,
            @Query("price") String reservePrice,
            @Query("non_ernng_price") String noReservePrice);

    // 카드 리스트 불러오기
    @POST("session/PaymenyManageCtr/card_list_Return.json")
    Call<ResCardListDto> onServerCardList();
}
