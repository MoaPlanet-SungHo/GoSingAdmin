package com.moaplanet.gosingadmin.main.slide_menu.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notice, parent, false);
        return new NoticeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class NoticeHolder extends RecyclerView.ViewHolder {

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
