<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
function columnDefs(row){
	 customerId = row.id;
	 customerName = row.name;
	 customerBalance = row.balance;
   	 var options = "";
   	 options += "<button type='button' id='update' class='btn btn-info' onclick=location.href='${ctx}/customer/form?id="+customerId+"'><i class='glyphicon glyphicon-edit'></i> 修改</button> &nbsp;";
	 options += "<button type='button' class='btn btn-danger' onclick=adjustBalance('"+customerId+"','"+customerName+"','"+customerBalance+"')><i class='glyphicon glyphicon-edit'></i>调整余额</button> &nbsp;";
     return options;
}

function adjustBalance(customerId,customerName,customerBalance){
    $("#customerId").val(customerId);
    $("#customer_name").html(customerName);
    $("#customer_balance").html(customerBalance);
    $("#adjustModal").modal('show');
}
$(function(){
    $("#updateForm").validate({
        rules:{
            customerAmount:{
                required: true,
                number:true
            }
        },
        submitHandler: function(form){
            $.ajax({
                "type" : 'post',
                "url" : "${ctx}/customer/updateBalance",
                "dataType" : "json",
                "data" : $("#updateForm").serialize(),
                "success" : function(result) {
                    alertx(result.message, function () {
                        if(result.rescode=='success'){
                            location.reload();
                        }
                    });
                },"error" : function (result) {
                    alertx("发生异常,或没权限...");
                }
            });
        }
    });
});
</script>
</head>
<body>
	<div class="portlet box red">
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-coffee"></i>资金方管理
	</div>
</div>
<div class="portlet-body">
	<div class="table-responsive">
			<div class="row">
				<div class="col-md-3">
					<div class="input-group">
						<span class="input-group-addon">资金方</span>
						<select class="form-control" name="id" >
							<option value="">请选择</option>
							${fns:getCustomers()}
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<div class="input-group">
					  <span class="input-group-addon">状态</span>
					  <select class="form-control" name="status">
					  	${fns:getDicOptions("user_status_type")}
					  </select>
					</div>
				</div>
				<div class="col-md-2">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<button type='button' id='add' onclick="location.href='${ctx}/customer/form'" class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i>添加</button>
				</div>	
			</div>
		<table:table headerNames="资金方,账户余额,通道最低收费,用户默认收费比例,用户默认最低佣金,低消模式,状态,备注,创建时间,更新时间,操作"
					 columnNames="name,balance,minCost,defaultTatio,defaultMinCost,costPatternStr,statusStr,notes,createTimeStr,modifyTimeStr"
					 ajaxURI="${ctx}/customer/find" id="showTable"
					 searchInputNames="id,status"
					 makeDefsHtmlFunc="columnDefs">

		</table:table>
	    </div>
	</div>
</div>
	<div class="modal fade" id="adjustModal" tabindex="-1" role="dialog" aria-labelledby="fillModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="fillModalLabel">金额调整</h4>
				</div>
				<div class="modal-body">
				<form id="updateForm" class="form-horizontal">
						<input type="hidden" id="customerId" name="customerId"/>
						<div class="form-group">
							<label class="col-sm-4 control-label">资金方：</label>
								 <span id="customer_name" class="b"></span>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">账户余额：</label>
							<div class="col-sm-4">
								<span id="customer_balance" class="b"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">金额：</label>
							<div class="col-sm-4">
								<input type="text" id="customerAmount" name="customerAmount" class="b"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label"><span>调整方向</span></label>
							<div class="col-sm-4">
								<select class="form-control" name="txtType" id="txtType" title="必须选择一条" required >
									<option value="">请选择</option>
									<option value="0">增加</option>
									<option value="1">减少</option>
								</select>
							</div>
						</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
						<button type="submit" id="btn_submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	</body>
</html>

