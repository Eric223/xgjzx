package com.dingyan.xgjzx.service;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.dingyan.xgjzx.dao.BusDao;

import com.dingyan.xgjzx.pojo.Bus;

import com.dingyan.xgjzx.util.PageUtil;

@Service
public class BusService{
	
	@Resource BusDao busdao;
	/**
	 * 查询所有的公交车
	 * @param page当前页
	 * @return
	 */
	public Map<String,Object> findAllBus(String page){
		System.out.println("findALL");
		PageUtil pp=new PageUtil();
		int count=this.busdao.countnum();
		PageUtil pageutil= pp.setall(count, Integer.parseInt(page), 10);
		//System.out.println("一共有"+count+"条公交车信息"+"一共有"+pageutil.getAllpage()+"页");
		List<Bus> listBus=this.busdao.findAllBus(pageutil);//按条件查询出所有的公交车信息
		Map<String,Object> all=new HashMap<String,Object>();
				all.put("maxPage",pageutil.getAllpage());
				all.put("data",listBus);
		return all;
	}
	/**
	 * 修改或添加 
	 * 判断获取的id 如果为id 则执行添加 否则 执行修改
	 * @param params 包含当前页page 与要操作的公交车data
	 * @return
	 */
	public int updateBus(String params){
		JSONObject jsb=	JSONObject.fromObject(params);
		String page=jsb.getString("page");
		String data= jsb.getString("data");
		JSONObject object=JSONObject.fromObject(data);
		Bus bus=new Bus();
		bus.setProvince(object.getString("province"));
		bus.setCity(object.getString("city"));
		bus.setLevel(Integer.parseInt(object.getString("level")));
		bus.setType(Integer.parseInt(object.getString("type")));
		bus.setPrice(Integer.parseInt(object.getString("price")));
		bus.setTime(Integer.parseInt(object.getString("time")));
		bus.setRoute(object.getString("route"));
		int c=this.checkbus(data);
		if(c!=100){
			return 102;
		}
		if(object.getString("id").equals("id")){
		int a=this.busdao.insert(bus);
		System.out.println("添加返回的值"+a);
		this.findAllBus(page);
		if(a==0){//添加失败
			return 101;
		}else if(a==1){//添加成功
			return 100;
		}
		}else{
		bus.setId(Integer.parseInt(object.getString("id")));
		int a=this.busdao.updateByPrimaryKey(bus);
		this.findAllBus(page);
		if(a==0){//修改失败
			return 101;
		}else if(a==1){//修改成功
			return 100;
		}
	}
		return 102;
	}
	/**
	 * 检查重复
	 * 避免添加重复的元素 导致搜索单个公交车信息时无法显示
	 */
	public int  checkbus(String Bus){
		JSONObject object=	JSONObject.fromObject(Bus);
		Bus bus=new Bus();
		bus.setProvince(object.getString("province"));
		bus.setCity(object.getString("city"));
		bus.setLevel(Integer.parseInt(object.getString("level")));
		bus.setType(Integer.parseInt(object.getString("type")));
		bus.setPrice(Integer.parseInt(object.getString("price")));
		bus.setTime(Integer.parseInt(object.getString("time")));
		bus.setRoute(object.getString("route"));
		List b=this.busdao.check(bus);
		if(b.size()>0){
			System.out.println("已经存在了");
			return 101;
		}
		return 100;
	}
	/**
	 * 删除
	 * @param delId 公交车id
	 * @param page	当前页
	 * @return
	 */
	public int deleteById(String delId,String page) {
		System.out.println("删除的Id————>"+delId);
		int a=this.busdao.deleteByPrimaryKey(Integer.parseInt(delId));
				this.findAllBus(page);
		if(a==0){//删除失败
			return 101;
		}else if(a==1){//删除成功
			return 100;
		}
		return a;
	}
	public int countBus() {
		// TODO Auto-generated method stub
		return this.busdao.countnum();
	}
	/**
	 * 按条件搜索
	 * @param keyBus 条件
	 * @return
	 */
	public Map<String,Object> search(String keyBus) {
			JSONObject js=JSONObject.fromObject(keyBus);
		Map<String,Object> mm=new HashMap<String ,Object>();
					String page=js.getString("page");
		JSONObject obj=JSONObject.fromObject(js.getString("data"));
						Bus bus=new Bus();
		if(obj.getString("level")!=null&&!obj.getString("level").equals("")){
			bus.setLevel(Integer.parseInt(obj.getString("level")));
		}			
		if(obj.getString("type")!=null&&!obj.getString("type").equals("")){
			bus.setType(Integer.parseInt(obj.getString("type")));
		}
		if(obj.getString("time")!=null&&!obj.getString("time").equals("")){
			bus.setTime(Integer.parseInt(obj.getString("time")));
		}	
						bus.setCity(obj.getString("city"));
						bus.setProvince(obj.getString("province"));
						   PageUtil pa=new PageUtil();
						int count=this.busdao.countSearch(bus);
						PageUtil pageutil=pa.setall(count, Integer.parseInt(page),10);
					Map<String,Object> map=new HashMap<String,Object>();
							map.put("page",pageutil);
							map.put("bus",bus);
					List<Bus> listbus=this.busdao.search(map);
					mm.put("results",listbus);
					mm.put("maxPage", pageutil.getAllpage());
					return mm;
	}
	/**
	 * 查询单个公交车价格表
	 * @param city
	 * @param route
	 * @param level
	 * @return
	 */
	public Map<String,Object> findSingle(String city, String route, String level){
		try {
			city=new String(city.getBytes("iso8859-1"),"UTF-8");
			route=new String(route.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("城市为:"+city);
		Map<String,Object> map=new HashMap<String, Object>();
				if(city!=null&&!city.equals("")){
							if(route!=null&&!route.equals("")){
										if(level!=null&&!level.equals("")){
												Bus bus=new Bus();
												bus.setCity(city);
												bus.setRoute(route);
												bus.setLevel(Integer.parseInt(level));
												 int TimeArray[]={12,6,3};//周期
												 int TypeArray[]={1,2,3};//类型
												for (int i=0; i <TimeArray.length; i++){
														bus.setTime(TimeArray[i]);
														List list=new ArrayList();
													for (int j=0; j<TypeArray.length;j++){
														bus.setType(TypeArray[j]);
														String busprice=this.busdao.findSingle(bus);
														if(busprice!=null&&!busprice.equals("")){
															list.add(busprice);
														}else{
															list.add("暂无");
														}
													}
													map.put("time"+(3-i),list);
												}
												JSONObject json=JSONObject.fromObject(map);
												//System.out.println("---->"+json);
											return map;
										}else{
											System.out.println("级别不能为空");
											map.put("result",101);
											return map;
										}
							}else{
								System.out.println("路线不能为空");
								map.put("result",101);
								return map;
							}
				}else{
					System.out.println("城市不能为空");
					map.put("result",101);
					return map;
				}	
	}
}
