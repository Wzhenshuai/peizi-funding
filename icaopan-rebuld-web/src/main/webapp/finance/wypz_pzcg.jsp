<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实盘申请</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link  href="<%=basePath%>/css/icaopan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//导航焦点态
	$('#main-nav li:eq(2)').addClass('current');
});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="../common/header.jsp" />
		
		<!--主要内容-->
		<div id="main">
			<div class="wrap">
		    	<div class="step">
		        	<ul class="clearfix">
		            	<li class="s1"><em>1</em><span>选择实盘方案</span></li>
		                <li class="s2 three"><em>2</em><span>确认实盘方案</span></li>
		                <li class="s3 current"><em>3</em><span>完成申请</span></li>
		            </ul>
		        </div>
		        <div class="success">
		        	<h3>
		            	<img src="images/i-binggo.png" alt="Binggo" /><span>实盘申请成功，您的申请号为: <em>${detailId }</em>，实盘申请审核通过后，我们将短信通知您</span>
		            </h3>
		            <p>交易日申请，当日完成审核，非交易日申请，将在下一交易日审核完成。</p>
		            <div class="ft"><a href="<%=basePath%>/account/wdzh_wdpz.jsp" class="btn btn-orange">进入实盘申请详情</a></div>
		        </div>
		    </div>
		</div>
		<!--主要内容结束-->
		
		<jsp:include page="../common/footer.jsp" />
	</div>
</body>
</html>
