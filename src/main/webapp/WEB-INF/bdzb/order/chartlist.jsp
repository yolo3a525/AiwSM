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
	
	
	
%>

<!DOCTYPE html>
<html>
<head>
<title>借戴统计</title>
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
	<legend>借戴统计</legend>
	</fieldset>
	
<form class="layui-form "  id="query" action="">
  	
   <div class="layui-input-inline">
    	<div class="layui-inline">
        <label class="layui-form-label">年份</label>
        <div class="layui-input-inline">
          <select name="yearNumber" id="yearNumber">
            <option accesskey="2017">2017</option>
            <option accesskey="2018">2018</option>
            <option accesskey="2019">2019</option>
            <option accesskey="2019">2020</option>
            
          </select>
        </div>
        </div>
        
	     <div class="layui-input-inline">
	      <button class="layui-btn" lay-submit="" type="button" onclick="cahrtQuery()" lay-filter="query">查询</button>
	    </div>
    </div>
</form>

<Br>
<Br>
<Br>

<div id="chart1" style="width: 1080px;height: 720px"></div>

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
        text: '预约借戴走势图',
        left: 'center'
    },
    legend: {
        left: 'left',
        data: ['累计预约次数', '累计借戴次数']
    },
    tooltip: {},
    xAxis: {
        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: {
    	
    },
    series: [{
        name: '累计预约次数',
        type: 'line',
        data: []
    },
    {
        name: '累计借戴次数',
        type: 'line',
        data: []
    }
    ]
});

chart(2017)

function cahrtQuery(){
	chart($('#yearNumber').val());
}

function chart(yearNumber){
	$.post('<%=basePath%>bdzb/order/chart1', {yearNumber:yearNumber},function(data){
	    // 填入数据
	    myChart.setOption({
	        series: [{
	            // 根据名字对应到相应的系列
	            name: '累计预约次数',
	            data: data.data[0]
	        },{
                // 根据名字对应到相应的系列
                name: '累计借戴次数',
                data: data.data[1]
            }]
	    });
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


 
</script>
</pagejs>
</body>
</html>