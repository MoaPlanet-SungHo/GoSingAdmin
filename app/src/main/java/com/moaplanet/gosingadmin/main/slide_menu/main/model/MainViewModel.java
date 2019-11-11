package com.moaplanet.gosingadmin.main.slide_menu.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.Map;

public class MainViewModel extends ViewModel {

    private final String POINT_TYPE_DEFAULT = "goeat_point";
    private final String POINT_TYPE_EXPECTED_ACTIVE = "actv_schdl_point";

    private MutableLiveData<String> pointGoSing = new MutableLiveData<>();
    private MutableLiveData<String> pointExpectedActive = new MutableLiveData<>();


    public void setPointMap(Map<String, Integer> pointMap) {
        pointGoSing.setValue(
                StringUtil.convertCommaPrice(pointMap.get(POINT_TYPE_DEFAULT)));
        pointExpectedActive.setValue(
                StringUtil.convertCommaPrice(pointMap.get(POINT_TYPE_EXPECTED_ACTIVE)));
    }

    public LiveData<String> getPointExpectedActive() {
        return pointExpectedActive;
    }

    public LiveData<String> getPointGoSing() {
        return pointGoSing;
    }
}
