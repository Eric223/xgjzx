package com.dingyan.xgjzx.dao;

import java.util.List;

import com.dingyan.xgjzx.pojo.Idea;

public interface IdeaDao {
    int deleteByPrimaryKey(Integer tiId);

    int insert(Idea record);

    int insertSelective(Idea record);

    Idea selectByPrimaryKey(Integer tiId);

    int updateByPrimaryKeySelective(Idea record);

    int updateByPrimaryKeyWithBLOBs(Idea record);

    int updateByPrimaryKey(Idea record);
    
    List IdeaComment(Integer ididea);

	int count();
	List<Idea> findAll();
	List<Idea> IndexIdeaShow(int parseInt);
}