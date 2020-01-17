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
    @Deprecated
    protected MutableLiveData<Boolean> session = new MutableLiveData<>();

    // 세션 상태값 -> true 만료, false 유지되어있음
    protected MutableLiveData<Boolean> expireSession = new MutableLiveData<>();

    // API 통신 성공 유무 --> true : 성공 | false : 실패
    @Deprecated
    protected MutableLiveData<Boolean> isApiSuccess = new MutableLiveData<>();

    // 통신 상태값 -> true 통신 실패, false 통신 성공
    protected MutableLiveData<Boolean> failNetwork = new MutableLiveData<>();

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Deprecated
    public void setSession(Boolean session) {
        this.session.setValue(session);
    }

    @Deprecated
    public LiveData<Boolean> getSession() {
        return session;
    }

    public LiveData<Boolean> getExpireSession() {
        return expireSession;
    }

    @Deprecated
    public void setIsApiSuccess(Boolean isApiSuccess) {
        this.isApiSuccess.setValue(isApiSuccess);
    }

    @Deprecated
    public LiveData<Boolean> getIsApiSuccess() {
        return isApiSuccess;
    }

    public LiveData<Boolean> getFailNetwork() {
        return failNetwork;
    }
}
