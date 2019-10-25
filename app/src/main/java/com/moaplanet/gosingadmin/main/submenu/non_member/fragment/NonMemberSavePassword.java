package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputBaseFragment;

public class NonMemberSavePassword extends PasswordInputBaseFragment {
    @Override
    public void checkPasswordViewType() {
        tvPasswordInputTitle.setText(R.string.fragment_non_member_password_title);
        tvPasswordError.setText(R.string.fragment_non_member_password_error);
        tvExplanation.setText(R.string.fragment_non_member_password_find_pw);
    }

    @Override
    public void checkPassword(String password) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public String titleText() {
        return getString(R.string.fragment_non_member_password_title_bar);
    }
}
