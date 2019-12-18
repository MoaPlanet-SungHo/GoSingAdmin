package com.moaplanet.gosingadmin.main.submenu.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.ResNotificationDTO;

import java.util.List;

/**
 * 알림 리스트 표시 어댑터
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    List<ResNotificationDTO.NotificationModel> notificationDtoList;

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.init(notificationDtoList.get(position));
    }

    public void setList(List<ResNotificationDTO.NotificationModel> notificationDtoList) {
        this.notificationDtoList = notificationDtoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (notificationDtoList == null) {
            return 0;
        } else {
            return notificationDtoList.size();
        }
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {

        private TextView tvType;
        private TextView tvContent;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_item_notification_title);
            tvContent = itemView.findViewById(R.id.tv_item_notification_content);
        }

        private void init(ResNotificationDTO.NotificationModel notificationDto) {
            tvContent.setText(notificationDto.getNotiContent());
        }

    }
}
