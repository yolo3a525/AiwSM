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
    <link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
    
    <title>后台下单</title>
    <style type="text/css">
    .layui-form-label{
    
    	width:150px;
    
    }
    
    
    
    </style>
</head>


<body>
	
<form id="item" class="layui-form" action="">
     <div class="layui-form-item">
    		<label class="layui-form-label">开始时间</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="startTime" lay-verify="datetime" placeholder="" 
                	autocomplete="off" class="layui-input" onblur="clearexcessAmount()"  onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
            </div>
     </div>
     <div class="layui-form-item">
    		<label class="layui-form-label">结束时间</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="endTime" lay-verify="datetime" placeholder="" 
                	autocomplete="off" class="layui-input" onblur="clearexcessAmount()"  onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
            </div>
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label">现金押金</label>
            <div class="layui-input-inline">
        
                <input type="text" name="deposit" value="${totalMoney}"  lay-verify="required" autocomplete="on"  class="layui-input">
                    
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">会员手机号码(即卡号)</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="phone"  lay-verify="required" onblur="clearexcessAmount()"  autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
	 <div id="excessAmountDiv" class="layui-form-item" style="display: none">
    		<label class="layui-form-label">超出租金</label>
    		<div class="layui-input-inline">
		    	<input type="text" id="excessAmount" name="excessAmount"  autocomplete="on"  class="layui-input">
            </div>
            <div class="layui-input-inline">
                                本金额有计算公式自动得出。可以根据实际情况自定定义金额。
            </div>
     </div>
     
    <div class="layui-form-item">
 	   <label class="layui-form-label"></label>
    	<button class="layui-btn" lay-submit="" lay-filter="rent">借戴</button>
    </div>
    
    <div class="layui-form-item">
            <div class="layui-input-inline">
                                    租金计算方式：
            <img src="<%=basePath%>zujinjisuan.jpg"/>
            
            
            </div>
     </div>
    
</form>

<pagejs>
<script>
var addUrl = "<%=basePath%>bdzb/order/create";


//自定义验证规则
form.verify({
	username: function(value){
        if(value.length < 3){
            return '至少3个字符';
        }
    }
    ,password: [/(.+){6,12}$/, '密码必须6到12位']
    ,datetime :[/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/,'日期时间格式不对.']
});


form.on('submit(rent)', function(data){
	save();
    return false;
});

function clearexcessAmount(){
	$('#excessAmountDiv').css("display","none")
    $('#excessAmount').val("")
}

//保存
function save(){
	url = addUrl;
	$.post(url,$('#item').serialize(),function(data){
		if(data.msg == "OK"){
			layer.msg("后台下单成功......");
			parent.document.location.reload(true);	
		}else if (data.code == 1001){
			$('#excessAmountDiv').css("display","block")
			$('#excessAmount').val(data.msg)
		}else{
			layer.msg(data.msg);
		}
	});
}

</script>

</pagejs>
</body>
</html>