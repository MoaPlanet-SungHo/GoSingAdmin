package com.moaplanet.gosingadmin.main.slide_menu.information.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.main.slide_menu.information.model.dto.res.ResInformationDto;

/**
 * Created by jiwun on 2019-11-11.
 */
public class InformationViewModel extends ViewModel {

    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    private MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    private MutableLiveData<String> pwd = new MutableLiveData<>();

    public void setUserInfoDto(ResInformationDto.InformationDto infoDto) {
        userEmail.setValue(infoDto.getUserEmail());
        phoneNumber.setValue(infoDto.getPhoneNumber());
        pwd.setValue(infoDto.getPwd());
    }

    public LiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public LiveData<String> getPwd() {
        return pwd;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }
}
