package com.moaplanet.gosingadmin.main.slide_menu.setting;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.activity.CreatePinActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class PaymentPwChangeActivity extends BaseActivity {
    @Override
    public int layoutRes() {
        return R.layout.activity_payment_pw_change;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

        CommonTitleBar titleBar = findViewById(R.id.title_activity_payment_pw_change);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());

        Button btnCertifed = findViewById(R.id.btn_activity_payment_pw_change);
        RxView.clicks(btnCertifed)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(this, CreatePinActivity.class);
                    startActivityForResult(intent, GoSingConstants.ACTION_REQ_CODE_PIN);
                });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.ACTION_REQ_CODE_PIN) {
            if (resultCode == GoSingConstants.ACTION_RESULT_CODE_PIN_SUCCESS) {
                finish();
            } else {
                Toast.makeText(this,
                        "결제 비밀번호 생성에 실패했습니다.",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }
}
