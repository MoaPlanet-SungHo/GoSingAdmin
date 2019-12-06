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
import com.moaplanet.gosingadmin.intro.main.IntroActivity;
import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 앱 최초 진입시 권한 체크
 */
public class GoSingAdminConfirmPermissionActivity extends BaseActivity {

    private CompositeDisposable compositeDisposable;

    @Override
    public int layoutRes() {
        return R.layout.activity_gosing_admin_confirm_permission;
    }

    @Override
    public void initActivity() {
        super.initActivity();
        compositeDisposable = new CompositeDisposable();
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
        onShowPermissionList();
    }

    /**
     * 사용자에게 권한 종류둘울 보여줌 및 권한 허용 다이얼로그 띄움
     */
    private void onShowPermissionList() {
        RxPermissions rxPermissions = new RxPermissions(this);
        compositeDisposable.add(rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        Logger.i("권한 성공");
                    } else {
                        Logger.i("권한 거절");
                    }
                }));
    }
}
