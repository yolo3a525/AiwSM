<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>bdzb</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
<link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
</head>
<body>

	<table class="layui-table" lay-even="" style="">
		<colgroup>
			<col width="50">
				<col>
				<col>
				<col>
			<col width="200">
			<col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>方式</th>
				<th>金额</th>
				<th>余额</th>
				<th>创建时间</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td>${a.id }</td>
						<td>${a.method}</td>
						<td>${a.amount}</td>
						<td>${a.balance}</td>
					
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.createUser }</td> 
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
	
<pagejs>

<script>

 
</script>
</pagejs>
</body>
</html>