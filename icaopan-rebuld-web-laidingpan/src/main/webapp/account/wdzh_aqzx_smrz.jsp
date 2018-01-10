<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<title>实名认证</title>
<link  href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/ajax_request.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/icp-common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/verifyIdNumber.js"></script>
<script type="text/javascript">
$(function(){
	$('#aside-nav li:eq(7)').addClass('active');
});

function saveID(){
	var realName = $("#realName").val();
	var idNumber = $("#idNumber").val();
	if ($.trim(realName) == "") {
		$("#errorDiv").html("姓名不能为空");
		return;
	}
	var regular = /^([^\`\+\~\!\#\$\%\^\&\*\(\)\|\}\{\=\"\'\！\￥\……\（\）\——]*[\+\~\!\#\$\%\^\&\*\(\)\|\}\{\=\"\'\`\！\?\:\<\>\•\“\”\；\‘\‘\〈\ 〉\￥\……\（\）\——\｛\｝\【\】\\\/\;\：\？\《\》\。\，\、\[\]\,]+.*)$/;
	if(regular.test(realName)){
    	$("#errorDiv").html("姓名不能含有特殊字符");
		return false;
    }
	var realnameValid=valid.isChinaName(realName);
	if(!realnameValid){
		$("#errorDiv").html("真实姓名要为汉字或英文字母");
		return false;
	}
	if ($.trim(idNumber) == "") {
		$("#errorDiv").html("身份证号不能为空");
		return;
	}
	if(!IdentityCodeValid(idNumber)){
		$("#errorDiv").html("身份证号输入不合法");
		return;
	}
	if (!IdCardValidate(idNumber)) {
		$("#errorDiv").html("身份证号输入不合法");
		return;
	}
	//判断身份证号是否已经存在
	var checkIdNumber=false;
	ajaxReuqestAsync(systemConfig.path+"/user/CheckIdNumber.ajax", {"idNumber":idNumber}, function(r){
		if(r.resCode=="login"){
			location.href = systemConfig.path+ "/user/login.jsp";
		}else if(r.resCode=="empty"){
			checkIdNumber=false;
			$("#errorDiv").html("身份证号不能为空");
			return;
		}else if(r.resCode=="id_set_exit"){
			checkIdNumber=false;
			$("#errorDiv").html("身份证号已设置，不能修改");
			return;
		}else if(r.resCode=="id_exit"){
			checkIdNumber=false;
			$("#errorDiv").html("身份证号已存在，请重新输入");
			return;	
		}else if(r.resCode=="success"){
			checkIdNumber=true;
		}
		if(checkIdNumber){
			ajaxReuqestSynchronize(systemConfig.path+"/user/ChangeIdNumber.ajax", {"realName":realName,"idNumber":idNumber}, function(r){
				if(r.resCode=="login"){
					location.href = systemConfig.path+ "/user/login.jsp";
				}else if(r.resCode=="success"){
					ShowDialogSuccess("恭喜你，认证成功", function(){location.href=systemConfig.path+"/user/ViewUserSecurityInfo.do";});
				}else if(r.errorID!=null){
					$("#errorDiv").html(r.errorID);
					return;	
				}
			});
		}
	});
}
</script>
</head>
<body>
<div id="container">
<jsp:include page="../common/header.jsp" />
<!--主要内容-->
<div id="main">
	<div class="wrap clearfix">
		<jsp:include page="wdzh_menu.jsp" />
		<div class="account-cont main-cont fr">
			<h2 class="ac-title"><span>实名认证</span></h2>
          	<table class="tabcontent2-table">
	          <tr>
			    <td colspan="2">*实盘交易与提现都需要实名认证，请填写您真实的身份信息</td>
			  </tr>
			  <tr>
			    <td width="110">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
			    <td><input name="" class="input" id="realName" type="text" /></td>
			  </tr>
			  <tr>
			    <td>身份证号码：</td>
			    <td><input name="" class="input" id="idNumber" type="text" /></td>
			  </tr>
			  <tr>
			    <td></td>
			    <td><div class="error text-orange" id="errorDiv"></div></td>
			  </tr>
			  <tr>
			    <td></td>
			    <td><span class="inp-btn btn btn-blue" onclick="saveID()">确认</span></td>
			  </tr>
			</table>
		</div>
	</div>
</div>
<!--主要内容结束-->
<jsp:include page="../common/footer.jsp" />
</div>
</body>
</html>
