package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResBankInfoDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * 은행 리스트 어뎁터
 */
public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListHolder> {

    // 은행 리스트
    private List<ResBankInfoDto.BankInformationDto> mBankList;

    // 선택한 카드
    private Button mBtnSelectBank;
    // 콜랙 리스터
    private BankListAdapter.onSelectBank mSelectBank;

    @NonNull
    @Override
    public BankListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank, parent, false);
        return new BankListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankListHolder holder, int position) {

        holder.init(mBankList.get(position));
        RxView.clicks(holder.mBankName)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (mBtnSelectBank != null) {
                        mBtnSelectBank.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_checkbox_nor, 0, 0, 0);
                    }
                    mBtnSelectBank = holder.mBankName;
                    mBtnSelectBank.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_all_check_press, 0, 0, 0);
                    if (mSelectBank != null) {
                        mSelectBank.onBankInformation(mBankList.get(position));
                    }
                });

    }

    @Override
    public int getItemCount() {
        if (mBankList == null) {
            return 0;
        } else {
            return mBankList.size();
        }
    }

    /**
     * 은행 리스트 받기
     */
    public void setBankList(List<ResBankInfoDto.BankInformationDto> mBankList) {
        this.mBankList = mBankList;
        notifyDataSetChanged();
    }

    public class BankListHolder extends RecyclerView.ViewHolder {

        // 은행 이름
        private Button mBankName;

        public BankListHolder(@NonNull View itemView) {
            super(itemView);
            mBankName = itemView.findViewById(R.id.btn_item_bank_added_icon);
        }

        private void init(ResBankInfoDto.BankInformationDto dto) {
            mBankName.setText(dto.getBankName());
        }
    }

    public void setSelectBank(onSelectBank selectBank) {
        this.mSelectBank = selectBank;
    }

    /**
     * 카드 선택시 선택된 은행 데이터를 전달할 인터페이스
     */
    public interface onSelectBank {
        void onBankInformation(ResBankInfoDto.BankInformationDto bankInformationDto);
    }
}
