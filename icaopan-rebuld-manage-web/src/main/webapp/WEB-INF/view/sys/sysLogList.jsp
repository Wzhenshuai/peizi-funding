<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>操作日志查询</title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>操作日志查询
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
						<c:choose>
							<c:when test="${ empty admin}">
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerId">
										<option value="">请选择</option>
											${fns:getCustomers()}
									</select>
								</div>
							</c:when>
							<c:otherwise>
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerId" disabled=disabled>
											${fns:getCustomers()}
									</select>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon12">开始日期</span> <input
								type="text" class="form-control" name="startTime"
								id="startDate" placeholder="开始日期"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-\#{%d}\'}'})">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addo1">结束日期</span> <input
								type="text" class="form-control" name="endTime"
								id="endDate" placeholder="结束日期"  onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-\#{%d}}'})">
						</div>
					</div>
					<div class="col-md-2">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="sysLogTable"
							 ajaxURI="${ctx}/sys/findSysLog"
							 columnNames="id,title,userName,createDateStr,remoteAddr"
							 headerNames="日志ID,操作说明,操作者,操作时间,ip地址"
							 searchInputNames="customerId,userName,startTime,endTime"/>
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



