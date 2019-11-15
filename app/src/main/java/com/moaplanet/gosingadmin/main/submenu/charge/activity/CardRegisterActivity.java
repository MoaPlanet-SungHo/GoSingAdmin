package com.moaplanet.gosingadmin.main.submenu.charge.activity;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;

/**
 * 카드 등록 화면
 */
public class CardRegisterActivity extends BaseActivity {

    private boolean mLoading = false;

    @Override
    public int layoutRes() {
        return R.layout.activity_card_register;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onBackPressed() {
        if (!mLoading) {
            super.onBackPressed();
        }
    }

    public void setLoading(boolean mLoading) {
        this.mLoading = mLoading;
    }
}
