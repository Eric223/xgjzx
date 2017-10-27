package com.dingyan.xgjzx.dao;

import com.dingyan.xgjzx.pojo.Level;

public interface LevelDao {
    int deleteByPrimaryKey(Integer leId);

    int insert(Level record);

    int insertSelective(Level record);
    
    Level selectByPrimaryKey(Integer leId);

    int updateByPrimaryKeySelective(Level record);

    int updateByPrimaryKey(Level record);
}