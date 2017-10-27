package com.dingyan.xgjzx.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 咨询信息
 * @author Administrator
 *
 */
public class Consult implements Serializable{
	//id
    private Integer id;
    //类型
    private String type;
    //标题
    private String title;
    //内容
    private String content;
    //作者
    private String author;
    //图片
    private String img1;
    //缩略图
    private String img2;
    
    private String origin;
    
    private String logo;
    
    private String date;
	
	public Consult() {
	}
	public Consult(Integer id, String type, String title, String content,
			String author, String img1, String img2, String logo, String date,String origin) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.content = content;
		this.author = author;
		this.img1 = img1;
		this.img2 = img2;
		this.logo = logo;
		this.date = date;
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "Consult [id=" + id + ", title=" + title + ", content="
				+ content + ", img1=" + img1 + "]";
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}

	

    
}