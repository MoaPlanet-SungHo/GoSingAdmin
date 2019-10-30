package com.moaplanet.gosingadmin.network.model;

import com.google.gson.annotations.SerializedName;

public abstract class CommonResDto {
    @SerializedName("detailCode")
    int detailCode;

    @SerializedName("stateCode")
    int stateCode;

    public int getDetailCode() {
        return detailCode;
    }

    public int getStateCode() {
        return stateCode;
    }
}
