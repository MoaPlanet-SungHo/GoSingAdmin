package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

/**
 * 출금 가능 금액 Fragment
 */
public class PointwithdrawalToolTipFragment extends BaseFragment {

    private Button confirm;

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_withdrawal_tool_tip;
    }

    @Override
    public void initView(View view) {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_point_withdrawal_tool_tip);
        commonTitle.setBackButtonClickListener(view1 -> Navigation.findNavController(view).popBackStack());
        commonTitle.setTitle("출금 가능 금액");
        confirm = view.findViewById(R.id.btn_fragment_point_withdrawal_tool_tip_confirm);

    }

    @Override
    public void initListener() {
        confirm.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
    }
}
