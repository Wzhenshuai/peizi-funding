<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
<div id="container">
<jsp:include page="../common/header.jsp" />

<!--主要内容-->
<div id="main">
	<div class="wrap clearfix">
		<jsp:include page="wycg_menu.jsp" />
		<div class="main-cont fr">
			<ul class="index-list clearfix">
				<li id="shang-index"><p class="index-name">上证指数</p><p class="big-num">__</p><div class="small-num"><p class="value">__</p><p class="rate">__</p></div></li>
				<li id="shen-index"><p class="index-name">深证指数</p><p class="big-num">__</p><div class="small-num"><p class="value">__</p><p class="rate">__</p></div></li>
				<li id="cyb-index"><p class="index-name">创业板</p><p class="big-num">__</p><div class="small-num"><p class="value">__</p><p class="rate">__</p></div></li>
			</ul>
			<h2 class="ac-title MT27">账户总资产：<span style="padding-right: 80px" id="sumAmountDd" class="ft22"></span></h2>
			<ul id="wycg-summary" class="clearfix">
				<li>可用余额<p id="stockBalanceDd">__</p></li>
				<li>股票市值<p id="marketValueDd">__</p></li>
				<li>持仓盈亏<p id="profitValue">__</p></li>
				<li>冻结金额<p id="stockBalanceDJ">__</p></li>
				<li>亏损警告线<em class="question question2" tips="当股票账户总资产触及或低于亏损警告线时，您将只能卖出股票，不能买入股票。">?</em><p id="alertLineDd">__</p></li>
				<!--  <li>净资产<em class="question question2" tips="当前股票账户的保证金及盈亏总计">?</em><p id="pureAmount">__</p></li>-->
				<li>亏损平仓线<em class="question question2" tips="当股票账户总资产触及或低于亏损平仓线时，您将不能买入或卖出股票，您账户内的证券将在交易时间被强制平仓，平台不收取平仓违约金。">?</em><p id="wrongLineDd">__</p></li> 
			</ul>
			<h2 class="rel wycg-title MT12">持仓<a href="<%=basePath%>trade/QueryHoldStocksInit.do">查看更多</a></h2>
			<table class="S-wtjl wycg-cctable" id="holdStockTable">
				<tr><th width="116">股票简称</th><th width="115">股票市值</th><th width="128">浮动盈亏/比例</th><th width="120">持股/可用</th><th width="104">现价</th><th width="98">成本价</th><th></th></tr>
			</table>
		</div>
	</div>
</div>
</div>
<!--离开页面停止轮询-->
<script type="text/javascript" src="<%=basePath%>/js/util/visibility.js"></script>
<!--主要离开页面停止轮询-->

<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-index.js?<%= System.currentTimeMillis()%>"></script>
</body>
</html>
