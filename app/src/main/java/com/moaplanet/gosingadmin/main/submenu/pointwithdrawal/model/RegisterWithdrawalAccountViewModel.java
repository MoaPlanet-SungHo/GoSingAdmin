package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;

/**
 * 출금 계좌 등록 뷰 모델
 */
public class RegisterWithdrawalAccountViewModel extends BaseViewModel {

    // 결제 비밀번호 입력
    private MutableLiveData<Boolean> mPinSuccess = new MutableLiveData<>();

    public void setPinSuccess(boolean mPinSuccess) {
        this.mPinSuccess.setValue(mPinSuccess);
    }

    public LiveData<Boolean> getmPinSuccess() {
        return mPinSuccess;
    }
}
