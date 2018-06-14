<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*,com.aiw.zycp.controller.*,com.aiw.entity.KeyValue,com.aiw.zycp.util.*" %>
<!DOCTYPE html>
<html>
<%
List<List<KeyValue>> list = QuestionUtil.getDISC();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>DISC性格测试题（完整版）</title>
		<link rel="stylesheet" href="/zycp/css/jquery.mobile-1.4.5.min.css" />
		<script src="/zycp/js/jquery.js"></script>
		<script src="/zycp/js/jquery.mobile-1.4.5.min.js"></script>
		<style type="text/css">
		
		body {
			font-family: 微软雅黑;
		}
		</style>
		
		<script type="text/javascript">
	    var max = 0
	    var questionvalue = ""
	    var currentQ;
	    var index = 0;
	    var rs = {D:0,I:0,S:0,C:0}
    
	    
	    jQuery(function($){
	    	currentQ = $($('#qlist').children("fieldset").get(0));
	    	//debugger;
	    	currentQ.show();
		});	
	    function nextQ(obj){
	    	currentQ.hide();
	    	currentQ = currentQ.next()
	    	currentQ.show();
	    	//alert(typeof(rs[$(obj).attr("ttype")]))
	    	//alert(typeof(obj.value))
	    	rs[obj.value] = rs[obj.value]  + 1;
	    	if(rs[obj.value] > index){
	    		index = rs[obj.value];
	    		questionvalue = obj.value;
	    	}
	    	console.log(questionvalue);	  
	    	console.log(rs);	  
	    }
	    
	    
	    function qSubmit(){
	    	$('#questionvalue').val(questionvalue);
	    	if($('#email').get(0).checkValidity()){
		    	$('#form1').submit();
	    	}else{
	    		alert('请正确输入必要的栏位.')
	    	}
	    }

	    </script>
		
		</script>
	</head>
	<body>
<div data-role="page" class="jqm-demos" data-quicklinks="true">

		<div data-role="header" class="jqm-header">
			<h2>DISC性格测试题（完整版）</h2>
		</div><!-- /header -->
			
<div role="main" id="qlist" class="ui-content jqm-content">

		<%
			int ttype = 0;
			int vvalue = 1;
			for (int i = 0 ; i < list.size();i++){
				
		%>
		<fieldset data-role="controlgroup" style="display: none">
      
            <legend><%=i+1%>/<%=list.size()%></legend>
           		<%
           			for(int k = 0; k < list.get(i).size();k++){
           				
           		%>
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_<%=k%>"  value=<%=list.get(i).get(k).getValue() %>>
                <label for="q<%=i%>_<%=k%>"><%=list.get(i).get(k).getKey() %></label>
         		<%
					}
         		%>
           
        </fieldset>
        <%}%>
	<fieldset data-role="controlgroup" style="display: none">
    <form action="/zycp/user/answer" id="form1" method="post" >
                邮箱:<input type="email" id="email" name="email" required="required">
         <input type="hidden" name="questionvalue" id="questionvalue">
         <input type="hidden" name="type" value="disc">
	  结果会送达你的邮箱.
	 	<button onclick="qSubmit()" type="button">提交</button>
      </form>
     </fieldset>
		
	</div>
	
</div>


</body>
</html>
