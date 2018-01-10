<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
	<head>
		<title>用户当日委托</title>
		<meta name="decorator" content="default"></meta>
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
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">委托ID</span> <input
								type="text" class="form-control" name="id"
								id="securityCode-search" placeholder="委托ID">
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
					<div class="col-md-1">
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
							onclick="exportExcel('${ctx }/personPlacement/exportPlacement')">
							<i class="glyphicon glyphicon-search"></i>导出
						</button>
					</div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="channelTable"
					ajaxURI="${ctx}/personPlacement/find"
						columnNames="id,userName,securityCode,securityName,sideStr,quantity,price,amount,fillQuantity,fillAmount,fillPrice,statusStr,timeStr"
						headerNames="委托ID,用户名,证券代码,证券名称,委托方向,委托数量,委托价格,委托金额,成交数量,成交金额,成交价格,状态,委托时间"
						searchInputNames="securityCode,customerId,status,userName,id"
					    loadEnd="loadEnd()"/>
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

        function loadEnd(){
            $("#channelTable tr").each(function(){
                var td = $(this).find('td').eq(0).html();
                if (!$.isNumeric(td)) return true;
                $(this).find('td').eq(0).html(placementConvert(td))
            });
        }

        function placementConvert(placementId){
            return "<a class='btn btn-link' href='${ctx}/channelPlacement/channelPlacementDetail?placementId="+placementId+"'>"+placementId+"</a>";
        }
	</script>
</body>
</html>



