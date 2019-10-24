package com.moaplanet.gosingadmin.main.submenu.ad.fragment;

import android.view.View;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

public class AdSelectFragment extends BaseFragment {

    private View thirtyDayProduct, fifteenDayProduct;

    @Override
    public int layoutRes() {
        return R.layout.fragment_ad_select;
    }

    @Override
    public void initView(View view) {
        thirtyDayProduct = view.findViewById(R.id.cl_ad_select_thirty_day_product_group);
        fifteenDayProduct = view.findViewById(R.id.cl_ad_select_fifteen_day_product_group);
    }

    @Override
    public void initListener() {
        thirtyDayProduct.setOnClickListener(view -> onMoveNavigation(R.id.action_fragment_ad_payment));

        fifteenDayProduct.setOnClickListener(view -> onMoveNavigation(R.id.action_fragment_ad_payment));
    }
}
