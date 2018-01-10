<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
function columnDefs(rowdata){
	 userId=rowdata.id;
	 console.log(rowdata);
   	 var options = "";
   	 options += "<button type='' class='btn btn-info' onclick=applyhandle("+rowdata.id+","+rowdata.cashAccount+")><i class='glyphicon glyphicon-edit'></i> 审核操作</button> &nbsp;";
     return options;
}

 function applyhandle(applyId,cashAccount) {
     $("#modifyAssets").modal();
     $("#cashAmount").val(cashAmount);
     $("#applyId").val(applyId);
     console.log(applyId);
	 
 }
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
		<table:table headerNames="券商名称,资金账号,交易账号,交易密码,通讯密码,营业部名称,状态,用户备注,管理员备注,创建时间,更新时间,操作"
					 columnNames="securityName,cashAccount,tradeAccount,jyPass,txPass,yybName,status,notes,adminNotes,createTimeStr,updateTimeStr"
					 ajaxURI="${ctx}/channel/findChannelApply" id="showTable"
					 searchInputNames="status"
					 makeDefsHtmlFunc="columnDefs">

		</table:table>
			<%--<c:choose>
				<c:when test="${customer = 'true'}">
					<table:table headerNames="券商名称,资金账号,交易账号,交易密码,通讯密码,营业部名称,状态,创建时间,备注,操作"
								 columnNames="securityName,cashAccount,tradeAccount,jyPass,txPass,yybName,status,createTimeStr,notes"
								 ajaxURI="${ctx}/channel/findChannelApply" id="showTable"
								 searchInputNames="status"
								 makeDefsHtmlFunc="columnDefs">

					</table:table>
				</c:when>
				<c:otherwise>
					<table:table headerNames="券商名称,资金账号,交易账号,交易密码,通讯密码,营业部名称,状态,用户备注，管理员备注,,创建时间,更新时间,操作"
								 columnNames="securityName,cashAccount,tradeAccount,jyPass,txPass,yybName,status,,notes,adminNotes,createTimeStr,updateTimeStr"
								 ajaxURI="${ctx}/channel/findChannelApply" id="showTable"
								 searchInputNames="status"
								 makeDefsHtmlFunc="columnDefs">

					</table:table>
				</c:otherwise>
			</c:choose>--%>
	    </div>
	</div>
</div>

	<div class="modal fade" id="modifyAssets">
		<div class="modal-dialog" style="padding-left:20px;text-align:center;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">金额调整</h4>
				</div>
				<div class="modal-body">
					<div class="form-group col-md-8 col-md-offset-3">
						<label for="cashAmount" class="col-sm-4 control-label">资金账户</label>
						<div class="col-sm-4"><input type="hidden" id="userIdAssets">
							<label id="cashAmount" class="clearInput control-label col-md-2"></label>
						</div>
					</div>
					<input type="hidden" name="applyId" id="applyId">
					<div class="form-group col-md-8 col-md-offset-3">
						<label class="col-sm-4 control-label">调整类型</label>
						<div class="col-sm-2">
							<select style="width: 100px" id="status" class="clearInput" name="isHandle">
								<option value="1">审核通过</option>
								<option value="2">审核失败</option>
							</select>
						</div>
					</div>

					<div class="form-group col-md-8 col-md-offset-3">
						<label class="col-sm-4 control-label">备注</label>
						<div class="col-sm-2">
							<textarea rows="3" id="adminNotes"></textarea>
						</div>
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input onclick="editAssets()" class="btn btn-primary" type="submit" value="确定"/>
					</div>
				</div>
			</div>
		</div>
	</div>
<script>
    function editAssets() {
        var applyId = parseInt($('#applyId').val());
        var adminNotes = $('#adminNotes').val();
        var status = parseFloat($('#status').val());
        console.log("applyId:==="+applyId);
        console.log("adminNotes:==="+adminNotes);
        console.log("status:==="+status);
        $.ajax({
            "type" : 'post',
            "url" : "${ctx}/channel/updateChannelApply",
            "dataType" : "json",
            "data" : {
                'applyId':applyId,
                'adminNotes':adminNotes,
				'status':status
            },
            "success" : function(result) {
                $('#modifyAssets').modal('hide');
                alertx(result.message);
				/* 清空 */
                window.location.reload();
            },"error" : function (result){
                alertx(result.message);
            }
        });
    }

</script>
	</body>
</html>

