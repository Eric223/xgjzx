package com.dingyan.xgjzx.dao;

import java.util.List;

import com.dingyan.xgjzx.pojo.Comment;

public interface CommentDao{
    int deleteByPrimaryKey(Integer ticId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer ticId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
    
    List selectAll();
}