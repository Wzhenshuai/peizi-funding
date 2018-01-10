<%@ page import="com.toft.gqd.user.bean.PrincipalTrade"%>
<%@ page import="com.common.util.DateUtils"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.toft.core3.util.Md5"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String MD5key; //MD5key值
	MD5key = PrincipalTrade.HUICHAO_PAY_KEY;
	String MerNo; //商户ID
	MerNo = "26565";
	String BillNo; //订单编号
    BillNo = (String)request.getAttribute("tradeNo");
	String Amount; //支付金额
	Amount=(String)request.getAttribute("tradeAmount");
	String ReturnURL; //返回地址
	ReturnURL = basePath+"PayResult.jsp";
	//[必填]返回数据给商户的地址(商户自己填写):::注意请在测试前将该地址告诉我方人员;否则测试通不过
	//    ReturnURL = "http://192.168.1.108/ecpss/payresult.jsp";
	String tradeAdd; //返回地址

	String md5src; //加密字符串    
	md5src = MerNo + "&" + BillNo + "&" + Amount + "&" + ReturnURL
			+ "&" + MD5key;
	String SignInfo; //MD5加密后的字符串
	SignInfo = Md5.md5(md5src);//MD5检验结果
	String AdviceURL =basePath+ "user/AdvicePayResultFromHuiChao.do"; //[必填]支付完成后，后台接收支付结果，可用来更新数据库值
	//送货信息(方便维护，请尽量收集！如果没有以下信息提供，请传空值:'')
	//因为关系到风险问题和以后商户升级的需要，如果有相应或相似的内容的一定要收集，实在没有的才赋空值,谢谢。

	String defaultBankNumber = ""; //[选填]银行代码
	String orderTime = DateUtils.parseDate(new Date(), "yyyyMMddHHmmss"); //[必填]交易时间：YYYYMMDDHHMMSS
	//账单地址选择传递
    String Remark="账户充值";
	String products = Remark;// '------------------物品信息
%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>支付</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
</head>
<body>
	<form action="https://pay.ecpss.com/sslpayment" method="post" target="_self"
		name="E_FORM" id="E_FORM">
				<input type="hidden" name="MerNo" value="<%=MerNo %>">
				<input type="hidden" name="BillNo" value="<%=BillNo %>">
				<input type="hidden" name="Amount" value="<%=Amount %>">
				<input type="hidden" name="ReturnURL" value="<%=ReturnURL %>">
				<input type="hidden" name="AdviceURL" value="<%=AdviceURL %>">
				<input type="hidden" name="SignInfo" value="<%=SignInfo %>">
				<input type="hidden" name="Remark" value="<%=Remark %>">
				<input type="hidden" name="defaultBankNumber"
					value="<%=defaultBankNumber%>">
				<input type="hidden" name="orderTime"
					value="<%=orderTime%>">
				<input type="hidden" name="products" value="<%=products%>">
	</form>
	<script>
document.getElementById("E_FORM").submit();
</script>
</body>
</html>
