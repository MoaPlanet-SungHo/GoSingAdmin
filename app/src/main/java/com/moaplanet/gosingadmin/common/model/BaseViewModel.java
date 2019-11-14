package com.moaplanet.gosingadmin.common.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 베이스 뷰 모델
 */
public class BaseViewModel extends ViewModel {

    // -- Field Start -- //
    // 로딩 유무 --> true : 로딩중 | false : 로딩중 아님
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    // 세션 유무 --> true : 세션 존재 | false : 세션이 없음
    private MutableLiveData<Boolean> mExistSession = new MutableLiveData<>();

    // API 통신 성공 유무 --> true : 성공 | false : 실패
    private MutableLiveData<Boolean> mIsApiSuccess = new MutableLiveData<>();
    // -- Field End -- //

    // -- Setter Start -- //
    public void setIsLoading(Boolean isLoading) {
        this.mIsLoading.setValue(isLoading);
    }

    public void setExistSession(Boolean existSession) {
        this.mExistSession.setValue(existSession);
    }

    public void setIsApiSuccess(Boolean isApiSuccess) {
        this.mIsApiSuccess.setValue(isApiSuccess);
    }
    // -- Setter End -- //

    // -- Getter Start -- //
    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public LiveData<Boolean> getExistSession() {
        return mExistSession;
    }

    public LiveData<Boolean> getIsApiSuccess() {
        return mIsApiSuccess;
    }
    // -- Getter End -- //
}
