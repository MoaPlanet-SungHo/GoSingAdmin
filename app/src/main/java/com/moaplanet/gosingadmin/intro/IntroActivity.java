package com.moaplanet.gosingadmin.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.intro.sign_up.SignUpActivity;

public class IntroActivity extends BaseActivity {

    private Button btnSignUp, btnLogin;
    private LinearLayout llLogin;

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
        // 데모 삭제 예정
        if (getIntent().getIntExtra("temp", 0) == 0) {
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(() -> {
                moveActivity(GoSingAdminConfirmPermissionActivity.class);
            }, 1800);
        }else{
            llLogin.setVisibility(View.VISIBLE);
        }
    }

    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
        finish();
    }
}
