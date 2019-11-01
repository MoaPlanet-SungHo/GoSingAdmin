package com.moaplanet.gosingadmin.intro.sign_up.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.sign_up.model.SignUpViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.req.ReqSignUpDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.submenu.store.StoreActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;

import retrofit2.Call;

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
        RetrofitService.getInstance().getGoSingApiService(getApplicationContext()).signUp(reqModel.getEmail(),
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
        public void onFinalResponse(Call<ResSignUpDto> call, ResSignUpDto resSignUpDto) {
            if (resSignUpDto.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {

                if (resSignUpDto.getDetailCode() == NetworkConstants.CODE_SIGN_UP_SUCCESS) {
                    successSignUp();
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "이미 존재하는 계정 입니다.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

            } else {
                Toast.makeText(SignUpActivity.this,
                        "회원가입을 실패했습니다.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onFinalFailure(Call<ResSignUpDto> call, boolean isSession, Throwable t) {
            Toast.makeText(SignUpActivity.this,
                    " 회원가입을 실패했습니다.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private void successSignUp() {
        LoginManager loginManager = new LoginManager();
        loginManager.setOnLoginListener(onLoginListener);
        loginManager.onLogin(
                reqModel.getEmail(),
                reqModel.getPw(),
                LoginManager.LoginType.LOGIN,
                this);

//        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
//        sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
//        sharedPreferencesManager.setLoginInfo(reqModel.getEmail(), reqModel.getPw());
//        Intent intent = new Intent(this, StoreActivity.class);
//        startActivity(intent);
//        finish();
    }

    private LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            Intent intent = new Intent(SignUpActivity.this, StoreActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            Toast.makeText(
                    SignUpActivity.this,
                    "회원가입을 실패했습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
