package com.aiw.bdzb.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aiw.base.util.ObjectExcelRead;

public class BatchUpload {

	
	public static void main(String[] args) throws SQLException {
		
		
		ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:batch_import.xml"); 
		
		DataSource data = (DataSource) factory.getBean("dataSource");
		
		Connection connection = data.getConnection();
		
		
		java.sql.Statement s = connection.createStatement();
		
		//1.读取文件
		String path  = "E:\\合作\\春晓\\数据\\6月份";
		String filename = "prd.xlsx";
		
		//2.插入数据库
		
		List<Map> listPd = (List)ObjectExcelRead.readExcel(path, filename, 1, 0, 0);
		
		String sql = null;
		Integer id = null;
		String sid = null;
		String name = null;
		String price = null;
		String type = null;
		Integer typeid = null;
		for (Map map : listPd) {
			System.out.println(map.get("var0"));
			sid = (String) map.get("var0");
			name = (String)  map.get("var1");
			type = (String)  map.get("var2");
			
			if(type != null) {
				
				if(type.contains("耳") ) {
					typeid = 1;
				}else if(type.contains("手链") ) {
					typeid = 2;
				}else if(type.contains("项链") ) {
					typeid = 3;
				}else if(type.contains("手镯") ) {
					typeid = 4;
				}else if(type.contains("戒指") ) {
					typeid = 5;
				}
				
			}
			
			price = (String) map.get("var3");
			id = Integer.parseInt(new BigDecimal((String) map.get("var4")).toPlainString());
			
			
			sql = "insert into bdzb_jewelry (id,sid,price,name,status,imageflag,movieflag,typeid,createtime,"
					+ "lastupdatetime,createuser,lastupdateuser)"
					+ " values ("+id+",'"+sid+"',"+price+",'"+name+"',1,1,1,"+typeid+",now(),now(),'admin','admin')";
			
			//s.execute(sql);
			
			try {
				FileUtils.copyFile(new File(path + "\\视频\\" + sid + ".mp4"), new File(path + "\\movie\\" + id + ".mp4"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				FileUtils.copyFile(new File(path + "\\图片\\" + sid + ".jpg"), new File(path + "\\image\\" + id + ".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		//3.生成图片
		
		
		
	}
	
}
