package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

/**
 * 충전완료 화면 Fragment
 */
public class ChargeCompleteFragment extends BaseFragment {

    @Override
    public int layoutRes() {
        return R.layout.fragment_charge_complete;
    }

    @Override
    public void initView(View view) {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_charge_complete);
        commonTitle.setBackButtonClickListener(view1 -> Navigation.findNavController(view).popBackStack());

        Button btnChargeConfrim = view.findViewById(R.id.btn_fragment_charge_complete_confirm);
        btnChargeConfrim.setOnClickListener(view1 -> getActivity().finish());

    }

    @Override
    public void initListener() {

    }
}
