<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>盘前未通过测试单的账户</title>
		<meta name="decorator" content="default"></meta>
		<script type="text/javascript" src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>盘前未通过测试单的用户
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- table 数据展示 start -->
				<table:table id="channelTable"
					ajaxURI="${ctx}/channel/findUnchecked_AM"
					columnNames="name,code,cashAvailable"
					headerNames="通道名称,账号,可用"/>
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
</body>
</html>



