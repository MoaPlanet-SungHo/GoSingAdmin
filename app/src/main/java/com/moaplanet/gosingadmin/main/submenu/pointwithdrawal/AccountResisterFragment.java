package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

/**
 * 계좌등록/변경 Fragment
 */
public class AccountResisterFragment extends BaseFragment {

    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER";
    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE";

    private ConstraintLayout clAccountInfoGroup;
    private TextView tvGuideText;
    private Button btnAccountRegister;

    // 사용자가 선택한 은행
    private TextView mTvSelectBank;

    @Override
    public int layoutRes() {
        return R.layout.fragment_account_register;
    }

    @Override
    public void initView(View view) {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_account_register);
        commonTitle.setBackButtonClickListener(view1 -> getActivity().finish());
        clAccountInfoGroup = view.findViewById(R.id.cl_fragment_account_register_account_info_group);
        tvGuideText = view.findViewById(R.id.tv_account_register_guide_text);
        btnAccountRegister = view.findViewById(R.id.btn_fragment_account_register);

        String fromView = "";
        if (getArguments() != null) {
            fromView = getArguments().getString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW);
        }

        if (fromView != null && fromView.equals(BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE)) {
            commonTitle.setTitle("계좌 변경");
            clAccountInfoGroup.setVisibility(View.VISIBLE);
            tvGuideText.setText("새로운 인출 계좌를 등록하세요.");
            btnAccountRegister.setText("변경");
        } else {
            commonTitle.setTitle("계좌 등록");
            clAccountInfoGroup.setVisibility(View.GONE);
            tvGuideText.setText("인출 계좌를 등록하세요.");
            btnAccountRegister.setText("등록");
        }

        mTvSelectBank = view.findViewById(R.id.tv_fragment_account_register_select_bank);

    }

    @Override
    public void initListener() {

        // 등록 화면
        btnAccountRegister.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW,
                    PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER);
            Navigation.findNavController(view).navigate(R.id.action_fragment_password_input, bundle);
        });

        // 은행 선택
        mTvSelectBank.setOnClickListener(view1 -> {

        });

    }
}
