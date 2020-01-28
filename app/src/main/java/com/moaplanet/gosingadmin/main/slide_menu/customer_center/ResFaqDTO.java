package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

public class ResFaqDTO extends CommonResDto {

    @SerializedName("list_return")
    private List<FaqModel> faqList;

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("top_title")
    private String topTitle;

    public List<FaqModel> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<FaqModel> faqList) {
        this.faqList = faqList;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }
}
