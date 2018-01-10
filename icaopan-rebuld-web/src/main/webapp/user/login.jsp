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
	response.setHeader("Pragma","No-cache");    
	response.setHeader("Cache-Control","no-cache");    
	response.setDateHeader("Expires", -10);   
%>
<%
	SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
	long seq=random.nextLong();
	String random_session=Long.toString(seq)+"";
	session.setAttribute("random_session",random_session);
%>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="<%=basePath%>js/util/jquery-1.8.0.min.js"></script>
<link rel="icon" href="<%=basePath%>images/gdq.ico" mce_href="<%=basePath%>images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>images/gdq.ico" mce_href="<%=basePath%>images/gdq.ico" type="image/x-icon" />
<link href="<%=basePath%>css/icp-login.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	var systemConfig = {
	    path:"<%=path%>"
	};
</script>
<script type="text/javascript">
	var proverbs=[{'pMain':'很多时候，人们宁愿得到一张下周可能会赢得大奖的彩票，也不愿抓住一个可以慢慢致富的机会。','author':'——沃伦·巴菲特'},
	              {'pMain':'今天的投资者不是从昨天的增长中获利的。','author':'——沃伦·巴菲特'},
	              {'pMain':'利率就像是投资上的地心引力一样。','author':'——沃伦·巴菲特'},
	              {'pMain':'我们欢迎市场下跌，因为它使我们能以新的、令人感到恐慌的便宜价格拣到更多的股票。','author':'——沃伦·巴菲特'},
	              {'pMain':'不能承受股价下跌50%的人就不应该炒股。','author':'——沃伦·巴菲特'},
	              {'pMain':'重要的不是你的判断是错还是对，而是在你正确的时候要最大限度地发挥出你的力量来！','author':'——乔治·索罗斯'},
	              {'pMain':'在股票市场上，寻求别人还没有意识到的突变。','author':'——乔治·索罗斯'},
	              {'pMain':'投资成功的关键——耐力胜过头脑。','author':'——彼得·林奇'},
	              {'pMain':'以近期的眼光看，股市是一个投票箱；以长远的眼光看，股市是一个天平。','author':'——本杰明·格雷厄姆'},
	              {'pMain':'市场也经常处于不定状态，投资者如果能对明显的事物打个折扣，而把赌注放在别人意想不到的事物上，则必将获得大利。','author':'——乔治·索罗斯'}];
	function enterkey(e) { 
		e = event.keyCode; 
		if (e==13||e==32) { 
			checkInput();
			event.returnValue= false; // 取消此事件的默认操作 
		}
	} 
	function checkInput() {
		var username = $("#login_uin").val();
		var password = $("#login_pwd").val();
		if (username == "" || password == "") {
			$("#allError").html("账号和密码不能为空");
			return false;
		}
		else{
			$("#pop_login").submit();
			var btn2 = document.getElementById("submitLogin");
			btn2.disabled=true;
		}
	}
	
	$(function(){
		var len=proverbs.length;
		var _index = Math.floor(Math.random()*len);//得到[0,len)的随机数
		$('#proverbs').text(proverbs[_index].pMain);
		$('#proverbs-author').text(proverbs[_index].author);
	});
</script>
</head>
<body onkeydown="enterkey()">
<div id="container">
<jsp:include page="../common/header.jsp" />
<div class="login-con">
    <div class="login-con-980">
    	<div class="login-yjh"><p id="proverbs"></p><p class="fr" id="proverbs-author"></p></div>
      <!--待确认弹窗-->
    <div class="log-pop">
	    <form id="pop_login" name="login" method="post" action="<%=basePath%>user/UserLogin.do">
	    	<input type="hidden" name="random_form" value="<%=random_session%>" />
	    	<input name="userType" type="hidden"  value="<c:if test='${not empty userType }'>${userType }</c:if><c:if test='${not empty param.userType }'>${param.userType }</c:if>"/>
            <div class="banner-login-1 right">
                <h2 class="h2-2">登录</h2>
                <div class="log-user"><input name="name" type="text" placeholder="请输入账号"/></div>
                <div class="log-password"><input name="pwd" type="password" placeholder="请输入密码"/></div>
                <div id="allError" class="text-red error">${requestScope.errorInfo}</div>
                <%-- <p class="wjmm"><span><a href="<%=basePath%>user/regist_sjh.jsp">忘记登录密码</a></span></p> --%>
                <div class="log-btn clear"><input name="" id="submitLogin" type="submit" value="" onclick="return checkInput();"/></div>
                <%-- <p class="mfzc"><a class="text-orange" href="<%=basePath%>user/regist_one.jsp">免费注册</a></p> --%>
				<input type="hidden" name="visitPath" value="<c:choose><c:when test='${empty visitPath }'>${param.visitPath }</c:when><c:otherwise>${visitPath }</c:otherwise></c:choose>" />
			</div>
	     </form>
    </div>
    <!--待确认弹窗结束-->
    </div>	
</div>
<jsp:include page="/common/footer.jsp" />
</div>
</body>
</html>
