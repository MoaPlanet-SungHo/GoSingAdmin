package com.moaplanet.gosingadmin.main.slide_menu.information.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResInformationDto extends CommonResDto {

    @SerializedName("returnMap")
    private InformationDto informationDto;

    public class InformationDto {
        @SerializedName("user_email")
        private String userEmail;

        @SerializedName("phone_number")
        private String phoneNumber;

        @SerializedName("pwd")
        private String pwd;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getPwd() {
            return pwd;
        }

        public String getUserEmail() {
            return userEmail;
        }
    }

    public InformationDto getInformationDto() {
        return informationDto;
    }
}
