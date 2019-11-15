package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.BaseViewModel;

/**
 * 출금 계좌 관련 뷰 모델
 */
public class DepositAccountViewModel extends BaseViewModel {

    // 계좌 번호
    private MutableLiveData<String> mAccountNumber = new MutableLiveData<>();
    // 예금주 이름
    private MutableLiveData<String> mAccountName = new MutableLiveData<>();
    // 은행 점보
    private MutableLiveData<ResBankInfoDto.BankInformationDto> mBankInfo = new MutableLiveData<>();

    public LiveData<ResBankInfoDto.BankInformationDto> getmBankInfo() {
        return mBankInfo;
    }

    public LiveData<String> getmAccountName() {
        return mAccountName;
    }

    public LiveData<String> getmAccountNumber() {
        return mAccountNumber;
    }

    public void setmAccountName(String mAccountName) {
        this.mAccountName.setValue(mAccountName);
    }

    public void setmAccountNumber(String mAccountNumber) {
        this.mAccountNumber.setValue(mAccountNumber);
    }

    public void setmBankInfo(ResBankInfoDto.BankInformationDto mBankInfo) {
        this.mBankInfo.setValue(mBankInfo);
    }
}
