package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputBaseFragment;
import com.moaplanet.gosingadmin.common.model.viewmodel.CreatePinViewModel;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.sign_up.activity.SignUpActivity;
import com.orhanobut.logger.Logger;


public class SignUpInputPasswordFragment extends PasswordInputBaseFragment {

    private CreatePinViewModel mVieModel;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            if (mVieModel == null) {
                mVieModel = ViewModelProviders.of(getActivity()).get(CreatePinViewModel.class);
            }
        }
    }

    @Override
    public void checkPasswordViewType() {
        if (getArguments() == null) {
            isFirstStack = true;
            viewType = GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD;
        } else {
            viewType = getArguments().getString(GoSingConstants.BUNDLE_KEY_TYPE_PASSWORD);
        }


        if (viewType == null) {
            Logger.e("viewType 값이 null 입니다.");
        } else if (viewType.equals(GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD)) {
            initCreatePasswordLayout();
        } else {
            initCheckPasswordLayout();
        }
    }

    @Override
    public void checkPassword(String password) {
        if (viewType.equals(GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD)) {
            moveCheckPasswordFragment(password);
        } else {
            if (getArguments() != null) {
                String beforePw = getArguments()
                        .getString(GoSingConstants.BUNDLE_KEY_BEFORE_INPUT_PASSWORD);

                if (beforePw == null) {
                    Logger.e("이전에 입력한 비밀번호가 null 입니다.");
                } else if (beforePw.equals(password)) {
                    mVieModel.setPinPw(password);
                    tvPasswordError.setVisibility(View.INVISIBLE);
                    if (getActivity() instanceof SignUpActivity) {
                        //패스워드 확인 후 화면 넘기기 처리
                        onMoveNavigation(R.id.action_fragment_sign_up_complete);
                    }
                } else {
                    tvPasswordError.setText("결제 비밀번호가 일치하지 않습니다.");
                    tvPasswordError.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void initCreatePasswordLayout() {
        tvPasswordInputTitle.setText(
                getString(R.string.fragment_password_input_new_password_title)
        );
        tvExplanation.setText(getString(R.string.fragment_password_input_new_password_explanation));
    }

    private void initCheckPasswordLayout() {
        passwordPinView.setText("");
        tvPasswordInputTitle.setText("결제 비밀번호 확인");
        tvExplanation.setText(getString(R.string.fragment_password_input_again_payment_password));
    }

    private void moveCheckPasswordFragment(String password) {

        Bundle bundle = new Bundle();
        bundle.putString(
                GoSingConstants.BUNDLE_KEY_TYPE_PASSWORD,
                GoSingConstants.BUNDLE_VALUE_CHECK_PASSWORD
        );
        bundle.putString(GoSingConstants.BUNDLE_KEY_BEFORE_INPUT_PASSWORD, password);

        Navigation.findNavController(this.view).navigate(
                R.id.action_fragment_sign_up_input_password_check,
                bundle
        );
    }

    @Override
    public String titleText() {
        return getString(R.string.sign_up_title);
    }
}
