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
<link  href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<script type="text/javascript" src="${ctx }/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx }/js/function/valide.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>js/function/common.js"></script>
<script type="text/javascript" src="${ctx }/js/function/verifyIdNumber.js" charset="utf-8"></script>  
<script type="text/javascript" src="<%=basePath%>/js/util/ajax_request.js"></script>
<script type="text/javascript">
	$(function(){
		$("#realname").blur(checkRealName);
		$("#idnumber").blur(checkIdNumber);
		$("#sourcecode").blur(checkSource);
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
	    	$("#realname_tips").html("真实姓名不能含有特殊字符");
			return false;
	    }
		if(realname=null||realname==""||realname=="请输入真实姓名"){
			$("#realname_tips").html("真实姓名不能为空");
			return false;
		}
		if(!realnameValid){
			$("#realname_tips").html("真实姓名要为汉字");
			return false;
		}
		$("#realname_tips").html("");
		return true;
	}; 
	//验证身份证号
	var checkIdNumber = function(){
		var idnumber = $("#idnumber").val();
		if ($.trim(idnumber) == ""||$.trim(idnumber)=="请输入身份证号") {
			$("#idnumber_tips").html("身份证号不能为空");
			return;
		}
		if(!IdentityCodeValid(idnumber)){
			$("#idnumber_tips").html("身份证号输入不合法");
			return;
		}
		if (!IdCardValidate(idnumber)) {
			$("#idnumber_tips").html("身份证号输入不合法");
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
    				$("#idnumber_tips").html("");
    			} else if(!res.checkIdNumber){
    				$("#idnumber_tips").html("身份证号已经存在");
    				checkID= false;
    			}
            }});
			return checkID;
	};
	//验证推荐码(选填)
	var checkSource=function(){
		var sourcecode = $.trim($("#sourcecode").val());
		$.post(systemConfig.path+"/user/checkCustomerCode.ajax", {
			"sourceCode" : sourcecode
		}, function(res) {
			if (res.resCode=="yes") {
				$("#sourcecode_tips").html(""); 
				return true;
			} else if(res.resCode=="no"){
				$("#sourcecode_tips").html("推荐码不正确");
				return false;
			}
		}, "json");
	};
	function regist2(ele){
		if(checkRealName()&&checkIdNumber()){
			$('#pop_regist2').submit();
		}
	}
</script>
</head>
<body>
<!--头部-->
<jsp:include page="/user/user_head.jsp" />
<!--头部结束-->
<!--正文-->
<div class="login-main">
  <div class="login-main-con">
  <img src="images/login_two.png" />
  <table width="503" border="0" cellspacing="0" class="login-table">
  <form id="pop_regist2" name="pop_regist2" method="post" action="<%=basePath%>user/UserRegist2.do">
  <input type="hidden" name="random_form" value=<%=random_session%>></input>
    <tr>
      <th width="79" scope="row"><span>真实姓名</span></th>
      <td rowspan="2"><input id="realname" name="realName" type="text" class="color-h" value="请输入真实姓名" onclick="javascript:empty('realname','realname');" onfocus="javascript:onFocus('realname_tips');"/><div class="error" id="realname_tips">${errorName}</div></td>
      </tr>
    <tr>
      <th scope="row">&nbsp;</th>
    </tr>
    <tr>
      <th scope="row"><span>身份证号码</span></th>
      <td rowspan="2"><input type="text" id="idnumber" name="idNumber" class="color-h" value="请输入身份证号" onclick="javascript:empty('idnumber','idnumber');" onfocus="javascript:onFocus('idnumber_tips');"/><div class="error" id="idnumber_tips">${errorID}${errorInfo}</div></td>
      </tr>
    <tr>
      <th scope="row">&nbsp;</th>
    </tr>
   <%--  <tr>
      <th scope="row"><span>邀请码</span></th>
      <td rowspan="2"><input type="text" name="sourceCode" id="sourcecode" /><!-- <span>提示：12123</span> --><div class="error" id="sourcecode_tips"></div></td>
      </tr> --%>
    <tr>
      <th scope="row">&nbsp;</th>
    </tr>
    <tr>
      <td colspan="2" scope="row"><a onclick="javascript:regist2(this);" id="surebtn"><img src="images/botton_next.gif"  class="btn-l-90"/></a><a href="<%=basePath%>user/regist_three.jsp"><img src="images/login_junp.gif" class="mar-l-10" /></a></td>
    </tr>
    </form>
  </table>
  </div>
</div>
<!--正文结束-->
<!--尾部-->
<jsp:include page="/user/user_foot.jsp" />
<!--尾部结束-->
</body>
</html>
