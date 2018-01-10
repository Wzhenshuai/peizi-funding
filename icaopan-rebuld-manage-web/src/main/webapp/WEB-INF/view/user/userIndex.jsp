<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<script type="text/javascript" src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
	<style type="text/css">
		.popover{
			max-width:600px;
		}
	</style>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>用户管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">用户名</span>
						<input type="text" class="form-control" name="userName" id="userName-search" placeholder="用户名" aria-describedby="basic-addon1">
					</div>
				</div>
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
					<div class="input-group">
						<span class="input-group-addon">用户状态</span>
						<select class="form-control" name="status">
							${fns:getDicOptions("user_status_type")}
							<option value="-1">全部</option>
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<a href="${ctx}/frontUser/add">
						<button type='button' class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i>添加</button>
					</a>
					<button class="btn btn-danger"
							onclick="exportExcel('${ctx }/frontUser/exportUser')">
						<i class="glyphicon glyphicon-search"></i>导出
					</button>
				</div>
			</div>


			<table:table id="userTable" ajaxURI="${ctx}/frontUser/find"
						 columnNames="placeholder,userName,realName,customer.name,channel.name,userTradeTypeDisplay,statusStr,amount,available,frozen,ratioFeeDisplay,minCost,line,"
						 headerNames="序号,用户名,姓名,资金方,通道,委托策略,状态,帐户总金额,可用金额,冻结金额,佣金费率,佣金下限,警戒/平仓线/持仓比例,操作"
						 searchInputNames="userName,customerId,channelId,status"
						 makeDefsHtmlFunc="defs"
						 loadEnd="loadEnd()"
					/>
		</div>
	</div>
</div>

	<!-- 定义增配减配的模态框 -->
	<div class="modal fade" id="modifyAssets">
		<div class="modal-dialog" style="padding-left:20px;text-align:center;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">金额调整</h4>
				</div>
				<div class="modal-body">
					<%--<div class="form-group col-md-8 col-md-offset-3">--%>
						<%--<label for="cashAmount" class="col-sm-4 control-label">本金金额</label>--%>
						<%--<div class="col-sm-4">--%>
							<%--<label id="cashAmount" class="clearInput control-label col-md-2">0000</label>--%>
						<%--</div>--%>
					<%--</div>--%>
					<input type="hidden" id="userIdAssets">
					<div class="form-group col-md-8 col-md-offset-3">
						<label class="col-sm-4 control-label">当前总金额</label>
						<div class="col-sm-4">
							<label id="preAmount" class="clearInput control-label col-md-2">0000</label>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-3">
						<label for="available" class="col-sm-4 control-label">可用金额</label>
						<div class="col-sm-4">
							<label id="available" class="clearInput control-label col-md-2">0000</label>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-3">
						<label class="col-sm-4 control-label">调整类型</label>
						<div class="col-sm-2">
							<select style="width: 100px" id="vmark" class="clearInput" name="vmark">
								<option value="+">增加</option>
								<option value="-">减少</option>
							</select>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-3">
						<label class="col-sm-4 control-label">调整量</label>
						<div class="col-sm-4">
							<input style="width: 100px" type="number" class="clearInput" id="amount" name="amount"  htmlEscape="false" maxlength="100"/>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-3">
						<label class="col-sm-4 control-label">备注调整原因</label>
						<div class="col-sm-4">
							<input style="width: 100px" type="text" class="clearInput" id="changeCause" name="changeCause"  htmlEscape="false" required="required" maxlength="666"/>
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

		var tempData = {}; //构建临时数据变量
		function defs(rowdata){
//			console.log(rowdata);
			var user = [rowdata.createTimeStr,rowdata.lastLoginTimeStr,rowdata.loginCount,rowdata.cashAmount,rowdata.financeAmount,rowdata.stockScale,rowdata.userClassTypeDisplay];
			tempData[rowdata.id] = user
			var url = "${ctx}/${'frontUser/edit'}" + "?userId=" + rowdata.id
            var urlFrozen = "${ctx}/${'frontUser/frozen'}" + "?userId=" + rowdata.id
            return  "<a href="+url+"><input type='button' class='btn btn-info btn-xs' value='详情'/></a>"
					+ "<input type='button' onclick=initAssets("+rowdata.id+","+rowdata.amount+","+rowdata.available+","+rowdata.cashAmount+") class='btn btn-danger btn-xs' value='金额调整'/>"
					+ "<a href="+urlFrozen+"><input type='button' class='btn btn-primary btn-xs' value='资金冻结'/></a>"
					+ "<input type='hidden' id='"+ rowdata.id +"'  name='userId' value='"+ rowdata.id +"'/>";
		}

		function loadEnd(){
			$('#userTable tr').click(function(){
				$(this).find(":radio[name=userId]").attr('checked', true);
				markSelected(this);
			});
			var tableSetings=$('#userTable').dataTable().fnSettings()
			var page_start=tableSetings._iDisplayStart;//当前页开始
			var i = page_start;		//行序号
			$(":hidden[name=userId]").each(function(){
				var tr = $(this).parents('tr');
				var td = tr.children("td:first");
				tr.children("td:eq(4)").attr("data-toggle","popover");
				tr.children("td:eq(4)").attr("data-trigger","hover");
				tr.children("td:eq(4)").attr("data-container","body");
                tr.children("td:eq(1)").attr("data-toggle","popover");
                tr.children("td:eq(1)").attr("data-trigger","hover");
                tr.children("td:eq(1)").attr("data-container","body");
                tr.children("td:eq(7)").attr("data-toggle","popover");
                tr.children("td:eq(7)").attr("data-trigger","hover");
                tr.children("td:eq(7)").attr("data-container","body");
                tr.children("td:eq(12)").attr("data-toggle","popover");
                tr.children("td:eq(12)").attr("data-trigger","hover");
                tr.children("td:eq(12)").attr("data-container","body");
				td.empty();
				$(this).prependTo(td);
				i++;
				$(this).parents('tr').children("td:first").append('<lable>'+i+'</lable>');
			});
		}

		var lastSelect=null;
		function markSelected(obj){

			$(obj).css("background-color","LightSteelBlue");
			var markId =$(obj).children().children('input').attr('id');
			if(lastSelect!=null&&markId!=lastSelect){
				$('#'+lastSelect).parent().parent('tr').css("background-color","White");
			}
			lastSelect = markId;
		}

		//增配减配弹出框
        function initAssets(userId,amount,available,cashAmount) {
			$("#modifyAssets").modal();
            $('#userIdAssets').text(userId);
//			$('#cashAmount').text(cashAmount);
			$('#available').text(available);
			$('#preAmount').text(amount);
        }
        function editAssets() {
			var vmark = $('#vmark').val();
			var amount = parseFloat($('#amount').val());
			var preAmount = parseFloat($('#preAmount').text());
            var available = parseFloat($('#available').text());
            var userId = parseInt($('#userIdAssets').text());
			var changeCause = $('#changeCause').val();
			if (amount==null||amount==""){
                alert('调整量不为空');
                return false;
			}
			if(vmark=="-"){
				amount=(amount)*(-1);
				if (preAmount+amount<0){
					alert('调整之后的总金额不低于0');
					return false;
				}
                if (available+amount<0){
                    alert('调整之后的可用金额不低于0');
                    return false;
                }
			}
			if(changeCause == null ||changeCause==""){
                alert('备注不能为空');
                return false;
			}
            $.ajax({
                "type" : 'post',
                "url" : "${ctx}/frontUser/editAssets",
                "dataType" : "json",
                "data" : {
                    'userId':userId,
                    "amount":amount,
					'changeCause':changeCause
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

		/**
		 * 编辑用户信息
		 * */
		function editUser(userId){
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/frontUser/edit",
				"dataType" : "json",
				"data" : {
					'userId':userId
				},
				"success" : function(result) {
					alertx(result.message);
					/* 重新查询 */
					reloadTableData();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
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

