<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>证券信息管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>编辑证券信息
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
				<form:form id="inputForm" modelAttribute="stockSecurity" class="form-horizontal">
						<input type="hidden" id="internalSecurityId" name="internalSecurityId" value="${stockSecurity.internalSecurityId}"/>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="name">股票名称</label><input type="hidden" id="nameHide" name="nameHide" value="${stockSecurity.name}">
							<div class="col-sm-4">
								<input  type="text"  id="name" value="${stockSecurity.name}" class="form-control" name="name" placeholder="股票名称" aria-describedby="basic-addon1">
								<label id="tipsName" class="warning"></label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="name">股票代码</label>
							<div class="col-sm-4">

								<input  type="text"  value="${stockSecurity.code}" readonly class="form-control"  placeholder="股票代码" aria-describedby="basic-addon1">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="firstLetter">拼音缩写</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="firstLetter" value="${stockSecurity.firstLetter}" id="firstLetter" placeholder="拼音缩写" aria-describedby="basic-addon1">
							</div>
						</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="suspensionFlag">停牌状态</label>
						<div class="col-sm-4">
							<select name="suspensionFlag" id="suspensionFlag" value="${stockSecurity.suspensionFlag}" _value="${stockSecurity.suspensionFlag}" class="form-control">
								<option value="false">正常</option>
								<option value="true">停牌</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label"></label>
						<div class="col-sm-4">
							<label id="alertLabel" class="badge-warning"></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-6">
							<button class="btn btn-danger btn-sm" onclick="return verifySecurity();">提交</button>
							<input type="button" onclick="history.go(-1)" class="btn btn-default btn-sm" value="返 回"/>
						</div>
					</div>
				</form:form>
		</div>
	</div>
</div>

<script>

	/**
	 * 保存用户信息
	 * */
	function sub(){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx}/stock/update",
			"dataType" : "json",
			"data" : $("#inputForm").serialize(),
			"success" : function(result) {
				alertx(result.message);
				/* 清空form表单 */
				$(".inputForm .form-control").val("");
				/*跳转*/
				window.location.href="${ctx}/stock/index";
			},"error" : function (result){
				alertx("发生异常,或没权限...");
			}
		});
	}

	//验证事件
	function verifySecurity()
	{
		var name = $('#name').val();
		var nameHide = $('#nameHide').val();
		name=(name==nameHide || name.length==0)?null:name;
		$.ajax({
			"type" : 'post',
			"url" : "${ctx}/stock/verify",
			"dataType" : "json",
			"data" : {
				"name":name
			},
			"success" : function(result) {
				if(result.rescode == "success"){
					$('#alertLabel').text("");
					sub();
				}else{
					$('#alertLabel').text(result.message);
					alertx(result.message);
					return false;
				}
			},"error" : function (result){
				alertx("发生异常,或没权限...");
				return false;
			}
		});
		return false;
	}

</script>
</body>
</html>