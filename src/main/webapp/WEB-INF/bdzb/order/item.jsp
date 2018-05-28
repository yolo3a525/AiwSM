<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String r = new Date().getTime()+ "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
    
    
    <style type="text/css">
    .layui-form-label{
    
    	width:150px;
    
    }
   
    
    
    
    </style>
</head>


<body>
	
<form id="item" class="layui-form" action="">
	 <input type="hidden" name="id">
	 
	 <div class="layui-form-item">
  		<label class="layui-form-label" >预约办理时间</label>
  		<div class="layui-input-inline">
    	<input type="text" name="appointmentTime" lay-verify="datetime" placeholder="" 
              	autocomplete="off" class="layui-input" onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
          </div>
   	 </div> 
   	  <div class="layui-form-item">
            <label class="layui-form-label">借戴人手机号码</label>
            <label class="layui-form-label" style="text-align:left"> ${tenant.phone }</label>
     </div>
      <div class="layui-form-item">
            <label class="layui-form-label">身份证号码</label>
            <label class="layui-form-label" style="text-align:left"> ${tenant.idCard}</label>
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label"> 意愿的押金方式（调查）</label>
            <div class="layui-input-block">
                <input type="checkbox" name="like1[write]" lay-skin="primary" title="直接缴纳押金">
                <input type="checkbox" name="like1[read]" lay-skin="primary" title="抵押珠宝">
                <input type="checkbox" name="like1[game]" lay-skin="primary" title="预存款转押金">
                <input type="checkbox" name="like1[game]" lay-skin="primary" title="银行三方监管">
                <input type="checkbox" name="like1[game]" lay-skin="primary" title="使用固定押金">
            </div>
     </div>
     <div class="layui-form-item">
    		<label class="layui-form-label">开始时间</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="startTime" lay-verify="datetime" placeholder="" 
                	autocomplete="off" class="layui-input" onblur="clearexcessAmount()" onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
            </div>
     </div>
     <div class="layui-form-item">
    		<label class="layui-form-label">结束时间</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="endTime" lay-verify="datetime" placeholder="" 
                	autocomplete="off" class="layui-input" onblur="clearexcessAmount()" onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">联系人姓名</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="tName"  lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
     <div class="layui-form-item">
            <label class="layui-form-label">联系人电话</label>
            <div class="layui-input-inline">
        
                <input type="text" name="tPhone"  lay-verify="required" autocomplete="on"  class="layui-input">
                    
            </div>
     </div>
     
     <div class="layui-form-item">
        <label class="layui-form-label">应付押金</label>
        <div class="layui-input-inline">
        <input type="text" name="deposit"  lay-verify="required" autocomplete="on"  class="layui-input">
        </div>
      </div>
     
     
     <div class="layui-form-item">
	    <label class="layui-form-label">押金支付方式</label>
	    <div class="layui-input-inline">
	      <input type="radio" name="depositMethod"
	       <c:if test='${edit.depositMethod == 0 || edit.depositMethod == 1}'>
	       checked
	       </c:if>
	      value="1" title="现金支付" checked="">
	    </div>
	  </div>
     
	 <div class="layui-form-item">
    		<label class="layui-form-label"></label>
    		<div class="layui-input-block">
                <input type="radio" name="depositMethod"
                <c:if test='${edit.depositMethod == 2}'>
		           checked
		           </c:if> 
                value="2" title="余额转押金(余额: ${tenant.balance })"> 
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
    	<button class="layui-btn" lay-submit="" lay-filter="item">修改</button>
    	<c:if test="${edit.status == 0}">
    		<button class="layui-btn" lay-submit="" lay-filter="rent">借戴</button>
    	</c:if>
    </div>
    
    <table class="layui-table" lay-even="" style="">
        <colgroup>
            <col width="50">
                <col>
                <col>
                <col width="200">
                <col>
                <col>
        </colgroup>
        <thead>
            <tr>
                <th>id</th>
                <th>名称</th>
                <th>图片</th>
                <th>珠宝编号</th>
                <th>价格</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty list}">
                <c:forEach items="${list}" var="a">
                    <tr>
                    <td>${a.jewelryid }</td>
                    <td>${a.name}</td>
                    <td><img alt="" src="<%=basePath%>upload/image/${a.sid}.jpg?r=<%=r%>" width="150"></td>
                    <td>${a.sid}</td>
                        <td>${a.price}</td>
                        <td>
                            <c:if test="${order.status == 0}">
                                <div class="layui-btn-group">
                                    <button class="layui-btn layui-btn-small" onclick="removeDetail(${a.orderid},${a.jewelryid});">移除</button>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>
</form>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form;
var addUrl = "<%=basePath%>bdzb/order/create";
var updateUrl ="<%=basePath%>bdzb/order/update";

<c:if test="${not empty edit}">
loadJsonDataToForm('${el:toJsonString(edit)}','item')
</c:if>


function clearexcessAmount(){
    $('#excessAmountDiv').css("display","none")
    $('#excessAmount').val("")
}

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

form.on('submit(item)', function(data){
	save();
    return false;
});

form.on('submit(rent)', function(data){
	rent(${edit.id});
    return false;
});


function rent(id){
	  update(id,1) 	
}

function update(id,status){
  debugger;
  $.post('<%=basePath%>bdzb/order/update',$('#item').serialize() + "&status="+status,function(data){
	    if(data.msg == msgOK){
	    	layer.alert("租赁完成");
			parent.document.location.reload(true);
	    }else if (data.code == 1001){
            $('#excessAmountDiv').css("display","block")
            $('#excessAmount').val(data.msg)
        }else{
            layer.msg(data.msg);
        }
	}); 
}

//保存
function save(){
	url = updateUrl;
	if($('#item input[name=id]').val() == ""){
		url = addUrl;
	}
	$.post(url,$('#item').serialize(),function(data){
		if(data.msg == "OK"){
            parent.document.location.reload(true);  
        }
	});
}

function removeDetail(id,jewelryid){ 
    $.post('<%=basePath%>bdzb/order/detail/remove/'+  id + '/' + jewelryid ,{},function(data){
          document.location.reload(true);
    });   
}

</script>

</pagejs>
</body>
</html>