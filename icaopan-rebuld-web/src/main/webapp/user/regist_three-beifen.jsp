<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
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
<title>i操盘</title>
<link  href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
<jsp:include page="/user/user_head.jsp" />
<div class="login-main">
  <div class="login-main-con">
  <img src="images/login_thr.png" />
  <table width="503" border="0" cellspacing="0" class="login-table-2">
    <tr>
      <th width="79" scope="row">
         <img src="images/login_suc_03.png">      </th>
      <td><span class="a-24">恭喜您已经成功注册</span></td>
      </tr>
    <tr>
      <th scope="row">&nbsp;</th>
      <td><a href="<%=basePath%>user/viewMyIndex.do"><img src="images/login_suc_07i.png"></img></a></td>
      </tr>
    <tr>
      <th scope="row">&nbsp;</th>
       <%--<td><div style=" height:1px; margin:30px 0px; border-bottom:#CCCCCC solid 1px;"></div></td>
      </tr>
    <tr>
      <th scope="row"><span class="pos-12-20"><img src="images/login_suc_11.png"></span></th>
      <td>
      	<p class="a-18">你可能还希望</p>
      	<p class="a-14"><span>炒股资金存到i操盘，立享</span><img src="images/login_suc_14.png" class="mar-r-3 mar-l-3" ><span>收益</span></p></td>
      </tr>
    <tr>
      <td colspan="2" scope="row"><a href="<%=basePath%>user/UserChargeInit.do"><img src="images/login_suc_17.png" class="btn-l-90"/></a></td>
    </tr>  --%>
  </table>
  </div>
</div>
<jsp:include page="/user/user_foot.jsp" />
</body>
</html>
