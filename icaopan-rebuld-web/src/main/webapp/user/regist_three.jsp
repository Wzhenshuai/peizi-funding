<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
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
<title>i操盘</title>
<link  href="<%=basePath%>css/icp-login.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div class="regist-main">
		<div class="regist-main-con">
			<ul class="regist-step-list clearfix">
				<li><span>1</span>创建帐户</li>
				<li><span>2</span>实名认证</li>
				<li class="active last"><span>3</span>注册成功</li>
			</ul>
			<table class="regist-info" id="regist-step-three">
				<tr><th>恭喜您已经成功注册</th></tr>
				<tr><td align='center'><a class="btn btn-orange" href="<%=basePath%>user/viewMyIndex.do">进入我的i操盘</a></td></tr>
			</table>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</div>
</body>
</html>
