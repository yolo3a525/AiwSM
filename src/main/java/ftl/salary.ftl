<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<p>
亲：
</p>
<p>
   &nbsp;&nbsp; 感谢您的辛勤劳动，以下是您${date}的工资明细，有疑问的，请于3个工作日内反馈到相关部门，谢谢！
</p>

<table border="1" cellspacing="0" cellpadding="0">
    <thead>
    <tr>
    	<th rowspan ="2" style="padding:5px">部门</th>
    	<th rowspan ="2" style="padding:5px">姓名</th>
    	<th colspan ="6" style="padding:5px">应发明细</th>
    	<th rowspan ="2" style="padding:5px">应发金额</th>
    	<th colspan ="5" style="padding:5px">代扣代交</th>
    	<th rowspan ="2" style="padding:5px">银行实发</th>
    	<th rowspan ="2" style="padding:5px">其他代扣备注</th>
    </tr>
     <tr>
     	<th style="padding:5px">工资标准</th>
    	<th style="padding:5px">绩效工资</th>
    	<th style="padding:5px">业务提成</th>
    	<th style="padding:5px">补贴</th>
    	<th style="padding:5px">缺勤工资</th>
    	<th style="padding:5px"> 其他</th>
    	
    	<th style="padding:5px">个人社保扣除</th>
    	<th style="padding:5px">个人公积金扣除</th>
    	<th style="padding:5px">个人所得税</th>
    	<th style="padding:5px">其他代扣</th>
    	<th style="padding:5px">扣款小计</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<#list data?keys as key> 
    	 	<td  style="padding:5px">${data[key]}</td>
		</#list> 
    </tr>
    </tbody>
</table>
<p>
基本工资、绩效工资问题：请联系人力行政部杨璐
</p>
<p>
考勤、社保及公积金扣款问题：请联系人力行政部奉丽萍
</p>
<p>
业绩提成请联系财务部出纳曾玉鑫
</p>

<br>

<p>
曾玉鑫 Mary
</p>
<p>
财 务 出 纳
</p>
<p>
 T (CN) :  +86 131 6807 7849
 </p>
<p>
RAYVISION 瑞云渲染 ( Former RenderBus )
</p>

</body>
</html>