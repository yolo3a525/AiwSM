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
<title>${module}</title>
</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="F_sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this" data-url="/a/sys/role/">列表</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">
				
				
				<form class="layui-form "  id="query" action="">
				 <input type="hidden" name="currentPage" value="1" id="currentPage" />
				 <input type="hidden" name="pageSize" value="${page.pageSize}" id="pageSize" />
				</form>
				
				
				<table class="layui-table" lay-even="" style="">
					<colgroup>
						<col>
						<col>
						<col>
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
							<th>requestURI</th>
							<th>requestParameters</th>
							<th>请求时间</th>
							<th>操作人</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty list}">
							<c:forEach items="${list}" var="a">
								<tr>
									<td>${a.id }</td>
									<td>${a.requestURI }</td>
									<td>${a.requestParameters }</td>
									<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
									<td>${a.lastUpdateUser }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
			
			
				</table>
				
				<div style="float: left">
					显示
					<select name="pageSizeSelect" id="pageSizeSelect" aria-controls="list-admin"
						class="select">
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="50">50</option>
					</select>
					
					第${page.currentPage}/${page.totalPage}页,共${page.totalSize}条.
				</div>
				<div id="page" style="float: right"></div>
			</div>
		</div>
	</div>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form,laypage = layui.laypage,layer = layui.layer,
	element = layui.element;
	
$('#pageSizeSelect').val(${page.pageSize});

$('#pageSizeSelect').on('change', function(){
	$.get("<%=basePath%>sys/pageSize/" + $('#pageSizeSelect').val(),function(data){
		$('#query').submit();
	});
});
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
         ,content: "<%=basePath%>dd/bsave"
         ,area: ['300px']    
     });
 	
 	
 }
  
 function del(id){
 	var url = "<%=basePath%>requestrecord/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
</script>
</pagejs>
</body>
</html>