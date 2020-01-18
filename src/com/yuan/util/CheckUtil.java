package com.yuan.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CheckUtil {
	
	private static final String token = "Yuan680923"; //改成唯一的字符串
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(arr);                  
		//..生成字符串
		StringBuffer content = new StringBuffer();   
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
			
		}
		
		String temp = getSha1(content.toString());
		
		return temp.equals(signature);
	}
	public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
	
	

	/*
	 *在微信中的Createtime，表示为1970年1月1日0时0分0秒至消息创建时所间隔的秒数，获取Createtime方式有如下两种：
	 long createtime = System.currentTimeMillis();
	 long createtime = new java.util.Date().getTime();
	 createtime转换成现在的时间的方式为：
	 long msgCreatetime  = Long.pareseLong(createtime)*1000L;
	 DateFormate format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 String nowTime = format.format(new Date(msgCreatetime));
	 * 
	 * @param createTime 消息创建时间
	 * 
	 * @return
	 */
	public static String formatTime(String createTime) {
		// 将微信传入的CreateTime转换成long类型,再乘以1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}
	
	/**
	 * 获取当前时间
	 */
	public static String getThisTime(){
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date)+"";
	}

	
}