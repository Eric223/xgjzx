package com.dingyan.xgjzx.pojo;

public class City {
    private Integer cId;

    private Integer cProvince;

    private String cCityname;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getcProvince() {
        return cProvince;
    }

    public void setcProvince(Integer cProvince) {
        this.cProvince = cProvince;
    }

    public String getcCityname() {
        return cCityname;
    }

    public void setcCityname(String cCityname) {
        this.cCityname = cCityname == null ? null : cCityname.trim();
    }
}