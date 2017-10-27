package com.dingyan.xgjzx.pojo;

import java.util.List;

/*
 * 创意评价
 */
public class Idea {
    private Integer id;
    //标题
    private String title;
    //原图
    private String img;
    //缩略图
    private String imgx;
    //评论id
    private Integer commentid;
    //日期
    private String date;
    
    private String icontent;
    //所有评论
    private List<Comment> listcomment;
    
	public List<Comment> getListcomment() {
		return listcomment;
	}

	public void setListcomment(List<Comment> listcomment) {
		this.listcomment = listcomment;
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

	public Integer getCommentid() {
		return commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIcontent() {
		return icontent;
	}

	public void setIcontent(String icontent) {
		this.icontent = icontent;
	}

	
    
    }