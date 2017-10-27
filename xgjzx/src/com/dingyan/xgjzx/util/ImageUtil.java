package com.dingyan.xgjzx.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 上传图片工具类
 * @author Administrator
 *
 */
public class ImageUtil {
	 //文件大小
	 private final static Long IMAGE_SIZE=1024*1024*2L;
	 //文件格式
	 private final static String [] FILETYPES={"jpg","gif","png","bmp","jpeg"};
	 List typelist=new ArrayList();
	
	//上传图片
	public static Map<String,Object> transfer(String path,MultipartFile file){
		Map<String, Object> map=new HashMap<String,Object>();
		if(file.isEmpty()){
			//System.err.println("文件不能为空");
				map.put("result","101");
				return map;
		}
		//判断文件类型
		String contype=file.getContentType();
		if(!contype.contains("image")){
			//System.out.println("文件格式不正确");
			map.put("result","101");
			return map;
		}
		//判断文件格式
		String filename=file.getOriginalFilename();
		String filetype=filename.substring(filename.lastIndexOf(".")+1,filename.length());
			//System.out.println("image格式:"+filetype);
			Boolean st=false;
			for (int i = 0; i < FILETYPES.length; i++) {
				if(FILETYPES[i].equals(filetype)){
					 st=true;
					break;
				}
			}
			if(st=false){
					//System.out.println("文件格式不正确");
					map.put("result","101");
					return map;
			}
		//判断文件大小
		Long filesize=file.getSize();
		if(filesize>IMAGE_SIZE){
			System.out.println("文件超出限定大小");
			map.put("result","101");
			return map;
		}
		String imgpath="/upload/image/"+UUID.randomUUID().toString().replaceAll("-","")+"."+filetype;
		File ff=new File(path+imgpath);
		try{
			file.transferTo(ff);
				createSmallPhoto(path,imgpath);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//String xxx=imgpath;//线下测试用的
		String xxx="/xgjzx"+imgpath;
			   try {
				URLEncoder.encode(xxx, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		map.put("imgpath",xxx);
		return map;
	}	
	/**
	 * 删除图片
	 */
	public static void removefile(String filepath){
		File ff=new File(filepath);
		ff.delete();
	}
	/**
	 * 创建图片并生成缩略图
	 * @param path  
	 * @param imgpath  
	 * @return
	 * @throws IOException
	 */
	public static String createSmallPhoto(String path,String imgpath)throws IOException{
        File _file=new File(path+imgpath);
        Image src;
        src=javax.imageio.ImageIO.read(_file);
        int wideth=300;
        int height=180;
        BufferedImage tag=new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src, 0, 0, wideth, height,null);
        //修改原图片名称生成缩略图
        String xtpath=imgpath.substring(0,imgpath.lastIndexOf("."))+"xt"+imgpath.substring(imgpath.lastIndexOf("."),imgpath.length());
        FileOutputStream out=new FileOutputStream(path+xtpath);
        JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        out.close();
        return xtpath;
    }
	/**
	 * 获取文件夹下所有图片名称
	 * @param path
	 * @return
	 */
	public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }
	/**
	 * 获取文件夹及子文件夹下所有图片名称
	 * @param path
	 * @param fileName
	 */
    public static void getAllFileName(String path,ArrayList<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
        fileName.addAll(Arrays.asList(names));
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }
    /**
     * 删除多余的图片
     * @param imgs  原图片地址 
     * @param listyt 新图片地址
     * @category 将两个图片地址做比较  如果有相同的就保留  没有就删除
     */
	public static int deletephoto(String imgs, String listyt){
		String path="C:\\Program Files\\apache-tomcat-7.0.53\\webapps\\";
		System.out.println("imageutil");
		System.out.println(path+imgs);
	String img2=imgs.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\''", "");//旧的图片地址
	String yt=listyt.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\''", "");//新的图片地址
	if(img2==null||img2.equals("")){
		 return 101;
	}
	if(img2.contains(",")){//如果旧的图片不止一张
	 String  st1[]=img2.split("\\,");
						if(yt.contains(",")){//而且新的图片不止一张
								String 	yts[]=yt.split("\\,");
										List<String> list=new ArrayList<String>();
										for (int i = 0; i < st1.length; i++) {
											list.add(st1[i]);
										}
										List<String> list2=new ArrayList<String>();
										for (int i = 0; i < yts.length; i++) {
											list2.add(yts[i]);
										}
										list2.retainAll(list);
										list.removeAll(list2);
										for (String o : list) {
										File ff=new File(path+o.substring(2,o.length()-1));
										if(ff.exists()){
											ff.delete();
										}
										}
							  }else{//新的图片只有一张
										for (int i = 0; i< st1.length; i++) {
													if(!st1[i].equals(yt)){//如果原图片地址不存在新图片地址中则删除
														File ff=new File(path+st1[i].substring(2, st1[i].length()-1));
														if(ff.exists()){
															ff.delete();
												}
											}
										}
						}
	}
	else{//如果旧的图片只有一张
			if(yt.contains(",")){//新的图片不止一张
				String 	yts[]=yt.split(",");
						for (int i = 0; i < yts.length; i++) {
								String a=yts[i];
									Boolean bo=false;
									if(img2.equals(a)){//如果原图片地址不存在新图片地址中则删除
										 bo=true;
									}
									if(bo=true){
									}else{
										File ff=new File(path+img2.substring(2,img2.length()-1));
										if(ff.exists()){
											ff.delete();
										}
							}
				    }
			}
		else{//新的图片只有一张
				if(!img2.equals(yt)){//如果原图片地址不存在新图片地址中则删除
						File ff=new File(path+img2.substring(2,img2.length()-1));
						System.out.println("-------"+ff.getAbsolutePath());
						if(ff.exists()){
							ff.delete();
						}
				}
		}
	}
	return 100;
	}
}
