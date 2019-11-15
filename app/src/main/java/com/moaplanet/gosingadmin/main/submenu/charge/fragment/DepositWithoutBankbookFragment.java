package com.moaplanet.gosingadmin.main.submenu.charge.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResSearchVirtualAccountDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.ChargeViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.DepositWithoutBankbookViewModel;

import static android.content.Context.CLIPBOARD_SERVICE;

public class DepositWithoutBankbookFragment extends BaseFragment {

    // 뷰모델
    // 무통장 입금 화면의 뷰모델
    private DepositWithoutBankbookViewModel mViewModel;
    // 충전 화면 activity 와 연결된 뷰 모델
    private ChargeViewModel mChargeViewModel;

    // 로딩바
    private ProgressBar mLoadingBar;

    // 가상계좌 조회 여부 --> true : 조회하기 | false : 조회 안함
    private boolean mSearchingVirtualAccount = true;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mChargeViewModel = ViewModelProviders.of(getActivity()).get(ChargeViewModel.class);
        }
        mViewModel = ViewModelProviders.of(this).get(DepositWithoutBankbookViewModel.class);
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_deposit_without_bankbook;
    }

    @Override
    public void initView(View view) {
        mLoadingBar = view.findViewById(R.id.pb_fragment_deposit_without_bankbook_loading);
    }

    @Override
    public void initListener() {
        initObserve();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSearchingVirtualAccount) {
            // 계좌 정보 조회 api 호출
            mViewModel.onSearchVirtualAccount();
        } else {
            mLoadingBar.setVisibility(View.GONE);
        }

    }

    private void initObserve() {

        // 계좌 정보 초기화
        mViewModel.getVirtualAccountDto().observe(this, dto -> {
            mSearchingVirtualAccount = false;
            if (dto == null) {
                onRegisterVirtualAccountDialogShow();
            } else {
                initVirtualAccount(dto);
            }
        });

        // 로딩 유무
        mViewModel.getIsLoading().observe(this, isLoading -> {
            onInitLoading(mLoadingBar, isLoading);
            mChargeViewModel.setIsLoading(isLoading);
        });

        // 세션 없을경우 처리
        mViewModel.getSession().observe(this, isSession -> {
            if (!isSession) {
                onNotSession();
            }
        });

        // 통신 실패
        mViewModel.getIsApiSuccess().observe(this, isSuccess -> {
            if (!isSuccess) {
                onNetworkConnectFail();
            }
        });
    }

    @Override
    protected void onStartLoading(View viewLoading) {
        super.onStartLoading(viewLoading);
    }

    @Override
    protected void onStopLoading(View viewLoading) {
        super.onStopLoading(viewLoading);
    }

    /**
     * 가상 계좌 발급 다이얼로그 표시
     */
    private void onRegisterVirtualAccountDialogShow() {
        NoTitleDialog noTitleDialog = new NoTitleDialog();
        noTitleDialog.setUseYesOrNo(true);
        noTitleDialog.setContent(R.string.fragment_deposit_without_bankbook_register_virtual_account);
        noTitleDialog.show(getChildFragmentManager(), "가상계좌발급 dialog");
        noTitleDialog.onNoOnClickListener(view -> {
            noTitleDialog.dismiss();

            if (getParentFragment() != null && getParentFragment() instanceof ChargeFragment) {
                mSearchingVirtualAccount = true;
                ChargeFragment chargeFragment = (ChargeFragment) getParentFragment();
                chargeFragment.getVpCharge().setCurrentItem(0);
                mLoadingBar.setVisibility(View.VISIBLE);
            }
        });

        noTitleDialog.onDoneOnCliListener(view -> {
            noTitleDialog.dismiss();
            mViewModel.onRegisterVirtualAccount();
        });
    }

    /**
     * 계좌 정보 초기화
     */
    private void initVirtualAccount(ResSearchVirtualAccountDto.VirtualAccountDto dto) {
        // 은행 이름
        TextView bankName =
                view.findViewById(R.id.tv_fragment_deposit_without_bankbook_bank_name);
        bankName.setText(dto.getBankName());

        // 계좌 번호
        TextView bankAccountNumber =
                view.findViewById(R.id.tv_fragment_deposit_without_bankbook_bank_number);
        bankAccountNumber.setText(dto.getVirtaulAccountNumber());

        // 계좌번호 복사
        LinearLayout llCopy = view.findViewById(R.id.ll_fragment_deposit_without_bankbook_copy);
        llCopy.setOnClickListener(viewCopy -> {
            ClipboardManager clipboardManager =
                    (ClipboardManager) view.getContext().getSystemService(CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText(
                        "계좌번호",
                        dto.getVirtaulAccountNumber());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(view.getContext(),
                        "복사가 완료 되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 확인 버튼
        Button btnDone = view.findViewById(R.id.btn_fragment_deposit_without_bankbook_confirm);
        btnDone.setOnClickListener(view -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }
}
