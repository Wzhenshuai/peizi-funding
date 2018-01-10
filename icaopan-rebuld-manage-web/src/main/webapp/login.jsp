<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行情精灵后台管理系统 | 登录</title>
<script type="text/javascript">
if (self.frameElement && self.frameElement.tagName == "IFRAME") {
	parent.window.location.reload();
}
</script>
<jsp:include page="STATICS/common/header.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/STATICS/icaopan-css/login-css.css" rel="stylesheet" type="text/css"/>
</head>
<body class="login"  style="background-image: url('${pageContext.request.contextPath}/STATICS/icaopan-img/login-bg-2.jpg');">
	<div class="container">
		<div class="row">
			<div class="col-md-4">
			</div>
			<div class="col-md-4">
				<form class="login-form" id="login-form">
					<%-- <img src="${pageContext.request.contextPath}/STATICS/icaopan-img/logo.png" class="logo"/> --%>
					<br/>
					<div class="form-group">
						<div class="input-group">
					         <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
					         <input type="text" class="form-control" name="username" placeholder="用户名">
					    </div>	
					</div>
					<div class="form-group">
						<div class="input-group">
					         <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
					         <input type="password" class="form-control" name="password" placeholder="密  &nbsp; 码">
					    </div>
					</div>
					<b><span id="errorMsg" style="color: red">&nbsp;</span></b>
					<div class="form-actions">
						<label class="checkbox re-box">
<!-- 						<input type="checkbox" name="remember" value="1" />
 -->						</label>
						<button type="button" id="login_btn" class="btn btn-primary btn-resize">登  录</button>
					</div>
					<br/>
				</form>
			</div>
			<div class="col-md-4">
			</div>
		</div>
	</div>	
	<jsp:include page="STATICS/common/footer.jsp"></jsp:include>
	<!-- 登录 -->
	<script type="text/javascript">
	$(function(){
		/* 回车键触发 */
		document.onkeydown  = function(e){
			var ev = document.all ? window.event : e;
			if(ev.keyCode==13){
				submitForm();
			}
		};
		
		/* 点击触发 */
		$("#login_btn").click(function(){
			submitForm();
		});
		
	});
	
	/* 提交表单 */
	function submitForm(){
		$.ajax({
			  url: "${pageContext.request.contextPath}/user/login",
			  type:"POST",
			  dataType: 'json',
			  data: $("#login-form").serialize(),
			  success: function(result){
				  if (result.rescode != "success"){
					  $("#errorMsg").html(result.message);
				  }else {
					  $("#errorMsg").html("&nbsp;");
					  window.location.href="${pageContext.request.contextPath}/user/home";
				  }
			  }
		});
	}
	
	</script>
</body>
</html>