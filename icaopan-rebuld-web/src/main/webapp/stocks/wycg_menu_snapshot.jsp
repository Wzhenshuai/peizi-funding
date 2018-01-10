<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="aside-nav fl">
	<ul>
		<li class="active">
			<a href="javascript:;">我要炒股</a>
		</li>
		<li>
			<a href="javascript:;">股票买入</a>
		</li>
		<li>
			<a href="javascript:;">股票卖出</a>
		</li>
		<li>
			<a href="javascript:;">高级股票买入</a>
		</li>
		<li>
			<a href="javascript:;">高级股票卖出</a>
		</li>
		
		<li>
			<a href="javascript:;">股票撤单</a>
		</li>
		<li>
			<a href="javascript:;">我的持仓</a>
		</li>
		<li>
			<a href="javascript:;">交割单</a>
		</li>
		<li>
			<a href="javascript:;">成交记录</a>
		</li>
		<li>
			<a href="javascript:;">委托记录</a>
		</li>
		<li>
			<a style="cursor:pointer" id='updatePW'>修改密码</a>
		</li>
	</ul>
</div>
