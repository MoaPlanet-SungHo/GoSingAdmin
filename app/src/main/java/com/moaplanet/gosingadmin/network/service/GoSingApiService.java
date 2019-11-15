package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResCreateQrCodeDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResPaymentInitDto;
import com.moaplanet.gosingadmin.main.slide_menu.information.model.dto.res.ResInformationDto;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardChargeDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResRegisterCardDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResSearchVirtualAccountDto;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.res.ResNotificationDto;
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

    /**
     * 카드로 포인트 충전
     *
     * @param CardHash     카드 해쉬 pk
     * @param EpProductAmt 충전할 금액
     */
    @POST("session/PaymenyManageCtr/card_pymnt_action.json")
    Call<ResCardChargeDto> onServerCardCharge(@Query("card_hash") String CardHash,
                                              @Query("EP_product_amt") String EpProductAmt);

    /**
     * 카드 등록
     *
     * @param cardNumberOne   카드번호 1
     * @param cardNumberTwo   카드번호 2
     * @param cardNumberThree 카드번호 3
     * @param cardNumberFour  카드번호 4
     * @param year            카드 년도
     * @param month           카드 월
     * @param pw              비밀번호 2자리
     * @param birthday        생일 혹은 사업자 등록번호
     * @param cardName        카드 이름
     */
    @POST("session/PaymenyManageCtr/card_join.json")
    Call<ResRegisterCardDto> onServerRegisterCard(@Query("EP_card_no1") String cardNumberOne,
                                                  @Query("EP_card_no2") String cardNumberTwo,
                                                  @Query("EP_card_no3") String cardNumberThree,
                                                  @Query("EP_card_no4") String cardNumberFour,
                                                  @Query("EP_expire_date_yy") String year,
                                                  @Query("EP_expire_date_mm") String month,
                                                  @Query("EP_password") String pw,
                                                  @Query("EP_auth_value") String birthday,
                                                  @Query("card_name") String cardName);

    /**
     * 가상계좌 조회
     */
    @POST("session/PaymenyManageCtr/virtualAccount.json")
    Call<ResSearchVirtualAccountDto> onServerSearchVirtualAccount();
}

