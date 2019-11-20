package com.moaplanet.gosingadmin.intro;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class GoSingAdminConfirmPermissionActivity extends BaseActivity {

    @Override
    public int layoutRes() {
        return R.layout.activity_gosing_admin_confirm_permission;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {

        // 완료 버튼
        Button btnDone = findViewById(R.id.btn_confirm_permission_done);
        RxView.clicks(btnDone)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
                    Intent intent = new Intent(this, IntroActivity.class);
                    sharedPreferencesManager.setIntroType(GoSingConstants.INTRO_TYPE_PERMISSION_CHECK_SUCCESS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {

                    if (granted) {
                        Logger.d("권한 성공");
                    } else {
                        Logger.d("권한 거절");
                    }
                });
    }
}
