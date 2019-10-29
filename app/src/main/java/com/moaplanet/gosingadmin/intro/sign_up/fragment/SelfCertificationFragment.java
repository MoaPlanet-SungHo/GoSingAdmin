package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.interfaces.JsReceiver;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.utils.JsBridge;
import com.orhanobut.logger.Logger;

public class SelfCertificationFragment extends BaseFragment implements JsReceiver {

    private WebView webViewKgMobilians;
    private CommonTitleBar titleBar;

    @Override
    public int layoutRes() {
        return R.layout.fragment_self_certification;
    }

    @Override
    public void initView(View view) {
        webViewKgMobilians = view.findViewById(
                R.id.wv_self_certification_kg);
        titleBar = view.findViewById(R.id.common_self_certification_title_bar);
    }

    @Override
    public void initListener() {
        titleBar.setBackButtonClickListener(view -> onBackNavigation());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initKgMobilians();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initKgMobilians() {
        final String KG_MOBILIANS_BASE_URL = "http://backend.goeat.co.kr/";
        final String KG_MOBILIANS_START_URL = "mobiAuth/mobiAuthWeb.jsp";
        final String KG_MOBILIANS_RESULT_URL = "mobiAuth/mobiAuthResult.jsp";
        final String DUMMY_JSON =
                "{\"siteurl\":\"(주)모아플래닛\",\"payMode\":\"10\",\"keygb\":\"0\",\"ciSvcid\":" +
                        "\"190211067167\",\"cryptyn\":\"Y\",\"callType\":\"SELF\"," +
                        "\"cashGb\":\"CI\",\"closeurl\":\"http://backend.goeat.co.kr/mobiAuth/fail.jsp\"," +
                        "\"ciMode\":\"61\",\"mstr\":\"authSepaCd=01$userId=191002001642$mobiAuthHistId=2019100200001140$email=null\"," +
                        "\"logoYn\":\"N\",\"okurl\":\"http://backend.goeat.co.kr/mobiAuth/mobiAuthResult.jsp\"," +
                        "\"tradeid\":\"MLVnGs0WdHia5dq1ISpIqgg==\"}";
        webViewKgMobilians.getSettings().setJavaScriptEnabled(true);
        webViewKgMobilians.addJavascriptInterface(new JsBridge(this), "Android");
        webViewKgMobilians.loadUrl(KG_MOBILIANS_BASE_URL + KG_MOBILIANS_START_URL);

        webViewKgMobilians.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                switch (url) {
                    case KG_MOBILIANS_BASE_URL + KG_MOBILIANS_START_URL:
                        webViewKgMobilians.loadUrl("javascript:setMyAuthInfo('" + DUMMY_JSON + "')");
                        break;
                    case KG_MOBILIANS_BASE_URL + KG_MOBILIANS_RESULT_URL:
                        webViewKgMobilians.loadUrl("javascript:Android.showHTML" + "(document.getElementsByTagName('body')[0].innerHTML);");
                        break;
                }

            }
        });
    }

    @Override
    public void onJsReceiverSuccess(String resultMsg) {
        Logger.d(resultMsg);
        Toast.makeText(getContext(), "본인인증 성공", Toast.LENGTH_SHORT).show();
        onMoveNavigation(R.id.action_fragment_create_account);
    }

    @Override
    public void onJsReceiverFail() {
        Toast.makeText(getContext(), "본인인증 실패", Toast.LENGTH_SHORT).show();
    }
}