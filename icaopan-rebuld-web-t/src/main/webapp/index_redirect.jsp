<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>来盯盘—只做为您股票投资助力的产品</title> 
<meta name="keywords" content="来盯盘,配资,在线配资,股票配资,配资炒股,借钱炒股,融资,杠杆 " />
<meta name="description" content="来盯盘为广大股民提供在线股票配资服务，配资炒股最高200万元，按月提供配资资金，1-3倍杠杆任您选择。公司覆盖股票、基金、期货、债券、银行等多种面向个人和企业的服务。您还在等什么，世界那么大，一起去赚赚！客服电话：4001-667-067" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link  href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var serverName="<%=request.getServerName()%>";
	var times=9;
	function fresh(){
		if(serverName=="www.gudaiquan.com"||serverName=="gudaiquan.com"){
			document.getElementById("commonDiv").style.display="block";
			setInterval(function(){
				if(times<1){
					location.href="http://www.icaopan.com";
				}else{
					document.getElementById("ses").innerHTML=times;
					times--;
				}
			}, 1000);
		}else{
			location.href="<%=basePath%>user/ViewIndex.do";
		}
	}
</script>
<body onload="javascript:fresh();">
	<div style="width: 600px;padding-top:100px;text-align: center;margin: 0 auto;display: none" id="commonDiv">
	</div>
</body>