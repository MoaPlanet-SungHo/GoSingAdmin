package com.moaplanet.gosingadmin.main.submenu.ad.fragment;

import android.view.View;
import android.widget.Button;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

public class AdPaymentFragment extends BaseFragment {

    private Button btnProductPayment;

    @Override
    public int layoutRes() {
        return R.layout.fragment_ad_payment;
    }

    @Override
    public void initView(View view) {
        btnProductPayment = view.findViewById(R.id.btn_ad_payment_product_payment);
    }

    @Override
    public void initListener() {
        btnProductPayment.setOnClickListener(view -> onMoveNavigation(R.id.action_fragment_ad_input_pw));
    }
}
