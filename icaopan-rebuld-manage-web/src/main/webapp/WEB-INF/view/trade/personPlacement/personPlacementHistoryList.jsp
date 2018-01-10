<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>用户历史委托查询</title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>用户历史委托查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="row">
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">证券代码</span> <input
								type="text" class="form-control" name="securityCode"
								id="securityCode-search" placeholder="证券代码" />
						</div>
					</div>
					<!-- <div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">委托方向</span> <input
								type="text" class="form-control" name="side" 
								id="side-search" placeholder="委托方向">
						</div>
					</div> -->
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">用户名</span> <input
								type="text" class="form-control" name="userName"
								id="user-search" placeholder="用户名" />
						</div>
					</div>
					<div class="col-md-2">
						<c:choose>
							<c:when test="${ empty admin}">
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerId" >
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
							<span class="input-group-addon" id="basic-addon1">状态</span>
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
							onclick="exportExcel('${ctx }/personPlacement/exportPlacementHistory')">
							<i class="glyphicon glyphicon-search"></i>导出
					</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="placementTable"
					ajaxURI="${ctx}/personPlacement/historyFind"
					columnNames="placementId,userName,securityCode,securityName,sideStr,quantity,price,fillQuantity,fillAmount,fillPrice,statusStr,commissionFee,stampDutyFee,transferFee,dateTimeStr"
					headerNames="委托ID,用户名,证券代码,证券名称,委托方向,委托数量,委托价格,成交数量,成交金额,成交价格,状态,佣金,印花税,过户费,委托时间"
					searchInputNames="status,securityCode,userName,customerId,startTime,endTime"
					 loadEnd="loadEnd()"/>
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
<script>

    function loadEnd(){
        $("#placementTable tr").each(function(){
            var td = $(this).find('td').eq(0).html();
            if (!$.isNumeric(td)) return true;
            $(this).find('td').eq(0).html(placementConvert(td))
        });
    }

    function placementConvert(placementId){
        return "<a class='btn btn-link' href='${ctx}/channelPlacement/channelPlacementHistoryDetail?placementId="+placementId+"'>"+placementId+"</a>";
    }

</script>
</body>
</html>



