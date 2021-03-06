package com.moaplanet.gosingadmin.main.slide_menu.notice;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
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
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

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
        NoticeDTO.NoticeModel model = getItem(position);
        holder.init(model);

        RxView.clicks(holder.itemView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> adapterClick.click(model));
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

    void setAdapterClick(AdapterClick<NoticeDTO.NoticeModel> adapterClick) {
        this.adapterClick = adapterClick;
    }

    class NoticeHolder extends RecyclerView.ViewHolder {

        // 타이틀, 날짜
        private TextView tvTitle, tvDate;

        NoticeHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_notice_title);
            tvDate = itemView.findViewById(R.id.tv_item_notice_date);
        }

        void init(NoticeDTO.NoticeModel model) {
            tvTitle.setText(model.getTitle());
            tvDate.setText(itemView.getContext().getString(
                    R.string.item_notice_date,
                    model.noticeDate()));
        }
    }
}
