package com.moaplanet.gosingadmin.main.slide_menu.information;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class PhoneNumberChangeDTO extends CommonResDto {

    @SerializedName("phone_number")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
