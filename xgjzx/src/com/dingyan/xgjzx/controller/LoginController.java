package com.dingyan.xgjzx.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import com.dingyan.xgjzx.service.AdminService;
import com.dingyan.xgjzx.service.UserService;
/**
 *  登录与找回密码(未完成)
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Resource UserService userService;
	
	@Resource AdminService adminservice;
	/**
	 * 前台登录
	 * @param user 用户
	 * @return SessionId:唯一  Id:用户id  status:用户激活状态   name:用户昵称
	 */
	@ResponseBody
	@RequestMapping(value="/sign",method=RequestMethod.POST)
	public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response,
			@RequestBody String user){
					System.out.println("<-----------------login--------------------->");
					return this.userService.login(user);
					
	}
	/**
	 * 后台登录
	 * @param  admin 用户信息
	 * @return 100成功 101 失败
	 */
	@ResponseBody
	@RequestMapping("/backsign")
	public int backsign(HttpServletRequest request,@RequestBody String admin,HttpServletResponse response){
		return this.adminservice.login(admin);
	}
}
