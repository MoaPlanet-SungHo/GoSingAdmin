package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.interfaces.AdapterClick;

public class FaqAdatper extends ListAdapter<FaqModel, FaqAdatper.ViewHolder> {

    private AdapterClick<FaqModel> adapterClick;
    private String hostUrl;

    public FaqAdatper(AdapterClick<FaqModel> adapterClick, @NonNull DiffUtil.ItemCallback<FaqModel> diffCallback) {
        super(diffCallback);
        this.adapterClick = adapterClick;
    }

    @NonNull
    @Override
    public FaqAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqAdatper.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(view -> {
            if (adapterClick != null) {
                adapterClick.click(getItem(position));
            }
        });
        holder.setData(getItem(position));
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_faq_title);
        }

        public void setData(FaqModel model) {
            tvTitle.setText(model.getTitle());
        }
    }
}
