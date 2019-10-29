package com.moaplanet.gosingadmin.intro.sign_up.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.intro.sign_up.model.SignUpViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.req.ReqSignUpDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitListener;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Response;

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
        signUpViewModel.getCheckEventPush().observe(this, reqModel::setAgreeEventNoti);

        signUpViewModel.getEmail().observe(this, reqModel::setEmail);

        signUpViewModel.getPw().observe(this, reqModel::setPw);

        signUpViewModel.getSalesCode().observe(this, salesCode -> {
            reqModel.setSalesCode(salesCode);
            onSignUp();
        });
    }

    private void onSignUp() {
        RetrofitService.getInstance().getGoSingApiService().signUp(reqModel.getEmail(),
                reqModel.getPw(),
                reqModel.getSalesCode(),
                reqModel.getEventType(),
                reqModel.getDeviceType(),
                reqModel.getSignType())
                .enqueue(moaAuthCallback);
    }

    private MoaAuthCallback<ResSignUpDto> moaAuthCallback = new MoaAuthCallback<ResSignUpDto>(
            RetrofitService.getInstance().getMoaAuthConfig(),
            RetrofitService.getInstance().getSessionChecker()
    ) {
        @Override
        public void onFinalResponse(Call<ResSignUpDto> call, ResSignUpDto response) {

        }

        @Override
        public void onFinalFailure(Call<ResSignUpDto> call, boolean isSession, Throwable t) {

        }
    };

}
