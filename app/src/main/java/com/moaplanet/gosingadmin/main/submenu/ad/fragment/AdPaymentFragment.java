package com.moaplanet.gosingadmin.main.submenu.ad.fragment;

import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class AdPaymentFragment extends BaseFragment {

    @Override
    public int layoutRes() {
        return R.layout.fragment_ad_payment;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initListener() {
        Button btnProductPayment = view.findViewById(R.id.btn_ad_payment_product_payment);
        RxView.clicks(btnProductPayment)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.action_fragment_ad_input_pw));
    }
}
