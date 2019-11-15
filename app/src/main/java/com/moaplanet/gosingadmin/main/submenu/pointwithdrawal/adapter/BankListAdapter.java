package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 은행 리스트 어뎁터
 */
public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListHolder> {

    @NonNull
    @Override
    public BankListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BankListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BankListHolder extends RecyclerView.ViewHolder {
        public BankListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
