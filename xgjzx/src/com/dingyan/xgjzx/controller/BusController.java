package com.dingyan.xgjzx.controller;

import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import com.dingyan.xgjzx.service.BusService;


/*
 * 
 * 公交车
 * 
 */
@Controller
@RequestMapping("/bus")
public class BusController  {
	
	@Resource BusService busservice;
	
	/**
	 * 查询公交车信息
	 * @param page 当前页
	 */
	@RequestMapping(value="/find",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> findAllBus(HttpServletRequest request,HttpServletResponse response, @RequestParam("page") String page){
			return this.busservice.findAllBus(page);
	}
	
	/**
	 * 添加修改公交车信息
	 * @param      params  公交车
	 */
	@ResponseBody
	@RequestMapping(value="/addupd",method=RequestMethod.POST)
	public int updatebus(HttpServletRequest request,HttpServletResponse response,
			@RequestBody String params){
			return this.busservice.updateBus(params);
	} 
	/**
	 * 删除公交车信息
	 * 
	 * @param id 删除的公交车id
	 * @param page 当前页码
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public int deleteBus(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id") String delId,
			@RequestParam("page") String page
			){
		return this.busservice.deleteById(delId,page);
	}
	/**
	 * 查询单个公交车详情
	 * @param city 城市
	 * @param route 线路
	 * @param level 级别
	 */
	@ResponseBody
	@RequestMapping(value="/single",method=RequestMethod.GET)
	public Map<String,Object> SingleBus(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("city") String city,
			@RequestParam("route") String route,
			@RequestParam("level") String level){
		return this.busservice.findSingle(city,route,level);
		
	}
}