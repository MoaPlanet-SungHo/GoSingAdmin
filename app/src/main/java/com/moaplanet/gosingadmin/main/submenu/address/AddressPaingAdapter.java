package com.moaplanet.gosingadmin.main.submenu.address;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;

public class AddressPaingAdapter extends PagedListAdapter<ResAddressSearchDto.AddressInfoDto, AddressPaingAdapter.AddressHolder> {


    protected AddressPaingAdapter() {
        super(DIFF_CALLBACK);
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

    }

    public class AddressHolder extends RecyclerView.ViewHolder {
        public AddressHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static DiffUtil.ItemCallback<ResAddressSearchDto.AddressInfoDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResAddressSearchDto.AddressInfoDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResAddressSearchDto.AddressInfoDto oldItem, @NonNull ResAddressSearchDto.AddressInfoDto newItem) {
            return oldItem.getZipNo().equals(newItem.getZipNo());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ResAddressSearchDto.AddressInfoDto oldItem, @NonNull ResAddressSearchDto.AddressInfoDto newItem) {
            return oldItem.equals(newItem);
        }
    };
}
