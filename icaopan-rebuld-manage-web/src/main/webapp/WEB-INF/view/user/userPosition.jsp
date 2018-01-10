<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>用户持仓管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>用户持仓管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">用户</span>
						<input type="text" class="form-control" name="userName" id="userName" placeholder="用户名" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-3">
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
                    <c:otherwise>
                        <div class="col-md-3" style="display: none">
                            <div class="input-group">
                                <span class="input-group-addon">资金方</span>
                                <select class="form-control" name="customerId" id="customerId-search2">
                                        ${fns:getCustomers()}
                                </select>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

				<div class="col-md-3">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<%--<c:if test="${admin == 'true'}">
						<a href="${ctx}/position/add">
							<button type='button' id="add" class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i>添加</button>
						</a>
					</c:if>--%>
					<button class="btn btn-danger"
							onclick="exportExcel('${ctx }/position/exportUserPosition')">
						<i class="glyphicon glyphicon-search"></i>导出
					</button>
				</div>
			</div>
			<table:table id="userTable" ajaxURI="${ctx}/position/findUserPosition"
                         columnNames="userName,securityName,securityCode,marketValue,marketProfit,marketProfitPercent,amount,availableAmount,latestPrice,costPrice,totalCost,suspensionDisplay,customerName"
                         headerNames="用户名,证券名称,证券代码,股票市值,持仓盈亏,持仓盈亏比例,持仓数量,持仓可用数量,现价,成本价,成本金额,停牌标识,资金方,操作"
                         searchInputNames="userName,securityCode,customerId"
                         makeDefsHtmlFunc="defs"/>
		</div>
	</div>
</div>

	<!-- 定义修改时的模态框 -->
	<div class="modal fade" id="modelForUpdate">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">更新头寸信息</h4>
				</div>
				<form id="updateForm" class="add-form">
					<div class="modal-body">
						<input type="hidden" id="id" name="id"/>
						<div class="form-group">
							<label for="available">可用头寸</label>
							<input type="number" class="form-control" name="available" id="available" placeholder="可用头寸" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label for="amount">总计头寸</label>
							<input type="number" class="form-control" name="amount" id="amount" placeholder="总计头寸" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label for="costPrice">成本价</label>
							<input type="number" class="form-control" name="costPrice" id="costPrice" placeholder="成本价" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label id="tips" style="color: red"></label>
						</div>
					</div>
					<div class="form-group">
						<p align="center">
							<input type="submit" class="btn btn-danger btn-sm" value="提 交"/>
							<input type="button" class="btn btn-default btn-sm" value="取 消"/>
						</p>
					</div>
				</form>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->



	<script>
		function defs(rowdata){
			console.log(rowdata);
			return "<a href=\"${ctx}/position/editUserPositionPrice?id="+rowdata.id+"\"><button class='btn btn-info'>成本价调整</button></a>";
		}
		$("#updateForm").validate({
			rules:{
				name:{
					required: true,
					rangelength:[0,10]
				},
			},
			submitHandler: function(form){
				$.ajax({
					"type" : 'post',
					"url" : "${ctx}/position/update",
					"dataType" : "json",
					"data" : $("#updateForm").serialize(),
					"success" : function(result) {
						alertx(result.message);
						/* 清空form表单 */
						$(".updateForm .form-control").val("");
						/* 关闭弹窗  */
						$('#modelForUpdate').modal('hide');
						/* 重新查询 */
						reloadTableData();
					},"error" : function (result){
						alertx("发生异常,或没权限...");
					}
				});
			}
		});


		$(function () {
			//失去焦点时触发的验证事件
			$('#name').bind('input propertychange', function() {
				$('#name').blur(function ()
				{
					verify();
				});
			});
		});

		//验证事件
		function verify()
		{
			var name = $('#name').val();
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/stock/verify",
				"dataType" : "json",
				"data" : {
					"name":name,
					"code":""
				},
				"success" : function(result) {
					if(result.rescode == "success"){
						$('#tips').text("股票名可用");
					}else
						$('#tips').text(result.message);
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}


		/* 查询用于修改的数据 */
		function getStockForUpdate(id){
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/position/get",
				"dataType" : "json",
				"data" : {'id':id},
				"success" : function(result) {
					console.log(result);
					$("#id").val(id);
					$("#internalSecurityId").val(result.internalSecurityId);
					$("#available").val(result.available);
					$("#amount").val(result.amount);
					$("#costPrice").val(result.costPrice);
					$("#customerId").val(result.customerId);
					$("#userId").val(result.userId);
					$("#modelForUpdate").modal();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}

	</script>

</body>




