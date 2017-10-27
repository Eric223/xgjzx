package com.dingyan.xgjzx.dao;

import java.util.List;

import com.dingyan.xgjzx.pojo.News;

import com.dingyan.xgjzx.util.TypeUtil;

import com.dingyan.xgjzx.util.PageUtil;

public interface NewsDao {
    int deleteByPrimaryKey(Integer ntId);
    
    int insert(News record);
    
    int insertSelective(News record);
    
    News selectByPrimaryKey(Integer ntId);
    
    int updateByPrimaryKeySelective(News record);
    
    int updateByPrimaryKeyWithBLOBs(News record);
    
    int updateByPrimaryKey(News record);
    
	List<News> findAll();

	int count();
	List<News> selectAll(PageUtil page);
	
	List<News> selectkey(TypeUtil type);
}