package com.moaplanet.gosingadmin.intro.login.moel.req;

public class ReqLoginDto {
    private String email;

    private String pw;

    private int signType = 0;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPw() {
        return pw;
    }

    public String getEmail() {
        return email;
    }

    public int getSignType() {
        return signType;
    }
}
