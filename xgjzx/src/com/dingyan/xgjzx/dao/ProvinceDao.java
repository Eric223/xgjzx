package com.dingyan.xgjzx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.dingyan.xgjzx.pojo.Province;

public interface ProvinceDao {
    int deleteByPrimaryKey(Integer pId);

    int insert(Province record);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);
    @Select("select *from t_province where p_province_name=#{name}")
    Province findByName(String name);

    @Select("select *from t_province")
	List<Province> findAll();
}