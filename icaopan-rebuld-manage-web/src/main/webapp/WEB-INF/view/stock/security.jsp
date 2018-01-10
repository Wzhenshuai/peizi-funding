<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>股票管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>证券管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >证券名称</span>
						<input type="text" class="form-control" name="name" id="name-search" placeholder="证券名称" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >证券代码</span>
						<input type="text" class="form-control" name="code" id="code-search" placeholder="证券代码" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >状态</span>
						<select class="form-control" name="suspensionFlag" id="suspensionFlag-search">
							<option value="">请选择</option>
							<option value="1">停牌</option>
							<option value="0">正常</option>
						</select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">交易所</span>
						<%--<input type="text" class="form-control" name="exchangeCode" id="exchangeCode-search" placeholder="交易所" aria-describedby="basic-addon1">--%>
						<select class="form-control" name="exchangeCode" id="exchangeCode-search">
							<option value="">请选择</option>
							<option value="XSHE">深市</option>
							<option value="XSHG">沪市</option>
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<button class="btn btn-primary" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
				</div>
			</div>
			<table:table id="userTable" ajaxURI="${ctx}/stock/find"
						 columnNames="name,code,firstLetter,issueDateStr,flag,exchangeCode"
						 headerNames="证券名称,证券代码,拼音缩写,上市时间,状态,交易所,操作"
						 searchInputNames="exchangeCode,name,code,suspensionFlag"
						 makeDefsHtmlFunc="defs"/>
		</div>
	</div>
</div>



	<script>
		function defs(rowdata){
			var pot = "<a href=\"${ctx}/stock/edit?id="+rowdata.internalSecurityId+"\"><button class='btn btn-info'>编辑</button></a>";
			console.log(rowdata);
			return pot;
		}



		$("#updateForm").validate({
			rules:{
				name:{
					required: true,
					rangelength:[0,10]
				},
			},
			submitHandler: function(form){
				$.ajax({
					"type" : 'post',
					"url" : "${ctx}/stock/update",
					"dataType" : "json",
					"data" : $("#updateForm").serialize(),
					"success" : function(result) {
						alertx(result.message);
						/* 清空form表单 */
						$(".updateForm .form-control").val("");
						/* 关闭弹窗  */
						$('#modelForUpdate').modal('hide');
						/* 重新查询 */
						reloadTableData();
					},"error" : function (result){
						alertx("发生异常,或没权限...");
					}
				});
			}
		});





	</script>

</body>




