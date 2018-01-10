<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<base href="<%=basePath%>"/>
<title>我的账户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link  href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>

</head>
<body>
<div id="container">
<jsp:include page="../common/header.jsp" />
<!--主要内容-->
<div id="main">
	<div class="wrap clearfix">
		<jsp:include page="wdzh_menu.jsp" />
		<div class="account-cont main-cont fr">
			<h2 class="ac-title">我要提现</h2>
			<div>
				<input type="hidden" value="${leftAmount }" id="leftmoney" name="leftmoney"/>
				<p class="orange-tit">现金账户余额：<strong id="availableAmountDd">--</strong>元</p>
				<div class="tab_plugin MT12">
					<ul class="tab_plugin_title recharge-trigger clearfix">
						<li class="active">提现</li>
						<li id="card_manage">银行卡管理</li>
					</ul>
					<div class="recharge-wrap">
						<div class="tab_plugin_list">
							<div class="recharge-content">
								<table class="wytx-tx">
									<tr><td>提现金额：</td><td><input type="text" class="recharge-input" id="amount" />元<span class="text-orange mar-l30 error" id="errorDiv1"></span></td></tr>
									<tr><td>提现手续费：</td><td><span id="wytx-sxf">0</span>元</td></tr>
									<tr><td>提现银行卡：</td><td>
										<select id="wytx-yhk">
											<c:choose>
												<c:when test="${empty cards}">
													<option value="-1" selected="selected">请选择</option>
								                </c:when>
												<c:otherwise>
													<c:forEach items="${cards }" var="card">
									         	       	<c:choose>
															<c:when test="${card.isdefault==0}">
																<option selected="selected" value="${card.id }">${card.bankname }&nbsp;&nbsp;${card.cardnoHidePart }</option>
									                     	</c:when>
															<c:otherwise>
									         	   				<option value="${card.id }">
									         	   					${card.bankname }&nbsp;&nbsp;${card.cardnoHidePart}
									         	   				</option>
									                     	</c:otherwise>
														</c:choose>
									        		</c:forEach>
									            </c:otherwise>
											</c:choose>
										</select><span class="text-orange mar-l30 error" id="errorDiv2"></span>
									</td></tr>
									<tr><td></td><td class="text-orange" id="bindCard"></td></tr>
									<tr><td></td><td class="bnt-new-width"><a href="javascript:;" onclick="MA.userWithDraw()" class="btn btn-orange">提现</a></td></tr>
								</table>
							</div>
						</div>
						<div class="tab_plugin_list hide">
							<div class="recharge-content">
								<p class="add-card-wrap">已绑定银行卡<span>${bankCardNum}</span>张<a href="javascript:;" onclick="MA.addBankcard()" class="mar-l30 btn btn-orange">添加</a></p>
								<table class="yhk-list">
									<thead>
									<tr><th width="90">姓名</th><th width="113">开户行</th><th width="113">开户省/市（区）</th><th width="150">支行信息</th><th width="160">卡号</th><th></th></tr>
									</thead>
									<tbody>
									<c:choose>
										<c:when test="${!empty cards }">
											<c:forEach items="${cards }" var="card">
												<tr>
													<td>${card.accountname }</td>
													<td>${card.bankname }</td>
													<td>${card.provice }<p>${card.city }</p></td>
													<td>${card.branchname }</td>
													<td>${card.cardnoHidePart }</td>
													<td colspan="2"><a class="btn4 btn4-red" href="javascript:;" onclick="MA.deleteBankCard(${card.id });">删除</a></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td colspan="6" class="txt-c">暂无银行卡</td></tr>
										</c:otherwise>
									</c:choose>
									</tbody>
							</table>
							<div class="addNewCard">
								<p>请务必填写本人的银行卡号，否则无法成功提现</p>
								<table>
									<tr><th width="80" class="txt-l">实名：</th><td class="ft18 text-orange">${realName}</td></tr>
									<tr><th class="txt-l">开户行：</th><td><select name="bankName">
					                  	   <option value="-1" selected="selected">请选择</option>
							           	   <option value="中国银行">中国银行</option>
							           	   <option value="工商银行">工商银行</option>
							           	   <option value="农业银行">农业银行</option>
							           	   <option value="交通银行">交通银行</option>
							           	   <option value="广东发展银行">广东发展银行</option>
							           	   <option value="深圳发展银行">深圳发展银行</option>
							           	   <option value="建设银行 ">建设银行 </option>
							           	   <option value="上海浦东发展银行">上海浦东发展银行</option>
							           	   <option value="浙江泰隆商业银行">浙江泰隆商业银行</option>
							           	   <option value="招商银行">招商银行</option>
							           	   <option value="中国民生银行">中国民生银行</option>
							           	   <option value="兴业银行">兴业银行</option>
							           	   <option value="广东发展银行">广东发展银行</option>
							           	   <option value="深圳发展银行">深圳发展银行</option>
							           	   <option value="中信银行">中信银行</option>
							           	   <option value="华夏银行">华夏银行</option>
							           	   <option value="中国光大银行">中国光大银行</option>
							           	   <option value="北京银行">北京银行</option>
							           	   <option value="上海银行">上海银行</option>
							           	   <option value="天津银行">天津银行</option>
							           	   <option value="大连银行">大连银行</option>
							           	   <option value="杭州银行">杭州银行</option>
							           	   <option value="宁波银行">宁波银行</option>
							           	   <option value="厦门银行">厦门银行</option>
							           	   <option value="广州银行">广州银行</option>
							           	   <option value="平安银行">平安银行</option>
							           	   <option value="浙商银行">浙商银行</option>
							           	   <option value="上海农村商业银行">上海农村商业银行</option>
							           	   <option value="重庆银行">重庆银行</option>
							           	   <option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
							           	   <option value="江苏银行">江苏银行</option>
							           	   <option value="北京农村商业银行">北京农村商业银行</option>
							           	   <option value="济宁银行">济宁银行</option>
							           	   <option value="台州银行">台州银行</option>
                  						</select>
                  					</td></tr>
									<tr><th class="txt-l">开户城市：</th><td><select name="provice" id="pro"></select><select name="provice" id="city"></select></td></tr>
									<tr><th class="txt-l">支行：</th><td><input type="text" id="branchName" /></td></tr>
									<tr><th class="txt-l">卡号：</th><td><input type="text" id="cardNo" /></td></tr>
									<tr><th></th><td><div class="error text-orange" id="errorDiv4"></div></td></tr>
									<tr><th></th><td><a href="javascript:;" class="btn btn-orange" onclick="MA.doBindCard()">提交</a></td></tr>
								</table>
							</div>
							</div>
						</div>
					</div>
				</div>
				<h2 class="ac-title ac-title-noBg">温馨提示</h2>
				<ul class="ac-wxts">
					<li>1. 提现单笔1000（含）元以上免手续费，单笔1000元以下1元一笔</li>
					<li>2. 现金账户余额不足100元时，需一次全部提取</li>
					<li>3. 请在现金账户预留少量资金，用于支付每月的系统使用费</li>
					<li>4. 充值后未使用的资金，1个月内最多可提现充值金额的10%</li>
					<li>5. 禁止洗钱、信用卡套现、虚假交易等行为、一经发现并确认，将终止该账户的使用</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!--主要内容结束-->
<jsp:include page="../common/footer.jsp" />
</div>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery.cityselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-valide.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/icp-dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/function/icp-plugin.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wdzh-tx.js"></script>
</body>
</html>
