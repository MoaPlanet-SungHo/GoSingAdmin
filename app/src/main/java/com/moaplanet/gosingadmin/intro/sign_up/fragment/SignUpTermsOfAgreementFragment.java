package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.intro.sign_up.model.SignUpViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SignUpTermsOfAgreementFragment extends BaseFragment {

    private Button btnDone;
    private SignUpViewModel signUpViewModel;
    private CheckBox cbAll, cbTermsOfUse, cbBanking, cbPersonalInfo, cbThirdParty, cbAge, cbEvent;
    private List<CheckBox> cbList;
    private SimpleDateFormat simpleDateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initDefault();
        return view;
    }

    private void initDefault() {
        if (getActivity() != null) {
            signUpViewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
        }
        simpleDateFormat = new SimpleDateFormat(getString(R.string.common_date_format),
                Locale.getDefault());
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_sign_up_terms_of_agreement;
    }

    @Override
    public void initView(View view) {
        btnDone = view.findViewById(R.id.btn_terms_of_agreement_done);

        cbAll = view.findViewById(R.id.cb_sign_up_terms_of_agreement_all_check);
        cbTermsOfUse = view.findViewById(R.id.cb_sign_up_terms_of_agreement_use_check);
        cbBanking = view.findViewById(R.id.cb_terms_of_agreement_banking_check);
        cbPersonalInfo = view.findViewById(R.id.cb_terms_of_agreement_personal_info_check);
        cbThirdParty = view.findViewById(R.id.cb_terms_of_agreement_third_party_check);
        cbAge = view.findViewById(R.id.cb_terms_of_agreement_age_check);
        cbEvent = view.findViewById(R.id.cb_terms_of_agreement_event_check);

        cbList = new ArrayList<>();
        cbList.add(cbTermsOfUse);
        cbList.add(cbBanking);
        cbList.add(cbPersonalInfo);
        cbList.add(cbThirdParty);
        cbList.add(cbAge);
    }

    @Override
    public void initListener() {
        btnDone.setOnClickListener(view -> {
            signUpViewModel.setCheckEventPush(cbEvent.isChecked() ? "Y" : "N");
            onMoveNavigation(R.id.action_fragment_create_account);
//                onMoveNavigation(R.id.action_fragment_sign_up_self_certification));

        });

        cbAll.setOnClickListener(onCheckBoxClickListener);
        cbTermsOfUse.setOnClickListener(onCheckBoxClickListener);
        cbBanking.setOnClickListener(onCheckBoxClickListener);
        cbPersonalInfo.setOnClickListener(onCheckBoxClickListener);
        cbThirdParty.setOnClickListener(onCheckBoxClickListener);
        cbAge.setOnClickListener(onCheckBoxClickListener);
        cbEvent.setOnClickListener(onCheckBoxClickListener);

    }

    private void allCheck(boolean checkType) {
        cbTermsOfUse.setChecked(checkType);
        cbBanking.setChecked(checkType);
        cbPersonalInfo.setChecked(checkType);
        cbThirdParty.setChecked(checkType);
        cbAge.setChecked(checkType);
        cbEvent.setChecked(checkType);
    }

    private View.OnClickListener onCheckBoxClickListener = view -> {
        if (view.getId() == cbAll.getId()) {
            allCheck(cbAll.isChecked());
            agreementEventToast();
        } else if (view.getId() == cbEvent.getId()) {
            agreementEventToast();
        }
        checkActivation();
    };

    private void agreementEventToast() {
        String msg;
        if (cbEvent.isChecked()) {
            msg = simpleDateFormat.format(System.currentTimeMillis()) +
                    getString(R.string.fragment_sign_up_terms_of_agreement_event_check);
        } else {
            msg = simpleDateFormat.format(System.currentTimeMillis()) +
                    getString(R.string.fragment_sign_up_terms_of_agreement_event_uncheck);
        }
        Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 전체 체크박스 및 버튼
     * 활성화 및 비활성화 컨트롤
     */
    private void checkActivation() {
        boolean activationType = false;
        for (CheckBox checkBox : cbList) {
            if (checkBox.isChecked()) {
                activationType = true;
            } else {
                activationType = false;
                break;
            }
        }
        btnDone.setEnabled(activationType);
        cbAll.setChecked(activationType);
    }
}
