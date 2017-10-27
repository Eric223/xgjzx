package com.dingyan.xgjzx.dao;

import java.util.List;

import com.dingyan.xgjzx.pojo.User;
import com.dingyan.xgjzx.util.PageUtil;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);

	User findByEmail(String email);

	User findByPhone(String phone);

	User login(String phone, String email, String password);

	int count();

	List<User> findAll(PageUtil page);
}