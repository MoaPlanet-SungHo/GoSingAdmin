package com.moaplanet.gosingadmin.main.slide_menu.notice;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.interfaces.AdapterClick;
import com.moaplanet.gosingadmin.utils.ViewUtil;

public class NoticeAdapter extends ListAdapter<NoticeDTO.NoticeModel,NoticeAdapter.NoticeHolder> {

    // 클릭 콜백
    private AdapterClick<NoticeDTO.NoticeModel> adapterClick;

    NoticeAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ViewUtil.getHolderView(parent, R.layout.item_notice);
        return new NoticeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {

    }

    private static final DiffUtil.ItemCallback<NoticeDTO.NoticeModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NoticeDTO.NoticeModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull NoticeDTO.NoticeModel oldItem, @NonNull NoticeDTO.NoticeModel newItem) {
                    return oldItem.getSeq().equals(newItem.getSeq());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(
                        @NonNull NoticeDTO.NoticeModel oldItem, @NonNull NoticeDTO.NoticeModel newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class NoticeHolder extends RecyclerView.ViewHolder {

        NoticeHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
