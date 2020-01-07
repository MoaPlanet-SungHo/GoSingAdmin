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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.activity.CreatePinActivity;
import com.moaplanet.gosingadmin.common.manager.LoginManager;
import com.moaplanet.gosingadmin.common.manager.StoreManager;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.login.moel.req.ReqLoginDto;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.RegisterStoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.StoreActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.WaitingApprovalActivity;
import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPw;
    private TextView tvErrMsg;
    private String userEmail, userPw;
    private TextView testMove;

    private int mDetailCode = -1;

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
    }

    @Override
    public void initListener() {

        CommonTitleBar titleBar = findViewById(R.id.common_login_title_bar);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());

        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> startLogin());

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

        // 아이디 찾기
        TextView tvFindId = findViewById(R.id.tv_login_find_id);
        tvFindId.setOnClickListener(view -> showReadyToast());

        // 비밀번호 찾기
        TextView tvChangePw = findViewById(R.id.tv_login_change_pw);
        tvChangePw.setOnClickListener(view -> showReadyToast());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.ACTION_REQ_CODE_PIN) {
            if (resultCode == GoSingConstants.ACTION_RESULT_CODE_PIN_SUCCESS) {
                onLoginComplete();
            } else {
                Toast.makeText(this,
                        "결제 비밀번호 생성에 실패했습니다.",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    private void startLogin() {

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

    LoginManager.onLoginListener onLoginListener = new LoginManager.onLoginListener() {
        @Override
        public void onLoginSuccess(int stateCode, int detailCode) {
            tvErrMsg.setVisibility(View.GONE);
            mDetailCode = detailCode;
            onCheckPin();
        }

        @Override
        public void onLoginFail(int stateCode, int detailCode) {
            tvErrMsg.setVisibility(View.VISIBLE);
        }
    };

    private void onCheckPin() {
        if (SharedPreferencesManager.getInstance().getPin().equals("")) {
            Toast.makeText(this,
                    "결제 비밀번호를 생성해 주세요.",
                    Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(this, CreatePinActivity.class);
            startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_PIN);
        } else {
            onLoginComplete();
        }
    }

    private void onLoginComplete() {
        if (mDetailCode == NetworkConstants.LOGIN_CODE_SUCCESS) {
            moveActivity(MainActivity.class);
            finishAffinity();
        } else if (mDetailCode == NetworkConstants.LOGIN_CODE_ACCOUNT_INACTIVE) {
            moveActivity(WaitingApprovalActivity.class);
            finishAffinity();
        } else if (mDetailCode == NetworkConstants.LOGIN_CODE_EMPTY_STORE) {
            moveActivity(RegisterStoreActivity.class);
            finishAffinity();
        } else {
            Toast.makeText(this,
                    "다시 로그인해 주세요.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void moveActivity(Class moveActivity) {
        Intent intent = new Intent(this, moveActivity);
        startActivity(intent);
    }

    private void showReadyToast() {
        Toast.makeText(this, R.string.common_toast_ready, Toast.LENGTH_SHORT).show();
    }
}
