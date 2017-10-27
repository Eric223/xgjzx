package com.dingyan.xgjzx.pojo;

import java.io.Serializable;
	/**
	 * 新闻咨询
	 * @author Administrator
	 *
	 */
public class News implements Serializable {
    private Integer id;
    
    private Integer type;

    private String title;

    private String img;

    private String imgx;

    private String date;

    private String content;
    
    private String txt;
    
    private String origin;
    
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImgx() {
		return imgx;
	}

	public void setImgx(String imgx) {
		this.imgx = imgx;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

   
}