package com.moaplanet.gosingadmin.main.slide_menu.event.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        public EventHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
