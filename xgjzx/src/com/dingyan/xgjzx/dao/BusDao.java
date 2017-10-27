package com.dingyan.xgjzx.dao;

import java.util.List;
import java.util.Map;

import com.dingyan.xgjzx.pojo.Bus;
import com.dingyan.xgjzx.util.PageUtil;

public interface BusDao {
    int deleteByPrimaryKey(Integer busId);

    int insert(Bus bus);

    int insertSelective(Bus record);

    Bus selectByPrimaryKey(Integer busId);

    int updateByPrimaryKeySelective(Bus record);
    
    int updateByPrimaryKey(Bus record);
    
    List<Bus> findAllBus(PageUtil page);

	int countnum();

	List<Bus> search(Map map);

	String findSingle(Bus bus);

	List check(Bus bus);

	int countSearch(Bus bus);

}