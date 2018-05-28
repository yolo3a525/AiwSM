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
	<#list entity?keys as key>  
	 <div class="layui-form-item">
    		<label class="layui-form-label">${key}</label>
    		<div class="layui-input-inline">
    	
    		<#if entity[key] == "java.sql.Date">
		    	<input type="text" name="${key}" lay-verify="date" placeholder="" 
                	autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		    <#elseif entity[key] == "Integer"> 
		    	<input type="text" name="${key}"  lay-verify="required|number" autocomplete="on"  class="layui-input">
		    <#else>
		    	<input type="text" name="${key}"  lay-verify="required" autocomplete="on"  class="layui-input">
			</#if>
                	
            </div>
     </div>
	</#list> 
    <div class="layui-form-item">
 	   <div class="layui-input-block">
    	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
    	</div>
    </div>
</form>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form();
var addUrl = "<%=basePath%>${module}/${componentLower}/save";
var updateUrl ="<%=basePath%>${module}/${componentLower}/update";

<c:if test="${r"${not empty edit}"}">
loadJsonDataToForm('${r"${el:toJsonString(edit)}"}','item')
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
</pagejs>
</body>
</html>