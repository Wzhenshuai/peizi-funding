<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>i操盘--人才招聘</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<link href="<%=basePath%>/css/icaopan.css" rel="stylesheet" type="text/css">
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>

<body>
<div id="container">
<jsp:include page="../common/header.jsp" />
<!--关于我们-->
<div id="main">
<div class="job">
	<h2>热招职位</h2>
    <p><a href="http://www.lagou.com/jobs/403808.html" target="_blank">java开发工程师</a></p>
    <p><a href="http://www.lagou.com/jobs/404022.html" target="_blank">iOS开发工程师</a></p>
    <p><a href="http://www.lagou.com/jobs/783446.html" target="_blank">性能测试工程师</a></p>
    <p><a href="http://www.lagou.com/jobs/752116.html" target="_blank">业务拓展经理/BD</a></p>
    <p><a href="http://www.lagou.com/jobs/597949.html" target="_blank">清算专员</a></p>
    <p><a href="http://www.lagou.com/jobs/598309.html" target="_blank">行政前台</a></p>
  
  <br><br><br><br><br><br><br><br><br><br>
</div>
</div>
<!--法律声明结束-->
<jsp:include page="../common/footer.jsp"></jsp:include>
</div>
</body>
</html>