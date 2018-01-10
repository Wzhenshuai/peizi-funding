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
<script type="text/javascript" src="<%=basePath%>js/function/common.js"></script>

</head>
<body>
<div id='time-share' class="time-share time-share-none"></div>
<script type="text/javascript" src="<%=basePath%>js/raphael-min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/stock.js"></script>	
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

getParameter();

</script>
</body>
</html>
