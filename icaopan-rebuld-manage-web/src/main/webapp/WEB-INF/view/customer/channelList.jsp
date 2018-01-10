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
				<i class="fa fa-coffee"></i>通道管理
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
					<div class="row">
						<div class="col-md-2">
							<div class="input-group">
							  <span class="input-group-addon">代码</span>
							  <input type="text" class="form-control" name="code" placeholder="代码">
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon">名称</span>
								<input type="text" class="form-control" name="name" placeholder="名称">
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
							  <span class="input-group-addon">状态</span>
							  <select class="form-control" name="isAvailable">
								<option value="">请选择</option>
								${fns:getDicOptions("available_type")}
							  </select>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
							  <span class="input-group-addon">类型</span>
							  <select class="form-control" name="channelType">
								<option value="">请选择</option>
								${fns:getDicOptions("channel_type")}
							  </select>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">资金方</span>
								<select class="form-control" name="customerId" id="customerId-search">
									<option value="">请选择</option>
									${fns:getCustomers()}
								</select>
							</div>
						</div>
						<div class="col-md-2 pull-right text-right">
							<button class="btn btn-danger" onclick="reloadIndexTable();"> <i class="glyphicon glyphicon-search"></i>查询</button>
							<button type='button' id='add' onclick="location.href='${ctx}/channel/form'" class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i>添加</button>
						</div>
					</div>
				<div class="row">
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">自动成交</span>
							<select class="form-control" name="autoFill" id="autoFill-search">
								<option value="">选择</option>
								<option value="true">是</option>
								<option value="false">否</option>
							</select>
						</div>
					</div>
				</div>
                <table:table headerNames="代码,名称,类型,资金方,可用资金,总资产,更新时间,收费比例,券商佣金比例,最低消费,佣金底消,状态,创建时间,操作时间,是否自动成交,备注,操作"
                             columnNames="code,name,channelTypeStr,customerName,cashAvailable,totalAssets,cashUptimeStr,tatio,tradeCommissionRate,minCost,tradeMinCost,isAvailableStr,createTimeStr,updateTimeStr,autoFillStr,notes"
                             ajaxURI="${ctx}/channel/find" id="showTable"
                             searchInputNames="code,name,isAvailable,channelType,customerId,autoFill"
                             makeDefsHtmlFunc="columnDefs">

				</table:table>
			</div>
		</div>
	</div>
	<!-- 定义解除关联用户的模态框 -->
	<div class="modal fade" id="unbindUsers" tabindex="-1" role="dialog" aria-labelledby="unbindUserslLabel">
		<div class="modal-dialog" role="document" style="padding-left:20px;text-align:center;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee"></i>解除通道所关联用户
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<div class="row">
									<div class="col-md-3">
										<div class="input-group">
											<input id="channelIdInput" type="hidden" name="channelIdNo" id="channelIdNo-search3" value="">
										</div>
										<div class="form-group has-warning">
											<label id="tips" style="color: red"></label>
										</div>
									</div>
									<div class="col-md-3 pull-right text-right">
										<button class="btn btn-danger" onclick="reloadUnbindTable();"> <i class="glyphicon glyphicon-refresh"></i>刷新</button>
									</div>
								</div>
								<table:table id="unbindTable" ajaxURI="${ctx}/frontUser/findUsersByChannelId"
											 columnNames="userName,realName,customerName,statusStr"
											 headerNames="用户名,真实姓名,资金方,状态,操作"
											 searchInputNames="channelIdNo"
											 makeDefsHtmlFunc="unbindDefs">
								</table:table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 定义清除通道持仓的模态框 -->
	<div class="modal fade" id="clearingPosition" tabindex="-1" role="dialog" aria-labelledby="clearingPositionlLabel">
		<div class="modal-dialog" role="document" style="padding-left:20px;text-align:center;width: 800px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee"></i>通道持仓列表
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<div class="row">
									<div class="col-md-3">
										<div class="input-group">
											<input type="hidden" name="channelId" id="channelId-search" value="00">
										</div>
										<div class="form-group has-warning">
											<label id="clearTips" style="color: red"></label>
										</div>
									</div>
									<div class="col-md-7 pull-right text-right">
										<button class="btn btn-danger" onclick="confirmx('确定要清空通道持仓吗？','oneKeyPosition();')"> <i class="glyphicon glyphicon-trash"></i>一键清空</button>
										<button class="btn btn-danger" onclick="reloadClearTable();"> <i class="glyphicon glyphicon-refresh"></i>刷新</button>
									</div>
								</div>
								<table:table id="clearPositionTable" ajaxURI="${ctx}/position/findChannelPosition"
											 columnNames="userName,securityName,securityCode,amount,availableAmount,marketValue,marketProfit,marketProfitPercent,latestPrice,costPrice"
											 headerNames="用户名,证券名称,证券代码,数量,可用数量,股票市值,盈亏,盈亏比例,现价,成本价"
											 searchInputNames="channelId">
								</table:table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
        function columnDefs(row){
            var userId=row.id;
            var options = "";
            options += "<button type='button' id='update' class='btn btn-info' onclick=location.href='${ctx}/channel/form?id="+userId+"'><i class='glyphicon glyphicon-edit'></i> 修改</button> &nbsp;"
                + "<button type='button' id='update' class='btn btn-danger' onclick='initUnbindUsersModal("+userId+")'><i class='glyphicon glyphicon-ban-circle'></i>解除用户</button> &nbsp;"
                + "<button type='button' id='update' class='btn btn-warning' onclick='initClearPositionModal("+userId+")'><i class='glyphicon glyphicon-trash'></i>清空持仓</button> &nbsp;";
            return options;
        }

        function unbindDefs(rowdata){
            console.log(rowdata);
            return "<button class='btn btn-info' onclick='unbindUs("+rowdata.id+")'>解绑</button>";
        }

        function initUnbindUsersModal(channelId) {
            $("#channelIdInput").val(channelId);
            document.getElementById("tips").innerText="";
            $('.dataTables_scrollHeadInner').css('width', '100%');
            reloadUnbindTable();
            sleep(1000);
            $("#unbindUsers").modal();
            reloadUnbindTable();
        }

        function initClearPositionModal(channelId) {
            document.getElementById("clearTips").innerText="";
            $("#channelId-search").val(channelId);
            $('.dataTables_scrollHeadInner').css('width', '100%');
            reloadClearTable();
            sleep(1400);
            $("#clearingPosition").modal();
            reloadClearTable();
        }

        function reloadClearTable() {
            var showTable = $("#clearPositionTable").dataTable();
            reloadTable(showTable);
        }

        function reloadIndexTable() {
            var showTable = $("#showTable").dataTable();
            reloadTable(showTable);
        }

        function reloadUnbindTable() {
            var showTable = $("#unbindTable").dataTable();
            reloadTable(showTable);
        }

        function reloadTable(showTable){
            var oSettings = showTable.fnSettings();
            oSettings._iDisplayStart = 0;
            showTable.fnDraw(false);
        }
	
        function unbindUs(userId) {
            var channelId = $("#channelIdInput").val();
            $.ajax({
                "url" : "${ctx}/channel/unbindChannel",
                "dataType" : "json",
				"async" : false,
                "data" : {
                    'userId':userId,
                    "channelId":channelId
                },
                "success" : function(result) {
                    console.log(result)
                    document.getElementById("tips").innerText=result.message;
					reloadUnbindTable();
                },"error" : function (result){
                    document.getElementById("tips").innerText=result.message;
                }
            });
        }

        function oneKeyPosition() {
            var channelId = $("#channelId-search").val();
            $.ajax({
                "url" : "${ctx}/position/oneKeyClearPosition",
                "dataType" : "json",
                "async" : false,
                "data" : {
                    "channelId":channelId
                },
                "success" : function(result) {
                    document.getElementById("clearTips").innerText=result.message;
                    reloadClearTable();
                },"error" : function (result){
                    document.getElementById("clearTips").innerText=result.message;
                }
            });
        }


	</script>
	</body>
</html>

