<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
<head>
	<title>用户当日委托</title>
	<meta name="decorator" content="default"></meta>
	<script type="text/javascript">
	function columnDefs(row){
       	var status=row.status;
       	var action="";
		if(status!="FILLED"){
	 	    action += "<a href='javascript:;'  onclick=doPersonFill('"+row.id+"',"+row.price+")>手工成交</a>&nbsp;";
	        return action;
		}
      	return "";
	}
	function doPersonFill(id,price){
		promptx("委托手动成交","成交价格",function(price){
			if(checkPrice(price)==false){
				alertx("请输入正确的价格");
				return false;
			}
			ajaxRequestPost("${ctx }/personPlacement/doPersonFill",{id:id,fillPrice:price},function(r){
				if(r.rescode=="success"){
					alertx("处理成功");
					reloadTableData();
				}else{
					alertx(r.message);
				}
			});
		});
	}
	</script>
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>用户当日委托
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
								<select class="form-control" name="status">
									<option value="">请选择</option>
					  				${fns:getDicOptions("trade_status")}
					  		</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">用户名</span> <input
								type="text" class="form-control" name="userName"
								id="status-search" placeholder="用户名">
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
				<table:table id="channelTable"
					ajaxURI="${ctx}/personPlacement/find"
						columnNames="id,userName,securityCode,securityName,sideStr,quantity,price,amount,fillQuantity,fillAmount,fillPrice,statusStr,timeStr"
						headerNames="委托ID,用户名,证券代码,证券名称,委托方向,委托数量,委托价格,委托金额,成交数量,成交金额,成交价格,状态,委托时间,操作"
					searchInputNames="securityCode,customerId,status,userName" 
					pageLength="20000" 
					makeDefsHtmlFunc="columnDefs" 
					/>	
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
</body>
</html>



