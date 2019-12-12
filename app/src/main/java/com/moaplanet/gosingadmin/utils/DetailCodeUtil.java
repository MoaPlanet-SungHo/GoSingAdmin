package com.moaplanet.gosingadmin.utils;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiwun on 2019-12-12.
 */
public class DetailCodeUtil {

    @SuppressLint("UseSparseArrays")
    public static final Map<Integer, String> korErrMSg = new HashMap<>();

    public static int SUCCESS = 200; //완료
    public static int OVERLAP = 201; //이미 존재
    public static int NOTVALUE = 4000; //조회결과 없음
    public static int NOTUSER1 = 2012; //매장이 있지만 승인이 안난것
    public static int NOTUSER2 = 2013; //매장이 없는것
    public static int NOTUSER3 = 2014; //거절
    public static int NULLCHECK = 2099;

    public static class NoneReturn {
        public static int SUCCESS_NONE = 200; //성공
        public static int REGMBR = SUCCESS_NONE + 1;//정회원 등록되어있음
        public static int NONE_COUNT = SUCCESS_NONE + 2;//2회 초과
        public static int MAX_SEND_POINT = SUCCESS_NONE + 3;//max 금액 초과
        public static int NONE_POINT = SUCCESS_NONE + 4;//포인트 부족

    }

    public static class NOTSEND { //실페관련
        /**
         * 존재하지 않음
         */
        public static final int NOTVALUET = 4000; //존재하지 않음
        /**
         * 번호가 맞지않음
         */
        public static final int NOTVALUETValue = NOTVALUET + 1; //번호가 맞지않음
        /**
         * 코드번호를 전송해 주세요
         */
        public static int NOTVALUEAuth = NOTVALUET + 2; //코드번호를 전송해 주세요.
        /**
         * 인증정보가 맞지 않음
         */
        public static int NOTVALUE = NOTVALUET + 3; //인증정보가 맞지 않음
    }

    public static class NOTPOINT {
        public static final int NOTPOINT_NONE_WRITE = 20001; //존재하지 않음

    }


    public static int EMAILCERTIFICATION = 508; //이메일 인증 진행중

    public static int NOUPDATE = 300;
    public static int ERR_UN_KNOWN = 500000;

    public static int NOT_LOGIN = 500500; //로그인 필요
    public static int NOT_LOGIN_WRONG_PWD = 500501;
    public static int NOT_LOGIN_NOT_FOUND_USER = 500502;

    public static int FEE_INSUFFICIENT = 500602;
    public static int AMOUNT_INSUFFICIENT = 500603;

    public static int ERR_RDB = 500501;
    public static int ERR_NODE = 500502;

    public static int ERR_NO_ACCOUNT = 500599;
    public static int ERR_INVALID_PARAM = 500598;

    public static class SignChecker {
        public static int sign_invalid = 600599;
    }
/*
These code indicate an error in the local sever processing in the transaction. It is
possible that another server with a different configuration or load level could process
the transaction successfully. They have numerical values in the range -399 to -300. The
exact code for any given error is subject to change, so don't rely on it.

*/


    /*
    These codes indicate that the transaction was malformed and cannot succeed according to
    the XRP Ledger protocol. They have numerical values in the range -299 to -200. The exact
    code for any given error is subject to change. so don't rely on it.
    */
    static {
        korErrMSg.put(SUCCESS, "성공");
        korErrMSg.put(OVERLAP, "이미 존재");
        korErrMSg.put(NOTVALUE, "조회결과 없음");
        korErrMSg.put(NOTUSER1, "매장이 있지만 승인이 안난것");
        korErrMSg.put(NOTUSER2, "매장이 없는것");
        korErrMSg.put(NOTUSER3, "거절");
        korErrMSg.put(NULLCHECK, "?");
        korErrMSg.put(NoneReturn.NONE_COUNT, "2회초과");
        korErrMSg.put(NoneReturn.MAX_SEND_POINT, "max 금액 초과");
        korErrMSg.put(NoneReturn.NONE_POINT, "포인트 부족"
        );
        korErrMSg.put(NOTSEND.NOTVALUE, "존재하지 않음");
        korErrMSg.put(NOTSEND.NOTVALUETValue, "번호가 맞지 않음");
        korErrMSg.put(NOTSEND.NOTVALUEAuth, "코드번호를 전송해 주세요");
        korErrMSg.put(NOTSEND.NOTVALUE, "인증 번호가 맞지 않음");


        korErrMSg.put(NOTPOINT.NOTPOINT_NONE_WRITE, "존재하지 않음");

        korErrMSg.put(EMAILCERTIFICATION, "이메일 인증 진행 중");
        korErrMSg.put(NOUPDATE, "업데이트 실패");


        korErrMSg.put(ERR_UN_KNOWN, "에러");
        korErrMSg.put(NOT_LOGIN, "로그인 필요");
        korErrMSg.put(NOT_LOGIN_WRONG_PWD, "패스워드 실패");
        korErrMSg.put(NOT_LOGIN_NOT_FOUND_USER, "사용자 못 찾음");
        korErrMSg.put(FEE_INSUFFICIENT, "수수료 부족");
        korErrMSg.put(AMOUNT_INSUFFICIENT, "수량 부족");
        korErrMSg.put(ERR_RDB, "RDB에러 ");
        korErrMSg.put(ERR_NODE, "NODE 에러");
        korErrMSg.put(ERR_NO_ACCOUNT, "계정이 없습니다");
        korErrMSg.put(ERR_INVALID_PARAM, "파라미터 잘못 되었습니다");
        korErrMSg.put(SignChecker.sign_invalid, "싸인이 잘못 되었습니다");
    }

}
