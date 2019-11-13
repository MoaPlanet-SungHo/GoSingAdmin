package com.moaplanet.gosingadmin.main.submenu.charge.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.List;

import retrofit2.Call;

/**
 * 카드 충전 뷰모델
 */
public class ChargeViewModel extends ViewModel {

    // 서버 통신 상태 -> true : 성공 | false : 실패
    private MutableLiveData<Boolean> mConnectServerCheck = new MutableLiveData<>();

    // 로딩 유무 -> true : 로딩중 | false 로딩 아님
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    // 카드 리스트
    private MutableLiveData<List<ResCardListDto.CardInformationDto>> mCardInfoList =
            new MutableLiveData<>();

    // --- getter start --- //

    public MutableLiveData<Boolean> getConnectServerCheck() {
        return mConnectServerCheck;
    }

    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public LiveData<List<ResCardListDto.CardInformationDto>> getCardInfoList() {
        return mCardInfoList;
    }

    // --- getter end --- //

    // --- setter start--- //

    public void setIsLoading(boolean isLoading) {
        this.mIsLoading.setValue(isLoading);
    }
    // --- setter end--- //

    // --- init start --- //

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
                        mCardInfoList.setValue(resModel.getCardInformationDtoList());
                        mIsLoading.setValue(false);
                    }

                    @Override
                    public void onFinalFailure(Call<ResCardListDto> call,
                                               boolean isSession,
                                               Throwable t) {
                        mIsLoading.setValue(false);
                    }
                });
    }

    // --- init end --- //
}
