package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.CreatePinActivity;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.interfaces.JsReceiver;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.intro.sign_up.activity.SignUpActivity;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.moaplanet.gosingadmin.utils.JsBridge;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class SelfCertificationFragment extends BaseFragment implements JsReceiver {

    private WebView webViewKgMobilians;

    // 뷰 모델
    private SignUpViewModel signUpViewModel;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null && getActivity() instanceof SignUpActivity && signUpViewModel == null) {
            signUpViewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_self_certification;
    }

    @Override
    public void initView(View view) {
        webViewKgMobilians = view.findViewById(
                R.id.wv_self_certification_kg);
    }

    @Override
    public void initListener() {
        CommonTitleBar titleBar = view.findViewById(R.id.common_self_certification_title_bar);
        RxView.clicks(titleBar)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null && getActivity() instanceof CreatePinActivity) {
                        getActivity().finish();
                    } else {
                        onBackNavigation();
                    }
                });
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

        final String PHONE_CHECK_URL = "http://175.198.102.230:8085/MOAGossingShop/notLogin/kgMobilTestPage.do?typeInfo=";
        final String RESULT_PAGE = "http://175.198.102.230:8085/MOAGossingShop/notLogin/okayMobile.do";


        webViewKgMobilians.getSettings().setJavaScriptEnabled(true);
        webViewKgMobilians.addJavascriptInterface(new JsBridge(this), "Android");
//        webViewKgMobilians.loadUrl(KG_MOBILIANS_BASE_URL + KG_MOBILIANS_START_URL);

        String authMobileType = "01";

        if (getActivity() != null) {
            if (getActivity() instanceof SignUpActivity) {
                authMobileType = "01";
            } else if (getActivity() instanceof CreatePinActivity) {
                authMobileType = "04";
            }
        }

        webViewKgMobilians.loadUrl(PHONE_CHECK_URL + authMobileType);

        webViewKgMobilians.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
                } else
                    return runIdentityVerificationApp(url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    return true;
                } else
                    return runIdentityVerificationApp(url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                if (url.equals("http://175.198.102.230:8085/MOAGossingShop/notLogin/okayMobile.do")) {
                    webViewKgMobilians.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                switch (url) {
//                    case KG_MOBILIANS_BASE_URL + KG_MOBILIANS_START_URL:
//                        webViewKgMobilians.loadUrl("javascript:setMyAuthInfo('" + DUMMY_JSON + "')");
//                        break;
//                    case KG_MOBILIANS_BASE_URL + KG_MOBILIANS_RESULT_URL:
//                        webViewKgMobilians.loadUrl("javascript:Android.showHTML" + "(document.getElementsByTagName('body')[0].innerHTML);");
//                        break;
                    case RESULT_PAGE:
                        webViewKgMobilians.loadUrl("javascript:Android.showHTML" + "(document.getElementsByTagName('body')[0].innerHTML);");
                        break;
                }

            }
        });
    }

    private boolean runIdentityVerificationApp(String url) {
        if (url.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                Intent getPackage = view.getContext().getPackageManager().getLaunchIntentForPackage(Objects.requireNonNull(intent.getPackage()));
                if (getPackage != null) {
                    startActivity(intent);
                } else {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                    startActivity(marketIntent);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void onJsReceiverSuccess(String resultMsg) {
        if (getActivity() instanceof SignUpActivity) {

            String ci = "";
            // 이름
            String name = "";
            // 성별
            String sex = "";
            // 생년월일
            String socialno = "";
            // 핸드폰 번호
            String phoneNUmber = "";

            try {
                JSONObject jsonObject = new JSONObject(resultMsg);
                if (!jsonObject.get("Ci").equals("")) {
                    ci = jsonObject.getString("Ci");
                }

                // 이름
                if (!jsonObject.get("Name").equals("")) {
                    name = jsonObject.getString("Name");
                }

                // 핸드폰 번호
                if (!jsonObject.get("No").equals("")) {
                    phoneNUmber = jsonObject.getString("No");
                }

                // 성별
                if (!jsonObject.get("sex").equals("")) {
                    sex = jsonObject.getString("sex");
                }

                // 생년 월일
                if (!jsonObject.get("Socialno").equals("")) {
                    socialno = jsonObject.getString("Socialno");
                }

            } catch (JSONException e) {
                Logger.d("Json Error\n" + e);
                onJsReceiverFail();
            }
            signUpViewModel.setCi(ci);
            signUpViewModel.setUserAge(socialno);
            signUpViewModel.setPhoneNumber(phoneNUmber);
            signUpViewModel.setUserGender(sex);
            signUpViewModel.setUserName(name);
            onMoveNavigation(R.id.action_fragment_create_account);
        } else if (getActivity() instanceof CreatePinActivity) {
            onMoveNavigation(R.id.action_fragment_sign_up_input_password);
        }
    }

    @Override
    public void onJsReceiverFail() {
        Toast.makeText(view.getContext(),
                "다시 시도해 주세요",
                Toast.LENGTH_SHORT).show();
        if (getActivity() instanceof SignUpActivity) {
            onBackNavigation();
        } else if (getActivity() instanceof CreatePinActivity) {
            getActivity().finish();
        }
    }
}
