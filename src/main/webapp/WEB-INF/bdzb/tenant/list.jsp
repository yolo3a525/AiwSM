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
	all.put("0","非会员");
	all.putAll(DDData.dd.get("xiaofeika"));
	all.putAll(DDData.dd.get("huijika"));
	pageContext.setAttribute("allCard", all);
	
	
%>

<!DOCTYPE html>
<html>
<head>
<title>客户管理</title>
<style type="text/css">
  img {
  	border-radius: 50px;
    height: 50px
  }
</style>
</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<!-- <li >添加</li>  -->
			
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">



<form class="layui-form "  id="query" action="">
  <div class="layui-form-item">
  	<div class="layui-inline">
	    <label class="layui-form-label">手机号码</label>
	    <div class="layui-input-inline">
	      <input type="text" name="phone" autocomplete="off" class="layui-input">
	      <input type="hidden" name="currentPage" value="1" id="currentPage" />
	    </div>
    </div>
    
    <div class="layui-inline">
	    <label class="layui-form-label">真实姓名</label>
	    <div class="layui-input-inline">
	      <input type="text" name="nickName" autocomplete="off" class="layui-input">
	    </div>
    </div>
    
    
    
     <div class="layui-inline">
	    <label class="layui-form-label">类别</label>
	    <div class="layui-input-inline">
	      <select name="vip" id="vip">
	      	   <option value="99" selected>全部</option>
		       <c:forEach items="${allCard}" var="m">
		       		<c:choose> 
					  <c:when test="${ query.vip == m.key }">
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
	    <label class="layui-form-label">会员到期</label>
	    <div class="layui-input-inline">
	      <select name="tenantDeadLine" id="tenantDeadLine">
	      	   <option value="99" selected>不限制</option>
		       <c:forEach items="${tenantDeadLine}" var="mm">
		       		<c:choose> 
					  <c:when test="${ query.tenantDeadLine == mm.key }">
					  	<option value='${mm.key}'selected > ${mm.value}</option>
					  </c:when>
					  	<c:otherwise>
						<option value='${mm.key}'> ${mm.value}</option>
					  </c:otherwise>
					</c:choose>
				</c:forEach>
	      	  </select>
	    </div>
    </div>
   </div>
   <div class="layui-form-item">
    <div class="layui-inline">
	    <label class="layui-form-label">注册时间(从)</label>
	    <div class="layui-input-inline">
	     <input type="text" name="startRegisterDate" onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD'})"  autocomplete="on"  class="layui-input">
	    </div>
    </div>
    
    <div class="layui-inline">
	    <label class="layui-form-label">注册时间(到)</label>
	    <div class="layui-input-inline">
	      <input type="text" name="endRegisterDate"  onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD'})"  autocomplete="on"  class="layui-input">
	    </div>
    </div>
    
    
    <div class="layui-inline">
        <label class="layui-form-label">借戴次数(eg:>10)</label>
        <div class="layui-input-inline">
          <input type="text" name="adornTimesQuery" autocomplete="off" class="layui-input">
        </div>
    </div>
    
    
    <div class="layui-inline">
        <label class="layui-form-label">借戴金额(eg:>10)</label>
        <div class="layui-input-inline">
          <input type="text" name="adornTotalMoneyQuery" autocomplete="off" class="layui-input">
        </div>
    </div>
   </div>
   <div class="layui-form-item">
	    <div class="layui-inline">
		    <div class="layui-input-inline">
		      <label class="layui-form-label"></label>
		      <button class="layui-btn" lay-submit="" lay-filter="query">查询</button>
		    </div>
	    
        <div class="layui-input-inline">
          
          <button class="layui-btn  layui-btn-normal" type="button" lay-submit="" onclick="export2()">手机号码导出</button>
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
			<col width="200">
			<col width="200">
			<col width="100">
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>id</th>
				<th>手机号码</th>
				<th>昵称</th>
				<th>卡类型</th>
				<th>到期时间</th>
				<th>借戴次数</th>
				<th>借戴总金额</th>
				<th>积分</th>
				<th>注册时间</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td> <a href="#" style="color: #1E9FFF;" onclick="edit('${a.id}')">${a.id } </a> </td>
					<td>${a.phone}</td>
					<td>${a.nickName}</td>
					<td>${allCard[fn:trim(a.vip)]}</td>
					<td>
						<c:if test="${a.vip != 0}">
						${a.vipDeadLine}
						  <c:if test="${a.tenantDeadLineDays < 0}">
						       已过期
						  </c:if>
						  <c:if test="${a.tenantDeadLineDays >= 0}">
                                                                                        剩余(${a.tenantDeadLineDays}天)
                          </c:if>
						
						</c:if>
					</td>
					<td>
						${a.adornTimes}
					</td>
						<td style="font-size: 9ptd;">${a.adornTotalMoney}</td>
						<td>${a.jifen}</td>
					<td><fmt:formatDate value="${a.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${a.lastUpdateUser }</td>
						<td>
							<div class="layui-btn-group">
							<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">编辑</button>
							<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>bdzb/tenant/delete/${a.id}">删除</button>	
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
		
		
	<div id="page" style="float: left;"></div>

</div>
			<!--  后台不允许添加客户.
		<div class="layui-tab-item">
			<iframe onload="setIframeHeight(this)" src="<%=basePath%>bdzb/tenant" id= "add_iframe" height=600 scrolling="no" frameborder="0"></iframe>
		</div>
		 -->
	</div>
</div>	

<pagejs>
<script>
loadJsonDataToForm('${el:toJsonString(query)}','query')
$.download = function(url, data, method){ // 获得url和data
    if( url && data ){ 
        // data 是 string 或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data); // 把参数组装成 form的 input
        var inputs = '';
        $.each(data.split('&'), function(){ 
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
        }); // request发送请求
        $('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
    };
};


function export2(){
	$.download('<%=basePath%>bdzb/tenant/export',$('#query').serialize(),'post')
}




//自定义验证规则
form.verify({
    username: function(value){
        if(value.length < 3){
            return '至少3个字符';
        }
    }
    ,dayuxiaoyushuzi: [/([><=]{1}[0-9]{1,8}) | ([><]{1}=[0-9]{1,8})$/, '输入规则不正确']
});


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
 	var url = "<%=basePath%>bdzb/tenant/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 100;
     var iframe = '<iframe onload="setIframeHeight(this)" id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/tenant/bedit/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
 
</script>
</pagejs>
</body>
</html>