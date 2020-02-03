package com.moaplanet.gosingadmin.main.slide_menu.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.activity.WebViewActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.network.NetworkConstants;

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
//        Intent intent = new Intent(this, SettingDetailDataActivity.class);
        Intent intent = new Intent(this, WebViewActivity.class);
        switch (v.getId()) {
            case R.id.terms_service:
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE, getString(R.string.detail_data_one));
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL, NetworkConstants.GOSING_ADMIN_AGREE_TERMS_USE_URL);
//                intent.putExtra("url", "1");
                startActivity(intent);
                break;
            case R.id.terms_location:
//                intent.putExtra("url", "2");
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE, getString(R.string.detail_data_two));
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL, NetworkConstants.GOSING_ADMIN_GPS_USE_URL);
                startActivity(intent);
                break;
            case R.id.terms_finance:
//                intent.putExtra("url", "3");
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE, getString(R.string.detail_data_three));
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL, NetworkConstants.GOSING_ADMIN_AGREE_ELET_FIN_URL);
                startActivity(intent);
                break;
            case R.id.terms_privacy:
//                intent.putExtra("url", "4");
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE, getString(R.string.detail_data_four));
                intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL, NetworkConstants.GOSING_ADMIN_AGREE_PRIVATE_INFO_URL);
                startActivity(intent);
                break;
        }
    }


}
