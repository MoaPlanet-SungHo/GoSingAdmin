package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.CommonTitleNoUnderlineDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResSearchDepositAccount;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * 포인트 인출 Fragment
 */
public class PointwithdrawalFragment extends BaseFragment {

    private TextView tvWithdrawAbleMoneyToolTip;
    private ImageView ivWithdrawAbleMoneyToolTip;
    private Button btnPointWithdrawal;      //인출하기
    private Button btnAccountCharge;        //계좌변경

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
            CommonTitleNoUnderlineDialog commonTitleNoUnderlineDialog = new CommonTitleNoUnderlineDialog(getContext());
            commonTitleNoUnderlineDialog.setContent("출금 요청이 완료되었습니다.");
            commonTitleNoUnderlineDialog.show();

            commonTitleNoUnderlineDialog.setDoneClickListener(view2 -> commonTitleNoUnderlineDialog.dismiss());
        });

        RetrofitService.getInstance().getGoSingApiService().onServerSearchDepositAccount()
                .enqueue(new MoaAuthCallback<ResSearchDepositAccount>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResSearchDepositAccount> call, ResSearchDepositAccount resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            TextView bankName = view.findViewById(R.id.tv_fragment_point_withdrawal_account_info_bank_name);
                            bankName.setText(resModel.getDepositAccount().getBankName());

                            TextView bankNumber = view.findViewById(R.id.tv_fragment_point_withdrawal_account_number);
                            bankName.setText(resModel.getDepositAccount().getAccountNumber());


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
}
