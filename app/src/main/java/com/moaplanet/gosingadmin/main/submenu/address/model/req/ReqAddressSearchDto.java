package com.moaplanet.gosingadmin.main.submenu.address.model.req;

import com.moaplanet.gosingadmin.network.NetworkConstants;

public class ReqAddressSearchDto {

    private String confmKey = NetworkConstants.GOSING_ADMIN_ADDRESS_KEY;
    private int currentPage;
    private int countPerPage = NetworkConstants.ADDRESS_SEACH_PAGING_COUNT;
    private String keyword;
    private String resultType = "json";

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getConfmKey() {
        return confmKey;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getResultType() {
        return resultType;
    }
}
