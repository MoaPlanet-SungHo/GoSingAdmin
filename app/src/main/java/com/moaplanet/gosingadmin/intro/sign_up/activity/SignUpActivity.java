package com.moaplanet.gosingadmin.intro.sign_up.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.activity.CreatePinActivity;
import com.moaplanet.gosingadmin.common.manager.AuthManager;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.common.model.viewmodel.CreatePinViewModel;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.CreateAccountViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.req.ReqSignUpDto;
import com.moaplanet.gosingadmin.intro.sign_up.model.res.ResSignUpDto;
import com.moaplanet.gosingadmin.main.submenu.store.activity.RegisterStoreActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import retrofit2.Call;

/**
 * 회원가입 액티비티
 */
public class SignUpActivity extends BaseActivity {

    // 뷰 모델
    private SignUpViewModel signUpViewModel;
    private CreatePinViewModel pinViewModel;
    private ReqSignUpDto reqModel;

    @Override
    public void initActivity() {
        super.initActivity();
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        pinViewModel = ViewModelProviders.of(this).get(CreatePinViewModel.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.ACTION_REQ_CODE_PIN) {

            if (resultCode == GoSingConstants.ACTION_RESULT_CODE_PIN_SUCCESS) {
                onLoadingStart();
                successSignUp();
            } else {
                Toast.makeText(SignUpActivity.this,
                        getString(R.string.common_toast_payment_password_not_create),
                        Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }

    /**
     * 회원가입
     */
    private void onSignUp() {
        onLoadingStart();
        RetrofitService.getInstance().getGoSingApiService().onServerSignUp(reqModel.getEmail(),
                reqModel.getPw(),
                reqModel.getSalesCode(),
                reqModel.getEventType(),
                reqModel.getDeviceType(),
                reqModel.getSignType())
                .enqueue(moaAuthCallback);
    }

    /**
     * 회원가입 콜백
     */
    private MoaAuthCallback<ResSignUpDto> moaAuthCallback = new MoaAuthCallback<ResSignUpDto>(
            RetrofitService.getInstance().getMoaAuthConfig(),
            RetrofitService.getInstance().getSessionChecker()
    ) {
        @Override
        public void onFinalResponse(Call<ResSignUpDto> call, ResSignUpDto resSignUpDto) {
            if (resSignUpDto.getDetailCode() == NetworkConstants.CODE_SIGN_UP_SUCCESS) {
                createPin();
            } else {
                onLoadingStop();
                Toast.makeText(SignUpActivity.this,
                        "이미 존재하는 계정 입니다.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onFinalFailure(Call<ResSignUpDto> call, boolean isSession, Throwable t) {
            onLoadingStop();
            Toast.makeText(SignUpActivity.this,
                    " 회원가입을 실패했습니다.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private void createPin() {

        AuthManager authManager = new AuthManager();
        authManager.setOnAuthCallback(new AuthManager.onAuthCallback() {
            @Override
            public void onSuccess() {
                Logger.d("키 생성 성공");
                successSignUp();
            }

            @Override
            public void onFail() {
                Logger.d("키 생성 실패");
                onLoadingStop();
                Toast.makeText(SignUpActivity.this,
                        "결제 비밀번호를 다시 생성해 주세요",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, CreatePinActivity.class);
                startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_PIN);

            }
        });
        authManager.onInitPin(this,
                authManager.KEY_ALIAS_MOBILE_PIN,
                pinViewModel.getPinPw().getValue());

    }

    /**
     * 로그인 실행
     */
    private void successSignUp() {
        LoginManager loginManager = new LoginManager();
        loginManager.setOnLoginListener(onLoginListener);
        loginManager.onLogin(
                reqModel.getEmail(),
                reqModel.getPw(),
                LoginManager.LoginType.LOGIN,
                this);
    }

    /**
     * 로그인 콜백
     */
    private LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            onLoadingStop();
            Intent intent = new Intent(SignUpActivity.this, RegisterStoreActivity.class);
            startActivity(intent);
            finishAffinity();
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            onLoadingStop();
            Toast.makeText(
                    SignUpActivity.this,
                    "로그인을 실패했습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
