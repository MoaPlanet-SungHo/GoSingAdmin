package com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.List;

import retrofit2.Call;

/**
 * 카드 충전 뷰모델
 */
public class ChargeCardViewModel extends BaseViewModel {

    // -- Field Start -- //
    // 카드 리스트
    private MutableLiveData<List<ResCardListDto.CardInformationDto>> mCardInfoList =
            new MutableLiveData<>();

    // 충전 버튼 활성화 유무 -- > true : 활성화 | false : 비활성화
    private MutableLiveData<Boolean> mChargeButtonActive = new MutableLiveData<>();
    // -- Field End -- //

    // --- Getter Start --- //
    public LiveData<List<ResCardListDto.CardInformationDto>> getCardInfoList() {
        return mCardInfoList;
    }

    public LiveData<Boolean> getChargeButtonActive() {
        return mChargeButtonActive;
    }
    // --- Getter End --- //

    // --- Setter Start--- //
    public void setChargeButtonActive(boolean chargeButtonActive) {
        mChargeButtonActive.setValue(chargeButtonActive);
    }
    // --- Setter End--- //

    // --- Init Start --- //

    /**
     * 카드 불러오기
     */
    public void onCardListInit() {
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerCardList()
                .enqueue(new MoaAuthCallback<ResCardListDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResCardListDto> call, ResCardListDto resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            setIsApiSuccess(true);
                            mCardInfoList.setValue(resModel.getCardInformationDtoList());
                        } else {
                            setSession(false);
                        }
                        setIsLoading(false);
                    }

                    @Override
                    public void onFinalFailure(Call<ResCardListDto> call,
                                               boolean isSession,
                                               Throwable t) {
                        setIsLoading(false);
                        setIsApiSuccess(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        setIsLoading(false);
                        setIsApiSuccess(false);
                        setSession(false);
                    }
                });
    }
    // --- Init End --- //
}
