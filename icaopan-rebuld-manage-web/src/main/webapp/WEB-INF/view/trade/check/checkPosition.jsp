<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="default"/>

</head>
<body>

<div class="portlet box red">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-coffee"></i>头寸对账
        </div>
    </div>
    <div class="portlet-body">
    	<div class="row">
			<div class="col-md-2" style="float:right;">
				<button  class="btn btn-primary" onclick="downloadCheckPlacmentSummary()"><i class="glyphicon glyphicon-save"></i>通道股票委托未对接汇总</button>
			</div>
		</div>
        <table class="table table-bordered table-striped">
            <tr>
                <th>ID</th>
                <th>更新时间</th>
                <th>操作类型</th>
                <th></th>
            </tr>
            <c:forEach items="${checkLogLists}" var="checkLog">
                <tr>
                    <td>${checkLog.id}</td>
                    <td>${checkLog.updateTimeStr}</td>
                    <td>${checkLog.operationName}</td>
                    <td>
                        <c:forEach items="${checkLog.results}" var="result">
                            <p>${result}</p>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script type="text/javascript">
function downloadCheckPlacmentSummary(){
	location.href="${ctx}/ems/downloadCheckPlacmentSummary";
}

</script>
</body>
</html>

