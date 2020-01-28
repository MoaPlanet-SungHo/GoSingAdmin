package com.moaplanet.gosingadmin.main.slide_menu.customer_center;

import com.google.gson.annotations.SerializedName;

public class FaqModel {
    @SerializedName("qa_list_seq")
    private String faqSeq;

    @SerializedName("qa_title")
    private String title;

    @SerializedName("info_count")
    private int count;

    public boolean isDetail() {
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getFaqSeq() {
        return faqSeq;
    }

    public void setFaqSeq(String faqSeq) {
        this.faqSeq = faqSeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
