package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseActivityViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.ChargeActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResNonMemberPointSaveInitDTO;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResNonMemberSavePointDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.text.DecimalFormat;

import retrofit2.Call;

public class SavePointFragment extends BaseFragment {

    // 뷰모델
    private NonMemberSavePointViewModel mViewModel;
    private BaseActivityViewModel mActivityViewModel;

    private EditText etInputPoint;
    private DecimalFormat decimalFormat;
    private Button btnSaving;

    private TextView tvSaveMaxPoint;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(NonMemberSavePointViewModel.class);
            mActivityViewModel = ViewModelProviders.of(getActivity()).get(BaseActivityViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_point;
    }

    @Override
    public void initView(View view) {

        mActivityViewModel.setmIsLoading(true);
        tvSaveMaxPoint = view.findViewById(R.id.tv_save_point_max_save);
        initObserve();
        btnSaving = view.findViewById(R.id.btn_save_point_saving);
        etInputPoint = view.findViewById(R.id.et_save_point_input_point);

        // 포인트 표시
        TextView tvPoint = view.findViewById(R.id.tv_fragment_save_point);

        if (mViewModel != null) {
            tvPoint.setText(getString(R.string.fragment_payment_money_won,
                    mViewModel.getPoint().getValue()));
        } else {
            tvPoint.setText("0원");
        }


    }

    @Override
    public void initListener() {

        // 적립 버튼
        btnSaving.setOnClickListener(view -> {
//            onMoveNavigation(R.id.actionfragment_non_member_save_password)
            onSavePoint();
        });

        etInputPoint.addTextChangedListener(mWatcherPriceCharge);

        Button btnCharge = view.findViewById(R.id.btn_fragment_save_point_charge);
        btnCharge.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), ChargeActivity.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSavePoint();
    }

    private void initSavePoint() {
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerNonMemberPointSaveInit()
                .enqueue(new MoaAuthCallback<ResNonMemberPointSaveInitDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResNonMemberPointSaveInitDTO> call,
                                                ResNonMemberPointSaveInitDTO resModel) {
                        mActivityViewModel.setmIsLoading(false);

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            mViewModel.setmSaveMaxPoint(resModel.getPointDto().getMaxPoint());
                        } else {
                            onNetworkConnectFail();
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResNonMemberPointSaveInitDTO> call,
                                               boolean isSession, Throwable t) {
                        mActivityViewModel.setmIsLoading(false);
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mActivityViewModel.setmIsLoading(false);
                        onNotSession();
                    }
                });
    }

    private void onSavePoint() {
        mActivityViewModel.setmIsLoading(true);
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerNonMemberSavePoint(
                        mViewModel.getPhoneNumber().getValue(),
                        mViewModel.getPoint().getValue().replace(",", ""))
                .enqueue(new MoaAuthCallback<ResNonMemberSavePointDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResNonMemberSavePointDTO> call,
                                                ResNonMemberSavePointDTO resModel) {
                        mActivityViewModel.setmIsLoading(false);
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        } else if (resModel.getDetailCode() == 203) {
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(getString(R.string.fragment_save_point_over_point_not_save,
                                    tvSaveMaxPoint.getText().toString()));
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        } else {
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(R.string.fragment_save_point_lacks_point);
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResNonMemberSavePointDTO> call,
                                               boolean isSession, Throwable t) {
                        mActivityViewModel.setmIsLoading(false);
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mActivityViewModel.setmIsLoading(false);
                        onNotSession();
                    }
                });
    }

    /**
     * 뷰 모델 옵저브
     */
    private void initObserve() {

        mViewModel.getSavePoint().observe(this, savePoint -> {
            int cp = etInputPoint.getSelectionStart();
            int startLen = etInputPoint.getText().length();
            int wonLen;
            if (etInputPoint.getText().length() == 1) {
                wonLen = -1;
            } else {
                wonLen = 0;
            }
            etInputPoint.setText(getString(R.string.fragment_payment_money_won, savePoint));
            int endLen = etInputPoint.getText().length();
            etInputPoint.setSelection((cp + (endLen - startLen)) + wonLen);

            TextView tvSaveAfterPoint = view.findViewById(R.id.tv_save_point_balance_point);
            int afterPoint = Integer.valueOf(savePoint.replace(",", "")) +
                    Integer.valueOf(mViewModel.getPoint().getValue().replace(",", ""));
            tvSaveAfterPoint.setText(getString(R.string.fragment_payment_money_won,
                    StringUtil.convertCommaPrice(afterPoint)));

        });

        mViewModel.getSaveMaxPoint().observe(this, saveMaxPoint -> {
            tvSaveMaxPoint.setText(getString(R.string.fragment_payment_money_won,
                    StringUtil.convertCommaPrice(saveMaxPoint)));
        });

    }

    private TextWatcher mWatcherPriceCharge = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mViewModel.setSavePoint(editable.toString());
        }
    };

}
