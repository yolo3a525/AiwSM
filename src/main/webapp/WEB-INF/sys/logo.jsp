<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="el-common" prefix="el"%>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ page import="com.aiw.util.DDData" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("commonStatus", BDZBConstants.commonStatus);
    request.setAttribute("commonStatus2", BDZBConstants.commonStatus2);
	String r = new Date().getTime()+ "";
	
%>
<!DOCTYPE html>
<html>
<head>
	<title>Logo管理</title>
<style type="text/css">

.layui-upload-icon {
    height: 30px;
    line-height: 30px;
    padding: 0 0px;
    margin: 0 5px;
    font-size: 12px;
}


.layui-upload-icon i {
    margin-right: 5px;
    vertical-align: top;
    font-size: 20px;
    color: #5FB878;
}



.layui-upload-button {
    position: relative;
    display: inline-block;
    vertical-align: middle;
    min-width: 30px;
    height: 30px;
    line-height: 30px;
    border: 1px solid #DFDFDF;
    border-radius: 2px;
    overflow: hidden;
    background-color: #fff;
    color: #666;
}
    
</style>
    
</head>
<body>
<div class="layui-main">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>logo管理</legend>
    </fieldset>
    
    
<form id="item" class="layui-form" action="">
     
    <div class="layui-form-item">
 		<label class="layui-form-label">上传Logo</label>
 		<div class="layui-input-inline">   	 	
   	 	<button type="button" class="layui-btn" id="image"><i class="layui-icon"></i></button>
   	 	
   	 	<br><br>
		<img id="jewelryImage" alt=""  src="<%=basePath%>upload/sys/logo.png?t=<%=r%>" width="200">
        </div>
    </div>

</form>
</div>


</body>

<pagejs>
<script>


var upload = "image";

layui.upload.render({
    url: '<%=basePath%>sys/logoupload' //上传接口
   ,exts: 'png' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,title: '上传'
   ,field:'image'
   ,elem:'#image'
   ,done: function(res){ //上传成功后的回调
   		layer.msg("上传完毕......")
   		if(res.msg == "OK"){
				$('#jewelryImage').attr("src","<%=basePath%>/upload/sys/logo.png?t" + Math.random());
				console.log($('#jewelryImage').attr("src"))
		}
   }
   ,before: function(res){ //上传前
   		layer.msg("正在上传......");
   }
 });


</script>
</pagejs>
</html>