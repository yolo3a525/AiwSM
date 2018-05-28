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

	<div style="padding: 10px 20px 0px 0px">
    <form id="item" class="layui-form" action="">
    
    	<c:choose> 
		  <c:when test="${ edit == null }">
		  	<div class="layui-form-item">
        		<label class="layui-form-label">code</label>
        		 <div class="layui-input-inline">
                <input type="text" name="code"  lay-verify="required" autocomplete="on"  class="layui-input">
                </div>
        </div>
		  </c:when>
		  	<c:otherwise>
			<input type="hidden" name="code">
		  </c:otherwise>
		</c:choose>
    
    	
        
        <div class="layui-form-item">
        		<label class="layui-form-label">name</label>
        		 <div class="layui-input-inline">
                <input type="text" name="name"   lay-verify="required" autocomplete="off"  class="layui-input">
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
var addUrl = "<%=basePath%>dg/save";
var updateUrl = "<%=basePath%>dg/update";
var addFlag = true;

<c:if test="${not empty edit}">
addFlag = false;
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
	url = addUrl;
	if(!addFlag){
		url = updateUrl;
	}
	$.post(url,$('#item').serialize(),function(data){
		parent.document.location.reload(true);
	});
}

</script>
</pagejs>
</body>
</html>