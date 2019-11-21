package com.moaplanet.gosingadmin.main.submenu.point.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;

/**
 * 포인트 내역 뷰 모델
 */
public class PointHistoryViewModel extends BaseViewModel {

    // 조회 시작 날짜
    private MutableLiveData<String> mStartDate = new MutableLiveData<>();
    // 조회 종료 날짜
    private MutableLiveData<String> mEndDate = new MutableLiveData<>();
    // 조회 준비 완료 플래그 --> true : 준비 완료 | false : 준비 안됨
    private MutableLiveData<Boolean> mSearchDateComplete = new MutableLiveData<>();

    public void setStartDate(String startDate) {
        this.mStartDate.setValue(startDate);
    }

    public void setEndDate(String endDate) {
        this.mEndDate.setValue(endDate);
    }

    public void setSearchDateComplete(boolean searchDateComplete) {
        this.mSearchDateComplete.setValue(searchDateComplete);
    }

    public String getStartDate() {
        if (mStartDate.getValue() != null) {
            return mStartDate.getValue().replace(".", "");
        } else {
            return "";
        }
    }

    public String getEndDate() {
        if (mEndDate.getValue() != null) {
            return mEndDate.getValue().replace(".", "");
        } else {
            return "";
        }
    }

    public LiveData<Boolean> getSearchDateComplete() {
        return mSearchDateComplete;
    }
}
