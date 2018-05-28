<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ page import="com.aiw.util.DDData" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	
	request.setAttribute("tenantStatus", BDZBConstants.tenantStatus);
	pageContext.setAttribute("tenantStatus", BDZBConstants.tenantStatus);
	request.setAttribute("tenantDeadLine", BDZBConstants.tenantDeadLine);
	pageContext.setAttribute("tenantDeadLine", BDZBConstants.tenantDeadLine);
	
	
	LinkedHashMap<String,String> all = new LinkedHashMap();
	all.putAll(DDData.dd.get("xiaofeika"));
	all.putAll(DDData.dd.get("huijika"));
	
	pageContext.setAttribute("allCard", all);
	
	
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>

<form id="item" class="layui-form" action="">
	
	<div class="layui-form-item">
    		<label class="layui-form-label">类别</label>
    		 <div class="layui-input-inline">
		      <select name="type" id="type">
			       <c:forEach items="${allCard}" var="m">
							<option value='${m.key}'> ${m.value}</option>
					</c:forEach>
		      	  </select>
		    </div>
     </div>
	<div class="layui-form-item">
    		<label class="layui-form-label">时间(天)</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="days"  lay-verify="required|number" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	<div class="layui-form-item">
    		<label class="layui-form-label">过期时间</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="expires" id="expires" lay-verify="date" placeholder="" 
                	autocomplete="on" class="layui-input" >
                	
                
                	
            </div>
     </div>
	 
	 <div class="layui-form-item">
    		<label class="layui-form-label">数量</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="count"  lay-verify="required|number" autocomplete="on"  class="layui-input">
                	
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
var addUrl = "<%=basePath%>bdzb/rechargecard/batchCreate";
var updateUrl ="<%=basePath%>bdzb/rechargecard/update";

<c:if test="${not empty edit}">
loadJsonDataToForm('${el:toJsonString(edit)}','item')
</c:if>


layui.laydate.render({
    elem: '#expires'
});

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
	$.post(url,$('#item').serialize(),function(data){
		parent.document.location.reload(true);
	});
}

</script>
</pagejs>
</body>
</html>