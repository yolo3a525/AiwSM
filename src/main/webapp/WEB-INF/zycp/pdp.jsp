<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<%
List<String> list = new ArrayList<String>();
list.add("1.你做事是一个值得信赖的人吗？");
list.add("2.你个性温和吗？");
list.add("3.你有活力吗？");
list.add("4.你善解人意吗？");
list.add("5.你独立吗？");
list.add("6.你受人爱戴吗？");
list.add("7.做事认真且正直吗？");
list.add("8.你富有同情心吗？");
list.add("9.你有说服力吗？");
list.add("10.你大胆吗？");
list.add("11.你精确吗？");
list.add("12.你适应能力强吗？");
list.add("13.你组织能力好吗？");
list.add("14.你是否积极主动？");
list.add("15.你害羞吗？");
list.add("16.你强势吗？");
list.add("17.你镇定吗？");
list.add("18.你勇于学习吗？");
list.add("19.你反应快吗？");
list.add("20.你外向吗？");
list.add("21.你注意细节吗？");
list.add("22.你爱说话吗？");
list.add("23.你的协调能力好吗？");
list.add("24.你勤劳吗？");
list.add("25.你慷慨吗？");
list.add("26.你小心翼翼吗？");
list.add("27.你令人愉快吗？");
list.add("28.你传统吗？");
list.add("29你亲切吗？");
list.add("30.你工作足够有效率吗？");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title></title>
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
	    var rs = [0,0,0,0,0];
	    var index = 0;
    
	    
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
	    	rs[$(obj).attr("ttype")]=rs[$(obj).attr("ttype")]+Number(obj.value);
	    	if(rs[$(obj).attr("ttype")] > max){
	    		max = rs[$(obj).attr("ttype")];
	    		index = $(obj).attr("ttype");
	    	}
	    	console.log(rs);	    	
	    }
	    
	    
	    function qSubmit(){
	    	$('#questionvalue').val(index);
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
			<h2>PDP性格测试(精简版)</h2>
		</div><!-- /header -->
			
<div role="main" id="qlist" class="ui-content jqm-content">

		<%
			int ttype = 0;
			for (int i = 0 ; i < list.size();i++){
				if(i == 5 ||  i ==10 ||  i ==14 ||  i ==18 ||  i ==24 ||  i ==30){
					ttype = 0;
				}else if(i == 3 || i ==6 || i ==13 || i ==20 || i ==22 || i ==29){
					ttype = 1;
				}else if(i == 2 || i ==8 || i ==15 || i ==17 || i ==25 || i ==28){
					ttype = 2;
				}else if(i == 1 || i ==7 || i ==11 || i ==16 || i ==21 || i ==26){
					ttype = 3;
				}else if(i == 4 || i ==9 || i ==12 || i ==19 || i ==23 || i ==27){
					ttype = 4;
				}
		%>
		<fieldset data-role="controlgroup" style="display: none">
      
            <legend><%=list.get(i)%> </legend>
           
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_1" ttype=<%=ttype%>   value=5>
                <label for="q<%=i%>_1">非常同意</label>
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_2" ttype=<%=ttype%>    value=4>
                <label for="q<%=i%>_2">比较同意</label>
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_3" ttype=<%=ttype%>    value=3>
                <label for="q<%=i%>_3">差不多</label>
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_4" ttype=<%=ttype%>    value=2>
                <label for="q<%=i%>_4">有一点同意</label>
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_5" ttype=<%=ttype%>    value=1>
                <label for="q<%=i%>_5">不同意</label>
         
           
        </fieldset>
        <%}%>
		
	<fieldset data-role="controlgroup" style="display: none">
    <form action="/zycp/user/answer" id="form1" method="post" >
                邮箱:<input type="email" id="email" name="email" required="required">
         <input type="hidden" name="questionvalue" id="questionvalue">
         <input type="hidden" name="type" value="pdp">
	  结果会送达你的邮箱.
	 	<button onclick="qSubmit()" type="button">提交</button>
      </form>
     </fieldset>
		
	</div>
	
</div>


</body>
</html>
