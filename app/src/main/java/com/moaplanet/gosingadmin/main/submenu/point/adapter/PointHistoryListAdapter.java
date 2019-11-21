package com.moaplanet.gosingadmin.main.submenu.point.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.point.dialog.PointHistoryDialog;
import com.moaplanet.gosingadmin.main.submenu.point.model.dto.ResPointHistoryDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class PointHistoryListAdapter extends
        RecyclerView.Adapter<PointHistoryListAdapter.PointHistoryHolder> {

    private FragmentManager fragmentManager;

    private List<ResPointHistoryDto.PointHistoryDto> pointList;

    @NonNull
    @Override
    public PointHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_point_history, parent, false);
        return new PointHistoryHolder(view);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onBindViewHolder(@NonNull PointHistoryHolder holder, int position) {
        holder.initView(pointList.get(position));
        PointHistoryDialog dialog = new PointHistoryDialog();
        RxView.clicks(holder.itemView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
//                    dialog.setType(viewType);
                    dialog.show(fragmentManager, "dialog");
                });

        dialog.setDialogDoneClickListener(view -> dialog.dismiss());
    }

    public void setList(List<ResPointHistoryDto.PointHistoryDto> pointList) {
        this.pointList = pointList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (pointList == null) {
            return 0;
        } else {
            return pointList.size();
        }
    }

    public class PointHistoryHolder extends RecyclerView.ViewHolder {

        private final int GET_POINT = 1;

        // 포인트
        private TextView tvPoint;
        // 타이틀
        private TextView mTitle;
        // 날짜
        private TextView mDate;

        public PointHistoryHolder(@NonNull View itemView) {
            super(itemView);
            tvPoint = itemView.findViewById(R.id.tv_item_point_history_point);
            mTitle = itemView.findViewById(R.id.tv_item_point_history_title);
            mDate = itemView.findViewById(R.id.tv_item_point_history_date);
        }

        public void initView(ResPointHistoryDto.PointHistoryDto pointModel) {

            mTitle.setText(pointModel.getTitle());
            mDate.setText(pointModel.getInsertDate());

            int pointColor;
            int stringResId;
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
