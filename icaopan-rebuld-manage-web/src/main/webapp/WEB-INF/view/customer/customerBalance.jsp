<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="portlet box red">
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-coffee"></i>资金方账户余额
	</div>
</div>
<div class="portlet-body">
	<div class="table-responsive">
			<div class="row">
				<div class="col-md-3">
					<div class="input-group">
						<span class="input-group-addon">资金方</span>
						<select class="form-control" name="id" id="customerId-search">
							<option value="">请选择</option>
							${fns:getCustomers()}
						</select>
					</div>
				</div>

				<div class="col-md-2">
					<button class="btn btn-success" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>搜索</button>
				</div>	
			</div>
		<table:table headerNames="资金方名称,账户余额,上月发生费用,上月实收费用,本月已经发生费用"
					 columnNames="customerName,balance,lastMonthTradeFee,lastMonthFee,currentMonthTradeFee"
					 ajaxURI="${ctx}/customer/queryCustomerBalance" id="showTable"
					 searchInputNames="id">

		</table:table>
	    </div>
	</div>
</div>	    
	</body>
</html>

