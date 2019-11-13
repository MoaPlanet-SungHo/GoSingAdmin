package com.moaplanet.gosingadmin.main.qrpayment.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.qrpayment.model.QrCodeViewModel;

public class QrCodeActivity extends BaseActivity {

    private boolean isLoading = false;

    @Override
    public int layoutRes() {
        return R.layout.activity_qr_code;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QrCodeViewModel qrCodeViewModel = ViewModelProviders.of(this).get(QrCodeViewModel.class);
        qrCodeViewModel.getIsLoadingServer().observe(this,
                isLoading -> this.isLoading = isLoading);
    }

    @Override
    public void onBackPressed() {
        if (!isLoading) {
            super.onBackPressed();
        }
    }
}
