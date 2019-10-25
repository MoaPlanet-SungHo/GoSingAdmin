package com.moaplanet.gosingadmin.common.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

/**
 * 결제 비밀번호 입력화면
 */
public class PasswordInputFragment extends PasswordInputBaseFragment {

    public static String BUNDLE_REQUEST_FROM_VIEW_CARD_REGISTER = "BUNDLE_REQUEST_FROM_VIEW_CARD_REGISTER";
    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER";
    public static String BUNDLE_REQUEST_FROM_VIEW_PAYMENT = "BUNDLE_REQUEST_FROM_VIEW_PAYMENT";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void checkPasswordViewType() {
        initCreatePasswordLayout();
    }

    @Override
    public void checkPassword(String password) {

        if (getArguments() != null) {
            String fromView = getArguments().getString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW);

            if (fromView.equals(PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER)) {
                //계좌 등록 비밀번호 일시
                Navigation.findNavController(view).navigate(R.id.action_fragment_withdrawal);

            } else if (fromView.equals(PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_CARD_REGISTER)) {
                //카드 등록 비밀번호 일시
                getActivity().finish();
            } else if (fromView.equals(PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_PAYMENT)) {
                //결제 비밀번호 작성 완료시
                getActivity().finish();
            }
        }
    }

    @Override
    public String titleText() {
        if (getArguments() != null) {
            String fromView = getArguments().getString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW);
            if (fromView.equals(PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER)) {
                return getString(R.string.fragment_password_input_title_account_register);
            } else if (fromView.equals(PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_PAYMENT)) {
                return getString(R.string.fragment_payment_title);
            } else if (fromView.equals(PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_CARD_REGISTER)) {
                return getString(R.string.fragment_card_info_register_title);
            } else {
                return "";
            }
        }
        return "";
    }

    private void initCreatePasswordLayout() {
        tvPasswordInputTitle.setText(
                getString(R.string.fragment_password_input_title)
        );
        tvExplanation.setText(getString(R.string.fragment_password_input_find));
    }
}
