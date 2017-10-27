package com.dingyan.xgjzx.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.dingyan.xgjzx.dao.AdminDao;
import com.dingyan.xgjzx.pojo.Admin;

@Service
public class AdminService {
 
	@Resource AdminDao admindao;
	/**
	 * 后台登录
	 * @param admin  用户
	 * @return
	 */
	public int login(String admin) {
		// TODO Auto-generated method stub
		System.out.println("----admin"+admin);
	JSONObject json=JSONObject.fromObject(admin);
			String username=json.getString("username");
			String password=json.getString("password");
					if(username!=null&&!username.equals("")){
									if(password!=null&&!password.equals("")){
												Admin adm=new Admin();
												adm.setUsername(username);
												adm.setPassword(password);
												Admin ad=this.admindao.login(adm);
													if(ad!=null){
														return 100;
													}else{
														System.out.println("输入的用户名或密码错误");
														return 101;
													}
									}else{
										System.out.println("密码不能为空");
										return 101;
									}
					}else{
					System.out.println("用户名不能为空");
					return 101;
				}
			
	}
}
