<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page language="java" import="java.security.SecureRandom"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<%
	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	long seq = random.nextLong();
	String random_session = Long.toString(seq) + "";
	session.setAttribute("random_session", random_session);
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript"
	src="<%=basePath%>js/util/jquery-1.8.0.min.js"></script>
<link href="<%=basePath%>css/icp-login.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript">
	var systemConfig = {
	    path:"<%=path%>"
	};
</script>
<script type="text/javascript">
	function enterkey(e) {
		e = event.keyCode;
		if (e == 13 || e == 32) {
			checkInput();
			event.returnValue = false; // 取消此事件的默认操作 
		}
	}
	function checkInput() {
		var username = $("#login_uin").val();
		var password = $("#login_pwd").val();
		if (username == "" || password == "") {
			$("#allError").html("账号和密码不能为空");
			return false;
		} else {
			$("#pop_login").submit();
			var btn2 = document.getElementById("submitLogin");
			btn2.disabled = true;
		}
	}
</script>
</head>
<body onkeydown="enterkey()">
	<div id="container">
		<div class="login-con-980">
			<div class="log-pop">
				<form id="pop_login" name="login" method="post"
					action="<%=basePath%>user/UserLogin.do">
					<input type="hidden" name="random_form" value="<%=random_session%>" />
					<input name="userType" type="hidden"
						value="<c:if test='${not empty userType }'>${userType }</c:if><c:if test='${not empty param.userType }'>${param.userType }</c:if>" />
					<div class="banner-login-1 right">
						<h2 class="h2-2">登录</h2>
						<div class="log-user">
							<input name="name" type="text" placeholder="请输入账号" />
						</div>
						<div class="log-password">
							<input name="pwd" type="password" placeholder="请输入密码" />
						</div>
						<div id="allError" class="text-red error">${requestScope.errorInfo}</div>
						<%-- <p class="wjmm"><span><a href="<%=basePath%>user/regist_sjh.jsp">忘记登录密码</a></span></p> --%>
						<div class="log-btn clear">
							<input name="" id="submitLogin" type="submit" value="登录"
								onclick="return checkInput();" />
						</div>
						<%-- <p class="mfzc"><a class="text-orange" href="<%=basePath%>user/regist_one.jsp">免费注册</a></p> --%>
						<input type="hidden" name="visitPath"
							value="<c:choose><c:when test='${empty visitPath }'>${param.visitPath }</c:when><c:otherwise>${visitPath }</c:otherwise></c:choose>" />
					</div>
				</form>
			</div>
			<!--待确认弹窗结束-->
		</div>
	</div>
</body>
</html>
