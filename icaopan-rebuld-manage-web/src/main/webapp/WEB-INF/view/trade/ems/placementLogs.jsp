<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js />
<html>
<head>
<meta name="decorator" content="default"></meta>
</head>
<body>

	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>委托日志查询
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
					<!-- 搜索框 start -->
					<div class="row">
						<div class="col-md-10">
							<table id="datatable">
								<c:forEach var="channel" items="${channelList }" varStatus="status">
									<tr>
										<td>${status.index }.</td>
										<td>
											<a href="file://W:/logs/${channel.code }-placement-${today }.log" target="_blank">${channel.name }的委托日志</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
			</div>
		</div>
	</div>

</body>
</html>
