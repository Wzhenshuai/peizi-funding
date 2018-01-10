<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<link rel="icon" href="<%=basePath%>/images/gdq.ico"
	mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico"
	mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link
	href="<%=basePath%>css/icaopan.css?<%=System.currentTimeMillis()%>"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
	<div id="container">
		<jsp:include page="../common/header_snapshot.jsp" />
		<!--主要内容-->
		<div id="main">
			<div class="wrap clearfix">
				<jsp:include page="wycg_menu_snapshot.jsp" />
				<div class="main-cont fr">
					<ul class="index-list clearfix">
						<li id="shang-index"><p class="index-name">上证指数</p>
							<p class="big-num">__</p>
							<div class="small-num">
								<p class="value">__</p>
								<p class="rate">__</p>
							</div></li>
						<li id="shen-index"><p class="index-name">深证指数</p>
							<p class="big-num">__</p>
							<div class="small-num">
								<p class="value">__</p>
								<p class="rate">__</p>
							</div></li>
						<li id="cyb-index"><p class="index-name">创业板</p>
							<p class="big-num">__</p>
							<div class="small-num">
								<p class="value">__</p>
								<p class="rate">__</p>
							</div></li>
					</ul>
					<h2 class="ac-title MT27">
						账户总资产：<span style="padding-right: 80px" id="sumAmountDd"
							class="ft22">${param.totalValue }</span><span
							style="padding-left: 80px"></span>
					</h2>
					<ul id="wycg-summary" class="clearfix">
						<li>可用余额
							<p id="stockBalanceDd">${param.available }</p>
						</li>
						<li>冻结金额
							<p id="stockBalanceDJ">0.00</p>
						</li>
						<li>亏损警告线<em class="question question2"
							tips="当股票账户总资产触及或低于亏损警告线时，您将只能卖出股票，不能买入股票。">?</em>
							<p id="alertLineDd">0.00</p></li>
						<li>股票市值：<em class="question question2"></em>
							<p id="pureAmount">${param.marketValue }</p></li>
						<li>持仓盈亏
							<p id="profitValue">${param.marketProfit}</p>
						</li>
						<li>亏损平仓线<em class="question question2"
							tips="当股票账户总资产触及或低于亏损平仓线时，您将不能买入或卖出股票，您账户内的证券将在交易时间被强制平仓，平台不收取平仓违约金。">?</em>
							<p id="wrongLineDd">0.00</p></li>
					</ul>
					<h2 class="rel wycg-title MT12">
						持仓<a href="javascript:;">查看更多</a>
					</h2>
					<table class="S-wtjl wycg-cctable" id="holdStockTable">
						<tr>
							<th width="116">股票简称</th>
							<th width="115">股票市值</th>
							<th width="128">浮动盈亏</th>
							<th width="120">持股/可用</th>
							<th width="104">现价</th>
							<th width="98">成本价</th>
						</tr>
						<tr>
							<td><p><%String sN=request.getParameter("securityName");sN=new String(sN.getBytes("iso8859-1"),"UTF-8");out.print(sN); %></p>
								<p>${param.securityCode}</p></td>
							<td>${param.marketValue}</td>
							<td><p>${param.marketProfit}</p>
								<%-- <p>${param.marketProfitPercent}</p> --%></td>
							<td><p>${param.positionAmount}</p>
								<p>${param.t0PlacementQuantity}</p></td>
							<td>${param.latestPrice}</td>
							<td>${param.costPrice}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<!--主要内容结束-->
		<jsp:include page="../common/footer.jsp" />
	</div>
	<!--离开页面停止轮询-->
	<script type="text/javascript"
		src="<%=basePath%>/js/util/visibility.js"></script>
	<!--主要离开页面停止轮询-->

	<script type="text/javascript"
		src="<%=basePath%>js/util/icp-dealmoney.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/function/icp-common.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/util/util-common.js"></script>
	<script type="text/javascript">
		function jsoncallback(e) {
			for (var i = 0; i < 3; i++) {
				var _node = $('.index-list li:eq(' + i + ')');
				if (e.info.result[i]) {
					if (e.info.result[i].dailyChangePercent > 0) {
						_node.removeClass('text-green').addClass('text-red');
					} else if (e.info.result[i].dailyChangePercent < 0) {
						_node.removeClass('text-red').addClass('text-green');
					}
					_node.find('.big-num').html(
							(e.info.result[i].lastPrice).toFixed(3));
					_node.find('.value').html(
							e.info.result[i].dailyChange.toFixed(2));
					_node.find('.rate').html(
							(e.info.result[i].dailyChangePercent * 100)
									.toFixed(2)
									+ '%');
				}
			}
		}
		function queryIndexInfo() {
			$
					.ajax({
						url : "//md.icaopan.com/openapi/queryUserCustomizeMarketDataList",
						type : 'post',
						dataType : 'jsonp',
						data : {
							'stockCodes' : '000001.SH,399001.SZ,399006.SZ',
							'is_web' : '1'
						}
					});
		};
		$(function() {
			queryIndexInfo();
		});
	</script>
</body>
</html>
