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
    <form id="item" class="layui-form" action="">
    
    			 <div class="layui-form-item">
        		<label class="layui-form-label">name</label>
        		 <div class="layui-input-inline">
                <input type="text" name="name"  lay-verify="required" autocomplete="on"  class="layui-input">
                </div>
         </div>
        <div class="layui-form-item">
     	   <div class="layui-input-block">
        	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
        	</div>
        </div>
    </form>
    </div>
<script src="<%=basePath%>lay/dest/layui.all.js"></script>
<script>
var $ = layui.jquery, form = layui.form;
var saveUrl = "<%=basePath%>order/save";
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
	save();
    return false;
});


//保存
function save(){
	$.post(saveUrl,$('#item').serialize(),function(data){
		parent.layer.close(index); //再执行关闭
		var iframeid = $(parent.document).find(".layui-tab-title.site-demo-title .layui-this").attr("lay-id");
		if(parent.document.getElementById('iframe'+iframeid) == null){
			parent.document.location.reload(true);
		}else{
			parent.document.getElementById('iframe'+iframeid).contentWindow.location.reload(true);
		}
	});
}

</script>
</body>
</html>