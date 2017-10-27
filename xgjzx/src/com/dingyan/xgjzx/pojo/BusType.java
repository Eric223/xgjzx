package com.dingyan.xgjzx.pojo;

import java.io.Serializable;

public class BusType implements Serializable {
	/*
	 * 公交车类型表
	 * @param id
	 * 
	 * @param typeName 类型
	 */
    private Integer tyId;

    private String typeName;

    public Integer getTyId() {
        return tyId;
    }

    public void setTyId(Integer tyId) {
        this.tyId = tyId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}