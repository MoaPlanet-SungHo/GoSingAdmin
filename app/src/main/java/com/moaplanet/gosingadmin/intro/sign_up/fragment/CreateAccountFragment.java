package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.Objects;

/**
 * 회원가입 아이디 패스워드 일벽 화면
 */
public class CreateAccountFragment extends BaseFragment {

    private Button checkEmail;
    private NoTitleDialog noTitleDialog;
    private Button nextStep;
    private EditText etEmail, etPw, etPwCheck;
    private SignUpViewModel signUpViewModel;

    private TextView tvEmailErr, tvPwErr, tvPwCheckErr;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            signUpViewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_create_account;
    }

    @Override
    public void initView(View view) {
        checkEmail = view.findViewById(R.id.btn_create_account_email_check);
        nextStep = view.findViewById(R.id.btn_create_account_done);
        etEmail = view.findViewById(R.id.et_create_account_email);
        etPw = view.findViewById(R.id.et_create_account_pw);
        etPwCheck = view.findViewById(R.id.et_create_account_pw_check);

        tvEmailErr = view.findViewById(R.id.tv_create_account_email_err);
        tvPwErr = view.findViewById(R.id.tv_create_account_pw_err);
        tvPwCheckErr = view.findViewById(R.id.tv_create_account_pw_check_err);

        tvEmailErr.setVisibility(View.GONE);
        tvPwErr.setVisibility(View.GONE);
        tvPwCheckErr.setVisibility(View.GONE);

        etEmail.setFilters(new InputFilter[]{
                StringUtil.notEmptyFilter()
        });
        etPw.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20),
                StringUtil.notEmptyFilter()
        });
        etPwCheck.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20),
                StringUtil.notEmptyFilter()
        });
    }

    @Override
    public void initListener() {

        CommonTitleBar commonTitleBar = view.findViewById(R.id.common_create_account_title_bar);
        commonTitleBar.setBackButtonClickListener(view -> onBackNavigation());

        checkEmail.setOnClickListener(view -> {
            if (getFragmentManager() != null) {
                noTitleDialog.setContent(R.string.fragment_create_account_exist_email);
                noTitleDialog.show(getFragmentManager(), "existDialog");
                noTitleDialog.onDoneOnCliListener(view1 -> noTitleDialog.dismiss());
            }
        });

        nextStep.setOnClickListener(view -> {

            if (checkInputData()) {
                signUpViewModel.setEmail(etEmail.getText().toString());
                signUpViewModel.setPw(etPw.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putString(
                        GoSingConstants.BUNDLE_KEY_TYPE_PASSWORD,
                        GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD
                );

//                Navigation.findNavController(this.view).navigate(
//                        R.id.action_fragment_sign_up_input_password,
//                        bundle
//                );

                Navigation.findNavController(this.view).navigate(
                        R.id.action_fragment_sign_up_complete,
                        bundle
                );
            }

        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (StringUtil.isEmail(editable.toString())) {
                    tvEmailErr.setVisibility(View.GONE);
                } else {
                    tvEmailErr.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean checkInputData() {
        if (etEmail.getText().toString().equals("") || tvEmailErr.getVisibility() == View.VISIBLE) {
            tvEmailErr.setVisibility(View.VISIBLE);
            return false;
        }

        String inputPw = etPw.getText().toString();
        if (!inputPw.equals("") && StringUtil.isPw(inputPw) && inputPw.length() >= 8) {
            tvPwErr.setVisibility(View.GONE);
        } else {
            tvPwErr.setVisibility(View.VISIBLE);
            return false;
        }
        String inputPwCheck = etPwCheck.getText().toString();
        if (inputPw.equals(inputPwCheck)) {
            tvPwCheckErr.setVisibility(View.GONE);
        } else {
            tvPwCheckErr.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    public void onPause() {
        view.clearFocus();
        //키보드 내리기
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noTitleDialog = new NoTitleDialog();
    }
}
