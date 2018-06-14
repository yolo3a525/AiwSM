<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<%
List<String> list = new ArrayList<String>();
list.add("1. 我喜欢阅读自然科学方面的书籍和杂志。");
list.add("2. 我喜欢参加各种各样的聚会。");
list.add("3. 我喜欢成为人们注意的焦点。");
list.add("4. 看情感影片时，我常禁不住眼圈红润。");
list.add("5. 我讨厌学数学。");
list.add("6. 我喜欢经常请示上级。");
list.add("7. 有些人太霸道，有时明明知道他们是对的，也要和他们对着干。");
list.add("8. 我喜欢不时地夸耀一下自己取得的好成就。");
list.add("9. 我喜欢作一名教师。");
list.add("10. 我喜欢使用榔头一类的工具。");
list.add("11. 音乐能使我陶醉。");
list.add("12. 我喜欢在做事情前，对此事情做出细致的安排。");
list.add("13. 我喜欢把一件事情做完后再做另一件事。");
list.add("14. 我有文艺方面的天赋。");
list.add("15. 我讨厌修理自行车、电器一类的工作。");
list.add("16. 当我一个独处时，会感到更愉快。");
list.add("17. 我爱幻想。");
list.add("18. 大家公认我是一名勤劳踏实的、愿为大家服务的人。");
list.add("19. 当接受新任务后，我喜欢以自己的独特方法去完成它。");
list.add("20. 我讨厌跟各类机械打交道。");
list.add("21. 我喜欢在人事部门工作。");
list.add("22. 与言情小说相比，我更喜欢推理小说。");
list.add("23. 对别人借我的和我借别人的东西，我都能记得很清楚。");
list.add("24. 当我开始做一件事情后，即使碰到再多的困难，我也要执著地干下去。");
list.add("25. 在实验室里独自做实验会令我寂寞难耐。");
list.add("26. 我喜欢做戏剧、音乐、歌舞、新闻采访等方面的工作。");
list.add("27. 我是一个沉静而不易动感情的人。");
list.add("28. 我很难做那种需要持续集中注意力的工作。");
list.add("29. 如果掌握一门手艺并能以此为生，我会感到非常满意。");
list.add("30. 我曾渴望当一名汽车司机。");
list.add("31. 我的动手能力很差。");
list.add("32. 当我工作时，我喜欢避免干扰。");
list.add("33. 我乐于解除别人的痛苦。");
list.add("34. 我曾经渴望有机会参加探险。");
list.add("35. 在工作中我喜欢独自筹划，不愿受别人干涉。");
list.add("36. 我的理想是当一名科学家。");
list.add("37. 听别人谈“家中被盗”一类的事，很难引起我的同情。");
list.add("38. 我愿意从事虽然工资少、但是比较稳定的职业。");
list.add("39. 我总留有充裕的时间去赴约会。");
list.add("40. 我喜欢需要运用智力的游戏。");
list.add("41. 和一群人在一起的时候，我总想不出恰当的话来说。");
list.add("42. 和不熟悉的人交谈对我来说毫不困难。");
list.add("43. 我总是主动地向别人提出自己的建议。");
list.add("44. 我希望能经常换不同的工作来做。");
list.add("45. 每次写信我都一挥而就，不再重复。");
list.add("46. 我喜欢亲自动手制作一些东西，从中得到乐趣。");
list.add("47. 在集体讨论中，我往往保持沉默。");
list.add("48. 我喜欢把一切安排得整整齐齐、井井有条。");
list.add("49. 遇到难解答的问题时，我常常放弃。");
list.add("50. 我办事很少思前想后。");
list.add("51. 对于急躁、爱发脾气的人，我仍能以礼相待。");
list.add("52. 我更喜欢自己下了赌注的比赛或游戏。");
list.add("53. 我喜欢抽象思维的工作，不喜欢动手的工作。");
list.add("54. 我很容易结识同性别朋友。");
list.add("55. 我小时候经常把玩具拆开，把里面看个究竟。");
list.add("56. 对于社会问题，我通常持中庸的态度。");
list.add("57. 我经常不停地思考某一问题，直到想出正确的答案。");
list.add("58. 如果待遇相同，我宁愿当商品推销员，而不愿当图书管理员。");
list.add("59. 我喜欢按部就班地完成要做的工作。");
list.add("60. 和别人谈判时，我总是很容易放弃自己的观点。");
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
			<h2>霍兰德职业兴趣测试(精简版)</h2>
		</div><!-- /header -->
			
<div role="main" id="qlist" class="ui-content jqm-content">

		<%
			int ttype = 0;
			int vvalue = 1;
			for (int i = 0 ; i < list.size();i++){
				if(i == 7 || i ==19 || i ==29 || i ==39 || i ==41 || i ==51 || i ==57 || i ==5 || i ==18 || i ==40){
					if(i == 5 || i ==18 || i ==40){
						vvalue = 0;
					}
					ttype = 0;
				}else if(i == 2 || i ==13 || i ==22 || i ==36 || i ==43|| i ==14 || i ==23 || i ==44 || i ==47 || i ==48){
					if(i ==14 || i ==23 || i ==44 || i ==47 || i ==48){
						vvalue = 0;
					}
					ttype = 1;
				}else if(i == 6 || i ==8 || i ==20 || i ==30 || i ==31 || i ==42 || i ==21 || i ==55 || i ==56 || i ==58){
					if(i ==21 || i ==55 || i ==56 || i ==58){
						vvalue = 0;
					}
					ttype = 2;
				}else if(i == 11 || i ==24 || i ==28 || i ==35 || i ==38 || i ==46 || i ==60 || i ==3 || i ==16 || i ==25){
					if( i ==3 || i ==16 || i ==25){
						vvalue = 0;
					}
					ttype = 3;
				}else if(i ==26 || i ==37 || i ==52 || i ==59 || i ==1 || i ==12 || i ==15 || i ==27 || i ==45 || i ==53){
					if(i ==1 || i ==12 || i ==15 || i ==27 || i ==45 || i ==53){
						vvalue = 0;
					}
					ttype = 4;
				}else if(i == 4 || i ==9 || i ==10 || i ==17 || i ==33 || i ==34 || i ==49 || i ==50 || i ==54 || i ==32){
					if(i ==32){
						vvalue = 0;
					}
					ttype = 5;
				}
		%>
		<fieldset data-role="controlgroup" style="display: none">
      
            <legend><%=list.get(i)%> </legend>
           
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_1" ttype=<%=ttype%>   value=<%=vvalue %>>
                <label for="q<%=i%>_1">是</label>
                <input type="radio"  onclick="nextQ(this)" name="q<%=i%>" id="q<%=i%>_2" ttype=<%=ttype%>    value=<%=1-vvalue %>>
                <label for="q<%=i%>_2">否</label>
               
         
           
        </fieldset>
        <%}%>
	<fieldset data-role="controlgroup" style="display: none">
    <form action="/zycp/user/answer" id="form1" method="post" >
                邮箱:<input type="email" id="email" name="email" required="required">
         <input type="hidden" name="questionvalue" id="questionvalue">
         <input type="hidden" name="type" value="hlt">
	  结果会送达你的邮箱.
	 	<button onclick="qSubmit()" type="button">提交</button>
      </form>
     </fieldset>
		
	</div>
	
</div>


</body>
</html>
