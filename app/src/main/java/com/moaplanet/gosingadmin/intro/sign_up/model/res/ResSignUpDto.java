package com.moaplanet.gosingadmin.intro.sign_up.model.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResSignUpDto extends CommonResDto {

    @SerializedName("detailCode")
    private int detailCode;

    @SerializedName("stateCode")
    private int stateCode;
}
