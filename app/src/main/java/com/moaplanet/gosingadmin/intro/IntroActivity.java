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
import com.moaplanet.gosingadmin.intro.sign_up.activity.SignUpActivity;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.RegisterStoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.WaitingApprovalActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;

public class IntroActivity extends BaseActivity {

    private Button btnSignUp, btnLogin;
    private LinearLayout viewLoginOrSignUp;

    @Override
    public int layoutRes() {
        return R.layout.activity_intro;
    }

    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnSignUp = findViewById(R.id.btn_activity_intro_sign_up);
        btnLogin = findViewById(R.id.btn_activity_intro_login);
        viewLoginOrSignUp = findViewById(R.id.ll_activity_intro_user_group);
        viewLoginOrSignUp.setVisibility(View.GONE);
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
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
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
                    () -> viewLoginOrSignUp.setVisibility(View.VISIBLE), 1800);
        }
    }

    private void onLogin() {
        LoginManager loginManager = new LoginManager();
        loginManager.setOnLoginListener(onLoginListener);
        loginManager.onLogin(this, LoginManager.LoginType.AUTO_LOGIN);
    }

    private LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            if (detailCode == NetworkConstants.CODE_LOGIN_SUCCESS) {
                moveActivity(MainActivity.class);
                finish();
            } else if (detailCode == NetworkConstants.CODE_ACCOUNT_INACTIVE) {
                moveActivity(WaitingApprovalActivity.class);
                finish();
            } else if (detailCode == NetworkConstants.CODE_ACCOUNT_DISINACTIVE) {
                moveActivity(RegisterStoreActivity.class);
                finish();
            }
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            viewLoginOrSignUp.setVisibility(View.VISIBLE);
            Toast.makeText(
                    IntroActivity.this,
                    "자동로그인을 실패 헀습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
