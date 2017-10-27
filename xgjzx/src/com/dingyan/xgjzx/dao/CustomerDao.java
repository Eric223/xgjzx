package com.dingyan.xgjzx.dao;

import java.util.List;

import com.dingyan.xgjzx.pojo.Consult;
import com.dingyan.xgjzx.pojo.Customer;
import com.dingyan.xgjzx.util.PageUtil;

public interface CustomerDao {
    int deleteByPrimaryKey(Integer tcId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer tcId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

	List<Customer> selectAll(PageUtil pg);
	
	List<Customer> selectnew(PageUtil pg);
	
	List<Customer> selectold(PageUtil pg);

	int count();

	int countnum();

	Customer findbyid(String id);

}