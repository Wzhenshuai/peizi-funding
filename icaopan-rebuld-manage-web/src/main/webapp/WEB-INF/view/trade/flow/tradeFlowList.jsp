<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>交割单查询</title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>交割单查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="row">
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">用户名</span> <input
								type="text" class="form-control" name="userName"
								id="status-search" placeholder="用户">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">证券代码</span> <input
								type="text" class="form-control" name="securityCode"
								id="securityCode-search" placeholder="证券代码">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">操作类型</span> 
								<select class="form-control" name="type">
									<option value="">请选择</option>
					  				${fns:getDicOptions("trade_fow_type")}
					  		</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">开始日期</span> <input
								type="text" class="form-control" name="startTime"
								id="startDate" placeholder="开始日期"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-\#{%d}\'}'})">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">结束日期</span> <input
								type="text" class="form-control" name="endTime"
								id="endDate" placeholder="结束日期"  onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-\#{%d}}'})">
						</div>
					</div>
					<div class="col-md-2">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
						<button class="btn btn-danger"
							onclick="exportExcel('${ctx }/flow/exportTradeFlow')">
							<i class="glyphicon glyphicon-search"></i>导出
						</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="flowTable"
							 ajaxURI="${ctx}/flow/tradeFlowFind"
							 columnNames="id,userName,securityCode,securityName,adjustQuantity,costPriceStr,adjustAmount,typeStr,commissionFee,stampDutyFee,transferFee,customerName,channelName,hiddenStr,notesStr,createTimeStr"
							 headerNames="流水ID,用户名,证券代码,证券名称,发生数量,成交价,发生金额,操作类型,佣金,印花税,过户费,资金方,通道名称,是否在前台显示,备注,操作时间"
							 searchInputNames="securityCode,type,userName,startTime,endTime" />
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
	<script type="text/javascript" src=${ctxStatic}/util/common.js></script>
	<script>
        $("#startDate").click(function(){
            var st = $("#startDate").val();
            var et = $("#endDate").val();
            if (st==et && (et == null||et == "")){
                var date=DF.getYesterdayDate();
                var lastMonthDate=DF.getLastMonthDate();
                console.log(date+":"+lastMonthDate)
                $("#startDate").val(lastMonthDate);
                $("#endDate").val(date);
			}
        });
        $("#endDate").click(function(){
            var st = $("#startDate").val();
            var et = $("#endDate").val();
            if (st==et && (et == null||et == "")){
                var date=DF.getYesterdayDate();
                var lastMonthDate=DF.getLastMonthDate();
                console.log(date+":"+lastMonthDate)
                $("#startDate").val(lastMonthDate);
                $("#endDate").val(date);
            }
        });
	</script>
</body>
</html>



