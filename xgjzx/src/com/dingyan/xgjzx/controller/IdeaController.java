package com.dingyan.xgjzx.controller;

import java.util.List;

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

import com.dingyan.xgjzx.service.IdeaService;

	/**
	 * 创意评价（由于前端人员变动,未完待续）
	 * @author Administrator
	 *
	 */
@Controller
@RequestMapping("/idea")
public class IdeaController {

	@Resource IdeaService ideaservice;
	/**
	 * 查询所有
	 * @param page 当前页码 
	 * @return map 所有结果 
	 */
	@ResponseBody
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public Map<String,Object> selectIdea(HttpServletRequest request,HttpServletResponse response,@RequestParam("page") String page){
				Map<String,Object> map=this.ideaservice.selectAll(page);
				return map;
	}
	/**
	 * 查询   单个创意评价详情 
	 * @param ideaid  查询id
	 */
	@ResponseBody
	@RequestMapping(value="/single",method=RequestMethod.GET)
	public List IdeaAndComment(HttpServletRequest request,HttpServletResponse response,@RequestParam("ideaid") String ideaid){
				List list=this.ideaservice.IdeaAndComment(ideaid);
				return list;
	}
	/**
	 * 首页显示创意评价
	 * @param 
	 */
	@ResponseBody
	@RequestMapping(value="/show",method=RequestMethod.GET)
	 public List IndexIdeaShow(HttpServletRequest request,HttpServletResponse response,@RequestParam("num") String num){
		 	List list=this.ideaservice.IndexIdeaShow(num);
		 return list;
	 }
	/**
	 * 添加修改创意
	 * @param request
	 * @param response
	 * @param idea
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertupdate",method=RequestMethod.POST)
	public int insertupdate(HttpServletRequest request,HttpServletResponse response,
			@RequestBody String Idea){
	return this.ideaservice.insertupdate(Idea);
	}
	/**
	 * 删除创意
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public int deleteIdea(){
		return 100;
	}
}
