package com.moaplanet.gosingadmin.main.slide_menu.information;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.BaseActivity;
import com.moaplanet.gosingadmin.common.fragment.SelfCertificationFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;

public class PhoneNumberChangeActivity extends BaseActivity {

    private FrameLayout flCertified;
    private TextView tvPhonNumberChangeSuccess;
    private View loadingBar;
    private Button btnCertified;

    private boolean inLoading = false;

    @Override
    public int layoutRes() {
        return R.layout.activity_phone_number_change;
    }

    @Override
    public void initView() {

        loadingBar = findViewById(R.id.pb_activity_phone_number_change_loading);
        loadingBar.setVisibility(View.GONE);

        TextView tvPhoneNumberChangePlz = findViewById(R.id.tv_activity_phone_number_change_plz);
        tvPhoneNumberChangePlz.setText(getString(R.string.activity_phone_number_change_certified_plz,
                getIntent().getStringExtra(GoSingConstants.INTENT_KEY_MY_PHONE_NUMBER)));

        flCertified = findViewById(R.id.fl_activity_phone_number_change);
        flCertified.setVisibility(View.GONE);

        tvPhonNumberChangeSuccess = findViewById(R.id.tv_activity_phone_number_change_success);
        tvPhonNumberChangeSuccess.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        CommonTitleBar titleBar = findViewById(R.id.title_activity_phone_number);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> finish());

        btnCertified = findViewById(R.id.btn_activity_phone_number_change_certified);
        RxView.clicks(btnCertified)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_activity_phone_number_change, new SelfCertificationFragment())
                            .commit();
                    flCertified.setVisibility(View.VISIBLE);
                });
    }

    @Override
    public void onBackPressed() {
        // 본인인증이 켜져있을경우는 백키 눌렀을때 본인인증 화면 닫음
        if (flCertified.getVisibility() == View.VISIBLE) {
            flCertified.setVisibility(View.GONE);
        } else {
            if (!inLoading) {
                super.onBackPressed();
            }
        }
    }

    /**
     * 본인 인증 실패
     */
    public void onFailCertification() {
        new Thread(() -> runOnUiThread(() -> flCertified.setVisibility(View.GONE))).start();
    }

    /**
     * 본인 인증 성공
     */
    public void onSuccessCertification(String phoneNumber, String ci, String name, String socialno,
                                       String sex) {
        new Thread(() -> runOnUiThread(() -> {
            loadingBar.setVisibility(View.VISIBLE);
            flCertified.setVisibility(View.GONE);
        })).start();
        Logger.d("변경할 번호 : " + phoneNumber);
        inLoading = true;
        RetrofitService.getInstance().getGoSingApi()
                .postPhoneNumberChange(phoneNumber, ci, name, socialno, sex)
                .enqueue(new RetrofitCallBack<PhoneNumberChangeDTO>() {
                    @Override
                    public void onSuccess(PhoneNumberChangeDTO response) {
                        loadingBar.setVisibility(View.GONE);
                        inLoading = false;
                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            View tvPhonNumberChangeBefore = findViewById(R.id.ll_activity_phone_number_change_before_group);
                            tvPhonNumberChangeBefore.setVisibility(View.GONE);
                            tvPhonNumberChangeSuccess.setText(response.getPhoneNumber());
                            tvPhonNumberChangeSuccess.setVisibility(View.VISIBLE);
                            btnCertified.setText(getString(R.string.activity_phone_number_change_done));
                        } else if (response.getDetailCode() == 201) {
                            Toast.makeText(PhoneNumberChangeActivity.this,
                                    "이미 존재하는 핸드폰 번호 입니다."
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            onNetworkConnectFail();
                        }

                    }

                    @Override
                    public void onFail(Response<PhoneNumberChangeDTO> response, Throwable t) {
                        loadingBar.setVisibility(View.GONE);
                        inLoading = false;
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onExpireSession(Response<PhoneNumberChangeDTO> response, Throwable t) {
                        loadingBar.setVisibility(View.GONE);
                        inLoading = false;
                        onNotSession();
                    }
                });

    }
}
