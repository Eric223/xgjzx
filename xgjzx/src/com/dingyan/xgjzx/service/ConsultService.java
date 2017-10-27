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

import com.dingyan.xgjzx.dao.ConsultDao;

import com.dingyan.xgjzx.pojo.Consult;

import com.dingyan.xgjzx.util.ImageUtil;

import com.dingyan.xgjzx.util.PageUtil;

@Service
public class ConsultService {
		
	@Resource ConsultDao consultdao;
	
	/**
	 * 分页查询所有的咨询信息
	 * @param page 当前页
	 * @return
	 */
	public Map<String,Object> selectAll(String page) {
		PageUtil pag=new PageUtil();
		int num=this.consultdao.count();
		PageUtil pp=pag.setall(num, Integer.parseInt(page), 10);//num总条数,page当前页,10每页数目
		List<Consult> list=this.consultdao.selectAll(pp);
		JSONArray json=JSONArray.fromObject(list);
		Map<String,Object> map=new HashMap<String,Object>();
		System.out.println("。。。。。>"+json);
		map.put("consults",list);
		map.put("maxPage",pp.getAllpage());
		return map;
	}
	/**
	 * 查询所有案例
	 */
	public List<Consult> findAll(){
		return this.consultdao.findAll();
	}
	/**
	 * 添加修改
	 * @param path 
	 * @param consult 案例展示
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public int insertupdate(String consult) throws ParseException{
		JSONObject alljson=JSONObject.fromObject(consult);
		String  page=alljson.getString("page");//页码
		String  sjson=alljson.getString("data");//所有数据
		JSONObject json=JSONObject.fromObject(sjson);
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				Consult con=new Consult();
			String  listyt=json.getString("img");
			String  slts="";
			//判断如果图片不为空 则生成缩略图
			if(listyt!=null&&!listyt.equals("")){
				//将多张图片地址转为list集合
				List<String> lsyt=JSONArray.fromObject(listyt);
				List<String> slt=new ArrayList<String>();
				//遍历产生缩略图地址
				for (String s: lsyt){
					String img2=s.substring(0,s.lastIndexOf("."))+"xt"+s.substring(s.lastIndexOf("."),s.length());
					  slt.add(img2);
				}
				slts=JSONArray.fromObject(slt).toString();	
			}
			if(json.getString("origin")!=null||!json.getString("").equals("")){
				con.setOrigin(json.getString("origin"));
			}
			con.setImg1(listyt);
			con.setImg2(slts);
		    con.setType(json.getString("type"));
			con.setTitle(json.getString("title"));
			con.setContent(json.getString("content"));
			con.setLogo(json.getString("logo"));
			con.setOrigin(json.getString("origin"));
			if(json.getString("date")==null||json.getString("date").equals("")){
				con.setDate(new Date().toLocaleString());
			}else{
				con.setDate(sd.parse(json.getString("date")).toLocaleString());
			}
			//如果id为id执行添加否则执行修改
		if(json.getString("id").equals("id")||json.getString("id").equals("")){
			this.consultdao.insert(con);
			this.selectAll(page);
			return 100;
		}else{
			//修改之前删除原来无效的图片
			String imgs=this.consultdao.selectByPrimaryKey(json.getInt("id")).getImg1();
			ImageUtil.deletephoto(imgs,listyt);
			String imgxs=this.consultdao.selectByPrimaryKey(json.getInt("id")).getImg2();
			ImageUtil.deletephoto(imgxs, slts);
			con.setId(Integer.parseInt(json.getString("id")));
			this.consultdao.updateByPrimaryKey(con);
			this.selectAll(page);
			return 100;
		}
	}
	/**
	 * 删除
	 * @param delid
	 */
	public int deleteById(String path,String delid,String page) {
		// TODO Auto-generated method stub
		Consult con=this.consultdao.selectByPrimaryKey(Integer.parseInt(delid));
		List<String> listIMG= JSONArray.fromObject(con.getImg1());
		List<String> listIMG2= JSONArray.fromObject(con.getImg2());
		for (String s : listIMG){
			File ff=new File(path+s.substring(6,s.length()));
			System.out.println(ff.getAbsolutePath());
			ff.delete();
		}
		for (String s : listIMG2){
		File ff=new File(path+s.substring(6,s.length()));
		System.out.println(ff.getAbsolutePath());
			ff.delete();
		}
		int a=this.consultdao.deleteByPrimaryKey(Integer.parseInt(delid));
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
		Map map=ImageUtil.transfer(path, file);
		System.out.println((String)map.get("imgpath"));
		return (String)map.get("imgpath");
	}
	/**
	 * 查询单个案例
	 * @param id
	 * @return
	 */
	public Consult Single(String id) {
		// TODO Auto-generated method stub
		if(id!=null&&!id.equals("")){
		return	this.consultdao.selectByPrimaryKey(Integer.parseInt(id));
		}
		return new Consult();
	}
	/**
	 * 按类别搜索
	 * @param type
	 * @return
	 */
	public Map<String, Object> search(String data) {
		JSONObject json=JSONObject.fromObject(data);
		String page=json.getString("page");
		String type=json.getString("type");
		PageUtil pa=new PageUtil();
		int count=this.consultdao.countsearch(type);
		PageUtil pp=pa.setall(count, Integer.parseInt(page), 10);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("page", pp);
		map.put("type",type);
		List<Consult> list=this.consultdao.search(map);
		Map mm=new HashMap<String,Object>();
		mm.put("consults", list);
		mm.put("maxPage",pp.getAllpage());
		return mm;
	}
}
