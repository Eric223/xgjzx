package com.dingyan.xgjzx.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dingyan.xgjzx.service.UserService;
import com.dingyan.xgjzx.util.randomUtil;

	/**
	 * 注册  短信注册并未使用 目前只采用了邮箱验证激活
	 */
@Controller
@RequestMapping("/register")
public class RegisterController {
		
		@Resource UserService userservice;

		/**
		 *  用户注册
		 * @param session
		 * @param all 用户
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/signin")
		public Map signin(HttpSession session,@RequestBody String user){
			//System.out.println("---sign---》"+request.getRequestedSessionId());
			//Object obj=session.getAttribute("whatsup");
			//Map<String,Object> map=new HashMap<String,Object>();
			/*if(obj!=null&&!obj.equals("")){
				System.out.println("验证码通过");
				Map mm=this.userservice.register(user,obj.toString());
					return mm;
			}else{
				System.out.println("请输入验证码");
				map.put("result","101");
				return map;
			}*/
				Map mm=this.userservice.register(user);
					return mm;
		}
		//发送手机验证码
		@ResponseBody
		@RequestMapping("/code")
		public String zxccode(HttpSession session,@RequestBody String phone){
			JSONObject json=JSONObject.fromObject(phone);
			String mobile=json.getString("phone");
			System.out.println("---发送验证码到--->"+phone);
			randomUtil ru=new randomUtil();
			String Number=ru.getrandom();
			session.setAttribute("whatsup",Number);
			System.out.println("--num---->"+session.getAttribute("whatsup"));
			//String result=this.userservice.sendcode(mobile,Number);
			System.out.println("----->result"+Number);
			    	return Number+"";
		}
		//验证邮箱激活
		@ResponseBody
		@RequestMapping("/activate")
		public String sendmail(HttpServletRequest request,@RequestParam("email") String email,HttpServletResponse response,
				@RequestParam("validateCode") String validatecode){
				System.out.println("-----激活邮箱----->"+email);
				System.out.println("-----激活验证码----->"+validatecode);
				this.userservice.activate(email,validatecode);
			return "激活成功";
		}
		//发送激活链接
		@ResponseBody
		@RequestMapping("/sendlink")
		public int sendlink(HttpServletRequest request,HttpServletResponse response,
				@RequestParam("email") String email
				){
			return this.userservice.sendlink(email, null);
		}
		//验证手机号是否注册
		@ResponseBody
		@RequestMapping(value="/checkphone",method=RequestMethod.GET)
		public int checkphone(HttpServletRequest request,HttpServletResponse response,
				@RequestParam("phone") String phone
				){
			System.out.println("验证手机:"+phone);
			return this.userservice.checkphone(phone);//
		}
		//验证邮箱是否注册
		@ResponseBody
		@RequestMapping(value="/checkemail",method=RequestMethod.GET)
		public int checkemail(HttpServletRequest request,HttpServletResponse response,
				@RequestParam("email") String email
				){
			System.out.println("验证邮箱:"+email);
			return this.userservice.checkemail(email);
		
}
}