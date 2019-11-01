package com.moaplanet.gosingadmin.constants;

import java.util.HashSet;

public class GoSingConstants {

    // SharedPreferences 관련
    public static String GOSING_ADMIN_FILE_NAME = "GOSING_ADMIN";
    public static String TYPE_INTRO = "INTRO_TYPE";
    // 최초 실행
    public static int TYPE_FIRST_START = 0;
    // 권한 허용
    public static int TYPE_PERMISSION_CHECK_SUCCESS = 1;
    // 로그인 완료
    public static int TYPE_AUTO_LOGIN = 2;
    // 아이디
    public static String USER_ID = "userId";
    // 패스워드
    public static String USER_PW = "userPw";

    // bundle key 관련
    public static String BUNDLE_REQUEST_FROM_VIEW = "BUNDLE_REQUEST_FROM_VIEW";
    public static final String BUNDLE_KEY_TYPE_PASSWORD = "PASSWORD_TYPE";
    public static final String BUNDLE_KEY_BEFORE_INPUT_PASSWORD = "BEFORE_INPUT_PASSWORD";

    public static HashSet<String> TestSet;

    // bundle value 관련
    public static final String BUNDLE_VALUE_CHECK_PASSWORD = "CHECK_PASSWORD";
    public static final String BUNDLE_VALUE_NEW_PASSWORD = "NEW_PASSWORD";
}
