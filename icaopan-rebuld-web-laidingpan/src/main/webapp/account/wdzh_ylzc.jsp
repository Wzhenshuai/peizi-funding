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
					<h2 class="ac-title">盈利提取</h2>
					<div>
						<p class="orange-tit">盈利可提取金额：<strong>
						<c:choose>
							<c:when test="${empty maxTransferAmount }">
			                        	--
			                </c:when>
							<c:otherwise>
								<fmt:formatNumber type="number" value="${maxTransferAmount }" pattern="#,##0.00"></fmt:formatNumber>
								<input type="hidden" id="portfolioId" name="portfolioId" value="${portfolioId }"/>
								<input type="hidden" id="maxTransferAmount" name="maxTransferAmount" value="${maxTransferAmount }"/>
	                		</c:otherwise>
						</c:choose></strong>元</p>
						<div class="recharge-body">
							<p>提取金额：<input type="text" id="tradeAmountInput" class="recharge-input" />元</p>
							<div id="errorDiv" class="text-orange">&nbsp;</div>
							<a href="javascript:;" class="btn btn-orange-big MT27" id="ylzc" onclick="MA.ylzc()">提交</a>
						</div>
						<h2 class="ac-title ac-title-noBg">温馨提示</h2>
						<ul class="ac-wxts">
							<li>1. 转出盈利时，必须股票总资产 &gt; 总操盘资金 * 110% 时，才可以操作<br />
								A：股票账户可用余额 &gt; 股票总资产−总操盘资金 * 110% <br />
								盈利可转出金额=股票总资产−总操盘资金 * 110%<br />
								例如：股票总资产35000元，总操盘资金30000元，股票账户可用余额3000元，盈利可转出金额就为2000元<br />
								B：股票账户可用余额  &lt; 股票总资产−总操盘资金 * 110%， 盈利可转出金额 =股票账户可用余额 例如：股票总资产35000元，总操盘资金30000元，股票账户可用余额1000元，盈利可转出金额就为1000元</li>
							<li>2. 转出的盈利是从您的股票账户可用资金扣除，需要您的股票账户可用资金充足</li>
							<li>3. 如果您要转出股票账户中的全部资金，您需要先把持仓股票全部卖出，在“实盘项目”中，选择“终止项目”，完成全部转出</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	<!--主要内容结束-->
	<jsp:include page="../common/footer.jsp" />
</div>
<script type="text/javascript" src="<%=basePath%>/js/function/valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-ylzc.js"></script>
</body>
</html>
