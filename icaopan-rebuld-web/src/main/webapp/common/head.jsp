<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/util/ajax_request.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dealmoney.js"></script>

<script type="text/javascript">
	    var systemConfig = {
	        path:"<%=path%>"
	};
function downLoad(apkOrIos){
	<%-- $("#downloadA").href="<%=basePath%>downapp/icaopan.apk";
	$("#downloadA").click(); --%>
	//window.location.href="<%=basePath%>downapp/icaopan.apk";
	if(apkOrIos=="apk"){
		window.open("http://hd.icaopan.com/app/icaopan.apk");
	}else{
		window.open("http://hd.icaopan.com/app/icaopan.ipa");
	}
}

var systemConfig = {
	    path:"<%=path%>"
	    /*path:"http://192.168.1.104:8090/GDQ"*/
}



var _hmt = _hmt || [];
(function() { var hm = document.createElement("script"); hm.src = "//hm.baidu.com/hm.js?2c84c6b4265f5872c59507d4f3bd0d0c"; var s = document.getElementsByTagName("script")[0]; s.parentNode.insertBefore(hm, s); })();
</script>
</head>

<body>
	<a href="javascript:void(0);" id="downloadA"></a>
	<div class="header">
		<div class="header-top">
			<!--手机二维码-->
			<div id="daohang" class="header-top-con">
				<li class="tab dropdown"><a  href="javascript:void(0);" onclick="javascript:void(0);"
					class="tablink arwlink"><img src="<%=basePath%>images/phone.png"><span>手机客户端</span><b></b></a>
					<ul class="subnav">
						<div class="ewm-xs-left">
							<img src="<%=basePath%>images/apk.png" width="107px" height="107px">
						</div>
						<div class="ewm-xs-right">
							<p>您的交易利器！</p>
							<p>
								<img src="<%=basePath%>images/ad.png" onclick="downLoad('apk')"></img>
							</p>
							<p>
								<img src="<%=basePath%>images/ios.png" onclick="downLoad('ios')"></img>
							</p>
						</div>
					</ul></li>
				<c:choose>
					<c:when test="${!empty sessionScope.Toft_SessionKey_UserData }">
						<a href="javascript:;">你好，${sessionScope.Toft_SessionKey_UserData.usernameHidePart }</a>|<a
							href="<%=basePath%>user/LogOut.do">退出</a>
						<img src="images/phones.png"
							style="margin-right: 5px; padding-left: 20px;" />
						<span>客服电话：<span class="color-yellow">4001-667-067</span></span>
					</c:when>
					<c:otherwise>
						<a href="<%=basePath%>user/login.jsp">登录</a>|<a
							href="<%=basePath%>user/regist_one.jsp">注册</a>
						<img src="images/phones.png"
							style="margin-right: 5px; padding-left: 20px;" />
						<span>客服电话：<span class="color-yellow">4001-667-067</span></span>
					</c:otherwise>
				</c:choose>
			</div>

		</div>
		<div class="header-con">
			<div class="logo left  clear">
				<a href="<%=basePath%>user/ViewIndex.do"><img
					src="images/logo.png" /></a>
			</div>
			<div class="nav right">
				<span><a href="<%=basePath%>user/ViewIndex.do" id="indexA">首页</a></span>
				<span><a onclick="javascript:checkLoginAndOpenDia('<%=basePath%>trade/BuyStocksInit.do')" id="stockA">我要炒股</a></span>
				<span><a onclick="javascript:checkLoginAndOpenDia('<%=basePath%>finance/MakeFinanceInit.do')" id="financeA">我要配资</a></span>
				<span><a onclick="javascript:checkLoginAndOpenDia('<%=basePath%>user/ViewMyAccount.do')" id="accountA">我的账户</a></span>
				<span><a href="<%=basePath%>guide/guideIndex.jsp" id="xsznA">新手指南</a></span>
			</div>
		</div>
	</div>
	<!-- <script src="https://kefu.qycn.com/vclient/state.php?webid=102673" language="javascript" type="text/javascript"></script> -->
	<script>
	(function() { 
		var hm = document.createElement("script");
		hm.src = "http://t.icaopan.com/dist/script/utils/kefu.js";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s); 
	})();
	</script>
</body>
</html>
