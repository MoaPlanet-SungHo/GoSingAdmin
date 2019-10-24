package com.moaplanet.gosingadmin.intro;

import android.content.Intent;
import android.widget.Button;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;

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
            Intent intent = new Intent(this, IntroActivity.class);
            intent.putExtra("temp",-1);
            startActivity(intent);
            finish();
        });
    }

}
