package com.moaplanet.gosingadmin.intro.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.common.manager.StoreManager;
import com.moaplanet.gosingadmin.intro.login.moel.req.ReqLoginDto;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.RegisterStoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.StoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.WaitingApprovalActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.utils.StringUtil;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPw;
    private TextView tvErrMsg;
    private String userEmail, userPw;
    private TextView testMove;

    @Override
    public int layoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        // 로딩바 초기화
        ProgressBar mLoadingBar = findViewById(R.id.pb_activity_login_loading);
        mLoadingBar.setVisibility(View.GONE);
        setLoadingBar(mLoadingBar);

        btnLogin = findViewById(R.id.btn_login_login);
        etEmail = findViewById(R.id.et_login_email);
        etPw = findViewById(R.id.et_login_pw);
        tvErrMsg = findViewById(R.id.tv_login_err);
        tvErrMsg.setVisibility(View.INVISIBLE);

        etEmail.setFilters(new InputFilter[]{
                StringUtil.notEmptyFilter()
        });

        etPw.setFilters(new InputFilter[]{
                StringUtil.notEmptyFilter()
        });

        if (BuildConfig.DEBUG) {
            //Todo testMove 삭제
            testMove = findViewById(R.id.tv_login_change_pw);
            testMove.setOnClickListener(view -> moveActivity(MainActivity.class));
        }
    }

    @Override
    public void initListener() {
        btnLogin.setOnClickListener(view -> startLogin());

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onActivationButton();
            }
        });

        etPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onActivationButton();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void startLogin() {
//        onLoadingStart();
        ReqLoginDto reqLoginDto = new ReqLoginDto();
        reqLoginDto.setEmail(userEmail);
        reqLoginDto.setPw(userPw);

        LoginManager loginManager = new LoginManager();
        loginManager.setOnLoginListener(onLoginListener);
        loginManager.onLogin(reqLoginDto, LoginManager.LoginType.LOGIN, this);

    }

    private void onActivationButton() {
        userEmail = etEmail.getText().toString();
        userPw = etPw.getText().toString();

        if (userEmail.length() > 0 && userPw.length() > 0) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

//    private MoaAuthCallback<ResLoginDto> moaAuthCallback = new MoaAuthCallback<ResLoginDto>(
//            RetrofitService.getInstance().getMoaAuthConfig(),
//            RetrofitService.getInstance().getSessionChecker()
//    ) {
//        @Override
//        public void onFinalResponse(Call<ResLoginDto> call, ResLoginDto resLoginDto) {
//            if (resLoginDto.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
//
//                if (resLoginDto.getDetailCode() == NetworkConstants.CODE_LOGIN_SUCCESS) {
//                    successLogin();
//                } else if (resLoginDto.getDetailCode() == NetworkConstants.CODE_ACCOUNT_INACTIVE) {
//                    accountInactive();
//                } else {
//                    tvErrMsg.setVisibility(View.VISIBLE);
//                }
//
//            } else {
//                tvErrMsg.setVisibility(View.VISIBLE);
//            }
//        }
//
//        @Override
//        public void onFinalFailure(Call<ResLoginDto> call, boolean isSession, Throwable t) {
//            tvErrMsg.setVisibility(View.VISIBLE);
//        }
//    };

    /**
     * 로그인 성공
     */
    private void successLogin() {
//        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
//        sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
//        sharedPreferencesManager.setLoginInfo(userEmail, userPw);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 계정 비활성화
     */
    private void accountInactive() {
        StoreManager storeManager = new StoreManager();
        storeManager.onStoreSearch();
//        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
//        sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
//        sharedPreferencesManager.setLoginInfo(userEmail, userPw);
    }

    LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            if (detailCode == NetworkConstants.LOGIN_CODE_SUCCESS) {
                moveActivity(MainActivity.class);
                finishAffinity();
            } else if (detailCode == NetworkConstants.LOGIN_CODE_ACCOUNT_INACTIVE) {
                moveActivity(WaitingApprovalActivity.class);
                finishAffinity();
            } else if (detailCode == NetworkConstants.LOGIN_CODE_EMPTY_STORE) {
                moveActivity(RegisterStoreActivity.class);
                finishAffinity();
            }
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            tvErrMsg.setVisibility(View.VISIBLE);
        }
    };

    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
    }
}
