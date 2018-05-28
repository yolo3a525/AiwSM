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
</head>
<body>

    <form id="item" class="layui-form" action="">
    	<input type="hidden" name="id">
		 <div class="layui-form-item">
        		<label class="layui-form-label">name</label>
        		 <div class="layui-input-inline">
                <input type="text" name="name"  lay-verify="required" autocomplete="on"  class="layui-input">
                </div>
         </div>
		 <div class="layui-form-item">
        		<label class="layui-form-label">权限码</label>
        		 <div class="layui-input-inline">
                <input type="text" name="code"  lay-verify="required" autocomplete="on"  class="layui-input">
                </div>
         </div>
        <div class="layui-form-item">
     	   <div class="layui-input-block">
        	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
        	</div>
        </div>
    </form>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var addUrl = "<%=basePath%>privilege/save";
var updateUrl ="<%=basePath%>privilege/update";
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