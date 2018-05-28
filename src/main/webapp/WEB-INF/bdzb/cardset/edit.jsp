<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="el-common" prefix="el"%>
<%@ page import="com.aiw.util.DDData" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("xiaofeika", DDData.dd.get("xiaofeika"));
	pageContext.setAttribute("huijika", DDData.dd.get("huijika"));
%>
<!DOCTYPE html>
<html>
<head>
	<title>卡管理</title>
	<style type="text/css">
	.layui-form-label {
    	width: 150px;
	}
	
	</style>
    
</head>
<body>

<form id="item" class="layui-form" action="">
	<input type="hidden" name="id">
	
	  <div class="layui-form-item">
    		<label class="layui-form-label">类型1</label>
    		<div class="layui-input-inline">
    		<label class="layui-form-label" style="text-align:left;">
    		
							<c:if test="${edit.type == 1}">
								消费卡
							</c:if>
							<c:if test="${edit.type == 2}">
								会籍卡
							</c:if>
				</label>
            </div>
     </div>
     <div class="layui-form-item">
    		<label class="layui-form-label">类型2</label>
    		 <div class="layui-input-inline">
	    		 <label class="layui-form-label" style="text-align:left;">
							<c:if test="${edit.type == 1}">
									${xiaofeika[fn:trim(edit.id)]}
								</c:if>
								<c:if test="${edit.type == 2}">
									${huijika[fn:trim(edit.id)]}
								</c:if>
				 </label>
            </div>
     </div>
	
		 <div class="layui-form-item">
    		<label class="layui-form-label">会费/消费金额(万)</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="dues"  lay-verify="required|number" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	 
	 <div class="layui-form-item">
    		<label class="layui-form-label">免费佩戴时间(天)</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="userTime"  lay-verify="required|number" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">可佩戴总金额(万)</label>
    		<div class="layui-input-inline">
		    	<input type="text" name="wearTotalMoney"  lay-verify="required|number" autocomplete="on"  class="layui-input">
            </div>
     </div>
    <div class="layui-form-item">
 	   <div class="layui-input-block">
    	<button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
    	</div>
    </div>
</form>


</body>

<pagejs>
<script>
var addUrl = "<%=basePath%>bdzb/cardset/save";
var updateUrl ="<%=basePath%>bdzb/cardset/update";

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
</html>