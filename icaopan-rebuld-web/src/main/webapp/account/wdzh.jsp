<%@page import="com.common.util.ServerContextUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账户</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />

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
		<div class="wdzh-cont fr">
			<table class="baseInfo">
				<tr><th class="wdzh-icon col1"></th><th class="col2 txt-l">账号：<span class="" id="login-user"></span></th><th class="col3"></th><th class="col4 txt-l">姓名：<span id="realName"></span></th></tr>
				<tr><td class="row1 row1col1"><i></i></td><td>身份信息</td><td class="row1col3"><i></i></td><td>银行卡</td></tr>
				<tr><td class="row2"></td><td><i class="verify_icon"></i><span id="verify_status"></span></td><td></td><td><span class="yhk-num" id="yhk-num">-</span>张</td></tr>
				<tr><td class="row3"></td><td><span class="login-account"></span><span id="idNumber"></span></td><td></td><td><a href="<%=basePath%>user/UserWithDrawInit.do#card_manage" class="btn btn-orange">添加</a></td></tr>
			</table>
			<div class="part">
				<p class="ac-title">现金账户</p>
				<table class="xjzh">
					<tr><th width="33%">总资产</th><th width="34%">可用余额</th><th>冻结资产</th></tr>
					<tr><td id="totalAmountDd"></td><td id="availableAmountDd"></td><td class="text-black" id="frzoneAmountDd"></td></tr>
				</table>
			</div>
			<div class="part">
				<p class="ac-title">股票账户</p>
				<table class="gpzh">
					<tr><td class="txt-r" width="120">总资产：</td><td id="sumAmountDd" width="148" class="ft18"></td><td class="txt-r" width="120">可用余额：</td><td class="ft18" id="stockBalanceDd" width="140"></td><td class="txt-r" width="120">净资产：</td><td id="pureAmount" width="147" class="ft18"></td></tr>
					<tr><td class="txt-r">股票市值：</td><td id="marketValueDd" class="ft18"></td><td class="txt-r">持仓盈亏：</td><td id="profitValue" class="ft18"></td><td class="txt-r">冻结资金<em class="question question2" tips="盈利提取审核资金冻结、停牌股保证金冻结">?</em>：</td><td id="frozenAmount" class="ft18"></td></tr>
				</table>
			</div>
		</div>
	</div>
</div>
<!--主要内容结束-->
<jsp:include page="../common/footer.jsp" />
</div>
<!--离开页面停止轮询-->
<script type="text/javascript" src="<%=basePath%>/js/util/visibility.js"></script>
<!--主要离开页面停止轮询-->
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh.js"></script>
</body>
</html>
