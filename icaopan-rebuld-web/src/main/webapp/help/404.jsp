<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>i操盘</title>
<style>
body{ font-family:"微软雅黑"; background:#FFFFFF;}
*{  color: #333;  margin:0px; padding:0px;}
.div-404{ width:760px; margin:0px auto; height:540px; overflow:hidden; position:absolute; top:50%; margin-top:-270px; left:50%; margin-left:-380px;}
a:link {color: #333; text-decoration:none;}
a:visited {color: #333;}
a:hover{color: #333; text-decoration:none; cursor: pointer;} 
a:active {color: #333;}
</style>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
</head>

<body>
<div class="div-404">
	<div style="width:760px; height:370px; overflow:hidden;">
	<div style=" width:440px; float:left; margin-top:100px;">
	<h2 style=" font-size:100px; font-weight: normal; color:#fd9d07; text-align:center; margin:0px; padding:0px;">404</h2>
    <p style=" color:#403f3f; line-height:30px; font-size:18px;">市场就像上帝一样，帮助那些自己帮助自己的人，但与上帝不一样的地方是，他不会原谅那些不知道自己在做什么的人</p>
    </div>
    <div style=" width:303px; float:right;"><img src="images/404_03.gif"></div>
	</div>
    
    <div style="clear:both; line-height:80px; text-align:center; font-size:24px; font-family:宋体; color:#333333; margin-top:30px;">其实我也不知道这是在说什么，反正页面不见了~</div>
    <div style="clear:both; line-height:30px; text-align:center;">客服电话：<span style="color:#fd9d07;">4001-667-067 </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="#">返回首页</a></div>
</div>
</body></html>