<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title><sitemesh:title/></title>
	<%@include file="/STATICS/common/header.jsp" %>		
	<sitemesh:head/>
	<script type="text/javascript">
		function ajaxRequestPost(url,postData,succFunc){
			$.ajax({
				"type" : 'post',
				"url" : url,
				"dataType" : "json",
				"data" : postData,
				"success" : function(result) {
					succFunc(result);
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
	</script>
</head>
<body style="background-color: white">
	<sitemesh:body/>
</body>
</html>