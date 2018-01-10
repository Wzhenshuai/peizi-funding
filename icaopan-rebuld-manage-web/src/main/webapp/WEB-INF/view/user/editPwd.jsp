<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee">重置密码</i>
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
				<form id="inputForm" modelAttribute="user" class="form-horizontal">
					<div class="modal-body">
						<input type="hidden" name="id" value="${user.id}"/>
						<div class="form-group">
							<label class="col-sm-3 control-label">用户名<span class="help-inline"><font color="red">*</font> </span>:</label>
							<div class="col-sm-3">
								<input name="userName" id="userName" value="${user.userName}" readonly class="form-control bg-success" maxlength="50" class="required"/>
							</div>
							<div class="col-sm-1 control-label"><label id="nameTips"></label></div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">真实姓名<span class="help-inline"><font color="red">*</font> </span>:</label>
							<div class="col-sm-3">
								<input name="userName" id="realName" value="${user.realName}" readonly class="form-control bg-success" maxlength="50" class="required"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="passWord">密码：</label>
							<div class="col-sm-3">
								<input  type="password"  id="passWord" class="form-control" name="passWord" placeholder="请输入新密码" aria-describedby="basic-addon1">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="rePassWord">确认密码：</label>
							<div class="col-sm-3">
								<input  type="password"  id="rePassWord" class="form-control" name="rePassWord" placeholder="请输再次入新密码" aria-describedby="basic-addon1">
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-6">
							<button type="button" class="btn btn-default" data-dismiss="modal" onclick="history.go(-1)">返 回</button>
							<input type="submit" class="btn btn-primary" value="保 存">
						</div>
					</div>
				</form>
		</div>
	</div>
</div>

<script>

$(function(){
	$("#inputForm").validate({
		rules:{
			passWord:{
				required: true,
				rangelength:[5,10]
			},
			rePassWord:{
				required: true,
				rangelength:[5,10],
				equalTo:"#inputForm input[name='passWord']"
			}
		},
		submitHandler: function(form){
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/frontUser/updatePwd",
				"dataType" : "json",
				"data" : $("#inputForm").serialize(),
				"success" : function(result) {
					alertx(result.message);
					window.location.href = "${ctx}/frontUser/userIndex";
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
	});
});

</script>
</body>
</html>