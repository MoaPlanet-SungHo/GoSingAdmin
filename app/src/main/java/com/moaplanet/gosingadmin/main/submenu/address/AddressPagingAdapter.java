package com.moaplanet.gosingadmin.main.submenu.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;

public class AddressPagingAdapter extends PagedListAdapter<ResAddressSearchDto.AddressInfoDto, AddressPagingAdapter.AddressHolder> {


    AddressPagingAdapter() {
        super(DIFF_CALLBACK);
    }

    private onItemClick onItemClick;

    public void setOnItemClick(AddressPagingAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        holder.init(position);
        holder.itemView.setOnClickListener(view -> {
            if (onItemClick != null) {
                onItemClick.onClick(getItem(position));
            }
        });
    }

    public class AddressHolder extends RecyclerView.ViewHolder {

        private TextView tvHeader;
        private TextView tvRoadAddress;
        private TextView tvJibunAddress;

        AddressHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_item_address_header);
            tvRoadAddress = itemView.findViewById(R.id.tv_item_address_road_address);
            tvJibunAddress = itemView.findViewById(R.id.tv_item_address_jibun_address);
        }

        void init(int position) {
            ResAddressSearchDto.AddressInfoDto addressInfoDto = getItem(position);
            if (addressInfoDto != null) {
                if (position == 0) {
                    initShowHeader();
                } else {
                    initHideHeader();
                }

                initAddress(addressInfoDto);
            }
        }

        private void initHideHeader() {
            tvHeader.setVisibility(View.GONE);
        }

        private void initShowHeader() {
            tvHeader.setVisibility(View.VISIBLE);
        }

        private void initAddress(ResAddressSearchDto.AddressInfoDto addressInfoDto) {
            Context context = itemView.getContext();
            String roadAddress = context.getString(
                    R.string.item_address_road_address,
                    addressInfoDto.getRoadAddress());

            String jibunAddress = context.getString(
                    R.string.item_address_jibun_address,
                    addressInfoDto.getJibunAddress());

            tvRoadAddress.setText(roadAddress);
            tvJibunAddress.setText(jibunAddress);
        }
    }

    private static DiffUtil.ItemCallback<ResAddressSearchDto.AddressInfoDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResAddressSearchDto.AddressInfoDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResAddressSearchDto.AddressInfoDto oldItem, @NonNull ResAddressSearchDto.AddressInfoDto newItem) {
            return oldItem.getZipNo().equals(newItem.getZipNo());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ResAddressSearchDto.AddressInfoDto oldItem, @NonNull ResAddressSearchDto.AddressInfoDto newItem) {
            return oldItem.equals(newItem);
        }
    };

    public interface onItemClick {
        void onClick(ResAddressSearchDto.AddressInfoDto addressInfoDto);
    }
}
