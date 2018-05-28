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
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    LinkedHashMap<String,String> all = new LinkedHashMap();
    all.put("0","非会员");
    all.putAll(DDData.dd.get("xiaofeika"));
    all.putAll(DDData.dd.get("huijika"));
    
    pageContext.setAttribute("allCard", all);
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

<c:choose>
<c:when test="${tenant == null }">
         推荐人不存在
</c:when>
<c:otherwise>
<form id="item" class="layui-form" action="">
    
         <div class="layui-form-item">
            <label class="layui-form-label">推荐人</label>
            <label class="layui-form-label" style="text-align: left">${tenant.phone}</label>
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label">卡类型</label>
            <label class="layui-form-label" style="text-align: left">${allCard[fn:trim(tenant.vip)]}</label>
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label">到期日期</label>
            <label class="layui-form-label" style="text-align: left">${tenant.vipDeadLine}</label>
              <input type="hidden" name="oldVipDeadLine" value="${tenant.vipDeadLine}">
              <input type="hidden" name="recommended" value="${recommended}">
              <input type="hidden" name="referrer" value="${tenant.phone}">
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label">延迟日期到</label>
            <div class="layui-input-inline">
                <input type="text" name="newVipDeadLine"  placeholder="" 
                        autocomplete="off" class="layui-input" value="${tenant.vipDeadLine}" lay-verify="required"  onclick="layui.laydate.render({elem: this})">
            </div>
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label">增加积分</label>
            <div class="layui-input-inline">
                <input type="text" name="jifen"  lay-verify="required|number" autocomplete="on"  class="layui-input">
            </div>
     </div>
    <div class="layui-form-item">
       <div class="layui-input-block">
        <button class="layui-btn layui-btn-small" lay-submit="" lay-filter="item">确定</button>
        </div>
    </div>
</form>

</c:otherwise>
</c:choose>


<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var addUrl = "<%=basePath%>bdzb/recommendrecord/save";

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
		if(data.msg == msgOK){
			  parent.document.location.reload(true);
		}else{
			 layer.msg(data.msg);			
		}
	});
}

</script>
</pagejs>
</body>
</html>