package com.moaplanet.gosingadmin.main.submenu.charge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        public CardHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
