<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
	<script>
        function columnDefs(row){
            channelId=row.id;
            channelName=row.name;
            var customerId = row.customerId;
            return "<input type='hidden' name='highSinglePositionLimit' value='"+row.highSinglePositionLimit+"'>" +
				"<a href=\"${ctx}/channel/findChannelSinglePositionLimitByChannelId?channelId=" +
				channelId+ "&customerId="+customerId + "&channelName="+channelName+"\"><button class='btn btn-info'>单票限额设置</button></a>"
        }
        function LoadEnd() {
			$(":hidden[name='highSinglePositionLimit']").each(function (e) {
				var highSinglePositionLimit = $(this).val();
                if($.isNumeric(highSinglePositionLimit) && Number(highSinglePositionLimit) >= 0.95){
                    $(this).parent().parent().css("background-color","red");
                }else if ($.isNumeric(highSinglePositionLimit) && Number(highSinglePositionLimit) >= 0.80){
                    $(this).parent().parent().css("background-color","yellow");
                }
            })
        }
	</script>
</head>
<body>
	<div class="portlet box red">
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-coffee"></i>通道持仓监控
	</div>
</div>
<div class="portlet-body">
	<div class="table-responsive">
			<div class="row">
				<input type="hidden" name="isAvailable" value="true">
				<div class="col-md-2">
					<c:choose>
						<c:when test="${ empty admin}">
							<div class="input-group">
								<span class="input-group-addon">资金方</span>
								<select class="form-control" name="customerId" id="customerId-search" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);" >
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
						<span class="input-group-addon">通道名称</span>
						<select class="form-control" name="channelId" id="channelId-search">
							<option value="">请选择</option>
							${fns:getAvailableChannelsOpt()}
						</select>
					</div>
				</div>
				<div class="col-md-2">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
				</div>	
			</div>
		<table:table headerNames="通道代码,名称,资金方,可用资金,资金更新时间,状态,创建时间,操作"
					 columnNames="code,name,customerName,cashAvailable,cashUptimeStr,isAvailableStr,createTimeStr"
					 ajaxURI="${ctx}/channel/find"
					 id="showTable"
					 searchInputNames="channelId,isAvailable,customerId"
					 makeDefsHtmlFunc="columnDefs"
					 loadEnd="LoadEnd()">
		</table:table>
	    </div>
	</div>
</div>	    
	</body>
</html>

