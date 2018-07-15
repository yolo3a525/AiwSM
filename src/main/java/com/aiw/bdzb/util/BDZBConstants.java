package com.aiw.bdzb.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.aiw.base.util.DDData;

public class BDZBConstants {
	
	
	public static Integer getAppPageSize() {
		return Integer.parseInt(DDData.dd.get("bdzb").get("apppagesize"));
	}
	
	
	public static String getAdminPhone() {
		return (String)DDData.dd.get("bdzb").get("adminphone");
	}
	
	//1表示可以  0 表示不发
	public static String getSendAdminFlag() {
		return (String)DDData.dd.get("bdzb").get("sendadminflag");
	}
	
	//免费佩戴时间
    public static Integer getFreeday() {
        return Integer.parseInt((String)DDData.dd.get("bdzb").get("freedays"));
    }
	
	
	public static final String UPLOAD_PROFILE = 	"upload/profile/";	//个人图片位置
	public static final String UPLOAD_IMG = 		"upload/image/";	//珠宝图片位置
	public static final String UPLOAD_SYS =        "upload/sys/";    //系统相关图片位置
	public static final String UPLOAD_SCENE_IMG = 		"upload/sence/";	//场景图片位置
	public static final String UPLOAD_SUBJECT_IMG = 		"upload/subject/";	//场景图片位置
	public static final String UPLOAD_SUBJECT_CONTENT_IMG =        "upload/subject/content/";  //场景图片位置
	public static final String UPLOAD_MOIVE = 		"upload/movie/";	//珠宝视频
	
	public static final String UPLOAD_TEMP = 		"upload/temp/";	//临时图片是视频的位置
	
	
	
	public static final String IMAGE_EXT = ".jpg";
	public static final String MOVIE_EXT = ".mp4";
	
	public static final Integer JEWELRY_STATUS_WAITING = -1;
	
	
	public static Map<Integer, String> orderStatus = new LinkedHashMap<Integer, String>();
	
	public static Map<Integer, String> tenantStatus = new LinkedHashMap<Integer, String>();
	
	public static Map<Integer, String> jewelryStatus = new LinkedHashMap<Integer, String>();
	
	
	public static Map<Integer, String> tenantDeadLine = new LinkedHashMap<Integer, String>();
	
	public static Map<Integer, String> commonStatus = new LinkedHashMap<Integer, String>();
	public static Map<Integer, String> commonStatus2 = new LinkedHashMap<Integer, String>();
	
	public static Map<Integer, String> onlineTime = new LinkedHashMap<Integer, String>();
	
	public static Map<Integer, String> sexStatus = new LinkedHashMap<Integer, String>();
	
	
	static  {
		
		orderStatus.put(0, "预约");
		orderStatus.put(1, "借戴中");
		orderStatus.put(2, "完成");
		orderStatus.put(3, "取消");
		orderStatus.put(4, "删除");
		orderStatus.put(100, "非预约");
		
		
		tenantStatus.put(0, "非会员");
		tenantStatus.put(1, "会员");
		
		jewelryStatus.put(-1,"待租中");
		jewelryStatus.put(0, "预约中");
		jewelryStatus.put(1, "借戴中");
		
		
		tenantDeadLine.put(0, "今天");
		tenantDeadLine.put(3, "3天");
		tenantDeadLine.put(7, "7天");
		tenantDeadLine.put(30, "一个月");
		
		onlineTime.put(0, "今天");
		onlineTime.put(7, "7天内");
		onlineTime.put(30, "一个月内");
		
		
		commonStatus.put(1, "有");
		commonStatus.put(0, "没有");
		
		commonStatus2.put(1, "是");
        commonStatus2.put(0, "否");
        
        sexStatus.put(1, "男");
        sexStatus.put(2, "女");
        sexStatus.put(3, "未知");
		
	}
	
}
