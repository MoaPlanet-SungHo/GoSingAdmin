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

    private onSelectCard mSelectCard;

    public void setmSelectCard(onSelectCard selectCard) {
        this.mSelectCard = selectCard;
    }

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
            mBtnSelectCard.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_checkbox_nor, 0, 0, 0);
            mBtnSelectCard = holder.mCardName;
            mBtnSelectCard.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_all_check_press, 0, 0, 0);
            if (mSelectCard != null) {
                mSelectCard.onCardInformation(mCardList.get(position));
            }
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

            if (mBtnSelectCard == null) {
                mCardName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_all_check_press, 0, 0, 0);
                mBtnSelectCard = mCardName;
            }

        }
    }

    /**
     * 카드 선택시 선택된 카드 데이터를 전달할 인터페이스
     */
    public interface onSelectCard {
        void onCardInformation(ResCardListDto.CardInformationDto cardInformation);
    }

}
