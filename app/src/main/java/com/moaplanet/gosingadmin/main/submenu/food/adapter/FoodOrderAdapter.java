package com.moaplanet.gosingadmin.main.submenu.food.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderHolder> {

    @NonNull
    @Override
    public FoodOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_order_menu, parent, false);
        return new FoodOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class FoodOrderHolder extends RecyclerView.ViewHolder {

        public FoodOrderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
