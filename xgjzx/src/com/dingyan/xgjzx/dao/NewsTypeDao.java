package com.dingyan.xgjzx.dao;

import com.dingyan.xgjzx.pojo.NewsType;

public interface NewsTypeDao {
    int deleteByPrimaryKey(Integer ntId);

    int insert(NewsType record);

    int insertSelective(NewsType record);

    NewsType selectByPrimaryKey(Integer ntId);

    int updateByPrimaryKeySelective(NewsType record);

    int updateByPrimaryKey(NewsType record);
}