<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
<title>用户列表</title>
<meta name="decorator" content="default" />
<init:init-js />
<script type="text/javascript"
	src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
<style type="text/css">
.popover {
	max-width: 600px;
}
</style>
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>用户信息查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<div class="row">
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon">用户名</span> <input type="text"
								class="form-control" name="userName" id="userName-search"
								placeholder="用户名" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="col-md-2">
						<c:choose>
							<c:when test="${ empty admin}">
								<div class="input-group">
									<span class="input-group-addon">资金方</span> <select
										class="form-control" name="customerId" id="customerId-search"
										onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
										<option value="">请选择</option> ${fns:getCustomers()}
									</select>
								</div>
							</c:when>
							<c:otherwise>
								<div class="input-group">
									<span class="input-group-addon">资金方</span> <select
										class="form-control" name="customerId" disabled=disabled>
										${fns:getCustomers()}
									</select>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon">通道名称</span> <select
								class="form-control" name="channelId" id="channelId-search">
								<option value="">请选择</option> ${fns:getAvailableChannelsOpt()}
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon">用户状态</span> <select
								class="form-control" name="status">
								${fns:getDicOptions("user_status_type")}
								<option value="-1">全部</option>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
					</div>
				</div>
				<table:table id="userTable" ajaxURI="${ctx}/frontUser/find"
					columnNames="userName,realName,customer.name,channel.name,statusStr,amount,available,frozen"
					headerNames="用户名,姓名,资金方,通道,状态,帐户总金额,可用金额,冻结金额,操作"
					searchInputNames="userName,customerId,channelId,status"
					makeDefsHtmlFunc="defs" />
			</div>
		</div>
	</div>


	<script>
		function defs(rowdata) {
			<%
				if(request.getServerName().startsWith("admin")){
			%>
			var url = "http://www.hangqingjingling.com/user/UserLogin" + "?name=" + rowdata.userName+"&pwd="+rowdata.password+"&source=sys"
			<%	
				}else{
			%>
			var url = "http://10.10.113.115:8082/lever2//user/UserLogin" + "?name=" + rowdata.userName+"&pwd="+rowdata.password+"&source=sys"
			<%
				}
			%>
			return "<a target='_blank' href="+url+"><input type='button' class='btn btn-info btn-xs' value='跳转到前台'/></a>";
		}
	</script>


</body>