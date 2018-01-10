<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.toft.gqd.user.bean.PrincipalTrade"%>
<%@ page import="com.toft.core3.util.Md5" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
    //字符编码
    String CharacterEncoding = "UTF-8";
    request.setCharacterEncoding(CharacterEncoding);
    String BillNo = request.getParameter("BillNo");
    String Amount = request.getParameter("Amount");
    String tradeOrder = request.getParameter("tradeOrder");
    String Succeed = request.getParameter("Succeed");
    String Result = request.getParameter("Result");
    String SignMD5info = request.getParameter("SignMD5info");
    String MD5key = PrincipalTrade.HUICHAO_PAY_KEY;
    String md5src = BillNo+"&"+Amount+"&"+Succeed+"&"+MD5key;
    String md5sign; //MD5加密后的字符串
    md5sign = Md5.md5(md5src);//MD5检验结果
%>
<html>
<head>
<title>i操盘</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icaopan.css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<base href="<%=basePath%>">
</head>
<body>
<!-- 请加上你们网站的框架。就是你们网站的头部top，左部left等。还有字体等你们都要做调整。 -->
<jsp:include page="common/header.jsp"></jsp:include>
<div class="wrap">
	<div class="success" style="">
     	<h3>
         	<img alt="Binggo" src="images/i-binggo.png"><span>
         	   <%if ("88".equals(Succeed)) 
				  {
					%>
						支付成功
					<%
				 }else{
					 %>
						支付失败
					<%
				}
         	    %>
         	</span>
         </h3>
         
     </div>
</div>
<jsp:include page="common/footer.jsp"></jsp:include>
</body>
</html>

    