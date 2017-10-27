package com.dingyan.xgjzx.dao;

import java.util.List;
import java.util.Map;

import com.dingyan.xgjzx.pojo.Consult;
import com.dingyan.xgjzx.util.PageUtil;

public interface ConsultDao {
    int deleteByPrimaryKey(Integer ctId);

    int insert(Consult consult);

    int insertSelective(Consult consult);

    Consult selectByPrimaryKey(Integer ctId);

    int updateByPrimaryKeySelective(Consult record);

    int updateByPrimaryKey(Consult record);
    
    List<Consult> selectAll(PageUtil page);

	int count();

	Consult findById(int id);

	List<Consult> findAll();

	List<Consult> search(Map map);

	int countsearch(String type);
}