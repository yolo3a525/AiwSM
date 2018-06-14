<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*,com.aiw.zycp.util.*" %>
<!DOCTYPE html>
<html lang="en">
<%
List<Question> list = QuestionUtil.getMBTIQuestions();
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/zycp/css/zycp.css">
    <script src="/zycp/js/jquery.js"></script>
    <title>Document</title>
</head>
<body>
    <setion id="box">
       <div class="wj_title">
           <p style="text-align: left;">MBTI测试(精简版)</p>
        </div>
       <div class="header clear project_title" id="begin_content">
            <div class="mtop desc_begin">
                <p>感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！</p>
            </div>       
        </div>
        
        <%
            int ttype = 0;
            int vvalue = 1;
            for (int i = 0 ; i < list.size();i++){
                
        %>
        <div class="question"
            <%
            if(i != 0){
            %> 
            style="display:none"
            <% 
            }
            %> 
        >
                <div class="topic_title wjNum">
                    <span class="Qnum">[<%=i+1%>/<%=list.size()%>]</span><%=list.get(i).getTitle() %>
                </div>
                <div class="topic_con">
                    <ul>
      
                <%
                    for(int k = 0; k < list.get(i).getList().size();k++){
                        
                %>
                <li class="selectItem"  value="<%=list.get(i).getList().get(k).getValue()%>">
                                <div class="checked_wrap">
                                    <i class="circle">
                                        <span class="dot"></span>
                                    </i>
                                    <div class="option_txt"><%=list.get(i).getList().get(k).getTitle() %></div>
                                </div>
                        </li>
                <%
                    }
                %>
                 </ul>
                </div>
           
         </div>
        <%}%>
        
    <div id="btn">
     <div class="question" style="display: none">
        <form action="/zycp/user/answer" id="form1" method="post" >
        <input type="hidden" name="questionvalue" id="questionvalue">
        <input type="hidden" name="type" value="mbti">
        <p>你的邮箱</p>
        <div class="topic_input">
                <input type="email" id="email" name="email" required="required">
        </div>
        <button class="submit" onclick="qSubmit()" type="button">提交</button>
         
        </form>        
    </div>
    </setion>
</body>
</html>
<script>
     var questionvalue = "";
    (function(doc,win){
    	 var max = 28
         
         var currentQ;
    	 
        let selectArr = doc.getElementsByClassName('selectItem'),
            circleArr = doc.getElementsByClassName('circle'),
            dotArr = doc.getElementsByClassName('dot'),
            questionArr = doc.getElementsByClassName('question')
            selectChange(selectArr,circleArr,dotArr);
        // let temp = 0; 

        // for(let i = 0,length = selectItem.length; i < length; i++){
        //     selectItem[i].index = i;
        //     selectItem[i].onclick = function(){
        //         selectItem[temp].style.background = "#ffffff";
        //         selectItem[temp].style.color = "#484848";
        //         circleItem[temp].style.background = '#ffffff',
        //         circleItem[temp].style.border = '1px solid #9B9B9B';
        //         dotItem[temp].style.display = "none";

        //         this.style.background = '#2672ff';
        //         this.style.color = "#ffffff";
        //         circleItem[this.index].style.background = '#2672ff',
        //         circleItem[this.index].style.border = '2px solid #ffffff';
        //         dotItem[this.index].style.display = "block";
        //         temp = this.index;
        //     }

        // }    
       
       
        
        function selectChange(dom,circle,dot){
            let temp = 0; 

            for(let i = 0,length = dom.length; i < length; i++){
                dom[i].index = i;
                dom[i].onclick = function(){
                    questionvalue = questionvalue + dom[i].getAttribute("value");
                    questionArr[ parseInt(i/ 2) ].style.display = "none";
                    questionArr[ parseInt(i / 2) + 1].style.display = "block";
                    if(i % 2 == 0){
                        dom[i+1].style.background = "#ffffff";
                        dom[i+1].style.color = "#484848";
                        circle[i+1].style.background = '#ffffff',
                        circle[i+1].style.border = '1px solid #9B9B9B';
                        dot[i+1].style.display = "none";
        
                        this.style.background = '#2672ff';
                        this.style.color = "#ffffff";
                        circle[this.index].style.background = '#2672ff',
                        circle[this.index].style.border = '2px solid #ffffff';
                        dot[this.index].style.display = "block";
                    }else{
                        dom[i-1].style.background = "#ffffff";
                        dom[i-1].style.color = "#484848";
                        circle[i-1].style.background = '#ffffff',
                        circle[i-1].style.border = '1px solid #9B9B9B';
                        dot[i-1].style.display = "none";
        
                        this.style.background = '#2672ff';
                        this.style.color = "#ffffff";
                        circle[this.index].style.background = '#2672ff',
                        circle[this.index].style.border = '2px solid #ffffff';
                        dot[this.index].style.display = "block";
                    }
                }

            }  
        }
        
    })(document,window)
 function qSubmit(){
            $('#questionvalue').val(questionvalue);
            if($('#email').get(0).checkValidity()){
                $('#form1').submit();
            }else{
                alert('请正确输入必要的栏位.')
            }
        }     
   
</script>