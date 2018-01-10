<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title></title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>交易违规查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<div class="row">
					<div class="col-md-2">
						<c:choose>
							<c:when test="${ empty admin}">
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerId" id="customerId-search" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
										<option value="">请选择</option>
											${fns:getCustomers()}
									</select>
								</div>
							</c:when>
							<c:otherwise>
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerId" disabled = disabled>
											${fns:getCustomers()}
									</select>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" >用户名</span>
							<input type="text" class="form-control" name="userName" id="userName-search" placeholder="用户名" aria-describedby="basic-addon1">
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
					<div class="col-md-3">
						<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					</div>
				</div>
				<!-- table 数据展示 start -->
				<table:table id="complianceTable"
					ajaxURI="${ctx}/compliance/findAll"
					columnNames="userName,customerName,opType,stockCode,stockName,quantity,reason,createTimeStr"
					headerNames="用户名,资金方,操作类型,证券代码,证券名称,证券数量,违规原因,操作时间"
				    searchInputNames="userName,stockCode,customerId,startTime,endTime"
					pageLength="30"/>
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
</body>
</html>



