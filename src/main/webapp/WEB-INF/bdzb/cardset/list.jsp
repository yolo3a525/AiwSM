<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="el-common" prefix="el"%>
<%@ page import="com.aiw.util.DDData" %>
<%@ page import="java.lang.String" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("xiaofeika", DDData.dd.get("xiaofeika"));
	pageContext.setAttribute("huijika", DDData.dd.get("huijika"));
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
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<li >添加</li>
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
				<col>
			<col width="200">
			<col width="100">
			<col width="150">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>类型1</th>
				<th>类型2</th>
				<th>会费押金/消费金额入会(万)</th>
				<th>免费时长(天)</th>
				<th>可佩戴总金额(万)</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td>${a.id }</td>
						<td>
							<c:if test="${a.type == 1}">
								消费卡
							</c:if>
							<c:if test="${a.type == 2}">
								会籍卡
							</c:if>
						</td>
						<td>
							<c:if test="${a.type == 1}">
								${xiaofeika[fn:trim(a.id)]}
							</c:if>
							<c:if test="${a.type == 2}">
								${huijika[fn:trim(a.id)]}
							</c:if>
						</td>
						<td>${a.dues}</td>
						<td>${a.userTime}</td>
						<td>${a.wearTotalMoney}</td>
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.lastUpdateUser }</td>
						<td>
							<div class="layui-btn-group">
							<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">编辑</button>
							<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>bdzb/cardset/delete/${a.id}">删除</button>	
							
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
			<iframe src="<%=basePath%>bdzb/cardset/badd" id= "add_iframe" height=600 scrolling="no" frameborder="0"></iframe>
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
 	var url = "<%=basePath%>bdzb/cardset/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/cardset/bedit/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
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