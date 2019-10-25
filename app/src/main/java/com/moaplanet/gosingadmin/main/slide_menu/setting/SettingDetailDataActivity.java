package com.moaplanet.gosingadmin.main.slide_menu.setting;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

public class SettingDetailDataActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleBar titleVIew;
    private WebView mWebView;

    @Override
    public int layoutRes() {
        return R.layout.activity_setting_detail_data;
    }

    @Override
    public void initView() {

        titleVIew = findViewById(R.id.common_notice_detail_title);

        Intent intent = getIntent(); /*데이터 수신*/
        String name = intent.getExtras().getString("url"); /*String형*/
        if (name.equals("1")) {
            titleVIew.setTitle(R.string.detail_data_one);
        }else if(name.equals("2")) {
            titleVIew.setTitle(R.string.detail_data_two);
        }else if(name.equals("3")) {
            titleVIew.setTitle(R.string.detail_data_three);
        }else if(name.equals("4")) {
            titleVIew.setTitle(R.string.detail_data_four);
        }

        mWebView = findViewById(R.id.setting_data_web);
        mWebView.loadUrl("www.naver.com");
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

    }


}
