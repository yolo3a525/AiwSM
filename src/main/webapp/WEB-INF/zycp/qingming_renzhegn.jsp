<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%

%>
<head lang="en">
    <meta charset="UTF-8">
    <title>为爱祭奠</title>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
		wx.config({
		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: 'wxf36d9dd7b7343243', // 必填，公众号的唯一标识
		    timestamp:'1459743105', // 必填，生成签名的时间戳
		    nonceStr: 'abc123', // 必填，生成签名的随机串
		    signature: 'ed2c9d17d29a112552a23ff059751d4c6cc3aea9',// 必填，签名，见附录1
		    jsApiList: ['onMenuShareAppMessage',
		                'onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		wx.ready(function(){
			
			wx.onMenuShareAppMessage({
				title: '为爱祭奠', // 分享标题
			    desc: '正在路上奔波的你,抽出时间为爱祭奠.', // 分享描述
			    link: location.href.split('#')[0], // 分享链接
			    imgUrl: 'http://down.qnwz.cn/uploads/allimg/140404/1-140404163620K2.jpg', // 分享图标
			    type: 'link' // 分享类型,music、video或link，不填默认为link
			});
		});
		
		
		
		
		</script>
</head>
<body>
"清明时节雨纷纷,路上行人欲断魂......"
也许你正在为自己的事业忙碌打拼
也许你正在为......
</body>
</html>