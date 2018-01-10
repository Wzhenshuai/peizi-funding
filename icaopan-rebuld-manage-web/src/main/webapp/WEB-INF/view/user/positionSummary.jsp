<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>持仓汇总管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<script type="text/javascript">
		function defs(rowdata){
			return "<button class='btn btn-info' onclick=\"userPositionQry("+rowdata.customerId+",\'"+rowdata.securityCode+"\')\">用户持仓详情</button>"+
                "<button class='btn btn-info' onclick=\"channelPositionQry("+rowdata.customerId+",\'"+rowdata.securityCode+"\')\">通道持仓详情</button>";
		}

        function reloadTable(showTable){
            var oSettings = showTable.fnSettings();
            oSettings._iDisplayStart = 0;
            showTable.fnDraw(false);
        }

        function reloadSummaryTableData() {
            var showTable = $("#userTable").dataTable();
            reloadTable(showTable);
        }

        function reloadUserPositionTableData(){
            var showTable = $("#userPositionTable").dataTable();
            reloadTable(showTable);
        }

        function reloadPositionTableData(){
            var showTable = $("#channelPositionTable").dataTable();
            reloadTable(showTable);
        }

        function userPositionQry(customerId,securityCode){
            $("#customerId-search3").val(customerId);
            $("#securityCode-search3").val(securityCode);
            $('.dataTables_scrollHeadInner').css('width', '100%');
            $('#modelForUserPosition').modal('show');
            sleep(1000);
            reloadUserPositionTableData();
        }

        function channelPositionQry(customerId,securityCode){
            $("#customerId-search4").val(customerId);
            $("#securityCode-search4").val(securityCode);
            $('.dataTables_scrollHeadInner').css('width', '100%');
            $('#modelForChannelPosition').modal('show');
            sleep(1000);
            reloadPositionTableData();
        }
        
        function  exportSummaryPositionExcel(url) {
            var customerId = $("#customerId-search").val();
            var securityCode = $("#securityCode-search").val();
            url+="?t=t";
            if(customerId){
            	url+="&customerId="+customerId;
            }
            if(securityCode){
            	url+="&internalSecurityId="+securityCode;
            }
            location.href=url;
        }

	</script>
	
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>持仓汇总管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >证券代码</span>
						<input type="text" class="form-control" name="securityCode" id="securityCode-search" placeholder="股票代码" aria-describedby="basic-addon1">
					</div>
				</div>
                <c:choose>
                    <c:when test="${admin == 'true'}">
                        <div class="col-md-2">
                            <div class="input-group">
                                <span class="input-group-addon">资金方</span>
                                <select class="form-control" name="customerId" id="customerId-search">
                                    <option value="">请选择</option>
                                        ${fns:getCustomers()}
                                </select>
                            </div>
                        </div>
                    </c:when>
                </c:choose>

				<div class="col-md-3">
					<button class="btn btn-danger" onclick="reloadSummaryTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<%--<c:if test="${admin == 'true'}">
						<a href="${ctx}/position/add">
							<button type='button' id="add" class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i>添加</button>
						</a>
					</c:if>--%>
					<button class="btn btn-danger"
							onclick="exportSummaryPositionExcel('${ctx }/position/exportUserPositionSummary')">
						<i class="glyphicon glyphicon-search"></i>导出
					</button>
				</div>
			</div>
			<table:table id="userTable" ajaxURI="${ctx}/position/findSummaryPosition"
						 columnNames="securityName,securityCode,amount,marketValue,availableAmount,customerName"
						 headerNames="证券名称,证券代码,持仓数量,股票市值,持仓可用数量,资金方,操作"
						 searchInputNames="userName,securityCode,customerId"
						 makeDefsHtmlFunc="defs"/>
		</div>
	</div>
</div>

	<!-- 定义查看用户持仓明细的模态框 -->
	<div class="modal fade" id="modelForUserPosition" tabindex="-1" role="dialog" aria-labelledby="queryDetailLabel" >
		<div class="modal-dialog" role="document" style="width: 950px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee"></i>用户持仓明细查询
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<div class="row">
									<div class="col-md-3">
										<div class="input-group">
											<span class="input-group-addon" >用户名</span>
											<input type="text" hidden name="customerIdParam" id="customerId-search3" value="">
											<input type="text" hidden name="securityCodeParam" id="securityCode-search3" value="">
											<input type="text" class="form-control" name="userNameParam" id="userNameParam-search" placeholder="用户名" aria-describedby="basic-addon1">
										</div>
									</div>
                                    <div class="col-md-3">
                                        <button class="btn btn-danger" onclick="reloadUserPositionTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
                                    </div>
								</div>
								<table:table id="userPositionTable" ajaxURI="${ctx}/position/findUserPositionDetail"
											 columnNames="userName,securityName,securityCode,amount,availableAmount,customerName"
											 headerNames="用户名,证券名称,证券代码,持仓数量,持仓可用数量,资金方"
											 searchInputNames="userNameParam,securityCodeParam,customerIdParam"/>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 定义查看通道持仓明细的模态框 -->
	<div class="modal fade" id="modelForChannelPosition" tabindex="-1" role="dialog" aria-labelledby="queryDetailLabel" >
		<div class="modal-dialog" role="document" style="width: 950px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee"></i>通道持仓明细查询
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<div class="row">
									<div class="col-md-4">
										<div class="input-group">
											<span class="input-group-addon" >通道名称</span>
											<input type="text" hidden name="customerIdP" id="customerId-search4" value="">
											<input type="text" hidden name="securityCodeP" id="securityCode-search4" value="">
                                            <select class="form-control" name="channelId" id="channelId-search">
                                                <option value="">请选择</option>
                                                ${fns:getChanelOpt()}
                                            </select>
										</div>
									</div>
                                    <div class="col-md-3">
                                        <button class="btn btn-danger" onclick="reloadPositionTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
                                    </div>
								</div>
								<table:table id="channelPositionTable" ajaxURI="${ctx}/position/findChannelPositionDetail"
											 columnNames="channelName,securityName,securityCode,amount,availableAmount,customerName"
											 headerNames="通道名称,证券名称,证券代码,持仓数量,持仓可用数量,资金方"
											 searchInputNames="channelId,securityCodeP,customerIdP"/>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	
</body>




