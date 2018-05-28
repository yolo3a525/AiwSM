<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
</head>
<body>

	<div style="padding: 10px 20px 0px 5px">
    <form id="item" class="layui-form" action="<%=basePath%>code/create">
    	<div class="layui-form-item">
        	<div class="layui-inline">
        		<label class="layui-form-label">module</label>
        		<div class="layui-input-inline">
                <input type="text" name="module" value="business"  lay-verify="required" autocomplete="on"  class="layui-input">
                </div>
        		<label class="layui-form-label">component</label>
        		<div class="layui-input-inline">
                <input type="text" name="component" value="Order"  lay-verify="required" autocomplete="on"  class="layui-input">
                </div>
            </div>
        </div>
    	<div id="entity">
        	<div class="layui-form-item">
	        	<div class="layui-inline">
	        		<label class="layui-form-label">字段</label>
	        		<div class="layui-input-inline">
	                <input type="text" name="key"  lay-verify="required" autocomplete="on"  class="layui-input">
	                </div>
	        		<label class="layui-form-label">类型</label>
	        		<div class="layui-input-inline">
	                <input type="text" name="type" value="String"  lay-verify="required" autocomplete="on"  class="layui-input">
	                </div>
	                <div class="layui-input-inline">
	                 	<button class="layui-btn"  type="button" onclick="delRow()">删除</button>
	                </div>
	            </div>
            </div>
         </div>
         
         <div class="layui-form-item">
     	   <div class="layui-input-block">
     	   		<button class="layui-btn" type="button" onclick="addRow()">添加</button>
        		<button class="layui-btn" lay-submit="" lay-filter="item">确定</button>
        	</div>
      	 </div>
    </form>
    </div>
     
<script src="<%=basePath%>lay/dest/layui.all.js"></script>
<script>
var module = "code"
var $ = layui.jquery, form = layui.form;
var saveUrl = "<%=basePath%>"+module+"/create";
var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
parent.layer.iframeAuto(index)//让自适应高度

//自定义验证规则
form.verify({
	username: function(value){
        if(value.length < 3){
            return '至少3个字符';
        }
    }
    ,password: [/(.+){6,12}$/, '密码必须6到12位']
});

form.on('submit(item)', function(data){
	//save();
    return true;
});


//保存
function save(){
	$.post(saveUrl,$('#item').serialize(),function(data){
		//parent.layer.close(index); //再执行关闭
		//var iframeid = $(parent.document).find(".layui-tab-title.site-demo-title .layui-this").attr("lay-id");
		//parent.document.getElementById('iframe'+iframeid).contentWindow.location.reload(true);
	});
}

function addRow(){
	
	
	$('#entity').append($($('#entity .layui-form-item')[0]).clone());
	
}
function delRow(){
	if($('#entity').children().length > 1){
		$(event.target).parent().parent().parent().remove();
	}
}

</script>
</body>
</html>