package com.dingyan.xgjzx.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.dingyan.xgjzx.dao.CustomerDao;
import com.dingyan.xgjzx.pojo.Customer;
import com.dingyan.xgjzx.pojo.News;
import com.dingyan.xgjzx.util.PageUtil;

@Service
public class CustomerService {
   
	@Resource CustomerDao customerdao;
	/**
	 * 查询
	 * @param page
	 * @return
	 */
	public Map<String, Object> selectAll(String page) {
		PageUtil pp=new PageUtil();
			int count=this.customerdao.count();
			System.out.println("一共有"+count+"条");
		PageUtil pg=pp.setall(count, Integer.parseInt(page), 10);
		List<Customer> listcus=this.customerdao.selectAll(pg);
		List nlist=new ArrayList();
		for (Customer c : listcus) {
				Calendar cl=Calendar.getInstance();
				cl.setTime(c.getDate());
				Map<String,Object> mm=new HashMap<String,Object>();
				mm.put("id",c.getId()+"");
				mm.put("company",c.getCompany());
				mm.put("city",c.getCity());
				mm.put("name", c.getName());
				mm.put("phone", c.getPhone());
				mm.put("status", c.getStatus());
				mm.put("date",cl.get(Calendar.YEAR)+"-"+(cl.get(Calendar.MONTH)+1)+"-"+cl.get(Calendar.DATE));
				nlist.add(mm);
		}
		JSONArray json=JSONArray.fromObject(nlist);
		Map<String,Object> map=new HashMap<String,Object>();
		System.out.println("。。。。。>"+json);
		map.put("maxPage",pg.getAllpage());
		map.put("customers",nlist);
		   return map;
	}
	/**
	 * 查询未读信息
	 * @param page
	 * @return
	 */
	public Map<String, Object> selectwd(String page) {
		PageUtil pp=new PageUtil();
		int count=this.customerdao.count();
	PageUtil pg=pp.setall(count, Integer.parseInt(page), 10);
	List<Customer> listcus=this.customerdao.selectnew(pg);
	List nlist=new ArrayList();
	for (Customer c : listcus) {
			Calendar cl=Calendar.getInstance();
			cl.setTime(c.getDate());
			Map<String,Object> mm=new HashMap<String,Object>();
			mm.put("id",c.getId()+"");
			mm.put("company",c.getCompany());
			mm.put("city",c.getCity());
			mm.put("name", c.getName());
			mm.put("phone", c.getPhone());
			mm.put("status", c.getStatus());
			mm.put("date",cl.get(Calendar.YEAR)+"-"+(cl.get(Calendar.MONTH)+1)+"-"+cl.get(Calendar.DATE));
			nlist.add(mm);
	}
	JSONArray json=JSONArray.fromObject(nlist);
	Map<String,Object> map=new HashMap<String,Object>();
	System.out.println("。。。。。>"+json);
	map.put("maxPage",pg.getAllpage());
	map.put("customers",nlist);
	   return map;
	}
	/**
	 * 查询已读信息
	 * @param page
	 * @return
	 */
	public Map<String, Object> selectold(String page) {
		PageUtil pp=new PageUtil();
		int count=this.customerdao.count();
	PageUtil pg=pp.setall(count, Integer.parseInt(page), 10);
	List<Customer> listcus=this.customerdao.selectold(pg);
	List nlist=new ArrayList();
	for (Customer c : listcus) {
			Calendar cl=Calendar.getInstance();
			cl.setTime(c.getDate());
			Map<String,Object> mm=new HashMap<String,Object>();
			mm.put("id",c.getId()+"");
			mm.put("company",c.getCompany());
			mm.put("city",c.getCity());
			mm.put("name", c.getName());
			mm.put("phone", c.getPhone());
			mm.put("status", c.getStatus());
			mm.put("date",cl.get(Calendar.YEAR)+"-"+(cl.get(Calendar.MONTH)+1)+"-"+cl.get(Calendar.DATE));
			nlist.add(mm);
	}
	JSONArray json=JSONArray.fromObject(nlist);
	Map<String,Object> map=new HashMap<String,Object>();
	System.out.println("。。。。。>"+json);
	map.put("maxPage",pg.getAllpage());
	map.put("customers",nlist);
	   return map;
	}
	/**
	 * 添加修改
	 * @param Customer客户信息
	 * @return
	 */
	public int insertupdate(String Customer) {
		System.out.println("Customer>>>>>>>"+Customer);
		JSONObject alljson=JSONObject.fromObject(Customer);
		System.out.println("------>"+alljson);
		//String  page=alljson.getString("page");//页码
		String  sjson=alljson.getString("data");//添加的客户信息
		JSONObject json=JSONObject.fromObject(sjson);
		if(json.getString("company")!=null&&!json.getString("company").equals("")){
					if(json.getString("name")!=null&&!json.getString("name").equals("")){
								if(json.getString("city")!=null&&!json.getString("city").equals("")){
											if(json.getString("phone")!=null&&!json.getString("phone").equals("")){
																	Customer customer=new Customer();
																	customer.setCompany(json.getString("company"));
																	customer.setName(json.getString("name"));
																	customer.setCity(json.getString("city"));
																	customer.setPhone(json.getString("phone"));
																	customer.setDate(new Date());
																	customer.setStatus(false);
																	if(json.getString("id").equals("id")||json.getString("id").equals("")){
																		customer.setStatus(false);
																		int a=this.customerdao.insert(customer);
																		this.selectAll("1");
																			return 100;
																	}//else if(json.getString("id").equals("")||json.getString("id")==null){ return 101;}
																	else{
																		customer.setId(Integer.parseInt(json.getString("id")));
																		int a=this.customerdao.updateByPrimaryKey(customer);
																		this.selectAll("1");
																			return 100;
																	}
											}else{
												System.out.println("手机号不能为空");
												return 101;
											}
								}else{
									System.out.println("城市不能为空");
									return 101;
								}
					}else{
						System.out.println("姓名不能为空");
						return 101;
					}
		}else{
			System.out.println("公司名称不能为空");
			return 101;
		}
	}
	
	/**
	 * 删除
	 * @param delid
	 * @param page
	 * @return
	 */
	public int deleteById(String delid, String page) {
		int a=this.customerdao.deleteByPrimaryKey(Integer.parseInt(delid));
		this.selectAll(page);
		if(a==1){
			System.out.println("shanchuchenggong");
			return 100;
		}else{
			System.out.println("删除失败");
			return 101;
		}
	}
	public int countnum() {
		return this.customerdao.countnum();
	}
	/**
	 * 修改读取状态
	 * @param id
	 * @param page
	 * @return
	 */
	public int updatestatus(String result) {
		 JSONObject json= JSONObject.fromObject(result);
		 String id=json.getString("id");
		 System.out.println("idshi------>"+id);
		 String page=json.getString("page");
		 Customer customer=this.customerdao.findbyid(id);
		 if(customer!=null){
			 customer.setStatus(true);
			 this.customerdao.updateByPrimaryKey(customer);
			 this.selectAll(page);
			 return 100;
		 }else{
			 System.out.println("修改的用户不存在");
			 return 101;
		 }
	}
}
