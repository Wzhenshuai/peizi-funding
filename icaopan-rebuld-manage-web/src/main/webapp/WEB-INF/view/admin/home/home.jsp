<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html> 
<head>
	<meta charset="utf-8" />
	<title>行情精灵后台 | 主页</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<jsp:include page="../../../../STATICS/common/header.jsp"></jsp:include>
</head>
<body class="page-header-fixed ">
	
	<!-- HEADER 开始 -->
	<div class="page-header navbar navbar-fixed-top">
		
		<!-- HEADER INNER 开始 -->
		<div class="page-header-inner">
			
			<!-- LOGO 开始 -->
			<div class="page-logo">
				<span class="glyphicon glyphicon-expand"></span>
				${showUserText }-后台管理系统
			</div>
			<!-- LOGO 结束 -->
			
			<!-- 开始响应式菜单的切换按钮 -->
			<div class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
			</div>
			<!-- 设置菜单切换按钮结束 -->
			
			<!-- 开始设置顶部菜单 -->
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown dropdown-user">
						<a href="#" class="dropdown-toggle" style="color:red" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">  
							<i class="glyphicon glyphicon-user"></i> &nbsp; <shiro:principal type="java.lang.String"/>  <i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${pageContext.request.contextPath}/user/forwardToRestPWD" target="myFrame">
									<i class="fa fa-key"></i> &nbsp;修改密码</a>
							</li>
							<li><a href="${pageContext.request.contextPath}/user/logout"> 
								<i class="glyphicon glyphicon-log-out"></i> &nbsp;退出系统</a>
							</li>
						</ul></li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
			</div>
			<!-- 下拉菜单设置完成 -->
		</div>
		<!-- HEADER INNER 设置完成 -->
	</div>  
	<!--  HEADER 设置完成 -->
	<div class="clearfix"></div>
	<!-- CONTAINER 开始 -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse">
				<!-- 开始设置左侧菜单 -->
				<ul class="page-sidebar-menu" data-auto-scroll="false" data-auto-speed="200">
					<li class="sidebar-toggler-wrapper">
						<div class="sidebar-toggler"></div> 
					</li>
					<li class="start ">
						<a href="${pageContext.request.contextPath}/user/home"> 
							<i class="fa fa-home"></i> <span class="title"> 控制台 </span>
						</a>
					</li>
					<c:forEach items="${adminMenus}" var="group">
						<li>
							<a href="javascript:;"> 
								<i class="${group.parentMenu.menuStyle}"></i> <span class="title"> ${group.parentMenu.menuName} </span> <span class="arrow "> </span>
							</a>
							<ul class="sub-menu">
								<c:forEach items="${group.subMenu}" var="adminMenu">
									<li>
										<a href="${pageContext.request.contextPath}/${adminMenu.menuUrl}" target="myFrame"> 
										<i class="${adminMenu.menuStyle}"></i> ${adminMenu.menuName} </a>
									</li>
								</c:forEach>
							</ul>
						 </li>		
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
			<div class="page-content-wrapper" >
				<div class="page-content" id="content-bar">
					<iframe  id="myFrame" name="myFrame"  width="100%" height="1000" style="border:0;background-color: white" allowTransparency="true" noresize="noresize"></iframe>
				</div>
			</div>
		<!-- END CONTENT -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<jsp:include page="../../../../STATICS/common/footer.jsp"></jsp:include>
	<script>
		function ajaxForChangePage(sSource){
			/* $.ajax({
				"type" : 'get',
				"url" : sSource,
				"dataType" : "html",
				"success" : function(resp) {
					$("#content-bar").html(resp);
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			}); */
			$("#myFrame").attr("src",sSource);
		}
		jQuery(document).ready(function() {
			// initiate layout and plugins
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout
		});
	</script>
</body>
<!-- END BODY -->
</html>