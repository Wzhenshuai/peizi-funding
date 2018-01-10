<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
	<script type="text/javascript">
function columnDefs(row){
	 userId=row.id;
   	 var options = "";
   	 options += "<button type='button' id='update' class='btn btn-info' onclick=location.href='${ctx}/customer/form?id="+userId+"'><i class='glyphicon glyphicon-edit'></i> 修改</button> &nbsp;";
     return options;
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
				setSelectOption('channelId', arr, '-请选择-',arr[0].val);
			}else {
				setSelectOption('channelId', arr, '-请选择-');
			}
		},"error" : function (result){
			alert("发生异常,或没权限...");
		}
	});
}
</script>
</head>
<body>
	<div class="portlet box red">
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-coffee"></i>账单查询
	</div>
</div>

<div class="portlet-body">
	<div class="table-responsive">
			<div class="row">
			    <div class="col-md-2">
					<c:choose>
						<c:when test="${ empty admin}">
							<div class="input-group">
							  <span class="input-group-addon">资金方</span>
								<select class="form-control" name="customerId" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
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
					  <span class="input-group-addon">交易通道</span>
					  <select class="form-control" name="channelId" id="channelId">
					  	<option value="">请选择</option>
					  	${fns:getChanelOpt()}
					  </select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
					  <span class="input-group-addon">操作类型</span>
					  <select class="form-control" name="operationType">
					    <option value="">请选择</option>
					  	${fns:getDicOptions("operation_type")}
					  </select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" id="basic-start">开始日期</span> <input
                            type="text" class="form-control" name="startDate"
                            onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'date-search-end\')}'})"
                            id="date-search-start" placeholder="开始日期" />
					</div>
				</div>
				<div class="col-md-2 col-lg2">
					<div class="input-group">
						<span class="input-group-addon" id="basic-end">结束日期</span> <input
                            type="text" class="form-control" name="endDate"
                            onfocus="WdatePicker({minDate:'#F{$dp.$D(\'date-search-start\')}',maxDate:'2120-10-01'})"
                            id="date-search-end" placeholder="结束日期" />
					</div>
				</div>
				<div class="col-md-2">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<button class="btn btn-danger" onclick="exportExcel('${ctx }/customer/exportBill')"><i class="glyphicon glyphicon-search" ></i>导出</button>
				</div>	
			</div>
		<table:table headerNames="资金方,交易通道,发生金额,扣除费用,操作类型,操作时间"
					 columnNames="customerName,channelName,fillAmount,fee,operationType,operationTimeStr"
					 ajaxURI="${ctx}/customer/findBill" id="showTable"
					 searchInputNames="channelId,operationType,customerId,startDate,endDate"
					>

		</table:table>
	    </div>
	</div>
</div>	    
	</body>
</html>

