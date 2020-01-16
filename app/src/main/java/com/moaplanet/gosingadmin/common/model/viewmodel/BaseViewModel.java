package com.moaplanet.gosingadmin.common.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 베이스 뷰 모델
 */
public abstract class BaseViewModel extends ViewModel {

    // 로딩 유무 --> true : 로딩중 | false : 로딩중 아님
    protected MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    // 세션 유무 --> true : 세션 존재 | false : 세션이 없음
    protected MutableLiveData<Boolean> session = new MutableLiveData<>();

    // API 통신 성공 유무 --> true : 성공 | false : 실패
    protected MutableLiveData<Boolean> isApiSuccess = new MutableLiveData<>();

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setSession(Boolean session) {
        this.session.setValue(session);
    }

    public LiveData<Boolean> getSession() {
        return session;
    }

    public void setIsApiSuccess(Boolean isApiSuccess) {
        this.isApiSuccess.setValue(isApiSuccess);
    }

    public LiveData<Boolean> getIsApiSuccess() {
        return isApiSuccess;
    }
    // -- Getter End -- //
}
