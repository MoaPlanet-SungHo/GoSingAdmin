package com.moaplanet.gosingadmin.constants;

import java.util.HashSet;

public class GoSingConstants {

    // *** SharedPreferences Start *** //

    // 파일 이름
    public static String GOSING_ADMIN_FILE_NAME = "GOSING_ADMIN";

    // 인트로 타입
    // 인트로 타입 키값
    public static String INTRO_TYPE = "INTRO_TYPE";
    // 최초 실행
    public static int INTRO_TYPE_FIRST_START = 0;
    // 권한 허용
    public static int INTRO_TYPE_PERMISSION_CHECK_SUCCESS = 1;
    // 로그인 완료
    public static int INTRO_TYPE_AUTO_LOGIN = 2;
    // 로그아웃
    public static int INTRO_TYPE_LOGOUT = 3;

    // 사용자 계정 관련
    // 아이디
    public static String USER_ID = "userId";
    // 패스워드
    public static String USER_PW = "userPw";

    // 암호화 데이터 관련
    // 핀
    public final static String USER_PIN = "userPin";

    // *** SharedPreferences End *** //

    // 세션
    public static HashSet<String> cookieHash;

    // 인드로에서 앱 버전 체크 관련 --> true : 체크 | false : 체크 안함
    public static final String BUNDLE_KEY_APP_VERSION_CHECK = "BUNDLE_KEY_APP_VERSION_CHECK";
    public static final boolean BUNDLE_VALUE_APP_VERSION_CHECK = true;
    public static final boolean BUNDLE_VALUE_APP_VERSION_NOT_CHECK = false;

    // 웹뷰 보여주기 관련
    // 타이틀
    public static final String BUNDLE_KEY_WEB_VIEW_TITLE = "BUNDLE_KEY_WEB_VIEW_TITLE";
    // url
    public static final String BUNDLE_KEY_WEB_VIEW_URL = "BUNDLE_KEY_WEB_VIEW_URL";

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

    // 리뷰 관련
    // 리뷰 리스트 불러올떄 최대 개수
    public static final int REVIEW_LIST_LIMIT = 50;
    // 리뷰 타입 키값
    public static final String BUNDLE_KEY_REVIEW_TYPE = "BUNDLE_KEY_REVIEW_TYPE";
    // 전체 리뷰
    public static final int BUNDLE_VALUE_REVIEW_LIST_ALL = 1;
    // 덧글 없는 리뷰만
    public static final int BUNDLE_VALUE_REVIEW_LIST_NOT_REPLY = 2;
}
