package com.moaplanet.gosingadmin.main.submenu.store.activity;

import android.widget.Button;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;


public class WaitingApprovalActivity extends BaseActivity {

    private Button btnFinish;
    private CommonTitleBar commonTitleBar;

    @Override
    public int layoutRes() {
        return R.layout.activity_wating_approval;
    }

    @Override
    public void initView() {
        btnFinish = findViewById(R.id.btn_waiting_approval_finish);
        commonTitleBar = findViewById(R.id.common_waiting_approval_title_bar);
        commonTitleBar.setChangeLeftIc(R.drawable.ic_common_title_bar_back);
    }

    @Override
    public void initListener() {
        commonTitleBar.setBackButtonClickListener(view -> finishAffinity());
        btnFinish.setOnClickListener(view -> finishAffinity());
    }
}
