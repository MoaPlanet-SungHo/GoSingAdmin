package com.moaplanet.gosingadmin.main.submenu.charge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;

import java.util.List;

/**
 * 카드 리스트 어뎁터
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<ResCardListDto.CardInformationDto> cardList;

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        if (cardList == null) {
            return 0;
        } else {
            return cardList.size();
        }

    }

    /**
     * 카드 리스트 초기화
     *
     * @param cardList 카드 리스트
     */
    public void setCardList(List<ResCardListDto.CardInformationDto> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        public CardHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
