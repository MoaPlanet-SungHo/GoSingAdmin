package com.moaplanet.gosingadmin.common.activity;

import android.view.View;

import com.moaplanet.gosingadmin.R;

/**
 * 핀 패스워드 생성 액티비티
 * 로그인 하는데 핀 패스워드 없을경우
 * 회원가입후 핀 생성 실패후 핀 생성시 등등
 * 특정한 이유로 인해 핀 패스워드가 없을졌을시 사용
 */
public class CreatePinActivity extends BaseActivity {
    @Override
    public int layoutRes() {
        return R.layout.activity_create_pin;
    }

    @Override
    public void initView() {
        View loadingBar = findViewById(R.id.pb_activity_create_pin_loading);
        loadingBar.setVisibility(View.GONE);
        setLoadingBar(loadingBar);
    }

    @Override
    public void initListener() {

    }
}
