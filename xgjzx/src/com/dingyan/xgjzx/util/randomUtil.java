package com.dingyan.xgjzx.util;

import java.util.Random;

/**
 * 生产随机验证码
 * @author Administrator
 *
 */
public class randomUtil{
			
	
	public String getrandom(){
		StringBuilder sb=new StringBuilder();
		Random random=new Random();
			for (int i = 0; i <4; i++) {
			Integer d=random.nextInt(10);
			sb.append(d);
			}
		String math=sb.toString();
		return math;
	}
		
}
