<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>风控管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<style type="text/css">
		#sumary table{
			border-collapse:collapse;
			border:1px solid black;
			text-align:center;
		}
		#sumary td{
			width: 100px;
			border:1px solid black;
			text-align:center;
		}
		#sumary caption{
			border:1px solid black;
			text-align:center;
		}
	</style>
	<script type="text/javascript">
		function reloadTable(showTable){
			var oSettings = showTable.fnSettings();
			oSettings._iDisplayStart = 0;
			showTable.fnDraw(false);
		}
		function reloadRiskTableData(){
			var showTable = $("#riskCtrlTable").dataTable();
			//showTable.fnClearTable();
			reloadTable(showTable);
			//reloadTableById("riskCtrlTable");
		}

		function reloadPlacementTableData(){
			//var showTable = $("#placementTable").dataTable();
			//showTable.fnClearTable();
			//showTable.clear();
			//showTable.fnDestroy();
			//reloadTable(showTable);
			reloadTableById("placementTable");
		}
		
		function clearPlacementTableData(){
			var showTable = $("#placementTable").dataTable();
			//showTable.fnClearTable();
			//showTable.clear();
			//showTable.fnDestroy();
			//reloadTable(showTable);
		}

		function reloadPositionTableData(){
			//var showTable = $("#positionTable").dataTable();
			//showTable.fnClearTable();
			//showTable.fnDestroy();
			//reloadTable(showTable);
			reloadTableById("positionTable");
		}
		
		function clearPositionTableData(){
			var showTable = $("#positionTable").dataTable();
			//showTable.fnClearTable();
			//showTable.Table.Rows.Clear();
			//showTable.fnDestroy({"bDestroy":true});
			//reloadTable(showTable);
		}

		function placementPositionQry(){
//			$('li.active').removeAttr('class');
//			$('ul.nav-tab li:first').attr('class', 'active');
			var userId = $(":radio[name=userId]:checked").val();
			if(!userId){
				alertx("请选择用户");
				return false;
			}
			$("#userId").val(userId);
			$('#placementTable_processing').hide();
			$('.dataTables_scrollHeadInner').css('width', '100%');
			$('#queryDetailModal').modal('show');
			sleep(500);
            reloadPositionTableData();
            reloadPlacementTableData();
		}
		

		/* function columnDefs(row) {
			return "<input type='hidden'  name='toWarnLine' value='"+ row.toWarnLine +"'/>" +
					"<input type='hidden'  name='toOpenLine' value='"+ row.toOpenLine +"'/>" +
					"<input type='hidden'  name='totalAmount' value='"+ row.totalAmount +"'/>" +
					"<input type='radio'  name='userId' value='"+ row.userId +"'/>";
		} */
		
		function columnHeadDefs(row) {
			return "<input type='hidden'  name='toWarnLine' value='"+ row.toWarnLine +"'/>" +
					"<input type='hidden'  name='toOpenLine' value='"+ row.toOpenLine +"'/>" +
					"<input type='hidden'  name='totalAmount' value='"+ row.totalAmount +"'/>" +
					"<input type='radio'  name='userId' value='"+ row.userId +"'/>";
		}

		function showPlacementModal(userId){
			$("#userId").val(userId);
			$("#placementModal").modal('show');
		}

        function showPositionModal(userId){
            $("#userId").val(userId);
            $("#positionModal").modal('show');
        }

		function hideQueryDetailModal(){
			$('#queryDetailModal').modal('hide');
		}

        //定时刷新数据
        function autoFresh(){
            setInterval(function(){
                var check=$("#autoFresh").is(':checked');
                if(check){
                	var userId = $(":radio[name=userId]:checked").val();
                	if(userId){
                		$("#userId").val(userId);
                	}
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

		function allRevokeFinally(){
			$('#allRevokeBtn').attr('disabled', 'disabled');
			var userId = $(":radio[name=userId]:checked").val();
			if(!userId){
				alertx("请选择用户");
				$('#allRevokeBtn').removeAttr('disabled');
				return false;
			}
			$.ajax({
				type: "get",//使用get方法访问后台
				dataType: "json",//返回json格式的数据
				url: "${ctx}/risk/cancelUserAllPlacement",//要访问的地址
				data: "userId="+userId,//要发送的数据
				success: function (data) {//data为返回的数据，在这里做数据绑定
					alertx(data.message);
					$('#allRevokeBtn').removeAttr('disabled');
				}
			});
		}


		function allSellConfirm(){
			confirmx("确定将该用户的持仓全部卖出?", 'allSellFinally()');
		}

		function allSellFinally(){
			$('#allSellBtn').attr('disabled', 'disabled');

			var userId = $(":radio[name=userId]:checked").val();
			var priceType = $(":radio[name=priceType]:checked").val();
			if(!userId){
				alertx("请选择用户");
				$('#allSellBtn').removeAttr('disabled');
				return false;
			}
			if(!priceType){
				alertx("请选择卖出价格");
				$('#allSellBtn').removeAttr('disabled');
				return false;
			}
			$.ajax({
				type: "get",//使用get方法访问后台
				dataType: "json",//返回json格式的数据
				url: "${ctx}/risk/singleUserSellAll",//要访问的地址
				data: "userId="+userId+"&priceType=" + priceType,//要发送的数据
				success: function (data) {//data为返回的数据，在这里做数据绑定
					$("#sellPriceTypeModal").modal('hide');
					alertx(data.message);
					$('#allSellBtn').removeAttr('disabled');
				}
			});
		}

		function allSell(){
			var userId = $(":radio[name=userId]:checked").val();
			if(!userId){
				alertx("请选择用户");
				return false;
			}
			$("#sellPriceTypeModal").modal('show');
		}

		function getMarketValues(){
			var val = 0;
			$(":checkbox[name=marketValueFlag]:checked").each(function(){
                val += parseInt($(this).val());
			});
			$("#marketValues-search").val(val);
		}
		function placementColumnDefs(row){
			return '<input type="radio"  name="riskPlacementId" value="'+ row.placementId +'"/>';
		}

		function positionColumnDefs(row){
			return '<input type="radio"  name="securityCode" value="'+ row.securityCode + ',' +row.securityName+'"/>';
		}

        function sumaryAmout(col2nd,col3rd,col4th,col5th,col6th,col10th) {
            $("#sumary").remove();
            $("#sumary_2").remove();
            $("div.datagrid-view1 table").after("<div id='sumary_2' style='height:84px'></div>");
            $('#riskCtrlTable').after("<div id='sumary' align='center'><br><table>" +
                "<tr><td colspan='5'>金额汇总</td></tr>" +
                "<tr><td>总资产汇总</td>" +
                "<td>余额汇总</td>" +
                "<td>市值汇总</td>" +
                "<td>本金金额汇总</td>" +
                "<td>融资金额汇总</td></tr>" +
                "<tr><td>"+col2nd+"</td><td>"+col3rd+"</td><td>"+col4th+"</td><td>"+col5th+"</td><td>"+col6th+"</td></tr></table><br></div>")
        }


		function LoadEnd(){
			//$("#riskCtrlTable_processing").css("display","none");
			$('#positionTable tr').click(function(){
				$(this).find(":radio[name=securityCode]").attr('checked', true);
			});
			$(":radio[name=securityCode]").parent('td').each(function(){
				var tr = $(this).parent('tr');
				$(this).prependTo(tr);
			});
			$('#placementTable tr').click(function(){
				$(this).find(":radio[name=riskPlacementId]").attr('checked', true);
			});
			$(":radio[name=riskPlacementId]").parent('td').each(function(){
				var tr = $(this).parent('tr');
				$(this).prependTo(tr);
			});
			$('#riskCtrlTable tr').click(function(){
				$(this).find(":radio[name=userId]").attr('checked', true);
			});

			/* $(":radio[name=userId]").parent('td').each(function(){
				var tr = $(this).parent('tr');
				$(this).prependTo(tr);
			}); */
            var col2nd=0.00,col3rd=0.00,col4th=0.00,col5th=0.00,col9th=0.00;
			$('#riskCtrlTable tr').each(function(){
				var totalAmount = $(this).find(":hidden[name=totalAmount]").val();
				var warnLine = $(this).find(":hidden[name=toWarnLine]").val();
				if($.isNumeric(warnLine) && Number(warnLine) <= 0 && $.isNumeric(totalAmount) && Number(totalAmount) > 0){
					$(this).css("background-color","yellow");
				}
				var openLine = $(this).find(":hidden[name=toOpenLine]").val();
				if($.isNumeric(openLine) && Number(openLine) <= 0 && $.isNumeric(totalAmount) && Number(totalAmount) > 0){
					$(this).css("background-color","red");
					if($('#warnSounds').is(':checked')){
						sounds();
					}
				}
				if (!$.isNumeric($(this).find("td").eq(4).text())) return true;
                <c:if test="${admin == 'true'}">
					col2nd += parseFloat($(this).find("td").eq(4).text());
					col3rd += parseFloat($(this).find("td").eq(5).text());
					col4th += parseFloat($(this).find("td").eq(6).text());
					col5th += parseFloat($(this).find("td").eq(7).text());
					col9th += parseFloat($(this).find("td").eq(8).text());
				</c:if>
                <c:if test="${admin != 'true'}">
					col2nd += parseFloat($(this).find("td").eq(3).text());
					col3rd += parseFloat($(this).find("td").eq(4).text());
					col4th += parseFloat($(this).find("td").eq(5).text());
					col5th += parseFloat($(this).find("td").eq(6).text());
					col9th += parseFloat($(this).find("td").eq(7).text());
				</c:if>

			});
            sumaryAmout(col2nd.toFixed(2),col3rd.toFixed(2),col4th.toFixed(2),col5th.toFixed(2),col9th.toFixed(2));
      		var userId=$("#userId").val();
      		if(userId){
				$(":radio[value='"+userId+"']").attr("checked","checked")
			}
		}

		$(function(){
			$('.tab-content').css('width', '100%');
			$('.tab-pane').css('width', '100%');
			$('.dataTables_scrollHead').css('width', '100%');
			$('.dataTables_scrollHeadInner').css('width', '100%');
//		$('.dataTables_processing').hide();
			$('.nav-tabs li').click(function(){
				$('.dataTables_scrollHeadInner').css('width', '100%');
			});
			autoFresh();
			
			$('#queryDetailModal').on('hide.bs.modal',function(){
				clearPositionTableData();
				clearPlacementTableData();
			});
		});

		function showCommonPlacementModal(){
			var items = $(":radio[name=securityCode]:checked").val();
			if(!items){
				alertx("请选择持仓股票");
				return false;
			}
			var itemArr = items.split(',');
			$("#tradeType").html("卖出");
			$("#securityCode").html(itemArr[0]);
			$("#securityCodeHidden").val(itemArr[0]);
			$("#securityName").html(itemArr[1]);
			$("#speedPlacementQty").val("");
			$("#commonPlacementModal").modal('show');
		}

		function showSpeedPlacementModal(){
			var items = $(":radio[name=securityCode]:checked").val();
			if(!items){
				alertx("请选择持仓股票");
				return false;
			}
			var itemArr = items.split(',');
			$("#speedTradeType").html("卖出");
			$("#speedSecurityCode").html(itemArr[0]);
			$("#speedSecurityCodeHidden").val(itemArr[0]);
			$("#speedSecurityName").html(itemArr[1]);
			$("#speedPlacementQty").val("");
			$("#speedPlacementModal").modal('show');
		}

		// 普通卖出
		function commonPlacement(_this){
			_this.disabled=true;
			var userId =  $("#userId").val();
			var price =  $("#placementPrice").val();
			if(!price || !$.isNumeric(price)){
				alertx("请输入正确的委托价格");
				_this.disabled=false;
				return false;
			}
			var quantity =  $("#placementQty").val();
			if(!quantity || !$.isNumeric(quantity)){
				alertx("请输入正确的委托数量");
				_this.disabled=false;
				return false;
			}
			var securityCode=$("#securityCodeHidden").val();
			$.ajax({
				type: "get",//使用get方法访问后台
				dataType: "json",//返回json格式的数据
				url: "${ctx}/risk/commonPlacement",//要访问的地址
				data: "userId="+userId+"&price="+price+"&quantity="+quantity+"&securityCode="+securityCode,//要发送的数据
				success: function (data) {//data为返回的数据，在这里做数据绑定
					$("#commonPlacementModal").modal('hide');
					alertx(data.message);
					reloadPositionTableData();
					_this.disabled=false;
				}
			});
		}

		// 极速卖出
		function speedPlacement(_this){
			_this.disabled=true;
			var userId =  $("#userId").val();
			var quantity =  $("#speedPlacementQty").val();
			if(!quantity || !$.isNumeric(quantity)){
				alertx("请输入正确的委托数量");
				_this.disabled=false;
				return false;
			}
			var securityCode=$("#speedSecurityCodeHidden").val();
			var priceType = $(":radio[name=speedPriceType]:checked").val();
			$.ajax({
				type: "get",//使用get方法访问后台
				dataType: "json",//返回json格式的数据
				url: "${ctx}/risk/speedPlacement",//要访问的地址
				data: "userId="+userId+"&quantity="+quantity+"&securityCode="+securityCode+"&priceType="+priceType,//要发送的数据
				success: function (data) {//data为返回的数据，在这里做数据绑定
					$("#speedPlacementModal").modal('hide');
					alertx(data.message);
					reloadPositionTableData();
					_this.disabled=false;
				}
			});
		}


		// 撤单
		function cancelPlacement(_this){
			var placementId = $(":radio[name=riskPlacementId]:checked").val();
			if(!placementId){
				alertx("请选择需要撤单的委托");
				_this.disabled=false;
				return false;
			}
			_this.disabled=true;
			$.ajax({
				type: "get",//使用get方法访问后台
				dataType: "json",//返回json格式的数据
				url: "${ctx}/risk/cancelPlacement",//要访问的地址
				data: "placementId="+placementId,//要发送的数据
				success: function (data) {//data为返回的数据，在这里做数据绑定
					alertx(data.message);
					reloadPlacementTableData();
					_this.disabled=false;
				}
			});
		}
	</script>
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
						<span class="input-group-addon">用户名</span>
						<input type="text" class="form-control" name="userName" id="userName-search" placeholder="用户名" aria-describedby="basic-addon1">
					</div>
				</div>
				<c:if test="${admin == 'true'}">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">资金方</span>
						<select class="form-control" name="customerId" id="customerId-search">
							<option value="">请选择</option>
							${fns:getCustomers()}
						</select>
					</div>
				</div>
				</c:if>
				<div class="col-md-1">
					<div class="input-group">
						市值>0：<br/>
                        <input type="hidden" name="marketValues" id="marketValues-search" value="3"/>
						<input type="checkbox" checked="true" name="marketValueFlag" value="1" onclick="getMarketValues()"/>
					</div>
				</div>
				<div class="col-md-1">
					<div class="input-group">
						市值=0：<br/>
						<input type="checkbox" checked="true" name="marketValueFlag" value="2" onclick="getMarketValues()"/>
					</div>
				</div>
				<div class="col-md-1">
					<div class="input-group">
						自动刷新：<br/>
						<input type="checkbox" id="autoFresh" checked="true"/>
					</div>
				</div>
				<div class="col-md-1">
					<div class="input-group">
						报警声音：<br/>
						<input type="checkbox" id="warnSounds" checked="true"/>
					</div>
				</div>

				<div class="col-md-4" style="float:right;">
					<%--<button class="btn btn-primary" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>自动刷新</button>--%>
					<button  class="btn btn-primary" onclick="reloadRiskTableData()"><i class="glyphicon glyphicon-search"></i>  查 询</button>
					<button class="btn btn-primary" onclick="placementPositionQry()">委托持仓查询</button>
				</div>

			</div>
			<form id="riskCtrlForm" method="post">
				<c:if test="${admin == 'true'}">
					<table:table id="riskCtrlTable" ajaxURI="${ctx}/risk/find"
								 columnNames="userId,userName,realName,customerName,totalAmount,amount,marketValue,cashAmount,financeAmount,warnLine,openLine,suspensionDisplay,toWarnLine,toOpenLine"
								 headerNames="选择,用户名,姓名,资金方,总资产,余额,市值,本金金额,融资金额,警戒线,平仓线,是否持有停牌股,距警戒线金额,距平仓线金额"
								 searchInputNames="userName,customerId,marketValues"
								 pageLength="100"
								 makeHeadDefsHtmlFunc="columnHeadDefs"
								 loadEnd="LoadEnd()"
								 showProgress="false"
					/>
				</c:if>
				<c:if test="${admin != 'true'}">
				<table:table id="riskCtrlTable" ajaxURI="${ctx}/risk/find"
							 columnNames="userId,userName,realName,totalAmount,amount,marketValue,cashAmount,financeAmount,warnLine,openLine,suspensionDisplay,toWarnLine,toOpenLine"
							 headerNames="选择,用户名,姓名,总资产,余额,市值,本金金额,融资金额,警戒线,平仓线,是否持有停牌股,距警戒线金额,距平仓线金额"
							 searchInputNames="userName,customerId,marketValues"
							 pageLength="100"
							 makeHeadDefsHtmlFunc="columnHeadDefs"
							 loadEnd="LoadEnd()"
							 showProgress="false"
				/>
				</c:if>
			</form>
		</div>
	</div>
</div>
	<div class="modal fade" id="sellPriceTypeModal" tabindex="-1" role="dialog" aria-labelledby="sellPriceTypeLabel" >
		<div class="modal-dialog" role="document" style="width: 650px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="sellPriceTypeLabel">请选择卖出价格类型</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-2">
							<div class="input-group">
								买1价：
								<input type="radio" name="priceType" value="1"/>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								买2价：
								<input type="radio" name="priceType" value="2"/>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								买3价：
								<input type="radio" name="priceType" value="3"/>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								买4价：
								<input type="radio" name="priceType" value="4"/>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								买5价：
								<input type="radio"  name="priceType" value="5"/>
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								跌停价：
								<input type="radio" checked="true" name="priceType" value="0"/>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="allSellBtn" onclick="allSellConfirm(this);">确认卖出</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="queryDetailModal" tabindex="-1" role="dialog" aria-labelledby="queryDetailLabel" >
		<div class="modal-dialog" role="document" style="width: 950px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-coffee"></i>委托持仓查询
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<form:form id="inputForm" modelAttribute="user" class="form-horizontal">
									<div id="exTab1">
										<ul  class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#2a" data-toggle="tab">持仓</a>
                                            </li>
											<li>
												<a  href="#1a" data-toggle="tab">当日委托</a>
											</li>

										</ul>

										<div class="tab-content clearfix">
											<input type="hidden" id="userId" name="userIdParam" />
											<div class="tab-pane active" id="2a">
												<form id="positionForm" method="post">
													<div class="form-group">
														<div class="col-sm-4" style="float:right;">
															<input type="button" onclick="reloadPositionTableData()" class="btn btn-danger btn-sm" value="刷 新"/>
														</div>
													</div>
													<table:table id="positionTable" ajaxURI="${ctx}/risk/queryPositionByPage"
																 columnNames="userName,securityCode,securityName,suspensionDisplay,marketValue,marketProfit,amount,availableAmount,latestPrice,costPrice,suspensionDisplay"
																 headerNames="用户名,股票代码,股票简称,状态,市值,浮动盈亏, 持仓数量, 当前可用, 现价, 成本价,停牌标识"
																 searchInputNames="userIdParam"
																 pageLength="6"
																 showProgress="false"
													/>

												</form>
											</div>
                                            <div class="tab-pane" id="1a">
                                                <form id="placementForm" method="post">
                                                    <div class="form-group">
                                                        <div class="col-sm-2" style="float:right;">
                                                            <input type="button" onclick="reloadPlacementTableData()" class="btn btn-danger btn-sm" value="刷 新"/>
                                                        </div>
                                                    </div>
                                                    <table:table id="placementTable" ajaxURI="${ctx}/risk/queryPlacementByPage"
                                                                 columnNames="securityCode,securityName,createTimeStr,tradeTypeDisplay,placementPrice,placementQty,placementAmount,filledQty,placementStatusDisplay"
                                                                 headerNames="股票代码,股票名称,委托时间,方向,委托价格,委托数量,委托金额,成交数量,委托状态"
                                                                 searchInputNames="userIdParam"
                                                                 pageLength="6"
                                                                 loadEnd="LoadEnd()"
                                                                 showProgress="false"
                                                    />

                                                </form>
                                            </div>
										</div>
										<div class="form-group" style="margin:0 auto;">
											<label class="col-sm-2 control-label"></label>
											<div style="margin:0 auto; float:none;" class="col-sm-2">
												<input type="button" style="margin:0 auto;" onclick="hideQueryDetailModal();" class="btn btn-default btn-sm" value="关 闭"/>
											</div>
										</div>
									</div>

								</form:form>
							</div>
						</div>
					</div>

					<div class="modal fade" id="commonPlacementModal" tabindex="-1" role="dialog" aria-labelledby="commonPlacementLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h4 class="modal-title" id="commonPlacementModalLabel">普通委托-卖出</h4>
								</div>
								<div class="modal-body">
									交易类型：<span id="tradeType" class="b"></span><br/><br/>
									股票代码：<span id="securityCode" class="b"></span><br/><br/>
									<input type="hidden" id="securityCodeHidden"/>
									股票名称：<span id="securityName" class="b"></span><br/><br/>
									委托价格：<input type="text" id="placementPrice"/><br/><br/>
									委托数量：<input type="text" id="placementQty"/><br/>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								</div>
							</div>
						</div>
					</div>

					<div class="modal fade" id="speedPlacementModal" tabindex="-1" role="dialog" aria-labelledby="speedPlacementLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h4 class="modal-title" id="speedPlacementModalLabel">极速委托-卖出</h4>
								</div>
								<div class="modal-body">
									交易类型：<span id="speedTradeType" class="b"></span><br/><br/>
									股票代码：<span id="speedSecurityCode" class="b"></span><br/><br/>
									<input type="hidden" id="speedSecurityCodeHidden"/>
									股票名称：<span id="speedSecurityName" class="b"></span><br/><br/>
									委托价格: <input type="radio" name="priceType" value="1"/> 买1价
									&nbsp;<input type="radio" name="speedPriceType" value="2"/> 买2价
									&nbsp;<input type="radio" name="speedPriceType" value="3"/> 买3价
									&nbsp;<input type="radio" name="speedPriceType" value="4"/> 买4价
									&nbsp;<input type="radio" name="speedPriceType" value="5"/> 买5价
									&nbsp;<input type="radio" name="speedPriceType" value="0" checked="checked"/> 跌停价
									<br/><br/>
									委托数量：<input type="text" id="speedPlacementQty"/><br/><br/>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	

</body>




