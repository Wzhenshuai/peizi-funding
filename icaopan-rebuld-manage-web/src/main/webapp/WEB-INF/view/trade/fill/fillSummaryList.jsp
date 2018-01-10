<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
<head>
<title>成交汇总查询</title>
<meta name="decorator" content="default">
<script type="text/javascript"
	src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
<script type="text/javascript">
function reload(){
	reloadTableById('Table1');
	reloadTableById('Table2');
	
	var securityCode=$("input[name='securityCode']").val();
	var startDate=$("input[name='startDate']").val();
	var endDate=$("input[name='endDate']").val();
	var customerId=$("select[name='customerId']").val();
	ajaxRequestPost("${ctx }/fill/queyrFillSummaryAmount",{securityCode:securityCode,startDate:startDate,endDate:endDate,customerId:customerId},function(r){
		$("#sumAmount").html(r);
	});
}

function exportExl(url){
	var securityCode=$("input[name='securityCode']").val();
	var startDate=$("input[name='startDate']").val();
	var endDate=$("input[name='endDate']").val();
	var customerId=$("select[name='customerId']").val();
	var type=1;
	if($("ul.nav li:eq(1)").hasClass("active")){
		type=2;
	}
	url=url+"?type="+type+"&startDate="+startDate+"&endDate="+endDate+"&securityCode="+securityCode+"&customerId="+customerId;
	location.href=url;
}
</script>
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>成交汇总查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="container-fuild">
					<div class="row">
						<div class="col-md-2">
							<c:choose>
								<c:when test="${ empty admin}">
									<div class="input-group">
										<span class="input-group-addon">资金方</span>
										<select class="form-control" name="customerId" id="customerId-search">
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
								<span class="input-group-addon" id="stocks-code">证券代码</span> <input
									type="text" class="form-control" name="securityCode"
									id="securityCode-search" placeholder="证券代码">
							</div>
						</div>
						
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon" id="basic-start">开始日期</span> <input
									type="text" class="form-control" name="startDate" value="${today }"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'date-search-end\')}'})"
									id="date-search-start" placeholder="开始日期" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon" id="basic-end">结束日期</span> <input
									type="text" class="form-control" name="endDate"
									onfocus="WdatePicker({minDate:'#F{$dp.$D(\'date-search-start\')}',maxDate:'2120-10-01'})"
									id="date-search-end" placeholder="结束日期"  value="${today }"/>
							</div>
						</div>
						<div class="col-md-2  text-right">
							<button class="btn btn-danger" onclick="reload();">
								<i class="glyphicon glyphicon-search"></i>查询
							</button>
							<button class="btn btn-danger"
								onclick="exportExl('${ctx }/fill/exportFillSummary')">
								<i class="glyphicon glyphicon-search"></i>导出
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<strong style="font-size:15px">总成交额：<span id="sumAmount" class="text-danger" style="font-size:20px;color:red">--</span> 元</strong>
						</div>
					</div>
					<div id="exTab1">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#1a" data-toggle="tab">按通道汇总</a></li>
							<li><a href="#2a" data-toggle="tab">按用户汇总</a></li>
						</ul>
						<div class="tab-content clearfix">
							<div class="tab-pane active" id="1a">
								<table:table id="Table1"
                                             ajaxURI="${ctx}/fill/queryFillSummary?type=1"
                                             columnNames="channelName,securityCode,securityName,side,quantity,amount,price"
                                             headerNames="通道名称,股票代码,股票名称,交易方向,成交数量,成交金额,成交均价"
                                             searchInputNames="securityCode,startDate,endDate,customerId"
                                             pageLength="20"
									/>
							</div>
							<div class="tab-pane" id="2a">
								<table:table id="Table2"
                                             ajaxURI="${ctx}/fill/queryFillSummary?type=2"
                                             columnNames="userName,securityCode,securityName,side,quantity,amount,price"
                                             headerNames="用户名,股票代码,股票名称,交易方向,成交数量,成交金额,成交均价"
                                             searchInputNames="securityCode,startDate,endDate,customerId" pageLength="20"
									/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
    </div>
<script> 
    $(function () { 
    
      $('#exTab1 a').click(function (e) { 
        e.preventDefault();//阻止a链接的跳转行为 
        reload();
        $(this).tab('show');//显示当前选中的链接及关联的content 
      }) 
      
      reload();
    }) 
    
  </script>
</body>
</html>



