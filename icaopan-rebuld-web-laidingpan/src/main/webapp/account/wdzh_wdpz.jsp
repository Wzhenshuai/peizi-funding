<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账户</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link  href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp" />
	<!--主要内容-->
	<div id="main">
		<div class="wrap clearfix">
			<jsp:include page="wdzh_menu.jsp" />
			<div class="account-cont main-cont fr">
				<h2 class="ac-title">实盘项目</h2>
				<div>
					<table id="wdpz">
						<tr><th class="col1 txt-l">申请时间</th><th class="col2">结束时间</th><th class="col3">编号</th><th class="col4">保证金</th><th class="col5">申请金额</th><th class="col6">月系统使用费率</th><th class="col7">状态</th><th>操作</th></tr>
					</table>
					<div class="txt-c MT42 digg"></div>
				</div>
				<input type="hidden" id="circle" value='' />
			</div>
		</div>
	</div>
	<!--主要内容结束-->
	<jsp:include page="../common/footer.jsp" />
</div>
<script type="text/javascript" src="<%=basePath%>/js/util/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-pz.js"></script>
</body>
</html>
