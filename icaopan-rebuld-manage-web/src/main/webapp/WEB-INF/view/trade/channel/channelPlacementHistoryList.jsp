<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
<head>
<title>通道历史委托查询</title>
<meta name="decorator" content="default"></meta>
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>通道历史委托查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="container-fuild">
					<div class="row">
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">证券代码</span> <input
									type="text" class="form-control" name="securityCode"
									id="securityCode-search" placeholder="证券代码" />
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">用户名</span> <input
									type="text" class="form-control" name="userName" id="user-search"
									placeholder="用户名称" />
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon3">通道</span> <select
									class="form-control" name="channelId">
									<option value="">请选择</option> ${fns:getChanelOpt()}
								</select>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-status">状态</span>
								<select class="form-control" name="status" id="status-search">
									<option value="">请选择</option>
									${fns:getDicOptions("trade_status")}
								</select>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-start">开始日期</span> <input
									type="text" class="form-control" name="startTime"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'date-search-end\')}'})"
									id="date-search-start" placeholder="开始日期" />
							</div>
						</div>
						<div class="col-md-2 col-lg2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-end">结束日期</span> <input
									type="text" class="form-control" name="endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'date-search-start\')}',maxDate:'2120-10-01'})"
									id="date-search-end" placeholder="结束日期" />
							</div>
						</div>
						<div class="col-md-2 pull-right text-right">
							<button class="btn btn-danger" onclick="reloadTableData();">
								<i class="glyphicon glyphicon-search"></i>查询
							</button>
							<button class="btn btn-danger"
								onclick="exportExcel('${ctx }/channelPlacement/exportChannelHistory')">
								<i class="glyphicon glyphicon-search"></i>导出
							</button>
						</div>
					</div>
					<div class="container-fuild">
						<!-- 搜索框 end -->

						<!-- table 数据展示 start -->
						<table:table id="channelTable"
                                     ajaxURI="${ctx}/channelPlacement/placementHistoryFind"
                                     columnNames="placementId,channelName,userName,securityCode,securityName,sideStr,quantity,price,fillQuantity,fillAmount,fillPrice,tradeCommissionFee,sysCommissionFee,statusStr,customerName,placementCode,rejectMessage,dateTimeStr"
                                     headerNames="用户委托ID,通道,用户名,证券代码,证券名称,委托方向,委托数量,委托价格,成交数量,成交金额,成交价格,券商佣金,系统佣金,状态,资金方,委托编号,拒绝原因,委托日期"
                                     searchInputNames="status,securityCode,userName,channelId,channel,startTime,endTime"/>
						<!-- table 数据展示 end-->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>



