<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>新红配查询</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>新红配查询
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">股票代码</span>
						<input type="text" class="form-control" name="securityCode" id="securityCode-search" placeholder="股票代码" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >状态</span>
						<select id="status" name="status" class="form-control">
							<option value="">全部</option>
							<option value="1" selected="selected">待处理</option>
							<option value="2">处理完成</option>
							<option value="3">不做处理</option>
						</select>
					</div>
				</div>
				<div class="col-md-8 text-right">
					<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<a href="${ctx}/stockBonus/makeBonusInput">
					<button type='button' id="add" class='btn btn-danger'>手工添加分配方案</button>
					</a>
					<a href="${ctx}/stockBonus/autoGetScheme">
					<button type='button' class='btn btn-danger'></i>自动获取分配方案</button>
					</a>
					<button type='button'  class='btn btn-danger' onclick="adjust(0)">全部调整</button>
					<button type='button'  class='btn btn-danger' onclick="adjustNo()">全部不处理</button>
				</div>
			</div>
			<table:table id="userTable" ajaxURI="${ctx}/stockBonus/find"
						 columnNames="userName,securityName,securityCode,channelName,securityPositionAmount,bonusBrokerAdjustAmount,bonusAdjustAmount,securityAdjustAmount,statusStr,createTimeStr,remark"
						 headerNames="用户名,证券名称,证券代码,通道名称,持仓数量,券商资金调整金额,平台资金调整金额,证券调整数量,状态,创建时间,备注,操作"
						 searchInputNames="securityCode,status"
						 makeDefsHtmlFunc="defs"/>
		</div>
	</div>
</div>
<script type="text/javascript">
function defs(rowdata){
	return "<button class='btn btn-info'  onclick=adjust('"+rowdata.id+"')><i class='glyphicon glyphicon-edit'></i>确认调整</button>";
}

function adjust(id){
	confirmx("确定要调整吗？",'adjustConfirm('+id+')');
}

function adjustConfirm(id){
	$.ajax({
        type: "post",//使用get方法访问后台
        dataType: "json",//返回json格式的数据
        url: "${ctx}/stockBonus/doBonusAdjust",//要访问的地址
        data: "id="+id,
        success: function (data) {//data为返回的数据，在这里做数据绑定
            if(data.rescode=="success"){
        		alertx("调整成功");
        		reloadTableData();
            }else{
            	alertx(data.message);
            }
        }
    });
}

function adjustNo(){
	if(confirm("确定要将未调整记录作废吗？")){
		$.ajax({
	        type: "post",//使用get方法访问后台
	        dataType: "json",//返回json格式的数据
	        url: "${ctx}/stockBonus/updateUserStockBonusToInvalid",//要访问的地址
	        success: function (data) {//data为返回的数据，在这里做数据绑定
	            if(data.rescode=="success"){
	        		alertx("调整成功");
	        		reloadTableData();
	            }else{
	            	alertx(data.message);
	            }
	        }
	    });
	}
}
</script>
</body>




