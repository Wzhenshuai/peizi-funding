<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<init:init-js />
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function adjustChannelPositionByBrokerPosition(){
		//String brokerPositionId,String channelPositionIds,String sides,String amounts,String costPrices
		var brokerPositionId=$("#brokerPositionId").val();
		var channelPositionIds=[];var sides=[];var amounts=[];var costPrices=[];
		$("#myTable tr").each(function(){
			var amount=$(this).find("input[ty='amount']").val();
			if($.trim(amount)!=''){
				var channelPositionId=$(this).find("input[ty='channelPositionId']").val();
				var side=$(this).find("select[ty='side']").val();
				var costPrice=$(this).find("input[ty='costPrice']").val();
				channelPositionIds.push(channelPositionId);
				sides.push(side);
				amounts.push(amount);
				costPrices.push(costPrice);
			}
		});
		ajaxRequestPost("${ctx }/brokerPosition/adjustChannelPositionByBrokerPosition",{brokerPositionId:brokerPositionId,channelPositionIds:channelPositionIds.join(","),sides:sides.join(","),amounts:amounts.join(","),costPrices:costPrices.join(",")},function(r){
			if(r.rescode=="success"){
				alertx("处理成功");
				location.go(-1);
			}else{
				alertx(r.message);
			}
		});
	}
</script>
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>通道持仓调整
			</div>
		</div>
		<div class="portlet-body">
			<input id="brokerPositionId" type="hidden" value="${result.id }"/>
			<div class="row">
				<div class="col-md-12 text-right">
					<div style="font-weight: bold;font-size: 12px;color: red;padding:10px">通道名称：${result.channelName } 股票代码：${result.stockCode }
						平台持仓总数量：${result.icpAmount } 券商持仓总数量：${result.amount }</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${!empty result.detailList}">
					<table class="table table-bordered table-striped" id="myTable">
						<tr>
							<th>通道ID</th>
							<th>通道名称</th>
							<th>股票代码</th>
							<th>股票名称</th>
							<th>用户名</th>
							<th>持仓数量</th>
							<th>平台持仓成本价</th>
							<th>券商持仓成本价</th>
							<th>调整方向</th>
							<th>调整数量</th>
							<th>成本价</th>
						</tr>
						<c:forEach items="${result.detailList}" var="cl">
							<tr>
								<td>${cl.id}<input type="hidden" value="${cl.id }" ty="channelPositionId"/></td>
								<td>${cl.channelName}</td>
								<td>${cl.internalSecurityId}</td>
								<td>${cl.stockName}</td>
								<td>${cl.userName}</td>
								<td>${cl.amount}</td>
								<td>${cl.costPrice}</td>
								<td>${result.costPrice}</td>
								<td>
									<select ty="side">
										<option value="+">调增+</option>
										<option value="-">调减-</option>
									</select>
								</td>
								<td><input type="text" ty="amount"/></td>
								<td><input type="text" ty="costPrice" value="${cl.costPrice}"/></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<h1>暂无数据......</h1>
				</c:otherwise>
			</c:choose>
			<div class="col-md-12 text-center">
				<button type="submit" class="btn btn-danger btn-sm" onclick="adjustChannelPositionByBrokerPosition()">确认</button>
				<button type="button" class="btn btn-default btn-sm" onclick="history.go(-1)">取消</button>
			</div>
		</div>
	</div>
</body>
</html>

