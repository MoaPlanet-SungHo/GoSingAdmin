package com.moaplanet.gosingadmin.network;

public class NetworkConstants {

    // 통신 관련 부분
    public static String GOSING_ADDIN_BASE_URL = "http://175.198.102.230:8080/MOAGossingShop/";

    // common
    public static int STATE_CODE_SUCCESS = 200;

    // 회원가입
    public static int CODE_SIGN_UP_SUCCESS = 200;
    public static int CODE_SIGN_UP_EXIST_ACCOUNT = 201;

    // 로그인
    public static int CODE_LOGIN_SUCCESS = 200;
    public static int CODE_NOT_MEMBER = 4000;
    public static int CODE_ACCOUNT_INACTIVE = 2012;
}
