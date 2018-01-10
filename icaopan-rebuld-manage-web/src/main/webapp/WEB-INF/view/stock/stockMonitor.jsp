<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>股票行情监控</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<style>
		tr{
			color:black;
		}
		tr.red{
			color:red;
		}
	</style>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>股票行情监控
		</div>
	</div>
	<div class="portlet-body">
		<table class="table table-bordered table-striped" id="myTable">
			<tr><th>监控服务器</th><th>股票代码</th><th>股票名称</th><th>行情更新时间</th><th>是否超时</th></tr>
			<c:forEach items="${dataList }" var="data">
				<tr class='<c:if test="${data.ok==false }">red</c:if>'><td>${data.serverName }</td><td>${data.stockCode }</td><td>${data.stockName }</td><td>${data.updateTime }</td><td><c:if test="${data.ok==false }">行情超时</c:if></td></tr>
			</c:forEach>
		</table>
	</div>
</div>
<script>
function sounds(){
	var sound = new Howl({
		  src: ['${ctxStatic}/sounds/ALARM8.wav'],
		  loop: false,
		  onend: function() {
			    console.log('Finished!');
			  }
	});
    sound.play();
}
$(function(){
	var trs=$("tr.red");
	if(trs&&trs.length>0){
		sounds();
	}
	setTimeout(function(){
		location.reload();
	}, 10000);
});
</script>

</body>




