<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("orderStatus", BDZBConstants.orderStatus);
	pageContext.setAttribute("orderStatus", BDZBConstants.orderStatus);
	
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>bdzb</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<style type="text/css">

.layui-form-item .layui-input-inline {
    width: 150px;
}
.layui-form-label {
    width: 60px;
}

</style>

</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">



<form class="layui-form "  id="query" action="">
  <input type="hidden" name="currentPage" value="1" id="currentPage" />

  <div class="layui-form-item" >
  	<div class="layui-inline">
	    <label class="layui-form-label">订单编号</label>
	    <div class="layui-input-inline">
	      <input type="text" name="id" autocomplete="off" class="layui-input">
	    </div>
    </div>
  
  	<div class="layui-inline">
	    <label class="layui-form-label">手机号码</label>
	    <div class="layui-input-inline">
	      <input type="text" name="phone" autocomplete="off" class="layui-input">
	      
	    </div>
    </div>
    
    
    <div class="layui-inline">
	    <label class="layui-form-label">状态</label>
	    <div class="layui-input-inline">
	      <select name="status" id="status">
	      
	       <c:forEach items="${orderStatus}" var="m">
	       		<c:choose> 
				  <c:when test="${ query.status == m.key }">
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
	    <label class="layui-form-label">开始时间</label>
	    <div class="layui-input-inline">
	     <input type="text" name="startTime" onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"  autocomplete="on"  class="layui-input">
	    </div>
    </div>
    
    <div class="layui-inline">
	    <label class="layui-form-label">结束时间</label>
	    <div class="layui-input-inline">
	      <input type="text" name="endTime"  onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"  autocomplete="on"  class="layui-input">
	    </div>
    </div>
    
    <div class="layui-inline">
        <div class="layui-input-inline">
        <c:choose>
            <c:when test="${query != null && query.locked == 1}">
                <input type="checkbox" name="locked" value="1" checked="checked" lay-skin="primary" title="预约处理中">
            </c:when>
            <c:otherwise>
                <input type="checkbox" name="locked" value="1" lay-skin="primary" title="预约处理中">
            </c:otherwise>
        </c:choose>
        </div>
    </div>
    
    <div class="layui-inline">
	    <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" lay-filter="query">查询</button>
	    </div>
    </div>
    <div class="layui-inline" style="float: right">
	    <div class="layui-input-inline" style="text-align:right;width: 500px;">
  			<div class="layui-btn-group">
				<button class="layui-btn layui-btn-mini layui-btn-normal" onclick="window.open('<%=basePath%>Agreement.docx')">百德珠宝租戴协议</button>
				<button class="layui-btn layui-btn-mini layui-btn-normal" onclick="window.open('<%=basePath%>Order.xlsx')">百度珠宝租戴货品清单</button>
			</div>
	    </div>
    </div>
  </div>
</form>

	<table class="layui-table" lay-even="" style="">
		<colgroup>
			<col width="50">
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
				<col>
			<col width="200">
			<col width="100">
			<col width="150">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>租赁人</th>
				<th>联系人</th>
				<th>押金</th>
				<th>超出租金</th>
				<th>逾期租金</th>
				<th>到店办理时间</th>
				<th>开始时间/结束时间
				</th>
				
				<th>归还时间</th>
				<th>状态</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td><a href="javascript:;" style="color: red" onclick="detail(${a.id })">${a.id }</a></td>
						<td>${a.phone}</td>
						<td>${a.tName}<br>
						    ${a.tPhone}
						</td>
						<td>${a.deposit}
						  <c:choose> 
                          <c:when test="${a.depositMethod == 1}">   
					                                (现金)
					                          </c:when> 
					                          <c:when test="${a.status == 2}"> 
					                                (余额)
					                          </c:when> 
					      
					                        </c:choose>
						</td>
						<td>${a.excessAmount}</td>
						<td>${a.overdueAmount}</td>
						<td><fmt:formatDate value="${a.appointmentTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						<fmt:formatDate value="${a.startTime}"  pattern="yyyy-MM-dd HH:mm:ss"/><br>
						<fmt:formatDate value="${a.endTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td><fmt:formatDate value="${a.returnTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						
						<c:choose> 
						  <c:when test="${a.status == 0}">   
						    	预约中 <c:if test="${a.locked == 1}"><span style="color:red">受理中</span></c:if>
						  </c:when> 
						  <c:when test="${a.status == 1}"> 
						    	租赁中
						  </c:when> 
						  <c:when test="${a.status == 2}"> 
						       	已归还    
						  </c:when>
						  <c:when test="${a.status == 3}"> 
						    	已取消  
						  </c:when>
						  <c:when test="${a.status == 4}"> 
						    	已删除
						  </c:when>
						</c:choose>
						
						</td>
					<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${a.lastUpdateUser }</td>
						<td>
							<div class="layui-btn-group">
							
							<c:choose> 
							  <c:when test="${a.status == 0}">
							  		<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">编辑</button>
							    	<button class="layui-btn layui-btn-small" onclick="cancel('${a.id}');">取消</button>
							    	<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">租赁</button>
							  </c:when> 
							  <c:when test="${a.status == 1}"> 
							    	<button class="layui-btn layui-btn-small" onclick="back('${a.id}');">归还</button>
							    	<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">编辑</button>
							  </c:when> 
							  <c:when test="${a.status == 3}"> 
							          
							       <button class="do-action layui-btn layui-btn-small layui-btn-danger" 
                                            data-type="ajaxDelete" data-url="<%=basePath%>bdzb/order/delete/${a.id}">删除</button>   
							  </c:when>
							</c:choose> 
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
	
	<div id="page" style="float: left;"></div>

</div>
			
	
	</div>
</div>	

<pagejs>


<script>
loadJsonDataToForm('${el:toJsonString(query)}','query')


//全选
form.on('checkbox(allChoose)', function(data){
    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
    child.each(function(index, item){
        item.checked = data.elem.checked;
    });
    form.render('checkbox');
});



laypage.render({
    elem: 'page'
    ,count: ${page.totalSize}
    ,limit:20
	,last:${page.totalPage}
	,curr:${page.currentPage}
    ,layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip']
    ,jump:function(obj,first){
    	if(first != true){
    		debugger;
    		$('#currentPage').val(obj.curr);
	    	$('#query').submit();
    	}
    }
  });

  
 function del(id){
 	var url = "<%=basePath%>bdzb/order/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe onload="setIframeHeight(this)" id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/order/bedit/'+id+'" style="width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
  
  function detail(id){
		 
		 subFilter = "sub_tab";
		 subId = "detail_tab" ;
		 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
		 mainHeight = document.body.scrollHeight  - 200;
	     var iframe = '<iframe onload="setIframeHeight(this)"  id ="iframe_detail_'+id+'" src="<%=basePath%>bdzb/order/detail/'+id+'" style="width:100%"></iframe>';
	     element.tabAdd(subFilter, {
	         title	: "订单详情",
	         content	: iframe,
	         id		: subId //tab 的 id
	     });
	     element.tabChange(subFilter, subId);
	 }
	  
	  
	  function cancel(id){ 
		  layer.confirm('确定取消订单了吗？', function(index){
			  update(id,3)  
          });
		  
	  }

	  function rent(id){ 
		  update(id,1) 	
	  }
	  
	  function back(id){
		  $('#overdueAmount').val("");
		  
		  layer.confirm('确定归还并退换押金了吗？', function(index){
			  //do something
			  back2(id)
		  });  
		  
		  
	  }

	  function back2(id){
		  var overdueAmount = null;
		  if($('#overdueAmount').val() != null &&　$('#overdueAmount').val() != ""){
			  overdueAmount = $('#overdueAmount').val();
		  }
		  $.post('<%=basePath%>bdzb/order/update',{id:id,status:2,overdueAmount:overdueAmount},function(data){
			    if(data.msg == msgOK){
					parent.document.location.reload(true);
			    }else if (data.code == 1002){
					$('#overdueAmount').val(data.msg)
					//示范一个公告层
				    layer.open({
				      type: 1
				      ,title: false //不显示标题栏
				      ,closeBtn: true
				      ,area: '500px;'
				      ,shade: 0.8
				      ,shadeClose: false
				      ,id: 'overdueAmount110' //设定一个id，防止重复弹出
				      ,btnAlign: 'c'
				      ,moveType: 0 //拖拽模式，0或者1
				      ,btn: ['确定']
				      ,content:  $('#overdueAmountdiv')
				      ,success: function(layero){
				      }
				      ,yes: function(){
				    	  back2(id);
				      }
				    });
				}else{
					layer.alert(data.msg);
				}
			}); 
	  }
		  
	  
	  function update(id,status){
		  $.post('<%=basePath%>bdzb/order/update',{id:id,status:status},function(data){
			    if(data.msg == msgOK){
					parent.document.location.reload(true);
			    }else{
			    	 layer.alert(data.msg);
			    }
			}); 
	  }
	  
	  

  
 
</script>
<div id="overdueAmountdiv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>支付逾期租金</legend>
</fieldset>
        <div class="layui-form-item">
                <label class="layui-form-label">逾期租金</label>
                 <div class="layui-input-inline">
                <input type="text" id="overdueAmount" name="overdueAmount" lay-verify="label" autocomplete="off" class="layui-input">
                </div>
        </div>
</div>
</pagejs>

</body>
</html>