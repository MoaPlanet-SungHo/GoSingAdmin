package com.moaplanet.gosingadmin.main.slide_menu.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

public class SettingDetailActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleBar titleVIew;
    private RelativeLayout titleListOne, titleListTwo, titleListThree, titleListFour;

    @Override
    public int layoutRes() {
        return R.layout.activity_setting_detail;
    }


    @Override
    public void initView() {
        titleVIew = findViewById(R.id.common_notice_detail_title);
        titleVIew.setTitle("약관 및 정책");
        titleListOne = findViewById(R.id.terms_service);
        titleListTwo = findViewById(R.id.terms_location);
        titleListThree = findViewById(R.id.terms_finance);
        titleListFour = findViewById(R.id.terms_privacy);

        titleListOne.setOnClickListener(this);
        titleListTwo.setOnClickListener(this);
        titleListThree.setOnClickListener(this);
        titleListFour.setOnClickListener(this);

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
            case R.id.terms_location:
                intent.putExtra("url", "2");
                startActivity(intent);
                break;
            case R.id.terms_finance:
                intent.putExtra("url", "3");
                startActivity(intent);
                break;
            case R.id.terms_privacy:
                intent.putExtra("url", "4");
                startActivity(intent);
                break;
        }
    }


}
