package com.moaplanet.gosingadmin.intro.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.login.moel.req.ReqLoginDto;
import com.moaplanet.gosingadmin.intro.login.moel.res.ResLoginDto;
import com.moaplanet.gosingadmin.main.MainActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;
import com.moaplanet.gosingadmin.utils.StringUtil;

import retrofit2.Call;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPw;
    private ReqLoginDto reqLoginDto;
    private TextView tvErrMsg;
    private String userEmail, userPw;

    @Override
    public int layoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
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
        reqLoginDto = new ReqLoginDto();
    }

    private void startLogin() {
        reqLoginDto.setEmail(userEmail);
        reqLoginDto.setPw(userPw);

        RetrofitService.getInstance().getGoSingApiService(getApplicationContext()).login(
                reqLoginDto.getEmail(),
                reqLoginDto.getPw()
                , reqLoginDto.getSignType())
                .enqueue(moaAuthCallback);
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

    private MoaAuthCallback<ResLoginDto> moaAuthCallback = new MoaAuthCallback<ResLoginDto>(
            RetrofitService.getInstance().getMoaAuthConfig(),
            RetrofitService.getInstance().getSessionChecker()
    ) {
        @Override
        public void onFinalResponse(Call<ResLoginDto> call, ResLoginDto resLoginDto) {
            if (resLoginDto.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {

                if (resLoginDto.getDetailCode() == NetworkConstants.CODE_LOGIN_SUCCESS) {
                    successLogin();
                } else if (resLoginDto.getDetailCode() == NetworkConstants.CODE_ACCOUNT_INACTIVE) {
                    accountInactive();
                } else {
                    tvErrMsg.setVisibility(View.VISIBLE);
                }

            } else {
                tvErrMsg.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFinalFailure(Call<ResLoginDto> call, boolean isSession, Throwable t) {
            tvErrMsg.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 로그인 성공
     */
    private void successLogin() {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
        sharedPreferencesManager.setLoginInfo(userEmail, userPw);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 계정 비활성화
     */
    private void accountInactive() {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_AUTO_LOGIN);
        sharedPreferencesManager.setLoginInfo(userEmail, userPw);
    }
}
