package com.moaplanet.gosingadmin.intro.sign_up;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.constants.GoSingConstants;

public class CreateAccountFragment extends BaseFragment {

    private Button checkEmail;
    private NoTitleDialog noTitleDialog;
    private Button nextStep;

    @Override
    public int layoutRes() {
        return R.layout.fragment_create_account;
    }

    @Override
    public void initView(View view) {
        checkEmail = view.findViewById(R.id.btn_create_account_email_check);
        nextStep = view.findViewById(R.id.btn_create_account_done);
    }

    @Override
    public void initListener() {
        checkEmail.setOnClickListener(view -> {
            if (getFragmentManager() != null) {
                noTitleDialog.setContent(R.string.fragment_create_account_exist_email);
                noTitleDialog.show(getFragmentManager(), "existDialog");
                noTitleDialog.onDoneOnCliListener(view1 -> noTitleDialog.dismiss());
            }
        });

        nextStep.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(
                    GoSingConstants.BUNDLE_KEY_TYPE_PASSWORD,
                    GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD
            );

            Navigation.findNavController(this.view).navigate(
                    R.id.action_fragment_sign_up_input_password,
                    bundle
            );
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noTitleDialog = new NoTitleDialog();
    }
}
