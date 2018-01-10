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
					<i class="fa fa-coffee"></i>账户信息
				</div>
			</div>
			<div class="portlet-body">
			<div class="table-responsive">
			<c:if test="${customer!=null }">
				<div class="panel panel-success">
					<div class="panel-body">
						<div class="col-md-5">
							<div class="row">
								<label class="col-sm-4 control-label">资金方名称：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.name }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label" >用户默认费率：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.defaultRatioStr }</p>
								</div>
							</div>
							<%--<div class="row">--%>
								<%--<label class="col-sm-4 control-label" >用户默认最低佣金：</label>--%>
								<%--<div class="col-sm-8">--%>
									<%--<p class="control-label">${customer.defaultMinCost }</p>--%>
								<%--</div>--%>
							<%--</div>--%>
							<div class="row">
								<label class="col-sm-4 control-label">低消模式：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.costPatternStr }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label">状态：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.statusStr }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label">创建时间：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.createTimeStr }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label">更新时间：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.modifyTimeStr }</p>
								</div>
							</div>
							<%--<div class="row">--%>
								<%--<label class="col-sm-4 control-label">备注：</label>--%>
								<%--<div class="col-sm-8">--%>
									<%--<p class="control-label">${customer.notes }</p>--%>
								<%--</div>--%>
							<%--</div>--%>
						</div>

						<div class="col-md-7">
							<div class="row">
								<label class="col-sm-4 control-label" >账户余额：</label>
								<div class="col-sm-8">
									<p class="control-label">${customer.balance }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label">上月发生费用：</label>
								<div class="col-sm-8">
									<p class="control-label">${customerBalance.lastMonthTradeFee }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label">上月实收费用：</label>
								<div class="col-sm-8">
									<p class="control-label">${customerBalance.lastMonthFee }</p>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-4 control-label">本月已经发生费用：</label>
								<div class="col-sm-8">
									<p class="control-label">${customerBalance.currentMonthTradeFee }</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${customer==null }">
				<table:table headerNames="客户名称,帐户余额,最低收费,默认费率,默认最低佣金,低消模式,状态,备注,创建时间,更新时间"
							 columnNames="name,balance,minCost,defaultTatio,defaultMinCost,costPatternStr,statusStr,notes,createTimeStr,modifyTimeStr"
							 ajaxURI="${ctx}/customer/find" id="showTable">

				</table:table>
			</c:if>
		</div>
		</div>
		</div>
		
	</body>
</html>

