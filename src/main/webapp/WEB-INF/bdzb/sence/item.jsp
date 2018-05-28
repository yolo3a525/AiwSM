<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String r = new Date().getTime()+ "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增/修改场景</title>
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

<form id="item" class="layui-form" action="">
	<input type="hidden" name="id">
	<input type="hidden" name="tempid" value="${tempid}">
	 <div class="layui-form-item">
    		<label class="layui-form-label">场景名</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="name" id="name"  lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">是否显示</label>
    		<div class="layui-input-inline">
    	
		    	<select name="isShow">
		    		<option value="0" <c:if test="${edit.isShow == 0}">selected</c:if> >不显示</option>
		    		<option value="1" <c:if test="${edit.isShow == 1}">selected</c:if> >显示</option>
		    	</select>
                	
            </div>
     </div>
     
     <div class="layui-form-item">
 		<label class="layui-form-label">上传Title</label>
 		<div class="layui-input-inline">
   	 	<button type="button" class="layui-btn" id="imageTitle"><i class="layui-icon"></i>上传Title</button>
   	 	<br><br>
		<img id="jewelryImageTitle" src="<%=basePath%>upload/sence/${edit.id}_title.jpg?t=<%=r%>"  width="200">
        </div>
    </div>
    
    
    <div class="layui-form-item">
 		<label class="layui-form-label">上传图片</label>
 		<div class="layui-input-inline">
   	 	
   	 	<button type="button" class="layui-btn" id="image"><i class="layui-icon"></i>上传图片</button>
   	 	<br><br>
		<img id="jewelryImage" src="<%=basePath%>upload/sence/${edit.id}.jpg?t=<%=r%>"  width="200">
        </div>
    </div>
    
    
     
    <div class="layui-form-item">
 	   <div class="layui-input-block">
    	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
    	</div>
    </div>
</form>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var addUrl = "<%=basePath%>bdzb/sence/save";
var updateUrl ="<%=basePath%>bdzb/sence/update";

<c:if test="${not empty edit}">
loadJsonDataToForm('${el:toJsonString(edit)}','item')
</c:if>

//自定义验证规则
form.verify({
	username: function(value){
        if(value.length < 3){
            return '至少3个字符';
        }
    }
    ,password: [/(.+){6,12}$/, '密码必须6到12位']
});

form.on('submit(item)', function(data){
	save();
    return false;
});


layui.upload.render({
    url: '<%=basePath%>bdzb/sence/upload/${tempid}' //上传接口
   ,exts: 'jpg' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,title: '上传'
   ,field:'imageTitle'
   ,elem: '#imageTitle'
   ,done: function(res){ //上传成功后的回调
   		layer.msg("上传完毕......")
   		if(res.msg == "OK"){
			
				$('#jewelryImageTitle').attr("src","<%=basePath%>/upload/temp/${tempid}_title.jpg?t" + Math.random());
				console.log($('#jewelryImageTitle').attr("src"))
			
		}
   }
   ,before: function(res){ //上传前
		
   		layer.msg("正在上传......");
   }
 });
 
 
layui.upload.render({
    url: '<%=basePath%>bdzb/sence/upload/${tempid}' //上传接口
   ,exts: 'jpg' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,title: '上传'
   ,field:'image'
   ,elem: '#image'
   ,done: function(res){ //上传成功后的回调
   		layer.msg("上传完毕......")
   		if(res.msg == "OK"){
			
				$('#jewelryImage').attr("src","<%=basePath%>/upload/temp/${tempid}.jpg?t" + Math.random());
				console.log($('#jewelryImage').attr("src"))
			
		}
   }
   ,before: function(res){ //上传前
		
   		layer.msg("正在上传......");
   }
 });
 


//保存
function save(){
	url = updateUrl;
	if($('#item input[name=id]').val() == ""){
		url = addUrl;
	}
	$.post(url,$('#item').serialize(),function(data){
		parent.document.location.reload(true);
	});
}

</script>
</pagejs>
</body>
</html>