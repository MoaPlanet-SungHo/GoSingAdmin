package com.moaplanet.gosingadmin.intro.sign_up;

import android.view.View;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

public class SignUpCompleteFragment extends BaseFragment {

    private CommonTitleBar commonTitleBar;

    @Override
    public int layoutRes() {
        return R.layout.fragment_sign_up_complete;
    }

    @Override
    public void initView(View view) {
        commonTitleBar = view.findViewById(R.id.common_sign_up_complete_title_bar);
        commonTitleBar.onHideBack();
    }

    @Override
    public void initListener() {

    }
}
