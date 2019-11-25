package com.moaplanet.gosingadmin.common.activity;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.manager.AuthManager;
import com.moaplanet.gosingadmin.common.model.viewmodel.CreatePinViewModel;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

/**
 * 핀 패스워드 생성 액티비티
 * 로그인 하는데 핀 패스워드 없을경우
 * 회원가입후 핀 생성 실패후 핀 생성시 등등
 * 특정한 이유로 인해 핀 패스워드가 없을졌을시 사용
 */
public class CreatePinActivity extends BaseActivity {

    private CreatePinViewModel viewModel;

    @Override
    public void initActivity() {
        super.initActivity();
        viewModel = ViewModelProviders.of(this).get(CreatePinViewModel.class);
    }

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

    @Override
    protected void initObserve() {
        super.initObserve();

        viewModel.getPinPw().observe(this, this::initPin);
    }

    private void initPin(String pin) {
        onLoadingStart();
        AuthManager authManager = new AuthManager();
        authManager.setOnAuthCallback(new AuthManager.onAuthCallback() {
            @Override
            public void onSuccess() {
                onLoadingStop();
                setResult(GoSingConstants.ACTION_RESULT_CODE_PIN_SUCCESS);
                finish();
            }

            @Override
            public void onFail() {
                onLoadingStop();
                setResult(GoSingConstants.ACTION_RESULT_CODE_PIN_FAIL);
                finish();
            }
        });

        authManager.onInitPin(this, authManager.KEY_ALIAS_MOBILE_PIN, pin);
    }
}
