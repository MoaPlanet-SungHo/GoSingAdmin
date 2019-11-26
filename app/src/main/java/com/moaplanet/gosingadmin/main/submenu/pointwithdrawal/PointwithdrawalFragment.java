package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResSearchDepositAccount;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity.RegisterAccountActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.DepositAccountViewModel;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResPointWithDrawalDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 포인트 인출 Fragment
 */
public class PointwithdrawalFragment extends BaseFragment {

    private TextView tvWithdrawAbleMoneyToolTip;
    private ImageView ivWithdrawAbleMoneyToolTip;
    private Button btnPointWithdrawal;      //인출하기
    private Button btnAccountCharge;        //계좌변경
    private EditText etWithdrawal;

    private int getPossibleWithdrawalPrice = 0;
    private String inputPrice = "";

    private String bankAccountPk = "";

    // 부모 모델의 뷰 모델
    private DepositAccountViewModel viewModel;

    // 서버 통신을 해서 계좌 정보를 가져왔는지 체크
    // false 계좌 정보 없음 | true 계좌 정보 있음
    private boolean isSearchDepositAccount = false;

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_withdrawal;
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(getActivity()).get(DepositAccountViewModel.class);
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        viewModel.getPinSuccess().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                pointWithdrawal();
            }
        });
    }

    @Override
    public void initView(View view) {
        tvWithdrawAbleMoneyToolTip = view.findViewById(R.id.tv_fragment_point_withdrawal_able_money_title);
        ivWithdrawAbleMoneyToolTip = view.findViewById(R.id.iv_fragment_point_withdrawal_able_money_tool_tip);
        btnPointWithdrawal = view.findViewById(R.id.btn_fragment_point_withdrawal);
        btnAccountCharge = view.findViewById(R.id.btn_fragment_point_withdrawal_account_charge);

        etWithdrawal = view.findViewById(R.id.et_fragment_point_withdrawal_input_won);

    }

    @Override
    public void initListener() {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_point_withdrawal);
        RxView.clicks(commonTitle.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });

        RxView.clicks(tvWithdrawAbleMoneyToolTip)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.action_fragment_withdrawal_tool_tip));

        RxView.clicks(ivWithdrawAbleMoneyToolTip)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.action_fragment_withdrawal_tool_tip));

        RxView.clicks(btnAccountCharge)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(view.getContext(), RegisterAccountActivity.class);
                    intent.putExtra("REQ_CODE", GoSingConstants.REQ_CODE_CHANGE_ACCOUNT_NUMBER);
                    startActivityForResult(intent, GoSingConstants.REQ_CODE_CHANGE_ACCOUNT_NUMBER);
                });


        RxView.clicks(btnPointWithdrawal)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(
                            PasswordInputFragment.BUNDLE_KEY_POINT_WITHDRAWAL_PASSWORD_TYPE,
                            PasswordInputFragment.BUNDLE_VALUE_POINT_WITHDRAWAL
                    );

                    Navigation.findNavController(this.view).navigate(
                            R.id.action_fragment_password_input,
                            bundle
                    );
                });

        ImageView clear = view.findViewById(R.id.iv_fragment_card_delete_input_won);
        RxView.clicks(clear)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> etWithdrawal.setText("0"));

        etWithdrawal.addTextChangedListener(mWatcherPriceCharge);

//        if (!isSearchDepositAccount) {
//            isSearchDepositAccount = true;
//            onLoadAccountInfo();
//        }
        onLoadAccountInfo();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoSingConstants.REQ_CODE_CHANGE_ACCOUNT_NUMBER) {
            if (resultCode == GoSingConstants.RESULT_CODE_CHANGE_ACCOUNT_NUMBER) {
                onLoadAccountInfo();
            }
        }

    }

    private void onLoadAccountInfo() {
        RetrofitService.getInstance().getGoSingApiService().onServerSearchDepositAccount()
                .enqueue(new MoaAuthCallback<ResSearchDepositAccount>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResSearchDepositAccount> call, ResSearchDepositAccount resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            bankAccountPk = resModel.getDepositAccount().getBankAccountPk();

                            TextView bankName = view.findViewById(R.id.tv_fragment_point_withdrawal_account_info_bank_name);
                            bankName.setText(resModel.getDepositAccount().getBankName());

                            TextView bankNumber = view.findViewById(R.id.tv_fragment_point_withdrawal_account_number);
                            bankNumber.setText(resModel.getDepositAccount().getAccountNumber());

                            // 출금 가능 금액
                            TextView possibleWithdrawalPrice = view.findViewById(R.id.tv_fragment_point_withdrawal_passible_money);
                            getPossibleWithdrawalPrice = resModel.getDepositAccount().getPossibleWithdrawalPrice();
                            possibleWithdrawalPrice.setText(getString(R.string.fragment_payment_money_won,
                                    StringUtil.convertCommaPrice(getPossibleWithdrawalPrice)));

                            // 출금 수수료
                            TextView tvWithdrawalFell = view.findViewById(R.id.tv_fragment_point_withdrawal_remain_money);
                            tvWithdrawalFell.setText(getString(R.string.fragment_payment_money_won,
                                    StringUtil.convertCommaPrice(resModel.getDepositAccount().getWithdrawalFee())));


                        } else {
                            onNetworkConnectFail();
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResSearchDepositAccount> call, boolean isSession, Throwable t) {
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();

                        onNotSession();

                    }
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
//            mChargeViewModel.setPriceCharge(editable.toString());

            if (!editable.toString().replaceAll("원", "").equals(inputPrice.replaceAll("원", ""))) {
                String price = editable.toString().replaceAll("[,원]", "");
                if (price.equals("")) {
                    price = "0";
                } else {
                    price = StringUtil.convertCommaPrice(price);
                }
                inputPrice = price;

                int cp = etWithdrawal.getSelectionStart();
                int startLen = etWithdrawal.getText().length();
                int wonLen;
                if (etWithdrawal.getText().length() == 1) {
                    wonLen = -1;
                } else {
                    wonLen = 0;
                }
                etWithdrawal.setText(getString(R.string.fragment_payment_money_won, price));
                int endLen = etWithdrawal.getText().length();
                etWithdrawal.setSelection((cp + (endLen - startLen)) + wonLen);

            }

        }
    };

    private void pointWithdrawal() {

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerPointWithDrawal(
                        bankAccountPk,
                        etWithdrawal.getText().toString().replaceAll("[,원]", "")
                )
                .enqueue(new MoaAuthCallback<ResPointWithDrawalDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPointWithDrawalDTO> call,
                                                ResPointWithDrawalDTO resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            Toast.makeText(view.getContext(),
                                    "출금요청이 완료되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        } else {
                            Toast.makeText(
                                    view.getContext(),
                                    "포인트가 부족합니다.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResPointWithDrawalDTO> call,
                                               boolean isSession, Throwable t) {
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        onNotSession();
                    }
                });

    }

    @Override
    public void onPause() {
        ViewUtil.onHideKeyboard(view);
        super.onPause();
    }
}
