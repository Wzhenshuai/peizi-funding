<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>股票今日新红配查询</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee">今日新红配查询</i>
		</div>
	</div>
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-3">
				<div class="input-group">
					<span class="input-group-addon" id="basic-start">除权除息日</span>
					<input
						type="text" class="form-control" name="tradeDate" value="${tradeDate }" 
						id="tradeDate" placeholder="除权除息日" onfocus="WdatePicker()"/>
				</div>
			</div>
			<div class="col-md-2">
				<button class="btn btn-danger" onclick="queryData();"><i class="glyphicon glyphicon-search"></i>查询</button>
			</div>
			<div class="col-md-7 text-right">
				
				<button class="btn btn-danger" onclick="importToICP();"> <i class="glyphicon glyphicon-search"></i>导入到系统</button>
				
				<button type='button' id="add" class='btn btn-danger' onclick="downloadTxt()">下载到txt</button>
				
				<button class="btn btn-danger" onclick="javascript:location.href='${ctx }/stockBonus/stockBonusList"> <i class="glyphicon glyphicon-search"></i>返回</button>
			</div>
			<div class="col-md-12" style="margin-top:20px">
				<h5 style="color:red">*获取结果是爬取同花顺网站，结果不一定准备，请跟正规网站进行比对</h5>
			</div>
			<div class="col-md-12" style="margin-top:20px">
				<table class="table table-bordered table-striped" id="myTable">
					<tr><th>股票代码</th><th>股票名称</th><th>每10股送红利（元）</th><th>每10股送股数（股）</th><th>每10股转赠股数（股）</th><th>股权登记日</th><th>除权除息日</th></tr>
					<c:forEach items="${dataList }" var="data">
						<tr><td>${data.securityCode }</td><td>${data.securityName }</td><td>${data.bonusProfit }</td><td>${data.distributeStockAmount }</td><td>${data.donationStockAmount }</td><td>${data.recordDate }</td><td>${data.exDivDate }</td></tr>
					</c:forEach>
				</table>
			</div> 
		</div>
	</div>
</div>
<script>

	function queryData(){
		var tradeDate=$("#tradeDate").val();
		if($.trim(tradeDate)==""){
			alertx("请选择除权除息交易日");
			return;
		}
		location.href="${ctx }/stockBonus/autoGetScheme?tradeDate="+tradeDate;
	}

	function importToICP(){
		if(confirm("确定要导入到系统吗？")){
			var tradeDate=$("#tradeDate").val();
			ajaxRequestPost("${ctx }/stockBonus/importToICP",{tradeDate:tradeDate},function(r){
				if(r.rescode=="success"){
					alertx("处理成功");
					location.href="${ctx }/stockBonus/stockBonusList";
				}else{
					alertx(r.message);
				}
			});
		}
	}
	
	function downloadTxt(){
		var tradeDate=$("#tradeDate").val();
		location.href="${ctx }/stockBonus/downLoadTxt?tradeDate="+tradeDate;
	}
</script>

</body>




