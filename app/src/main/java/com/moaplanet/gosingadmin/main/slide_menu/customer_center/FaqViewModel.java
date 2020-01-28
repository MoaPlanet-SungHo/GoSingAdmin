package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Response;

/**
 * FAQ ViewModel
 */
public class FaqViewModel extends BaseViewModel {

    private String reqeustFaqSeq;

    private MutableLiveData<ResFaqDTO> resFaqDTO = new MutableLiveData<>();

    public LiveData<ResFaqDTO> getResFaqDTO() {
        return resFaqDTO;
    }

    public void getFaqList(String faqSeq) {
        this.reqeustFaqSeq = faqSeq;

        RetrofitService.getInstance().getGoSingApi()
                .postFaq(reqeustFaqSeq)
                .enqueue(new RetrofitCallBack<ResFaqDTO>() {
                    @Override
                    public void onSuccess(ResFaqDTO response) {
                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            resFaqDTO.setValue(response);
                        } else {
                            failNetwork.setValue(true);
                        }
                    }

                    @Override
                    public void onFail(Response<ResFaqDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<ResFaqDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });
    }
}
