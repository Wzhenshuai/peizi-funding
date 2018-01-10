<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<link href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp"></jsp:include>
	<!--主要内容-->
	<div id="main">
		<div class="wrap clearfix">
			<jsp:include page="wycg_menu.jsp" />
			<div class="main-cont fr">
				<div class="account-cont tab_plugin MT27">
					<ul class="wtjl-title tab_plugin_title clearfix">
						<li class="active">当日成交</li>
						<li>历史成交</li>
						<li>成交汇总</li>
					</ul>
					<div class="tab_plugin_list">
						<table class="S-wtjl" id="PlacementFillTable">
							<thead>
								<tr><th width="119">股票简称</th><th width="150">成交时间</th><th width="100">类型</th><th width="169">成交价</th><th width="145">成交量</th><th>成交金额</th></tr>
							</thead>
							<tbody></tbody>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
					<div class="tab_plugin_list hide">
						<div class="light-orange-tit light-white-tit zjmx-tit">
							<table>
								<tr><td width="80">股票查询：</td><td width="150"><input type="text" name="textfield" id="stockCode" class="zjmx-option" /></td><td width="80">委托方向：</td><td width="150">
									<select name="select3" id="tradeType" class="zjmx-option">
											<option value="">全部</option>
											<option value="BUY">买入</option>
											<option value="SELL">卖出</option>
									</select>
									</td><td rowspan="2"><a href="javascript:;" class="btn btn-blue MT36" onclick="MS.loadPlacementFillList('PlacementFillHistoryTable',1,12)">查询</a></td></tr>
								<tr><td>起始日期</td><td><input type="text" id="startDate" readonly="readonly" name="textfield2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" class="Wdate zjmx-option" /></td><td>结束日期</td><td><input type="text" id="endDate" readonly="readonly" name="textfield2" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="Wdate zjmx-option" /></td></tr>
							</table>
						</div>
						<table class="S-wtjl" id="PlacementFillHistoryTable">
							<thead>
								<tr><th width="119">股票简称</th><th width="150">成交时间</th><th width="100">类型</th><th width="169">成交价</th><th width="145">成交量</th><th>成交金额</th></tr>
							</thead>
							<tbody></tbody>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
					
					<!-- 汇总成交 -->
					<div class="tab_plugin_list hide">
							<div class="light-orange-tit light-white-tit zjmx-tit">
								<table>
									<tr><td width="80">股票查询：</td><td width="150"><input type="text" name="textfield" id="stockCodeS" class="zjmx-option" /></td><td width="80">委托方向：</td><td width="150">
										<select name="select3" id="tradeTypeS" class="zjmx-option">
												<option value="">全部</option>
												<option value="0">买入</option>
												<option value="1">卖出</option>
										</select>
										</td><td rowspan="2"><a href="javascript:;" class="btn btn-blue MT36" onclick="MS.loadPlacementFillSummaryList('PlacementFillSummaryTable',1,12)">查询</a></td></tr>
									<tr><td>起始日期</td><td><input type="text" id="startDateS" readonly="readonly" name="textfield2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDateS\')||\'%y-%M-%d\'}'})" class="Wdate zjmx-option" /></td><td>结束日期</td><td><input type="text" id="endDateS" readonly="readonly" name="textfield2" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDateS\')}',maxDate:'%y-%M-%d'})" class="Wdate zjmx-option" /></td></tr>
								</table>
							</div>
							<table class="S-wtjl" id="PlacementFillSummaryTable">
								<thead>
									<tr><th width="119">股票简称</th><th width="100">类型</th><th width="169">成交价</th><th width="145">成交量</th><th>成交金额</th><th>成交次数</th></tr>
								</thead>
								<tbody></tbody>
							</table>
							<div class="txt-c MT42 digg"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--主要内容结束-->
</div>
<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-cj.js"></script>
</body>
</html>