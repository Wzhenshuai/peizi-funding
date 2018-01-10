<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>i操盘--公告</title>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
<link rel="stylesheet" href="css/icp-news.css" type="text/css" />
<!--[if lt IE 9]><script src="/js/html5.js"></script><![endif]-->
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script>
function formatDate(date){
	if(date.length==8){
		date=date.substr(0,4)+'-'+date.substr(4,2)+'-'+date.substr(6,2);
	}
	return date;
};

$(function(){
	$('.notice-list li').live('click', function(){
		if($(this).hasClass('active')){
			$(this).removeClass('active');
		}else{
			$(this).siblings().removeClass('active').end().addClass('active');
		}
	});
	//公告列表和当前公告
	$.ajax({
		url:systemConfig.path +'/common/ViewSysNewsPublishDetail.ajax', 
		data:{'categoryId':2},
		type:'get',
		success:function(e){
			var pageObj = e.page;
			var len = pageObj.dataList.length;
			if(pageObj && len){
				var html='', flag=0;
				for(var i=0; i<len && i<10; i++){
					var obj=pageObj.dataList[i];
					if(location.href.split('id=')[1]==obj.id){
						flag=i;
					}
					/* if(i==0){
						$('#title').html(obj.title);
						$('#content').html(obj.content);
						$('.news-content .time').html(obj.publishDate);
					}else{ */
						html+='<li><div class="clearfix"><a class="article-title fl" href="javascript:;">'+obj.title+'</a><span class="fr">'+formatDate(obj.publishDate)+'</span></div><p>'+obj.content+'</p></li>';
					/* } */
				}
				$('#notice-list').html(html);
				$('#notice-list li:eq('+flag+')').addClass('active');
			}
		}
	});
	
	//新闻
	$.ajax({
		url:systemConfig.path +'/common/ViewSysNews.ajax?categoryId=1', 
		type:'post',
		success:function(e){
			var pageObj = e.page;
			var len = pageObj.dataList.length;
			if(pageObj && len){
				var html='';
				for(var i=0;i<len;i++){
					var obj=pageObj.dataList[i];
					html+='<li><a href="'+systemConfig.path +'/news/news.jsp?categoryId=1&id='+obj.id+'" title="'+obj.title+'">'+obj.title+'</a></li>';
				}
			}
			$('#news-list').html(html);
		}
	});
});
</script>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp" />
	<!--主要内容-->
	<div id="main">
       	<div class="wrap clearfix">
       		<div class="main-cont fl">
       			<p class="notice-title"><span>公告</span></p>
<!-- 	       		<article class="news-content MT40">
	       			<h2 id="title"></h2>
	       			<div id="content"></div>
	       			<div class="clearfix">
	       				<span class="time fr"></span>
	       			</div>
	       		</article> -->
	       		<ul class="notice-list" id="notice-list"></ul>
       		</div>
       		<aside class="side-cont fr">
       			<h3>财经资讯</h3>
       			<ul class="news-list" id="news-list"></ul>
       		</aside>
       	</div>
    </div>
	<!--主要内容结束-->
	<jsp:include page="../common/footer.jsp" />
</div>
</body>
</html>
