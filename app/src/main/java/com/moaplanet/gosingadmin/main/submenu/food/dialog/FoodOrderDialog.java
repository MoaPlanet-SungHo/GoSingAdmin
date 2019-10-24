package com.moaplanet.gosingadmin.main.submenu.food.dialog;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.BaseDialogFragment;

public class FoodOrderDialog extends BaseDialogFragment {

    private View.OnClickListener closeOnClickListener;
    private ConstraintLayout btnClose;

    @Override
    public int layoutRes() {
        return R.layout.dialog_food_order;
    }

    @Override
    public void initView() {
        btnClose = view.findViewById(R.id.cl_dialog_food_order_close);
    }

    @Override
    public void initListener() {
        btnClose.setOnClickListener(closeOnClickListener);
    }

    public void onCLoseOnClickListener(View.OnClickListener onClickListener) {
        closeOnClickListener = onClickListener;
    }
}
