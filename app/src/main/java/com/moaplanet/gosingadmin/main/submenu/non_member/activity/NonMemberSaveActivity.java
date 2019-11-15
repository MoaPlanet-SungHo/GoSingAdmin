package com.moaplanet.gosingadmin.main.submenu.non_member.activity;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;

public class NonMemberSaveActivity extends BaseActivity {

    // 뒤로가기 제어 플래그
    private boolean mIsLoading = false;

    @Override
    public int layoutRes() {
        return R.layout.activity_non_member_save;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        // 뷰 모델
        NonMemberSavePointViewModel mViewModel = ViewModelProviders.of(this).get(NonMemberSavePointViewModel.class);
        mViewModel.getIsLoading().observe(this, isLoading -> mIsLoading = isLoading);
    }

    @Override
    public void onBackPressed() {
        if (!mIsLoading) {
            super.onBackPressed();
        }
    }
}
