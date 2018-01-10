<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
   	<ul>
	<!--     <li onclick="javascript:void(0)"><img src="images/wdzh.png" />我的账户</li> -->
	    <li onclick="location.href='<%=basePath%>user/UserChargeInit.do'"><img src="images/wdzh-cz.png" /><span>我要充值</span></li>
	    <li onclick="location.href='<%=basePath%>user/UserWithDrawInit.do'"><img src="images/wdzh-tx.png" /><span>我要提现</span></li>
	    <li onclick="location.href='<%=basePath%>finance/FinancingAcctInOutInit.do?tradeInOut=in'"><img src="images/wdzh-gszr.png" /><span>追加保证金</span></li>
	    <li onclick="location.href='<%=basePath%>finance/FinancingAcctInOutInit.do?tradeInOut=out'"><img src="images/wdzh-ylzc.png" /><span>盈利转出</span></li>
		<li onclick="location.href='<%=basePath%>user/QueryPrincipalAndPositonChangeInit.do'"><img src="images/wdzh-zjmx.png" /><span>资金明细</span></li>
	    <li onclick="location.href='<%=basePath%>finance/ViewFinancingHistory.do'"><img src="images/wdzh-wdpz.png" /><span>我的配资</span></li>
	    <li onclick="location.href='<%=basePath%>user/ViewUserSecurityInfo.do'"><img src="images/wdzh-aqzx.png" /><span>安全中心</span></li>         
     </ul>
     <input id="stockCodeFromParam" type="hidden" value="${param.stockCode }"/>
     <input id="priceFromParam" type="hidden" value="${param.price }"/>
     <input id="pzType" type="hidden" value="${param.type }"/>
     <div id="account_mod_div" style="display:none">
	<ul>
		<c:forEach items="${portList }" var ="p" varStatus="sta">
			<li portid="${p.portfolioId }" order="${sta.index }" class="actived">
				<c:choose>
					<c:when test="${p.type==0 }">
						固定利率账户
					</c:when>
					<c:otherwise>
						分成项目账户
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>
	</ul>
</div>
</body>
</html>
