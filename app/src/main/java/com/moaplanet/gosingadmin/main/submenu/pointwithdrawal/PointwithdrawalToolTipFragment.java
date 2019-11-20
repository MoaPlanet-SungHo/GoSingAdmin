package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * 출금 가능 금액 Fragment
 */
public class PointwithdrawalToolTipFragment extends BaseFragment {

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_withdrawal_tool_tip;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initListener() {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_point_withdrawal_tool_tip);
        RxView.clicks(commonTitle.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        Button confirm = view.findViewById(R.id.btn_fragment_point_withdrawal_tool_tip_confirm);
        RxView.clicks(confirm)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());
    }
}
