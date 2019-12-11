package com.moaplanet.gosingadmin.intro.sign_up.model.req;

import com.google.gson.annotations.SerializedName;

public class ReqSignUpDto {
    @SerializedName("user_email")
    private String email;

    @SerializedName("pwd")
    private String pw;

    @SerializedName("is_agree_event_noti")
    private String agreeEventNoti;

    @SerializedName("sales_code")
    private String salesCode;

    @SerializedName("device_type")
    private int deviceType = 1;

    @SerializedName("signType")
    private int signType = 0;

    private String ci;

    private String userName;

    private String userAge;

    private String phoneNumber;

    private String userGender;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public void setAgreeEventNoti(String eventType) {
        this.agreeEventNoti = eventType;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getEventType() {
        return agreeEventNoti;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getSignType() {
        return signType;
    }

    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public String getCi() {
        return ci;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserGender() {
        return userGender;
    }
}
