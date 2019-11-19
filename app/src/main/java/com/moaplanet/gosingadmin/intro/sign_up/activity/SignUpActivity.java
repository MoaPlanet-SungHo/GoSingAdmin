package com.moaplanet.gosingadmin.intro.sign_up.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.req.ReqSignUpDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.submenu.store.activity.RegisterStoreActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * 회원가입 액티비티
 */
public class SignUpActivity extends BaseActivity {

    // 뷰 모델
    private SignUpViewModel signUpViewModel;
    private ReqSignUpDto reqModel;

    @Override
    public void initActivity() {
        super.initActivity();
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initView() {
        // 로딩바 초기화
        View loadingBar = findViewById(R.id.pb_activity_sign_up_loading);
        loadingBar.setVisibility(View.GONE);
        setLoadingBar(loadingBar);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initObserve() {
        super.initObserve();

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
        RetrofitService.getInstance().getGoSingApiService().onServerSignUp(reqModel.getEmail(),
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
            if (resSignUpDto.getDetailCode() == NetworkConstants.CODE_SIGN_UP_SUCCESS) {
                successSignUp();
            } else {
                Toast.makeText(SignUpActivity.this,
                        "이미 존재하는 계정 입니다.",
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
            Intent intent = new Intent(SignUpActivity.this, RegisterStoreActivity.class);
            startActivity(intent);
            finishAffinity();
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
