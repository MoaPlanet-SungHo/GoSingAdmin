package com.moaplanet.gosingadmin.intro.login.moel.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResLoginDto extends CommonResDto {
    @SerializedName("user_email")
    String email;
}
