package com.moaplanet.gosingadmin.main.submenu.food.fragment;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputBaseFragment;
import com.moaplanet.gosingadmin.main.submenu.food.dialog.FoodOrderDialog;

public class FoodOrderPaymentFragment extends PasswordInputBaseFragment {
    @Override
    public void checkPasswordViewType() {
        tvPasswordInputTitle.setText(R.string.fragment_food_order_payment_input_pw);
        tvExplanation.setText(R.string.fragment_food_order_payment_find_pw);
    }

    @Override
    public void checkPassword(String password) {
        FoodOrderDialog foodOrderDialog = new FoodOrderDialog();
        if (getFragmentManager() != null) {
            foodOrderDialog.show(getFragmentManager(), "dialog");
        }

        foodOrderDialog.onCLoseOnClickListener(view -> {
            foodOrderDialog.dismiss();
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        //todo 4초후 삭제 예정 다이얼로그 필요
    }

    @Override
    public String titleText() {
        return getString(R.string.fragment_food_order_payment_title_bar);
    }
}
