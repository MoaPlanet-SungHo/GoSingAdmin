package com.moaplanet.gosingadmin.common.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseActivityViewModel extends ViewModel {

    // --- Field Start --- //

    // 로딩 유무 --> true : 로딩중 | false : 로딩 아님
    private MutableLiveData<Boolean> mIsLoading;

    // --- Field End --- //

    // --- Getter Start --- //

    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    // --- Getter End --- //

    // --- Setter Start --- //

    public void setmIsLoading(boolean isLoading) {
        this.mIsLoading.setValue(isLoading);
    }

    // --- Setter End --- //

    // --- Other Start --- //
    // --- Other End --- //

}
