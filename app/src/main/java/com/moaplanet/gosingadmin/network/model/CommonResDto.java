package com.moaplanet.gosingadmin.network.model;

import com.google.gson.annotations.SerializedName;

public abstract class CommonResDto {
    @SerializedName("detailCode")
    private Integer detailCode;

    @SerializedName("stateCode")
    private Integer stateCode;

    public Integer getDetailCode() {
        return detailCode;
    }

    public Integer getStateCode() {
        return stateCode;
    }
}
