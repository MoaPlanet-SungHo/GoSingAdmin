package com.moaplanet.gosingadmin.network;

public class NetworkConstants {

    // 주소 key
    // 주소 검색 승인키
    public static String GOSING_ADMIN_ADDRESS_KEY = "U01TX0FVVEgyMDE5MTAzMDEwMTM0OTEwOTE1MTQ=";
    // 좌표 검색 승인키
    public static String GOSING_ADMIN_COORDINATES_KEY = "U01TX0FVVEgyMDE5MTAzMDExMDcwNzEwOTE1MTY=";

    // 통신 관련 부분
    public static String GOSING_ADMIN_BASE_URL = "http://14.36.10.149:8080/MOAGossingShop/";
    public static String ADDRESS_BASE_URL = "http://www.juso.go.kr/addrlink/";
    public static String IMAGE_BASE_URL = "https://image.goeat.co.kr/";

    // 개인정보 제 3자 제공동의
    public static String GOSING_ADMIN_AGREE_SHARE_PRIVATE_URL
            = "http://14.36.10.149:8080/MOAGossingShop/res/6.agree_share_private_info.html";
    // 개인정보 수집 및 이용동의
    public static String GOSING_ADMIN_AGREE_PRIVATE_INFO_URL
            = "http://14.36.10.149:8080/MOAGossingShop/res/5.agree_private_info.html";
    // 전자 금융 거래
    public static String GOSING_ADMIN_AGREE_ELET_FIN_URL
            = "http://14.36.10.149:8080/MOAGossingShop/res/4.agree_elet_fin.html";
    // 이용약관
    public static String GOSING_ADMIN_AGREE_TERMS_USE_URL
            = "http://14.36.10.149:8080/MOAGossingShop/res/3.TermsOPfUse.html";

    // 주소 페이징 개수
    public static int ADDRESS_SEACH_PAGING_COUNT = 30;

    // common
    // 성공
    public static int STATE_CODE_SUCCESS = 200;
    // 성공
    public static int DETAIL_CODE_SUCCESS = 200;
    // 로그인 필요 ( 세션이 없음 )
    public static int DETAIL_CODE_NOT_EXIST_SESSION = 500500;

    // 회원가입
    public static int CODE_SIGN_UP_SUCCESS = 200;
    public static int CODE_SIGN_UP_EXIST_ACCOUNT = 201;

    // 로그인
    public static int LOGIN_CODE_SUCCESS = 200;
    public static int LOGIN_CODE_NOT_MEMBER = 4000;
    public static int LOGIN_CODE_ACCOUNT_INACTIVE = 2012;
    public static int LOGIN_CODE_EMPTY_STORE = 2013;

    public static int EVENT_LIMIT = 100;
    public static int NOTICE_LIMIT = 100;
}
