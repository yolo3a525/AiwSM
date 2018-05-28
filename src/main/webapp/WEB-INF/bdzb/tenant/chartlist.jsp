<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.aiw.bdzb.util.*" %> 
<%@ page import="com.aiw.util.DDData" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ taglib uri="el-common" prefix="el"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	
	request.setAttribute("tenantStatus", BDZBConstants.tenantStatus);
	pageContext.setAttribute("tenantStatus", BDZBConstants.tenantStatus);
	request.setAttribute("tenantDeadLine", BDZBConstants.tenantDeadLine);
	pageContext.setAttribute("tenantDeadLine", BDZBConstants.tenantDeadLine);
	
	
	LinkedHashMap<String,String> all = new LinkedHashMap();
	all.put("0","非会员");
	all.put("2","临时会员");
	all.putAll(DDData.dd.get("xiaofeika"));
	all.putAll(DDData.dd.get("huijika"));
	
	pageContext.setAttribute("allCard", all);
	
	
%>

<!DOCTYPE html>
<html>
<head>
<title>客户管理</title>
<style type="text/css">
  img {
  	border-radius: 50px;
    height: 50px
  }
</style>
</head>
<body>

<div class="layui-main">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
	<legend>注册统计</legend>
	</fieldset>
	
<form class="layui-form "  id="query" action="">
  	
   <div class="layui-input-inline">
    	<div class="layui-btn-group">
		    <button class="layui-btn" type="button" onclick="chart1(7)">最近7天</button>
		    <button class="layui-btn" type="button" onclick="chart1(30)">最近一月</button>
	    </div>
	    <div class="layui-input-inline">
	     <input type="text" name="startRegisterDate"  id="startRegisterDate" onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD'})"  
	     	autocomplete="on"  class="layui-input">
	    </div>
	     <div class="layui-input-inline">
	     <input type="text" name="endRegisterDate"   id="endRegisterDate"  onclick="layui.laydate.render({elem: this, istime: true, format: 'YYYY-MM-DD'})"  
	      	autocomplete="on"  class="layui-input">
	     </div>
	     <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" type="button" onclick="cahrtQuery()" lay-filter="query">查询</button>
	    </div>
    </div>
</form>

<div id="chart1" style="width: 600px;height: 300px"></div>

<div id="chart2" style="width: 300px;height: 300px"></div>

</div>


<pagejs>
<script type="text/javascript" src="<%=basePath%>echarts/echarts.js"></script>
<script>
var myChart = echarts.init(document.getElementById('chart1'));
var myChart2 = echarts.init(document.getElementById('chart2'));
//显示标题，图例和空的坐标轴
myChart.setOption({
    title: {
        text: '注册人数'
    },
    tooltip: {},
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [{
        name: '人数',
        type: 'bar',
        data: []
    }]
});

chart(null,null)


function chart1(days){
	chart(getDate(-days),getDate(0));
}
function cahrtQuery(){
	chart($('#startRegisterDate').val(),$('#endRegisterDate').val());
}

function chart(start,end){
	$.post('<%=basePath%>bdzb/tenant/chart1', {startRegisterDate:start,endRegisterDate:end},function(data){
	    // 填入数据
	    myChart.setOption({
	        xAxis: {
	            data: data.categories
	        },
	        series: [{
	            // 根据名字对应到相应的系列
	            name: '人数',
	            data: data.data
	        }]
	    });
	    if(start == null && end == null){
	    	chartHuan(data);
	    }
	})
}

//日期，在原有日期基础上，增加days天数，默认增加1天
function getDate(days) {
    if (days == undefined || days == '') {
        days = 1;
    }
    var date = new Date();
    date.setDate(date.getDate() + days);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
}

// 日期月份/天的显示，如果是1位数，则在前面加上'0'
function getFormatDate(arg) {
    if (arg == undefined || arg == '') {
        return '';
    }
    var re = arg + '';
    if (re.length < 2) {
        re = '0' + re;
    }
    return re;
}

function chartHuan(data){
	
	var data2 = [];
	for(var i =0 ;i < data.categories.length;i++){
		data2.push({
			name:data.categories[i],
			value:data.data[i]
		})
	}

	option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:data.categories
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                    show: true,
	                    textStyle: {
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:data2
	        }
	    ]
	};
	myChart2.setOption(option);
}

 
</script>
</pagejs>
</body>
</html>