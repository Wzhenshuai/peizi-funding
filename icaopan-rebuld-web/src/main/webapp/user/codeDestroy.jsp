<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" import="java.security.SecureRandom"%>
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
<link rel="icon" href="<%=basePath%>images/gdq.ico"
	mce_href="<%=basePath%>images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>images/gdq.ico"
	mce_href="<%=basePath%>images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icp-login.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/common.js"></script>
<script type="text/javascript">
	var systemConfig = {
	    path:"<%=path%>"
	};
</script>
<script type="text/javascript">
	function disable(data) {
		var data = data;
		$.ajax({
			url : systemConfig.path + "/user/logParseRegistDisable.ajax",
			type : 'post',
			data : {
				disable :data
			},
			success : function(r) {
				if (r.resCode) {
					alert('操作成功');
				}
			}
		});
	}
	function codeDestroy() {
		$.ajax({
			url : systemConfig.path + "/user/logParseRegistDestroy.ajax",
			type : 'post',
			data : {},
			success : function(r) {
				if (r.resCode) {
					alert('操作成功');
				}
			}
		});
	}
</script>
</head>
<body>
<table border="2" style="font-size:13px;width:500px;margin:0 auto;margin-top: 30px;text-align:center;height: 200px;">
	<tr><td>禁用查看code页面：</td><td><input type="button" value='禁用' onclick="disable(false);" /></td></tr>
	<tr><td>启用查看code页面：</td><td><input type="button" value='启用' onclick="disable(true);" /></td></tr>
	<tr><td>清空数据（清除以后只能看到之后的数据）：</td><td><input type="button" value='清空缓存' onclick="codeDestroy();" /></td></tr>
</table>
</body>
</html>
