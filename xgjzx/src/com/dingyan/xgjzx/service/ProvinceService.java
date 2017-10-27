package com.dingyan.xgjzx.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dingyan.xgjzx.dao.ProvinceDao;
import com.dingyan.xgjzx.pojo.Province;

@Service
public class ProvinceService {

	@Resource
	public ProvinceDao provincedao;
	
	public Province findByName(String name){
		return this.provincedao.findByName(name);
	}
	public int addProvince(Province province){
		return this.provincedao.insert(province);
	}
	public List<Province> findALLpro(){
		return this.provincedao.findAll();
		
	}
}
