<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="el-common" prefix="el"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("jewelryStatus", BDZBConstants.jewelryStatus);
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
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">


	<table class="layui-table" lay-even="" style="">
		<colgroup>
			<col width="50">
				<col>
				<col>
				<col>
				<col>
			<col width="216">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>名称</th>
				<th>图片</th>
				<th>实物价格</th>
				<th>状态</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
						<td>${a.id }</td>
						<td>${a.name}</td>
						<td>
								<img alt="" src="/upload/image/${a.sid}.jpg" width="30">
						</td>
						<td>${a.price}</td>
						<td>
						  ${jewelryStatus[a.orderStatus]}
						</td>
						<td>
							<div class="layui-btn-group">
								<button class="layui-btn layui-btn-small layui-btn-danger" onclick="remove('${a.id}');">移除</button>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
	<c:if test="${list != null && fn:length(list) > 0}">
		<button class="layui-btn" type="button" onclick="apply();">出租下单</button>
	</c:if>
	
</div>
			

	</div>
</div>	




<pagejs>

<script>

  
 function remove(id){
 	var url = "<%=basePath%>bdzb/rentcart/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/jewelry/bedit/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "订单",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
  
  
  function order(id){
		 subFilter = "sub_tab";
		 subId = "order_tab" ;
		 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
		 mainHeight = document.body.scrollHeight  - 200;
	     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/order/list?id='+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
	     element.tabAdd(subFilter, {
	         title	: "订单",
	         content	: iframe,
	         id		: subId //tab 的 id
	     });
	     element.tabChange(subFilter, subId);
	 }
  
  
  function play(id){ 
	  parent.layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,area: ['544px', '620px']
        ,closeBtn: false
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div><embed src="/uploadFiles/file/'+id+'.mp4" type="audio/mpeg" width="544" height="544" ></embed></div>'
        ,success: function(layero){
        }
      });	 	
 }
  
  
  function apply(){ 
	  
	  parent.layer.open({
        type: 2
        ,title:"后台下单"
        ,area: ['800px', '600px']
        ,id: 'apply' //设定一个id，防止重复弹出
        ,content:  '<%=basePath%>bdzb/order/bcreate'
        ,success: function(layero){
        }
      });	 	
 }
 
</script>
</pagejs>
</body>
</html>