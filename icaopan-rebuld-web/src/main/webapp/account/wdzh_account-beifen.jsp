<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/ajax_request.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/function/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/dialog.js"></script>
<script type="text/javascript">
	$(function(){
		viewMyAccount();
	});
</script>
</head>
<body>
	<div class="gpzh">
      	<h2 class="bt-h2"><span>现金账户</span></h2>
          <div class="gpzh-con">
          	<ul class="gpzh-ul">
              	<li><p>总资产</p><p class="a-28 col-red"><font id="totalAmountDd"></font></p></li>
                  <li>
                    <p>可用余额</p><p class="a-28 color-yellow"><font id="availableAmountDd"></font></p></li>
                  <li><p>冻结金额</p><p class="a-28"><font id="frzoneAmountDd"></font></p></li>
              </ul>
          </div>
      </div>
  	<div class="gpzh">
      	<h2 class="bt-h2"><span>股票账户</span></h2>
          <div class="gpzh-con">
          	<ul class="gpzh-ul">
              	<li class="li-180"><p>总资产</p><p class="a-28"><font id="sumAmountDd"></font></p></li>
                  <li class="li-260" style="width: 240px">
                  <p class="t-left"><img src="images/wycg_20.png" /><span>股票市值：<font id="marketValueDd"></font></span></p>
                  <p class="t-left"><img src="images/wycg_07.png" /><span>可用余额：<font  class="col-red" id="stockBalanceDd"></font></span></p>
                  </li>
                   <li class="li-200" style="width:220px">
                   <span style="height:50px;line-height:50px; display:block;text-align: left">亏损警告线<div class="tipWrapper"><img src="images/wycg_13.png"
                    onmouseover="javascript:showTips('当股票账户总资产触及或低于亏损警告线时，您将只能卖出股票，不能买入股票。',this)" onmouseout="closeTips()"/></div>：<font id="alertLineDd"></font></span>
                   </li>
                   <li class="li-200" style="width:220px">
                   <span style="height:50px;line-height:50px; display:block;text-align: left">亏损平仓线<div class="tipWrapper"><img src="images/wycg_13.png" onmouseover="javascript:showTips('当股票账户总资产触及或低于亏损平仓线时，您将不能买入或卖出股票，平台会对操盘账户内的证券采取强制平仓的操作。',this)" onmouseout="closeTips()"/></div>：<font id="wrongLineDd"></font></span>
                   </li>
              </ul>
          </div>
     </div>
</body>