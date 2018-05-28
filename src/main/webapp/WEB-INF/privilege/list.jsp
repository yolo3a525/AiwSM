<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<li >添加</li>
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
					<thead>
						<tr>
							<th>id</th>
							<th>权限名称</th>
							<th>权限码</th>
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
									<td>${a.code}</td>
								<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
								<td>${a.lastUpdateUser }</td>
								<td><fmt:formatDate value="${a.createTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
								<td>${a.createUser }</td> 
									<td>
									<div class="layui-btn-group">
									
										<button
											class="layui-btn layui-btn-small"
											onclick="edit('${a.id}');">编辑</button>
											
										
										<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>privilege/delete/${a.id}">删除</button>	
											
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
				<iframe src="<%=basePath%>privilege" id= "add_iframe" height=600 scrolling="no" frameborder="0"></iframe>
			</div>
		</div>
	</div>	
	
<pagejs>
<script>
var $ = layui.jquery, form = layui.form,laypage = layui.laypage,layer = layui.layer,element = layui.element;
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
 
 function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>privilege/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 	
 	
 }
  
 function del(id){
 	var url = "<%=basePath%>privilege/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
</script>
</pagejs>
</body>
</html>