package com.moaplanet.gosingadmin.network.service;

import com.moaplanet.gosingadmin.common.model.dto.res.ResStoreSearchDto;
import com.moaplanet.gosingadmin.intro.ResVersionDTO;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResCreateQrCodeDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResPaymentInitDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResQrCodeCheckDTO;
import com.moaplanet.gosingadmin.main.slide_menu.information.model.dto.res.ResInformationDto;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResSearchDepositAccount;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardChargeDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResRegisterCardDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResRegisterVirtualAccountDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResSearchVirtualAccountDto;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResNonMemberPointSaveInitDTO;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResNonMemberSavePointDTO;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResPointSaveNonMemberCheckDTO;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.ResNotificationDTO;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResBankInfoDto;
import com.moaplanet.gosingadmin.main.submenu.point.model.dto.ResPointHistoryDto;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResDepositablePointDto;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResPointWithDrawalDTO;
import com.moaplanet.gosingadmin.main.submenu.review.model.ResReviewDTO;
import com.moaplanet.gosingadmin.main.submenu.store.model.req.ReqStoreRegisterDto;
import com.moaplanet.gosingadmin.main.submenu.store.model.res.ResStoreRegisterDto;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

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
                                      @Query("signType") int signType,
                                      @Query("ci") String ci,
                                      @Query("user_name") String userName,
                                      @Query("user_age") String userAge,
                                      @Query("phone_number") String phoneNumber,
                                      @Query("user_gender") String userGender);

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
    Call<ResNotificationDTO> onServerNotificationList(@Query("alam_type") String alarmType,
                                                      @Query("check_point") String checkPoint);

    // 포인트 내역
    @POST("session/MemberManageCtr/point_page_info_list.json")
    Call<ResPointHistoryDto> onServerPointHistoryList(@Query("start_date") String startDate,
                                                      @Query("end_date") String endDate,
                                                      @Query("search_count") String searchCount,
                                                      @Query("pageNo") int pageNo,
                                                      @Query("limit") int limit);

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

    /**
     * QRCode 체크
     */
    @POST("session/PaymenyManageCtr/qrPaymentConfirm.json")
    Call<ResQrCodeCheckDTO> onServerCreateQrCodeCheck(
            @Query("shop_qr_seq") String shopQrSeq);

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

    /**
     * 가상 계좌 등록
     */
    @POST("session/PaymenyManageCtr/virtualAccount_write.json")
    Call<ResRegisterVirtualAccountDto> onServerRegisterVirtualAccount();

    /**
     * 은행 정보 불러오기
     */
    @POST("session/PaymenyManageCtr/withdraw_write_page_init.json")
    Call<ResBankInfoDto> onServerBankList();

    /**
     * 출금 계좌 조회
     */
    @POST("session/PaymenyManageCtr/withdraw_list.json")
    Call<ResSearchDepositAccount> onServerSearchDepositAccount();

    /**
     * 비회원 포인트 적립시 비회원 검사
     */
    @POST("session/PaymenyManageCtr/nonmmb_point_write_f_two.json")
    Call<ResPointSaveNonMemberCheckDTO> onServerPointSaveNonMemberCheck(
            @Query("phone_number") String phoneNumber);

    /**
     * 비회원 포인트 적립시 초기화
     */
    @POST("session/PaymenyManageCtr/nonmmb_point_page_init.json")
    Call<ResNonMemberPointSaveInitDTO> onServerNonMemberPointSaveInit();

    /**
     * 비회원 포인트 적립
     */
    @POST("session/PaymenyManageCtr/nonmmb_point_write_f.json")
    Call<ResNonMemberSavePointDTO> onServerNonMemberSavePoint(
            @Query("phone_number") String phoneNumber,
            @Query("point") String point
    );

    /**
     * 출금 요청
     */
    @POST("session/PaymenyManageCtr/withdraw_request_join.json")
    Call<ResPointWithDrawalDTO> onServerPointWithDrawal(
            @Query("withdraw_info_seq") String accountNumber,
            @Query("wthdr_amnt") String point
    );

    /**
     * 가상계좌 등록
     */
    @POST("session/PaymenyManageCtr/virtualAccount_write.json")
    Call<CommonResDto> onServerOpenBankAccount(
            @Query("bank_cd") String bankPk,
            @Query("bank_nm") String BankName,
            @Query("EP_vacct_txtype") String txType
    );

    /**
     * 출금 계좌 등록
     */
    @POST("session/PaymenyManageCtr/withdraw_join.json")
    Call<CommonResDto> onServerRegisterWithdrawalBank(
            @Query("back_cd") String bankPk,
            @Query("back_name") String BankName,
            @Query("account_number") String accountNumber,
            @Query("user_name") String userName
    );

    /**
     * 출금 가능 금액
     */
    @POST("session/PaymenyManageCtr/fees_point.json")
    Call<ResDepositablePointDto> onServerDepositablePoint();

    /**
     * 앱 버전 체크
     */
    @POST("MemberManageCtr/app_version_info.json")
    Call<ResVersionDTO> onServerAppVersionCheck(
            @Query("app_type") String deviceType,
            @Query("side_type") String userType);

    /**
     * 리뷰 리스트 불러오기
     *
     * @param pageNo     불러올 리뷰 페이지
     * @param limit      불러올 리뷰 개수
     * @param reviewType 리뷰 리스트 타입 -> ( 1 : 전체 리뷰, 2 : 덧글 없는 리뷰 )
     */
    @POST("session/ReviewManageCtr/review_list.json")
    Call<ResReviewDTO> onServerReviewLis(@Query("pageNo") int pageNo,
                                         @Query("limit") int limit,
                                         @Query("shop_count") int reviewType);

    /**
     * 리뷰 답변 수정 및 추가
     *
     * @param pk    리류 pk 값
     * @param reply 리뷰 답변
     */
    @POST("session/ReviewManageCtr/review_content_join.json")
    Call<CommonResDto> onServerReviewModifyOrRegister(@Query("review_seq") String pk,
                                                      @Query("shop_comment") String reply);

    /**
     * 리뷰 삭제
     *
     * @param reviewPk 리뷰 pk 값
     */
    @POST("session/ReviewManageCtr/review_comment_del.json")
    Call<CommonResDto> onServerReviewRemove(@Query("review_seq") String reviewPk);


}

