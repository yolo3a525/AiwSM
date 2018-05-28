<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.aiw.util.DDData" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String sysname = (String)DDData.dd.get("sys").get("name");
	String indexpage = (String)DDData.dd.get("sys").get("indexpage");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>DEMO</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=basePath%>layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>layui/css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/my.css" media="all">

</head>
<body style="background-color: #23262E">

<div id="login" style="display: none">

    <form class="layui-form"  style="padding: 20px" method="post" action="loginConfirm">
        <div class="layui-form-item">
                <input type="text" name="username" lay-verify="required" autocomplete="off" placeholder="用户名" class="layui-input">

        </div>
        <div class="layui-form-item">
                    <input type="password" name="password" lay-verify="password"  placeholder="密码"  autocomplete="off" class="layui-input">
        </div>

        <div class="layui-form-item">
                <button class="layui-btn" lay-submit="" lay-filter="login">登录</button>
        </div>
    </form>

</div>


<script src="<%=basePath%>layui/layui.all.js"></script>
<div id="js">
    <script>
    	//;!的写法是为了简单一些.
        ;!function(){
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            var loginDivHeight =  $("#login").outerHeight(true);
            var type = "登录";
            parent.layer.open({
                type: 1
                ,title:"<%=sysname%>"
                ,id: 'loginLayer' //防止重复弹出
                ,content: $('#login')
                ,move: false
                ,offset: '300px'
                ,area: ['300px', loginDivHeight]
                ,closeBtn: 0
                ,btnAlign: 'c' //按钮居中
                //,shade: 0 //不显示遮罩
                ,yes: function(){
                    layer.closeAll();
                }
            });
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;

            //创建一个编辑器
            var editIndex = layedit.build('LAY_demo_editor');

            //自定义验证规则
            form.verify({
                title: function(value){
                    if(value.length < 5){
                        return '标题至少得5个字符啊';
                    }
                }
                ,pass: [/(.+){6,12}$/, '密码必须6到12位']
                ,content: function(value){
                    layedit.sync(editIndex);
                }
            });

            //监听指定开关
            form.on('switch(switchTest)', function(data){
                layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
                    offset: '6px'
                });
                layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
            });

            form.on('submit(login)', function(data){
               /*  parent.layer.alert(JSON.stringify(data.field), {
                    title: '最终的提交信息'
                })
                 */
                $.post('loginConfirm', data.field, function(res) {
                	if(res == null ||res == ""){
                		layer.msg("登录失败", {icon:5, time:2000,anim:6});
                	}else{
                		//alert(res.msg)
                		<%
                		if(indexpage != null){
                		    %>
                		    window.location.href = '<%=indexpage%>';
                		    <%
                		}else{
                		%>
                		  window.location.href = 'bdzb/home';
                	    <%}%>
                		
                	}
		  		});
                return false;
            });


        }();

    </script>
</div>
</body>
</html>