package com.moaplanet.gosingadmin.main.slide_menu.information;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class MyInfoDTO extends CommonResDto {

    @SerializedName("returnMap")
    private MyInfoModel myInfoModel;

    public class MyInfoModel {

        // 유저 이메일
        @SerializedName("user_email")
        private String userEmail;

        // 핸드폰 번호
        @SerializedName("phone_number")
        private String phoneNumber;

        // 비밀번호
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

    public MyInfoModel getMyInfoModel() {
        return myInfoModel;
    }
}
