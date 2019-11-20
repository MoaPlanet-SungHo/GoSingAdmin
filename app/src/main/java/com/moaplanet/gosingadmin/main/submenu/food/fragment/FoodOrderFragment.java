package com.moaplanet.gosingadmin.main.submenu.food.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.food.adapter.FoodOrderAdapter;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class FoodOrderFragment extends BaseFragment {

    private RecyclerView rvFoodMenu;
    private Button btnFoodPayment;

    @Override
    public int layoutRes() {
        return R.layout.fragment_food_order;
    }

    @Override
    public void initView(View view) {
        rvFoodMenu = view.findViewById(R.id.rv_food_order_food);
        rvFoodMenu.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        btnFoodPayment = view.findViewById(R.id.btn_food_order_payment);
    }

    @Override
    public void initListener() {
        RxView.clicks(btnFoodPayment)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.action_fragment_food_order_payment));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FoodOrderAdapter foodOrderAdapter = new FoodOrderAdapter();
        rvFoodMenu.setAdapter(foodOrderAdapter);
    }
}
