package com.moaplanet.gosingadmin.intro;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.utils.SharedPreferencesManager;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class GoSingAdminConfirmPermissionActivity extends BaseActivity {

    private Button btnDone;

    @Override
    public int layoutRes() {
        return R.layout.activity_gosing_admin_confirm_permission;
    }

    @Override
    public void initView() {
        btnDone = findViewById(R.id.btn_confirm_permission_done);
    }

    @Override
    public void initListener() {

        btnDone.setOnClickListener(view -> {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
            Intent intent = new Intent(this, IntroActivity.class);
            sharedPreferencesManager.setIntroType(GoSingConstants.TYPE_PERMISSION_CHECK_SUCCESS);
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
