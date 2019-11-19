package com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;

/**
 * 회원가입 계정 정보 입력 뷰모델
 */
public class CreateAccountViewModel extends BaseViewModel {

    // 이메일 입력한 이메일
    private MutableLiveData<String> mEmail = new MutableLiveData<>();

    // 이메일 입력한 이메일
    private MutableLiveData<String> mPw = new MutableLiveData<>();

    // 이메일 에러 메시지 타입 --> View.GONE : 숨김 | View.VISIBLE : 표시
    private MutableLiveData<Integer> mEmailErrMsg = new MutableLiveData<>();

    // 비밀번호 에러 메시지 타입 --> View.GONE : 숨김 | View.VISIBLE : 표시
    private MutableLiveData<Integer> mPwErrMsg = new MutableLiveData<>();

    // 비밀번호 확인 에러 메시지 타입 --> View.GONE : 숨김 | View.VISIBLE : 표시
    private MutableLiveData<Integer> mPwCheckErrMsg = new MutableLiveData<>();

    // 계정 정보들 (이메일, 비밀번호, 비밀번호 확인) 이 제대로 입력됬는지 유무
    // true : 전부 정상 입력 | false 정상 입력 아님
    private MutableLiveData<Boolean> mAccountComplete = new MutableLiveData<>();

    public void setEmail(String email) {
        this.mEmail.setValue(email);

        // 이메일 타입인지 체크후 에러 메시지 표시할건지 안할건지 표시
        if (StringUtil.isEmail(email)) {
            mEmailErrMsg.setValue(View.GONE);
        } else {
            mEmailErrMsg.setValue(View.VISIBLE);
        }

    }

    public LiveData<String> getEmail() {
        return mEmail;
    }

    public LiveData<String> getPw() {
        return mPw;
    }

    public LiveData<Integer> getEmailErrMsg() {
        return mEmailErrMsg;
    }

    public LiveData<Integer> getPwErrMsg() {
        return mPwErrMsg;
    }

    public LiveData<Integer> getPwCheckErrMsg() {
        return mPwCheckErrMsg;
    }

    public LiveData<Boolean> getAccountComplete() {
        return mAccountComplete;
    }

    /**
     * 회원가입 계정 체크
     *
     * @param pw      비밀번호
     * @param checkPw 비밀번호 확인
     */
    public void onCheckAccount(String pw, String checkPw) {

        // 이메일 체크
        String email = mEmail.getValue();
        Integer emailErrMsg = mEmailErrMsg.getValue();
        if (email == null || email.equals("") ||
                emailErrMsg == null || emailErrMsg == View.VISIBLE) {

            mEmailErrMsg.setValue(View.VISIBLE);
            return;
        }

        // 패스워드 체크
        if (!pw.equals("") && StringUtil.isPw(pw) && pw.length() >= 8) {
            mPwErrMsg.setValue(View.GONE);
        } else {
            mPwErrMsg.setValue(View.VISIBLE);
            return;
        }

        // 패스워드 확인 체크
        if (pw.equals(checkPw)) {
            mPwCheckErrMsg.setValue(View.GONE);
        } else {
            mPwCheckErrMsg.setValue(View.VISIBLE);
            return;
        }

        mPw.setValue(pw);
        mAccountComplete.setValue(true);

    }
}
