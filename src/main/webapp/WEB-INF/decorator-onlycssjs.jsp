<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><sitemesh:write property='title' /></title>
    
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=basePath%>layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>layui/css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
        
    <sitemesh:write property='head' />
    
</head>
<body>

<sitemesh:write property='body' />

<script src="<%=basePath%>layui/layui.all.js"></script>
<script src="<%=basePath%>my/form.js"></script>
<script src="<%=basePath%>my/common.js"></script>
		
<script type="text/javascript">
  var $ = layui.jquery, 
  form = layui.form,
  laypage = layui.laypage,
  layer = layui.layer,
  element = layui.element;
</script>
<sitemesh:write property='pagejs' />
</body>
</html>