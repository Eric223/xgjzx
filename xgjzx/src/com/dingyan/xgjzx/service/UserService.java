package com.dingyan.xgjzx.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.dingyan.xgjzx.dao.UserDao;
import com.dingyan.xgjzx.pojo.User;
import com.dingyan.xgjzx.util.MD5Util;
import com.dingyan.xgjzx.util.PageUtil;
import com.dingyan.xgjzx.util.SendEmail;
import com.dingyan.xgjzx.util.SendMessage;

@Service
public class UserService {
	
	@Resource UserDao userdao;
	
	public Map<String,Object> findAll(String page){
		int count=this.userdao.count();
		PageUtil pg=new PageUtil().setall(count,Integer.parseInt(page),7);
		List<User> list=this.userdao.findAll(pg);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("consults",list);
		map.put("maxPage",pg.getAllpage());
		return map;
	}
	/**
	 * 验证邮箱是否注册
	 * @param email
	 * @return
	 */
	public int checkemail(String email) {
		User ch=this.userdao.findByEmail(email);
		if(ch==null){
			return 100;
		}else{
			return 101;
		}
	}
	/**
	 * 验证手机号是否注册
	 * @param phone
	 * @return 100未注册 102 已注册
	 */
	public int checkphone(String phone) {
		User ch=this.userdao.findByPhone(phone);
		if(ch==null){
			return 100;
		}else{
			return 101;
		}
	}
	/**
	 * 添加用户
	 * @param user 用户
	 */
	public void insertUser(User user) {
		this.userdao.insert(user);
	}
	/**
	 * 激活邮箱帐号
	 * @email 邮箱
	 * @validatecode 激活码
	 * @return 100激活成功 101 激活码错误 或该用户不存在
	 */
	public int activate(String email, String validatecode) {
		User user=this.userdao.findByEmail(email);
		if(user!=null){
			if(user.getCode().equals(validatecode)){
				user.setStatus(true);
				this.userdao.updateByPrimaryKey(user);
				System.out.println("激活成功");
				return 100;
			}else{
				System.out.println("激活码不正确");
				return 101;
			}
		}else{
			System.out.println("该用户不存在");
			return 101;
		}
	}
	/**
	 * 发送手机验证码
	 * @param phone 手机号
	 * @return 短信平台内部错误代码 
	 */
	public String sendcode(String phone,String number) {
		SendMessage sd=new SendMessage();
		String result=sd.Send(phone, number);
		return result;
	}
	/**
	 * 用户注册
	 * @param user  用户
	 * @param number 存入session中的验证码 
	 * @return result注册结果 user注册对象
	 */
	public Map<String,Object> register(String all) {
		Map<String,Object> map=new HashMap<String,Object>();
		JSONObject obj=JSONObject.fromObject(all);
		String user=obj.getString("user");
		//String code=obj.getString("code");手机验证码
		JSONObject json=JSONObject.fromObject(user);
		String email=json.getString("email");
		String phone=json.getString("phone");
			User us1=this.userdao.findByEmail(email);
			User us2=this.userdao.findByEmail(phone);
		if(us1!=null){
				map.put("result","101");//该邮箱已经注册
				map.put("warn","邮箱已注册");
				return map;
		}else if(us2!=null){
		map.put("result","101");//手机号已经注册
		return map;
		}else{
							//if(number.equals(code)){
									User uu=new User();
									uu.setName(json.getString("name"));
									uu.setPassword(json.getString("password"));
									uu.setEmail(email);
									uu.setPhone(json.getString("phone"));
									uu.setStatus(false);
									uu.setCode(MD5Util.encode2hex(email));
									this.userdao.insert(uu);
									System.out.println("register success");
									this.sendlink(email, uu);
									map.put("result","100");
									map.put("user",uu);
								return map;
							/*}else{
								map.put("result","101");//验证码输入错误
								return map;
							}*/
						}
		}	
	/**
	 * 发送激活链接
	 * @param email 邮箱
	 * @param user	用户
	 * @return 100 发送成功 101 发送失败
	 */
	 public int sendlink(String email ,User user){
		 if(user!=null){
			 StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");  
							sb.append("<a href=\"http://192.168.18.23:8080/xgjzx/register/activate?email=");  
							sb.append(email);   
							sb.append("&validateCode=");   
							sb.append(user.getCode());  
							sb.append("\">http://localhost:8080/xgjzx/register/activate?email=");   
							sb.append(email);  
							sb.append("&validateCode=");  
							sb.append(user.getCode());  
							sb.append("</a>");  
							//发送邮件 
							System.out.println("发送激活邮件");
							SendEmail.send(email, sb.toString()); 
							return 100;
		 }else{
			User uu= this.userdao.findByEmail(email);
			if(uu!=null){
				 StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");  
								sb.append("<a href=\"http://192.168.18.23:8080/xgjzx/register/activate?email=");  
								sb.append(email);   
								sb.append("&validateCode=");   
								sb.append(uu.getCode());  
								sb.append("\">http://localhost:8080/xgjzx/register/activate?email=");   
								sb.append(email);  
								sb.append("&validateCode=");  
								sb.append(uu.getCode());  
								sb.append("</a>");  
					//发送邮件  
						SendEmail.send(email, sb.toString()); 
								return 100;
			}else{
				System.out.println("激活的用户不存在");
				return 101;
			}
		 }
	 }
	public User findByphone(String phone) {
		return this.userdao.findByPhone(phone);
	}
	public User findByEmail(String email) {
		return this.findByEmail(email);
	}
	/**
	 * 登录 
	 * @param user 用户信息
	 * @return
	 */
	public Map<String,Object> login(String user) {
		System.out.println("登录的用户:"+user);
		JSONObject json=JSONObject.fromObject(user);
		String phone=json.getString("phone");
		String email=json.getString("email");
		String password=json.getString("password");
		Map<String, Object> map=new HashMap<String, Object>();
		if(phone.equals("")||phone==null){
				User uu=this.userdao.findByEmail(email);
					if(uu!=null){
								if(email.equals(uu.getEmail())&&password.equals(uu.getPassword())){
										if(uu.getStatus()!=false){
												String sessionid=MD5Util.encode2hex(uu.getEmail()+new Date()+uu.getId());
														map.put("name", uu.getName());
														map.put("result", "100");
														map.put("id",uu.getId());
														map.put("sessionid",sessionid);
														map.put("status",uu.getStatus());
														uu.setCode(sessionid);
														this.userdao.updateByPrimaryKey(uu);
														return map;
										}else{
												System.out.println("用户未激活");
												map.put("result","101");
												map.put("warn","用户未激活");
												return map;
										}
								}else{			
												System.out.println("密码错误");
												map.put("result","101");
												map.put("warn","密码错误");
										return map;
									}
					}else{
						System.out.println("用户不存在");
						map.put("result","101");
						map.put("warn","用户不存在");
						return map;
					}
		}else{
				User u=this.userdao.findByPhone(phone);
					if(u!=null){
								if(password.equals(u.getPassword())&&phone.equals(u.getPhone())){
										if(u.getStatus()!=false){
												String sessionid=MD5Util.encode2hex(u.getEmail()+new Date()+u.getId());
														map.put("name", u.getName());
														map.put("result", "100");
														map.put("id",u.getId());
														map.put("sessionid",sessionid);
														map.put("status",u.getStatus());
														u.setCode(sessionid);
														this.userdao.updateByPrimaryKey(u);
														return map;
										}else{
											System.out
											.println("用户未激活");
											map.put("result","101");
											map.put("warn","用户未激活");
										}
								}else{
									System.out.println("密码错误");
									map.put("result","101");
									map.put("warn","密码错误");
								}
					}else{
						System.out.println("用户不存在");
						map.put("result","101");
						map.put("warn","用户不存在");
					}
		}
			return map;
	}
	
}
