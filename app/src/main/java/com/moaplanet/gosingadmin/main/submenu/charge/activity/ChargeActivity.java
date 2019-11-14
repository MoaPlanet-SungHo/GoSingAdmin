package com.moaplanet.gosingadmin.main.submenu.charge.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.model.ChargeViewModel;

public class ChargeActivity extends BaseActivity {

    // 충전 뷰 모델
    private ChargeViewModel mChargeViewModel;
    // 로딩 유무
    private boolean mIsLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChargeViewModel = ViewModelProviders.of(this).get(ChargeViewModel.class);
        mChargeViewModel.getIsLoading().observe(this, isLoading -> mIsLoading = isLoading);
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_charge;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onBackPressed() {
        if (!mIsLoading) {
            super.onBackPressed();
        }
    }
}
