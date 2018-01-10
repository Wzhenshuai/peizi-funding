<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>通道历史委托编号(${placementId})明细</title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>通道历史委托编号(${placementId})明细
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="row">
					<input type="hidden" name="placementId" value="${placementId}">
					<div class="col-md-2 pull-right text-right">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-refresh"></i>刷新
						</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="channelTable"
					ajaxURI="${ctx}/channelPlacement/placementHistoryFind"
				    columnNames="placementId,channelName,userName,securityCode,securityName,sideStr,quantity,price,fillQuantity,fillAmount,fillPrice,statusStr,customerName,placementCode,rejectMessage,dateTimeStr"
				    headerNames="用户委托ID,通道,用户名,证券代码,证券名称,委托方向,委托数量,委托价格,成交数量,成交金额,成交价格,状态,资金方,委托编号,拒绝原因,委托日期"
					searchInputNames="placementId" />
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
</body>
</html>



