package com.moaplanet.gosingadmin.main.submenu.point.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.point.model.dto.ResPointHistoryDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

/**
 * 포인트 내역 페이징 어뎁터
 */
public class PointHistoryPagingAdapter extends PagedListAdapter<ResPointHistoryDto.PointHistoryDto, PointHistoryPagingAdapter.PointHolder> {

    @NonNull
    @Override
    public PointHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_point_history, parent, false);
        return new PointHistoryPagingAdapter.PointHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointHolder holder, int position) {
        holder.initView(position);
    }

    public class PointHolder extends RecyclerView.ViewHolder {

        // 포인트
        private TextView tvPoint;
        // 타이틀
        private TextView mTitle;
        // 날짜
        private TextView mDate;

        public PointHolder(@NonNull View itemView) {
            super(itemView);
            tvPoint = itemView.findViewById(R.id.tv_item_point_history_point);
            mTitle = itemView.findViewById(R.id.tv_item_point_history_title);
            mDate = itemView.findViewById(R.id.tv_item_point_history_date);
        }

        public void initView(int pos) {
            ResPointHistoryDto.PointHistoryDto pointModel = getItem(pos);

            if (pointModel != null) {
                mTitle.setText(pointModel.getTitle());
                mDate.setText(pointModel.getInsertDate());

                int pointColor;
                int stringResId;
                final int GET_POINT = 1;
                if (pointModel.getInfoPkType() == GET_POINT) {
                    stringResId = R.string.item_point_history_plus_point;
                    pointColor = ContextCompat.getColor(itemView.getContext(), R.color.color_4300ff);
                } else {
                    stringResId = R.string.item_point_history_minus_point;
                    pointColor = ContextCompat.getColor(itemView.getContext(), R.color.color_ff4a24);
                }
                tvPoint.setText(itemView.getContext().getString(
                        stringResId,
                        StringUtil.convertCommaPrice(pointModel.getPointInfo())));
                tvPoint.setTextColor(pointColor);
            }
        }
    }

    public PointHistoryPagingAdapter() {
        super(DIFF_CALLBACK);
    }

    private static DiffUtil.ItemCallback<ResPointHistoryDto.PointHistoryDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResPointHistoryDto.PointHistoryDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResPointHistoryDto.PointHistoryDto oldItem, @NonNull ResPointHistoryDto.PointHistoryDto newItem) {
            return oldItem.getInfoPk().equals(newItem.getInfoPk());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ResPointHistoryDto.PointHistoryDto oldItem, @NonNull ResPointHistoryDto.PointHistoryDto newItem) {
            return oldItem.equals(newItem);
        }
    };
}
