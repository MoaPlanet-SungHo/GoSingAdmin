package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.view.View;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputBaseFragment;
import com.moaplanet.gosingadmin.common.manager.AuthManager;

/**
 * 출금 계좌 등록 결제 비밀번호 입력
 */
public class AccountResisterInputPassword extends PasswordInputBaseFragment {
    @Override
    public void checkPasswordViewType() {
        tvPasswordInputTitle.setText(R.string.fragment_non_member_password_title);
        tvPasswordError.setText(R.string.fragment_non_member_password_error);
        tvExplanation.setText(R.string.fragment_non_member_password_find_pw);
    }

    @Override
    public void checkPassword(String password) {
        AuthManager authManager = new AuthManager();
        authManager.setOnAuthCallback(new AuthManager.onAuthCallback() {
            @Override
            public void onSuccess() {
                tvPasswordError.setVisibility(View.GONE);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFail() {
                tvPasswordError.setText("결제 비밀번호가 일치하지 않습니다.");
                tvPasswordError.setVisibility(View.VISIBLE);
            }
        });
        authManager.onCheckPin(authManager.KEY_ALIAS_MOBILE_PIN, password);
    }

    @Override
    public String titleText() {
        return null;
    }
}
