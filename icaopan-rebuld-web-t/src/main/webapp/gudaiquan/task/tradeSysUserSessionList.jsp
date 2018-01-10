<%@page import="com.common.util.StockSysUserSessionManage"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>来盯盘-任务管理</title>
<script type="text/javascript"
	src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script>
function cleanStockUserSession(isAll){
	var userName=$("#userName").val();
	$.ajax({
        type: "GET",
        url: "<%=basePath%>/user/cleanStockUserSession.ajax",
        data: {'isAll':isAll,'userName':userName},
        dataType: "json",
        success: function(data){
        	location.reload();
        }
    });
}
function updateSecurityModelList(){
	$.ajax({
        type: "GET",
        url: "<%=basePath%>/trade/updateSecurityModelList.ajax",
        dataType: "json",
        success: function(data){
        	alert(data.result);
        }
    });
}
</script>
</head>
<body>
    <div>
    	<h2>1.清除网站存储交易系统session</h2>
    	用户名：<input type="text" id="userName"/>
    	<input type="button" value="清除" onclick="cleanStockUserSession('single');"/>
    	<input type="button" value="清除所有" onclick="cleanStockUserSession('all');"/>
    </div>
	<div>
		<c:forEach var="item" items="<%=StockSysUserSessionManage.map%>">   
			${item.key} > ${item.value} <br>   
		</c:forEach>
		<h2>2.手动更新股票信息<a href="javascript:;" onclick="updateSecurityModelList()">更新</a></h2>
	</div>
</body>
</html>