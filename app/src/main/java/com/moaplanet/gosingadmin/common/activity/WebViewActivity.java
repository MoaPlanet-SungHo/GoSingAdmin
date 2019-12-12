package com.moaplanet.gosingadmin.common.activity;

import android.webkit.WebView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * 웹뷰로 뭔가를 보여줄때 사용
 */
public class WebViewActivity extends BaseActivity {
    @Override
    public int layoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        CommonTitleBar titleBar = findViewById(R.id.common_activity_web_view_title_bar);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());
        WebView webView = findViewById(R.id.wv_activity_web_view);

        if (getIntent() != null) {
            String title = getIntent().getStringExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE);
            if (title != null && !title.equals("")) {
                titleBar.setTitle(title);
            }

            String url = getIntent().getStringExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL);
            if (url != null && !url.equals("")) {
                webView.loadUrl(url);
            }
        }
    }

    @Override
    public void initListener() {

    }
}
