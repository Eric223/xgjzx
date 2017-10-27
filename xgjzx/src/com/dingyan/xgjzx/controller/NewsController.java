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

import com.dingyan.xgjzx.pojo.News;
import com.dingyan.xgjzx.service.NewsSerivce;

/**
 * 新闻咨询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/news")
public class NewsController{
	
	@Resource NewsSerivce newsservice;
	
	/**
	 * 分页查询新闻
	 * @param page 当前页码
	 */
	@ResponseBody
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public Map<String,Object> select(HttpServletRequest request,HttpServletResponse response,@RequestParam("page") String page){
		Map<String,Object>  map=this.newsservice.selectAll(page);
			//this.newsservice.cleanpicture();
				return map;
	}
	/**
	 * 单个新闻信息获取
	 */
	@ResponseBody
	@RequestMapping(value="/single",method=RequestMethod.GET)
	public News SingleNews(HttpServletRequest request,HttpServletResponse response,@RequestParam("newsid") String id){
		return this.newsservice.single(Integer.parseInt(id));
	}
	/**
	 * 分组查询     用于首页部分展示各类别的信息
	 * @param TypeId 类别  num 数目
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/groupby",method=RequestMethod.GET)
	public List<News> groupby(HttpServletRequest request,HttpServletResponse response,@RequestParam("TypeId") String tid,@RequestParam("num") String num){
		List<News> list=this.newsservice.groupBy(tid,num);
		return list;
	}
	/**
	 * 添加修改新闻
	 * @param News 新闻
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public int insertconsult(HttpServletRequest request,HttpServletResponse response,
			//@RequestParam("title") String title,
			//@RequestParam("content") String content,
			@RequestBody String News) throws ParseException{
				System.out.println(">>>>>>>>>"+News);
		return this.newsservice.insertupdate(News);
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
		//System.out.println("----------insert photo---------");
		return this.newsservice.addphoto(path,file);
	}
	@RequestMapping("/test")
	public void  asdad(HttpServletRequest request,HttpServletResponse response){
			
		String path=request.getSession().getServletContext().getRealPath("");
		System.out.println("----------insert photo---------");
		System.out.println(">>>>"+path);
	}
	/**
	 * 删除新闻
	 * @param delid 删除Id
	 * @param page 当前页
	 */
	@ResponseBody
	@RequestMapping("/delete") 
	public int deleteconsult(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("delid") String delid,
			@RequestParam("page") String page){
		String path=request.getSession().getServletContext().getRealPath("");
		 return this.newsservice.deleteById(path,delid,page);
	}
}
