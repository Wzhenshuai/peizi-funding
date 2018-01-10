<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
			<h2 class="ac-title">追加保证金</h2>
			<div>
				<p class="orange-tit">现金账户余额：<strong id="availableAmountDd">--</strong>元</p>
				<div class="recharge-body">
        	  		<input type="hidden" id="leftmoney" name="leftmoney" value="${leftmoney }" />
        	  		<input type="hidden" id="portfolioId" name="portfolioId" value="${portfolioId }" />
					<p>追加金额：<input type="text" id="recharge-input" class="recharge-input" />元</p>
					<div id="errorDiv" class="text-orange">&nbsp;</div>
					<a href="javascript:;" class="btn btn-orange-big MT27" onclick="MA.gszr()">提交</a>
				</div>
				<h2 class="ac-title ac-title-noBg">温馨提示</h2>
				<ul class="ac-wxts">
					<li>1. 当前账户必须有操盘项目，才可以追加保证金</li>
					<li>2. 当您的股票账户达到亏损警告线、亏损平仓线，应及时追加保证金，避免系统对您的账户强行平仓</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!--主要内容结束-->
<jsp:include page="../common/footer.jsp" />
</div>
<script type="text/javascript" src="<%=basePath%>js/function/valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-bzj.js"></script>
</body>
</html>
