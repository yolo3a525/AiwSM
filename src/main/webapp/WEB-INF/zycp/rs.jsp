<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%
String type = (String)request.getAttribute("rs");
String desc = (String)request.getAttribute("desc");
%>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
   		<link rel="stylesheet" href="/zycp/css/jquery.mobile-1.4.5.min.css" />
		<script src="/zycp/js/jquery.js"></script>
		<script src="/zycp/js/jquery.mobile-1.4.5.min.js"></script>
</head>
<div data-role="page" class="jqm-demos" data-quicklinks="true">

		<div data-role="header" class="jqm-header">
			<h2>你属于<%=type %>类型</h2>
		</div><!-- /header -->

		<div role="main" id="qlist" class="ui-content jqm-content">
        <p>
		<%=desc %>
        </p>
    	</div>
</div>
</body>
</html>