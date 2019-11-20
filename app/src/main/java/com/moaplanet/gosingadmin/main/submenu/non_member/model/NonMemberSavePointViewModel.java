package com.moaplanet.gosingadmin.main.submenu.non_member.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.manager.PointManager;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

/**
 * 비회원 적립 뷰 모델
 */
public class NonMemberSavePointViewModel extends BaseViewModel {

    // --- Field Start --- //

    // 고싱 포인트
    private MutableLiveData<String> mPoint = new MutableLiveData<>();
    // 적립할 포인트
    private MutableLiveData<String> mSavePoint = new MutableLiveData<>();
    // 최대 적립 포인트
    private MutableLiveData<Integer> mSaveMaxPoint = new MutableLiveData<>();
    // 핸드폰 번호
    private MutableLiveData<String> mPhoneNumber = new MutableLiveData<>();
    // 결제 비밀번호 체크 --> true : 체크 완료 | false : 체크 안함
    private MutableLiveData<Boolean> mIsPinCheck = new MutableLiveData<>();

    // --- Field End--- //

    // --- Getter Start --- //

    public LiveData<String> getPoint() {
        return mPoint;
    }

    public LiveData<String> getSavePoint() {
        return mSavePoint;
    }

    public LiveData<Integer> getSaveMaxPoint() {
        return mSaveMaxPoint;
    }

    public LiveData<String> getPhoneNumber() {
        return mPhoneNumber;
    }

    public LiveData<Boolean> getIsPinCheck() {
        return mIsPinCheck;
    }

    // --- Getter End --- //

    // --- Setter Start --- //

    public void setSavePoint(String priceCharge) {

        if (!priceCharge.replaceAll("원", "").equals(mSavePoint.getValue())) {
            String price = priceCharge.replaceAll("[,원]", "");
            if (price.equals("")) {
                price = "0";
            } else {
                price = StringUtil.convertCommaPrice(price);
            }
            mSavePoint.setValue(price);
        }
    }

    public void setmSaveMaxPoint(int saveMaxPoint) {
        if (mSaveMaxPoint.getValue() != null && saveMaxPoint != mSaveMaxPoint.getValue()) {
            this.mSaveMaxPoint.setValue(saveMaxPoint);
        }
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber.setValue(mPhoneNumber);
    }

    public void setIsPinCheck(Boolean mIsPinCheck) {
        this.mIsPinCheck.setValue(mIsPinCheck);
    }

    // --- Setter End --- //

    // --- Other Method Start --- //

    public void searchGoSingPoint() {
        setIsLoading(true);
        PointManager pointManager = new PointManager();
        pointManager.setGoSingPointCallback(new PointManager.GoSingPointCallback() {

            @Override
            public void onSuccess(ResPointDto resPointDto) {
                mPoint.setValue(StringUtil.convertCommaPrice(resPointDto.getPoint()));
                setIsApiSuccess(true);
                setIsLoading(false);
            }

            @Override
            public void onFail() {
                setIsLoading(false);
                setIsApiSuccess(false);
            }

            @Override
            public void onNotSession() {
                setIsApiSuccess(false);
                setIsLoading(false);
                setSession(false);
            }
        });
        pointManager.getGoSingPoint();
    }

    // --- Other Method End --- //

}
