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
import com.moaplanet.gosingadmin.main.submenu.point.dto.res.ResPointHistoryDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class PointHistoryListAdapter extends
        RecyclerView.Adapter<PointHistoryListAdapter.PointHistoryHolder> {

    private String viewType = "all";
    private FragmentManager fragmentManager;

    private List<ResPointHistoryDto.PointHistoryDto> pointHistoryDtoList;

    @NonNull
    @Override
    public PointHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_point_history, parent, false);
        return new PointHistoryHolder(view);
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onBindViewHolder(@NonNull PointHistoryHolder holder, int position) {
        holder.initView();
        PointHistoryDialog dialog = new PointHistoryDialog();
        RxView.clicks(holder.itemView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    dialog.setType(viewType);
                    dialog.show(fragmentManager, "dialog");
                });

        dialog.setDialogDoneClickListener(view -> dialog.dismiss());
    }

    public void setList(List<ResPointHistoryDto.PointHistoryDto> pointHistoryDtoList) {
        this.pointHistoryDtoList = pointHistoryDtoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class PointHistoryHolder extends RecyclerView.ViewHolder {

        private TextView tvPoint;

        public PointHistoryHolder(@NonNull View itemView) {
            super(itemView);
            tvPoint = itemView.findViewById(R.id.tv_item_point_history_point);
        }

        public void initView() {

            int pointColor;
            int stringResId;
            if (viewType.equals("deposit")) {
                stringResId = R.string.item_point_history_plus_point;
                pointColor = ContextCompat.getColor(itemView.getContext(), R.color.color_4300ff);
            } else {
                stringResId = R.string.item_point_history_minus_point;
                pointColor = ContextCompat.getColor(itemView.getContext(), R.color.color_ff4a24);
            }
            tvPoint.setText(itemView.getContext().getString(
                    stringResId,
                    "3,000"));

            tvPoint.setTextColor(pointColor);
        }
    }
}
