package com.dingyan.xgjzx.pojo;

import java.util.Date;
/**
 * 	客户投放信息
 * @author yang
 *
 */
public class Customer {
    
	private Integer id;
    //公司
    private String company;
    //姓名
    private String name;
    //城市
    private String city;
    //手机号
    private String phone;
    //读取状态
    private boolean status;
    //日期
    private Date date;
    
    
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

   
}