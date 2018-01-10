<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>股票交易</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<title>委托记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp"></jsp:include>
	<!--主要内容开始-->
	<div id="main">
		<div class="wrap clearfix">
			<jsp:include page="wycg_menu.jsp" />
			<div class="main-cont fr">
				<h2 class="ac-title">委托记录</h2>
				<div class="account-cont tab_plugin MT27">
					<ul class="wtjl-title tab_plugin_title clearfix">
						<li class="active">当日委托</li>
						<li>历史委托</li>
					</ul>
					<div class="tab_plugin_list">
						<table class="S-wtjl" id="currentDayPlacementTable">
							<tr><th width="119">股票简称</th><th width="100">类型</th><th width="169">委托价/数量</th><th width="150">成交价/数量</th><th width="145">成交总金额</th><th>状态</th></tr>
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
									</td><td rowspan="2"><a href="javascript:;" class="btn btn-blue MT36" onclick="MS.loadHistoryPlacementList('historyPlacementTable',1,12)">查询</a></td></tr>
								<tr><td>起始日期</td><td><input type="text" id="startDate" readonly="readonly" name="textfield2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" class="Wdate zjmx-option" /></td><td>结束日期</td><td><input type="text" id="endDate" readonly="readonly" name="textfield2" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="Wdate zjmx-option" /></td></tr>
							</table>
						</div>
						<table class="S-wtjl" id="historyPlacementTable">
							<tr><th width="119">股票简称</th><th width="100">类型</th><th width="169">委托价/数量</th><th width="150">成交价/数量</th><th width="145">成交总金额</th><th>状态</th></tr>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--主要内容结束-->
	<jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=basePath%>/js/util/visibility.js"></script>
<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-wt.js"></script>
</body>
</html>
