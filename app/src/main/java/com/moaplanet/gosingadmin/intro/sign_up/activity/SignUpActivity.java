package com.moaplanet.gosingadmin.intro.sign_up.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.intro.sign_up.model.SignUpViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.req.ReqSignUpDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitListener;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

public class SignUpActivity extends BaseActivity {

    private SignUpViewModel signUpViewModel;
    private ReqSignUpDto reqModel;

    @Override
    public int layoutRes() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        reqModelInit();
    }

    private void reqModelInit() {
        reqModel = new ReqSignUpDto();
        signUpViewModel.getCheckEventPush().observe(this, reqModel::setEventType);

        signUpViewModel.getEmail().observe(this, reqModel::setEmail);

        signUpViewModel.getPw().observe(this, reqModel::setPw);

        signUpViewModel.getSalesCode().observe(this, salesCode -> {
            reqModel.setSalesCode(salesCode);
            onSignUp();
        });
    }

    private HttpLoggingInterceptor getLoggingInterface() {
        return new HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
        );
    }

    private void onSignUp() {
        new RetrofitService()
                .getGoSingApiService()
                .signUp("qqq@qqq.com", "222222", "", "Y", 1, 0)
                .enqueue(new RetrofitCallBack<>(retrofitListener));
    }

    private RetrofitListener<ResSignUpDto> retrofitListener = new RetrofitListener<ResSignUpDto>() {
        @Override
        public void onSuccess(ResSignUpDto responseData) {
            Logger.d("성공");
        }

        @Override
        public void onReissuedAccessToken() {
            Logger.d("성공");
        }

        @Override
        public void onFail(String msg) {
            Logger.d("실패 : " + msg);

        }

        @Override
        public void onNetworkError(Throwable t) {
            Logger.d("실패");
        }
    };

}
