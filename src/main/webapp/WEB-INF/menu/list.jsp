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
<title>DEMO</title>
</head>
<body>
	


<div style="float: left;width: 180px;padding: 10px;">
   <div id="tree" class="layui-box layui-tree">
	</div>
</div>

<div style="float: left;padding: 10px;">
	<form id="item" class="layui-form" action="">
		<div class="layui-form-item">
			 <div class="layui-input-block">选中的菜单</div>
		</div>
        <div class="layui-form-item">
        		<label class="layui-form-label">name</label>
        		 <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
        	<label class="layui-form-label">url</label>
        		 <div class="layui-input-inline">
                <input type="text" name="url"  lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
                <input type="hidden" name="id">
                </div>
        </div>
         <div class="layui-form-item">
        		<label class="layui-form-label">权限码</label>
        		 <div class="layui-input-inline">
                <input type="text" name="privilegeCode"  lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
                <label class="layui-form-label">icon</label>
                 <div class="layui-input-inline">
                <input type="text" name="icon"  autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
        		<label class="layui-form-label">顺序</label>
        		 <div class="layui-input-inline">
                <input type="text" name="order"  lay-verify="required|number" autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
        	 <div class="layui-input-block">
        	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item" >修改</button>
        	<button class="layui-btn layui-btn-small" onclick="del()">删除</button>
        	</div>
        </div>
    </form>
</div>

<div style="float: left;padding: 10px;">
	<form id="add" class="layui-form" action="">
	
		<div class="layui-form-item">
			 <div class="layui-input-block">新增子菜单</div>
		</div>
		
        <div class="layui-form-item">
        <label class="layui-form-label">name</label>
        		 <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="required"  autocomplete="off" placeholder="" class="layui-input"/>
                <input type="hidden" name="pid"/>
                <input type="hidden" name="depth"/>
                </div>
        </div>
        <div class="layui-form-item">
        <label class="layui-form-label">url</label>
        		 <div class="layui-input-inline">
                <input type="text" name="url"  lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        
         <div class="layui-form-item">
        		<label class="layui-form-label">权限码</label>
        		 <div class="layui-input-inline">
                <input type="text" name="privilegeCode"  lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        
        <div class="layui-form-item">
                <label class="layui-form-label">icon</label>
                 <div class="layui-input-inline">
                <input type="text" name="icon"  autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        
        <div class="layui-form-item">
        		<label class="layui-form-label">顺序</label>
        		 <div class="layui-input-inline">
                <input type="text" name="order"  lay-verify="required|number" autocomplete="off" placeholder="" class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
         <div class="layui-input-block">
        	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="add" >新增</button>
        	</div>
        </div>
    </form>
</div>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var module = 'menu';
var options = {
		 elem: '#tree',
		 nodes:${tree},
		 click: function(node){
			 //$('#item input[name="name"]').val(node.name);
			 loadJsonDataToForm(node,'item')
			 $('#add input[name="pid"]').val(node.id);
			 $('#add input[name="depth"]').val(node.depth+1);
    		 console.log(node) //node即为当前点击的节点数据
  		 } 
}
layui.tree(options);



//自定义验证规则
form.verify({
	name: function(value){
        if(value.length < 3){
            return '至少3个字符';
        }
    }
});

form.on('submit(item)', function(data){
	debugger;
	update();
    return false;
});


form.on('submit(add)', function(data){
	add();
    return false;
});


function add(){
	if($('#add input[name=pid]').val() == ""){
		parent.layer.alert("必须选择一个当前菜单,才能增加子菜单."); 
		return;
	}
	
	if($('#add input[name=depth]').val() > 2 ){
		parent.layer.alert("暂时只能增加2级菜单"); 
		return;
	}
	
	
	var saveUrl = "<%=basePath%>"+module+"/save";
	$.post(saveUrl,$('#add').serialize(),function(data){
		 location.reload();
	});
	
	
}

function update(){
	if($('#item input[name=id]').val() == ""){
		parent.layer.alert("请选择一个当前菜单进行修改."); 
		return;
	}
	var saveUrl = "<%=basePath%>"+module+"/update";
	$.post(saveUrl,$('#item').serialize(),function(data){
		 location.reload();
	});
	
	
}

function del(){
	if($('#item input[name=id]').val() == ""){
		parent.layer.alert("没有选择当前菜单,删除失败.请点击菜单树选择."); 
		return;
	}
	var url = '<%=basePath%>'+module+'/delete/'+$('#item input[name=id]').val();
	$.get(url,function(data){
		  location.reload();
	});
}

</script>
</pagejs>
</body>
</html>