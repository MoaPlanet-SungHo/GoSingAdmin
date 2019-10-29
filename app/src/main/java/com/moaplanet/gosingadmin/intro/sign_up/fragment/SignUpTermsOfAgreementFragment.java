package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.intro.sign_up.model.SignUpViewModel;

public class SignUpTermsOfAgreementFragment extends BaseFragment {

    private Button btnDone;
    private CheckBox cbGoSingEvent;
    private SignUpViewModel signUpViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getActivity() != null) {
            signUpViewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
        }
        return view;
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_sign_up_terms_of_agreement;
    }

    @Override
    public void initView(View view) {
        cbGoSingEvent = view.findViewById(R.id.cb_terms_of_agreement_event_check);
        btnDone = view.findViewById(R.id.btn_terms_of_agreement_done);
    }

    @Override
    public void initListener() {
        btnDone.setOnClickListener(view -> {
            signUpViewModel.setCheckEventPush(cbGoSingEvent.isChecked() ? "Y" : "N");
            onMoveNavigation(R.id.action_fragment_create_account);
//                onMoveNavigation(R.id.action_fragment_sign_up_self_certification));

        });
    }
}
