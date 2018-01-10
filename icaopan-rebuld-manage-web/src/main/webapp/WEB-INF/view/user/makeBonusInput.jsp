<%@ page import="com.icaopan.admin.model.AdminUser" %>
<%@ page import="com.icaopan.admin.realm.LoginRealm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>录入新红配</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>录入新红配
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<form id="inputForm" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="internalSecurityId">股票代码</label>
						<div class="col-sm-4">
							<input id="securityCode" class="form-control" name="securityCode"  aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="available">每10股送股数</label>
						<div class="col-sm-4">
							<input type="number" class="form-control" id="distributeStockAmount" name="distributeStockAmount" value="0" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="amount">每10股转赠股数</label>
						<div class="col-sm-4">
							<input type="number" class="form-control" id="donationStockAmount" name="donationStockAmount" value="0" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="costPrice">每10股送红利</label>
						<div class="col-sm-4">
							<input id="bonusProfit" type="number" class="form-control" name="bonusProfit" value="0"  aria-describedby="basic-addon1">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-6">
							<input type="submit" class="btn btn-danger btn-sm" value="提 交">
							<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="history.go(-1)">返 回</button>

						</div>
					</div>
			</form>
		</div>
		
		<div class="table-responsive" style="margin-top:200px">
			<form  class="form-horizontal" method="post" action="${ctx}/stockBonus/uploadBonusFile" enctype="multipart/form-data">
				<div class="form-group">
						<label class="col-sm-3 control-label" for="available">选择新红配txt文件批量导入</label>
						<div class="col-sm-4">
							<input type="file" name="file" />
						</div>
				</div>
				<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-6">
							<input type="submit" class="btn btn-danger btn-sm" value="上传文件">
						</div>
					</div>
			</form>
		</div>
	</div>
</div>
<script>

	$(function(){
		$("#inputForm").validate({
			onsubmit:true,// 是否在提交时验证,默认也是true
			rules:{
				securityCode:{
					required: true,
					rangelength:[6,6]
				},
				distributeStockAmount:{
					required: true,
				},
				donationStockAmount:{
					required: true,
				},
				bonusProfit:{
					required: true,
				},
			},
			submitHandler: function(form) {
				var securityCode = $('#securityCode').val();
				var distributeStockAmount = $('#distributeStockAmount').val();
				var donationStockAmount = $('#donationStockAmount').val();
				var bonusProfit = $('#bonusProfit').val();
				$.ajax({
					"type" : 'post',
					"url" : "${ctx}/stockBonus/makeBonus",
					"dataType" : "json",
					"data" : {
						"securityCode":securityCode,
						"distributeStockAmount":distributeStockAmount,
						"donationStockAmount":donationStockAmount,
						"bonusProfit":bonusProfit
					},
					"success" : function(result) {
						if(result.rescode == "success") {
							alertx("录入成功");
							window.history.go(-1);
						}else{
							alertx(result.message);
						}
					}
				});

			},
		});
	});
	
</script>
</body>
</html>