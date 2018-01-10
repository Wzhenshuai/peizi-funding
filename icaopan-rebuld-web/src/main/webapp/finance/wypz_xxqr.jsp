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
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实盘申请</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="stylesheet" href="css/icaopan.css" type="text/css"  />
<!--[if lt IE 9]><script src="<%=basePath%>/js/html5.js"></script><![endif]-->
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wypz.js"></script>

</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp" />
	<!--主要内容-->
	<div id="main">
       	<div class="wrap">
           	<div class="step">
               	<ul class="clearfix">
                	<li class="s1 two"><em>1</em><span>选择实盘方案</span></li>
                    <li class="s2 current"><em>2</em><span>确认实盘方案</span></li>
                    <li class="s3"><em>3</em><span>完成申请</span></li>
                </ul>
            </div>
			<!-- 表单隐藏区域 -->
			<input type="hidden" value="${applyFinancingVo.leftAmount }" id="leftAmount" />
			<input type="hidden" value="${applyFinancingVo.principal }" id="principal" />
			<input type="hidden" value="${applyFinancingVo.financingAmount }" id="financeAmount" />
			<input type="hidden" value="${applyFinancingVo.stockValue }" id="stockValue" />
			<input type="hidden" value="${applyFinancingVo.stockValueChange }" id="stockValueChange" />
			<input type="hidden" value="${applyFinancingVo.alertLine }" id="alertLine" />
			<input type="hidden" value="${applyFinancingVo.alertLineChange }" id="alertLineChange" />
			<input type="hidden" value="${applyFinancingVo.wrongLine }" id="wrongLine" />
			<input type="hidden" value="${applyFinancingVo.wrongLineChange }" id="wrongLineChange" />
			<input type="hidden" value="${applyFinancingVo.cycle }" id="cycle" />
			<input type="hidden" value="${applyFinancingVo.interest }" id="interest" />
			<input type="hidden" value="${applyFinancingVo.type }" id="type" />
			<input type="hidden" value="${applyFinancingVo.pzbs }" id="pzbs" />
            <p class="total">您本次需支付保证金<span class="name"><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.principal}"></fmt:formatNumber></span>元，系统使用费<span class="name"><fmt:formatNumber type="currency" pattern="#,##0.00" value="${applyFinancingVo.interestFirstMonth}"></fmt:formatNumber></span>元，共<em><span class="money"><fmt:formatNumber type="currency" pattern="#,##0.00" value="${applyFinancingVo.totalAmount}"></fmt:formatNumber></span></em>元</p>
            <div class="clearfix pad-b50">
            	<div class="half-l half-l-st2">
                	<div class="dash"></div>
                	<div class="pz-info">
                    	<h2>本次操盘信息</h2>
                        <dl>
                        	<dt>保证金：</dt><!--  class="justify" -->
                            <dd><strong><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.principal}"></fmt:formatNumber></strong>元</dd>
                        </dl>
                        <dl>
                        	<dt>申请金额：</dt>
                            <dd><strong><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.financingAmount}"></fmt:formatNumber></strong>元</dd>
                        </dl>
                        <dl>
                        	<dt>操盘金额合计：</dt>
                            <dd><strong><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.stockValue}"></fmt:formatNumber></strong>元</dd>
                        </dl>
                        <dl>
                        	<dt>实盘操作周期：</dt>
                            <dd><strong>${applyFinancingVo.cycle}</strong>个月</dd>
                        </dl>
                        <dl>
                        	<dt>开始交易日期：</dt>
                            <dd><time class="ft18"><fmt:formatDate value="${applyFinancingVo.startDate }" type="both" pattern="yyyy-MM-dd" /></time></dd>
                        </dl>
                    </div>
	            </div>
	            <div class="half-r half-r-st2">
	               	<div class="pz-info">
	                   	<h2>申请后操盘信息</h2>
                       <dl>
                       	<dt>现有操盘资金：</dt>
                           <dd><strong><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.totalStockBalance}"></fmt:formatNumber></strong>元</dd>
                       </dl>
                       <dl>
                       	<dt>申请后操盘资金：</dt>
                           <dd><strong class="txt-orange"><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.totalAvailiableAmount}"></fmt:formatNumber></strong>元</dd>
                       </dl>
                       <dl>
                       	<dt>亏损警告线：</dt>
                           <dd><strong class="txt-orange"><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.totalAlertLine}"></fmt:formatNumber></strong>元</dd>
                       </dl>
                       <dl>
                       	<dt>亏损平仓线：</dt>
                           <dd><strong class="txt-orange"><fmt:formatNumber type="currency" pattern="#,##0" value="${applyFinancingVo.totalWrongLine}"></fmt:formatNumber></strong>元</dd>
                       </dl>
                       <input type="button" class="btn btn-orange" id="confirm-pz" value="确认申请" />
                       <div class="pz-need">
                           <p class="verified hide">*实盘申请前需要完成实名认证<a href="<%=basePath%>/account/wdzh_aqzx_smrz.jsp" target="_blank">实名认证</a></p>
                           <p class="not-enough">*现金账户余额不足，本次实盘申请还差<span id="D-value">0</span>元<a href="<%=basePath%>/user/UserChargeInit.do" target="_blank" id="recharge-link">立即充值</a></p>
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
