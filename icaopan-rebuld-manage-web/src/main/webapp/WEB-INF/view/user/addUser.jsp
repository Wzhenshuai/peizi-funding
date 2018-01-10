<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>添加用户</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>添加用户
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<form:form id="inputForm" class="form-horizontal">
				<div class=" panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">基本信息</h3>
					</div>
					<div class="panel-body">
						<div class="col-md-5" style="width:39%">

							<div class="form-group">
								<label class="col-sm-4 control-label">用户名<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="userName" id="userName" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
								<div class="col-sm-4 control-label"><font color="red"><label id="nameTips"></label></font></div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">真实姓名<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="realName" class="form-control"  maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">用户密码<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="password" class="form-control" maxlength="100" class="password"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">状态<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<select class="form-control" name="status" id="status">
										<option value="0">正常</option>
										<option value="1">锁买卖</option>
										<option value="3">锁买</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">用户类型<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<select class="form-control" name="userClassTypeVal" id="userClassTypeVal">
										<option value="0">普通用户</option>
										<option value="1">高级用户</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">总金额<font color="red">*</font>:</label>
								<div class="col-sm-4">
									<input type="number" value="0.00" name="amount" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">本金(供参考):</label>
								<div class="col-sm-4">
									<input type="number" value="0.00" name="cashAmount" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">融资总额(供参考):</label>
								<div class="col-sm-4">
									<input type="number" value="0.00" name="financeAmount" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
						<div class="col-md-7">
							<div class="form-group">
								<label class="col-sm-2 control-label">通道下单模式<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-5">
									<select class="form-control" name="userTradeTypeVal" id="userTradeTypeVal" onchange="tradeTypeChange()">
										<option value="0">优先下单</option>
										<option value="1">平分下单</option>
										<option value="2">比例下单</option>
									</select>
								</div>
							</div>
							<c:if test="${admin == 'true'}">
								<div class="form-group">
									<label class="col-sm-2 control-label">资金方<span class="help-inline"><font color="red">*</font> </span>:</label>
									<div class="col-sm-5">
										<select class="form-control" name="customerId" id="customerId-search" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
											<option value="">-请选择-</option>
												${fns:getCustomers()}
										</select>
									</div>
								</div>
							</c:if>

							<div class="form-group"><input type="hidden" id="channelIds" name="channelIds">
								<label class="col-sm-2 control-label">通道<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-9">
									<div id="relatedTable" class="table-responsive"></div>
									<small id="smallTips" style="display: none;color: red">备注：优先级从高往低排序</small>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">未分配通道:<br><br></label>
								<div class="col-sm-5">
									<div id="channelFreeTable" class="table-responsive"></div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">佣金信息</h3>
					</div>
					<div class="panel-body">
						<div class="col-md-5">
							<div class="form-group">
								<label class="col-sm-4 control-label">使用默认费率<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<select class="form-control" name="isDefaultFee" onchange="if(this.value != '') setRatioFee(this.options[this.selectedIndex].value);">
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">最低费用:</label>
								<div class="col-sm-4">
									<input type="number" value="5" name="minCost" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>

						</div>
						<div class="col-md-7">
							<div class="form-group" id="ratioFeeSwitch">
								<label class="col-sm-4 control-label">佣金费率:</label>
								<div class="col-sm-4">
									<input type="number" value="0.0008" step="0.0001" id="ratioFee" name="ratioFee" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">风控信息</h3>
					</div>
					<div class="panel-body">
						<div class="tab-content clearfix">
							<ul  class="nav nav-tabs">
								<li class="active">
									<a  href="#1a" data-toggle="tab">基本信息</a>
								</li>
								<li><a href="#2a" data-toggle="tab">高级风控</a>
								</li>
							</ul>
							<div class="tab-pane active" id="1a">
								<div class="col-md-5">
									<div class="form-group">
										<label class="col-sm-4 control-label">警戒线 <font color="red">*</font>:</label>
										<div class="col-sm-4">
											<input type="number" value="0.00" name="warnLine" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">平仓线 <font color="red">*</font>:</label>
										<div class="col-sm-4">
											<input type="number" value="0.00" name="openLine" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
								</div>
								<div class="col-md-7">
									<div class="form-group">
										<label class="col-sm-4 control-label">单支股票比例:</label>
										<div class="col-sm-4">
											<input type="number" value="1" step="0.1" name="singleStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">中小板单支股票比例:</label>
										<div class="col-sm-4">
											<input type="number" value="1" step="0.1" name="smallSingleStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">中小板比例:</label>
										<div class="col-sm-4">
											<input type="number" value="1" step="0.1" name="smallStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">创业板单支比例:</label>
										<div class="col-sm-4">
											<input type="number" value="1" step="0.1" name="createSingleStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">创业板比例:</label>
										<div class="col-sm-4">
											<input type="number" value="1" step="0.1" name="createStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="2a">
								<div class="col-sm-6">
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title">下单上限设置</h3>
										</div>
										<div class="panel-body">
											<div class="form-group">
												<label class="col-sm-4 control-label">启用:</label>
												<div class="col-sm-4">
													<input id="limit" type="checkbox"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-4 control-label">金额上限:</label>
												<div class="col-sm-4">
													<input type="number" id="riskAmountQuota" value="0" min="0" step="1" name="riskAmountQuota" class="form-control" htmlEscape="false" maxlength="100"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-4 control-label">数量上限:</label>
												<div class="col-sm-4">
													<input type="number" id="riskQuantityQuota" value="0" min="0" step="1" name="riskQuantityQuota" class="form-control" htmlEscape="false" maxlength="100"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-8 control-label" style="color: red">提示：如果限制金额设置为0，表示对应的金额未作限制</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-6"><input type="hidden" id="riskFlag" name="riskFlag">
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title">涨跌停振幅比例控制</h3>
										</div>
										<div class="panel-body">
											<div class="form-group">
												<label class="col-sm-4 control-label">启用:</label>
												<div class="col-sm-4">
													<input id="quoteChange" type="checkbox"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-5 control-label">涨停振幅比例限制:</label>
												<div class="col-sm-4">
													<input type="number" min="0" value="0.00" step="0.01" id="riskUpAmplitude" name="riskUpAmplitude" class="form-control" htmlEscape="false" maxlength="100"/>
													<label id="riskUpAmplitudeLabel" class="error" style="display: none"></label>
												</div>
												<span class="help-inline" style="color: red">%</span>
											</div>
											<div class="form-group">
												<label class="col-sm-5 control-label">跌停振幅比例限制:</label>
												<div class="col-sm-4">
													<input type="number" min="0" value="0.00" step="0.01" id="riskDownAmplitude" name="riskDownAmplitude" class="form-control" htmlEscape="false" maxlength="100"/>
													<label id="riskDownAmplitudeLabel" class="error" style="display: none"></label>
												</div>
												<span class="help-inline" style="color: red">%</span>
											</div>
											<div class="form-group">
												<label class="col-sm-8 control-label" style="color: white">.</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title">股票白名单设置</h3>
										</div>
										<div class="panel-body">
											<div class="form-group">
												<label class="col-sm-4 control-label">启用:</label>
												<div class="col-sm-4">
													<input type="checkbox" id="whiteCheckBox" name=""/>
												</div>
											</div>
											<div class="form-group"><input type="hidden" id="whiteList" name="whiteList">
												<label class="col-sm-4 control-label">股票白名单列表:<br><br></label>
												<div class="col-sm-5">
													<div id="whiteListTable" class="table-responsive"></div>
												</div>
												<div class="col-sm-2">
													<span onclick="addStock('white')" class="btn-sm btn-danger glyphicon glyphicon-plus"></span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title">股票黑名单设置</h3>
										</div>
										<div class="panel-body">
											<div class="form-group">
												<label class="col-sm-4 control-label">启用:</label>
												<div class="col-sm-4">
													<input id="blackCheckBox" type="checkbox" name=""/>
												</div>
											</div>
											<div class="form-group"><input type="hidden" id="blackList" name="blackList">
												<label class="col-sm-4 control-label">股票黑名单列表:<br><br></label>
												<div class="col-sm-5">
													<div id="blackListTable" class="table-responsive"></div>
												</div>
												<div class="col-sm-2">
													<span onclick="addStock('black')" class="btn-sm btn-warning glyphicon glyphicon-plus"></span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-5 control-label"></label>
					<div class="col-sm-6">
						<button type="submit" class="btn btn-danger btn-sm">提交</button>
						<button type="button" class="btn btn-default btn-sm" onclick="history.go(-1)">取消</button>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>

<!-- 定义增加股票清单 -->
<div class="modal fade" id="modelStock">
	<div class="modal-dialog" style="padding-left:20px;text-align:center;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加股票</h4>
			</div>
			<div class="modal-body form-horizontal">
				<input type="hidden" id="typeIn">
				<div class="form-group">
					<label class="col-sm-3 control-label">股票代码</label>
					<div class="col-sm-6">
						<input id="stockCode" class="form-control" type="text" value="" placeholder="股票代码">
					</div>
					<div class="col-sm-3">
						<input  id="btnStockConfirm" class="btn btn-primary" type="submit" value="确定" onclick='addStockCode()'/>
					</div>
				</div><br>
				<div class="form-group">
					<label id="tipsStockCode" style="align-content: center;color: red"></label>
				</div>
				<br><br><br>
			</div>
		</div>
	</div>
</div>

<script>

    $("#riskUpAmplitude").blur(function(){
        var riskUpAmplitude = $("#riskUpAmplitude").val();
        if (riskUpAmplitude>100){
            $("#riskUpAmplitudeLabel").text("请输入一个最大为 100.00 的值");
            $("#riskUpAmplitudeLabel").removeAttr('style');
        }else {
            $("#riskUpAmplitudeLabel").attr("style","display: none");
        }
    });
    $("#riskDownAmplitude").blur(function(){
        var riskUpAmplitude = $("#riskDownAmplitude").val();
        if (riskUpAmplitude>100){
            $("#riskDownAmplitudeLabel").text("请输入一个最大为 100.00 的值")
            $("#riskDownAmplitudeLabel").removeAttr('style');
        }else {
            $("#riskDownAmplitudeLabel").attr("style","display: none");
        }
    });

	function setRatioFee(val){
		if(val==1){
			$("#ratioFeeSwitch").hide();
			$("#ratioFee").val(0)
			$("#ratioFee").attr("readOnly","true");
		}else{
			$("#ratioFeeSwitch").show();
			$("#ratioFee").removeAttr("readonly");
		}
	}

	if(!${admin == 'true'}){
		var customerId = "${customerId}";
		setChannel(parseInt(customerId));
	}


	var isCorrectName = false;
	$(function () {
		//失去焦点时触发的验证事件
		$('#userName').blur(function ()
		{
			var name = $("#userName").val()
			if(name==null||name.length==0||name==""){
				$('#nameTips').text("用户名不可为空");
				return false;
			}
			//验证用户名
			if(!isalphanumber(name)){
				$('#nameTips').text("用户名为5-15位字母或数字组合");
				return false;
			}
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/frontUser/verify",
				"dataType" : "json",
				"data" : {"userName": name},
				"success" : function(result) {
					$('#nameTips').text(result.message);
					if(result.rescode=="success"){
						$('#nameTips').text("");
						isCorrectName = true;
					}else{
						isCorrectName = false;
					}
				},"error" : function (result){
					$('#nameTips').text(result.message)
				}
			});
		})
	});

	/**
	 * 保存用户信息
	 * */
	$("#inputForm").validate({
		rules:{
			userName:{
				required: true,
				rangelength:[5,15]
			},
			realName:{
				required: true,
				rangelength:[1,30]
			},
			password:{
				required: true,
				rangelength:[6,16]
			},
			status:{
				required:true
			},
			warnLine:{
				required: true,
				min:0,
			},
			openLine:{
				required: true,
				min:0,
			},
			customerId:{
				required: true
			},
			singleStockScale:{
				required: true,
				min:0,
				max:1,
			},
			smallStockScale:{
				required: true,
				min:0,
				max:1,
			},
			createStockScale:{
				required: true,
				min:0,
				max:1,
			},
			smallSingleStockScale:{
				required: true,
				min:0,
				max:1,
			},
			createSingleStockScale:{
				required: true,
				min:0,
				max:1,
			},
			ratioFee:{
				required: true,
				min:0,
				max:1,
			},
			minCost:{
				required: true,
				min:0,
			},
			amount:{
				required: true,
				min:0,
			},
			cashAmount:{
				min:0,
			},
			financeAmount:{
				min:0,
			},
		},
		submitHandler: function(form){
			var userName=$("#userName").val();
			//验证用户名
			if(!isalphanumber(userName)){
				alertx("用户名需为5-15位字母或数字组合");
				return false;
			}
			if(!isCorrectName){
				alertx("用户名不可用");
				return false;
			}
            var riskUpAmplitude = $("#riskUpAmplitude").val();
            var riskDownAmplitude = $("#riskDownAmplitude").val();
            if (Number(riskUpAmplitude)>100){
                $("#riskUpAmplitudeLabel").text("请输入一个最大为 100.00 的值");
                $("#riskUpAmplitudeLabel").removeAttr('style');
                return false;
            }
            if (Number(riskDownAmplitude)>100){
                $("#riskDownAmplitudeLabel").text("请输入一个最大为 100.00 的值");
                $("#riskDownAmplitudeLabel").removeAttr('style');
                return false;
            }
			var channels ="";
			var exitFlag = false;
			$("#relatedTable tr").each(function(){

				var channelId = $(this).find("td").eq(0).text();
                var userChannelTypeVal = $(this).find('[name=userChannelTypeVal]').val();
                var quota = $(this).find('input[name=quota]').val();
				var proportion = $(this).find('input[name=proportion]').val();
				var userTradeTypeVal = $('#userTradeTypeVal').val();
				if(channelId==null||channelId.length==0||channelId=="通道编号"){
					return true;
				}
				// 当通道选择限额时
				if(userChannelTypeVal == 1){
					if(!quota){
						exitFlag = true;
						alertx('通道限额标识为限额时,必须输入限额额度!');
						return false;
					}
					if(!$.isNumeric(quota)){
						exitFlag = true;
						alertx('请输入正确的通道限额额度!');
						return false;
					}
				}
				// 当选择比例下单时
				if(userTradeTypeVal == 2){
					if(!proportion){
						exitFlag = true;
						alertx('下单模式为比例下单时,必须输入通道下单比例!');
						return false;
					}
					if(!/^\d+$/.test(proportion)){
						exitFlag = true;
						alertx('通道下单比例必须为整数!');
						return false;
					}
				}
				if(proportion){
					channelId = channelId + "," + userChannelTypeVal + "," + quota + "," + proportion;
				}else{
					channelId = channelId + "," + userChannelTypeVal + "," + quota;
				}
				channels = channels+":"+channelId;
			});
			// 如果有不合法的字段,程序退出
			if(exitFlag){
				return false;
			}
			if(channels==""){
                alertx("通道不可为空,至少选择一个通道");
				return false;
			}
			//高级风控信息提交校验
            var riskFlag = 0;
            if (!$("#limit").is(":checked")){
                $("#riskAmountQuota").val('');
                $("#riskQuantityQuota").val('');
            }else {
                riskFlag += 1;
            }
            if (!$("#quoteChange").is(":checked")){
                $("#riskUpAmplitude").val('');
                $("#riskDownAmplitude").val('');
            }else{
                riskFlag += 2;
            }
            if ($("#blackCheckBox").is(":checked")){
                riskFlag += 4;
                var blackList = "";
                $("#blackListTable table tr").each(function () {
                    var code = $(this).find('td').eq(0).text();
                    if (!$.isNumeric(code)) return true;
                    blackList = blackList + code+":";
                });
                if (blackList == ""){
                    alertx("当前已经启用黑名单模式，请至少限制一支股票！");
                    return false;
				}
                blackList += "end";
                $("#blackList").val(blackList);
            }
            if ($("#whiteCheckBox").is(":checked")){
                riskFlag += 8;
                var whiteList = "";
                $("#whiteListTable table tr").each(function () {
                    var code = $(this).find('td').eq(0).text();
                    if (!$.isNumeric(code)) return true;
                    whiteList = whiteList + code+":";
                });
                if (whiteList == ""){
                    alertx("当前已经启用白名单模式，请至少限制一支股票！")
					return false;
				}
                whiteList += "end";
                $("#whiteList").val(whiteList);
            }
            $("#riskFlag").val(riskFlag);
			$("#channelIds").val(channels);
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/frontUser/save",
				"dataType" : "json",
				"data" : $("#inputForm").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 清空form表单 */
					$(".inputForm .form-control").val("");
					/*跳转*/
					window.location.href="${ctx}/frontUser/userIndex";
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
	});

	function isalphanumber(str){ 
		var result=str.match(/^[a-zA-Z0-9]+$/); 
		if(result==null) return false; 
		return true;
	}

	function setChannel(customerId){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx }/channel/findByCustomerId?customerId="+customerId,
			"dataType" : "json",
			"success" : function(result) {
				var tableRelated = document.createElement("table");
				tableRelated.setAttribute("class","table table-bordered");
				var tbodyRelated = document.createElement("tbody");
				var theadRelated = document.createElement("thead");
				var userTradeTypeVal = $('#userTradeTypeVal').val();
				var tr =  document.createElement("tr");
				var td0 = document.createElement("td");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");

				td0.innerHTML="通道编号";
				td1.innerHTML="通道名称";
				td2.innerHTML="可用金额";
				td3.innerHTML="限额标识";
				td4.innerHTML="限额额度";

				tr.appendChild(td0);
				tr.appendChild(td1);
				tr.appendChild(td2);
				tr.appendChild(td3);
				tr.appendChild(td4);


				var td5 = document.createElement("td");
				td5.innerHTML="下单比例";
				// 当选择比例下单时
				if(userTradeTypeVal == 2){
					td5.style.display = '';
					td5.style.width = '50px;';
				}else{
					td5.style.display = 'none';
				}
				td5.setAttribute("class", "td_proportion");
				tr.appendChild(td5);
				var td6 = document.createElement("td");
				td6.innerHTML="操作";
				tr.appendChild(td6);
				theadRelated.appendChild(tr);
				for(var i = 0 ; i<result.length ;i++){
					var tr =  document.createElement("tr");
					var td0 = document.createElement("td");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");

					td0.innerText=result[i].id;
					td1.innerHTML=result[i].name;
					td2.innerHTML=result[i].cashAvailable;
					td3.innerHTML='<select name="userChannelTypeVal"><option value="0" selected>不限额</option><option value="1">限额</option></select>';
					td4.innerHTML="<input name='quota' style='width:70px;'/>";

					tr.appendChild(td0);
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					var td5 = document.createElement("td");
					td5.innerHTML="<input name='proportion' style='width:45px;'/>";
					if(userTradeTypeVal == 2){
						td5.style.display = '';
						td5.style.width = '50px;';
					}else{
						td5.style.display = 'none';
					}
					td5.setAttribute("class", "text_proportion");
					tr.appendChild(td5);
					var td6 = document.createElement("td");
					tr.appendChild(td6);
					if(result.length>1){
						td6.innerHTML="<button type='button' onclick='up(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-arrow-up'></i></button><button type='button' onclick='down(this)' class='btn btn-info btn-xs'><i class='glyphicon glyphicon-arrow-down'></i></button><button onclick='del(this)' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-minus'></i></button>";
					}
					tbodyRelated.appendChild(tr);
				}
				tableRelated.appendChild(theadRelated);
				tableRelated.appendChild(tbodyRelated);
				$("#relatedTable").empty();
				$("#relatedTable").append(tableRelated);
				generateFreeTable();
				document.getElementById("smallTips").style.display="";
			},"error" : function (result){
				alert("发生异常,或没权限...");
			}
		});
	}
	function generateFreeTable(){
		var tableRelated = document.createElement("table");
		tableRelated.setAttribute("class","table table-bordered");
		tableRelated.setAttribute("id","freeTable");
		var tbodyRelated = document.createElement("tbody");
		var theadRelated = document.createElement("thead");
		var tr =  document.createElement("tr");
		var td0 = document.createElement("td");
		var td1 = document.createElement("td");
		var td2 = document.createElement("td");
		var td3 = document.createElement("td");
		td0.innerHTML="通道编号";
		td1.innerHTML="通道名称";
		td2.innerHTML="可用金额";
		td3.innerHTML="操作";
		tr.appendChild(td0);
		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		theadRelated.appendChild(tr);
		tableRelated.appendChild(theadRelated);
		tableRelated.appendChild(tbodyRelated);
		$("#channelFreeTable").empty();
		$("#channelFreeTable").append(tableRelated);
	}

    $(function () {
        generateStockListTable("#blackListTable");
        generateStockListTable("#whiteListTable");
	});

    function generateStockListTable(tableName){
        var tableRelated = document.createElement("table");
        tableRelated.setAttribute("class","table table-bordered");
        tableRelated.setAttribute("id","freeTable");
        var tbodyRelated = document.createElement("tbody");
        var theadRelated = document.createElement("thead");
        var tr =  document.createElement("tr");
        var td0 = document.createElement("td");
        var td1 = document.createElement("td");
        var td3 = document.createElement("td");
        td0.innerHTML="股票名称";
        td1.innerHTML="股票代码";
        td3.innerHTML="操作";
        tr.appendChild(td0);
        tr.appendChild(td1);
        tr.appendChild(td3);
        theadRelated.appendChild(tr);
        tableRelated.appendChild(theadRelated);
        tableRelated.appendChild(tbodyRelated);
        $(tableName).empty();
        $(tableName).append(tableRelated);
    }

    function addStock(type) {
        if (type == 'black' && !$("#blackCheckBox").is(":checked")){
			alertx("尚未启用黑名单限制功能");
			return false;
		}
		if (type == 'white' && !$("#whiteCheckBox").is(":checked")){
			alertx("尚未启用白名单限制功能");
			return false;
		}
		$("#typeIn").val(type);
		$("#modelStock").modal();
    }
    
    function addStockCode() {
        var type = $("#typeIn").val();
        var tableName;
        if (type == "black"){
			tableName = "#blackListTable";
		}else if (type == "white"){
            tableName = "#whiteListTable";
		}else {
		    return false;
		}
        var addStockCode = $("#stockCode").val();
		var sus = false;
        $("#blackListTable tr").each(function () {//过滤重复添加股票
            if ($(this).find('td').eq(0).text() == addStockCode){
                $("#tipsStockCode").text("该股票已经存在规则列表中,不允许重复添加")
                sus = true;
                return false;
            }
        });
        $("#whiteListTable tr").each(function () {//过滤重复添加股票
            if ($(this).find('td').eq(0).text() == addStockCode) {
                $("#tipsStockCode").text("该股票已经存在规则列表中,不允许重复添加")
                sus = true;
            }
        });
        if (sus){	//解决回掉函数不能跳出方法
            return false;
		}
        $.ajax({
            "type" : 'post',
            "url" : "${ctx}/stock/getStockName",
            "dataType" : "json",
			"async" : false,
            "data" : {"stockCode": addStockCode},
            "success" : function(result) {
               if (result==""||result==null){
                   $("#tipsStockCode").text("该股票不存在，请检查输入是否有误！");
				   return false;
			   }else{
                   $("#modelStock").modal('hide');
                   $("#tipsStockCode").val("");
                   $("#typeIn").val("");
                   $("#stockCode").val("");
                   $("#tipsStockCode").text("");
			   }
			   addToTable(addStockCode,result,tableName)
            },"error" : function (result){
                $('#tipsStockCode').text(result)
            }
        });
    }

    function addToTable(code,name,tableName) {
        var tableHtml = "<tr><td>"+code+"</td><td>"+name+"</td><td><button onclick='removeStock(this)' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-minus'></i></button></td></tr>";
        $(tableName+" table").append(tableHtml);
    }

    function  removeStock(obj) {
        var objParentTR=$(obj).parent().parent("tr");
        objParentTR.remove();
    }

    
	function up(obj)
	{
		var objParentTR=$(obj).parent().parent();
		var prevTR=objParentTR.prev();
		if(prevTR.length>0)
		{
			prevTR.insertAfter(objParentTR);
		}
	}
	function down(obj)
	{
		var objParentTR=$(obj).parent().parent();
		var nextTR=objParentTR.next();
		if(nextTR.length>0)
		{
			nextTR.insertBefore(objParentTR);
		}
	}
	function del(obj){
		var objParentTR=$(obj).parent().parent("tr");
		var no = objParentTR.find("td").eq(0).text();
		var name = objParentTR.find("td").eq(1).text();
		var amount = objParentTR.find("td").eq(2).text();
		$("#freeTable").append("<tr><td>"+no+"</td><td>"+name+"</td><td>"+amount+"</td><td><button type='button' onclick='add(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-plus'></i></button></td></tr>");
		objParentTR.remove();
	}
	function add(obj){
		var objParentTR=$(obj).parent().parent("tr");
		var no = objParentTR.find("td").eq(0).text();
		var name = objParentTR.find("td").eq(1).text();
		var amount = objParentTR.find("td").eq(2).text();
		var userTradeTypeVal = $('#userTradeTypeVal').val();
		var tableHtml = "<tr><td>"+no+"</td><td>"+name+"</td><td>"+amount+"</td><td><select name='userChannelTypeVal'><option value='0' selected>不限额</option><option value='1'>限额</option></select></td><td><input name='quota' style='width:70px;'/></td>";
		// 当选择比例下单时
		if(userTradeTypeVal == 2){
			tableHtml = tableHtml + "<td class='text_proportion' ><input name='proportion'style='width:45px;'/></td>";
		}else{
			tableHtml = tableHtml + "<td class='text_proportion' style='display:none;'><input name='proportion'style='width:45px;'/></td>";
		}
			tableHtml = tableHtml + "<td><button type='button' onclick='up(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-arrow-up'></i></button><button type='button' onclick='down(this)' class='btn btn-info btn-xs'><i class='glyphicon glyphicon-arrow-down'></i></button><button onclick='del(this)' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-minus'></i></button></td></tr>";
		$("#relatedTable table").append(tableHtml);
		objParentTR.remove();
	}

	function tradeTypeChange(){
		var userTradeTypeVal = $('#userTradeTypeVal').val();
		// 当选择比例下单时
		if(userTradeTypeVal == 2){
			$('.td_proportion').show();
			$('.text_proportion').show();
		}else{
			$('.td_proportion').hide();
			$('.text_proportion').hide();
		}
	}

</script>
</body>
</html>