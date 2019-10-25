package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

public class CardInfoRegisterFragment extends BaseFragment {
    @Override
    public int layoutRes() {
        return R.layout.fragment_card_info_register;
    }

    @Override
    public void initView(View view) {
        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_card_info_register);
        commonTitle.setBackButtonClickListener(view1 -> Navigation.findNavController(view).popBackStack());
        commonTitle.setTitle(getString(R.string.fragment_card_info_register_title));
        Button button = view.findViewById(R.id.btn_fragment_card_info_register);
        button.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW, PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_CARD_REGISTER);
            Navigation.findNavController(view).navigate(R.id.action_fragment_password_input, bundle);
        });
    }

    @Override
    public void initListener() {

    }
}
