<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%@ page import="com.aiw.util.DDData" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	LinkedHashMap<String,String> all = new LinkedHashMap();
	all.put("0","非会员");
	all.putAll(DDData.dd.get("xiaofeika"));
	all.putAll(DDData.dd.get("huijika"));
	
	pageContext.setAttribute("allCard", all);
	
	request.setAttribute("tenantStatus", BDZBConstants.tenantStatus);
	
	request.setAttribute("sexStatus", BDZBConstants.sexStatus);
	request.setAttribute("faxingStatus", DDData.ddLabel.get("faxing"));
	request.setAttribute("fuseStatus", DDData.ddLabel.get("fuse"));
	request.setAttribute("lianxingStatus", DDData.ddLabel.get("lianxing"));
	
%>
<!DOCTYPE html>
<html>
<head>
    <title>新增/修改客户资料</title>
    <style type="text/css">
    
    .huise {
    	color: #999999;
    }
    
    .layui-form-label{
    
    	width:150px;
    
    }
    
    </style>
    
</head>
<body>

<form class="layui-form "  id="item" action="">
<input type="hidden" name="id">

<fieldset class="layui-elem-field" style="margin-top: 30px;">

<legend>基本资料</legend>


  <div class="layui-form-item">
  	<div class="layui-inline">
	    <label class="layui-form-label">手机号码</label>
	    <div class="layui-input-inline">
	      <input  type="text" name="phone" readonly="readonly" lay-verify="required" autocomplete="on"  class="huise layui-input">
	    </div>
    </div>
    
    <div class="layui-inline">
	    <label class="layui-form-label">真实姓名</label>
	    <div class="layui-input-inline">
	      <input type="text" name="nickName"  lay-verify="required" autocomplete="on"  class="layui-input">
	    </div>
    </div>
    
    <div class="layui-inline">
    		<label class="layui-form-label">出生日期</label>
    		<div class="layui-input-inline">
	    	<input type="text" name="birthday"  placeholder="" 
	                	autocomplete="off" class="layui-input" lay-verify="required"  onclick="layui.laydate.render({elem: this})">
                	
            </div>
     </div>
    
    
     <div class="layui-inline">
    		<label class="layui-form-label">身份证号码</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="idCard"  lay-verify="required" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
     <div class="layui-inline">
    		<label class="layui-form-label">居住地址</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="address"  autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
    
     <div class="layui-inline">
	    <label class="layui-form-label">注册时间</label>
	    <div class="layui-input-inline">
	      <input  type="text" name="createTime" readonly="readonly" lay-verify="required" autocomplete="on"  class="huise layui-input">
	    </div>
    </div>
    
     <div class="layui-inline">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-inline">
             <select name="sex" id="sex">
               <c:forEach items="${sexStatus}" var="m">
                    <c:choose> 
                      <c:when test="${ edit.sex == m.key }">
                        <option value='${m.key}'selected > ${m.value}</option>
                      </c:when>
                        <c:otherwise>
                        <option value='${m.key}'> ${m.value}</option>
                      </c:otherwise>
                    </c:choose>
                </c:forEach>
              </select>
            </div>
     </div>
     
     <div class="layui-inline">
            <label class="layui-form-label">发型</label>
            <div class="layui-input-inline">
             <select name="faxing" id="faxing">
               <c:forEach items="${faxingStatus}" var="m">
                    <c:choose> 
                      <c:when test="${ edit.faxing == m.key }">
                        <option value='${m.key}'selected > ${m.value}</option>
                      </c:when>
                        <c:otherwise>
                        <option value='${m.key}'> ${m.value}</option>
                      </c:otherwise>
                    </c:choose>
                </c:forEach>
              </select>
            </div>
     </div>
     
     <div class="layui-inline">
            <label class="layui-form-label">肤色</label>
            <div class="layui-input-inline">
             <select name="fuse" id="fuse">
               <c:forEach items="${fuseStatus}" var="m">
                    <c:choose> 
                      <c:when test="${ edit.fuse == m.key }">
                        <option value='${m.key}'selected > ${m.value}</option>
                      </c:when>
                        <c:otherwise>
                        <option value='${m.key}'> ${m.value}</option>
                      </c:otherwise>
                    </c:choose>
                </c:forEach>
              </select>
            </div>
     </div>
     
     <div class="layui-inline">
            <label class="layui-form-label">脸型</label>
            <div class="layui-input-inline">
             <select name="lianxing" id="lianxing">
               <c:forEach items="${lianxingStatus}" var="m">
                    <c:choose> 
                      <c:when test="${ edit.lianxing == m.key }">
                        <option value='${m.key}'selected > ${m.value}</option>
                      </c:when>
                        <c:otherwise>
                        <option value='${m.key}'> ${m.value}</option>
                      </c:otherwise>
                    </c:choose>
                </c:forEach>
              </select>
            </div>
     </div>
    
    
       <div class="layui-form-item">
       	<label class="layui-form-label"></label>
	    <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" lay-filter="item">更新</button>
	    </div>
    </div>
  </div>
	
</fieldset>
</form>


<form class="layui-form "  id="item2" action="">
<input type="hidden" name="id">
<fieldset class="layui-elem-field" style="margin-top: 30px;">

<legend>会员信息</legend>	
		 
 <div class="layui-form-item">
     <div class="layui-inline">
    		<label class="layui-form-label">会员卡类型</label>
    		<div class="layui-input-inline">
    		 <select name="vip" id="vip">
		       <c:forEach items="${allCard}" var="m">
		       		<c:choose> 
					  <c:when test="${ edit.vip == m.key }">
					  	<option value='${m.key}'selected > ${m.value}</option>
					  </c:when>
					  	<c:otherwise>
						<option value='${m.key}'> ${m.value}</option>
					  </c:otherwise>
					</c:choose>
				</c:forEach>
	      	  </select>
            </div>
     </div>
      <div class="layui-inline">
    		<label class="layui-form-label">卡号</label>
    		<div class="layui-input-inline">
    			<div class="layui-form-mid">同手机号</div>
            </div>
     </div>
      <div class="layui-inline">
    		<label class="layui-form-label">最后会员日期</label>
    		<div class="layui-input-inline">
    		
    			<input type="text" name="vipDeadLine"  placeholder="" 
                	autocomplete="off" class="layui-input" onclick="layui.laydate.render({elem: this})">
            </div>
     </div>
   </div>
   <div class="layui-form-item">
      <div class="layui-inline">
    		<label class="layui-form-label">线下消费金额</label>
    		<div class="layui-input-inline">
    			<div class="layui-form-mid">${edit.consumeAmount}元</div>
    			<a href="#" class="layui-form-mid" style="color: blue;" onclick="addConsume()"  lay-filter="itemConsumeAmount">消费</a>
    			
    			
            </div>
     </div>
      <div class="layui-inline">
    		<label class="layui-form-label">积分</label>
    		<div class="layui-input-inline">
    			<div class="layui-form-mid">${edit.jifen}分</div>
    			<a href="#" class="layui-form-mid" style="color: blue;" onclick="consumeJifen()" lay-filter="itemConsumeAmount">积分消费</a>
    			<a href="#" class="layui-form-mid" style="color: blue;" onclick="jifenHistory()" lay-filter="itemConsumeAmount">记录</a>
            </div>
     </div>
     
     <div class="layui-inline">
            <label class="layui-form-label">余额</label>
            <div class="layui-input-inline">
                <div class="layui-form-mid">${edit.balance}元</div>
                <a href="#" class="layui-form-mid" style="color: blue;" onclick="addBalance()"  lay-filter="itemConsumeAmount">充值</a>
                <a href="#" class="layui-form-mid" style="color: blue;" onclick="balancerecordHistory()" lay-filter="itemConsumeAmount">记录</a>
            </div>
     </div>
     
  
    
    <div class="layui-form-item">
       <label class="layui-form-label"></label>
 	   <div class="layui-input-inline">
    	<button class="layui-btn" lay-submit="" lay-filter="item2">更新</button>
    	</div>
    </div>
  </div>
</fieldset>
</form>


<fieldset class="layui-elem-field site-demo-button" style="margin-top: 30px;">
<legend>推荐人信息</legend>
	<div>
		<c:choose> 
		  <c:when test="${ edit.referer == null || edit.referer == ''}">
		  	<button class="layui-btn  layui-btn-primary" onclick="addReferer()">添加推荐人</button>
		  </c:when>
		  <c:otherwise>
			我的推荐人是：${ edit.referer}
		  </c:otherwise>
		</c:choose>
   		
   	</div>
   	
   	<div>
   	
   	
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>推荐记录：</legend>
</fieldset>
   	<table class="layui-table" lay-even="" style="">
		<colgroup>
			<col width="50">
				<col>
				<col>
				<col>
				<col>
			<col width="200">
			<col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>我推荐的人</th>
				<th>原本截止日期</th>
				<th>延期截止日期</th>
				<th>增加积分</th>
				<th>创建时间</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty recommendList}">
				<c:forEach items="${recommendList}" var="a">
					<tr>
						<td>${a.id}</td>
						<td>${a.recommended}</td>
						<td>${a.oldVipDeadLine}</td>
						<td>${a.newVipDeadLine}</td>
						<td>${a.jifen}</td>
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.createUser }</td> 
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
   </div>
</fieldset>




<pagejs>
<script>
var addUrl = "<%=basePath%>bdzb/tenant/save";
var updateUrl ="<%=basePath%>bdzb/tenant/update";

<c:if test="${not empty edit}">
loadJsonDataToForm('${el:toJsonString(edit)}','item')
loadJsonDataToForm('${el:toJsonString(edit)}','item2')
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

form.on('submit(item2)', function(data){
	save2();
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

//保存
function save2(){
	url = updateUrl;
	if($('#item2 input[name=id]').val() == ""){
		url = addUrl;
	}
	$.post(url,$('#item2').serialize(),function(data){
		parent.document.location.reload(true);
	});
}

function addConsume(){
	
	//示范一个公告层
    layer.open({
      type: 1
      ,title: false //不显示标题栏
      ,closeBtn: true
      ,area: '500px;'
      ,shade: 0.8
      ,shadeClose: false
      ,id: 'labeldiv110' //设定一个id，防止重复弹出
      ,btnAlign: 'c'
      ,moveType: 0 //拖拽模式，0或者1
      ,btn: ['确定']
      ,content:  $('#addConsumeDiv')
      ,success: function(layero){
    	  
      }
      ,yes: function(){
    	  var url = "<%=basePath%>bdzb/balancerecord/save"
    	  var data = {
    			  tenantId:${edit.id},
    			  method:"线下消费"
    	  }
    	  $.extend(data,$('#addConsumeDivForm').serializeObject())
    	  data.amount = - data.amount;
    	  $.post(url,data,function(data){
    		  if(data.msg == msgOK){
    			  layer.closeAll();
                  layer.msg("增加成功");
                  document.location.reload(true);
    		  }else{
    			  layer.msg(data.msg);
    		  }
    		  
    	  });
      }
    });
	
}

function addBalance(){
    
    //示范一个公告层
    layer.open({
      type: 1
      ,title: false //不显示标题栏
      ,closeBtn: true
      ,area: '500px;'
      ,shade: 0.8
      ,shadeClose: false
      ,id: 'addBalance110' //设定一个id，防止重复弹出
      ,btnAlign: 'c'
      ,moveType: 0 //拖拽模式，0或者1
      ,btn: ['确定']
      ,content:  $('#addBalanceDiv')
      ,success: function(layero){
          
      }
      ,yes: function(){
          var url = "<%=basePath%>bdzb/balancerecord/save"
          var data = {
                  tenantId:${edit.id},
                  method:"充值"
          }
          $.extend(data,$('#addBalanceDivForm').serializeObject())
          $.post(url,data,function(data){
              if(data.msg == msgOK){
                  layer.closeAll();
                  layer.msg("增加成功");
                  document.location.reload(true);
              }else{
                  layer.msg(data.msg);
              }
              
          });
      }
    });
    
}



function consumeJifen(){
	
	//示范一个公告层
    layer.open({
      type: 1
      ,area: '500px;'
      ,shade: 0.8
      ,id: 'consumeJifen' //设定一个id，防止重复弹出
      ,btn: ['确定']
      ,content:  $('#consumeJifenDiv')
      ,success: function(layero){
    	  
      }
      ,yes: function(){
    	  var url = "<%=basePath%>bdzb/jifen/save"
    	  var data = {
    		tenantId:${edit.id}
    	  }
    	  $.extend(data,$('#consumeJifenForm').serializeObject())
    	  $.post(url,data,function(data){
    		  if(data.msg == msgOK){
                  layer.closeAll();
                  layer.msg("增加成功");
                  document.location.reload(true);
              }else{
                  layer.msg(data.msg);
              }
    	  });
      }
    });
	
}

function addReferer(){
	
	//示范一个公告层
    layer.open({
      type: 1
      ,title: false //不显示标题栏
      ,closeBtn: true
      ,area: '500px;'
      ,shade: 0.8
      ,shadeClose: false
      ,id: 'addReferer100' //设定一个id，防止重复弹出
      ,btnAlign: 'c'
      ,moveType: 0 //拖拽模式，0或者1
      ,btn: ['下一步']
      ,content:  $('#addRefererDiv')
      ,success: function(layero){
    	  
      }
      ,yes: function(){
    	  
    	  layer.open({
    	        type: 2
    	        ,id: 'addReferer2'
    	        ,content: "<%=basePath%>bdzb/tenant/addreferrer/"+ $("#addRefererForm input[name='referrer']")[0].value +"/"+${edit.phone}
    	        ,area: ['500px','500px']
    	    });
    	  
    	  
    	  
    	  
    	  
    	  <%-- var url = "<%=basePath%>bdzb/recommendrecord/save"
    	  var data = {
    			  recommended:${edit.phone}
    	  }
    	  $.extend(data,$('#addRefererForm').serializeObject())
    	  $.post(url,data,function(data){
    		    if(data.msg == "OK"){
    		    	parent.document.location.reload(true);
    		    }else{
    		    	alert(data.msg)
    		    }
    	  }); --%>
      }
    });
	
}
function jifenHistory(){
	parent.layer.open({
        type: 2
        ,id: 'jifenHistory'
        ,content: "<%=basePath%>bdzb/jifen/list2?tenantId="+${edit.id}
        ,area: ['1000px','500px']
    });
}

function balancerecordHistory(){
    parent.layer.open({
        type: 2
        ,id: 'balancerecordHistory'
        ,content: "<%=basePath%>bdzb/balancerecord/list2?tenantId="+${edit.id}
        ,area: ['1000px','500px']
    });
}

</script>

<div id="addConsumeDiv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>线下消费</legend>
</fieldset>
    <form id="addConsumeDivForm" class="layui-form" action="">
        <div class="layui-form-item">
             <label class="layui-form-label">消费金额</label>
             <div class="layui-input-inline">
                <input type="text" name="amount" lay-verify="label" lay-verify="required" autocomplete="off" class="layui-input">
             </div>
        </div>
    </form>
</div>

<div id="addBalanceDiv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>充值</legend>
</fieldset>
    <form id="addBalanceDivForm" class="layui-form" action="">
        <div class="layui-form-item">
             <label class="layui-form-label">充值金额</label>
             <div class="layui-input-inline">
                <input type="text" name="amount" lay-verify="label" lay-verify="required" autocomplete="off" class="layui-input">
             </div>
        </div>
    </form>
</div>


<div id="consumeJifenDiv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>积分消费</legend>
</fieldset>
    <form id="consumeJifenForm" class="layui-form" action="">
        <div class="layui-form-item">
             <label class="layui-form-label">消费类别</label>
             <div class="layui-input-inline">
                <input type="text" name="method" lay-verify="label" lay-verify="required" autocomplete="off" class="layui-input">
             </div>
        </div>
        <div class="layui-form-item">
             <label class="layui-form-label">消费积分</label>
             <div class="layui-input-inline">
                <input type="text" name="jifen" lay-verify="label" lay-verify="required" autocomplete="off" class="layui-input">
             </div>
        </div>
    </form>
</div>
    

<div id="addRefererDiv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>添加推荐人</legend>
</fieldset>
    <form id="addRefererForm" class="layui-form" action="">
        <div class="layui-form-item">
             <label class="layui-form-label">手机号码</label>
             <div class="layui-input-inline">
                <input type="text" name="referrer" lay-verify="required" autocomplete="off" class="layui-input">
             </div>
        </div>
    </form>
</div>

<div id="addRefererDiv2" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>添加推荐人</legend>
</fieldset>
    <form id="addRefererForm" class="layui-form" action="">
        <div class="layui-form-item">
             <label class="layui-form-label">手机号码</label>
             <div class="layui-input-inline">
                <input type="text" name="referrer" lay-verify="required" autocomplete="off" class="layui-input">
             </div>
        </div>
    </form>
</div>

</pagejs>
</body>
</html>