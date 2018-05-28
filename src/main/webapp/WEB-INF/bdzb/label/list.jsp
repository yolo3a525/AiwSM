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
<meta charset="utf-8">
<title>bdzb</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<style type="text/css">

.divX{
    z-index:100;
    border-style:solid;
    border-width:1px;
    border-color:#FF0000;
    -moz-border-radius:20px;
    -webkit-border-radius:20px;
    background-color:#ffffff;
    line-height:10px;
    text-align:center;
    font-weight:bold;
    cursor:pointer;
    font-size:10px;
    color:red;
    display: none;
}

.btnbox{  
  z-index:999;  
  position:relative;  
  left:400px;  
  top:-350px;  
}  
</style>
<script type="text/javascript">

function showdelete(thisObj,name){
	$(thisObj).addClass("layui-btn-danger")
	$(thisObj).html("<i class='layui-icon'></i>" + name)
	
}
function hidedelete(thisObj,name){
	$(thisObj).removeClass("layui-btn-danger")
	$(thisObj).children("i").remove();
}




function addLabel(id){

	
	//示范一个公告层
    layer.open({
      type: 1
      ,title: false //不显示标题栏
      ,closeBtn: true
      ,area: '500px;'
      ,shade: 0
      ,shadeClose: false
      ,id: 'labeldiv110' //设定一个id，防止重复弹出
      ,btnAlign: 'c'
      ,moveType: 0 //拖拽模式，0或者1
      ,btn: ['确定']
      ,content:  $('#labeldiv')
      ,success: function(layero){
    	  document.getElementById("type").value = id;
      }
      ,yes: function(){
    	  var url = "<%=basePath%>bdzb/label/save"
    	  $.post(url,$('#item').serialize(),function(data){
    			parent.document.location.reload(true);
    		});
      }
    });
    
	
}


</script>

</head>
<body>



<div class="layui-main">

	<fieldset class="layui-elem-field site-demo-button" style="margin-top: 30px;">

	<legend>标签管理</legend>
  
  <c:forEach items="${map}" var="a">
			<div>
				<span style="padding: 12px;font-size:20px">
			    ${a.key.value}
			    </span>
			    <button class="layui-btn " onclick="addLabel('${a.key.key}')"><i class="layui-icon"></i></button>
			    
			     <c:forEach items="${a.value}" var="aa">
			     	<button class="do-action layui-btn layui-btn-normal" data-type="ajaxDelete"
											onmouseover="showdelete(this,'${aa.name}')" onmouseout="hidedelete(this,'${aa.name}')" 
											data-url="<%=basePath%>bdzb/label/delete/${aa.id}">${aa.name}</button>	
			     </c:forEach>
			 </div>
    </c:forEach>
  
  
</fieldset>

	

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



  
 function del(id){
 	var url = "<%=basePath%>bdzb/label/delete/"+id;
 	$.get(url,function(data){
 		  location.reload();
 	});
 }
 
  function edit(id){
	 
	 subFilter = "sub_tab";
	 subId = "edit_tab" ;
	 element.tabDelete(subFilter, subId); //删除 lay-id="xxx" 的这一项  
	 mainHeight = document.body.scrollHeight  - 200;
     var iframe = '<iframe id ="iframe_eidt_'+id+'" src="<%=basePath%>bdzb/label/bedit/'+id+'" style="height:' + mainHeight + 'px;width:100%"></iframe>';
     element.tabAdd(subFilter, {
         title	: "编辑",
         content	: iframe,
         id		: subId //tab 的 id
     });
     element.tabChange(subFilter, subId);
 	
 }
 
</script>
</pagejs>
<div id="labeldiv" style="display: none">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>新增标签</legend>
</fieldset>
	<form id="item" class="layui-form" action="/bdzb/label/save">
    	<input type="hidden" id="type" name="type">
        <div class="layui-form-item">
        		<label class="layui-form-label">标签名</label>
        		 <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="label" autocomplete="off" class="layui-input">
                </div>
        </div>
    </form>
</div>
</body>
</html>