package com.moaplanet.gosingadmin.network;

public class NetworkConstants {

    // 주소 key
    // 주소 검색 승인키
    public static String GOSING_ADMIN_ADDRESS_KEY = "U01TX0FVVEgyMDE5MTAzMDEwMTM0OTEwOTE1MTQ=";
    // 좌표 검색 승인키
    public static String GOSING_ADMIN_COORDINATES_KEY = "U01TX0FVVEgyMDE5MTAzMDExMDcwNzEwOTE1MTY=";

    // 통신 관련 부분
    public static String GOSING_ADMIN_BASE_URL = "http://175.198.102.230:8080/MOAGossingShop/";
    //    public static String GOSING_ADMIN_BASE_URL = "http://192.168.0.11:8080/MOAGossingShop/";
    //    public static String GOSING_ADMIN_BASE_URL = "http://172.30.1.160:8080/";
    public static String ADDRESS_BASE_URL = "http://www.juso.go.kr/addrlink/";

    // 주소 페이징 개수
    public static int ADDRESS_SEACH_PAGING_COUNT = 30;

    // common
    public static int STATE_CODE_SUCCESS = 200;

    // 회원가입
    public static int CODE_SIGN_UP_SUCCESS = 200;
    public static int CODE_SIGN_UP_EXIST_ACCOUNT = 201;

    // 로그인
    public static int CODE_LOGIN_SUCCESS = 200;
    public static int CODE_NOT_MEMBER = 4000;
    public static int CODE_ACCOUNT_INACTIVE = 2012;
    public static int CODE_ACCOUNT_DISINACTIVE = 2013;
}
