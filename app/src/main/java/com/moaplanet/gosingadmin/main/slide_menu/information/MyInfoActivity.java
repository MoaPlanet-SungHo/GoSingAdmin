package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class MyInfoActivity extends BaseActivity {

    private MyInfoViewModel viewModel;

    @Override
    public void initActivity() {
        super.initActivity();
        viewModel = ViewModelProviders.of(this).get(MyInfoViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {

        // 뒤로가기
        CommonTitleBar titleBar = findViewById(R.id.common_activity_my_info_title);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewModel != null) {

            // 이메일 세팅
            viewModel.getEmail().observe(this, email -> {
                TextView tvEmail = findViewById(R.id.tv_activity_my_info_email);
                tvEmail.setText(email);
            });

            // 패스워드 세팅
            viewModel.getPw().observe(this, pw -> {
                TextView tvPw = findViewById(R.id.tv_activity_my_info_pw);
                tvPw.setText(pw);
            });

            // 휴대폰 번호
            viewModel.getPhoneNumber().observe(this, phoneNumber -> {
                TextView tvPhoneNumber = findViewById(R.id.tv_activity_my_info_phone);
                tvPhoneNumber.setText(phoneNumber);
            });

        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.postMyInfo();
    }

}
