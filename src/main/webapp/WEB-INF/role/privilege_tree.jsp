<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	</head>	
<body>

</head>
<body>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>zTree/2.6/zTreeStyle.css"/>
	<link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
	
	<style type="text/css">
	footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
	</style>
	
	<div class="" style="padding: 5px">
	 <ul id="tree" class="tree" style="overflow:auto;"></ul>
	 
	 <div class="layui-layer-btn-c" style="margin-bottom: 50px">
	 	<button class="layui-btn layui-btn-small" onclick="save()">保存</button>
	 </div>
	</div>
	<pagejs>
<script src="<%=basePath%>lay/dest/layui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>zTree/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=basePath%>zTree/2.6/jquery.ztree-2.6.min.js"></script>
<script>
        var form = layui.form;
        //全选
        form.on('checkbox(allChoose)', function(data){
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function(index, item){
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });
        var laypage = layui.laypage
            ,layer = layui.layer;
        
        var zTree;
		$(document).ready(function(){
			var setting = {
			    showLine: true,
			    checkable: true
			};
			var zn = '${list}';
			var zTreeNodes = eval(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);
		});
	
		//保存
		 function save(){
			var nodes = zTree.getCheckedNodes();
			var tmpNode;
			var ids = "";
			for(var i=0; i<nodes.length; i++){
				tmpNode = nodes[i];
				if(i!=nodes.length-1){
					ids += tmpNode.id+",";
				}else{
					ids += tmpNode.id;
				}
			}
			var role_id = "${role_id}";
			var url = "<%=basePath%>role/savePrivilege";
			var postData;
			postData = {"role_id":role_id,"privilege_ids":ids};
			$.post(url,postData,function(data){
				  parent.layer.closeAll();
			});
		 }
        
		
	
	</script>
	</pagejs>
</body>
</html>