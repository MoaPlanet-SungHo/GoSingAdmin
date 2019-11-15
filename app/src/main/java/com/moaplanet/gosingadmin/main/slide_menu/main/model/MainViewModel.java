package com.moaplanet.gosingadmin.main.slide_menu.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> pointGoSing = new MutableLiveData<>();
    private MutableLiveData<String> pointExpectedActive = new MutableLiveData<>();

    public void setPoint(ResPointDto pointDto) {
        pointGoSing.setValue(
                StringUtil.convertCommaPrice(pointDto.getPoint()));
        pointExpectedActive.setValue(
                StringUtil.convertCommaPrice(pointDto.getPointExpectedActive()));
    }

    public LiveData<String> getPointExpectedActive() {
        return pointExpectedActive;
    }

    public LiveData<String> getPointGoSing() {
        return pointGoSing;
    }
}
