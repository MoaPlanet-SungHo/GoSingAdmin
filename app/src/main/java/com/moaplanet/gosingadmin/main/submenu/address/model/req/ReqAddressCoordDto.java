package com.moaplanet.gosingadmin.main.submenu.address.model.req;

import com.moaplanet.gosingadmin.network.NetworkConstants;

public class ReqAddressCoordDto {

    // 승인키
    private String confmKey = NetworkConstants.GOSING_ADMIN_COORDINATES_KEY;
    // 행정구역코드
    private String admCd;
    // 도로명코드
    private String rnMgtSn;
    // 지하여부
    private String udrtYn;
    // 건물본번
    private String buldMnnm;
    // 간물부번
    private String buldSlno;
    // 결과 형식
    private String resultType = "json";

    public void setBuldMnnm(String buldMnnm) {
        this.buldMnnm = buldMnnm;
    }

    public void setBuldSlno(String buldSlno) {
        this.buldSlno = buldSlno;
    }

    public void setRnMgtSn(String rnMgtSn) {
        this.rnMgtSn = rnMgtSn;
    }

    public void setUdrtYn(String udrtYn) {
        this.udrtYn = udrtYn;
    }

    public void setAdmCd(String admCd) {
        this.admCd = admCd;
    }

    public String getResultType() {
        return resultType;
    }

    public String getRnMgtSn() {
        return rnMgtSn;
    }

    public String getAdmCd() {
        return admCd;
    }

    public String getConfmKey() {
        return confmKey;
    }

    public String getBuldMnnm() {
        return buldMnnm;
    }

    public String getBuldSlno() {
        return buldSlno;
    }

    public String getUdrtYn() {
        return udrtYn;
    }
}
