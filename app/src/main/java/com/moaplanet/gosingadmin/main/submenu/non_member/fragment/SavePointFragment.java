package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.interfaces.PriceWatcher;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseActivityViewModel;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.ChargeActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResNonMemberPointSaveInitDTO;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResNonMemberSavePointDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

public class SavePointFragment extends BaseFragment {

    // 뷰모델
    private NonMemberSavePointViewModel mViewModel;
    private BaseActivityViewModel mActivityViewModel;

    private EditText etInputPoint;
    private Button btnSaving;

    private TextView tvSaveMaxPoint;
    private TextView tvSaveAfterPoint;

    // 포인트 초기화 통신을 할지 말지에 대한 플래그값
    private boolean isLoadSavePoint = true;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(NonMemberSavePointViewModel.class);
            mActivityViewModel = ViewModelProviders.of(getActivity()).get(BaseActivityViewModel.class);

            // 포인트 표시
            TextView tvPoint = view.findViewById(R.id.tv_fragment_save_point);

            if (mViewModel != null) {
                tvPoint.setText(getString(R.string.fragment_payment_money_won,
                        mViewModel.getPoint().getValue()));
            } else {
                tvPoint.setText("0원");
            }
            tvSaveAfterPoint.setText(tvPoint.getText());

        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_point;
    }

    @Override
    public void initView(View view) {
        tvSaveMaxPoint = view.findViewById(R.id.tv_save_point_max_save);
        btnSaving = view.findViewById(R.id.btn_save_point_saving);
        etInputPoint = view.findViewById(R.id.et_save_point_input_point);
        tvSaveAfterPoint = view.findViewById(R.id.tv_save_point_balance_point);
    }

    @Override
    public void initListener() {

        CommonTitleBar commonTitleBar = view.findViewById(R.id.common_save_point_title_bar);
        RxView.clicks(commonTitleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        // 적립 버튼
        RxView.clicks(btnSaving)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.actionfragment_non_member_save_password));

        PriceWatcher priceWatcher = new PriceWatcher(etInputPoint);
        priceWatcher.setCallback((completePrice, price) -> {
            int afterPoint;
            try {
                String havePoint = mViewModel.getPoint().getValue().replace(",", "");
                afterPoint = Integer.parseInt(havePoint) + price;
            } catch (NullPointerException | NumberFormatException e) {
                afterPoint = price;
            }
            mViewModel.setSavePoint(completePrice);
            tvSaveAfterPoint.setText(getString(R.string.fragment_payment_money_won,
                    StringUtil.convertCommaPrice(afterPoint)));
        });
        etInputPoint.addTextChangedListener(priceWatcher);

        Button btnCharge = view.findViewById(R.id.btn_fragment_save_point_charge);
        RxView.clicks(btnCharge)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(view.getContext(), ChargeActivity.class);
                    startActivity(intent);
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isLoadSavePoint) {
            isLoadSavePoint = false;
            initSavePoint();
        }
    }

    private void initSavePoint() {
        mActivityViewModel.setIsLoading(true);
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
                        mActivityViewModel.setIsLoading(false);

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            mViewModel.setmSaveMaxPoint(resModel.getPointDto().getMaxPoint());
                        } else {
                            onNetworkConnectFail();
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResNonMemberPointSaveInitDTO> call,
                                               boolean isSession, Throwable t) {
                        mActivityViewModel.setIsLoading(false);
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mActivityViewModel.setIsLoading(false);
                        onNotSession();
                    }
                });
    }

    private void onSavePoint() {
        mActivityViewModel.setIsLoading(true);
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerNonMemberSavePoint(
                        mViewModel.getPhoneNumber().getValue(),
                        mViewModel.getSavePoint().getValue().replace(",", ""))
                .enqueue(new MoaAuthCallback<ResNonMemberSavePointDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResNonMemberSavePointDTO> call,
                                                ResNonMemberSavePointDTO resModel) {
                        mActivityViewModel.setIsLoading(false);
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            Toast.makeText(view.getContext(),
                                    "포인트 적립이 완료되었습니다."
                                    , Toast.LENGTH_SHORT).show();
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        } else if (resModel.getDetailCode() == 203) {
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(getString(R.string.fragment_save_point_over_point_not_save,
                                    tvSaveMaxPoint.getText().toString()));
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        } else if (resModel.getDetailCode() == 204){
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(R.string.fragment_save_point_lacks_point);
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        } else if (resModel.getDetailCode() == 201){
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(R.string.fragment_save_phone_number_exist_user);
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResNonMemberSavePointDTO> call,
                                               boolean isSession, Throwable t) {
                        mActivityViewModel.setIsLoading(false);
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mActivityViewModel.setIsLoading(false);
                        onNotSession();
                    }
                });
    }

    /**
     * 뷰 모델 옵저브
     */
    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.getSaveMaxPoint().observe(getViewLifecycleOwner(),
                saveMaxPoint -> tvSaveMaxPoint.setText(getString(R.string.fragment_payment_money_won,
                        StringUtil.convertCommaPrice(saveMaxPoint))));

        mViewModel.getIsPinCheck().observe(getViewLifecycleOwner(), isCheck -> {
            if (isCheck) {
                mViewModel.setIsPinCheck(false);
                onSavePoint();
            }
        });

    }

    @Override
    public void onPause() {
        ViewUtil.onHideKeyboard(view);
        super.onPause();
    }

}
