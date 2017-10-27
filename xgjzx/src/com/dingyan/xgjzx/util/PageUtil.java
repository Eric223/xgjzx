package com.dingyan.xgjzx.util;

/*
 * 分页工具类
 */
public class PageUtil {
	private Integer counts;//总的信息条数
	private Integer nownum;//当前页码
	private Integer start;//开始位置
	private Integer allpage;//总页数
	private Integer size;//每页显示数目
	
	public PageUtil setall(Integer count,Integer page,Integer size){
		PageUtil pp=new PageUtil();
		pp.setSize(size);
		pp.setCounts(count);
		if(page==1){
			pp.setStart(0);
		}else{
			pp.setStart((page-1)*pp.getSize());
		}
		if(count%size==0){
			pp.setAllpage((pp.getCounts()/pp.getSize()));
		}else{
			pp.setAllpage(((pp.getCounts()/pp.getSize())+1));
		}
		return pp;
		
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start){
		this.start=start;
	}
	public Integer getAllpage(){
		return allpage;
	}
	public void setAllpage(Integer allpage){
		this.allpage=allpage;
	}
	public Integer getCounts() {
		
		return counts;
	}
	public void setCounts(Integer counts) {
		
		this.counts = counts;
	}
	public Integer getNownum() {
		return nownum;
	}
	public void setNownum(Integer nownum) {
		this.nownum=nownum;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
}
