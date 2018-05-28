<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.aiw.util.DDData" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String r = new Date().getTime()+ "";
	request.setAttribute("jewelryStatus", BDZBConstants.jewelryStatus);
	request.setAttribute("commonStatus", BDZBConstants.commonStatus);
	request.setAttribute("commonStatus2", BDZBConstants.commonStatus2);
	request.setAttribute("onlineTime", BDZBConstants.onlineTime);
	pageContext.setAttribute("jewelryStatus", BDZBConstants.jewelryStatus);
	pageContext.setAttribute("pinlei", DDData.ddLabel.get("pinlei"));
	request.setAttribute("pinlei",DDData.ddLabel.get("pinlei"));
	
%>

<!DOCTYPE html>
<html>
<head>
<title>珠宝列表</title>
<style type="text/css">
.layui-upload-icon {
    height: 30px;
    line-height: 30px;
    padding: 0 0px;
    margin: 0 5px;
    font-size: 12px;
}
.layui-upload-icon i {
    margin-right: 5px;
    vertical-align: top;
    font-size: 20px;
    color: #5FB878;
}
.layui-upload-button {
    position: relative;
    display: inline-block;
    vertical-align: middle;
    min-width: 30px;
    height: 30px;
    line-height: 30px;
    border: 1px solid #DFDFDF;
    border-radius: 2px;
    overflow: hidden;
    background-color: #fff;
    color: #666;
}
</style>
</head>
<body>
<div class="layui-tab layui-tab-brief sub-page-tab" lay-filter="sub_tab">
		<ul class="layui-tab-title">
			<li class="layui-this">列表</li>
			<li onclick="addIframe()">添加</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">



<form class="layui-form "  id="query" action="">
  <div class="layui-form-item">
  	<div class="layui-inline">
	    <label class="layui-form-label">珠宝名字</label>
	    <div class="layui-input-inline">
	      <input type="text" name="name" autocomplete="off" class="layui-input">
	      <input type="hidden" name="currentPage" value="1" id="currentPage" />
	    </div>
    </div>
    <div class="layui-inline">
	    <label class="layui-form-label">珠宝编号</label>
	    <div class="layui-input-inline">
	      <input type="text" name="sid" autocomplete="off" class="layui-input">
	    </div>
    </div>
    
    <div class="layui-inline">
        <label class="layui-form-label">供应商名字</label>
        <div class="layui-input-inline">
          <input type="text" name="username" autocomplete="off" class="layui-input">
        </div>
    </div>
    
    <div class="layui-form-item">
    
      <div class="layui-inline">
        <label class="layui-form-label">是否新品</label>
        <div class="layui-input-inline">
          <select name="hotflag" id="hotflag">
          <option value='99' >全部</option>
           <c:forEach items="${commonStatus2}" var="m">
                <c:choose> 
                  <c:when test="${ query.hotflag == m.key }">
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
        <label class="layui-form-label">上架状态</label>
        <div class="layui-input-inline">
          <select name="showflag" id="showflag">
          <option value='99' >全部</option>
           <c:forEach items="${commonStatus2}" var="m">
                <c:choose> 
                  <c:when test="${ query.showflag == m.key }">
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
	    <label class="layui-form-label">借戴状态</label>
	    <div class="layui-input-inline">
	      <select name="orderStatus" id="orderStatus">
	      <option value='99' >全部</option>
	       <c:forEach items="${jewelryStatus}" var="m">
	       		<c:choose> 
				  <c:when test="${ query.orderStatus == m.key }">
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
        <label class="layui-form-label">图片</label>
        <div class="layui-input-inline">
           <select name="imageflag" id="imageflag">
          <option value='99' >全部</option>
           <c:forEach items="${commonStatus}" var="m">
                <c:choose> 
                  <c:when test="${ query.imageflag == m.key }">
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
        <label class="layui-form-label">视频</label>
        <div class="layui-input-inline">
           <select name="movieflag" id="movieflag">
          <option value='99' >全部</option>
           <c:forEach items="${commonStatus}" var="m">
                <c:choose> 
                  <c:when test="${ query.movieflag == m.key }">
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
    </div>

    
      <div class="layui-form-item">
	      <div class="layui-inline">
	            <label class="layui-form-label">类型</label>
	            <div class="layui-input-inline">
	              <select style="float: left" name="label_pinlei" id="label_pinlei">
	              <option value='99' >全部</option>
	               <c:forEach items="${pinlei}" var="m">
	                        <option value='${m.key}' <c:if test="${query.label_pinlei == m.key}"> selected </c:if> > ${m.value}</option>
	                </c:forEach>
	              </select>
	            </div>
	     </div>
        <div class="layui-inline">
            <label class="layui-form-label">场景</label>
            
            <div class="layui-input-inline">
              <select style="float: left" name="label_changjing" id="label_changjing">
              <option value='99' >全部</option>
               <c:forEach items="${changjing}" var="m">
                        <option value='${m.key}'  <c:if test="${query.label_changjing == m.key}"> selected </c:if>> ${m.value}</option>
                </c:forEach>
              </select>
              </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">上新时间</label>
            <div class="layui-input-inline">
              <select name="onlineTime" id="onlineTime">
                   <option value="99" selected>不限制</option>
                   <c:forEach items="${onlineTime}" var="mm">
                        <c:choose> 
                          <c:when test="${ query.onlineTime == mm.key }">
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
            <label class="layui-form-label">其他标签</label>
            <div class="layui-input-inline">
              <select style="float: left" name="label_fengge" id="label_fengge">
              <option value='99' >全部</option>
               <c:forEach items="${fengge}" var="m">
                        <option value='${m.key}' <c:if test="${query.label_fengge == m.key}"> selected </c:if>> ${m.value}</option>
                </c:forEach>
              </select>
            </div>
            <div class="layui-input-inline">
              <select style="float: left" name="label_faxing" id="label_faxing">
              <option value='99' >全部</option>
               <c:forEach items="${faxing}" var="m">
                        <option value='${m.key}' <c:if test="${query.label_faxing == m.key}"> selected </c:if>> ${m.value}</option>
                </c:forEach>
              </select>
            </div>
            <div class="layui-input-inline">
              <select style="float: left" name="label_fuse" id="label_fuse">
              <option value='99' >全部</option>
               <c:forEach items="${fuse}" var="m">
                        <option value='${m.key}' <c:if test="${query.label_fuse == m.key}"> selected </c:if>> ${m.value}</option>
                </c:forEach>
              </select>
            </div>
            <div class="layui-input-inline">
              <select style="float: left" name="label_lianxing" id="label_lianxing">
              <option value='99' >全部</option>
               <c:forEach items="${lianxing}" var="m">
                        <option value='${m.key}' <c:if test="${query.label_lianxing == m.key}"> selected </c:if>> ${m.value}</option>
                </c:forEach>
              </select>
            </div>
    </div>
    
  

    <div class="layui-inline">
        <label class="layui-form-label"></label>
	    <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" lay-filter="query">查询</button>
	    </div>
    </div>
    
    <div class="layui-inline">
	      
	      <button type="button" class="layui-btn" id="import_jewelry"><i class="layui-icon"></i>导入珠宝基本数据</button>
	      
    </div>
    <button class="layui-btn layui-btn-small layui-btn-normal" onclick="window.open('<%=basePath%>TemplateV2.xlsx')">导入模板下载</button>
    <button class="layui-btn layui-btn-small layui-btn-normal" type="button" lay-submit="" onclick="batch(1)">选中上架</button>
    <button class="layui-btn layui-btn-small layui-btn-normal" type="button" lay-submit="" onclick="batch(0)">选中下架</button>
  </div>
</form>

<div style="color: red;">
	注意事项:使用模板珠宝基本数据导入,严格按照模板的数据格式(不修改表格的单元格属性,不增加列,多余的空行最好删除,采用统一字体,避免导入失败.).导入后编辑上传视频与图片.<br>
	图片:只支持jpg格式,分辨率680*420<br>
	视频:mp4格式并且h264编码
</div>

<div class="layui-form">
	<table class="layui-table" lay-even="" style="min-width:780px; ">
		<!-- 
		<colgroup>
			<col width="50">
			<col width="100">
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
			<col width="200">
		</colgroup>
		 -->
		<thead>
			<tr>
				<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
				<th>珠宝编号</th>
				<th>名称</th>
				<th>类型</th>
				<th>图片</th>
				<th>视频</th>
				<th>价格</th>
				<th>状态</th>
				<th>供应商</th>
				<th>借戴次数</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th></th>
			</tr>
		</thead>
		<tbody id ="tbody">
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="a">
					<tr>
					<td>
					<input type="checkbox" name="jewelryids" value="${a.id}" lay-skin="primary">
					</td>
					<td>
					${a.sid }
					</td>
						<td>
						  <c:if test="${a.hotflag == 1}">
						  	<img alt="" width="30px" src="<%=basePath%>images/news.png">
						  </c:if>
                          <c:if test="${a.showflag == 0}">
                            <img alt="" width="30px" src="<%=basePath%>images/xiajia.png">
                          </c:if>
						${a.name}
						</td>
						<td>
						${pinlei[fn:trim(a.label_pinlei)]}
						</td>
						<td>
							<c:choose> 
							  <c:when test="${not empty a.imageflag && a.imageflag == 1}">
							  <a href="javscript:;" onclick="bigImage('${a.sid}')">
							  	<img alt="" src="<%=basePath%>upload/image/${a.sid}.jpg?r=<%=r%>" width="30">
							  </a>
							  </c:when>
							  	<c:otherwise>
							    没有图片
							  </c:otherwise>
							</c:choose>
						</td>
						<td>
						
							<c:choose> 
							  <c:when test="${not empty a.movieflag && a.movieflag == 1}">
							  	<button class="layui-btn layui-btn-small" onclick="play('${a.sid}');">播放</button>
							  </c:when>
							  	<c:otherwise>
							    没有视频
							  </c:otherwise>
							</c:choose>
						</td>
						<td>${a.price}</td>
						<td>
							${jewelryStatus[a.orderStatus]}
							<c:if test="${a.orderStatus == 0 || a.orderStatus == 1}">
								<a href="<%=basePath%>bdzb/order/list?id=${a.orderid}" target="_blank" style="color: #FF5722">订单</a>
							</c:if>

						</td>
					<td>${a.username }</td>
					<td>${a.times }</td>
					<td><fmt:formatDate value="${a.lastUpdateTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${a.lastUpdateUser }</td>
					
						<td>
							<div class="layui-btn-group">
							
							<c:if test="${sessionScope.gys == null}">
							
							<c:if test="${a.orderStatus == -1}">
								<button class="layui-btn layui-btn-small" onclick="addRentCart('${a.id}');">加入借戴</button>
							</c:if>  
							</c:if>
							
							<button class="layui-btn layui-btn-small" onclick="edit('${a.id}');">编辑</button>
							<button class="do-action layui-btn layui-btn-small layui-btn-danger" 
											data-type="ajaxDelete" data-url="<%=basePath%>bdzb/jewelry/delete/${a.id}">删除</button>	
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>


	</table>
</div>
	
	<div id="page" style="float: left;"></div>

</div>
			
		<div class="layui-tab-item">
			<iframe src="" id= "add_iframe" height=1000 scrolling="no" frameborder="0"></iframe>
		</div>
	</div>
</div>

</body>
	
<pagejs>

<script>
	
layui.upload.render({
    url: '<%=basePath%>bdzb/jewelry/import'
   ,elem :'#import_jewelry'
   ,exts: 'xlsx' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,field:'excel'
   ,title: '导入珠宝基本数据'
   ,done: function(res){ //上传成功后的回调
	   layer.alert(res.msg,function(){
		   location.reload();
	   }); 
   }
   ,before: function(res){ //上传成功后的回调
   	   layer.msg("正在导入,请等待完成......")
   }
 });

	
loadJsonDataToForm('${el:toJsonString(query)}','query')

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



 function batch(showflag){

    var ids="";
    $('input[type="checkbox"][name="jewelryids"]:checked').each(
                function() {
                       if(ids == ""){
                            ids = $(this).val(); 
                        }else{
                            ids = ids+","+$(this).val(); 
                        }
                }
            );
    if(ids == ""){
        layer.alert("请选择后再操作。");
        return;
    }
    //alert(ids);
    var url = "<%=basePath%>bdzb/jewelry/batchup";
    $.post(url,{jewelryids:ids,showflag:showflag},function(data){
          location.reload();
    });
 }
  
 function del(id){
 	var url = "<%=basePath%>bdzb/jewelry/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight ;
     var iframe = '<iframe onload="setIframeHeight(this)" id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/jewelry/bedit/'+id+'" style="width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
  
  
  function order(id){
		 subFilter = "sub_tab";
		 subId = "order_tab" ;
		 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
		 mainHeight = document.body.scrollHeight  - 200;
	     var iframe = '<iframe onload="setIframeHeight(this)" id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/order/list?id='+id+'" style="width:100%"></iframe>';
	     element.tabAdd(subFilter, {
	         title	: "订单",
	         content	: iframe,
	         id		: subId //tab 的 id
	     });
	     element.tabChange(subFilter, subId);
	 }
  
  
  function bigImage(sid){
	  parent.layer.open({
	        type: 1
	        ,title: false //不显示标题栏
	        ,area: ['680px']
	        ,closeBtn: false
	        ,shade: 0.8
	        ,id: 'bigImage' //设定一个id，防止重复弹出
	        ,btn: ['关闭']
	        ,moveType: 1 //拖拽模式，0或者1
	        ,content: '<div><img src="<%=basePath%>upload/image/'+sid+'.jpg?r=<%=r%>" width="680" ></img></div>'
	        ,success: function(layero){
	        }
	      });	 
  }
  
  function play(sid){ 
	  parent.layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,area: ['544px', '620px']
        ,closeBtn: false
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div><embed src="<%=basePath%>upload/movie/'+sid+'.mp4?r=<%=r%>" type="audio/mpeg" width="544" height="544" ></embed></div>'
        ,success: function(layero){
        }
      });	 	
 }
  
  function addRentCart(id){ 
	$.post('<%=basePath%>bdzb/rentcart/add/'+id,function(data){
		 layer.alert("成功加入出租车中...");
	}); 	
 }
  
 
  function hot(id){ 
	$.post('<%=basePath%>bdzb/jewelry/update',{id:id,hotflag:1},function(data){
		parent.document.location.reload(true);
	}); 	
  }
  function unhot(id){ 
	$.post('<%=basePath%>bdzb/jewelry/update',{id:id,hotflag:0},function(data){
		parent.document.location.reload(true);
	}); 	
  }
  
  function addIframe(){
	  $('#add_iframe').attr("src","<%=basePath%>bdzb/jewelry/badd");
  }
</script>

</pagejs>





</html>