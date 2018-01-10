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
<title>交割单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icaopan.css?<%= System.currentTimeMillis()%>" rel="stylesheet" type="text/css" />
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
				<div class="light-orange-tit light-white-tit zjmx-tit">
					<table>
						<tr><td width="80" class="txt-r">股票查询：</td><td width="150"><input type="text" name="textfield" id="stockCode" class="zjmx-option" /></td><td width="80" class="txt-r">业务：</td><td width="150">
							<select name="select3" id="tradeType" class="zjmx-option">
									<option value="">全部</option>
									<option value="0">证券买入</option>
									<option value="1">证券卖出</option>
									<option value="2">资金增加</option>
									<option value="3">资金减少</option>
									<option value="4">证券增加</option>
									<option value="5">证券减少</option>
							</select>
							</td><td rowspan="2"><a href="javascript:;" class="btn btn-blue MT36" onclick="MS.query_jgd_list('jgd-table',1,12)">查询</a></td></tr>
						<tr><td class="txt-r">起始日期：</td><td><input type="text" id="startDate" readonly="readonly" name="textfield2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" class="Wdate zjmx-option" /></td><td class="txt-r">结束日期：</td><td><input type="text" id="endDate" readonly="readonly" name="textfield2" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d}'})" class="Wdate zjmx-option" /></td></tr>
					</table>
				</div>
				<table class="S-wtjl jgd-table" id="jgd-table">
					<tr><th width="80">流水号</th><th width="110">操作时间</th><th width="90">业务</th><th width="100">证券名称</th><th width="80">成交量</th><th width="100">成交金额</th><th width="90">交易佣金</th><th width="80">印花税</th><th>其它费</th></tr>
				</table>
				<div class="txt-c MT42 digg"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-jgd.js?<%= System.currentTimeMillis()%>"></script>
</body>
</html>
