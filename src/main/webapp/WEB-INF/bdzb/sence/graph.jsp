<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String r = new Date().getTime()+ "";
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

 <link href='<%=basePath%>jquery.gridly/css/jquery.gridly.css' rel='stylesheet' type='text/css'>

 <link href='<%=basePath%>jquery.gridly/css/sample.css' rel='stylesheet' type='text/css'>

 <script src='<%=basePath%>jquery.gridly/javascripts/jquery.js' type='text/javascript'></script>

 <script src='<%=basePath%>jquery.gridly/javascripts/jquery.gridly.js' type='text/javascript'></script>

 <script src='<%=basePath%>jquery.gridly/javascripts/sample.js' type='text/javascript'></script>

 <script src='<%=basePath%>jquery.gridly/javascripts/rainbow.js' type='text/javascript'></script>

 <style type="text/css">
 /* 样式1 */
.iphone {
    box-shadow: inset 0 0 3px 0 rgba(0, 0, 0, 0.2), 0 0 0 1px #999, 0 0 30px 0px rgba(0, 0, 0, 0.7);
    border: 5px solid #d9dbdc;
    background: #f8f8f8;
    padding: 15px;
    border-radius: 50px;
    height: 877px;
    width: 423px;
    position: relative;
    -webkit-transform: scale(0.8);
    transform: scale(0.8);
}
.iphone-top {
    padding: 5px 110px 40px;
    position: relative;
}
.iphone-top .speaker {
    display: block;
    width: 70px;
    height: 6px;
    margin: 0 auto;
    border-radius: 6px;
    background: #292728;
}
.iphone-top .camera {
    display: block;
    margin: 0 auto;
    height: 10px;
    width: 10px;
    border-radius: 50%;
    margin-bottom: 13px;
    background: #333;
}
.iphone-top .sensor {
    display: block;
    width: 15px;
    height: 15px;
    float: left;
    background: #333;
    margin-top: -5px;
    border-radius: 50%;
}
.iphone .top-bar, .iphone .bottom-bar {
    display: block;
    width: 423px;
    height: 15px;
    border-left: 5px solid #BBB;
    border-right: 5px solid #BBB;
    position: absolute;
    left: -5px;
}
.iphone .top-bar {
    top: 65px;
}
.iphone .bottom-bar {
    bottom: 65px;
}
.iphone-screen {
    background: #eee;
    border: 1px solid #fff;
    height: 677px;
    width: 375px;
   	overflow-y :auto;
    margin: 0 auto;
    border: 2px solid rgba(0, 0, 0, 0.9);
    border-radius: 3px;
}
.iphone-screen img {
    width: 100%;
}
.iphone .buttons .on-off, .iphone .buttons .up, .iphone .buttons .down, .iphone .buttons .sleep {
    display: block;
    background: #CCC;
    position: absolute;
    border-radius: 2px 0px 0px 2px;
}
.iphone .buttons .on-off {
    height: 40px;
    width: 3px;
    top: 100px;
    left: -8px;
}
.iphone .buttons .up, .iphone .buttons .down, .iphone .buttons .sleep {
    height: 60px;
    width: 5px;
    left: -10px;
}
.iphone .buttons .up {
    top: 170px;
}
.iphone .buttons .down {
    top: 250px;
}
.iphone .buttons .sleep {
    left: auto;
    right: -10px;
    top: 170px;
    border-radius: 0px 2px 2px 0px;
}
.iphone-bottom {
    padding: 10px 0 0;
}
.iphone-bottom span {
    display: block;
    margin: 0 auto;
    width: 68px;
    height: 68px;
    background: #ccc;
    border-radius: 50%;
    background: -webkit-linear-gradient(315deg, #303233 0%, #b5b7b9 50%, #f0f2f2 69%, #303233 100%);
    background: linear-gradient(135deg, #303233 0%, #b5b7b9 50%, #f0f2f2 69%, #303233 100%);
    position: relative;
}
.iphone-bottom span:after {
    content: "";
    display: block;
    width: 60px;
    height: 60px;
    background: #fff;
    border-radius: 50%;
    position: absolute;
    left: 4px;
    top: 4px;
}
 
 
 </style>
 <script type="text/javascript">
 
 function show(){
	 $('#iphone-screen').empty();
	 var boxs = $('.gridly').children();
	 var boxsnew = [];
	 var x = 0;
	 var y = 0;
	 var k = 0;
	 for(i=0;i<boxs.length;i++){
		 x = parseInt($(boxs[i]).css("left"));
		 y = parseInt($(boxs[i]).css("top"));
		 k = y / 150 + x / 160;
		 boxsnew[k] = boxs[i];
	 }
	 for(i=0;i<boxsnew.length;i++){
		 var img = $(boxsnew[i]).children("img").clone();
		 if(i % 3 != 0){
			 $(img).css("display","")
			 $(img).css("width",180)
		 }else{
			 $(img).css("display","block");
		 }
	 	$('#iphone-screen').append(img)
	 }
 }
 
 function save(){
     $('#iphone-screen').empty();
     var boxs = $('.gridly').children();
     var boxsnew = [];
     var x = 0;
     var y = 0;
     var k = 0;
     for(i=0;i<boxs.length;i++){
         x = parseInt($(boxs[i]).css("left"));
         y = parseInt($(boxs[i]).css("top"));
         k = y / 150 + x / 160;
         boxsnew[k] = boxs[i];
     }
     var updateShunxu = "";
     for(i =0 ; i < boxsnew.length;i++){
    	 updateShunxu = updateShunxu + $(boxsnew[i]).attr("id") + "&" + i + "#"
     }
     
     $.post("<%=basePath%>bdzb/sence/paixu",{idAndIndexs:updateShunxu},function(data){
         document.location.reload(true);
     });
     
 }
 
 </script>

</head>
<body>

<div style="float: left">
<button class="layui-btn " style="margin-bottom: 10px" onclick="show()">预览</button>
<button class="layui-btn " style="margin-bottom: 10px" onclick="save()">保存</button>
<section class='example'>

        <div class='gridly'>

		<c:forEach items="${list}" var="a">
		  <div class='brick small' id=${a.id}>
			${a.name}
			<img src="<%=basePath%>upload/sence/${a.id}.jpg" style="display:none">
          </div>
		</c:forEach>

        </div>
</section>
</div>


		<div class="iphone" style="float: left;margin-left: 800px">
		    <div class="iphone-top">
		      <span class="camera"></span>
		      <span class="sensor"></span>
		      <span class="speaker"></span>
		    </div>
		    <div class="top-bar"></div>
		    <div class="iphone-screen" id="iphone-screen">
		      
		    </div>
		    <div class="buttons">
		      <span class="on-off"></span>
		      <span class="sleep"></span>
		      <span class="up"></span>
		      <span class="down"></span>
		    </div>
		    <div class="bottom-bar"></div>
		    <div class="iphone-bottom">
		      <span></span>
		    </div>
		  </div>

</body>
</html>