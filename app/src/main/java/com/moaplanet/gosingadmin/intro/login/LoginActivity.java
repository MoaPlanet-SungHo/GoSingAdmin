package com.moaplanet.gosingadmin.intro.login;

import android.content.Intent;
import android.widget.Button;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;

    @Override
    public int layoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        btnLogin = findViewById(R.id.btn_login_login);
    }

    @Override
    public void initListener() {
        btnLogin.setOnClickListener(view -> {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
            sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
            sharedPreferencesManager.setLoginInfo("id", "pw");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
