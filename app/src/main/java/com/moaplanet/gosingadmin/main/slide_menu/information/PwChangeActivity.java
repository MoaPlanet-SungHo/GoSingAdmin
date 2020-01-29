package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.fragment.SelfCertificationFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.model.CommonResDto;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.preference.UserSharedPreference;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.concurrent.TimeUnit;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;

public class PwChangeActivity extends BaseActivity {

    private FrameLayout pwChangeCertifed;

    private View pwChangeView;
    private View pwCertifiedView;

    private View inputPwErr, inputPwCheckErr;

    private boolean inputPwSuccess = false, inputPwCheckSuccess = false;

    private Button btnDone;

    private View loadingBar;

    @Override
    public int layoutRes() {
        return R.layout.activity_pw_change;
    }

    @Override
    public void initView() {
        pwChangeCertifed = findViewById(R.id.fl_activity_pw_change);
        pwChangeCertifed.setVisibility(View.GONE);

        pwChangeView = findViewById(R.id.cl_activity_pw_change_group);
        pwChangeView.setVisibility(View.GONE);

        pwCertifiedView = findViewById(R.id.cl_activity_pw_change_certified_group);
        pwCertifiedView.setVisibility(View.VISIBLE);

        inputPwErr = findViewById(R.id.tv_activity_pw_change_err);
        inputPwErr.setVisibility(View.GONE);

        inputPwCheckErr = findViewById(R.id.tv_activity_pw_change_check_err);
        inputPwCheckErr.setVisibility(View.GONE);

        btnDone = findViewById(R.id.btn_activity_pw_change_done);

        loadingBar = findViewById(R.id.pr_activity_pw_change_loading);
        loadingBar.setVisibility(View.GONE);

    }

    @Override
    public void initListener() {
        // 뒤로가기
        CommonTitleBar titleBar = findViewById(R.id.title_activity_pw_change);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (pwChangeView.getVisibility() == View.VISIBLE) {
                        pwChangeView.setVisibility(View.GONE);
                        pwCertifiedView.setVisibility(View.VISIBLE);
                    } else if (pwChangeCertifed.getVisibility() == View.VISIBLE) {
                        pwChangeCertifed.setVisibility(View.GONE);
                        pwCertifiedView.setVisibility(View.VISIBLE);
                    } else {
                        finish();
                    }
                });

        // 휴대폰 본인인증
        Button btnCertifed = findViewById(R.id.btn_activity_pw_change);
        RxView.clicks(btnCertifed)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_activity_pw_change, new SelfCertificationFragment())
                            .commit();
                    pwChangeCertifed.setVisibility(View.VISIBLE);
                });

        EditText etNewPw = findViewById(R.id.et_activity_pw_change_new);
        etNewPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 8 && s.length() <= 20 && StringUtil.isPw(s.toString())) {
                    etNewPw.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_checkbox_press, 0);
                    inputPwErr.setVisibility(View.GONE);
                    inputPwSuccess = true;
                    if (inputPwCheckSuccess && inputPwSuccess) {
                        btnDone.setEnabled(true);
                    }
                } else {
                    etNewPw.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_checkbox_nor, 0);
                    inputPwErr.setVisibility(View.VISIBLE);
                    inputPwSuccess = false;
                    btnDone.setEnabled(false);
                }
            }
        });

        EditText etPwCheck = findViewById(R.id.et_activity_pw_change_check);
        etPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(etNewPw.getText().toString())) {
                    etPwCheck.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_checkbox_press, 0);
                    inputPwCheckErr.setVisibility(View.GONE);
                    inputPwCheckSuccess = true;

                    if (inputPwCheckSuccess && inputPwSuccess) {
                        btnDone.setEnabled(true);
                    }

                } else {
                    etPwCheck.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_checkbox_nor, 0);
                    inputPwCheckErr.setVisibility(View.VISIBLE);
                    inputPwCheckSuccess = false;
                    btnDone.setEnabled(false);
                }
            }
        });

        // 비밀번호 변경
        RxView.clicks(btnDone)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    loadingBar.setVisibility(View.VISIBLE);
                    RetrofitService.getInstance().getGoSingApi()
                            .postPwChange(getIntent().getStringExtra(GoSingConstants.INTENT_KEY_MY_PHONE_NUMBER),
                                    etPwCheck.getText().toString())
                            .enqueue(new RetrofitCallBack<CommonResDto>() {
                                @Override
                                public void onSuccess(CommonResDto response) {

                                    if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                                        UserSharedPreference userSharedPreference = new UserSharedPreference();
                                        userSharedPreference.onLogout();
                                        Intent intent = new Intent(PwChangeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else {
                                        onNetworkConnectFail();
                                    }
                                    loadingBar.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFail(Response<CommonResDto> response, Throwable t) {
                                    onNetworkConnectFail();
                                    loadingBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onExpireSession(Response<CommonResDto> response, Throwable t) {
                                    onNotSession();
                                    loadingBar.setVisibility(View.GONE);
                                }
                            });
                });

    }

    public void onFailCertified() {
        new Thread(() -> runOnUiThread(() -> pwChangeCertifed.setVisibility(View.GONE))).start();
    }

    public void onSuccessCertified() {
        new Thread(() -> runOnUiThread(() -> {
            pwChangeCertifed.setVisibility(View.GONE);
            pwCertifiedView.setVisibility(View.GONE);
            pwChangeView.setVisibility(View.VISIBLE);
        })).start();
    }

    @Override
    public void onBackPressed() {

        if (pwChangeView.getVisibility() == View.VISIBLE) {
            pwChangeView.setVisibility(View.GONE);
            pwCertifiedView.setVisibility(View.VISIBLE);
        } else if (pwChangeCertifed.getVisibility() == View.VISIBLE) {
            pwChangeCertifed.setVisibility(View.GONE);
            pwCertifiedView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }

    }
}
