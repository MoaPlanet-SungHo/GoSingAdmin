package com.moaplanet.gosingadmin.main.slide_menu.event.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.interfaces.AdapterClick;
import com.moaplanet.gosingadmin.main.slide_menu.event.EventDTO;
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class EventAdapter extends ListAdapter<EventDTO.EventModel, EventAdapter.EventHolder> {

    private AdapterClick<EventDTO.EventModel> callback;

    public EventAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ViewUtil.getHolderView(parent, R.layout.item_event);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

        EventDTO.EventModel model = getItem(position);
        holder.init(model);

        RxView.clicks(holder.itemView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> callback.click(model));

    }

    public void setCallback(AdapterClick<EventDTO.EventModel> callback) {
        this.callback = callback;
    }

    private static final DiffUtil.ItemCallback<EventDTO.EventModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<EventDTO.EventModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull EventDTO.EventModel oldItem, @NonNull EventDTO.EventModel newItem) {
                    return oldItem.getEventSeq().equals(newItem.getEventSeq());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(
                        @NonNull EventDTO.EventModel oldItem, @NonNull EventDTO.EventModel newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class EventHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDate;

        EventHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_event_title);
            tvDate = itemView.findViewById(R.id.tv_item_event_date);
        }

        void init(EventDTO.EventModel model) {
            tvTitle.setText(model.getTitle());
            tvDate.setText(itemView.getContext().getString(R.string.item_event_date, model.getEventDate()));
        }
    }

}
