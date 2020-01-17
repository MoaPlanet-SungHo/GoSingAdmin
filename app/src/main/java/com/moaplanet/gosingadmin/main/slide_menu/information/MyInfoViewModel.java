package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.os.Build;
import android.telephony.PhoneNumberUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.Locale;

import retrofit2.Response;

/**
 * 내정보 뷰 모델
 */
public class MyInfoViewModel extends BaseViewModel {

    // 이메일
    private MutableLiveData<String> email = new MutableLiveData<>();
    // 비밀번호
    private MutableLiveData<String> pw = new MutableLiveData<>();
    // 핸드폰 번호
    private MutableLiveData<String> phoneNumber = new MutableLiveData<>();

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPw() {
        return pw;
    }

    public LiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void postMyInfo() {
        RetrofitService.getInstance().getGoSingApi().postMyInfo()
                .enqueue(new RetrofitCallBack<MyInfoDTO>() {
                    @Override
                    public void onSuccess(MyInfoDTO response) {
                        Logger.i("내정보 값 : " + new Gson().toJson(response));

                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                            MyInfoDTO.MyInfoModel model = response.getMyInfoModel();

                            email.setValue(model.getUserEmail());
                            pw.setValue(model.getPwd());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                phoneNumber.setValue(
                                        PhoneNumberUtils.formatNumber(model.getPhoneNumber(), Locale.getDefault().getCountry()));
                            } else {
                                phoneNumber.setValue(
                                        PhoneNumberUtils.formatNumber(model.getPhoneNumber()));
                            }

                        } else {
                            failNetwork.setValue(true);
                        }

                    }

                    @Override
                    public void onFail(Response<MyInfoDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<MyInfoDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });
    }

}
