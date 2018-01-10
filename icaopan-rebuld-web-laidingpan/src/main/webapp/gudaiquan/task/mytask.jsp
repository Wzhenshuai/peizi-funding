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
<title>任务管理</title>
<script type="text/javascript"
	src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript">

function updateMarketData(){
	$.ajax({
        type: "GET",
        url: "<%=basePath%>/stock/updateMarketData.ajax",
        dataType: "json",
        success: function(data){
       	 console.log(data);
        }
    });
	}
setInterval(updateMarketData, 6000);
</script>
</head>
<body>
	<!--  -->
	<div class="content">
		<div class="title">
			<h1>任务列表</h1>
		</div>
		<div class="taskList">
			<ul>
				<li><label>手动更新股票行情信息：</label>
				<button onclick="javascript:updateMarketData()">更新</button>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>