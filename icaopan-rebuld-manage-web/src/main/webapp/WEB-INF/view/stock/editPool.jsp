<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>编辑股票池信息</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>编辑股票池信息
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
				<form id="inputForm" modelAttribute="stockPool" class="form-horizontal">
					<input type="hidden" name="id" value="${stockPool.id}"/>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="stockName">股票名称:</label>
						<div class="col-sm-4">
							<input  type="text"  id="stockName" class="form-control" name="stockName" value="${stockPool.stockName}" placeholder="股票名称" aria-describedby="basic-addon1">
							<input type="hidden" id="nameHide" value="${stockPool.stockName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="stockCode">股票编号:</label>
						<div class="col-sm-4">
							<input  type="text"  id="stockCode" class="form-control" name="stockCode" value="${stockPool.stockCode}" placeholder="股票编号" aria-describedby="basic-addon1">
							<input type="hidden" id="codeHide" value="${stockPool.stockCode}">
						</div>
					</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="type">状态:</label>
							<div class="col-sm-4">
								<select id="type" class="form-control" name="type" value="${stockPool.type}" _value="${stockPool.type}">
									${fns:getDicOptions("pooltype")}
								</select>
							</div>
						</div>
						<div class="form-group">
							<label id="tips" class="label label-danger"></label>
						</div>
					<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-6">
							<input type="button" class="btn btn-danger btn-sm" onclick="verify()" value="保 存">
							<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="history.go(-1)">返 回</button>
						</div>
					</div>
				</form>
		</div>
	</div>
</div>

<script>
	//验证事件
	function verify()
	{
		var nameHide=$('#nameHide').val();
		var codeHide=$('#codeHide').val();
		var code = $('#stockCode').val();
		var name = $('#stockName').val();
		console.log(name==nameHide);
		console.log(name+"-->"+nameHide);
		name = (name==nameHide)?null:name;
		code = (code==codeHide)?null:code;
		$.ajax({
			"type" : 'post',
			"url" : "${ctx}/pool/verify",
			"dataType" : "json",
			"data" : {
				"stockName":name,
				"stockCode":code
			},
			"success" : function(result) {
				if(result.rescode == "success") {
					$.ajax({
						"type" : 'post',
						"url" : "${ctx}/pool/update",
						"dataType" : "json",
						"data" : $("#inputForm").serialize(),
						"success" : function(result) {
							alertx(result.message);
							/* 清空form表单 */
							$(".inputForm .form-control").val("");
							/*跳转*/
							window.location.href="${ctx}/pool/pool";
						},"error" : function (result){
							alertx("发生异常,或没权限...");
						}
					});
				}else{
					$('#tips').text(result.message);
				}

			},"error" : function (result){
				$('#tips').text(result.message);
			}
		});
	}

</script>
</body>
</html>