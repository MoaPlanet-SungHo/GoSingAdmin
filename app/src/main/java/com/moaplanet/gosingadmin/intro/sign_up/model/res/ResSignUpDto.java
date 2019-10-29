package com.moaplanet.gosingadmin.intro.sign_up.model.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonModel;

public class ResSignUpDto extends CommonModel {

    @SerializedName("detailCode")
    private int detailCode;

    @SerializedName("stateCode")
    private int stateCode;
}
