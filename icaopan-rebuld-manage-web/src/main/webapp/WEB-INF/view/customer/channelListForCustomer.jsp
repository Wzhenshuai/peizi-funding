<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<html>
<head>
<meta name="decorator" content="default"/>
	<script>
        function columnDefs(row){
            channelId=row.id;
            channelName=row.name;
            return "<a href=\"${ctx}/channel/findChannelLimitByChannelId?channelId=" + channelId + "&channelName="+channelName+"\"><button class='btn btn-info'>单票详情</button></a>"
        }
	</script>
</head>
<body>
	<div class="portlet box red">
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-coffee"></i>通道信息
	</div>
</div>
<div class="portlet-body">
	<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
					  <span class="input-group-addon">名称</span>
					  <input type="text" class="form-control" name="name" placeholder="名称">
					</div>
				</div>
				<%--<div class="col-md-2">--%>
					<%--<div class="input-group">--%>
					  <%--<span class="input-group-addon">状态</span>--%>
					  <%--<select class="form-control" name="isAvailable">--%>
					    <%--<option value="">请选择</option>--%>
					  	<%--${fns:getDicOptions("available_type")}--%>
					  <%--</select>--%>
					<%--</div>--%>
				<%--</div>--%>
				<input type="hidden" name="isAvailable" value="true">
				<div class="col-md-2">
					<div class="input-group">
					  <span class="input-group-addon">类型</span>
					  <select class="form-control" name="channelType">
					    <option value="">请选择</option>
					  	${fns:getDicOptions("channel_type")}
					  </select>
					</div>
				</div>
				<div class="col-md-2">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
				</div>	
			</div>
		<table:table headerNames="通道代码,名称,资金方,可用资金,资金更新时间,收费比例,最低消费,状态,类型,创建时间,操作"
					 columnNames="code,name,customerName,cashAvailable,cashUptimeStr,tatio,minCost,isAvailableStr,channelTypeStr,createTimeStr"
					 ajaxURI="${ctx}/channel/find"
					 id="showTable"
					 searchInputNames="name,isAvailable,channel_type"
					 makeDefsHtmlFunc="columnDefs">

		</table:table>
	    </div>
	</div>
</div>	    
	</body>
</html>

