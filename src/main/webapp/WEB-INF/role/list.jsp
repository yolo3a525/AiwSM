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
</head>
<body>

<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<li >添加</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">


	<form class="layui-form" id="query" action="">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">名称</label>
				<div class="layui-input-inline">
					<input type="text" name="name" autocomplete="off"
						class="layui-input"> <input type="hidden"
						name="currentPage" value="1" id="currentPage" />
				</div>
			</div>
			<div class="layui-inline">
				<div class="layui-input-inline">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">查询</button>
				</div>
			</div>
		</div>
	</form>

	<table class="layui-table" lay-even="">
		<colgroup>
			<col>
			<col>
			<col width="200">
			<col width="100">
			<col width="200">
			<col width="100">
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>名字</th>
				<th>修改时间</th>
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
						<td>${a.id }</td>
						<td>${a.name }</td>
						<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
						<td>${a.lastUpdateUser }</td>
						<td><fmt:formatDate value="${a.createTime}"  pattern="yyyy-MM-dd hh:mm:ss"/></td>
						<td>${a.createUser }</td>
						<td>								
							<div class="layui-btn-group">
										<button
										class="layui-btn layui-btn-small layui-btn-danger"
										onclick="privilegeTree('${a.id}');">权限</button>
										<button
											class="layui-btn layui-btn-small"
											onclick="edit('${a.id}');">编辑</button>
											
										<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>role/delete/${a.id}">删除</button>	
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
				<iframe src="<%=basePath%>role/badd" id= "add_iframe" height=600 scrolling="no" frameborder="0"></iframe>
			</div>
		</div>
	</div>	
	
	
	
	<pagejs>

	<script>
var $ = layui.jquery, form = layui.form,laypage = layui.laypage,layer = layui.layer,element = layui.element;

loadJsonDataToForm('${el:toJsonString(query)}','query')

var laypage = layui.laypage
    ,layer = layui.layer;


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
        
        function privilegeTree(roleId){
        	
        	parent.layer.open({
                type: 2
                ,id: 'tree'
                ,content: "<%=basePath%>role/privilegeTree/"+roleId
                ,area: ['300px']
            });
        	
        }
        function del(id){
        	var url = "<%=basePath%>role/delete/"+id;
        	$.get(url,function(data){
        		  location.reload();
        	});
        }
        
        function edit(id){
       	 
       	 subFilter = "sub_tab";
       	 subId = "edit_tab" ;
       	 
       	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
       	 mainHeight = document.body.scrollHeight  - 200;
            var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>role/bedit/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
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