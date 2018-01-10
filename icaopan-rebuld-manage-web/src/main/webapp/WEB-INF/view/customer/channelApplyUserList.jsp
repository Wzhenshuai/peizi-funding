<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="portlet box red">
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-coffee"></i>通道审核
	</div>
</div>
	<div class="portlet-body">
		<div class="table-responsive">
				<div class="row">
					<div class="col-md-2">
						<div class="input-group">
						  <span class="input-group-addon">状态</span>
						  <select class="form-control" name="status">
							<option value="">请选择</option>
							${fns:getDicOptions("apply_status")}
						  </select>
						</div>
					</div>
					<div class="col-md-2">
						<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
						<button class='btn btn-danger' onclick="location.href='${ctx}/channel/applyForm'">添加</button>
					</div>
				</div>
					<table:table headerNames="券商名称,资金账号,交易账号,交易密码,通讯密码,营业部名称,状态,创建时间,备注"
								 columnNames="securityName,cashAccount,tradeAccount,jyPass,txPass,yybName,statusStr,createTimeStr,notes"
								 ajaxURI="${ctx}/channel/findChannelApply" id="showTable"
								 searchInputNames="status">

					</table:table>
			</div>
		</div>
	</div>
	</body>
</html>

