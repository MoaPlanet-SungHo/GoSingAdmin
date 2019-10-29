package com.moaplanet.gosingadmin.intro.sign_up.model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<String> checkEventPush = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> pw = new MutableLiveData<>();
    private MutableLiveData<String> salesCode = new MutableLiveData<>();

    public void setSalesCode(String salesCode) {
        this.salesCode.setValue(salesCode);
    }

    public void setCheckEventPush(String type) {
        checkEventPush.setValue(type);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPw(String pw) {
        this.pw.setValue(pw);
    }

    public LiveData<String> getCheckEventPush() {
        return checkEventPush;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPw() {
        return pw;
    }

    public LiveData<String> getSalesCode() {
        return salesCode;
    }
}
