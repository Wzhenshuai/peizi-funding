<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>通道当日委托查询</title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>通道当日委托查询
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
								id="securityCode-search" placeholder="证券代码"
								aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">用户委托ID</span> <input
								type="text" class="form-control" name="placementId"
								id="securityCode-search" placeholder="委托ID"
								aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">委托方向</span>
								<select class="form-control" name="side" id="side-search">
									<option value="">请选择</option>
									${fns:getDicOptions("trade_type")}
								</select>
							 
						</div>
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
							<span class="input-group-addon" id="basic-addon1">通道</span>
							<select class="form-control" name="channelId" id="channelId-search">
								<option value="">请选择</option>
								${fns:getChanelOpt()}
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							自动刷新：<br/>
							<input type="checkbox" id="autoFresh" onclick="autoFresh()"/>
						</div>
					</div>
					<div class="col-md-2 pull-right">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
						<button class="btn btn-danger"
							onclick="exportExcel('${ctx }/channelPlacement/exportChannelDay')">
							<i class="glyphicon glyphicon-search"></i>导出
						</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="channelTable"
					ajaxURI="${ctx}/channelPlacement/placementFind"
					columnNames="placementId,channelName,userName,securityCode,securityName,sideStr,quantity,price,amount,fillQuantity,fillAmount,fillPrice,statusStr,customerName,placementCode,rejectMessage,dateTimeStr"
					headerNames="用户委托ID,通道,用户名,证券代码,证券名称,委托方向,委托数量,委托价格,委托金额,成交数量,成交金额,成交价格,状态,资金方,委托编号,拒绝原因,创建时间"
					searchInputNames="securityCode,side,status,channelId,placementId" />
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
	<script>
        //定时刷新数据
        var rs = 0;
        function autoFresh(){
            var check=$("#autoFresh").is(':checked');
            if (!check){
                clearInterval(rs);
                return false;
            }
            rs = setInterval(function(){
                if(check){
                    var showTable = $("#channelTable").dataTable();
                    showTable.fnDraw(false);
                }
            }, 10000);
        }
	</script>
</body>
</html>



