<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String r = new Date().getTime()+ "";
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

</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<li >添加</li>
			<li >场景排序</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">



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

	<table class="layui-table" lay-even="" style="">
	<!-- 
		<colgroup>
			<col width="50">
				<col>
				<col>
				<col>
				<col>
			<col width="200">
			<col width="100">
			<col width="200">
			<col width="100">
			<col width="150">
		</colgroup>
	  -->
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>title</th>
				<th>图片</th>
				<th>是否显示</th>
				<th>顺序</th>
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
						<td>
							<c:choose> 
							  <c:when test="${not empty a.imageFlag && a.imageFlag == 1}">
							  <a href="javscript:;" onclick="bigTitleImage(${a.id})">
							  	<img alt="" src="<%=basePath%>upload/sence/${a.id}_title.jpg?r=<%=r%>" width="30">
							  </a>
							  </c:when>
							  	<c:otherwise>
							    没有图片
							  </c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose> 
							  <c:when test="${not empty a.imageFlag && a.imageFlag == 1}">
							  <a href="javscript:;" onclick="bigImage(${a.id})">
							  	<img alt="" src="<%=basePath%>upload/sence/${a.id}.jpg?r=<%=r%>" width="30">
							  </a>
							  </c:when>
							  	<c:otherwise>
							    没有图片
							  </c:otherwise>
							</c:choose>
						</td>
						<td>
						
						<c:choose> 
							  <c:when test="${not empty a.isShow && a.isShow == 1}">
							  	显示
							  </c:when>
							  	<c:otherwise>
							      不显示
							  </c:otherwise>
							</c:choose>
						</td>
					<td>${a.order}</td>
					
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.lastUpdateUser }</td>
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.createUser }</td> 
						<td>
							<div class="layui-btn-group">
							<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">编辑</button>
							
							<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>bdzb/sence/delete/${a.id}">删除</button>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
	<div id="page"></div>

</div>
			
		<div class="layui-tab-item">
			<iframe src="<%=basePath%>bdzb/sence/badd" id= "add_iframe" height=1000 scrolling="no" frameborder="0"></iframe>
		</div>
		<div class="layui-tab-item">
			<iframe src="<%=basePath%>bdzb/sence/graph" id= "add_iframe" height=1000 scrolling="no" frameborder="0"></iframe>
		</div>
	</div>
</div>	


<pagejs>

<script>
var $ = layui.jquery, form = layui.form
	,laypage = layui.laypage,layer = layui.layer,element = layui.element;
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

  
 function del(id){
 	var url = "<%=basePath%>bdzb/sence/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 function bigImage(id){
	  parent.layer.open({
	        type: 1
	        ,title: false //不显示标题栏
	        ,area: ['680px']
	        ,closeBtn: false
	        ,shade: 0.8
	        ,id: 'bigImage' //设定一个id，防止重复弹出
	        ,btn: ['关闭']
	        ,moveType: 1 //拖拽模式，0或者1
	        ,content: '<div><img src="<%=basePath%>upload/sence/'+id+'.jpg?r=<%=r%>" width="680" ></img></div>'
	        ,success: function(layero){
	        }
	      });	 
 }
 function bigTitleImage(id){
	  parent.layer.open({
	        type: 1
	        ,title: false //不显示标题栏
	        ,area: ['680px']
	        ,closeBtn: false
	        ,shade: 0.8
	        ,id: 'bigImage' //设定一个id，防止重复弹出
	        ,btn: ['关闭']
	        ,moveType: 1 //拖拽模式，0或者1
	        ,content: '<div><img src="<%=basePath%>upload/sence/'+id+'_title.jpg?r=<%=r%>" width="680" ></img></div>'
	        ,success: function(layero){
	        }
	      });	 
}
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/sence/bedit/'+id+'" style="height:1000px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
 
</script>
</pagejs>
</body>
</html>