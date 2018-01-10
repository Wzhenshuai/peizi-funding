<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
<%@ page language="java" import="java.security.SecureRandom"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
<script type="text/javascript">
	var systemConfig = {
			path:"<%=path%>"
		};    
</script>
<link  href="<%=basePath%>css/icp-login.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<script type="text/javascript" src="${ctx }/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx }/js/function/valide.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="${ctx }/js/function/verifyIdNumber.js" charset="utf-8"></script>  
<script type="text/javascript" src="<%=basePath%>/js/util/ajax_request.js"></script>
<script type="text/javascript">
	$(function(){
		$("#realname").blur(checkRealName);
		$("#idnumber").blur(checkIdNumber);
		document.onkeydown = function(e){    
			var ev = document.all ? window.event : e;  
			if(ev.keyCode==13) {
			    $("#surebtn").click();
			 }  
		}; 
	});
	var checkRealName=function(){
		var realname=$.trim($("#realname").val());
		var realnameValid=valid.isChinaName(realname);
	 	var regular = /^([^\`\+\~\!\#\$\%\^\&\*\(\)\|\}\{\=\"\'\！\￥\……\（\）\——]*[\+\~\!\#\$\%\^\&\*\(\)\|\}\{\=\"\'\`\！\?\:\<\>\•\“\”\；\‘\‘\〈\ 〉\￥\……\（\）\——\｛\｝\【\】\\\/\;\：\？\《\》\。\，\、\[\]\,]+.*)$/;
		if(regular.test(realname)){
	    	$("#realname_tips").addClass('tips-error').html("真实姓名不能含有特殊字符").show();
			return false;
	    }
		if(realname=null||realname==""||realname=="请输入真实姓名"){
			$("#realname_tips").addClass('tips-error').html("<i></i>真实姓名不能为空").show();
			return false;
		}
		if(!realnameValid){
			$("#realname_tips").addClass('tips-error').html("<i></i>真实姓名要为汉字").show();
			return false;
		}
		$("#realname_tips").hide();
		return true;
	}; 
	//验证身份证号
	var checkIdNumber = function(){
		var idnumber = $("#idnumber").val();
		if ($.trim(idnumber) == ""||$.trim(idnumber)=="请输入身份证号") {
			$("#idnumber_tips").addClass('tips-error').html("<i></i>身份证号不能为空").show();
			return;
		}
		if(!IdentityCodeValid(idnumber) || !IdCardValidate(idnumber)){
			$("#idnumber_tips").addClass('tips-error').html("<i></i>身份证号输入不合法").show();
			return;
		}
		//判断身份证号是否已经存在
		var checkID=false;
		$.ajax({url: systemConfig.path+"/user/checkIdNumber1.ajax",
            type: 'post',
            dataType: "json",
            data: {"idNumber" : idnumber},
            async:false,
            success: function(res){
            	if (res.checkIdNumber) {
    				checkID= true;
    				$("#idnumber_tips").hide();
    			} else if(!res.checkIdNumber){
    				$("#idnumber_tips").addClass('tips-error').html("<i></i>身份证号已经存在").show();
    			}
            }});
			return checkID;
	};
	function regist2(){
		if(checkRealName()&&checkIdNumber()){
			$('#pop_regist2').submit();
		}
	}
</script>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp"></jsp:include>
<!--头部结束-->
<!--正文-->
	<div class="regist-main">
		<div class="regist-main-con">
			<ul class="regist-step-list clearfix">
				<li><span>1</span>创建帐户</li>
				<li class="active"><span>2</span>实名认证</li>
				<li class="last"><span>3</span>注册成功</li>
			</ul>
			<form id="pop_regist2" name="pop_regist2" method="post" action="<%=basePath%>user/UserRegist2.do">
				<input type="hidden" name="random_form" value=<%=random_session%>></input>	
				<table class="regist-info">
					<tr><th width='86'>真实姓名</th><td width="279"><input type='text' class="input" id="realname" name="realName" onfocus="onFocus('realname_tips');" /></td><td width="150"><p id="realname_tips" class="tips hide"><i></i><span></span></p></td></tr>
					<tr><th>身份证号码</th><td><input type='text' class="input" id="idnumber" name="idNumber" onfocus="onFocus('idnumber_tips');" /></td><td><p id="idnumber_tips" class="tips hide"><i></i><span></span></p></td></tr>
					<tr><td colspan="3">${errorID}</td></tr>
					<tr><th></th><td><a onclick="regist2()" href="javascript:;" class="btn btn-orange" id="surebtn">下一步</a><a class="btn mar-l30 btn-gray" href="<%=basePath%>user/regist_three.jsp">跳过</a></td><td></td></tr>
				</table>
			</form>
		</div>
	</div>
<!--正文结束-->
	<jsp:include page="../common/footer.jsp"></jsp:include>
</div>
</body>
</html>
