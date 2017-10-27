package com.dingyan.xgjzx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

	/**
	 *   用于后台页面跳转
	 * @author Administrator
	 *
	 */
@Controller
@RequestMapping("/back")
public class backController {

	@RequestMapping("")
	public String redirectback(HttpServletRequest request,HttpServletResponse response){
		return "redirect:manage/index.html";
	}
}
