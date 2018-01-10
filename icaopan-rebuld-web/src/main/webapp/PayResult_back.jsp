<%@page import="com.toft.gqd.user.bean.PrincipalTrade"%>
<%@ page import="com.toft.core3.util.Md5" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<base href="<%=basePath%>">
</head>

<body>
<!-- 请加上你们网站的框架。就是你们网站的头部top，左部left等。还有字体等你们都要做调整。 -->
<jsp:include page="common/header.jsp"></jsp:include>
<div id="main_container" style="width:800px;margin: 0 auto;min-height: 600px;border:1px solid #d3d3d3;border-radius:3px;background-color: white " >

 <%
 if (SignMD5info.equals(md5sign)){
 %>
 <!-- MD5验证成功 -->
	<table width="728" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td  align="right" valign="top" width="200">Your order number is：</td>
    <td  align="left" valign="top"><%= BillNo%></td>
  </tr>
    <tr>
    <td  align="right" valign="top">Amount：</td>
    <td  align="left" valign="top"><%= Amount%></td>
  </tr>
    <tr>
    <td  align="right" valign="top">Payment result：</td>
	<%if ( Succeed=="88" ) 
	{
	%><!-- 可修改订单状态为正在付款中 -->
	<!-- 提交支付信息成功，返回绿色的提示信息 -->
	<td  align="left" valign="top" style="color:green;"><%= Result%>
	&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>user/GetUserAccount.do" style="text-decoration: underline;color:#052a6d">查看账户余额</a>
	</td>
	<%
	}
	else
	{
	%><!-- 提交支付信息失败，返回红色的提示信息 -->
    <td  align="left" valign="top" style="color:red;"><%= Result%>&nbsp;&nbsp;&nbsp;&nbsp;<%=Succeed %></td>
	<%
	}%>
  </tr>
  
</table>
<%
}
else 
{
%>
 <!-- MD5验证失败 -->
<table width="728" border="0" cellspacing="0" cellpadding="0" align="center">
 <tr>
    <td align="center" valign="top" style="color:red;">Validation failed!</td>
	</tr>
	</table>
<%	
}
 %>
 </div>
<jsp:include page="common/footer.jsp"></jsp:include>
</body>
</html>

