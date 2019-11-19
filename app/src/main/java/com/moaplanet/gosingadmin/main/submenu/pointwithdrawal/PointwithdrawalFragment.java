package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.CommonTitleNoUnderlineDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResSearchDepositAccount;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResPointWithDrawalDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import retrofit2.Call;

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

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_withdrawal;
    }

    @Override
    public void initView(View view) {

        CommonTitleBar commonTitle = view.findViewById(R.id.title_fragment_point_withdrawal);
        commonTitle.setBackButtonClickListener(view1 -> Navigation.findNavController(view).popBackStack());
        commonTitle.setTitle("포인트 출금");

        tvWithdrawAbleMoneyToolTip = view.findViewById(R.id.tv_fragment_point_withdrawal_able_money_title);
        ivWithdrawAbleMoneyToolTip = view.findViewById(R.id.iv_fragment_point_withdrawal_able_money_tool_tip);
        btnPointWithdrawal = view.findViewById(R.id.btn_fragment_point_withdrawal);
        btnAccountCharge = view.findViewById(R.id.btn_fragment_point_withdrawal_account_charge);

        etWithdrawal = view.findViewById(R.id.et_fragment_point_withdrawal_input_won);

    }

    @Override
    public void initListener() {
        tvWithdrawAbleMoneyToolTip.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_withdrawal_tool_tip, null);
        });
        ivWithdrawAbleMoneyToolTip.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_withdrawal_tool_tip, null);
        });

        btnAccountCharge.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW, AccountResisterFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE);
            Navigation.findNavController(view).navigate(R.id.action_fragment_change_account, bundle);
        });

        btnPointWithdrawal.setOnClickListener(view1 -> {
            pointWithdrawal();
//            CommonTitleNoUnderlineDialog commonTitleNoUnderlineDialog = new CommonTitleNoUnderlineDialog(getContext());
//            commonTitleNoUnderlineDialog.setContent("출금 요청이 완료되었습니다.");
//            commonTitleNoUnderlineDialog.show();
//
//            commonTitleNoUnderlineDialog.setDoneClickListener(view2 -> commonTitleNoUnderlineDialog.dismiss());
        });

        ImageView clear = view.findViewById(R.id.iv_fragment_card_delete_input_won);
        clear.setOnClickListener(v -> {
            etWithdrawal.setText("0");
        });

        etWithdrawal.addTextChangedListener(mWatcherPriceCharge);

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

}
