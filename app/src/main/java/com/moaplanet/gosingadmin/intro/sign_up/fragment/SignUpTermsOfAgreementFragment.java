package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * 회원가입 전 약관 동의 화면
 */
public class SignUpTermsOfAgreementFragment extends BaseFragment {

    // 회원가입 공통 뷰 모델
    private SignUpViewModel signUpViewModel;

    // 다음 스탭으로 이동하기 위한 버튼
    private Button mBtnDone;
    // 전체 동의, 고싱 혜택 알림 체크박스
    private CheckBox mCbAll, mCbEvent;

    // 필수 체크 박스 리스트
    private List<CheckBox> mRequiredCbList;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            if (signUpViewModel == null) {
                signUpViewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
            }
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_sign_up_terms_of_agreement;
    }

    @Override
    public void initView(View view) {

        mBtnDone = view.findViewById(R.id.btn_fragment_terms_of_agreement_done);

        mCbAll = view.findViewById(R.id.cb_sign_up_terms_of_agreement_all_check);
        CheckBox cbTermsOfUse = view.findViewById(R.id.cb_sign_up_terms_of_agreement_use_check);
        CheckBox cbBanking = view.findViewById(R.id.cb_terms_of_agreement_banking_check);
        CheckBox cbPersonalInfo = view.findViewById(R.id.cb_terms_of_agreement_personal_info_check);
        CheckBox cbThirdParty = view.findViewById(R.id.cb_terms_of_agreement_third_party_check);
        CheckBox cbAge = view.findViewById(R.id.cb_terms_of_agreement_age_check);
        mCbEvent = view.findViewById(R.id.cb_terms_of_agreement_event_check);

        mRequiredCbList = new ArrayList<>();
        mRequiredCbList.add(cbTermsOfUse);
        mRequiredCbList.add(cbBanking);
        mRequiredCbList.add(cbPersonalInfo);
        mRequiredCbList.add(cbThirdParty);
        mRequiredCbList.add(cbAge);
    }

    @Override
    public void initListener() {

        // 계정 입력 하는 화면으로 이동
        RxView.clicks(mBtnDone)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (signUpViewModel != null) {
                        signUpViewModel.setCheckEventPush(mCbEvent.isChecked());
                    }
                    onMoveNavigation(R.id.action_fragment_create_account);
                });

        // 체크박스에 리스너 연결
        mCbAll.setOnClickListener(onCheckBoxClickListener);
        for (CheckBox checkBox : mRequiredCbList) {
            checkBox.setOnClickListener(onCheckBoxClickListener);
        }
        mCbEvent.setOnClickListener(onCheckBoxClickListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        checkActivation();
    }

    /**
     * 전체 체크 및 해제
     *
     * @param checkType 체크 타입 --> true : 체크 | false : 체크해제
     */
    private void allCheck(boolean checkType) {
        for (CheckBox checkBox : mRequiredCbList) {
            checkBox.setChecked(checkType);
        }
        mCbEvent.setChecked(checkType);
    }

    /**
     * 체크 박스들의 클릭 이벤트
     */
    private View.OnClickListener onCheckBoxClickListener = view -> {
        if (view.getId() == mCbAll.getId()) {
            allCheck(mCbAll.isChecked());
            agreementEventToast();
        } else if (view.getId() == mCbEvent.getId()) {
            agreementEventToast();
        }
        checkActivation();
    };

    /**
     * 혜택 알림 동의 및 비동의 시 토스트 메시지 띄움
     */
    private void agreementEventToast() {
        String msg;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y.M.d", Locale.KOREA);
        if (mCbEvent.isChecked()) {
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
        for (CheckBox checkBox : mRequiredCbList) {
            if (checkBox.isChecked()) {
                activationType = true;
            } else {
                activationType = false;
                break;
            }
        }
        mBtnDone.setEnabled(activationType);
        mCbAll.setChecked(activationType);
    }
}
