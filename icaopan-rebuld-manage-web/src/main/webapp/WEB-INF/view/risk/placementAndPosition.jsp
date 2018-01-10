<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>风控管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<style>
		body {  padding : 10px ;  }
	</style>
</head>
<body>
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
							<a  href="#1a" data-toggle="tab">当日委托</a>
						</li>
						<li><a href="#2a" data-toggle="tab">持仓</a>
						</li>
					</ul>

					<div class="tab-content clearfix">
						<input type="hidden" id="userId" value="${userId}"/>
						<div class="tab-pane" id="1a">
							<form id="placementForm" method="post">
								<div class="form-group">
									<div class="col-sm-2" style="float:right;">
										<input type="button" onclick="reloadTableData()" class="btn btn-danger btn-sm" value="刷 新"/>
										<input type="button" onclick="cancelPlacement(this);" class="btn btn-danger btn-sm" value="撤 单"/>
									</div>
								</div>
								<table:table id="placementTable" ajaxURI="${ctx}/risk/queryPlacementByPage?userId=${userId}"
											 columnNames="ecurityCode,securityName,createTime,tradeTypeDisplay,placementPrice,placementQty,placementAmount,placementStatusDisplay"
											 headerNames="选择,用户名,股票代码,股票名称,委托时间,卖卖方向,委托价格,委托数量,委托金额,委托状态"
											 searchInputNames=""
											 pageLength="10"
											 makeDefsHtmlFunc="placementColumnDefs"
                                             loadEnd="onLoadSuccess()"
								/>

							</form>
						</div>
						<div class="tab-pane active" id="2a">
							<form id="positionForm" method="post">
								<div class="form-group">
									<div class="col-sm-3" style="float:right;">
										<input type="button" onclick="reloadTableData()" class="btn btn-danger btn-sm" value="刷 新"/>
										<input type="button" onclick="showSpeedPlacementModal()" class="btn btn-danger btn-sm" value="极速卖出"/>
										<input type="button" onclick="showCommonPlacementModal()" class="btn btn-danger btn-sm" value="	"/>
									</div>
								</div>
								<table:table id="positionTable" ajaxURI="${ctx}/risk/queryPositionByPage?userId=${userId}"
											 columnNames="securityCode,securityName,marketValue,marketProfit,amount,availableAmount,latestPrice,costPrice"
											 headerNames="选择,股票代码,股票简称,股票市值,浮动盈亏, 持股数量, 当前可用, 现价, 成本价"
											 searchInputNames=""
											 pageLength="10"
											 makeDefsHtmlFunc="positionColumnDefs"
											 loadEnd="loadEnd()"
								/>

							</form>
						</div>

					</div>
					<div class="form-group" style="margin:0 auto;">
						<label class="col-sm-2 control-label"></label>
						<div style="margin:0 auto; float:none;" class="col-sm-2">
							<input type="button" style="margin:0 auto;" onclick="history.go(-1)" class="btn btn-default btn-sm" value="返 回"/>
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
				股票名称：<span id="securityName" class="b"></span><br/><br/>
				委托价格：<input type="text" id="placementPrice"/><br/><br/>
				委托数量：<input type="text" id="placementQty"/><br/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="commonPlacement(this);">确认提交</button>
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
				股票名称：<span id="speedSecurityName" class="b"></span><br/><br/>
				委托价格：买1价：<input type="radio" name="priceType" value="1"/>
				买2价：<input type="radio" name="priceType" value="2"/>
				买3价：<input type="radio" name="priceType" value="3"/>
				买4价：<input type="radio" name="priceType" value="4"/>
				买5价：<input type="radio" name="priceType" value="5"/>
				跌停价：<input type="radio" name="priceType" value="0"/>
				委托数量：<input type="text" id="speedPlacementQty"/><br/><br/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="speedPlacement(this);">确认提交</button>
			</div>
		</div>
	</div>
</div>

<script>
	function placementColumnDefs(row){
        return '<input type="radio"  name="riskPlacementId" value="'+ row.placementId +'"/>';
	}

	function positionColumnDefs(row){
		return '<input type="radio"  name="securityCode" value="'+ row.securityCode + ',' +row.securityName+'"/>';
	}

	function onLoadSuccess(){
        $('#placementTable tr').click(function(){
            $(this).find(":radio[name=riskPlacementId]").attr('checked', true);
        });
        $(":radio[name=riskPlacementId]").parent('td').each(function(){
            var tr = $(this).parent('tr');
            $(this).prependTo(tr);
        });
	}

	function loadEnd(){
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
	});

	function showCommonPlacementModal(){
		var items = $(":radio[name=securityCode]:checked").val();
		if(!items){
			alert("请选择持仓股票");
		}
		var itemArr = items.split(',');
		$("#tradeType").html("卖出");
		$("#securityCode").html(itemArr[0]);
		$("#securityName").html(itemArr[1]);
		$("#commonPlacementModal").modal('show');
	}

	function showSpeedPlacementModal(){
		var items = $(":radio[name=securityCode]:checked").val();
		if(!items){
			alert("请选择持仓股票");
		}
		var itemArr = items.split(',');
		$("#speedTradeType").html("卖出");
		$("#speedSecurityCode").html(itemArr[0]);
		$("#speedSecurityName").html(itemArr[1]);
		$("#speedPlacementModal").modal('show');
	}

	// 普通卖出
	function commonPlacement(_this){
		_this.disabled=true;
		var userId =  $("#userId").val();
		var price =  $("#placementPrice").val();
		if(!price || !$.isNumeric(price)){
			alert("请输入正确的委托价格");
		}
		var quantity =  $("#placementQty").val();
		if(!quantity || !$.isNumeric(quantity)){
			alert("请输入正确的委托数量");
		}
		var securityCode=$("#securityCode").val();
		$.ajax({
			type: "get",//使用get方法访问后台
			dataType: "json",//返回json格式的数据
			url: "${ctx}/risk/commonPlacement",//要访问的地址
			data: "userId="+userId+"&price="+price+"&quantity="+quantity+"&securityCode="+securityCode,//要发送的数据
			success: function (data) {//data为返回的数据，在这里做数据绑定
				$("#commonPlacementModal").modal('hide');
				alert(data.message);
				reloadTableData();
				_this.disabled=false;
			}
		});
	}

	// 极速卖出
	function speedPlacement(_this){
        _this.disabled=true;
		var userId =  $("#userId").val();
//		var price =  $("#speedPlacementPrice").val();
//		if(!price || !$.isNumeric(price)){
//			alert("请输入正确的委托价格");
//		}
		var quantity =  $("#speedPlacementQty").val();
		if(!quantity || !$.isNumeric(quantity)){
			alert("请输入正确的委托数量");
		}
		var securityCode=$("#securityCode").val();
        var priceType = $(":radio[name=priceType]:checked").val();
		$.ajax({
			type: "get",//使用get方法访问后台
			dataType: "json",//返回json格式的数据
			url: "${ctx}/risk/speedPlacement",//要访问的地址
			data: "userId="+userId+"&price="+price+"&quantity="+quantity+"&securityCode="+securityCode+"&priceType="+priceType,//要发送的数据
			success: function (data) {//data为返回的数据，在这里做数据绑定
				$("#speedPlacementModal").modal('hide');
				alert(data.message);
				reloadTableData();
                _this.disabled=false;
			}
		});
	}


	// 撤单
	function cancelPlacement(_this){
        _this.disabled=true;
		var placementId = $(":radio[name=riskPlacementId]:checked").val();
		if(!placementId){
			alert("请选择需要撤单的委托");
		}
		$.ajax({
			type: "get",//使用get方法访问后台
			dataType: "json",//返回json格式的数据
			url: "${ctx}/risk/cancelPlacement",//要访问的地址
			data: "placementId="+placementId,//要发送的数据
			success: function (data) {//data为返回的数据，在这里做数据绑定
				alert(data.message);
				reloadTableData();
                _this.disabled=false;
			}
		});
	}

</script>
</body>
</html>