package com.moaplanet.gosingadmin.intro.sign_up.model.req;

import com.google.gson.annotations.SerializedName;

public class ReqSignUpDto {
    @SerializedName("user_email")
    private String email;

    @SerializedName("pwd")
    private String pw;

    @SerializedName("is_agree_event_noti")
    private String eventType;

    @SerializedName("sales_code")
    private String salesCode;

    @SerializedName("ci")
    private String ci;

    @SerializedName("public_key")
    private String publickKey;

    @SerializedName("device_type")
    private int deviceType = 1;

    @SerializedName("signType")
    private int signType = 0;

    @SerializedName("signHash")
    private String signHash = null;

    @SerializedName("mobileKey")
    private String mobileKey = null;

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setPublickKey(String publickKey) {
        this.publickKey = publickKey;
    }
}
