<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("orderStatus", BDZBConstants.orderStatus);
	pageContext.setAttribute("orderStatus", BDZBConstants.orderStatus);
	
	
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

<style type="text/css">

.layui-form-item .layui-input-inline {
    width: 150px;
}
.layui-form-label {
    width: 60px;
}

</style>

</head>
<body>

<div class="layui-main">
	<table class="layui-table" lay-even="" style="width:20%">
		<colgroup>
			<col width="200">
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>今日注册</th>
				<td>${todayRegister }</td>
			</tr>
			<tr>
				<th>总注册</th>
				<td>${totalRegister }</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<br>
	<br>
	<table class="layui-table" lay-even="" style="width:20%">
		<colgroup>
			<col width="200">
			<col width="200">
		</colgroup>
		<thead>
			
			<tr>
				<th>今日预约</th>
				<td>${todayyuyueOrder }</td>
			</tr>
			<tr>
				<th>总预约</th>
				<td>${totalyuyueOrder }</td>
			</tr>
			<tr>
				<th>今日借戴</th>
				<td>${todayjiedaiOrder }</td>
			</tr>
			<tr>
				<th>总借戴</th>
				<td>${totaljiedaiOrder }</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<br>
	<br>
	<table class="layui-table" lay-even="" style="width:80%">
		<thead>
			<tr>
				<th>商品总数</th>
				<th>供应商商品总数</th>
				<th>下架中</th>
				<th>可借戴</th>
				<th>预约中</th>
				<th>借戴中</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${jewelryselectCount }</<td>
				<td>${jewelryselectCountGys }</<td>
				<td>${jewelryselectCountShowStatus0 }</<td>
				<td>${jewelryselectCountStatusF1 }</<td>
				<td>${jewelryselectCountStatus20 }</<td>
				<td>${jewelryselectCountStatus21 }</<td>
			</tr>
		</tbody>
	</table>
	
</div>
<pagejs>



</pagejs>

</body>
</html>