<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<title>股票交易</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icaopan.css?<%= System.currentTimeMillis()%>" rel="stylesheet" type="text/css" />
<link id="easyui_theme_id" rel="stylesheet" type="text/css"
	href="<%=basePath%>/js/plugins/jquery.easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/js/plugins/jquery.easyui/themes/icon.css">
<link rel="stylesheet" href="<%=basePath%>/css/main.css?<%= System.currentTimeMillis()%>">
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<style>
	div.nihao a{
		color:red;
		text-decoration: underline;
	}
	div.nihao a:hover{
		text-decoration: none;
		color:black;
	}
</style>
</head>
<body>
<div id="container">
<jsp:include page="../common/header.jsp" />
<!--主要内容-->
	<div id="main">
		<div class="wrap clearfix">
			<jsp:include page="wycg_menu.jsp" />
			<div class="main-cont fr">
				<div class="ac-title">
					<div style="float:left;"><h2 class="ac-title">股票买入</h2></div>
					<div style="float:right;padding-right:30px;font-size:14px;" class="nihao"><a  href='javascript:;' onclick="MS.queryStockAccount()">刷新资金</a></div>
				</div>
				<div style="clear:both"></div>
				<div>
				<ul id="wdcc-summary" class="clearfix">
					<li>股票市值<p id="marketValueDd">__</p></li>
					<li>持仓盈亏<p id="profitValueDd">__</p></li>
					<li>可用余额<p id="stockBalanceDd">__</p></li>
				</ul>
				</div>
				<div class="stocks-info-wrap MT16 clearfix">
				<div class="stocks-info fl">
					<table>
						<tr><td>股票代码：</td><td><input type="text" class="input" id="stockCode" value="${param.stockCode }" onblur='MS.buyStockCodeInputBlur("stockCode")' /></td></tr>
						<tr><td>股票简称：</td><td id="stockName"></td></tr>
						<tr><td>委托价格：</td><td><div id="wtjg-wrap"><input type="text" class="input" id="stockPrice" value="${param.price }" onblur='MS.buyPriceInputBlur()'/><a href="javascript:;" class="math-icon" id="minus-icon" onclick="MS.adjustPrice('minus','stockPrice',true)"><i></i></a><a href="javascript:;" class="math-icon" id="plus-icon" onclick="MS.adjustPrice('add','stockPrice',true)"><i></i></a></div></td></tr>
						<tr><td>最大可买：</td><td id="maxQuantity"></td></tr>
						<tr><td>委托数量：</td><td><input type="text" class="input" id="quantity" /></td></tr>
						<tr><td colspan='2' id="buyRate">
							<input type="radio" name="stocks-rate" id="all" /><label for="all">全部</label>
							<input type="radio" name="stocks-rate" id="harf" /><label for="harf">1/2</label>
							<input type="radio" name="stocks-rate" id="third" /><label for="third">1/3</label>
							<input type="radio" name="stocks-rate" id="quarter" /><label for="quarter">1/4</label>
						</td></tr>
						<tr>
						<td style="vertical-align: top;padding-top:0px">
							交易通道：
						</td>
						<td class="ntb">
							<table id="channelTB" class="channelTB">
								<c:forEach items="${channelList }" var="channel">
									<tr><td class="cb"><input class="channelCB" type="checkbox" value="${channel.id }"/></td>
										<td>
											<table class="channelTB">
												<tr><td>通道:</td><td>${channel.name }</td></tr>
												<tr><td>余额:</td><td>${channel.cashAvailable }</td></tr>
											</table>
										</td>
									</tr>
								</c:forEach>	
							</table>
						<tr><td colspan='2' class="error text-red ft12"></td></tr>
						<tr><td colspan="2"><span class="btn5 btn5-red" onclick="MS.doBuyPlacementStocksTrade()">买&nbsp;&nbsp;入</span></td></tr>
					</table>
				</div>
				<div class="stocks-detail fr">
					<table>
						<tr><th colspan='4' class="txt-l pad-l13"><span id="securityName">--</span>&nbsp;<span id="securityCode">--</span></th></tr>
						<tr><td rowspan='2' id="lastPrice" class="pad-l13" width="118">--</td><td width="75" id="dailyChange">--</td><td width="109">今开：<span id="openPrice">--</span></td><td width="105">昨收：<span id="preClosePrice">--</span></td></tr>
						<tr><td id="dailyChangePercent">--</td><td>最高：<span id="highPrice">--</span></td><td>最低：<span id="lowPrice">--</span></td></tr>
						<tr><td colspan="2" id="date" class="pad-l13">--</td><td>涨停：<span id="upLimit">--</span></td><td>跌停：<span id="downLimit">--</span></td></tr>
					</table>
					<div class="figure-table">
						<table>
							<tr><td width="45">买&nbsp;&nbsp;一：</td><td id="bprice1" class="price-select" width="75"></td><td width="75" id="bamount1">--</td><td width="45">卖&nbsp;&nbsp;一：</td><td id="sprice1" width="65" class="price-select"></td><td width="105" id="samount1">--</td></tr>
							<tr><td>买&nbsp;&nbsp;二：</td><td id="bprice2" class="price-select"></td><td id="bamount2">--</td><td>卖&nbsp;&nbsp;二：</td><td id="sprice2" class="price-select"></td><td id="samount2">--</td></tr>
							<tr><td>买&nbsp;&nbsp;三：</td><td id="bprice3" class="price-select"></td><td id="bamount3">--</td><td>卖&nbsp;&nbsp;三：</td><td id="sprice3" class="price-select"></td><td id="samount3">--</td></tr>
							<tr><td>买&nbsp;&nbsp;四：</td><td id="bprice4" class="price-select"></td><td id="bamount4">--</td><td>卖&nbsp;&nbsp;四：</td><td id="sprice4" class="price-select"></td><td id="samount4">--</td></tr>
							<tr><td>买&nbsp;&nbsp;五：</td><td id="bprice5" class="price-select"></td><td id="bamount5">--</td><td>卖&nbsp;&nbsp;五：</td><td id="sprice5" class="price-select"></td><td id="samount5">--</td></tr>
						</table>
						<div  class="easyui-tabs tabs-container easyui-fluid" id="tabs" style="width:410px;height:400px;float:right;margin-top:5px"> 
							<div title="分时" style="padding:10px" >
								<div id="time-share" style="width:388px; height:335px;">
								</div>
							</div>
							<div title="日k" style="padding:10px" >
								<div id="day-k" style="width:388px;height:335px;">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
				<div class="tab_plugin MT16">
					<ul class="wtjl-title tab_plugin_title clearfix">
						<li class="active" onclick="MS.loadCurrentDayPlacementListShowBtn('cancelPlacementTable',1,8)">委托</li>
						<li onclick="MS.queryHoldStockListFunction(1,8)">持仓</li>
					</ul>
					<div class="tab_plugin_list">
						<table class="S-wtjl" id="cancelPlacementTable">
							<tr><th width="119">股票简称</th><th width="100">类型</th><th width="140">委托价/数量</th><th width="140">成交价/数量</th><th width="145">成交总金额</th><th>状态</th><th></th></tr>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
					<div class="tab_plugin_list hide">
						<table class="S-wtjl" id="holdStockTable">
							<thead>
								<tr><th width="116">股票简称</th><th width="115">股票市值</th><th width="128">浮动盈亏/比例</th><th width="120">持股/可用</th><th width="104">现价</th><th width="98">成本价</th><th></th></tr>
							</thead>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="currentPrice" value="${param.price }" />
	<input type="hidden" id="lastChannelId" value="${param.channelId }" />
<!--主要内容结束-->
<jsp:include page="../common/footer.jsp" />
</div>
<script>	
$(function(){
	$(".aside-nav li a:contains('高级股票买入')").parent("li").addClass('active');
});
</script>
<!--离开页面停止轮询-->
<script type="text/javascript" src="<%=basePath%>/js/util/visibility.js"></script>
<!--主要离开页面停止轮询-->
<script type="text/javascript" src="<%=basePath%>js/util/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js?<%= System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-valide.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js?<%= System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=basePath%>js/raphael-min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/stock.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-mr-senior.js??<%= System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="<%=basePath%>/js/hidpi-canvas.js?k=4" ></script>
<script type="text/javascript" src="<%=basePath%>/js/moment.js"></script>
<script src="<%=basePath%>/js/plugins/jquery.easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>/js/plugins/jquery-cookie/jquery.cookie.js"></script>
<script src="<%=basePath%>/js/easyui-theme-change.js"></script>
<script src="<%=basePath%>/js/jquery-loading.js"></script>
<script src="<%=basePath%>/js/plugins/echarts-master/dist/echarts.min.js"></script>
<script src="<%=basePath%>/js/example/nkkline.js?<%= System.currentTimeMillis()%>"></script>
<script src="<%=basePath%>/js/example/nkktimeline.js?<%= System.currentTimeMillis()%>"></script>
<script>
$(function(){
	$('#tabs').tabs({
	    onSelect:function(title,index){
	    	var stockCode=MS.currentStockCode;
	        if(title=="分时"){
	        	queryTimeShare(stockCode, 'new', 'buy');
	        }else if(title="日k"){
	        	drawKLine(stockCode);
	        }
	    }
	});
	$("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
        	if($("div.pop").length > 0){
        		sureButtonClick();
        	}else{
        		if($("#stockCode").val()==""){
                	$("#stockCode").focus();
                }else if($("#stockPrice").val()==""){
                	$("#stockPrice").focus();
                }else if($("#quantity").val()==""){
                	$("#quantity").focus();
                }else{
                	MS.doBuyPlacementStocksTrade();
                }
        	}
            
        }
    });
});
</script>
</body>
</html>
