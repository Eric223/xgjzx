package com.dingyan.xgjzx.pojo;
 /*
  * 评论
  */
public class Comment {
	
    private Integer id;
    //创意id
    private Integer ideaid;
    //内容
    private String content;
    //用户
    private String user;
    //日期
    private String date;
    
	public Integer getIdeaid() {
		return ideaid;
	}

	public void setIdeaid(Integer ideaid) {
		this.ideaid = ideaid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}