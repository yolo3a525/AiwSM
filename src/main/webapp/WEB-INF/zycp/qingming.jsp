<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%

%>
<head lang="en">
    <meta charset="UTF-8">
    <title>为爱祭奠</title>
   		<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
		<script src="js/jquery.js"></script>
		<script src="js/jquery.mobile-1.4.5.min.js"></script>
		<script type="text/javascript">
		
		var imgUrl = 'http://#www.jakehu.me/xxx.jpg';
		var lineLink = 'http://#www.jakehu.me/904';
		var descContent = "这是晨曦的博客";
		var shareTitle = '晨曦';
		var appid = '';

		function shareFriend() {
		    WeixinJSBridge.invoke('sendAppMessage',{
		        "appid": appid,
		            "img_url": imgUrl,
		            "img_width": "200",
		            "img_height": "200",
		            "link": lineLink,
		            "desc": descContent,
		            "title": shareTitle
		        }, function(res) {
		            //_report('send_msg', res.err_msg);
		        })
		    }
		    function shareTimeline() {
		        WeixinJSBridge.invoke('shareTimeline',{
		            "img_url": imgUrl,
		            "img_width": "200",
		            "img_height": "200",
		            "link": lineLink,
		            "desc": descContent,
		            "title": shareTitle
		        }, function(res) {
		              //_report('timeline', res.err_msg);
		        });
		    }
		    function shareWeibo() {
		        WeixinJSBridge.invoke('shareWeibo',{
		            "content": descContent,
		            "url": lineLink,
		        }, function(res) {
		            //_report('weibo', res.err_msg);
		        });
		    }
		    // 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
		    document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		        // 发送给好友
		        WeixinJSBridge.on('menu:share:appmessage', function(argv){
		            shareFriend();
		        });
		        // 分享到朋友圈
		        WeixinJSBridge.on('menu:share:timeline', function(argv){
		            shareTimeline();
		        });
		        // 分享到微博
		        WeixinJSBridge.on('menu:share:weibo', function(argv){
		            shareWeibo();
		        });
		    }, false);


		
		
		</script>
</head>
<body>
<div style='margin:0 auto;width:0px;height:0px;overflow:hidden;'>
<img alt="" src="http://down.qnwz.cn/uploads/allimg/140404/1-140404163620K2.jpg">
</div>
</body>
</html>

