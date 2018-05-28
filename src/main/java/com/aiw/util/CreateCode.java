package com.aiw.util;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.ScriptRunner;

public class CreateCode {
	
	
	
	public static void create(Map<String,Object> map,HttpServletResponse response, Connection connection) throws Exception {
		
			
			//map.put("entity",entity);
			//map.put("module","aa");
			//map.put("component","Abc");
			map.put("nowDate", new Date());
			String module = (String) map.get("module");  				//主表名				========参数0-1 主附结构用
			String  component = (String) map.get("component");
			map.put("componentLower", component.toLowerCase());
			
//			
//			//模块类型			========参数0-2 类型，单表、树形结构、主表明细表
//			String TITLE = pd.getString("TITLE");  						//说明				========参数0
//			String packageName = pd.getString("packageName");  			//包名				========参数1
//			String objectName = pd.getString("objectName");	   			//类名				========参数2
//			String tabletop = pd.getString("tabletop");	   				//表前缀				========参数3
//			tabletop = null == tabletop?"":tabletop.toUpperCase();		//表前缀转大写
//			String zindext = pd.getString("zindex");	   	   			//属性总数
//			int zindex = 0;
//			if(null != zindext && !"".equals(zindext)){
//				zindex = Integer.parseInt(zindext);
//			}
//			List<String[]> fieldList = new ArrayList<String[]>();   	//属性集合			========参数4
//			for(int i=0; i< zindex; i++){
//				fieldList.add(pd.getString("field"+i).split(",fh,"));	//属性放到集合里面
//			}
//			Map<String,Object> root = new HashMap<String,Object>();		//创建数据模型
//			root.put("fieldList", fieldList);
//			root.put("faobject", faobject.toUpperCase());				//主附结构用，主表名
//			root.put("TITLE", TITLE);									//说明
//			root.put("packageName", packageName);						//包名
//			root.put("objectName", objectName);							//类名
//			root.put("objectNameLower", objectName.toLowerCase());		//类名(全小写)
//			root.put("objectNameUpper", objectName.toUpperCase());		//类名(全大写)
//			root.put("tabletop", tabletop);								//表前缀	
//			root.put("nowDate", new Date());							//当前日期
//			
//			DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl"); //生成代码前,先清空之前生成的代码
//			/* ============================================================================================= */
//			String filePath = "admin/ftl/code/";						//存放路径
//			String ftlPath = "createCode";								//ftl路径
//			if("tree".equals(FHTYPE)){
//				ftlPath = "createTreeCode";
//				/*生成实体类*/
//				Freemarker.printFile("entityTemplate.ftl", root, "entity/"+packageName+"/"+objectName+".java", filePath, ftlPath);
//				/*生成jsp_tree页面*/
//				Freemarker.printFile("jsp_tree_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_tree.jsp", filePath, ftlPath);
//			}else if("fathertable".equals(FHTYPE)){
//				ftlPath = "createFaCode";	//主表
//			}else if("sontable".equals(FHTYPE)){
//				ftlPath = "createSoCode";	//明细表
//			}
			/*生成controller*/
			
			String src = "code/src/main/java/";
			String jsp = "code/src/main/webapp/WEB-INF/"+module+"/";
			String db  = "code/db/";
			
			//先删除之前的代码
			DelAllFile.delFolder(PathUtil.getClasspath()+"code"); //生成代码前,先清空之前生成的代码
			
			
			Freemarker.printFile("controllerTemplate.ftl", map, 
					"com/aiw/"+module+"/controller/"+component+"Controller.java",
					src,
					"");
			Freemarker.printFile("entityTemplate.ftl", map, 
					"com/aiw/"+module+"/entity/"+component+".java",
					src,
					"");
			Freemarker.printFile("mapperTemplate.ftl", map, 
					"com/aiw/"+module+"/mapper/"+component+"Mapper.java",
					src,
					"");
			Freemarker.printFile("xmlTemplate.ftl", map, 
					"com/aiw/"+module+"/mapper/"+component+"Mapper.xml",
					src,
					"");
			
			
			Freemarker.printFile("jsp_listTemplate.ftl", map, 
					component.toLowerCase()+"/list.jsp",
					jsp,
					"");
			
			Freemarker.printFile("jsp_itemTemplate.ftl", map, 
					component.toLowerCase()+"/item.jsp",
					jsp,
					"");
			Freemarker.printFile("mysql_Template.ftl", map, 
					component.toLowerCase()+".sql",
					db,
					"");
			
//			/*生成service*/
//			Freemarker.printFile("serviceTemplate.ftl", root, "service/"+packageName+"/"+objectName.toLowerCase()+"/impl/"+objectName+"Service.java", filePath, ftlPath);
//			/*生成manager*/
//			Freemarker.printFile("managerTemplate.ftl", root, "service/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Manager.java", filePath, ftlPath);
//			/*生成mybatis xml*/
//			Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
//			Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
//			Freemarker.printFile("mapperSqlserverTemplate.ftl", root, "mybatis_sqlserver/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
//			/*生成SQL脚本*/
//			Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
//			Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
//			Freemarker.printFile("sqlserver_SQL_Template.ftl", root, "sqlserver数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
//			/*生成jsp页面*/
//			Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", filePath, ftlPath);
//			Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_edit.jsp", filePath, ftlPath);
//			/*生成说明文档*/
//			Freemarker.printFile("docTemplate.ftl", root, "部署说明.doc", filePath, ftlPath);
			//this.print("oracle_SQL_Template.ftl", root);  控制台打印
			
			//执行sql
			File file = new File(PathUtil.getClasspath() + db + component.toLowerCase()+".sql");
		    ScriptRunner runner = new ScriptRunner(connection);  
		    runner.setErrorLogWriter(null);  
		    runner.setLogWriter(null);  
		    runner.runScript(new FileReader(file));  
			
			/*生成的全部代码压缩成zip文件*/
			if(FileZip.zip(PathUtil.getClasspath()+"code", PathUtil.getClasspath()+"code.zip")){
				/*下载代码*/
				FileDownload.fileDownload(response, PathUtil.getClasspath()+"code.zip", "code.zip");
			}
			
			
			
		
	}
		
}
