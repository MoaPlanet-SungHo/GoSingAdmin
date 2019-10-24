package com.moaplanet.gosingadmin.intro.sign_up;

import android.view.View;
import android.widget.Button;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

public class SignUpTermsOfAgreementFragment extends BaseFragment {

    private Button btnDone;

    @Override
    public int layoutRes() {
        return R.layout.fragment_sign_up_terms_of_agreement;
    }

    @Override
    public void initView(View view) {
        btnDone = view.findViewById(R.id.btn_terms_of_agreement_done);
    }

    @Override
    public void initListener() {
        btnDone.setOnClickListener(view ->
                onMoveNavigation(R.id.action_fragment_create_account));
//                onMoveNavigation(R.id.action_fragment_sign_up_self_certification));
    }
}
