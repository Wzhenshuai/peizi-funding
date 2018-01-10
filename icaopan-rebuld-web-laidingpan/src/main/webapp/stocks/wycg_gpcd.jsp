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
<title>股票交易</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
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
			<div><!-- 这个空div用来设定table高度不要删除 -->
				<table id="gpcdTable" class="wycg-cctable S-wtjl">
					<thead>
						<tr><th width="100">股票简称</th><th width="115">委托时间</th><th width="128">类型</th><th width="120">委托价/数量</th><th width="120">成交数/可撤数</th><th width="100">状态</th><th></th></tr>
					</thead>
					<tbody></tbody>
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
<script type="text/javascript" src="<%=basePath%>js/util/icp-dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/wycg-cd.js"></script>
</body>
</html>
