package com.moaplanet.gosingadmin.main.submenu.charge.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.model.ChargeViewModel;

public class ChargeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChargeViewModel mChargeViewModel = ViewModelProviders.of(this).get(ChargeViewModel.class);
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
}
