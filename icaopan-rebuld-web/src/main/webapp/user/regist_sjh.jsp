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
<title></title>
<link  href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<script type="text/javascript" src="<%=basePath%>js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/valide.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/ajax_request.js"></script>
<script type="text/javascript">
	    var systemConfig = {path:"<%=path%>"};    
</script>
<script type="text/javascript">

var loginBuarful = true;

$(function(){
	$(".error").html("");
	$("#mobile").blur(checkFindMobile);
	$("#code").blur(checkCode);
	document.onkeydown = function(e){    
		var ev = document.all ? window.event : e;  
		if(ev.keyCode==13) {
		    $("#surebtn").click();
		 }
	};
});
function checkFindMobile(){
	var mobile=$.trim($("#mobile").val());
	if(mobile==""||mobile=="请输入你的手机号"){
		$("#mobile_tips").html("手机号不能为空");
		return false;
	}
	if(!valid.isMobile(mobile)){
		$("#mobile_tips").html("手机号不正确");
		return false;
	}
	var checkFindMobile=false;
	ajaxReuqestSynchronize(systemConfig.path + "/user/CheckFindMobile.ajax", {"mobile":mobile}, function(r){
		if(r.checkFindMobile){
			$("#mobile_tips").html("");
			checkFindMobile=true;
		}else{
			$("#mobile_tips").html("对不起,你输入的手机号有误");
			return false;
		}
	});
	return checkFindMobile;
}
function checkCode(){
	var code=$.trim($("#code").val());
	var mobile=$.trim($("#mobile").val());
	if(code==""||code==null){
		$("#code_tips").html("验证码不能为空");
		return false;
	}
	var checkCode=false;
	ajaxReuqestSynchronize(systemConfig.path + "/user/VerfySendCode.ajax",{"mobile" : mobile,"code" : code},
		function(res) {
			if (res.resCode == "mobile_is_empty") {
				$("#code_tips").html("请输入手机号");
				return false;
			} else if (res.resCode == "code_is_empty") {
				$("#code_tips").html("请输入验证码");
				return false;
			} else if (res.resCode == "server_code_is_empty") {
				$("#code_tips").html("请获取验证码");
				return false;
			}else if(res.resCode=="verify_mobile_time_out"){
				$("#code_tips").html("验证码超时，请重新获取验证码");
				return false;
			}else if (res.resCode == "fail") {
				$("#code_tips").html("验证码不正确");
				return false;
			}else if (res.resCode == "success") {
				$("#code_tips").html("");
				checkCode=true;
			}
		}
	);
	return checkCode;
}
function findPassOne(ele){
	if(checkFindMobile()&&checkCode()){
		$('#regist_sjh').submit();
	}
}
//发送手机验证码
function sendCode() {
	
	if(!loginBuarful){
		return false;
	}
	loginBuarful = false;
	var mobile=$.trim($("#mobile").val());
	var val=checkFindMobile();
	if(val==false){
		return;
	}
	if(!valid.isMobile(mobile)){
		$("#mobile_tips").html("手机号不正确");
		return false;
	}
	$.post(systemConfig.path + "/user/SendCode.ajax", {
		"mobile" : mobile
		},function(res){
			changeButtonStyleForSendCodeToWait("codeBtn");
			if(res.resCode=="mobile_is_empty"){
				$("#code_tips").html("请输入手机号");
				return false;
			}else if(res.resCode=="send_more"){
				$("#code_tips").html("您发送太频繁，请稍后发送");
				return false;
			}else if(res.resCode=="fail"){
				$("#code_tips").html("发送失败，请重新发送");
				return false;
			}else if(res.resCode=="success"){
				$("#code_tips").html("发送成功");
				return true;
			}
		},"json");
}
//获取验证码超时
function changeButtonStyleForSendCodeToWait(btnId) {
	var txt = "秒";
	var startData = 60;
	$("#" + btnId).attr("disabled", "disabled");
	inter = setInterval(function() {
		if (startData <= 0) {
			loginBuarful = true;
			$("#" + btnId).removeAttr("disabled");
			$("#" + btnId).val("获取验证码");
			clearInterval(inter);
		} else {
			$("#" + btnId).attr("disabled", "disabled");
			startData--;
			$("#" + btnId).val(startData + txt);
		}
	}, 1000);
}
//获取验证码超时
function changeSendVoiceCodeForgetPassToWait(btnId) {
	if(!loginBuarful){
		return false;
	}
	loginBuarful = false;
	var txt = "秒";
	var startData = 60;
	$("#" + btnId).html("");
	$("#" + btnId).removeAttr("onclick");
	inter1 = setInterval(function() {
		if (startData <= 0) {
			loginBuarful = true;
			$("#" + btnId).html("语音验证码");
			$("#" + btnId).attr("onclick","sendVoiceCodeForgetPass();");
			clearInterval(inter1);
		} else {
			startData--;
			$("#" + btnId).html(startData + txt);
		}
	}, 1000);
}
//发送语音验证码
function sendVoiceCodeForgetPass(){
	if(!loginBuarful){
		return false;
	}
	loginBuarful = false;
	
	var mobile=$.trim($("#mobile").val());
	var val = checkFindMobile();
	if(!val){
		return false;
	}
	if(!valid.isMobile(mobile)){
		$("#mobile_tips").html("手机号不正确");
		return false;
	}
	var type = "template_forget_password";
	ajaxReuqestAsync(systemConfig.path + "/user/SendVoiceCode.ajax", {
		"mobile" : mobile,
		"type" : type
		},function(res){
			changeSendVoiceCodeForgetPassToWait("voiceCode");
			if(res.result=="success"){
				$("#code_tips").html("发送成功");
				return true;
			}else{
				$("#code_tips").html(res.result);
				return false;
			}
		},"json");
	}
	$(function (){
		$('#codeBtn').removeAttr('disabled');
	})
</script>
</head>
<body>
<jsp:include page="/user/user_head.jsp" />
<div class="login-main">
  <div class="login-main-con-2">
  <h3><img src="images/icon_03.png" /><span class="mar-l-5">请输入你注册时的手机号</span></h3>
  <div class="error"><span>${requestScope.errorInfo}</span></div>
  <table width="503" border="0" cellspacing="0" class="login-table">
   <form id="regist_sjh" name="regist_sjh" method="post" action="<%=basePath%>user/FindPassWordOne.do">
  	<input type="hidden" name="random_form" value=<%=random_session%> />
    <tr>
      <th width="79" scope="row">手机号</th>
      <td rowspan="2" valign="top"><input id="mobile" name="mobile" type="text"  value=""/><div class="error" id="mobile_tips" ><span>提示：12123</span></div></td>
    </tr>
    <tr>
      <th scope="row">&nbsp;</th><td></td>
    </tr>
    <tr>
      <th scope="row">验证码</th>
      <td rowspan="2">
	      <input type="text" name="code" id="code" class="inp-code left"  value="" />
	      <input name="" type="button" value="获取验证码" id="codeBtn" class="inp-btn-blue mar-l-5" onclick="sendCode();"/>&nbsp;&nbsp;&nbsp;
	      <a id="voiceCode" onclick="javascript:sendVoiceCodeForgetPass();" style="color:#3f99d2;font-size: 14px;">语音验证码</a>
	      <div class="error" id="code_tips"><span></span></div>
	  </td>
    </tr>
    <tr>
      <th scope="row">&nbsp;</th><td></td>
    </tr>
    <tr>
    	<th scope="row"></th>
	    <td colspan="2" scope="row">
	    	<a onclick="javascript:findPassOne(this);" id="surebtn"><img src="<%=basePath%>images/btn_03.png" class="btn-l-90"/></a>
	    </td>
    </tr>
    </form>
  </table>
  </div>
</div>
<%-- <jsp:include page="/user/user_foot.jsp" /> --%>
</body>
</html>
