<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	


<div style="float: left;width: 180px;padding: 10px;">
   <div id="tree" class="layui-box layui-tree">
	</div>
</div>

<div style="float: left;padding: 10px;">当前权限
	<form id="item" class="layui-form" action="">
        <div class="layui-form-item">
                <input type="text" name="name"  autocomplete="off" placeholder="" class="layui-input">
        </div>
        <div class="layui-form-item">
                <input type="text" name="code"  autocomplete="off" placeholder="" class="layui-input">
                <input type="hidden" name="id">
        </div>
        
        <div class="layui-form-item">
        	<input type="text" name="lastUpdateUser" readonly="readonly"  class="layui-input">
        </div>
        <div class="layui-form-item">
        	<input type="text" name="lastUpdateTime"  class="layui-input">
        </div>
         <div class="layui-form-item">
        	<input type="text" name="createUser"  class="layui-input">
        </div>
         <div class="layui-form-item">
        	<input type="text" name="createTime"  class="layui-input">
        </div>
    </form>
     <div class="layui-form-item">
        	<button class="layui-btn layui-btn-small" onclick="save()">修改</button>
        	<button class="layui-btn layui-btn-small" onclick="del()">删除</button>
        </div>
</div>

<div style="float: left;padding: 10px;">新增权限
	<form id="add" class="layui-form" action="">
        <div class="layui-form-item">
                <input type="text" name="name"  autocomplete="off" placeholder="" class="layui-input">
                <input type="hidden" name="pid">
                <input type="hidden" name="dept">
        </div>
        <div class="layui-form-item">
                <input type="text" name="code"  autocomplete="off" placeholder="" class="layui-input">
        </div>
        <div class="layui-form-item">
        	<button class="layui-btn layui-btn-small" onclick="add()">新增</button>
        </div>
    </form>
</div>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var options = {
		 elem: '#tree',
		 nodes:${tree},
		 click: function(node){
			 debugger;
			 //$('#item input[name="name"]').val(node.name);
			 loadJsonDataToForm(node,'item')
			 $('#add input[name="pid"]').val(node.id);
			 $('#add input[name="dept"]').val(node.dept+1);
    		 console.log(node) //node即为当前点击的节点数据
  		 } 
}
layui.tree(options);


function add(){
	debugger;
	var saveUrl = "<%=basePath%>privilege/save";
	$.post(saveUrl,$('#add').serialize(),function(data){
		  parent.layer.closeAll();
		  location.reload();
	});
	
	
}


function del(){
	var url = "<%=basePath%>privilege/delete/"+$('#item input[name=id]').val();
	$.get(url,function(data){
		  location.reload();
	});
}
</script>
</pagejs>
</body>
</html>