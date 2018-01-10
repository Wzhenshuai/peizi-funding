<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<style>
		body {  padding : 10px ;  }
		#exTab1 .tab-content {  width: auto;  padding : 5px 65px 5px 15px;  }
	</style>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>用户详情
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<form:form id="inputForm" modelAttribute="user" class="form-horizontal">
				<div class="panel panel-info col-sm-10 col-sm-offset-1">
					<input type="hidden" id="id" value="${user.id}" name="id"/>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">用户名:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.userName}</label>
							</div>
							<label class="col-sm-2 control-label">真实姓名:</label>
							<div class="col-sm-2">
								<input type="text" value="${user.realName}" disabled name="realName" class="form-control autoChange" htmlEscape="false" maxlength="100"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">用户类型:</label>
							<div class="col-sm-2">
								<label class="radio-inline"><input id="normalUser" type="radio" name="userClassTypeVal" value="0">普通</label>
								<label class="radio-inline"><input id="advancedUser" type="radio" name="userClassTypeVal" value="1">高级</label>
							</div>
							<label class="col-sm-2 control-label">状态:</label>
							<div class="col-sm-4">
								<label class="radio-inline"><input id="normal" type="radio" name="status" value="0">正常</label>
								<label class="radio-inline"><input id="BuySelllocked" type="radio" name="status" value="1">锁买卖</label>
								<label class="radio-inline"><input id="BugLocked" type="radio" name="status" value="3">锁买</label>
								<label class="radio-inline"><input id="logout" type="radio" name="status" value="2">停用</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">资金方:</label>
							<div class="col-sm-2"><input type="hidden" id="customerId" value="${user.customer.id}">
								<label class="control-label">${user.customer.name}</label>
							</div>
							<label class="col-sm-2 control-label">登陆次数:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.loginCount}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">创建时间:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.createTimeStr}</label>
							</div>
							<label class="col-sm-2 control-label">上次登陆:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.lastLoginTimeStr}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">总资产:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.amount}</label>
							</div>
							<label class="col-sm-2 control-label">股票市值:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.totalMaketValue}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">可用金额:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.available}</label>
							</div>
							<label class="col-sm-2 control-label">冻结金额:</label>
							<div class="col-sm-2">
								<label class="control-label">${user.frozen}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">本金金额:</label>
							<div class="col-sm-2">
								<input type="number" disabled value="${user.cashAmount}" name="cashAmount" class="form-control autoChange" htmlEscape="false" maxlength="100"/>
							</div>
							<label class="col-sm-2 control-label">融资金额:</label>
							<div class="col-sm-2">
								<input type="number" disabled value="${user.financeAmount}" name="financeAmount" class="form-control autoChange" htmlEscape="false" maxlength="100"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">警戒线:</label>
							<div class="col-sm-2">
								<label class="control-label" id="warnLine">${user.warnLine}</label>
							</div>
							<label class="col-sm-2 control-label">平仓线:</label>
							<div class="col-sm-2">
								<label class="control-label" id="openLine">${user.openLine}</label>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-9 col-md-offset-3">
					<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-4">
							<input type="hidden" id="sbbtn" class="btn btn-danger btn-sm" value="保 存">
							<input type="button" id="modifButton" onclick="swithBtn()" class="btn btn-danger btn-sm" value="修 改"/>
							<input type="button" onclick="history.go(0)" class="btn btn-primary btn-sm" value="刷 新"/>
							<input type="button" onclick="history.go(-1)" class="btn btn-default btn-sm" value="返 回"/>
						</div>
					</div>
				</div>
				<div id="exTab1" class="container col-sm-offset-1">
					<div class="tab-content clearfix">
						<ul  class="nav nav-tabs">
							<li class="active"><a href="#1a" data-toggle="tab">用户持仓</a>
							</li>
							<li id="4ali"><a href="#4a" data-toggle="tab">通道持仓</a>
							</li>
							<li><a href="#5a" data-toggle="tab">通道管理</a>
							</li>
							<li><a href="#2a" data-toggle="tab">风控管理</a>
							</li>
							<li><a href="#3a" data-toggle="tab">佣金管理</a>
							</li>
							<li><a href="#6a" data-toggle="tab">高级风控</a>
							</li>
							<li><a href="#7a" data-toggle="tab">关联用户</a>
							</li>
							<li><a href="#8a" data-toggle="tab">重置密码</a>
							</li>
						</ul>
						<div class="tab-pane active" id="1a">
							<div class="col-md-1 pull-right">
								<input type="hidden" value="${user.id}" name="userId" id="userId">
								<input onclick="reloadTableById('upTable')" type="button" class="btn btn-danger" value="刷新">
							</div>
							<table:table id="upTable" ajaxURI="${ctx}/position/findUserPosition"
										 columnNames="securityName,securityCode,marketValue,marketProfit,marketProfitPercent,amount,availableAmount,latestPrice,costPriceStr,suspensionDisplay"
										 headerNames="证券名称,证券代码,股票市值,浮动盈亏,盈亏比例(%),持股数量,持股可用,现价,成本价,停牌标识"
										 searchInputNames="customerId,userId"
							/>
						</div>
						<div class="tab-pane" id="4a">
							<div class="col-md-2 pull-right">
								<a href="${ctx}/channel/addForUser?userId=${user.id}">
									<button type='button' id="add" class='btn btn-danger'>
										添加
									</button>
								</a>
								<input onclick="reloadTableById('cpTable')" type="button" class="btn btn-danger" value="刷新">
							</div>
							<input type="hidden" value="${user.id}" name="userCPId">
							<table:table id="cpTable"
										 ajaxURI="${ctx}/position/findChannelPosition"
										 columnNames="securityName,securityCode,marketValue,marketProfit,marketProfitPercent,amount,availableAmount,latestPrice,costPriceStr,suspensionDisplay,channelName"
										 headerNames="证券名称,证券代码,股票市值,浮动盈亏,盈亏比例(%),持股数量,持股可用,现价,成本价,停牌标识,通道名称,操作"
										 searchInputNames="userCPId,customerId"
										 makeDefsHtmlFunc="defs"/>
						</div>
						<div class="tab-pane" id="2a">
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">警戒线:</label>
								<div class="col-sm-4">
									<input type="number" value="${user.warnLine}" name="warnLine" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">平仓线:</label>
								<div class="col-sm-4">
									<input type="number" value="${user.openLine}" name="openLine" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">单支股票比例:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="${user.singleStockScale}" name="singleStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">中小板单支股票比例:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="${user.smallSingleStockScale}" name="smallSingleStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">中小板比例:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="${user.smallStockScale}" name="smallStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">创业板单支比例:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="${user.createSingleStockScale}" name="createSingleStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">创业板比例:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="${user.createStockScale}" name="createStockScale" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="3a">
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">是否使用默认费率:</label>
								<div class="col-sm-4">
									<label class="radio-inline"><input id="radioFee1" onclick="defaultFee(this.value)" type="radio" name="isDefaultFee" value="1">是</label>
                                    <label class="radio-inline"><input id="radioFee2" onclick="defaultFee(this.value)"
                                                                       type="radio" name="isDefaultFee"
                                                                       value="0">否</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">收费比例:</label>
								<div class="col-sm-4">
									<input type="number" step="0.0001" value="${user.ratioFee}" name="ratioFee" id="ratioFee" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">佣金最低额度:</label>
								<div class="col-sm-4">
									<input type="number" value="${user.minCost}" name="minCost" id="minCost" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="5a">
							<div class="form-group">
								<label class="col-sm-2 control-label">通道下单模式<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<select class="form-control" name="userTradeTypeVal" id="userTradeTypeVal" value="${user.userTradeType.code}" _value="${user.userTradeType.code}" onchange="tradeTypeChange()">
										<option value="0" <c:if test="${user.userTradeType.code == '0'}">selected="selected" </c:if>>优先下单</option>
										<option value="1" <c:if test="${user.userTradeType.code == '1'}">selected="selected" </c:if>>平分下单</option>
										<option value="2" <c:if test="${user.userTradeType.code == '2'}">selected="selected" </c:if>>比例下单</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">当前通道:</label>
								<div class="col-sm-7">
									<div id="channelTable" class="table-responsive"></div>
									<input id="channelIds" type="hidden" value="" name="channelIds"/>
									<small id="smallTips" style="color: red">备注：优先级从高往低排序</small>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">未分配通道:<br><br></label>
								<div class="col-sm-7">
									<div id="channelFreeTable" class="table-responsive"></div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="6a">
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
												<input type="number" id="riskAmountQuota" value="${user.riskAmountQuota}" min="0" step="0.01" name="riskAmountQuota" class="form-control" htmlEscape="false" maxlength="100"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">数量上限:</label>
											<div class="col-sm-4">
												<input type="number" id="riskQuantityQuota" value="${user.riskQuantityQuota}" min="0" step="0.01" name="riskQuantityQuota" class="form-control" htmlEscape="false" maxlength="100"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-10 control-label" style="color: red">提示：如果限制金额设置为0，表示对应的金额未作限制</label>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6"><input type="hidden" id="riskFlag" name="riskFlag" value="${user.riskFlag}">
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
												<input type="number" value="${user.riskUpAmplitude}" min='0' step="0.01" id="riskUpAmplitude" name="riskUpAmplitude" class="form-control" htmlEscape="false" maxlength="100"/>
                                            	<label id="riskUpAmplitudeLabel" class="error" style="display: none"></label>
											</div>
                                            <span class="help-inline" style="color: red">%</span>
										</div>
										<div class="form-group">
											<label class="col-sm-5 control-label">跌停振幅比例限制:</label>
											<div class="col-sm-4">
												<input type="number" value="${user.riskDownAmplitude}" min='0' step="0.01" id="riskDownAmplitude" name="riskDownAmplitude" class="form-control" htmlEscape="false" maxlength="100"/>
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
											<div class="col-sm-6">
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
											<div class="col-sm-6">
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
						<div class="tab-pane" id="7a">
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-2 control-label">关联账户:</label>
								<div class="col-sm-4">
									<div id="relatedTable" class="table-responsive"></div>
								</div>
								<label class="col-sm-1 control-label"><span id="btnRelated" class="btn btn-primary btn-xs">添加关联</span></label>
							</div>
						</div>
						<div class="tab-pane" id="8a">
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-3 control-label">用户名<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-3">
									<input value="${user.userName}" readonly class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-3 control-label" for="passWord">密码：</label>
								<div class="col-sm-3">
									<input  type="password"  id="passWord" class="form-control" placeholder="请输入新密码" aria-describedby="basic-addon1">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<label class="col-sm-3 control-label" for="rePassWord">确认密码：</label>
								<div class="col-sm-3">
									<input  type="password"  id="rePassWord" class="form-control" placeholder="请输再次入新密码" aria-describedby="basic-addon1">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-5 control-label"></label>
								<div class="col-sm-6">
									<label class="col-sm-1 control-label"></label>
									<input type="button" onclick="updatePWD()" class="btn btn-primary" value="重 置">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-5 control-label"></label>
								<label class="col-sm-2 control-label" id="pwdTips" style="align-content: center;color: red"></label>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>


<!-- 定义增加关联用户 -->
<div class="modal fade" id="modelRelated">
    <div class="modal-dialog" style="padding-left:20px;text-align:center;">t
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加关联用户</h4>
			</div>
			<div class="modal-body form-horizontal">
				<div class="form-group">
					<label class="col-sm-3 control-label">用户账号</label>
					<div class="col-sm-6">
						<select id="userIdRelated" class="form-control">
						</select>
						<%--<input id="userIdRelated" class="form-control" type="text" value="" placeholder="请输入用户名">--%>
					</div>
					<div class="col-sm-3">
						<input  id="btnRelatedConfirm" class="btn btn-primary" type="submit" value="确定" onclick='return addRelatedUser("${user.id}")'/>
					</div>
				</div><br>
				<div class="form-group">
					<label id="tipsRelated" style="align-content: center;color: red"></label>
				</div>
				<br><br><br>
			</div>
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
	window.onload = riskSet();
	function riskSet() {
		var amount = ${user.amount};
		var openLine = ${user.openLine};
		var warnLine = ${user.warnLine};
		if (amount<=openLine){
            $("#openLine").css("color","red");
		}
		if (amount <= warnLine){
            $("#warnLine").css("color","red");
		}
    }

    $('#4ali').click(function () {
        reloadTableById('cpTable');
    });

    function defs(rowdata) {
        var str= "<a href='${ctx}/position/edit?id="+rowdata.id+"'><input type='button'  class='btn btn-success' value='调仓'></a>"+
			"<a href='${ctx}/position/movePosition?id="+rowdata.id+"'> <input type='button' class='btn btn-info' value='持仓转移'></a>"+
            "<a href='${ctx}/position/editPrice?id="+rowdata.id+"'><input type='button' class='btn btn-primary' value='调成本价'></a>";
        return str;
    }

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

    function swithBtn() {
		$(".autoChange").removeAttr("disabled");
        $("#modifButton").hide();
        $("#sbbtn").attr("type","submit");
        $("#sbbtn").show();
    }

    function updatePWD() {
		var pwd = $("#passWord").val();
		var re_pwd = $("#rePassWord").val();
		var userIdPWD = $("#id").val();
		if (pwd.length==0||re_pwd.length==0||pwd != re_pwd){
			$("#pwdTips").text('密码输入输入有误');
		    return false;
		}
        $.ajax({
            "type" : 'post',
            "url" : "${ctx}/frontUser/updatePwd",
            "dataType" : "json",
            "data" : {
				"id":userIdPWD,
				"passWord":pwd,
				"rePassword":re_pwd
			},
            "success" : function(result) {
                alertx(result.message);
                window.location.reload();
            },"error" : function (result){
                alertx("发生异常,或没权限...");
            }
        });
    }

	var userId = $("#id").val();
	var customerId = $("#customerId").val();
	setCurrentChannel(userId);
	setFreeChannel(userId,customerId);
	function setCurrentChannel(userId){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx }/channel/findByUserId?userId="+userId,
			"dataType" : "json",
			"success" : function(result) {
				var tableRelated = document.createElement("table");
				tableRelated.setAttribute("class","table table-bordered");
				tableRelated.setAttribute("id","currentTable");
				var tbodyRelated = document.createElement("tbody");
				var theadRelated = document.createElement("thead");
				var userTradeTypeVal = $('#userTradeTypeVal').val();
				var tr =  document.createElement("tr");
				var td0 = document.createElement("td");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
                var td6 = document.createElement("td");
				td1.innerHTML="通道名称";
				td2.innerHTML="可用金额";
				td3.innerHTML="限额标识";
                td4.innerHTML="限额额度";
                td6.innerHTML="操作";
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
					if(result[i].userChannelTypeVal == '1'){
						td3.innerHTML='<select id="userChannelTypeVal" name="userChannelTypeVal"><option value="0">不限额</option><option value="1" selected>限额</option></select>';
					}else{
						td3.innerHTML='<select id="userChannelTypeVal" name="userChannelTypeVal"><option value="0" selected>不限额</option><option value="1">限额</option></select>';
					}

					td4.innerHTML="<input name='quota' style='width:70px;' value='" + result[i].quota + "'/><input type='hidden' name='userChannelTypeValHidden' value='" + result[i].userChannelTypeVal +"'/><input type='hidden' name='quotaHidden' value='"+result[i].quota +"'/>";

					tr.appendChild(td0);
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					var userTradeTypeVal = $('#userTradeTypeVal').val();
					var td5 = document.createElement("td");
					if(result[i].proportion || result[i].proportion==0){
						td5.innerHTML="<input name='proportion' style='width:45px;' value='" + result[i].proportion + "'/><input type='hidden' name='proportionHidden' value='" + result[i].proportion +"'/>";
					}else{
						td5.innerHTML="<input name='proportion' style='width:45px;' /><input type='hidden' name='proportionHidden' value=''/>";
					}
					td5.style.width = '50px;';
					// 当选择比例下单时
					if(userTradeTypeVal == 2){
						td5.style.display = '';
					}else{
						td5.innerHTML="<input name='proportion' style='width:45px;' /><input type='hidden' name='proportionHidden' value=''/>";
						td5.style.display = 'none';
					}
					td5.setAttribute("class", "text_proportion");
					tr.appendChild(td5);
					var td6 = document.createElement("td");
					tr.appendChild(td6);
					td6.innerHTML="<button type='button' onclick='up(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-arrow-up'></i></button><button type='button' onclick='down(this)' class='btn btn-info btn-xs'><i class='glyphicon glyphicon-arrow-down'></i></button><button onclick='del(this)' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-minus'></i></button>";
					tbodyRelated.appendChild(tr);
				}
				tableRelated.appendChild(theadRelated);
				tableRelated.appendChild(tbodyRelated);
				$("#channelTable").empty();
				$("#channelTable").append(tableRelated);
			},"error" : function (result){
				alert("发生异常,或没权限...");
			}
		});
	}

	function setFreeChannel(userId,customerId){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx }/channel/findByCustomerIdNotInUserId?customerId="+customerId+"&userId="+userId,
			"dataType" : "json",
			"success" : function(result) {
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
				td1.innerHTML="通道名称";
				td2.innerHTML="可用金额";
				td3.innerHTML="操作";
				tr.appendChild(td0);
				tr.appendChild(td1);
				tr.appendChild(td2);
				tr.appendChild(td3);
				theadRelated.appendChild(tr);
				for(var i = 0 ; i<result.length ;i++){
					var tr =  document.createElement("tr");
					var td0 = document.createElement("td");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					td0.innerText=result[i].id;
					td1.innerHTML=result[i].name;
					td2.innerHTML=result[i].cashAvailable;
					tr.appendChild(td0);
					tr.appendChild(td1);
					tr.appendChild(td2);
					var td3 = document.createElement("td");
					tr.appendChild(td3);
					td3.innerHTML="<button type='button' onclick='addC(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-plus'></i></button>";
					tbodyRelated.appendChild(tr);
				}
				tableRelated.appendChild(theadRelated);
				tableRelated.appendChild(tbodyRelated);
				$("#channelFreeTable").empty();
				$("#channelFreeTable").append(tableRelated);
			},"error" : function (result){
				alert("发生异常,或没权限...");
			}
		});
	}

	setRelateUser();
	function setRelateUser(){
		var customerId = parseInt("${user.customer.id}");
		$.ajax({
			"type" : 'post',
			"url" : "${ctx }/frontUser/findByCustomerId?customerId="+customerId,
			"dataType" : "json",
			"success" : function(result) {
				var arr = [];
				for(var i = 0 ; i < result.length ;i++){
					var data = {};
					data["txt"] = result[i].userName;
					data["val"] = result[i].id;
					arr.push(data);
					data = null;
				}
				if (arr.length==1){
					setSelectOption('userIdRelated', arr, '-请选择-',arr[0].val);
				}else {
					setSelectOption('userIdRelated', arr, '-请选择-');
				}
			},"error" : function (result){
				alert("发生异常,或没权限...");
			}
		});
	}

	//设置限制默认费率
	function defaultFee(obj){
		if(obj=='1'){
			$('#ratioFee').attr("readOnly","true");
		}else{
			$('#ratioFee').removeAttr('readOnly');
		}
	}

	$(function(){
		var isDefaultF = $("input[name='isDefaultFee']:checked").val();
		if(isDefaultF=='1'){
			$('#ratioFee').attr("readOnly","true");
			$('#minCost').attr("readOnly","true");
		}
	});

	//增加关联账户
	function addRelatedUser(userId){
		var idB = $("#userIdRelated").val();
		if(idB=="" || idB.length==0){
			$("#tipsRelated").text("账户不可为空！");
			return false;
		}
		if(idB=="${user.id}"){
			$("#tipsRelated").text("不可关联自身！");
			return false;
		}
		$.ajax({
			"type" : 'post',
			"url" : "${ctx}/frontUser/addRelatedUser",
			"dataType" : "json",
			"data" : {
				"idB": idB,
				"idA":"${user.id}"
			},
			"success" : function(result) {
				$("#modelRelated").modal('hide');
				alertx(result.message);
				window.location.reload();
			},"error" : function (result){
				$("#tipsRelated").text(result.message);
			}
		});
	}

	//取关
	function unlink(userId){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx}/frontUser/unlink",
			"dataType" : "json",
			"data" : {"userId": userId},
			"success" : function(result) {
				alertx(result.message);
				window.location.reload();
			},"error" : function (result){
				alertx(result.message);
			}
		});
	}

	$(document).ready(function(){

		$.ajax({
			"type" : 'post',
			"url" : "${ctx}/frontUser/findRelatedUsers",
			"dataType" : "json",
			"data" : {"userId": "${user.id}"},
			"success" : function(result) {
				var tableRelated = document.createElement("table");
				tableRelated.setAttribute("class","table table-bordered");
				var tbodyRelated = document.createElement("tbody");
				var theadRelated = document.createElement("thead");
				for(var i = 0 ; i<result.length ;i++){
					var tr =  document.createElement("tr");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					td1.innerHTML="<a href='${ctx}/frontUser/edit?userId="+result[i].id+"'>"+result[i].userName+"</a>";
					td2.innerHTML="<span class='btn btn-danger btn-xs' onclick='unlink("+result[i].id+")'>取消关联</span>";
					tr.appendChild(td1);
					tr.appendChild(td2);
					tbodyRelated.appendChild(tr);
				}
				tableRelated.appendChild(tbodyRelated);
				$("#relatedTable").append(tableRelated);
			},"error" : function (result){
				alertx("error");
			}
		});
	});

	$(document).ready(function() {
		$("#btnChannel").click(function(){
			$("#modelForChannel").modal();
		});
		$("#btnRelated").click(function(){
			$("#modelRelated").modal();
		});
		$("#btnEdit").click(function(){
			var channelName = $("#channnelId option:selected").text();
			var channelValue = $("#channnelId option:selected").val();
			$("#channelId").attr("value",channelValue);
			$("#channelLabel").text(channelName);
			$("#modelForChannel").modal('hide');
		});
	});



	chooseRatio();
	chooseStatus();
    chooseUserClassType();
	//选中是否使用默认费率的单选框
	function chooseRatio(){
		var t = "${user.isDefaultFee}";
		if(t=="1"){
			document.getElementById("radioFee1").setAttribute("checked","true");
		}else {
			document.getElementById("radioFee2").setAttribute("checked","true");
		}
	}

    //选择用户类型的单选框
    function chooseUserClassType(){
        var temp = "${user.userClassTypeVal}";
        console.log(temp)
        switch(temp){
            case "1":document.getElementById("advancedUser").setAttribute("checked","true");
                break;
            default :document.getElementById("normalUser").setAttribute("checked","true");
                break;
        }
    }

	//选择用户状态的单选框
	function chooseStatus(){
		var temp = "${user.status}";
		switch(temp){
			case "1":document.getElementById("BuySelllocked").setAttribute("checked","true");
				break;
			case "2":document.getElementById("logout").setAttribute("checked","true");
				break;
            case "3":document.getElementById("BugLocked").setAttribute("checked","true");
                break;
			default :document.getElementById("normal").setAttribute("checked","true");
				break;
		}
	}

	/**
	 * 保存用户信息
	 * */
	$("#inputForm").validate({
		rules:{
			singleStockScale:{
				required:true,
				range:[0,1]
			},
			smallSingleStockScale:{
				required:true,
				range:[0,1]
			},
			smallStockScale:{
				required:true,
				range:[0,1]
			},
			createSingleStockScale:{
				required:true,
				range:[0,1]
			},
			createStockScale:{
				required:true,
				range:[0,1]
			},
			warnLine:{
				required:true,
				min:0
			},
			openLine:{
				required:true,
				min:0
			},
			minCost:{
				required:true,
				min:0
			},

		},
		submitHandler: function(form){
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
			$("#channelTable tr").each(function(){
				var channelId = $(this).find("td").eq(0).text();
				var userChannelTypeVal = $(this).find('[name=userChannelTypeVal]').val();
				var quota = $(this).find('input[name=quota]').val();
				var proportion = $(this).find('input[name=proportion]').val();
				var userTradeTypeVal = $('#userTradeTypeVal').val();
				if(channelId==null||channelId.length==0){
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
            //高级风控信息提交校验
            var riskFlag = 0;
            if (!$("#limit").is(":checked")){
//                $("#riskAmountQuota").val(0);
//                $("#riskQuantityQuota").val(0);
            }else {
                riskFlag += 1;
            }
            if (!$("#quoteChange").is(":checked")){
//                $("#riskUpAmplitude").val(0);
//                $("#riskDownAmplitude").val(0);
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
			if(channels==""){
                alertx("通道不可为空");
				return false;
			}
			$("#channelIds").val(channels);
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/frontUser/update",
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



    $(function () {
        //init checkbox
		var riskFlag = $("#riskFlag").val();
		console.log(riskFlag);
		if ((riskFlag & 8) == 8){
		    $("#whiteCheckBox").prop("checked",true);
		}
		if ((riskFlag & 4) == 4){
		    $("#blackCheckBox").prop("checked",true);
		}
		if ((riskFlag & 2) == 2){
		    $("#quoteChange").prop("checked",true);
		}
		if ((riskFlag & 1) == 1){
            $("#limit").prop("checked",true);
		}
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
        td0.innerHTML="股票代码";
        td1.innerHTML="股票名称";
        td3.innerHTML="操作";
        tr.appendChild(td0);
        tr.appendChild(td1);
        tr.appendChild(td3);
        theadRelated.appendChild(tr);
        $.ajax({
            "type" : 'post',
            "url" : "${ctx}/stock/getStock",
            "dataType" : "json",
            "async" : false,
            "data" : {
                "userId": userId,
				"type" : tableName
            },
            "success" : function(result) {
                for (var i = 0 ; i < result.length ; i++){
                    var trr = document.createElement("tr");
                    var tdd0 = document.createElement("td");
                    var tdd1 = document.createElement("td");
                    var tdd3 = document.createElement("td");
                    tdd0.innerHTML=result[i].stockCode;
                    tdd1.innerHTML=result[i].stockName;
                    tdd3.innerHTML="<button onclick='removeStock(this)' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-minus'></i></button>";
                    trr.appendChild(tdd0);
                    trr.appendChild(tdd1);
                    trr.appendChild(tdd3);
                    tbodyRelated.appendChild(trr);
				}
            },"error" : function (result){
                    console.log(result);
            }
        });
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
                $("#tipsStockCode").text("该股票已经存在规则列表中")
                sus = true;
                return false;
            }
        });
        $("#whiteListTable tr").each(function () {//过滤重复添加股票
            if ($(this).find('td').eq(0).text() == addStockCode){
                $("#tipsStockCode").text("该股票已经存在规则列表中")
                sus = true;
                return false;
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
		var quotaHidden = objParentTR.find("td").eq(4).find(":hidden[name=quotaHidden]").val();
		var proportionHidden = objParentTR.find("td").eq(5).find(":hidden[name=proportionHidden]").val();
		var userChannelTypeValHidden = objParentTR.find("td").eq(4).find(":hidden[name=userChannelTypeValHidden]").val();
		$("#freeTable").append("<tr><td>"+no+"<input type='hidden' name='userChannelTypeValHidden' value='" + userChannelTypeValHidden +"'/><input type='hidden' name='quotaHidden' value='"+ quotaHidden +"'/><input type='hidden' name='proportionHidden' value='"+ proportionHidden +"'/></td><td>"+name+"</td><td>"+amount+"</td><td><button type='button' onclick='addC(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-plus'></i></button></td></tr>");
		objParentTR.remove();
	}

	function addC(obj){
		var objParentTR=$(obj).parent().parent("tr");
		var no = objParentTR.find("td").eq(0).text();
		var quotaHidden = objParentTR.find("td").eq(0).find(":hidden[name=quotaHidden]").val();
		if(quotaHidden ===undefined){
			quotaHidden = '0';
		}

		var proportionHidden = objParentTR.find("td").eq(0).find(":hidden[name=proportionHidden]").val();
		if(proportionHidden ===undefined){
			proportionHidden = '';
		}
		var userChannelTypeValHidden = objParentTR.find("td").eq(0).find(":hidden[name=userChannelTypeValHidden]").val();
		var td3 = '';
		if(userChannelTypeValHidden == '1'){
			td3='<select id="userChannelTypeVal" name="userChannelTypeVal"><option value="0">不限额</option><option value="1" selected>限额</option></select>';
		}else{
			td3='<select id="userChannelTypeVal" name="userChannelTypeVal"><option value="0" selected>不限额</option><option value="1">限额</option></select>';
		}
		var td4="<input name='quota' style='width:70px;' value='" + quotaHidden + "'/><input type='hidden' name='userChannelTypeValHidden' value='" + userChannelTypeValHidden +"'/><input type='hidden' name='quotaHidden' value='"+quotaHidden +"'/>";
		var userTradeTypeVal = $('#userTradeTypeVal').val();
		var name = objParentTR.find("td").eq(1).text();
		var amount = objParentTR.find("td").eq(2).text();
		var tableHtml = "<tr><td>"+no+"</td><td>"+name+"</td><td>"+amount+"</td><td>"+td3+"</td><td>"+td4+"</td>";
		var td5="<input name='proportion' style='width:45px;' value='" + proportionHidden + "'/><input type='hidden' name='proportionHidden' value='" + proportionHidden +"'/>";
		// 当选择比例下单时
		if(userTradeTypeVal == 2){
			tableHtml = tableHtml + "<td class='text_proportion'>"+td5+"</td>";
		}else{
			tableHtml = tableHtml + "<td class='text_proportion' style='display:none;'>"+td5+"</td>";
		}
	    tableHtml = tableHtml + "<td><button type='button' onclick='up(this)' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-arrow-up'></i></button><button type='button' onclick='down(this)' class='btn btn-info btn-xs'><i class='glyphicon glyphicon-arrow-down'></i></button><button onclick='del(this)' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-minus'></i></button></td></tr>";
		$("#currentTable").append(tableHtml);
		objParentTR.remove();
	}

	//联动菜单:选择券商，后面对应出现相应的营业部信息，券商服务器信息
	/*
	 * 说明：将指定下拉列表的选项值清空
	 * 作者：royleo.xyz
	 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须
	 */
	function removeOptions(selectObj){
		if (typeof selectObj != 'object')
		{
			selectObj = document.getElementById(selectObj);
		}
		for (var i=0; i < selectObj.options.length; i++)
		{
			// 移除当前选项
			selectObj.options[0] = null;
		}
	}
	/*
	 * 说明：设置传入的选项值到指定的下拉列表中
	 * 作者：royleo.xyz
	 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须
	 * @param {Array} optionList 选项值设置 格式：[{txt:'北京', val:'010'}, {txt:'上海', val:'020'}] ，必须
	 * @param {String} firstOption 第一个选项值，如：“请选择”，可选，值为空
	 * @param {String} selected 默认选中值，可选
	 */
	function setSelectOption(selectObj, optionList, firstOption, selected)
	{
		if (typeof selectObj != 'object')
		{
			selectObj = document.getElementById(selectObj);
		}
		removeOptions(selectObj); // 清空选项
		var start = 0;   // 选项计数
		var len = optionList.length;
		if (firstOption)  // 如果需要添加第一个选项
		{
			selectObj.options[0] = new Option(firstOption, '');
			start ++;   // 选项计数从 1 开始
		}
		for (var i=0; i < len; i++)
		{

			selectObj.options[start] = new Option(optionList[i].txt, optionList[i].val); // 设置 option
			if(selected == optionList[i].val)  // 选中项
			{
				selectObj.options[start].selected = true;
			}
			start ++;  // 计数加 1
		}

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
<init:init-js/>
</body>
</html>