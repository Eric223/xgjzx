package com.dingyan.xgjzx.controller;

import java.text.ParseException;

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

import com.dingyan.xgjzx.service.CustomerService;

/**
 *   联系我们 首页左边部分 客户提交的信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Resource CustomerService customerservice;
	/**
	 * 查询客户信息
	 */
	@ResponseBody
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public Map<String,Object> select(HttpServletRequest request,HttpServletResponse response,@RequestParam("page") String page){
		Map<String,Object>  map=this.customerservice.selectAll(page);
				return map;
	}
	/**
	 * 未读条数
	 * 后台部分用于展示未读的客户信息
	 */
	 @ResponseBody
	 @RequestMapping(value="/countnum",method=RequestMethod.GET)
	 public int countnum(HttpServletRequest request,HttpServletResponse response){
				int a=this.customerservice.countnum();
					return a;
		}
	/**
	 * 查询未读信息
	 * @param page 当前页码
	 */
	@ResponseBody
	@RequestMapping(value="/newcustomer",method=RequestMethod.GET)
	public Map<String,Object> selectwd(HttpServletRequest request,HttpServletResponse response,@RequestParam("page") String page){
		Map<String,Object>  map=this.customerservice.selectwd(page);
				return map;
	}
	/**
	 * 查询已读信息
	 */
	@ResponseBody
	@RequestMapping(value="/oldcustomer",method=RequestMethod.GET)
	public Map<String,Object> selectold(HttpServletRequest request,HttpServletResponse response,@RequestParam("page") String page){
		Map<String,Object>  map=this.customerservice.selectold(page);
				return map;
	}
	/**
	 * 添加客户信息
	 * @param customer 前台部分客户提交的个人信息
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public int insertconsult(HttpServletRequest request,HttpServletResponse response,
			//@RequestParam("title") String title,
			//@RequestParam("content") String content,
			@RequestBody String customer) throws ParseException{
				System.out.println(">>>>>>>>>"+customer);
		return this.customerservice.insertupdate(customer);
	}
	/**
	 * 更新读取状态
	 * @param id
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updatestatus",method=RequestMethod.POST) 
	public int updatestatus(HttpServletRequest request,HttpServletResponse response,
			@RequestBody String result
			){
		 return this.customerservice.updatestatus(result);
	}
	/**
	 * 删除客户信息
	 * @param delid  删除id
	 * @param page	 当前页码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete") 
	public int deleteconsult(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("delid") String delid,
			@RequestParam("page") String page
			){
		 return this.customerservice.deleteById(delid,page);
	}
}
