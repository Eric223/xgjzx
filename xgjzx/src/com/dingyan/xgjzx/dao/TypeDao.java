package com.dingyan.xgjzx.dao;

import com.dingyan.xgjzx.pojo.BusType;

public interface TypeDao {
    int deleteByPrimaryKey(Integer tyId);

    int insert(BusType record);

    int insertSelective(BusType record);

    BusType selectByPrimaryKey(Integer tyId);

    int updateByPrimaryKeySelective(BusType record);

    int updateByPrimaryKey(BusType record);
}