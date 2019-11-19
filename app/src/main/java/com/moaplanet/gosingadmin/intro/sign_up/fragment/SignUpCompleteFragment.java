package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;

public class SignUpCompleteFragment extends BaseFragment {

    private Button btnDone, btnCodeCheck;
    private View errMsg;
    private EditText etCode;
    private SignUpViewModel signUpViewModel;
    private String code = "";

    @Override
    public int layoutRes() {
        return R.layout.fragment_sign_up_complete;
    }

    @Override
    public void initView(View view) {
        CommonTitleBar commonTitleBar = view.findViewById(R.id.common_sign_up_complete_title_bar);
        commonTitleBar.onHideBack();

        btnDone = view.findViewById(R.id.btn_sign_up_completed_done);
        errMsg = view.findViewById(R.id.tv_sign_up_sales_code_err_msg);
        errMsg.setVisibility(View.GONE);
        btnCodeCheck = view.findViewById(R.id.btn_sign_up_completed_code_check);
        etCode = view.findViewById(R.id.et_sign_up_completed_input_code);

        etCode.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(13),
                StringUtil.notEmptyFilter(),
                StringUtil.notKoreanFilter()
        });
    }

    @Override
    public void initListener() {
        btnDone.setOnClickListener(v -> {
            if (getActivity() != null) {
                code = etCode.getText().toString();//존재 확인시 삭제 가능
                signUpViewModel.setSalesCode(code);
            }
        });
        btnCodeCheck.setOnClickListener(view -> code = btnCodeCheck.getText().toString());//btnCodeCheck 이 값을 왜 가져와!!
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //todo 영업자 방침 정해짐에 따라 조건 결정
//                if (editable.length() == 6) {
//                    etCode.setCompoundDrawablesWithIntrinsicBounds(
//                            0, 0, R.drawable.ic_checkbox_press, 0);
//                    btnCodeCheck.setEnabled(true);
//                } else {
//                    btnCodeCheck.setEnabled(false);
//                    etCode.setCompoundDrawablesWithIntrinsicBounds(
//                            0, 0, R.drawable.ic_checkbox_nor, 0);
//                }
            }
        });
    }

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
}
