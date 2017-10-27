package com.dingyan.xgjzx.controller;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;

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

import org.springframework.web.multipart.MultipartFile;

import com.dingyan.xgjzx.pojo.Consult;
import com.dingyan.xgjzx.service.ConsultService;

	/**
	 * 案例展示
	 * 
	 */
@Controller
@RequestMapping("/consult")
public class ConsultController {
	
	@Resource ConsultService consultService;
	/**
	 * 分页查询所有案例(用于后台部分)
	 * @param page 当前页码
	 */
	@ResponseBody
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public Map<String,Object> select(HttpServletRequest request,HttpServletResponse response,@RequestParam("page") String page){
		Map<String,Object>  map=this.consultService.selectAll(page);
				return map;
	}
	 /**
	  * 查询所有案例展示(用于前台部分)
	  */
	@ResponseBody
	@RequestMapping(value="/findall" ,method=RequestMethod.GET)
	public List<Consult> findAll(HttpServletRequest request,HttpServletResponse response){
	return this.consultService.findAll();
	}
				
	/**
	 * 按类别筛选案例展示
	 * @param data 包含 当前页 和 类别
	 * @return 一组案例展示对象以及总页数
	 */
	@ResponseBody
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public Map<String,Object> search(HttpServletRequest request,HttpServletResponse response,
			@RequestBody String data){
			return this.consultService.search(data);
	}
	/**
	 * 单个案例展示详情
	 * @param  id 查询id
	 * @return 单个案例展示对象
	 */
	@ResponseBody
	@RequestMapping(value="/single",method=RequestMethod.GET)
	public Consult SingleShow(HttpServletRequest request,HttpServletResponse response,@RequestParam("consultid") String id){
		return this.consultService.Single(id);
	}
	/**
	 * 添加修改咨询
	 * @param consult 咨询
	 * @throws ParseException
	 * @return 100添加成功 101添加失败 
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public int insertconsult(HttpServletRequest request,HttpServletResponse response,
			//@RequestParam("title") String title,
			//@RequestParam("content") String content,
			@RequestBody String consult) throws ParseException{
		return this.consultService.insertupdate(consult);
	}
	/**
	 *  添加图片
	 * @param request
	 * @param response
	 * @param file 图片集合
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/uploadimage",method=RequestMethod.POST)
	public String  addphoto(HttpServletRequest request,HttpServletResponse response,@RequestParam("upfile") MultipartFile file) throws UnsupportedEncodingException{
		String path=request.getSession().getServletContext().getRealPath("");
		return this.consultService.addphoto(path,file);
	}
	/**
	 * 删除案例展示
	 * @param delid 删除id
	 * @param page 当前页码
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.GET) 
	public int deleteconsult(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("delid") String delid,
			@RequestParam("page") String page){
		String path=request.getSession().getServletContext().getRealPath("");
		return this.consultService.deleteById(path,delid,page);
	}
}

