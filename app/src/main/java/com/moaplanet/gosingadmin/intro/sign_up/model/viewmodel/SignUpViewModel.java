package com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 회원가입화면에서 공통으로 사용하는 뷰 모델
 */
public class SignUpViewModel extends ViewModel {

    // --- Field Start --- //

    // 약관동의 화면의 혜택 알림 타입 --> Y : 동의 | N : 비동의
    private MutableLiveData<String> mCheckEventPush = new MutableLiveData<>();
    // 사용자가 입력한 이메일
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    // 사용자가 입력한 페스워드
    private MutableLiveData<String> mPw = new MutableLiveData<>();
    // 영업자 코드
    private MutableLiveData<String> mSalesCode = new MutableLiveData<>();
    // 핀 패스워드
    private MutableLiveData<String> mPinPw = new MutableLiveData<>();

    // --- Field End --- //

    // --- Setter Start --- //

    public void setCheckEventPush(boolean checkType) {
        String eventType;
        if (checkType) {
            eventType = "Y";
        } else {
            eventType = "N";
        }
        mCheckEventPush.setValue(eventType);
    }

    public void setEmail(String email) {
        this.mEmail.setValue(email);
    }

    public void setPw(String pw) {
        this.mPw.setValue(pw);
    }

    public void setSalesCode(String salesCode) {
        this.mSalesCode.setValue(salesCode);
    }

    public void setPinPw(String pinPw) {
        this.mPinPw.setValue(pinPw);
    }

    // --- Setter End --- //

    // --- Getter Start --- //
    // --- Getter End --- //

    public LiveData<String> getCheckEventPush() {
        return mCheckEventPush;
    }

    public LiveData<String> getEmail() {
        return mEmail;
    }

    public LiveData<String> getPw() {
        return mPw;
    }

    public LiveData<String> getSalesCode() {
        return mSalesCode;
    }

    public LiveData<String> getPinPw() {
        return mPinPw;
    }
}
