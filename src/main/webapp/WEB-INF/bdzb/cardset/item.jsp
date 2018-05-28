<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <script type="text/javascript">
    	var xiaofeika = {
  			<c:if test="${not empty xiaofeika}">
  				<c:forEach items="${xiaofeika}" var="a">
  					"${a.key }":"${a.value }",
  				</c:forEach>
  			</c:if>
  			}
    	var huijika = {
    			<c:if test="${not empty huijika}">
    				<c:forEach items="${huijika}" var="a">
    					"${a.key }":"${a.value }",
    				</c:forEach>
    			</c:if>
    			}
    </script>
    
</head>
<body>

<form id="item" class="layui-form" action="">
	
	  <div class="layui-form-item">
    		<label class="layui-form-label">类型1</label>
    		<div class="layui-input-inline">
    			<select name="type" id="type" lay-filter="test">
			  		<option value='1'> 消费卡</option>
			  		<option value='2'> 会籍卡</option>
      			</select>
            </div>
     </div>
     <div class="layui-form-item">
    		<label class="layui-form-label">类型2</label>
    		 <div class="layui-input-inline" id="type2outdiv">

            </div>
     </div>
	
		 <div class="layui-form-item">
    		<label class="layui-form-label">会费/消费金额(万)	</label>
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

form.on('select(test)', function(data){
	  //console.log(data.elem); //得到select原始DOM对象
	  //console.log(data.value); //得到被选中的值
	  //console.log(data.othis); //得到美化后的DOM对象
	  $("#type2outdiv").empty();
	  $("#type2outdiv").append("<select id='type2' name='id' lay-filter='type2filter'></select>");
	  if(data.value == 1){
		$.each(xiaofeika,function(name,value) {
  	    	$("#type2").append("<option value='"+name+"'>"+value+"</option>");  
  	    });
	  }else{
		 $.each(huijika,function(name,value) {
	  	    	$("#type2").append("<option value='"+name+"'>"+value+"</option>");  
	  	 }); 
	  }
	  form.render('select');
});

$("#type2outdiv").empty();
$("#type2outdiv").append("<select id='type2' name='id' lay-filter='type2filter'></select>");
$.each(xiaofeika,function(name,value) {
  	$("#type2").append("<option value='"+name+"'>"+value+"</option>");  
});
form.render('select');

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
</html>