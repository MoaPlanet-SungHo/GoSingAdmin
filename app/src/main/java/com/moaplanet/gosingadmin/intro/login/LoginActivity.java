package com.moaplanet.gosingadmin.intro.login;

import android.content.Intent;
import android.widget.Button;

import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;

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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
