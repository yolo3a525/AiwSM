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
<title>${module}</title>
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
					      <button class="layui-btn" type="button" onclick="add()">新增</button>
					    </div>
				    </div>
				  </div>
				</form>
				
				
				<table class="layui-table" lay-even="" style="">
					
					<thead>
						<tr>
							<th>code</th>
							<th>name</th>
							<th>最后修改时间</th>
							<th>更新人</th>
							<th>创建时间</th>
							<th>创建人</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty list}">
							<c:forEach items="${list}" var="a">
								<tr>
									<td>${a.code }</td>
									<td>${a.name }</td>
									<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
									<td>${a.lastUpdateUser }</td>
									<td><fmt:formatDate value="${a.createTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
									<td>${a.createUser }</td>
									<td style="text-align:right">
									<div class="layui-btn-group">
										<button class="layui-btn layui-btn-small" onclick="addDD('${a.code}');">增加子类</button>
										<button class="layui-btn layui-btn-small" onclick="edit('${a.code}');">编辑</button>
										<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>dg/delete/${a.code}">删除</button>	
									</div>
											
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
			
			
				</table>
				
				<div id="page"></div>
				

<pagejs>
<script>
var $ = layui.jquery, form = layui.form,laypage = layui.laypage,layer = layui.layer,
	element = layui.element;


loadJsonDataToForm('${el:toJsonString(query)}','query')


function add(){
	layer.open({
        type: 2
        ,id: 'addDG'
        ,content: "<%=basePath%>dg/badd"
        ,area: ['500px','300px']
    });
}
function addDD(code){
	layer.open({
        type: 2
        ,id: 'addDG'
        ,content: "<%=basePath%>dd/badd/"+code
        ,area: ['500px','300px']
    });
}
function edit(code){
	layer.open({
        type: 2
        ,id: 'editDG'
        ,content: "<%=basePath%>dg/bedit/"+code
        ,area: ['500px','300px']
    });
}


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
         ,content: "<%=basePath%>dd"
         ,area: ['300px']    
     });
 	
 	
 }
  
 function del(id){
 	var url = "<%=basePath%>dd/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
</script>
</pagejs>
</body>
</html>