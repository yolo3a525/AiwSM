<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<input type="hidden" name="id">
		 <div class="layui-form-item">
    		<label class="layui-form-label">regulation</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="regulation"  lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">openid</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="openid"  lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">title</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="title"  lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
    <div class="layui-form-item">
 	   <div class="layui-input-block">
    	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
    	</div>
    </div>
</form>

<script src="<%=basePath%>lay/dest/layui.all.js"></script>
<script src="<%=basePath%>my/form.js"></script>
<script>
var $ = layui.jquery, form = layui.form;
var addUrl = "<%=basePath%>dominos/info/save";
var updateUrl ="<%=basePath%>dominos/info/update";

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
</body>
</html>