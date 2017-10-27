package com.dingyan.xgjzx.service;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.dingyan.xgjzx.dao.IdeaDao;

import com.dingyan.xgjzx.pojo.Idea;
import com.dingyan.xgjzx.util.ImageUtil;

	/**
	 * 创意评价
	 * @author Administrator
	 *
	 */
@Service
public class IdeaService {
	
	@Resource IdeaDao ideadao;
	/**
	 * 查询所有创意评价
	 */
	public Map<String,Object> selectAll(String page){
		Map<String,Object> map=new HashMap<String, Object>();
		
		return map;
	}
	/**
	 * 首页显示最新创意评价
	 * @param num
	 * @return
	 */
	public List IndexIdeaShow(String num) {
		return this.ideadao.IndexIdeaShow(Integer.parseInt(num));
	}
	/**
	 * 查询单个创意评价
	 * @param ideaid 查询的id
	 * @return 单个创意以及5条评价
	 */
	 public List IdeaAndComment(String ideaid){
		 List listIdeaComment=new ArrayList();
		List list=this.ideadao.IdeaComment(Integer.parseInt(ideaid));
		 JSONArray json=JSONArray.fromObject(listIdeaComment);
		 	System.out.println("<><><><><>"+json);
		 return list;
	 }
	/**
	 * 添加修改创意板块
	 * @param Idea
	 * @return
	 */
	public int insertupdate(String Idea) {
		JSONObject js=JSONObject.fromObject(Idea);
		if(js.getString("page")!=null||!js.getString("page").equals("")){
			if(js.getString("data")!=null||!js.getString("data").equals("")){
						String page=js.getString("page");
						String data=js.getString("data");
			JSONObject	json=JSONObject.fromObject(data);
						if(json.getString("content")!=null||!json.getString("content").equals("")){
								if(json.getString("img")!=null||!json.getString("img").equals("")){
												if(json.getString("title")!=null||!json.getString("title").equals("")){
																Idea idea=new Idea();
															List<String> lsyt=JSONArray.fromObject(json.getString("img"));//将多张图片地址转为list集合
															List<String> slt=new ArrayList<String>();
															for (String s: lsyt){//遍历产生缩略图地址
																String img2=s.substring(0,s.lastIndexOf("."))+"xt"+s.substring(s.lastIndexOf("."),s.length());
																  slt.add(img2);
															}
															 String slts=JSONArray.fromObject(slt).toString();
																	idea.setIcontent(json.getString("content"));
																	idea.setDate(new Date().toLocaleString());
																	idea.setImg(json.getString("img"));
																	idea.setTitle(json.getString("title"));
																	idea.setImgx(slts);
																	try {
																		idea.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("date")).toLocaleString());
																	} catch (ParseException e1) {
																		// TODO Auto-generated catch block
																		e1.printStackTrace();
																	}
																if(json.getString("id")==null||json.getString("id").equals("")){//如果id不存在或者为""则添加
																	this.ideadao.insert(idea);
																	this.selectAll(page);
																		return 100;
																}
																else{//否则执行修改 修改之前删除无效的图片
																	 String imgs=this.ideadao.selectByPrimaryKey(json.getInt("id")).getImg();
																		ImageUtil.deletephoto(imgs,json.getString("img"));
																		String imgxs=this.ideadao.selectByPrimaryKey(json.getInt("id")).getImgx();
																		ImageUtil.deletephoto(imgxs, slts);
																		try {
																			idea.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("date")).toLocaleString());
																			} catch (ParseException e) {
																				System.out.println("日期格式输入不正确");
																				return 101;
																			}
																	idea.setId(Integer.parseInt(json.getString("id")));
																	this.ideadao.updateByPrimaryKey(idea);
																	this.selectAll(page);
																	return 100;
																}
												}else{
													System.out.println("标题不能为空");
													return 101;
												}
								}else{
									System.out.println("图片不能为空");
									return 101;
								}
						}else{
							System.out.println("内容不能为空");
							return 101;
						}
			}else{
				System.out.println("data不存在");
				return 101;
			}
	}else{
		System.out.println("page不存在");
		return 101;
	}		
	}

	
}
