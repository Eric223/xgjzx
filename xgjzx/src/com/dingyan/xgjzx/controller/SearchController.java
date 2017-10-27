package com.dingyan.xgjzx.controller;

import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.dingyan.xgjzx.service.BusService;

			
/*
 * 搜索公交车信息
 */
@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Resource BusService busservice;
	
	/**
	 * 根据条件查询公交车信息
	 * @param keyBus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/found",method=RequestMethod.POST)
	public Map searchkey(HttpServletRequest request,HttpServletResponse response,@RequestBody String keyBus){
			return	this.busservice.search(keyBus);
	}
}
