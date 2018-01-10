<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="java.security.SecureRandom"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<script>
location.href="<%=basePath%>trade/StocksIndex.do";
</script>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>—只做为您股票投资助力的产品</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icaopan.css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery.jslides.js"></script>

<style>
#full-screen-slider { width:100%; height:380px; position:relative}
#slides { display:block; width:100%; height:380px; list-style:none; padding:0; margin:0; position:relative}
#slides li { display:block; width:100%; height:100%; list-style:none; padding:0; margin:0; position:absolute}
#pagination {position:absolute;bottom:13px;left:50%;margin-left:-26px;background-color:rgba(200,200,200,0.5); overflow:hidden; z-index:910;width:42px;height:8px;padding:7px 0 7px 11px; border-radius:10px;}
#pagination li {width:8px;height:8px; float:left;overflow:hidden; margin-right:4px; cursor:pointer;}
#pagination li a {background-color:#ccc; display:block;width:100%;height:100%;border-radius:50%; text-align:center; color:#ccc;}
#pagination li.current a{ background-color:#8c8c8c; color:#8c8c8c;}
</style>
</head>
</html>
