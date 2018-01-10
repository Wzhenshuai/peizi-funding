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
<script type="text/javascript" src="<%=basePath%>js/util/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/common.js"></script>
<script type="text/javascript">
	$(function(){
		queryStockAccount();
		setInterval(queryStockAccount, Configs.stock_balance_fresh_interval);
	});
	
</script>
</head>
<body>
	<h2 class="bt-h2">
		<span>股票账户</span>
	</h2>
	<div class="gpzh-left">
		<ul class="gpzh-ul">
			<li><img src="images/wycg_10.png" width="19px"/><span><span>总资产：</span><font id="sumAmountDd">--</font></span></li>
			<li><img src="images/wycg_20.png" width="19px"/><span>股票市值：</span><span><font id="marketValueDd">--</font></span></li>
			<li><img src="images/wycg_07.png" width="19px"/><span>持仓总盈亏：</span><span><font id="profitValueDd">--</font></span></li>
			<li><img src="images/wycg_21.png" width="19px"/><span>可用余额：</span><span><font id="stockBalanceDd">--</font></span></li>
		</ul>
		<font id="t0DayPositonAmountDd" style="display: none"></font>
	</div>
	<div class="gpzh-right right">
		<ul class="gpzh-ul-2">
			<li><span>亏损警告线<div class="tipWrapper" style="width: 15px"><img src="images/wycg_13.png"  onmouseover="javascript:showTips('当股票账户总资产触及或低于亏损警告线时，您将只能卖出股票，不能买入股票。',this)" onmouseout="closeTips()"/></div>：<font id="alertLineDd">--</font></span></li>
			<li><span>亏损平仓线<div class="tipWrapper" style="width: 15px"><img src="images/wycg_13.png"  onmouseover="javascript:showTips('当股票账户总资产触及或低于亏损平仓线时，您将不能买入或卖出股票，平台会对操盘账户内的证券采取强制平仓的操作。',this)" onmouseout="closeTips()"/></div>：<font id="wrongLineDd">--</font></span></li>
		</ul>
	</div>
</body>
</html>
