<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("jewelryStatus", BDZBConstants.jewelryStatus);
	pageContext.setAttribute("orderStatus", BDZBConstants.orderStatus);
	String r = new Date().getTime()+ "";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>百德珠宝</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="<%=basePath%>css/layui.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
<link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">

<style type="text/css">
	.order {
		font-size: 14px;
		padding: 5px;
	}

</style>


</head>
<body>
	<div  id="print">
	
	<div class="order">
	订单编号:${order.id}(${orderStatus[order.status]})
	</div>
	<div class="order">
	身份证号码:${tenant.idCard}
	</div>
	<div class="order">
	租赁人手机:${tenant.phone}
	</div>
	<div class="order">
            押金:${order.deposit}
            <c:choose> 
               <c:when test="${order.depositMethod == 1}">   
                                         (现金)
              </c:when> 
              <c:when test="${order.status == 2}"> 
                    (余额)
              </c:when> 

            </c:choose>
    </div>
	<div class="order">
	超出租金:${order.excessAmount}
	</div>
	<div class="order">
	逾期租金:${order.overdueAmount}
	</div>
	<div class="order">
	开始时间:<fmt:formatDate value="${order.startTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
	</div>
	<div class="order">
	截止时间:<fmt:formatDate value="${order.endTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
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

	<br>
	<br>
	
	<div ><div style="float:right;width: 200px">借戴人(签字):</div></di>
	</div>
	<br><br>
	<div style="clear: both;"><div  style="float:right;width: 200px">日期:</div> </div>
	<button class="layui-btn layui-btn-small" onclick="printdiv()">打印</button>


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
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/order/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
	  
	  
	  function cancel(id){ 
		  update(id,3) 	
	  }

	  function rent(id){ 
		  update(id,1) 	
	  }

	  function back(id){ 
		  update(id,2) 	
	  }
	  
	  
	  function removeDetail(id,jewelryid){ 
		  $.post('<%=basePath%>bdzb/order/detail/remove/'+  id + '/' + jewelryid ,{},function(data){
				document.location.reload(true);
		  }); 	
	  }
		  
	  
	  function update(id,status){
		  $.post('<%=basePath%>bdzb/order/update',{id:id,status:status},function(data){
				parent.document.location.reload(true);
			}); 
	  }
	  
	  
	  
  
	  function printdiv(printpage) 
	  { 
		  var headstr = "<html><body>";
		  var title = "<div style=\"font-size: 30px;text-align:center\">百德借戴清单</div>"
		  var footstr = "</body>"; 
		  var newstr = document.all.item('print').innerHTML;
		  var oldstr = document.body.innerHTML; 
		  document.body.innerHTML = headstr+title+newstr+footstr; 
		  window.print(); 
		  document.body.innerHTML = oldstr; 
		  return false; 
	  } 
</script>
</pagejs>


</body>
</html>