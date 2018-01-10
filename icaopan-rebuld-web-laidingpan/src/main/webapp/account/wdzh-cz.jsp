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
<style>
.zhcc-table-2{margin-top:24px;}
.zhcc-table-2 td{height:32px;}
</style>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp" />
<script type="text/javascript" src="<%=basePath%>js/function/icp-valide.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-cz.js"></script>
	<!--主要内容-->
	<div id="main">
		<div class="wrap clearfix">
			<jsp:include page="wdzh_menu.jsp" />
			<div class="account-cont main-cont fr">
				<h2 class="ac-title">我要充值</h2>
				<input id="isDevelop" value="${isDevelop }" type="hidden"/>
				<div>
					<p class="orange-tit">现金账户余额：<strong id="availableAmountDd">--</strong>元</p>
					<div class="tab_plugin MT12">
						<ul class="tab_plugin_title recharge-trigger clearfix">
							<!-- <li>网银充值</li>
							<li>支付宝充值</li> -->
							<li class="active">银行转账</li>
						</ul>
						<div class="recharge-wrap">
							<div class="tab_plugin_list" id="bank-transfer">
								<div class="recharge-content">
									<p class="recharge-subtitle">银行转账--请务必把您的<strong class="text-orange">注册手机号</strong>填写在转账备注或用途；</p>
									<p class="recharge-tips">您可以通过银行柜台向转账</p>
								</div>
								<h2 class="ac-title ac-title-noBg">温馨提示</h2>
								<ul class="ac-wxts">
									<!-- <li>1. 推荐使用网银充值。因为银行转账不是即时到账，我们处理需要一些时间，有可能处理不及时会导致您的实盘方案被系统平仓。</li> -->
									<li>转账时请务必把您<span class="text-red">注册时手机号</span>填写在转账备注或用途里，以便我们充值到您对应的账号里。</li>
								</ul>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<!--主要内容结束-->
	<jsp:include page="../common/footer.jsp" />
</div>
</body>
</html>
