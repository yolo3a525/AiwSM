<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.aiw.util.DDData" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+ 
	        (request.getServerPort() == 80 ? "" : (":"+request.getServerPort())) 
	        + path+"/";
	String sysname = (String)DDData.dd.get("sys").get("name");
	
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><sitemesh:write property='title' /></title>
    
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=basePath%>layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>layui/css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">
    
  
    <sitemesh:write property='head' />
    
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header header header-demo">
        <div class="layui-main">
                <img alt="" src="<%=basePath%>/upload/sys/logo.png" height="60px">
               	<span style="font-size: 26px;color: white;line-height:60px"><%=sysname%></span>
               	
               	
            <ul class="layui-nav" pc>
                <li class="layui-nav-item layui-hide-xs" lay-unselect="">
		        <a href="javascript:;" onclick="notice()" layadmin-event="note">
		          <i class="layui-icon layui-icon-note"></i>
		        </a>
		      </li>
		      
		      
                
                <li class="layui-nav-item">
                    <a href="javascript:;">${sessionScope.user.username}</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=basePath%>logout" >登出</a></dd>
                    </dl>
                </li>
               
            </ul>
        </div>
    </div>
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree site-demo-nav">
				<c:forEach items="${sessionScope.menu}" var="a">
					 <li class="layui-nav-item ">
					   <a class="javascript:;" href="javascript:;">
					   <i class="layui-icon ${a.icon}"></i>
					   <cite>${a.name}</cite>
					   </a>
					   <dl class="layui-nav-child">
					   <c:forEach items="${a.children}" var="aa">
						    <dd class="">
	                           <a href="<%=basePath%>${aa.url}" class="menu-a" data-url="${aa.url}" id=${aa.id}>${aa.name}</a>
	                        </dd>
						</c:forEach>
						</dl>
					</li>
				</c:forEach>
            </ul>

        </div>
    </div>
	<div class="layui-body">
		<sitemesh:write property='body' />
	</div>

</div>
<script src="<%=basePath%>layui/layui.all.js"></script>
<script src="<%=basePath%>my/form.js"></script>
<script src="<%=basePath%>my/common.js"></script>
<script type="text/javascript">

  var $ = layui.jquery, 
  form = layui.form,
  laypage = layui.laypage,
  layer = layui.layer,
  element = layui.element;
  layui.use(['element','layer'], function(){
      var $ = layui.jquery,
      		element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
      var locationUrl = window.location.href;
      var flag = '?';
      var urls = locationUrl.split(flag);
      var baseUrl = urls[0];
      var shortUrl = '';
      if(urls.length > 1){
      	shortUrl = urls[1];
      }
     $('.menu-a').each(function() {
	 var $this = $(this);
	 if($this.attr("href").split(flag)[0] == baseUrl){
		 $this.parent().addClass("layui-this")
		 $this.parent().parent().parent().addClass("layui-nav-itemed")
		 return false;
	 }
     });
  });
  
  function dbback(){
  	var url = "<%=basePath%>code/dbback";
   	$.get(url,function(data){
   		layer.confirm('数据恢复成功,跳转登陆页面....', {
   		    btn: ['确定'], //按钮
   		    shade: true //显示遮罩
   		}, function(index){
   			 location.href = "<%=basePath%>login";
			});
		});
	}
  
  function notice(){
	  //示范一个公告层
      layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,closeBtn: false
        ,area: '300px;'
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['拭目以待']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你知道吗？亲！<br><br>编程 ≠ 架构<br><br>这是一个预留功能,为了更好的体验!<br><br>我们此后的征途是星辰大海 ^_^</div>'
        ,success: function(layero){
          
        }
      });
  }
</script>
<sitemesh:write property='pagejs' />
</body>
</html>