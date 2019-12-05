package com.moaplanet.gosingadmin.constants;

import java.util.HashSet;

public class GoSingConstants {

    // SharedPreferences 관련
    public static String GOSING_ADMIN_FILE_NAME = "GOSING_ADMIN";
    public static String INTRO_TYPE = "INTRO_TYPE";
    // 최초 실행
    public static int INTRO_TYPE_FIRST_START = 0;
    // 권한 허용
    public static int INTRO_TYPE_PERMISSION_CHECK_SUCCESS = 1;
    // 로그인 완료
    public static int INTRO_TYPE_AUTO_LOGIN = 2;
    // 에러 타입 일경울
    public static int INTRO_TYPE_ERROR = -999;
    // 아이디
    public static String USER_ID = "userId";
    // 패스워드
    public static String USER_PW = "userPw";
    // 핀
    public final static String USER_PIN = "userPin";

    public static HashSet<String> cookieHash;

    // Bundle 관련
    // 인드로에서 앱 버전 체크 관련 --> true : 체크 | false : 체크 안함
    public static final String BUNDLE_KEY_APP_VERSION_CHECK = "BUNDLE_KEY_APP_VERSION_CHECK";
    public static final boolean BUNDLE_VALUE_APP_VERSION_CHECK = true;
    public static final boolean BUNDLE_VALUE_APP_VERSION_NOT_CHECK = false;

    // bundle key 관련
    public static String BUNDLE_REQUEST_FROM_VIEW = "BUNDLE_REQUEST_FROM_VIEW";
    public static final String BUNDLE_KEY_TYPE_PASSWORD = "PASSWORD_TYPE";
    public static final String BUNDLE_KEY_BEFORE_INPUT_PASSWORD = "BEFORE_INPUT_PASSWORD";
    public static final String BUNDLE_KEY_MOBILE_AUTH_TYPE = "BUNDLE_KEY_MOBILE_AUTH_TYPE";
    // 포인트 관련
    public static final String BUNDLE_KEY_TYPE_POINT_VIEW = "BUNDLE_KEY_TYPE_POINT_VIEW";

    // bundle value 관련
    public static final String BUNDLE_VALUE_CHECK_PASSWORD = "CHECK_PASSWORD";
    public static final String BUNDLE_VALUE_NEW_PASSWORD = "NEW_PASSWORD";
    // 포인트 관련
    public static final String BUNDLE_VALUE_POINT_VIEW_ALL = "1";
    // 입금만 조회
    public static final String BUNDLE_VALUE_POINT_VIEW_DEPOSIT = "2";
    // 출금만 조회
    public static final String BUNDLE_VALUE_POINT_VIEW_WITHDRAWAL = "3";

    // 주소 검색 관련
    public static final int REQ_CODE_ADDRESS_SEARCH = 3000;
    public static final int RESULT_CODE_ADDRESS_SEARCH = 30001;

    // 계좌 번호 변경
    public static final int REQ_CODE_CHANGE_ACCOUNT_NUMBER = 4000;
    public static final int RESULT_CODE_CHANGE_ACCOUNT_NUMBER = 4001;

    // 결제 비밀번호 생성 관련
    public static final int ACTION_REQ_CODE_PIN = 7300;
    public static final int ACTION_RESULT_CODE_PIN_SUCCESS = 7301;
    public static final int ACTION_RESULT_CODE_PIN_FAIL = 7302;

    // 카드 등록 관련
    public static final int ACTION_REQ_CODE_REGISTER_CARD = 8300;
    public static final int ACTION_RESULT_CODE_REGISTER_CARD = 8301;

    // Intent 관련
    // 선택한 주소
    public static final String INTENT_KEY_ADDRESS_INFO = "INTENT_KEY_ADDRESS_INFO";
    // 주소 좌표
    public static final String INTENT_KEY_ADDRESS_COORDINATES = "INTENT_KEY_ADDRESS_COORDINATES";
}
