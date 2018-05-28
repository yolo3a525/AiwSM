<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="el-common" prefix="el"%>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ page import="com.aiw.util.DDData" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	request.setAttribute("commonStatus", BDZBConstants.commonStatus);
	request.setAttribute("commonStatus2", BDZBConstants.commonStatus2);
	
	String r = new Date().getTime()+ "";
	
%>
<!DOCTYPE html>
<html>
<head>
	<title>新增或修改珠宝</title>
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
<form id="item" class="layui-form" action="">
	 <input type="hidden" name="tempid" value="${tempid}">
	 <input type="hidden" name="id" value="${edit.id}">
     <div class="layui-form-item">
    		<label class="layui-form-label">名称</label>
    		<div class="layui-input-inline">
		    	<input type="text" name="name"  lay-verify="required" autocomplete="on"  class="layui-input">
            </div>
     </div>
     
     <div class="layui-form-item">
    		<label class="layui-form-label">唯一编号</label>
    		<div class="layui-input-inline">
		    	<input type="text" name="sid"  lay-verify="required" autocomplete="on"  class="layui-input">
            </div>
     </div>
     
	 <div class="layui-form-item">
    		<label class="layui-form-label">价格</label>
    		<div class="layui-input-inline">
    	
		    	<input type="text" name="price"  lay-verify="required|number" autocomplete="on"  class="layui-input">
                	
            </div>
     </div>
     
	 <div class="layui-form-item">
    		<label class="layui-form-label">类型</label>
    		<div class="layui-input-inline">
		      <select style="float: left" name="label_pinlei" id="label_pinlei">
		       <c:forEach items="${pinlei}" var="m">
						<option value='${m.key}' <c:if test="${edit.label_pinlei == m.key}"> selected </c:if> > ${m.value}</option>
				</c:forEach>
		      </select>
		    </div>
	 </div>
    
    
    
     <div class="layui-form-item">
    		<label class="layui-form-label">其他标签</label>
    		
    		
    		
		    <div class="layui-input-inline">
		      <select style="float: left" name="label_fengge" id="label_fengge">
		       <c:forEach items="${fengge}" var="m">
						<option value='${m.key}' <c:if test="${edit.label_fengge == m.key}"> selected </c:if>> ${m.value}</option>
				</c:forEach>
		      </select>
		    </div>
		    <div class="layui-input-inline">
		      <select style="float: left" name="label_faxing" id="label_faxing">
		       <c:forEach items="${faxing}" var="m">
						<option value='${m.key}' <c:if test="${edit.label_faxing == m.key}"> selected </c:if>> ${m.value}</option>
				</c:forEach>
		      </select>
		    </div>
		    <div class="layui-input-inline">
		      <select style="float: left" name="label_fuse" id="label_fuse">
		       <c:forEach items="${fuse}" var="m">
						<option value='${m.key}' <c:if test="${edit.label_fuse == m.key}"> selected </c:if>> ${m.value}</option>
				</c:forEach>
		      </select>
		    </div>
		    <div class="layui-input-inline">
		      <select style="float: left" name="label_lianxing" id="label_lianxing">
		       <c:forEach items="${lianxing}" var="m">
						<option value='${m.key}' <c:if test="${edit.label_lianxing == m.key}"> selected </c:if>> ${m.value}</option>
				</c:forEach>
		      </select>
		    </div>
    </div>
    
    <div class="layui-form-item">
    		<label class="layui-form-label">场景</label>
    		
    		<div class="layui-input-inline">
		      <select style="float: left" name="label_changjing" id="label_changjing">
		       <c:forEach items="${changjing}" var="m">
						<option value='${m.key}'  <c:if test="${edit.label_changjing == m.key}"> selected </c:if>> ${m.value}</option>
				</c:forEach>
		      </select>
		      </div>
    </div>
    <div class="layui-form-item">
 		<label class="layui-form-label">上传图片</label>
 		<div class="layui-input-inline">
   	 	<button type="button" class="layui-btn" id="image"><i class="layui-icon"></i>上传图片</button>
   	 	<br><br>
		<img id="jewelryImage" src="<%=basePath%>upload/image/${edit.sid}.jpg?t=<%=r%>"  width="200">
        </div>
    </div>
    <div class="layui-form-item">
  		<label class="layui-form-label">上传视频</label>
  		<div class="layui-input-inline">
    	 <button type="button" class="layui-btn" id="movie"><i class="layui-icon"></i>上传视频</button>
    	 <br><br>
		 <button id="movieButton" style='display:<c:if test="${empty edit.movieflag || edit.movieflag != 1 }">none</c:if>' class="layui-btn" type="button" onclick="play();">播放</button>
        </div>
   </div>
   <div class="layui-form-item">
    		<label class="layui-form-label">设计师说</label>
    		<div class="layui-input-block" style="width: 60%">
		    	<input type="text" name="designerWords"  lay-verify="" autocomplete="on"  class="layui-input">
            </div>
     </div>


    
     
     <div class="layui-form-item">
            <label class="layui-form-label">是否新品</label>
            <div class="layui-input-inline">
              <select style="float: left" name="hotflag" id="hotflag">
               <c:forEach items="${commonStatus2}" var="m">
                        <option value='${m.key}' <c:if test="${edit.hotflag == m.key}"> selected </c:if> > ${m.value}</option>
                </c:forEach>
              </select>
            </div>
     </div>
     
     <div class="layui-form-item">
            <label class="layui-form-label">是否上架</label>
            <div class="layui-input-inline">
              <select style="float: left" name="showflag" id="showflag">
               <c:forEach items="${commonStatus2}" var="m">
                        <option value='${m.key}' <c:if test="${edit.showflag == m.key}"> selected </c:if> > ${m.value}</option>
                </c:forEach>
              </select>
            </div>
     </div>


     <div class="layui-form-item">
    		<label class="layui-form-label">推荐珠宝</label>
    		<div class="layui-input-block" style="width: 60%">
		    	<input type="text" name="relatedSid"  lay-verify="" autocomplete="on"  class="layui-input">
            </div>
     </div>
    
    <div class="layui-form-item">
 	   <div class="layui-input-block">
    	<button class="layui-btn " lay-submit="" lay-filter="item">修改保存</button>
       </div>
    </div>
</form>

</body>

<pagejs>
<script>

var url = "<%=basePath%>bdzb/jewelry/update";

loadJsonDataToForm('${el:toJsonString(edit)}','item')

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


var upload = "";
var moviepath = "movie/${edit.sid}.mp4";




layui.upload.render({
    url: '<%=basePath%>bdzb/jewelry/upload/${tempid}' //上传接口
   ,exts: 'jpg' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,title: '上传'
   ,field :'image'
   ,elem: '#image'
   ,done: function(res){ //上传成功后的回调
   		layer.msg("上传完毕......")
   		if(res.msg == "OK"){
			$('#jewelryImage').attr("src","<%=basePath%>/upload/temp/${tempid}.jpg?t" + Math.random());
			console.log($('#jewelryImage').attr("src"))
			
		}
   }
   ,before: function(res){ //上传前
   		layer.msg("正在上传......");
   }
 });
 
 
layui.upload.render({
    url: '<%=basePath%>bdzb/jewelry/upload/${tempid}' //上传接口
   ,exts: 'mp4' //那么，就只会支持这三种格式的上传。注意是用|分割。
   ,field :'movie'
   ,elem: '#movie'
   ,title: '上传'
   ,done: function(res){ //上传成功后的回调
   		layer.msg("上传完毕......")
   		if(res.msg == "OK"){
				$('#movieButton').css("display","block");
				moviepath = "temp/" + ${tempid} + ".mp4"
		}
   }
   ,before: function(res){ //上传前
   		layer.msg("正在上传......");
   }
 });



//保存
function save(again){
	$.post(url,$('#item').serialize(),function(data){
		if(data.msg ="OK"){
			parent.document.location.reload(true);
		}
	});
}

//播放
  function play(){ 
	
	
	parent.layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,area: ['544px', '620px']
        ,closeBtn: false
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['关闭']
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div><embed src="<%=basePath%>upload/'+moviepath+ '?t' + Math.random() + '" type="audio/mpeg" width="544" height="544" ></embed></div>'
        ,success: function(layero){
        }
    });	
	
 }

</script>
</pagejs>
</html>