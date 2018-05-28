<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>





	<div style="padding: 5px 20px 0px 5px">
    <form id="item" class="layui-form" action="">
    	<input type="hidden" name="id">
        <div class="layui-form-item">
        		<label class="layui-form-label">用户名</label>
        		 <div class="layui-input-inline">
                <input type="text" name="username"  lay-verify="username" autocomplete="off"  class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
        		<label class="layui-form-label">密码</label>
        		 <div class="layui-input-inline">
                <input type="password" name="password"   lay-verify="password" autocomplete="off"  class="layui-input">
                </div>
        </div>
        
        
        
        <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                 <div class="layui-input-inline">
                <input type="text" name="name"   autocomplete="off"  class="layui-input">
                </div>
        </div>
        
        
        <div class="layui-form-item">
                <label class="layui-form-label">手机号码</label>
                 <div class="layui-input-inline">
                <input type="text" name="phone"  autocomplete="off"  class="layui-input">
                </div>
        </div>
        <div class="layui-form-item">
                <label class="layui-form-label">邮箱</label>
                 <div class="layui-input-inline">
                <input type="text" name="email"  autocomplete="off"  class="layui-input">
                </div>
        </div>
        
        <div class="layui-form-item">
                <label class="layui-form-label">备注</label>
                 <div class="layui-input-inline">
                <input type="text" name="remark"  autocomplete="off"  class="layui-input">
                </div>
        </div>
        
        
        <div class="layui-form-item">
     	   <div class="layui-input-block">
        	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
        	</div>
        </div>
    </form>
    </div>
    
    
<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var addUrl = "<%=basePath%>user/save";
var updateUrl ="<%=basePath%>user/update";
<c:if test="${not empty edit}">
	loadJsonDataToForm('${el:toJsonString(edit)}','item')
</c:if>

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
	url = updateUrl;
	if($('#item input[name=id]').val() == ""){
		url = addUrl;
	}
	$.post(url,$('#item').serialize(),function(data){
		parent.document.location.reload(true);
	});
}

</script>
</pagejs>
</body>
</html>