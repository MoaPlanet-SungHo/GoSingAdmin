package com.moaplanet.gosingadmin.main.submenu.review.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.utils.ViewUtil;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ViewUtil.getHolderView(parent, R.layout.item_review);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
