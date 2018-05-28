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
    <link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
</head>
<body>

<form id="item" class="layui-form" action="">
	<input type="hidden" name="id" value="${edit.id}">
	<input type="hidden" name="tempid" value="${tempid}">
	 <div class="layui-form-item">
    		<label class="layui-form-label">主题名字</label>
    		<div class="layui-input-inline" style="width: 800px">
    	
		    	<input type="text" name="title" value="${edit.title}" lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
     <div class="layui-form-item">
    		<label class="layui-form-label">主题内容</label>
    		<div class="layui-input-inline" style="width: 800px">
    			<textarea class="layui-textarea" id="content" name="content"  style="display: none">
    			${edit.content}
    			</textarea>
            </div>
     </div>
	  <div class="layui-form-item">
 		<label class="layui-form-label">上传图片</label>
 		<div class="layui-input-inline">
   	 	<button type="button" class="layui-btn" id="image"><i class="layui-icon"></i>上传图片</button>
   	 	<br><br>
		<img id="jewelryImage" src=""  width="200">
		
        </div>
    </div>
	
     
     <div class="layui-form-item">
    		<label class="layui-form-label">是否发布</label>
    		<div class="layui-input-inline">
    	
		    	<select name="status">
		    		<option value="0" <c:if test="${edit.status == 0}">selected</c:if> >不发布</option>
		    		<option value="1" <c:if test="${edit.status == 1}">selected</c:if> >发布</option>
		    	</select>
                	
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
var addUrl = "<%=basePath%>bdzb/subject/save";
var updateUrl ="<%=basePath%>bdzb/subject/update";

var layedit = layui.layedit;
layedit.set({
  uploadImage: {
    url: "<%=basePath%>bdzb/subject/insertImage" //接口url
    ,type: '' //默认post
  }
});
var index = layedit.build('content');


<c:if test="${not empty edit}">
$('#jewelryImage').attr("src","<%=basePath%>upload/subject/${edit.id}.jpg?t=<%=r%>");
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
	layedit.sync(index)
	save();
    return false;
});


layui.upload.render({
    url: '<%=basePath%>bdzb/subject/upload/${tempid}' //上传接口
   ,ext: 'jpg' //那么，就只会支持这三种格式的上传。注意是用|分割。
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