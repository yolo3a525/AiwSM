<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%
String rs = (String)request.getAttribute("rs");
String desc = (String)request.getAttribute("desc");
String[] rss = rs.split(",");
%>
<head lang="en">
    <meta charset="UTF-8">
    	<title></title>
   		<link rel="stylesheet" href="/zycp/css/jquery.mobile-1.4.5.min.css" />
		<script src="/zycp/js/jquery.js"></script>
		<script src="/zycp/js/canvas.js"></script>
		<script src="/zycp/js/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
<div id="survey-chart" style="height:400px"></div>
<div>
<%=desc%>
</div>
</body>
<script type="text/javascript">

	$(document).ready(
					function() {
						types.init(0, "");
						echart
								.draw({
									"color" : [ "#ff7f50", "#6495ed",
											"#ffd700", "#32cd32", "#87cefa" ],
									"series" : [ {
										"name" : "FPA\u4e50\u5609\u6027\u683c\u8272\u5f69\u6d4b\u8bd5",
										"data" : [ {
											"name" : "\u7ea2",
											"value" : <%=rss[0]%>
										}, {
											"name" : "\u84dd",
											"value" : <%=rss[1]%>
										}, {
											"name" : "\u9ec4",
											"value" : <%=rss[2]%>
										}, {
											"name" : "\u7eff",
											"value" : <%=rss[3]%>
										} ],
										"radius" : [ 20, 110 ],
										"type" : "pie",
										"roseType" : "radius",
										"center" : [ "25%", 200 ]
									} ],
									"toolbox" : {
										"feature" : {
											"restore" : {
												"show" : false
											},
											"dataView" : {
												"readOnly" : false,
												"show" : false
											},
											"saveAsImage" : {
												"show" : false
											},
											"mark" : {
												"show" : false
											}
										},
										"show" : true
									},
									"legend" : {
										"y" : "bottom",
										"x" : "left",
										"data" : [ "\u7ea2", "\u84dd",
												"\u9ec4", "\u7eff" ]
									},
									"calculable" : true
								});
						baidu_share.innerpage("", "保存到");
					});
</script>
</html>