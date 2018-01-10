<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>用户资金流水</title>
		<meta name="decorator" content="default"></meta>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>用户资金流水
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
								id="securityCode-search" placeholder="证券代码">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">操作类型</span> 
								<select class="form-control" name="type">
									<option value="">请选择</option>
					  				${fns:getDicOptions("fund_fow_type")}
					  		</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">用户名</span> <input
								type="text" class="form-control" name="userId"
								id="status-search" placeholder="用户">
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
					<div class="col-md-2">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
						<button class="btn btn-danger"
							onclick="exportExcel('${ctx }/flow/exportFundFlow')">
							<i class="glyphicon glyphicon-search"></i>导出
						</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="flowTable"
					ajaxURI="${ctx}/flow/fundFlowFind"
					columnNames="id,userName,adjustAmount,typeStr,cash,financing,cashAmount,financingAmount,createTimeStr,customerName,notesStr"
					headerNames="流水ID,用户名,发生金额,操作类型,本金金额,融资金额,本金总计,融资总计,操作时间,资金方,备注"
					searchInputNames="securityCode,type,userId,startTime,endTime" />
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
</body>
</html>



