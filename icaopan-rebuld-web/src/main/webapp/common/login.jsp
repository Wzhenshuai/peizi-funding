<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html">
<html>
<head>
<script type="text/javascript">

document.domain = "icaopan.com";
window.jsonp = {
		isLogin : false,
		systemConfig : "<%=path%>",
		logOut :"<%=basePath%>user/LogOut.do",
		login  :"<%=basePath%>user/login.jsp",
		register : "<%=basePath%>user/regist_one.jsp",
		center   :"<%=basePath%>user/ViewUserSecurityInfo.do",
		number :"${sessionScope.Toft_SessionKey_UserData.mobileHidePart }"		
}

</script>
<c:choose>
<c:when test="${!empty sessionScope.Toft_SessionKey_UserData }">
<script>window.jsonp.isLogin = true;</script></c:when></c:choose>
</head>
<body>
</body>
</html>

  