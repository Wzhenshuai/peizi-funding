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
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icaopan.css?<%= System.currentTimeMillis()%>" rel="stylesheet" type="text/css" />
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
				<ul id="wdcc-summary" class="clearfix">
					<li>股票市值:<p id="wdcc-gpsz">__</p></li>
					<li>持仓盈亏:<p id="wdcc-ccyk">__</p></li>
					<li>可用余额:<p id="wdcc-kyye">__</p></li>
				</ul>
				<div style="margin-top:5px"><!-- 这个空div用来设定table高度不要删除 -->
					<table id="wdccTable" class="wycg-cctable S-wtjl">
						<tr><th width="30"></th><th width="96">股票简称</th><th width="115">股票市值</th><th width="128">浮动盈亏/比例</th><th width="90">持股/可用</th><th width="90">现价</th><th width="90">成本价</th><th></th></tr>
					</table>
					<div class="txt-c MT42 digg"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=basePath%>/js/util/visibility.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-cc.js?<%= System.currentTimeMillis()%>"></script>
</body>
</html>
