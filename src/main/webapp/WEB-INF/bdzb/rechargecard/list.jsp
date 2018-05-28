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
<title>bdzb</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<li >添加</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">



<form class="layui-form "  id="query" action="">
  <div class="layui-form-item">
  	<div class="layui-inline">
	    <label class="layui-form-label">卡号</label>
	    <div class="layui-input-inline">
	      <input type="text" name="no" autocomplete="off" class="layui-input">
	      <input type="hidden" name="currentPage" value="1" id="currentPage" />
	    </div>
    </div>
    <div class="layui-inline">
	    <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" lay-filter="query">查询</button>
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
			<col width="200">
			<col width="100">
			<col width="200">
			<col width="100">
			<col width="150">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>卡号</th>
				<th>类别</th>
				<th>时间（天）</th>
				<th>过期时间</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td>${a.id }</td>
						<td>${a.no}</td>
						<td>${allCard[fn:trim(a.type)]}</td>
						<td>${a.days}</td>
						<td>${a.expires}</td>
					
					
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.lastUpdateUser }</td>
					<td><fmt:formatDate value="${a.lastUpdateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${a.createUser }</td> 
						<td>
							<div class="layui-btn-group">
							<c:if test="${a.status == 0 }">
								<button class="layui-btn layui-btn-small" onclick="use('${a.id}');">使用</button>
							
							<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>bdzb/rechargecard/delete/${a.id}">删除</button>
							</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
	<div id="page"></div>

</div>
			
		<div class="layui-tab-item">
			<iframe src="<%=basePath%>bdzb/rechargecard/badd" id= "add_iframe" height=600 scrolling="no" frameborder="0"></iframe>
		</div>
	</div>
</div>	


<pagejs>

<script>
var $ = layui.jquery, form = layui.form
	,laypage = layui.laypage,layer = layui.layer,element = layui.element;
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
    cont: 'page'
    ,pages: ${page.totalPage}
    ,groups: 5 //连续显示分页数
    ,last:${page.totalPage}
    ,curr:${page.currentPage}
    ,jump:function(obj,first){
    	if(first != true){
    		debugger;
    		$('#currentPage').val(obj.curr);
	    	$('#query').submit();
    	}
    }
});

  
 function del(id){
 	var url = "<%=basePath%>bdzb/rechargecard/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 function use(id){
	//示范一个公告层
	    layer.open({
	      type: 1
	      ,title: false //不显示标题栏
	      ,closeBtn: true
	      ,area: '500px;'
	      ,shade: 0
	      ,shadeClose: false
	      ,id: 'usediv110' //设定一个id，防止重复弹出
	      ,btnAlign: 'c'
	      ,moveType: 0 //拖拽模式，0或者1
	      ,btn: ['确定']
	      ,content:  $('#usediv')
	      ,success: function(layero){
	      }
	      ,yes: function(){
	    	  var url = "<%=basePath%>bdzb/rechargecard/use"
	    	  $.post(url,{id:id,phone:$('#phone').val()},function(data){
	    			parent.document.location.reload(true);
	    		});
	      }
	    });
	 
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/rechargecard/bedit/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
 
</script>
</pagejs>

<div id="usediv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>使用充值卡</legend>
</fieldset>
       <div class="layui-form-item">
       		<label class="layui-form-label">会员手机号码</label>
       		 <div class="layui-input-inline">
               <input type="text" name="phone" id="phone" lay-verify="label" autocomplete="off" class="layui-input">
               </div>
       </div>
</div>
</body>
</html>