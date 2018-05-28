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
<title>business</title>
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
<form class="layui-form "  id="query" action="">
  <div class="layui-form-item">
  	<div class="layui-inline">
	    <label class="layui-form-label">类别(模糊)</label>
	    <div class="layui-input-inline">
	      <input type="text" name="code" autocomplete="off" class="layui-input">
	      <input type="hidden" name="currentPage" value="1" id="currentPage" />
	    </div>
    </div>
    <div class="layui-inline">
	    <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" lay-filter="query">查询</button>
	    </div>
    </div>
  </div>
</form>

	<button class="layui-btn layui-btn-small" onclick="bsave()">新增</button>
	<table class="layui-table" lay-even="" style="">
		<colgroup>
			<col width="50">
				<col>
			<col width="200">
			<col width="100">
			<col width="200">
			<col width="100">
			<col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td>${a.id }</td>
						<td>${a.name}</td>
					<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${a.lastUpdateUser }</td>
					<td><fmt:formatDate value="${a.createTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${a.createUser }</td> 
						<td>
							<button
								class="layui-btn layui-btn-small layui-btn-danger"
								onclick="del('${a.id}');">删除</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>


<div id="page"></div>
<script src="<%=basePath%>lay/dest/layui.all.js"></script>
<script src="<%=basePath%>my/form.js"></script>

<script>
var $ = layui.jquery, form = layui.form,laypage = layui.laypage,layer = layui.layer;
loadJsonDataToForm('${el:toJsonString(query)}','query')

//全选
form.on('checkbox(allChoose)', function(data){
    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
    child.each(function(index, item){
        item.checked = data.elem.checked;
    });
    form.render('checkbox');
});

laypage.render({
    cont: 'page'
    ,pages: ${page.totalPage}
    ,groups: 5 //连续显示分页数
    ,last:${page.totalPage}
    ,curr:${page.currentPage}
    ,jump:function(obj,first){
    	if(first != true){
    		debugger;
    		$('#currentPage').val(obj.curr);
	    	$('#query').submit();
    	}
    }
});

 
 function bsave(){
 	
 	parent.layer.open({
         type: 2
         ,offset:"150px"
         ,title:"新增"
         ,id: 'itemadd'
         ,content: "<%=basePath%>order/bsave"
         ,area: ['300px']    
     });
 	
 	
 }
  
 function del(id){
 	var url = "<%=basePath%>order/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
</script>

</body>
</html>