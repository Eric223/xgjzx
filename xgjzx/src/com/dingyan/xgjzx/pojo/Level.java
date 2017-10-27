package com.dingyan.xgjzx.pojo;

import java.io.Serializable;

public class Level implements Serializable {
	/*
	 * 公交车级别
	 * @param leId
	 * 
	 */
    private Integer leId;

    private String leName;

    public Integer getLeId() {
        return leId;
    }

    public void setLeId(Integer leId) {
        this.leId = leId;
    }

    public String getLeName() {
        return leName;
    }

    public void setLeName(String leName) {
        this.leName = leName == null ? null : leName.trim();
    }
}