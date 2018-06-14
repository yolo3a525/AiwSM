<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*" %>
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
        var max = 28
        var questionvalue = ""
        var currentQ;
        
        jQuery(function($){
            currentQ = $($('#qlist').children("fieldset").get(0));
            //debugger;
            currentQ.show();
        }); 
        function nextQ(obj){
            currentQ.hide();
            currentQ = currentQ.next()
            currentQ.show();
            questionvalue = questionvalue + obj.value;
            console.log(questionvalue);
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
            <h2>MBTI测试(精简版)</h2>
        </div><!-- /header -->
            
<div role="main" id="qlist" class="ui-content jqm-content">

        <fieldset data-role="controlgroup" style="display: none">
       
            <legend>1.当你要外出一整天，你会 </legend>
           
                <input type="radio"  onclick="nextQ(this)" name="q1" id="q1_1"  value="J">
                <label for="q1_1">A. 计划你要做什么和在什么时候做(J)</label>
           
           
                <input type="radio"  onclick="nextQ(this)" name="q1" id="q1_2"  value="P">
                <label for="q1_2">B. 说去就去(P)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>2.你是否</legend>
           
                <input type="radio"  onclick="nextQ(this)" name="q2" id="q2_1"  value="E">
                <label for="q2_1">A.容易让人了解(E)</label>
           
           
                <input type="radio"  onclick="nextQ(this)" name="q2" id="q2_2"  value="I">
                <label for="q2_2">B.难于让人了解(I)</label>
           
        </fieldset>

    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>3.你认为自己是一个</legend>
           
                <input type="radio"  onclick="nextQ(this)" name="q3" id="q3_1"  value="P">
                <label for="q3_1">A.较为随兴所至的人(P)</label>
           
           
                <input type="radio"  onclick="nextQ(this)" name="q3" id="q3_2"  value="J">
                <label for="q3_2">B.较为有条理的人(J)</label>
           
        </fieldset>

    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>4.假如你是一位老师，你会选教</legend>
            <input type="radio"  onclick="nextQ(this)" name="q4" id="q4_1"  value="S">
                <label for="q4_1">A.以事实为主的课程(S)</label>
           
           <input type="radio"  onclick="nextQ(this)" name="q4" id="q4_2"  value="N">
                <label for="q4_2">B.涉及理论的课程(N)</label>
           
        </fieldset>

    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>5.处理许多事情上，你会喜欢 </legend>
            <input type="radio"  onclick="nextQ(this)" name="q5" id="q5_1"  value="P">
                <label for="q5_1">A.凭兴所至行事(P)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q5" id="q5_2"  value="J">
                <label for="q5_2">B. 按照计划行事(J)</label>
           
        </fieldset>

    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>6.下面哪个词语更和我心意</legend>
            <input type="radio"  onclick="nextQ(this)" name="q6" id="q6_1"  value="F">
                <label for="q6_1">A.仁慈慷慨的（F）</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q6" id="q6_2"  value="T">
                <label for="q6_2">B.意志坚定的(T)</label>
           
        </fieldset>

    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>7.按照程序表做事</legend>
            <input type="radio"  onclick="nextQ(this)" name="q7" id="q7_1"  value="J">
                <label for="q7_1">A.合你心意(J)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q7" id="q7_2"  value="P">
                <label for="q7_2">B.令你感到束缚(P)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>8.你做事多数是</legend>
            <input type="radio"  onclick="nextQ(this)" name="q8" id="q8_1"  value="J">
                <label for="q8_1">A.按当天心情去做(J)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q8" id="q8_2"  value="P">
                <label for="q8_2">B.照拟好的程序表去做(P)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>9.你倾向 </legend>
            <input type="radio"  onclick="nextQ(this)" name="q9" id="q9_1"  value="F">
                <label for="q9_1">A.重视感情多于逻辑(F)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q9" id="q9_2"  value="T">
                <label for="q9_2">B.重视逻辑多于感情(T)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>10.与很多人一起会</legend>
            <input type="radio"  onclick="nextQ(this)" name="q10" id="q10_1"  value="E">
                <label for="q10_1">A.令你活力培增(E)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q10" id="q10_2"  value="I">
                <label for="q10_2">B.常常令你心力憔悴(I)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>11.当你有一份特别的任务，你会喜欢</legend>
            <input type="radio"  onclick="nextQ(this)" name="q11" id="q11_1"  value="J">
                <label for="q11_1">A.开始前小心组织计划(J)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q11" id="q11_2"  value="P">
                <label for="q11_2">B.边做边找须做什么(P)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>12.在大多数情况下，你会选择当你有一份特别的任务，你会喜欢 </legend>
            <input type="radio"  onclick="nextQ(this)" name="q12" id="q12_1"  value="P">
                <label for="q12_1">A.顺其自然(P)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q12" id="q12_2"  value="J">
                <label for="q12_2">B.按程序表做事(J)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>13.你通常</legend>
            <input type="radio"  onclick="nextQ(this)" name="q13" id="q13_1"  value="E">
                <label for="q13_1">A.与人容易混熟(E)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q13" id="q13_2"  value="I">
                <label for="q13_2">B.比较沉静或矜持(I)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>14.哪些人会更吸引你？</legend>
            <input type="radio"  onclick="nextQ(this)" name="q14" id="q14_1"  value="N">
                <label for="q14_1">A. 一个思想敏捷及非常聪颖的人(N)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q14" id="q14_2"  value="S">
                <label for="q14_2">B.实事求是，具丰富常识的人(S)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>15.大多数人会说你是一个</legend>
            <input type="radio"  onclick="nextQ(this)" name="q15" id="q15_1"  value="I">
                <label for="q15_1">A.重视自我隐私的人(I)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q15" id="q15_2"  value="E">
                <label for="q15_2">B.非常坦率开放的人(E)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>16.在一大群人当中，通常是</legend>
            <input type="radio"  onclick="nextQ(this)" name="q16" id="q16_1"  value="E">
                <label for="q16_1">A.你介绍大家认识(E)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q16" id="q16_2"  value="I">
                <label for="q16_2">B.别人介绍你(I)</label>
           
        </fieldset>

    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>17.哪个是较高的赞誉，或称许为</legend>
            <input type="radio"  onclick="nextQ(this)" name="q17" id="q17_1"  value="T">
                <label for="q17_1">A.能干的(T)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q17" id="q17_2"  value="F">
                <label for="q17_2">B.富有同情心(F)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>18.你喜欢花很多的时间</legend>
            <input type="radio"  onclick="nextQ(this)" name="q18" id="q18_1"  value="I">
                <label for="q18_1">A.一个人独处(I)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q18" id="q18_2"  value="E">
                <label for="q18_2">B.合别人在一起(E)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>19.一般来说，你和哪些人比较合得来？</legend>
            <input type="radio"  onclick="nextQ(this)" name="q19" id="q19_1"  value="N">
                <label for="q19_1">A.富于想象力的人(N)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q19" id="q19_2"  value="S">
                <label for="q19_2">B.现实的人(S)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>20.你宁愿被人认为是一个</legend>
            <input type="radio"  onclick="nextQ(this)" name="q20" id="q20_1"  value="S">
                <label for="q20_1">A.实事求是的人(S)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q20" id="q20_2"  value="N">
                <label for="q20_2">B.机灵的人(N)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>21.哪个是较高的赞誉，或称许为？</legend>
            <input type="radio"  onclick="nextQ(this)" name="q21" id="q21_1"  value="F">
                <label for="q21_1">A.一贯感性的人(F)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q21" id="q21_2"  value="T">
                <label for="q21_2">B.一贯理性的人(T)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>22.你会跟哪些人做朋友？</legend>
            <input type="radio"  onclick="nextQ(this)" name="q22" id="q22_1"  value="N">
                <label for="q22_1">A.常提出新注意的(N)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q22" id="q22_2"  value="S">
                <label for="q22_2">B.脚踏实地的(S)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>23.要作决定时，你认为比较重要的是</legend>
            <input type="radio"  onclick="nextQ(this)" name="q23" id="q23_1"  value="T">
                <label for="q23_1">A.据事实衡量(T)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q23" id="q23_2"  value="F">
                <label for="q23_2">B.考虑他人的感受和意见(F)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>24.要做许多人也做的事，你比较喜欢</legend>
            <input type="radio"  onclick="nextQ(this)" name="q24" id="q24_1"  value="S">
                <label for="q24_1">A.按照一般认可的方法去做（S）</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q24" id="q24_2"  value="N">
                <label for="q24_2">B.构想一个自己的想法(N)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>25.在社交聚会中，你</legend>
            <input type="radio"  onclick="nextQ(this)" name="q25" id="q25_1"  value="I">
                <label for="q25_1">A.有时感到郁闷(I)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q25" id="q25_2"  value="E">
                <label for="q25_2">B.常常乐在其中(E)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>26.下面哪个词语更和我心意</legend>
            <input type="radio"  onclick="nextQ(this)" name="q26" id="q26_1"  value="T">
                <label for="q26_1">A.实际(T)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q26" id="q26_2"  value="F">
                <label for="q26_2">B.多愁善感(F)</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>27.你通常较喜欢的科目是 </legend>
            <input type="radio"  onclick="nextQ(this)" name="q27" id="q27_1"  value="N">
                <label for="q27_1">A.讲授概念和原则的(N)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q27" id="q27_2"  value="S">
                <label for="q27_2">B.讲授事实和数据的（S）</label>
           
        </fieldset>
    <fieldset data-role="controlgroup" style="display: none">
       
            <legend>28.你是否经常让</legend>
            <input type="radio"  onclick="nextQ(this)" name="q28" id="q28_1"  value="F">
                <label for="q28_1">A.你的情感支配你的理智(F)</label>
           
            <input type="radio"  onclick="nextQ(this)" name="q28" id="q28_2"  value="T">
                <label for="q28_2">B.你的理智主宰你的情感(T)</label>
           
        </fieldset>
        
    <fieldset data-role="controlgroup" style="display: none">
    <form action="/zycp/user/answer" id="form1" method="post" >
                邮箱:<input type="email" id="email" name="email" required="required">
         <input type="hidden" name="questionvalue" id="questionvalue">
         <input type="hidden" name="type" value="mbti">
      结果会送达你的邮箱.
        <button onclick="qSubmit()" type="button">提交</button>
      </form>
     </fieldset>
        
    </div>
    
</div>


</body>
</html>
    