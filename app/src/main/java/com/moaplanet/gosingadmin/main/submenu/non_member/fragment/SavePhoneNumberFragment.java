package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

public class SavePhoneNumberFragment extends BaseFragment {

    private EditText etPhoneNumber;
    private Button btnDone;

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_phone_number;
    }

    @Override
    public void initView(View view) {
        etPhoneNumber = view.findViewById(R.id.et_save_phone_number_input_number);
        btnDone = view.findViewById(R.id.btn_save_phone_number_done);
    }

    @Override
    public void initListener() {
        etPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        btnDone.setOnClickListener(view -> onMoveNavigation(R.id.action_fragment_save_point));
    }
}
