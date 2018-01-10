<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
<div class="login-header">
	<div class="login-header-con">
    	<%-- <div class="left"><a href="javascript:;"><img src="<%=basePath%>images/login_logo.gif" class="left"></a></img><div class="left zc">重置登陆密码</div>
    	</div> --%>
        <div class="right login-header-con-r"><a href="<%=basePath%>user/login.jsp">登录</a><%-- |<a href="<%=basePath%>user/regist_one.jsp">注册</a>  --%><img src="<%=basePath%>images/phones.png" width="12" height="12" style=" position:relative; top:2px; margin-right:5px; padding-left:20px;" /><!-- <span>客服电话：<span class="color-yellow">4001-667-067</span></span> --></div>
    </div>
</div>
<script src="https://kefu.qycn.com/vclient/state.php?webid=102673" language="javascript" type="text/javascript"></script>
</body>
</html>