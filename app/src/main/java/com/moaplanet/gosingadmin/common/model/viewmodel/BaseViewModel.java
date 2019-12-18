package com.moaplanet.gosingadmin.common.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 베이스 뷰 모델
 */
public abstract class BaseViewModel extends ViewModel {

    // 로딩 유무 --> true : 로딩중 | false : 로딩중 아님
    protected MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    // 세션 유무 --> true : 세션 존재 | false : 세션이 없음
    protected MutableLiveData<Boolean> mSession = new MutableLiveData<>();

    // API 통신 성공 유무 --> true : 성공 | false : 실패
    protected MutableLiveData<Boolean> mIsApiSuccess = new MutableLiveData<>();

    public void setIsLoading(Boolean isLoading) {
        this.mIsLoading.setValue(isLoading);
    }

    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public void setSession(Boolean session) {
        this.mSession.setValue(session);
    }

    public LiveData<Boolean> getSession() {
        return mSession;
    }

    public void setIsApiSuccess(Boolean isApiSuccess) {
        this.mIsApiSuccess.setValue(isApiSuccess);
    }

    public LiveData<Boolean> getIsApiSuccess() {
        return mIsApiSuccess;
    }
    // -- Getter End -- //
}
