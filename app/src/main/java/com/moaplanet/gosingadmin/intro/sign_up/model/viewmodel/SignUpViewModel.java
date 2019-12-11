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
    // CI 값
    private MutableLiveData<String> mCi = new MutableLiveData<>();
    // 이름
    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    // 생년월일
    private MutableLiveData<String> mUserAge = new MutableLiveData<>();
    // 휴대폰번호
    private MutableLiveData<String> mPhoneNumber = new MutableLiveData<>();
    // 성별
    private MutableLiveData<String> mUserGender = new MutableLiveData<>();

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

    public void setCi(String ci) {
        this.mCi.postValue(ci);
    }

    public void setUserName(String userName) {
        this.mUserName.postValue(userName);
    }

    public void setUserAge(String userAge) {
        this.mUserAge.postValue(userAge);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber.postValue(phoneNumber);
    }

    public void setUserGender(String userGender) {
        this.mUserGender.postValue(userGender);
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

    public LiveData<String> getCi() {
        return mCi;
    }

    public LiveData<String> getUserName() {
        return mUserName;
    }
    public LiveData<String> getUserAge() {
        return mUserAge;
    }
    public LiveData<String> getPhoneNumber() {
        return mPhoneNumber;
    }
    public LiveData<String> getUserGender() {
        return mUserGender;
    }

}
