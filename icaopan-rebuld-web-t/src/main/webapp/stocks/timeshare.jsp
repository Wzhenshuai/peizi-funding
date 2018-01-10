<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
<link href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>

</head>
<body>
<div class="chart-config" style="margin-top:0px">
	<ul class="type-list">
		<li id="time-li" class="select">分时</li>
		<li id="day-li">日K</li>
	</ul>
</div>
<div id='time-share' class="time-share time-share-none"></div>
<div id="vkContent2" class="time-share" style="display:none;height:300px; width: 419px;"></div>
<script type="text/javascript" src="<%=basePath%>js/raphael-min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/stock.js"></script>	
<script type="text/javascript" src="<%=basePath%>/js/hidpi-canvas.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/moment.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/vkchart.js" ></script>
<script>
MS = {};
MS.timeShare = null;
MS.currentStockCode = null;
MS.count = 0;
MS.timer = null;
MS.loadingClass = 'loading';
MS.width = 320;
MS.height = 150;

function returnjson(data) {
	if(MS.type == 'new' && MS.currentStockCode == data.info.result.s1) {
		return;
	}
	
	
	if(MS.timeShare){
		MS.timeShare.destroy();
		MS.timeShare = null;
	}
	$('#time-share').removeClass('time-share-none');
	MS.timeShare = new TimeShare('time-share', MS.width, MS.height, data, {type: 2});
	MS.timeShare.draw();
	MS.currentStockCode = data.info.result.s1;
	//如果是停牌就不发请求了
	if(MS.timeShare.suspensionFlag == 'true') {
		$('.'+MS.loadingClass).remove();
		return;
	}
	MS.timer = setTimeout($.proxy(queryTimeShare, this, MS.currentStockCode), 1000*10);
	if ($('.'+MS.loadingClass).size() > 0 && MS.timeShare !=null) {
		if(MS.timeShare.competitionFlag != 1) {
			setTimeout(function() {
				$('.'+MS.loadingClass).remove();
			}, 2000);
		} else {
			$('.'+MS.loadingClass).remove();
		}
	}
}

function queryTimeShare(stockCode, type) {
	//MS.currentStockCode = stockCode;

	if(type == 'new') {
		clearTimeout(MS.timer);
		MS.type = 'new';
	} else {
		MS.type = 'old';
	}


	$.ajax({
		type: "get",
		//url: systemConfig.path + "/js/test.json",
		//dataType: 'json',
		//success: returnjson,
		url: 'http://md.icaopan.com/openapi/queryTimeMarketData?is_web=1&exChangeCode=&stockCode=' + stockCode,
		dataType: 'jsonp'
	});
	drawKLine(stockCode);
}

var getParameter = function() {
	var params = window.location.search.substring(1).split('&'),
		len = params.length,
		i = 0,
		parameter = {};
	for(; i <  len; i++) {
		var paramArr = params[i].split('=');
		parameter[paramArr[0]] = paramArr[1];
	}
	if(/^\d{6}$/ig.test(parameter.stockCode)) {
		queryTimeShare(parameter.stockCode, 'new');
	}
	if(parameter.width) {
		MS.width = parameter.width;
	}
	if(parameter.height) {
		MS.height = parameter.height;
	}
	var left = [(Math.floor(MS.width/2) - 20), 'px'].join('');
	var top = [(Math.floor(MS.height/2) - 10), 'px'].join('');
	var load = null;
	if($('.'+MS.loadingClass).size() == 0) {
		load = $('<div></div>').attr('class', MS.loadingClass).css('left', left).html('加载中').appendTo($('#time-share'));
		load.css('top', top);
	}
};


function initDrawLine(){
	$('.chart-config .type-list li:first').click();
}
var pattern = /(\d{4})(\d{2})(\d{2})/;
function drawkline(d){
	var chart2 = new VKChart('vkContent2');
	chart2.options.dateTimeType='date';

	var data = {items:[]};
	$.each(d.Data[0], function(i, item) {
		var v = {
			"symbol": "XAGRMB",
			"barPeriodType": "M1"
			};
		v.barId = item[0];
		var formatDate = (item[0]/1000000).toString().replace(pattern, '$1-$2-$3');
		v.date = new Date(formatDate);
		v.open = item[2]/100;
		v.close = item[3]/100;
		v.low = item[5]/100;
		v.high = item[4]/100;
		v.volume = item[6];
		v.preclose=item[1]/100;
		data.items.push(v);
	});
	chart2.paint(data,VKChart.Type.kline);
} 
function formateCode(code){
	pf="";
	fstLetter=code.substring(0,1);
	if(fstLetter=="0"){
		pf="SZSE";
	}else if(fstLetter=="6"){
		pf="SSE";
	}else if(fstLetter=="3"){
		pf="SZSE";
	}
	return pf+code;
}
function drawKLine(code){
	start=new Date().format("yyyyMMdd");
	start=start+"000000";
	$.ajax({
		type: "get",
		url: 'http://webstock.quote.hermes.hexun.com/a/kline?code='+formateCode(code)+'&start='+start+'&number=-1000&type=5&callback=drawKline',
		dataType: 'jsonp'
	});
}

getParameter();
initDrawLine();
</script>

<script>
$(function(){
	$('.chart-config .type-list li').click(function(){
		$('.chart-config .type-list li').removeClass("select");
		$(this).addClass("select");
		$(".time-share").css("display","none");
		var _id=$(this).attr("id");
		if(_id=="time-li"){
			$("#time-share").css("display","block");
		}else if(_id=="day-li"){
			$("#vkContent2").css("display","block");
		}
	});
}); 

</script>
</body>
</html>
