package com.dingyan.xgjzx.service;

import java.io.File;

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

import org.springframework.web.multipart.MultipartFile;

import com.dingyan.xgjzx.dao.NewsDao;

import com.dingyan.xgjzx.pojo.News;

import com.dingyan.xgjzx.util.TypeUtil;

import com.dingyan.xgjzx.util.ImageUtil;

import com.dingyan.xgjzx.util.PageUtil;

@Service
public class NewsSerivce {
	@Resource NewsDao newsdao;
	/**
	 * 查询所有的咨询信息
	 * @param page 当前页
	 * @return
	 */
	/**
	 * 查询所有的咨询信息
	 * @param page 当前页
	 * @return
	 */
	public Map<String,Object> selectAll(String page) {
		int nowpage=1;
		try {
			nowpage=Integer.parseInt(page);
		} catch (Exception e) {
			System.out.println("你输入的格式不正确");
		}
		PageUtil pag=new PageUtil();
		int num=this.newsdao.count();
		PageUtil pp=pag.setall(num,nowpage,6);//num总条数,page当前页,6每页数目
		List<News> list=this.newsdao.selectAll(pp);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("consults",list);
		map.put("maxPage",pp.getAllpage());
		return map;
	}
	/**
	 * 添加修改
	 * @param path 
	 * @param consult 行业咨询
	 * @return
	 * @throws ParseException 
	 */
	public int insertupdate(String news) throws ParseException{
		System.out.println("news>>>>>>>"+news);
		JSONObject alljson=JSONObject.fromObject(news);
		System.out.println("------>"+alljson);
		String  page=alljson.getString("page");//页码
		String  sjson=alljson.getString("data");//所有数据
		JSONObject json=JSONObject.fromObject(sjson);
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				News con=new News();
			String  listyt=json.getString("img");
			String  slts="";
		if(json.getString("title")!=null&&!json.getString("title").equals("")){
					if(json.getString("content")!=null&&!json.getString("content").equals("")){
								if(json.getString("txt")!=null&&!json.getString("txt").equals("")){
											if(json.getString("type")!=null&&!json.getString("type").equals("")){
												if(json.getString("origin")!=null&&!json.getString("origin").equals("")){
													if(listyt!=null&&!listyt.equals("")){//判断如果图片不为空 则生成缩略图
														List<String> lsyt=JSONArray.fromObject(listyt);//将多张图片地址转为list集合
														System.out.println("修改的图片地址:"+lsyt);
														List<String> slt=new ArrayList<String>();
														for (String s: lsyt){//遍历产生缩略图地址
															String img2=s.substring(0,s.lastIndexOf("."))+"xt"+s.substring(s.lastIndexOf("."),s.length());
															  slt.add(img2);
														}
														slts=JSONArray.fromObject(slt).toString();	
													}
														con.setImg(listyt);
														con.setImgx(slts);
													    con.setType(Integer.parseInt(json.getString("type")));
														con.setTitle(json.getString("title"));
														con.setTxt(json.getString("txt"));
														con.setOrigin(json.getString("origin"));
														con.setContent(json.getString("content"));
														if(json.getString("date")==null&&json.getString("date").equals("")){
															con.setDate(new Date().toLocaleString());
														}else{
															 con.setDate(sd.parse(json.getString("date")).toLocaleString());
														}
													if(json.getString("id").equals("id")||json.getString("id").equals("")){//如果id为id执行添加否则执行修改
														this.newsdao.insert(con);
														System.out.println("添加成功");
														this.selectAll(page);
														return 100;
													}else{//修改之前删除原有图片
														String imgs=this.newsdao.selectByPrimaryKey(json.getInt("id")).getImg();
														ImageUtil.deletephoto(imgs,listyt);
														String imgxs=this.newsdao.selectByPrimaryKey(json.getInt("id")).getImgx();
														ImageUtil.deletephoto(imgxs, slts);
														con.setId(Integer.parseInt(json.getString("id")));
														this.newsdao.updateByPrimaryKey(con);
														this.selectAll(page);
														return 100;
												    }
											}else{
												System.out.println("来源不能为空");
												return 101;
											}
											}else{
												System.out.println("类型不能为空");
												return 101;
											}
								}else{
									System.out.println("纯文本不能为空");
									return 101;
								}
					}else{
						System.out.println("内容不能为空");
						return 101;
					}
		}else{
			System.out.println("标题不能为空");
			return 101;
		}	
	}
	/**
	 * 删除
	 * @param delid
	 */
	public int deleteById(String path,String delid,String page) {
		// TODO Auto-generated method stub
		News con=this.newsdao.selectByPrimaryKey(Integer.parseInt(delid));
		List<String> listIMG= JSONArray.fromObject(con.getImg());
		List<String> listIMG2= JSONArray.fromObject(con.getImgx());
		for (String s : listIMG){
			File ff=new File(path+s.substring(6,s.length()));
			ff.delete();
		}
		for (String s : listIMG2){
		File ff=new File(path+s.substring(6,s.length()));
		//System.out.println(ff.getAbsolutePath());
		ff.delete();
		}
		int a=this.newsdao.deleteByPrimaryKey(Integer.parseInt(delid));
		this.selectAll(page);
		if(a==0){
			System.out.println("删除失败");
			return 101;
		}else{
			System.out.println("删除成功");
			return 100;
		}
	}
	/**
	 * 添加图片
	 * @param path
	 * @param file
	 * @return
	 */
	public String addphoto(String path,MultipartFile file){
		Map<String,Object> map=ImageUtil.transfer(path, file);
		return (String)map.get("imgpath");
	}
	/**
	 * 分组查询
	 * @return
	 */
	public List<News> groupBy(String tid,String num){
		TypeUtil type=new TypeUtil();
		type.setNum(Integer.parseInt(num));
		type.setType(Integer.parseInt(tid));
	  List<News> list=this.newsdao.selectkey(type);
	  for (News news : list) {
		 news.setContent("");
	}
		return list;
	}
	/**
	 * 查询单个新闻详情
	 * @param id
	 * @return
	 */
	public News single(Integer id){
		return this.newsdao.selectByPrimaryKey(id);
	}
}
