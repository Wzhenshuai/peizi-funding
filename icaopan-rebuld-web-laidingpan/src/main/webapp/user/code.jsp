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
<title>来盯盘</title>
<link rel="icon" href="<%=basePath%>images/gdq.ico"
	mce_href="<%=basePath%>images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>images/gdq.ico"
	mce_href="<%=basePath%>images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icp-login.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/common.js"></script>
<script type="text/javascript">
	var systemConfig = {
	    path:"<%=path%>"
	};
</script>
<script type="text/javascript">
$(function(){
    $.ajax({
    	url:systemConfig.path + "/user/logParseRegist.ajax",
    	type:'post', 
    	data:{}, 
    	success:function(r){
    		var list = r.codeInfos;
			var html="<tr><td style='width: 117px'>时间</td><td>模板类型</td><td>手机号</td><td>验证码</td></tr>";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var registDate=formateDate(obj.registDate);
					var sourceInfo=obj.sourceInfo;
					var mobile=obj.mobile;
					var registCode=obj.registCode;
					html += "<tr class=";
					if (i % 2 == 0) {
						html += "odd>";
					} else {
						html += ">";
					}
					html+="<td><p>"+registDate+"</p></td>"
		              +"<td><p>"+sourceInfo+"</p></td>"
		              +"<td><p>"+mobile+"</p></td>"
		              +"<td><p>"+registCode+"</p></td>";
				}
			}
			$("#code_table").html(html);
		}
    });
})

</script>
</head>
<body>
	<table id = 'code_table' border="2" style="font-size:13px;width:500px;margin:0 auto;margin-top: 30px;text-align:center;">
	</table>
</body>
</html>
