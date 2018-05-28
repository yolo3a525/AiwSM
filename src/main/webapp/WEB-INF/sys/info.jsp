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
    <link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
    <style type="text/css">
    </style>
</head>
<body>

<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="F_sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this" data-url="/a/sys/role/">java环境</li>
			<li >系统环境</li>
			<li >开发团队</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">



		<blockquote class="layui-elem-quote">
			<ul class="list-info">
				<c:if test="${not empty sys}">
					<c:forEach items="${sys}" var="a">
				 	 	<li><span>${a.key}:</span>${a.value}</li>
				 	</c:forEach>
				</c:if>	                    
		    </ul>
		</blockquote>
		</div>
		
		<div class="layui-tab-item">
			<blockquote class="layui-elem-quote">
				<ul class="list-info">
					<c:if test="${not empty env}">
						<c:forEach items="${env}" var="a">
					 	 	<li><span>${a.key}:</span>${a.value}</li>
					 	</c:forEach>
					</c:if>	                    
			    </ul>
			</blockquote>
		</div>
		<div class="layui-tab-item">
			<fieldset class="layui-elem-field">
			  <legend>开发团队</legend>
			  <div class="layui-field-box">
			     	王海申 aiw QQ:3684170 
			  </div>
			</fieldset>
		
		</div>
		
		
	</div>
</div>
		
	
	
<script src="<%=basePath%>lay/dest/layui.all.js"></script>
<script>
var $ = layui.jquery, form = layui.form;


</script>
</body>
</html>