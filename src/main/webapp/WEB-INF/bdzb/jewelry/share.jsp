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
<meta charset="utf-8">
<title>百德珠宝</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

	<style type="text/css">
	
	.img1 { width: 100%; height: auto;max-width: 100%;}  
	td {
	   padding:5px;
	}
	body{
	   padding:0;
	   margin:0;
	}
	</style>
	
    
</head>
<body>

<div style="position:relative">
<div 
onclick="window.open('https://itunes.apple.com/us/app/百德珠宝/id1252934482?l=zh&ls=1&mt=8')"
style="position:absolute;top:1;
flex-direction: row;width:100%;display: flex;align-items: center;
padding:5px 5px 5px 10px;
background-color:rgba(135,135,135,0.3);
">
  <img  src="<%=basePath%>images/80_80.png" width="60px"/>
  <div style="flex:1;padding-left: 10px">
    <div style="color:rgb(172,146,111)">百德珠宝APP</div>
    <div style="color:rgb(172,146,111)">包年无限次借戴百万珠宝</div>
  </div>
  <div>
    <div style="padding-right: 20px">
    <span style="padding:5px 10px;background-color:rgb(172,146,111);color:white;
    border-radius: 20px;
     ">
            立即下载
    </span>
    </div>
  </div>
</div>

<img class="img1" src="<%=basePath%>upload/image/${edit.sid}.jpg?r=<%=r%>"/>
</div>

<div style="
padding:15px;
color:rgb(119,119,119);
">
    <div>
    ${edit.designerWords}
    </div>
    <div style="
width:100%;
text-align:right;
margin-top: 30px;
">
    ---设计师说
</div>
</div>



<div
style="padding:20px;"
>
商品信息
<table style="padding:10px;width:100%;color:rgb(119,119,119)">
    <tr>
        <td style="width:40%">价格</td>
        <td style="width:60%">${edit.price}</td>
    <tr>
    <tr>
        <td style="width:40%">品类</td>
        <td style="width:60%">${edit.label_pinlei_name}</td>
    <tr>
    <tr>
        <td style="width:40%">可选颜色</td>
        <td style="width:60%">如图</td>
    <tr>
    <tr>
        <td style="width:40%">适应场景</td>
        <td style="width:60%">${edit.label_changjing_name}</td>
    <tr>
</table>
</div>


<div
onclick="window.open('https://itunes.apple.com/us/app/百德珠宝/id1252934482?l=zh&ls=1&mt=8')" 
style="background-color:rgb(172,146,111);text-align: center;padding-top: 10px;padding-bottom: 10px;width:100%;color:white;
position:fixed;
bottom: 0;
">
想获取更多珠宝信息，马上下载百德珠宝APP吧
</div>

</body>


<script>



</script>

</html>