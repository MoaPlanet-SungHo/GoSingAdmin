package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputBaseFragment;
import com.moaplanet.gosingadmin.common.manager.AuthManager;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;

public class NonMemberSavePassword extends PasswordInputBaseFragment {

    private NonMemberSavePointViewModel mViewModel;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(NonMemberSavePointViewModel.class);
        }
    }

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
                mViewModel.setIsPinCheck(true);
                onBackNavigation();
            }

            @Override
            public void onFail() {
                tvPasswordError.setText("결제 비밀번호가 일치하지 않습니다.");
                tvPasswordError.setVisibility(View.VISIBLE);
                mViewModel.setIsPinCheck(true);
            }
        });
        authManager.onCheckPin(authManager.KEY_ALIAS_MOBILE_PIN, password);

    }

    @Override
    public String titleText() {
        return getString(R.string.fragment_non_member_password_title_bar);
    }
}
