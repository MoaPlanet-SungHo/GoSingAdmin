package com.moaplanet.gosingadmin.main.submenu.charge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;

import java.util.List;

/**
 * 카드 리스트 어뎁터
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    // 카드 리스트
    private List<ResCardListDto.CardInformationDto> mCardList;

    // 선택한 카드
    private Button mBtnSelectCard;

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        holder.init(mCardList.get(position));

        holder.mCardName.setOnClickListener(view -> {
            if (mBtnSelectCard != null) {
                mBtnSelectCard.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_checkbox_nor, 0, 0, 0);
            }
            mBtnSelectCard = holder.mCardName;
            mBtnSelectCard.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_all_check_press, 0, 0, 0);
        });
    }

    @Override
    public int getItemCount() {

        if (mCardList == null) {
            return 0;
        } else {
            return mCardList.size();
        }

    }

    /**
     * 카드 리스트 초기화
     *
     * @param cardList 카드 리스트
     */
    public void setCardList(List<ResCardListDto.CardInformationDto> cardList) {
        this.mCardList = cardList;
        notifyDataSetChanged();
    }

    class CardHolder extends RecyclerView.ViewHolder {

        // 카드 이름
        private Button mCardName;

        CardHolder(@NonNull View itemView) {
            super(itemView);
            mCardName = itemView.findViewById(R.id.btn_item_card_added_icon);
        }

        /**
         * 초기화
         */
        private void init(ResCardListDto.CardInformationDto cardInfo) {
            mCardName.setText(cardInfo.getmCardName());
        }
    }

}
