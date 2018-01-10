<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
	<script type="text/javascript" src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
</head>
<body>

	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>券商与平台证券头寸核对
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="row">
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">证券代码</span> <input
								type="text" class="form-control" name="stockCode"
								id="securityCode-search" placeholder="证券代码">
						</div>
					</div>
					<div class="col-md-2">
						<c:choose>
							<c:when test="${ empty admin}">
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerId" id="customerId-search" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
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
						<%--<div class="input-group">--%>
							<%--<span class="input-group-addon">资金方</span>--%>
							<%--<select class="form-control" name="customerId" id="customerId-search" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">--%>
								<%--<option value="">请选择</option>--%>
									<%--${fns:getCustomers()}--%>
							<%--</select>--%>
						<%--</div>--%>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon">通道</span>
							<select class="form-control" name="channelId" id="channelId-search">
								<option value="">请选择</option>
								${fns:getChanelOpt()}
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon">对比结果</span>
							<select class="form-control" name="checkResult" id="checkResult-search">
								<option value="">请选择</option>
								<option value="数量一致">数量一致</option>
								<option value="数量不一致">数量不一致</option>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
						<button class="btn btn-danger"
								onclick="exportExcel('${ctx }/brokerPosition/exportBrokerPosition')">
							<i class="glyphicon glyphicon-search"></i>导出
						</button>
					</div>
					<div class="col-md-2"><p style="color: red">注：当天有效数据在每个交易日下午三点半后生成</p></div>
				</div>
				<!-- 搜索框 end -->

				<!-- table 数据展示 start -->
				<table:table id="brokerPosition"
					ajaxURI="${ctx}/brokerPosition/query"
					columnNames="account,channelName,stockCode,stockName,icpAmount,amount,createDateStr,checkResult,minusAmount"
					headerNames="资金账号,通道名称,股票代码,股票名称,平台持仓数量,券商持仓数量,对账日期,比对结果,相差数量,操作"
					searchInputNames="stockCode,account,checkResult,customerId,channelId" makeDefsHtmlFunc="columnDefs" />
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function columnDefs(row) {
			var minusAmount = row.minusAmount;
			var action = "";
			if (minusAmount != 0) {
				action += "<a href='javascript:;'  onclick=queryDetail('"
						+ row.id + "')>持仓调整</a>&nbsp;";
				return action;
			}
			return "";
		}

		function queryDetail(id) {
			location.href = "${ctx}/brokerPosition/queryDetail?id=" + id;
		}

        function setChannel(customerId){
            $.ajax({
                "type" : 'post',
                "url" : "${ctx }/channel/findByCustomerId?customerId="+customerId,
                "dataType" : "json",
                "success" : function(result) {
                    var arr = [];
                    for(var i = 0 ; i < result.length ;i++){
                        var data = {};
                        data["txt"] = result[i].name;
                        data["val"] = result[i].id;
                        arr.push(data);
                        data = null;
                    }
                    if (arr.length==1){
                        setSelectOption('channelId-search', arr, '-请选择-',arr[0].val);
                    }else {
                        setSelectOption('channelId-search', arr, '-请选择-');
                    }
                },"error" : function (result){
                    alert("发生异常,或没权限...");
                }
            });
        }
	</script>
</body>
</html>

