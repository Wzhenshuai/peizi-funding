<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
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
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>i操盘</title>
<link  href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/valide.js"></script>
<script type="text/javascript">
	    var systemConfig = {path:"<%=path%>"};    
</script>
<script type="text/javascript">
$(function(){
	$("#password").focus(function(){
	});
	$("#password").blur(checkPass);
	$("#repassword").blur(checkRePass);
	document.onkeydown = function(e){    
		var ev = document.all ? window.event : e;  
		if(ev.keyCode==13) {
		    $("#surebtn").click();
		 }  
	};
});
function checkPass(){
	var password=$.trim($("#password").val());
	var strExp=/^[a-zA-Z0-9]{6,16}$/;
	if(password==""||password==null){
		$("#password_tips").html("密码不能为空");
		return false;
	}
	if(password.length<6||password.length>16){
		$("#password_tips").html("密码只能6-16位数字和字母混合组成");
		return false;
	}
	if(!strExp.test(password)){
		$("#password_tips").html("密码只能由数字和字母组成");
		return false;
	}
	$("#password_tips").html("");
	return true;
};
function checkRePass(){
	var password=$.trim($("#password").val());
	var repassword=$.trim($("#repassword").val());
	var strExp=/^(?!\d+$)(?![A-Za-z]+$)[a-zA-Z0-9]{6,}$/;
	checkPass();
	if(repassword==""||repassword==null){
		$("#repassword_tips").html("确认密码不能为空");
		return false;
	}
	if(password!=repassword){
		$("#repassword_tips").html("两次密码不相同");
		return false;
	}
	$("#repassword_tips").html("");
	return true;
};
function findPassTwo(ele){
	if(checkPass()&&checkRePass()){
		$("#regist_reset").submit();
	}
}
</script>
</head>
<body>
<jsp:include page="/user/user_head.jsp" />
<div class="login-main">
  <div class="login-main-con-2">
  <h3><img src="images/icon_03.png" ><span class="mar-l-5">请设置新的登录密码</span>&nbsp;&nbsp;&nbsp;<span class="a-10 color-808080 font-nor">请设置6位以上密码，字母数字混合</span></h3>
  <table width="503" border="0" cellspacing="0" class="login-table">
   <form id="regist_reset" name="regist_reset" method="post" action="<%=basePath%>user/FindPassWordTwo.do">
  	<input type="hidden" name="random_form" value=<%=random_session%>></input>
  	<input type="hidden" name="userid" value="${userid }"></input>
    <tr>
      <th scope="row"><span>新密码</span></th>
      <td rowspan="2"><input type="password" name="password" id="password" value="" /><div class="error" id="password_tips"><span></span></div></td>
      </tr>
    <tr>
      <th scope="row">&nbsp;</th>
    </tr>
    <tr>
      <th scope="row"><span>再次输入</span></th>
      <td rowspan="2"><input type="password" name="repassword" id="repassword" value=""/>
      <div class="error" id="repassword_tips"><span>${errorInfo}</span>
      
      </div></td>
      </tr>
    <tr>
      <th scope="row">&nbsp;</th>
    </tr>
    <tr>
      <td colspan="2" scope="row"><a onclick="javascript:findPassTwo(this);" id="surebtn"><img src="images/btn_fin.png" class="btn-l-90"></a></td>
    </tr>
    </form>
  </table>
  </div>
</div>
<jsp:include page="/user/user_foot.jsp" />
</body>
</html>
