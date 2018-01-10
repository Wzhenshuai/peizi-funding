<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function deleteLimit(limitId,obj){
            var trs = $(obj).parents('tr');
//            var positionLimit = trs.find('td').eq(2).text();
//            var positionSummary = trs.find('td').eq(3).text();
//            var unTrade = trs.find('td').eq(4).text();
//            if (Number(positionSummary)!=0 || Number(positionLimit)!=0 || Number(unTrade)!=0){
//                alertx("持仓限额/持仓总市值/未成交金额不为0，不满足删除条件");
//                return false;
//			}
            confirmx("确定要删除吗?",'deleteLimitToServer('+limitId+')');
        }
        function deleteLimitToServer(limitId) {
            $.ajax({
                "type" : 'post',
                "url" : "${ctx}/channel/deleteChannelLimit",
                "dataType" : "json",
                "data" : {"limitId":limitId},
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

        function refresh() {
			window.location.reload();
        }

        function adjustAmount(id,securityCode,amount,channelName){
            $("#limit_id").val(id);
            $("#security_code").html(securityCode);
            $("#old_amount").html(amount);
            $("#channel_name").html(channelName);
            $("#adjustModal").modal('show');
        }

        function addAmount(){
			/* $("#channel_name").html(channelName);
			 $("#channel_id").val(channelId);*/
            $("#addModal").modal('show');
        }
        $(function(){
            $("#updateForm").validate({
                rules:{
                    newAmount:{
                        required: true,
                        number:true
                    }
                },
                submitHandler: function(form){
                    $.ajax({
                        "type" : 'post',
                        "url" : "${ctx}/channel/updateChannelLimit",
                        "dataType" : "json",
                        "data" : $("#updateForm").serialize(),
                        "success" : function(result) {
                            $("#adjustModal").modal('hide');
                            alertx(result.message, function () {
                                if(result.rescode=='success'){
                                    window.location.reload();
                                }
                            });
                        },"error" : function (result) {
                            alertx("发生异常,或没权限...");
                        }
                    });
                }
            });
            $("#add_dateForm").validate({
                rules:{
                    newAmount:{
                        required: true,
                        number:true
                    },
                    securityCodeAdd:{
                        required:true
                    }
                },
                submitHandler: function(form){
                    $.ajax({
                        "type" : 'post',
                        "url" : "${ctx}/channel/addChannelLimit",
                        "dataType" : "json",
                        "data" : $("#add_dateForm").serialize(),
                        "success" : function(result) {
                            $("#addModal").modal('hide');
                            alertx(result.message, function () {
                                if(result.rescode=='success'){
                                    window.location.reload();
                                }
                            });
                        },"error" : function (result) {
                            alertx("发生异常,或没权限...");
                        }
                    });
                }
            })
        });
        $(document).ready(function(){
            $('#limitTable tr').each(function(){
                var limitRatio = $(this).find("td").eq(6).text();
                console.log(limitRatio)
                if($.isNumeric(limitRatio) && Number(limitRatio) >= 0.95){
                    $(this).css("background-color","red");
                }else if ($.isNumeric(limitRatio) && Number(limitRatio) >= 0.80){
                    $(this).css("background-color","yellow");
            }
            });
		});
	</script>

</head>
	<body>
		<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>【${channelName}】的单票限额设置
			</div>
		</div>
			<div class="portlet-body">
				<div class="row">
					<div class="col-md-4">
						<div>
							<button onclick="addAmount()" class="btn btn-info" data-dismiss="modal"><i class='glyphicon glyphicon-plus'>添加单票限制</i></button>
							<button onclick="refresh()" class="btn btn-info" data-dismiss="modal"><i class='glyphicon glyphicon-refresh'>刷新</i></button>

						</div>
					</div>
				</div>
				<c:choose>
					<c:when test="${!empty channelLimitList}">
						<table id="limitTable" class="table table-bordered table-striped">
							<tr>
								<th>通道名称</th>
								<th>股票代码</th>
								<th>持仓限额</th>
								<th>持仓总市值</th>
								<th>未成交总额</th>
								<th>距限额差额</th>
								<th>限额比例</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${channelLimitList}" var="cl">
								<tr>
									<td>${cl.channelName}</td>
									<td>${cl.securityCode}</td>
									<td>${cl.amount}</td>
									<td>${cl.positionSummaryAmount}</td>
									<td>${cl.notTradedAmount}</td>
									<td>${cl.disLimit}</td>
									<td>${cl.limitRatio}</td>
									<td>
										<button class='btn btn-info' onclick=adjustAmount(${cl.id},'${cl.securityCode}',${cl.amount},'${cl.channelName}') >  <i class='glyphicon glyphicon-edit'></i> 修改</button>
										<button class='btn btn-info' onclick=deleteLimit(${cl.id},this) > <i class='glyphicon glyphicon-remove'></i> 删除 </button>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<h1>暂无数据......</h1>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="modal fade" id="adjustModal" tabindex="-1" role="dialog" aria-labelledby="limitModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="adjustModalLabel">单票限制修改</h4>
					</div>
					<form id="updateForm" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label class="col-sm-4 control-label">通道名称:</label>
								<div class="col-sm-4">
									<span id="channel_name" class="b"></span>
								</div>
							</div>
							<input type="hidden" id="limit_id" name="limitId"/>
							<div class="form-group">
								<label class="col-sm-4 control-label">证券代码:</label>
								<div class="col-sm-4">
									<span id="security_code" class="b"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">现在限制的金额:</label>
								<div class="col-sm-4">
									<span id="old_amount" class="b"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">重新限制金额:</label>
								<div class="col-sm-4">
									<input type="text" id="new_amount"  name="newAmount" class="b"/>
								</div>
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

		<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="limitModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="addModalLabel">添加单票限制</h4>
					</div>
					<form id="add_dateForm" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label class="col-sm-4 control-label">通道名称:</label>
								<div class="col-sm-4">
									<span id="channel_name_add" class="b">${channelName}</span>
								</div>
							</div>
							<input type="hidden" id="channel_id" name="channelId" value="${channelId}"/>
							<div class="form-group">
								<label class="col-sm-4 control-label">证券代码:</label>
								<div class="col-sm-4">
									<input type="text" id="security_code_add" name="securityCodeAdd" class="b" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">限制的金额:</label>
								<div class="col-sm-4">
									<input type="text" id="add_amount"  name="addAmount" class="b"/>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
							<button type="submit" id="btn_submit_add" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

