package com.dingyan.xgjzx.pojo;

public class Province {
    private Integer pId;

    private String pProvinceName;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpProvinceName() {
        return pProvinceName;
    }

    public void setpProvinceName(String pProvinceName) {
        this.pProvinceName = pProvinceName == null ? null : pProvinceName.trim();
    }
}