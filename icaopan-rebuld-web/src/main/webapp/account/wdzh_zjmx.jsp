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
				<div class="account-cont main-cont tab_plugin fr">
					<ul class="zjmx-title tab_plugin_title clearfix">
						<li class="active">资金明细<i></i></li>
						<!-- <li>冻结明细<i></i></li> -->
					</ul>
					<div class="tab_plugin_list">
						<div class="light-orange-tit zjmx-tit">
							<table>
								<tr><td width="66">交易帐户</td><td width="149">
									<select class="zjmx-option" id="tradeAccountType">
										<option value="">请选择</option>
								      	<!-- <option value="acct">现金账户</option> -->
								      	<option value="stock">股票账户</option>
								    </select></td>
								    <td width="66">交易类型</td>
								    <td width="149">
									    <select class="zjmx-option" id="tradeType">
									    	<option></option>
									    </select>
								    </td>
								    <td rowspan="2"><a href="javascript:;" class="btn btn-blue MT36" onclick="MA.queryPrincipalChangeRecord('principalChangeTable',1,12)">查询</a></td>
								</tr>
								<tr><td>起始日期</td><td><input type="text" id="startDate" readonly="readonly" name="textfield2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" class="Wdate zjmx-option" /></td><td>结束日期</td><td><input type="text" id="endDate"  readonly="readonly" name="textfield2" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="Wdate zjmx-option" /></td></tr>
							</table>
						</div>
						<table class="zjmx-djlb" id="principalChangeTable">
							<tr><th width="20"></th><th width="168">发生时间</th><th width="157">发生种类</th><th width="157">转入</th><th width="157">转出</th><th>账户类型</th></tr>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
					<div class="tab_plugin_list hide">
						<div class="light-orange-tit zjmx-jd-tit">冻结总金额：<span id="frozenAmount">0</span>元</div>
						<table class="zjmx-djlb" width="100%" id="frozenTable">
							<thead>
								<tr><th width="30"></th><th>冻结时间</th><th>账户类型</th><th>冻结种类</th><th>冻结金额</th><th>备注</th></tr>
							</thead>
							<tbody></tbody>
						</table>
						<div class="txt-c MT42 digg"></div>
					</div>
				</div>
			</div>
		</div>
		<!--主要内容结束-->
		<jsp:include page="../common/footer.jsp" />
	</div>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-mx.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/datePicker/WdatePicker.js"></script>
</body>
</html>
