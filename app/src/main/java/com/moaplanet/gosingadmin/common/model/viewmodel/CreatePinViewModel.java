package com.moaplanet.gosingadmin.common.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * 결제 비밀번호 관련 뷰 모델
 */
public class CreatePinViewModel extends BaseViewModel {

    // 핀 패스워드
    private MutableLiveData<String> mPinPw = new MutableLiveData<>();

    public void setPinPw(String pinPw) {
        this.mPinPw.setValue(pinPw);
    }

    public LiveData<String> getPinPw() {
        return mPinPw;
    }

}
