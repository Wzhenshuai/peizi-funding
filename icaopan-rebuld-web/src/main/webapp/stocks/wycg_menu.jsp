<%@page import="com.icaopan.web.user.realm.LoginRealm"%>
<%@page import="com.icaopan.user.model.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user=LoginRealm.getCurrentUser();
	String type=user.getUserClassTypeVal();
	String user_type=(String) request.getSession().getAttribute("userType");
%>
<div class="aside-nav fl">
	<ul>
		<li>
			<a href="<%=basePath%>trade/StocksIndex.do">我要炒股</a>
		</li>
		<li>
			<a href="<%=basePath%>trade/BuyStocksInit.do">股票买入</a>
		</li>
		<li>
			<a href="<%=basePath%>trade/SellStocksInit.do">股票卖出</a>
		</li>
		<% 
			if("1".equals(type)||"senior".equals(user_type)){
		%>
			<li>
			<a href="<%=basePath%>trade/BuyStocksInitSenior.do">高级股票买入</a>
			</li>
			<li>
				<a href="<%=basePath%>trade/SellStocksInitSenior.do">高级股票卖出</a>
			</li>
		<% 		
			}
		%>
		
		<li>
			<a href="<%=basePath%>trade/CancelStocksInit.do">股票撤单</a>
		</li>
		<li>
			<a href="<%=basePath%>trade/QueryHoldStocksInit.do">我的持仓</a>
		</li>
		<li>
			<a href="<%=basePath%>trade/QueryJiaoGeDanInit.do">交割单</a>
		</li>
		<li>
			<a href="<%=basePath%>trade/QueryFillInit.do">成交记录</a>
		</li>
		<li>
			<a href="<%=basePath%>trade/QueryPlacementInit.do">委托记录</a>
		</li>
		<li>
			<a style="cursor:pointer" id='updatePW'>修改密码</a>
		</li>
		<%-- <li>
			<a href="<%=basePath%>trade/queryUpdownStocksInit.do">上日漲跌停列表</a>
		</li> --%>
	</ul>
</div>
 <div id="account_mod_div" style="display:none">
	<ul>
		<c:forEach items="${portList }" var ="p" varStatus="sta">
			<li portid="${p.portfolioId }" order="${sta.index }" class="actived">
				<c:choose>
					<c:when test="${p.type==0 }">
						固定利率账户
					</c:when>
					<c:otherwise>
						分成项目账户
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>
	</ul>
</div>
<script type="text/javascript" src="<%=basePath%>/js/util/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wycg_menu.js"></script>