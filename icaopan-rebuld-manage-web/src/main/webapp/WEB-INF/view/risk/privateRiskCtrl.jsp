<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>风控管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>风控管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >交易账号</span>
						<input type="text" class="form-control" name="tradeAccount" id="tradeAccount-search" placeholder="交易账号" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >状态</span>
						<select class="form-control" name="status" id="status-search">
							<option value="Normal">正常</option>
							<option value="Invalid">锁定</option>
						</select>
					</div>
				</div>
				<div class="col-md-1">
					<div class="input-group">
						自动刷新：<br/>
						<input type="checkbox" id="autoFresh" checked="true"/>
					</div>
				</div>
				<div class="col-md-6" style="float:right;">
					<%--<button class="btn btn-primary" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>自动刷新</button>--%>
					<button  class="btn btn-primary" onclick="reloadRiskTableData()"><i class="glyphicon glyphicon-search"></i>  查 询</button>
					<button class="btn btn-primary" onclick="modifyRiskCtrlInfoForm()">编辑信息</button>
					<button class="btn btn-primary" onclick="queryRiskCtrlInfoForm()">明细查询</button>
					<a href="${ctx}/privateRiskCtrl/addRiskCtrlInfo"><button class="btn btn-primary">添加账户</button></a>
						<c:if test="${isAdmin==true }">
						<button  class="btn btn-primary" onclick="downloadFillSummary()"><i class="glyphicon glyphicon-save"></i>下载所有通道成交汇总</button>
					</c:if>
				</div>

			</div>
			<form id="riskCtrlForm" method="post">
				<table:table id="riskCtrlTable" ajaxURI="${ctx}/privateRiskCtrl/find"
							 columnNames="nickName,tradeAccount,warnLine,openLine,totalAmount,available,warnRate,openRate,updateTimeStr,errorInfo"
							 headerNames="选择,名称,交易账号,警戒线,平仓先,总资产,可用资产,总资产/警告线,总资产/平仓线,数据更新时间,更新金额失败原因"
							 searchInputNames="status,tradeAccount"
							 pageLength="25"
							 makeDefsHtmlFunc="columnDefs"
							 loadEnd="LoadEnd()"
				/>
			</form>
		</div>
	</div>
</div>


	<div class="modal fade" id="modifyRiskInfoModal" tabindex="-1" role="dialog" aria-labelledby="modifyRiskInfoLabel" >
		<div class="modal-dialog" role="document" style="width: 950px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee"></i>风控信息修改
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<form id="inputRiskForm" modelAttribute="user" class="form-horizontal">
									<input type="hidden" name="modifyId" id="modifyId">
									<div class="form-group">
										<label class="col-sm-4 control-label">用户名<span class="help-inline"><font color="red">*</font> </span>:</label>
										<div class="col-sm-4">
											<input name="nickName" id="nickName" value="" class="form-control bg-success" maxlength="50" class="required"/>
										</div>
										<div class="col-sm-4 control-label"><font color="blue"><label id="nameTips"></label></font></div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">平仓线<span class="help-inline"><font color="red">*</font> </span>:</label>
										<div class="col-sm-4">
											<input type="number" name="openLine" id="openLine" value="" class="form-control bg-success" maxlength="50" class="required"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">警告线<span class="help-inline"><font color="red">*</font> </span>:</label>
										<div class="col-sm-4">
											<input type="number" name="warnLine" id="warnLine" value="" class="form-control bg-success" maxlength="50" class="required"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">备注<span class="help-inline"><font color="red">*</font> </span>:</label>
										<div class="col-sm-4">
											<input name="remark" id="remark" value="" class="form-control bg-success" maxlength="50" class="required"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label">状态<span class="help-inline"><font color="red">*</font> </span>:</label>
										<div class="col-sm-4">
											<select name="status" id="status" class="form-control bg-success">
												<option value="Normal">正常</option>
												<option value="Invalid">锁定</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-5 control-label"></label>
										<div class="col-sm-6">
											<button type="button" class="btn btn-danger btn-sm" onclick="submitRiskCtrlFrom()" >提交</button>
											<button type="button" class="btn btn-default btn-sm" onclick="hideModalForm('#modifyRiskInfoModal')">取消</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="RiskInforDetailModal" tabindex="-1" role="dialog" aria-labelledby="RiskInforDetailLabel" >
		<div class="modal-dialog" role="document" style="width: 1150px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee">当前持仓/当日委托/当日成交查询</i>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<div id="exTab1">
									<ul  class="nav nav-tabs">
										<li class="active">
											<a href="#1a" data-toggle="tab">当前持仓</a>
										</li>
										<li>
											<a  href="#2a" data-toggle="tab">当日委托</a>
										</li>
										<li>
											<a  href="#3a" data-toggle="tab">当日成交</a>
										</li>
									</ul>
									<div class="tab-content clearfix">
										<input type="hidden" id="userId" name="userIdParam" />
										<div class="tab-pane active" id="1a">
                                            <div class="col-sm-4" style="float:right;">
                                                <input type="button" onclick="reloadPositionTableData()" class="btn btn-danger btn-sm" value="刷 新"/>
                                                <input type="button" onclick="raiseOneKeyPingStructInfo()" class="btn btn-danger btn-sm" value="一键平仓"/>
                                                <input type="button" onclick="raiseNormalPingStructInfo()" class="btn btn-danger btn-sm" value="普通平仓"/>
                                            </div><br><br>
                                            <table id="tTable" class="table table-bordered" align="center"></table>
										</div>
										<div class="tab-pane" id="2a">
                                            <table id="pTable" class="table table-bordered" align="center"></table>
                                        </div>
										<div class="tab-pane" id="3a">
                                            <table id="dTable" class="table table-bordered" align="center"></table>
										</div>
									</div>
									<div class="form-group" style="margin:0 auto;">
										<label class="col-sm-2 control-label"></label>
										<div style="margin:0 auto; float:none;" class="col-sm-2">
											<input type="button" style="margin:0 auto;" onclick="hideModalForm('#RiskInforDetailModal');" class="btn btn-default btn-sm" value="关 闭"/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

                    <div class="modal fade" id="oneKeyPingModal" tabindex="-1" role="dialog" aria-labelledby="oneKeyPingLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="modalLabel"></h4>
                                </div>
                                <div class="modal-body">
                                    <table id="RiskTable" class="table table-bordered"></table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <button type="button" class="btn btn-primary" id="RiskCtrlBtn"></button>
                                </div>
                            </div>
                        </div>
                    </div>

				</div>
			</div>
		</div>
	</div>



	<script type="text/javascript">

        var placementData = [];

        function LoadEnd(){
            $('#positionTable tr').click(function(){
                $(this).find(":radio[name=securityCode]").attr('checked', true);
            });
            $('#riskCtrlTable tr').click(function(){
                $(this).find(":radio[name=userId]").attr('checked', true);
            });
            $(":radio[name=userId]").parent('td').each(function(){
                var tr = $(this).parent('tr');
                $(this).prependTo(tr);
            });
            $('#riskCtrlTable tr').each(function(){
                var totalAmount = $(this).find(":hidden[name=totalAmount]").val();
                var warnLine = $(this).find(":hidden[name=toWarnLine]").val();
                var openLine = $(this).find(":hidden[name=toOpenLine]").val();
                var errorInfo = $(this).find(":hidden[name=errorInfo]").val();
                if($.isNumeric(openLine) &&  $.isNumeric(totalAmount) && Number(totalAmount) <= Number(openLine)){
                    $(this).css("background-color","red");
                }else if($.isNumeric(warnLine) &&  $.isNumeric(totalAmount) && Number(totalAmount) < Number(warnLine)){
                    $(this).css("background-color","yellow");
                }
                if (errorInfo != null && errorInfo != 'null' && errorInfo.length>0){
                    $(this).css("background-color", "#cc3399");
                }

            });
        }

		function reloadTable(showTable){
			var oSettings = showTable.fnSettings();
			oSettings._iDisplayStart = 0;
			showTable.fnDraw(false);
		}
		function reloadRiskTableData(){
			var showTable = $("#riskCtrlTable").dataTable();
			reloadTable(showTable);
		}


		function columnDefs(row) {
			return "<input type='hidden'  name='toWarnLine' value='"+ row.warnLine +"'/>" +
                "<input type='hidden'  name='toOpenLine' value='"+ row.openLine +"'/>" +
                "<input type='hidden'  name='totalAmount' value='"+ row.totalAmount +"'/>" +
                "<input type='hidden'  name='errorInfo' value='"+ row.errorInfo +"'/>" +
                "<input type='hidden'  name='nickName' value='"+ row.nickName +"'/>" +
                "<input type='hidden'  name='remark' value='"+ row.remark +"'/>" +
                "<input type='hidden'  name='status' value='"+ row.status +"'/>" +
				"<input type='radio'  name='userId' value='"+ row.id +"'/>";
		}

        //定时刷新数据
        function autoFresh(){
            setInterval(function(){
                var check=$("#autoFresh").is(':checked');
                if(check){
					var showTable = $("#riskCtrlTable").dataTable();
					showTable.fnDraw(false);
                }
            }, 20000);
        }

        function sounds(){
            var sound = new Howl({
                src: ['${ctxStatic}/sounds/warn.wav'],
                loop: false,
                onend: function() {
                    console.log('Finished!');
                }
            });
            sound.play();
        }

        function showSide(side){
            switch(side){
                case "BUY": return "买入";
                case "SELL": return "卖出";
                default: return side;
            }
        }

		function allRevoke(_this){
			confirmx("确定将该用户的委托全部撤单?", 'allRevokeFinally()');
		}

		function allSellConfirm(){
			confirmx("确定将该用户的持仓全部卖出?", 'allSellFinally()');
		}


		$(function(){
			$('.tab-content').css('width', '100%');
			$('.tab-pane').css('width', '100%');
			$('.dataTables_scrollHead').css('width', '100%');
			$('.dataTables_scrollHeadInner').css('width', '100%');
			$('.nav-tabs li').click(function(){
				$('.dataTables_scrollHeadInner').css('width', '100%');
			});
			autoFresh();
		});
		
		function submitRiskCtrlFrom() {
            $.ajax({
                "type" : 'post',
                "url" : "${ctx}/privateRiskCtrl/updateRiskInfo",
                "dataType" : "json",
                "data" : $("#inputRiskForm").serialize(),
                "success" : function(result) {
                    if(result.rescode=="success"){
                        hideModalForm("#modifyRiskInfoModal");
                        alertx("更新风控信息成功！")
                        reloadRiskTableData();
                    }else{
                    }
                },"error" : function (result){
                    $('#nameTips').text(result.message)
                }
            });
        }

        function modifyRiskCtrlInfoForm(){
            var trObj = $(":radio[name=userId]:checked").parents('tr');
            var userId = $(":radio[name=userId]:checked").val();
            if(!userId){
                alertx("请选择用户");
                return false;
            }
            var warnLine = $(trObj).find(":hidden[name=toWarnLine]").val();
            var openLine = $(trObj).find(":hidden[name=toOpenLine]").val();
            var nickName = $(trObj).find(":hidden[name=nickName]").val();
            var remark = $(trObj).find(":hidden[name=remark]").val();
            var status = $(trObj).find(":hidden[name=status]").val();
            $("#modifyId").val(userId);
            $("#openLine").val(openLine);
            $("#warnLine").val(warnLine);
            $("#remark").val(remark);
            $("#status").attr("value",status);
            $("#nickName").val(nickName);
            $("#autoFresh").attr('checked', false);
            $('#modifyRiskInfoModal').modal('show');
        }

        function queryRiskCtrlInfoForm() {
            var trObj = $(":radio[name=userId]:checked").parents('tr');
            var userId = $(":radio[name=userId]:checked").val();
            if(!userId){
                alertx("请选择用户");
                return false;
            }
            onLoadDetailDate(userId,"1","tTable");
            onLoadDetailDate(userId,"2","pTable");
            onLoadDetailDate(userId,"3","dTable");
            $("#autoFresh").attr('checked', false);
            $('#RiskInforDetailModal').modal('show');
        }

        function hideModalForm(idName) {
            $("#autoFresh").attr('checked', true);
            $(idName).modal('hide');
        }
        
        function onLoadDetailDate(userId,type,tableName) {
            document.getElementById(tableName).innerHTML=null;
            $.ajax({
                "type" : 'post',
                "url": "${ctx }/privateRiskCtrl/queryInfo",
                "dataType" : "json",
                "data" : {
                    "userId":userId,
                    "type":type
                },
                "success" : function(result) {
                    var json = JSON.parse(result);
                    if (type == "1"){
                        generatePositionTable(json,tableName);
                    }else {
                        generateDealOrPlacementTable(json,tableName);
					}
                },"error" : function (result){
                    alert("发生异常,或没权限...");
                }
            });
        }

        function generatePositionTable(resultJson,tableName) {
            var jbody = resultJson.body;
            var jhead = resultJson.header;
            var trData = "<thead><tr><th>选择</th>";
            var len = 12;
            for (var j =0 ; j < len ; j++){
                trData += "<th>" + jhead[j] + "</th>";
            }
            trData += "</tr></thead><tbody>";
            for (var i = 0 ; i<jbody.length;i++){
                var jbodyLine = jbody[i];
                var itrDate = "<tr><td><input type='checkbox' name='stockKey'></td>";
                for (var j =0 ; j < len ; j++){
                    itrDate += "<td>" + jbodyLine[j] + "</td>";
                }
                itrDate += "</tr>";
                trData += itrDate;
            }
            document.getElementById(tableName).innerHTML=trData+"</tbody>";
        }

        function generateDealOrPlacementTable(resultJson,tableName) {
            var jbody = resultJson.body;
            var jhead = resultJson.header;
            var trData = "<thead><tr>";
            var len = 16;
            if (jbody.length==0){
//                len = jhead.length;
            }else {
                if (jbody[0].length<len)
                    len = jbody[0].length;
            }
            for (var j =0 ; j < len ; j++){
                trData += "<th>" + jhead[j] + "</th>";
            }
            trData += "</tr></thead><tbody>";
            for (var i = 0 ; i<jbody.length;i++){
                var jbodyLine = jbody[i];
                var itrDate = "<tr>";
                for (var j =0 ; j < len ; j++){
                    itrDate += "<td>" + jbodyLine[j] + "</td>";
                }
                itrDate += "</tr>";
                trData += itrDate;
            }
            document.getElementById(tableName).innerHTML=trData+"</tbody>";
        }

        function raiseOneKeyPingStructInfo() {
            var userId = $(":radio[name=userId]:checked").val();
            if (userId == null || userId == ""){
                alertx("发生异常，请刷新页面!");
                return false;
            }
            var placement = {};
            console.log(userId);
            placement.userId = userId;
            var datas = [];
            $("#tTable tr").each(function(){
                var code= $(this).find("td").eq(1).text();
                var availableToSell = parseInt($(this).find("td").eq(4).text());
                var reg = /^(0|3|6)/;
                if (!reg.test(code)){
                    return true;
                }
                if (availableToSell<=0) return true;
                var data = {};
                data['code'] = code;
                data['availableToSell'] = availableToSell;
                datas.push(data);
            });
            if (datas==null||datas.length==0){
                alert("没有可卖出的持仓");
                return false;
            }
            placement.contents = datas;
            generateOneKeyPingDataTabel(datas);
            placementData = placement;
            $('#oneKeyPingModal').modal('show');
        }
        
        function submitOneKeyPing() {
            $.ajax({
                "type" : 'post',
                "url": "${ctx }/privateRiskCtrl/oneKeyPing",
                "dataType" : "json",
                "data" : {
                    "placementStr":JSON.stringify(placementData)
                },
                "success" : function(result) {
                    var result=result.message;
                    $('#oneKeyPingModal').modal('hide');
                    $('#RiskInforDetailModal').modal('hide');
                    alertx(result);
                    window.location.reload();
                },"error" : function (result){
                    alert("发生异常,或没权限...");
                }
            });
        }

        function submitNormalPing() {
            var userId = $(":radio[name=userId]:checked").val();
            if (userId == null || userId == ""){
                alertx("发生异常，请刷新页面!");
                return false;
            }
            var placement = {};
            console.log(userId);
            placement.userId = userId;
            var datas = [];
            $("#RiskTable tr").each(function(){
                var code= $(this).find("td").eq(0).text();
                var toSell = parseInt($("#dealToSell").val());
                var toPrice = parseFloat($("#dealPrice").val());
                var reg = /^(0|3|6)/;
                if (!reg.test(code)){
                    return true;
                }
                if (toSell<=0) return true;
                var data = {};
                data['code'] = code;
                data['availableToSell'] = toSell;
                data['price'] = toPrice;
                datas.push(data);
            });
            if (datas==null||datas.length==0){
                alert("没有可卖出的持仓");
                return false;
            }
            placement.contents = datas;
            $.ajax({
                "type" : 'post',
                "url": "${ctx }/privateRiskCtrl/normalPing",
                "dataType" : "json",
                "data" : {
                    "placementStr":JSON.stringify(placement)
                },
                "success" : function(result) {
                    var result=result.message;
                    $('#oneKeyPingModal').modal('hide');
                    $('#RiskInforDetailModal').modal('hide');
                    alertx(result);
                    window.location.reload();
                },"error" : function (result){
                    alert("发生异常,或没权限...");
                }
            });
        }
        
        function  generateOneKeyPingDataTabel(datas) {
            var trData = "<caption><b>发起平仓信息</b><em>(成交价会以跌停价卖出)</em></caption><thead><tr><th>股票代码</th><th>卖出数量</th></tr></thead><tbody>";
            for (var i = 0 ; i < datas.length ; i ++){
                trData += "<tr><td>"+datas[i]['code']+"</td>"+"<td>"+datas[i]['availableToSell']+"</td></tr>";
            }
            trData+="</tbody>"
            document.getElementById('RiskTable').innerHTML=trData;
            document.getElementById('RiskCtrlBtn').innerText="提交一键平仓";
            $("#RiskCtrlBtn").attr('onclick',"submitOneKeyPing()");
        }

        function  generateNormalPingDataTabel(datas) {
            var trData = "<thead><tr><th>股票代码</th><th>股票名称</th><th>可卖出数量</th><th>成交数量</th><th>成交价格</th></tr></thead><tbody>";
            for (var i = 0 ; i < datas.length ; i ++){
                trData += "<tr><td>"+datas[i]['code']+"</td>"+
                    "<td>"+datas[i]['stockName'] +"</td>"+
                    "<td>"+datas[i]['availableToSell'] +"</td>"+
                    "<td><input type='number' id='dealToSell' name='dealToSell' value='"+datas[i]['availableToSell']+"'></td>"+
                    "<td><input type='number' id='dealPrice' name='dealPrice' value='"+datas[i]['price']+"'></td>"+
                    "</tr>";
            }
            trData+="</tbody>"
            document.getElementById('RiskTable').innerHTML=trData;
            document.getElementById('RiskCtrlBtn').innerText="提交";
            $("#RiskCtrlBtn").attr('onclick',"submitNormalPing()");
        }
		
        function raiseNormalPingStructInfo() {
            var userId = $(":radio[name=userId]:checked").val();
            if (userId == null || userId == ""){
                alertx("发生异常，请刷新页面!");
                return false;
            }
            var placement = {};
            placement.userId = userId;
            var datas = [];
            $("#tTable tr").each(function(){
                if (!$(this).find(':checkbox[name=stockKey]').is(':checked')) return true;
                var code= $(this).find("td").eq(1).text();
                var stockName= $(this).find("td").eq(2).text();
                var availableToSell = parseInt($(this).find("td").eq(4).text());
                var reg = /^(0|3|6)/;
                if (!reg.test(code)){
                    return true;
                }
                if (availableToSell<=0) return true;
                var data = {};
                data['code'] = code;
                data['stockName'] = stockName;
                data['availableToSell'] = availableToSell;
                data['price'] = getLimitDownPrice(code);
                datas.push(data);
            });
            if (datas==null||datas.length==0){
                alert("没有可卖出的持仓");
                return false;
            }
            placement.contents = datas;
            generateNormalPingDataTabel(datas);
            placementData = placement;
            $('#oneKeyPingModal').modal('show');
        }
		
        function getLimitDownPrice(code) {
            var limitDownPrice;
            $.ajax({
                "type" : 'post',
                "url": "${ctx }/privateRiskCtrl/getLimitDownPrice",
                "dataType" : "json",
                "async": false,
                "data" : {
                    "code":code
                },
                "success" : function(result) {
                    limitDownPrice = result;
                },"error" : function (result){
                    alert("查询跌停价失败");
                }
            });
            return limitDownPrice;
        }

		function downloadFillSummary(){
			location.href="${ctx}/privateRiskCtrl/downloadStockFillSummary";
		}
	</script>

</body>




