package com.moaplanet.gosingadmin.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.intro.login.moel.req.ReqLoginDto;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.intro.sign_up.activity.SignUpActivity;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.main.submenu.store.StoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.WaitingApprovalActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;

import retrofit2.Call;

public class IntroActivity extends BaseActivity {

    private Button btnSignUp, btnLogin;
    private LinearLayout llLogin;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public int layoutRes() {
        return R.layout.activity_intro;
    }

    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnSignUp = findViewById(R.id.btn_intro_sign_up);
        btnLogin = findViewById(R.id.btn_intro_login);
        llLogin = findViewById(R.id.ll_intro_user_group);
        llLogin.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        btnSignUp.setOnClickListener(view -> moveActivity(SignUpActivity.class));
        btnLogin.setOnClickListener(view -> moveActivity(LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIntroType();
    }

    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
    }

    private void checkIntroType() {
        sharedPreferencesManager = new SharedPreferencesManager(this);
        int introType = sharedPreferencesManager.getType();
        if (introType == GoSingConstants.TYPE_FIRST_START) {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(
                    () -> moveActivity(GoSingAdminConfirmPermissionActivity.class), 1800);
        } else if (introType == GoSingConstants.TYPE_AUTO_LOGIN) {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(
                    this::onLogin, 1800);
        } else {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(
                    () -> llLogin.setVisibility(View.VISIBLE), 1800);
        }
    }

    private void onLogin() {
        ReqLoginDto reqLoginDto = new ReqLoginDto();
//        reqLoginDto.setEmail(sharedPreferencesManager.getEmail());
//        reqLoginDto.setPw(sharedPreferencesManager.getPw());

        LoginManager loginManager = new LoginManager();
        loginManager.setOnLoginListener(onLoginListener);
        loginManager.onLogin(this, LoginManager.LoginType.AUTO_LOGIN);

//        RetrofitService.getInstance().getGoSingApiService(getApplicationContext())
//                .login(
//                        reqLoginDto.getEmail(),
//                        reqLoginDto.getPw(),
//                        reqLoginDto.getSignType())
//                .enqueue(loginCallback);
//        moveActivity(StoreActivity.class);
    }

//    private MoaAuthCallback<ResLoginDto> loginCallback = new MoaAuthCallback<ResLoginDto>(
//            RetrofitService.getInstance().getMoaAuthConfig(),
//            RetrofitService.getInstance().getSessionChecker()
//    ) {
//        @Override
//        public void onFinalResponse(Call<ResLoginDto> call, ResLoginDto resModel) {
//            if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
//                if (resModel.getDetailCode() == NetworkConstants.CODE_LOGIN_SUCCESS) {
//                    moveActivity(MainActivity.class);
//                } else if (resModel.getDetailCode() == NetworkConstants.CODE_ACCOUNT_INACTIVE) {
//                    moveActivity(StoreActivity.class);
//                } else {
//                    moveActivity(StoreActivity.class);
//                    Toast.makeText(
//                            IntroActivity.this,
//                            "자동로그인을 실패 헀습니다.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(
//                        IntroActivity.this,
//                        "자동로그인을 실패 헀습니다.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onFinalFailure(Call<ResLoginDto> call, boolean isSession, Throwable t) {
//            Toast.makeText(
//                    IntroActivity.this,
//                    "자동로그인을 실패 헀습니다.",
//                    Toast.LENGTH_SHORT).show();
//        }
//    };

    private LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            if (detailCode == NetworkConstants.CODE_LOGIN_SUCCESS) {
                moveActivity(MainActivity.class);
            } else if (detailCode == NetworkConstants.CODE_ACCOUNT_INACTIVE) {
                moveActivity(WaitingApprovalActivity.class);
            } else if (detailCode == NetworkConstants.CODE_ACCOUNT_DISINACTIVE) {
                moveActivity(StoreActivity.class);
            }
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            llLogin.setVisibility(View.VISIBLE);
            Toast.makeText(
                    IntroActivity.this,
                    "자동로그인을 실패 헀습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
