package com.moaplanet.gosingadmin.main.slide_menu.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class SettingFragment extends BaseFragment {

    private SettingViewModel viewModel;

    // 혜탟 알림
    private Switch benefitSwitch;

    // 야간 알림
    Switch nightSwitch;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewModel != null) {
            // 세션 만료시
            viewModel.getExpireSession().observe(getViewLifecycleOwner(), expireSession -> {
                if (expireSession) {
                    onExpireSession();
                }
            });

            // 통신 실패
            viewModel.getFailNetwork().observe(getViewLifecycleOwner(), failNetwork -> {
                if (failNetwork) {
                    onNetworkConnectFail();
                }
            });

            // 앱 버전
            viewModel.getAppVersionText().observe(getViewLifecycleOwner(), text -> {
                TextView tvAppVersion = view.findViewById(R.id.tv_fragment_setting_version_text);
                tvAppVersion.setText(text);
            });

            // 고씽 혜택 알림
            viewModel.getActiveBenefit().observe(getViewLifecycleOwner(), active -> benefitSwitch.setChecked(active));

            // 야간 알림
            viewModel.getActiveNight().observe(getViewLifecycleOwner(), active -> {
                nightSwitch.setChecked(active);
            });

        }

    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(View view) {
        TextView appVersion = view.findViewById(R.id.tv_fragment_setting_app_version);
        appVersion.setText(getString(R.string.fragment_setting_app_version, BuildConfig.VERSION_NAME));

        benefitSwitch = view.findViewById(R.id.sw_fragment_setting_benefit);
        nightSwitch = view.findViewById(R.id.sw_fragment_setting_night);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.postSetting();
    }

    @Override
    public void initListener() {

        CommonTitleBar titleBar = view.findViewById(R.id.common_notice_title);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        View termsView = view.findViewById(R.id.cl_fragment_setting_terms_group);
        RxView.clicks(termsView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(getContext(), SettingDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                });

        // 혜택 알림
        benefitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (viewModel != null) {
                if (isChecked) {
                    viewModel.postChangeBenefit("Y");
                } else {
                    viewModel.postChangeBenefit("N");
                }
            }
        });

        // 야간 알림
        nightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (viewModel != null) {
                if (isChecked) {
                    viewModel.postChangeNight("Y");
                } else {
                    viewModel.postChangeNight("N");
                }
            }
        });

    }

}
