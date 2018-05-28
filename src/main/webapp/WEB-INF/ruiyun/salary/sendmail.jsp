<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>ruiyun</title>
</head>
<body>

<div style="padding:20px">	

 	 <div class="layui-form-item">
    		<label class="layui-form-label">发送者邮箱</label>
    		<div class="layui-input-inline">
		    	<input type="text" id="fromEmail" name="fromEmail"  lay-verify="required" autocomplete="on"  class="layui-input">
            </div>
     </div>
	 <div class="layui-form-item">
    		<label class="layui-form-label">密码</label>
    		<div class="layui-input-inline">
		    	<input type="password" id="fromEmailPassword" name="fromEmailPassword"  lay-verify="required" autocomplete="on"  class="layui-input">
            </div>
     </div>



	<input type="file" name="excel" lay-type="file" class="layui-upload-file">

</div>

<pagejs>
<script>
var $ = layui.jquery, form = layui.form
	,laypage = layui.laypage,layer = layui.layer,element = layui.element;


layui.upload.render({
    url: '<%=basePath%>salary/sendMail' //上传接口
   ,ext: 'xlsx' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,title: '发送本月薪酬邮件通知.'
   ,success: function(res){ //上传成功后的回调
   	 	layer.alert(res.msg);
   }
   ,before: function(res){ //上传成功后的回调
	   $(res).parent().attr("action","<%=basePath%>salary/sendMail?email=" + $('#fromEmail').val() + "&password=" + $('#fromEmailPassword').val());
   	   layer.msg("正在发送,请等待发送完毕....")
   }
 });
	
	
	
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
 	var url = "<%=basePath%>salary/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
 function sendMail(id){
 	var url = "<%=basePath%>salary/sendMail";
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>salary/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
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