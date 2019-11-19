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
import com.moaplanet.gosingadmin.common.activity.CreatePinActivity;
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
        // StatusBar 삭제
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.ACTION_REQ_CODE_PIN) {
            if (resultCode == GoSingConstants.ACTION_RESULT_CODE_PIN_SUCCESS) {
                onLogin();
            } else {
                Toast.makeText(this,
                        "결제 비밀번호 생성에 실패했습니다",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
    }

    private void checkIntroType() {
        int introType = SharedPreferencesManager.getInstance().getType();
        if (introType == GoSingConstants.INTRO_TYPE_FIRST_START
                || introType == GoSingConstants.INTRO_TYPE_ERROR) {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(
                    () -> moveActivity(GoSingAdminConfirmPermissionActivity.class), 1800);
        } else if (introType == GoSingConstants.INTRO_TYPE_AUTO_LOGIN) {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(
                    this::onLogin, 1800);
        } else if (introType == GoSingConstants.INTRO_TYPE_PERMISSION_CHECK_SUCCESS) {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(
                    () -> viewLoginOrSignUp.setVisibility(View.VISIBLE), 1800);
        }
    }

    private void onLogin() {

        if (SharedPreferencesManager.getInstance().getPin().equals("")) {
            Toast.makeText(this,
                    "결제 비밀번호를 생성해 주세요",
                    Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(this, CreatePinActivity.class);
            startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_PIN);
        } else {
            LoginManager loginManager = new LoginManager();
            loginManager.setOnLoginListener(onLoginListener);
            loginManager.onLogin(this, LoginManager.LoginType.AUTO_LOGIN);
        }
    }

    private LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            if (detailCode == NetworkConstants.LOGIN_CODE_SUCCESS) {
                moveActivity(MainActivity.class);
            } else if (detailCode == NetworkConstants.LOGIN_CODE_ACCOUNT_INACTIVE) {
                moveActivity(WaitingApprovalActivity.class);
            } else if (detailCode == NetworkConstants.LOGIN_CODE_EMPTY_STORE) {
                moveActivity(RegisterStoreActivity.class);
            }
            finish();
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            viewLoginOrSignUp.setVisibility(View.VISIBLE);
            Toast.makeText(
                    IntroActivity.this,
                    getString(R.string.activity_intro_auto_login_fail),
                    Toast.LENGTH_SHORT).show();
        }
    };

}
