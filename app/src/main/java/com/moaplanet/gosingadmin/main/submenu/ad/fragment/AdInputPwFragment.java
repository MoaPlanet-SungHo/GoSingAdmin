package com.moaplanet.gosingadmin.main.submenu.ad.fragment;

import android.widget.Toast;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputBaseFragment;

public class AdInputPwFragment extends PasswordInputBaseFragment {
    @Override
    public void checkPasswordViewType() {
        tvPasswordInputTitle.setText(getString(R.string.fragment_ad_input_pw_use_input_pw));
        tvExplanation.setText(getString(R.string.fragment_ad_input_pw_find_pw));

        //TODO 기획서에 없음
        //        tvPasswordError.setText(getString());
    }

    @Override
    public void checkPassword(String password) {
        if (getActivity() != null) {
            Toast.makeText(
                    view.getContext(),
                    getString(R.string.fragment_ad_input_pw_payment_success),
                    Toast.LENGTH_SHORT)
                    .show();

            getActivity().finish();
        }
    }

    @Override
    public String titleText() {
        return getString(R.string.fragment_ad_input_pw_title);
    }
}
