<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
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
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账户</title>
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
			<h2 class="ac-title">安全信息</h2>
			<div>
				<table class="safety-center">
					<tr><td class="col1 identify"><i></i></td><td class="col2">实名认证</td>
					<td class="col3">
						<c:if test='${user.isVerifyIdentity==0}'>
				   		 	${user.realName}
						</c:if>
					</td>
					<c:choose>
						<c:when test="${user.isVerifyIdentity==0}">
							<td class="col3 col3-right"><i></i></td>
						</c:when>
						<c:otherwise>
							<td class="col3"><a class="inp-btn-n" href="<%=basePath%>account/wdzh_aqzx_smrz.jsp">去认证</a></td>
						</c:otherwise>
					</c:choose>
					</tr>
					<tr><td class="col1 mobile"><i></i></td><td>手机</td>
					<td>${user.mobileHidePart}</td>
					<c:choose>
						<c:when test="${user.isBindingMobile!=0||user.mobile==null}">
							<td class="col3"><i></i></td>
						</c:when>
						<c:otherwise>
							 <td class="col3 col3-right"><i></i></td>
						</c:otherwise>
					</c:choose>
					</tr>
					<tr><td class="col1 email"><i></i></td><td>邮箱</td>
					<c:choose>
						<c:when test="${user.isBindingMail!=0}">
							<td class="col3" id="email-verify" ></td>
							<td><span class="text-blue pointer" onclick="MA.bindEmail()">绑 定</span></td></tr>
						</c:when>
						<c:otherwise>
							<td>${user.emailHidePart}</td>
							<td><span class="text-blue pointer" onclick="MA.bindEmail()">修 改</span></td></tr>
						</c:otherwise>
					</c:choose>
					<tr><td class="col1 password"><i></i></td><td>登录密码</td>
					<c:choose>
						<c:when test="${empty user.loginPass }">
							<td class="col3"><i></i></td>
						</c:when>
						<c:otherwise>
							 <td class="col3"></td>
						</c:otherwise>
					</c:choose>
					<td><span class="text-blue pointer" onclick="MA.updateLoginPass()">修 改</span></td></tr>
				</table>
			</div>
		</div>
	</div>
</div>
<!--主要内容结束-->
<jsp:include page="../common/footer.jsp" />
</div>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-aqzx.js"></script>
</body>
</html>
