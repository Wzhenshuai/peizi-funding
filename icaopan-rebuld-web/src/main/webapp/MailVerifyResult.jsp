<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>i操盘</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<style>
    a:link{
    text-decoration: underline;
    }
	a:hover{
	text-decoration: none;
	}
</style>
</head>
<body >
<div style="color: #475058;font-family: '微软雅黑';font-size: 15px;line-height: 20px;text-align: center;padding-top:100px">
	${tipInfo }&nbsp;&nbsp;&nbsp;
	<a href="user/ViewUserSecurityInfo.do" style="color:green;">返回i操盘</a>
</div>
</body>
</html>