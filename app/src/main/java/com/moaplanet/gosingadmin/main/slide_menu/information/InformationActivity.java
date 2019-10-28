package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.content.Intent;
import android.view.View;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.slide_menu.setting.SettingDetailDataActivity;

public class InformationActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleBar titleVIew;


    @Override
    public int layoutRes() {
        return R.layout.activity_my_info;
    }


    @Override
    public void initView() {
        titleVIew = findViewById(R.id.common_information_title_bar);
        titleVIew.setTitle("내 정보");

    }

    @Override
    public void initListener() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SettingDetailDataActivity.class);
        switch (v.getId()) {
            case R.id.terms_service:
                intent.putExtra("url", "1");
                startActivity(intent);
                break;
        }
    }


}
