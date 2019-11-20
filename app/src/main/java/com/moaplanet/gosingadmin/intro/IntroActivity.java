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

import com.jakewharton.rxbinding.view.RxView;
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

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class IntroActivity extends BaseActivity {

    // 회원가입 및 로그인 그룹
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

        viewLoginOrSignUp = findViewById(R.id.ll_activity_intro_user_group);
        viewLoginOrSignUp.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {

        // 회원가입 클릭
        Button btnSignUp = findViewById(R.id.btn_activity_intro_sign_up);
        RxView.clicks(btnSignUp)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(SignUpActivity.class));

        // 로그인 클릭
        Button btnLogin = findViewById(R.id.btn_activity_intro_login);
        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(LoginActivity.class));
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
                        getString(R.string.common_toast_payment_password_not_create),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    /**
     * 액티비티 이동
     */
    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
    }

    /**
     * 인트로 타입에 따라 화면 이동
     */
    private void checkIntroType() {

        Handler delayHandler = new Handler();
        delayHandler.postDelayed(() -> {
            int introType = SharedPreferencesManager.getInstance().getType();

            if (introType == GoSingConstants.INTRO_TYPE_FIRST_START
                    || introType == GoSingConstants.INTRO_TYPE_ERROR) {
                // 권한 설정 화면으로 이동
                moveActivity(GoSingAdminConfirmPermissionActivity.class);
            } else if (introType == GoSingConstants.INTRO_TYPE_AUTO_LOGIN) {
                // 자동 로그인
                onLogin();
            } else if (introType == GoSingConstants.INTRO_TYPE_PERMISSION_CHECK_SUCCESS) {
                // 로그인 또는 회원가입 그룹 표시
                viewLoginOrSignUp.setVisibility(View.VISIBLE);
            }


        }, 1800);
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
