<%@page import="com.icaopan.task.mytask.service.impl.TaskSwitch"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>任务管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>任务管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
            <div class=" panel panel-success">
                <div class="panel-body">
                	<div class="col-md-4">
                		<button class="btn btn-danger" onclick="update('clearPosition')">清除空持仓</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="update('cancelPlacement')">取消在途</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="update('clearing')">日终清算</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="update('checkPosition')">用户通道持仓对账</button>
                        <br><br>
                        <%if (TaskSwitch.autoFill == true) {%>
                        	<button class="btn btn-danger" onclick="update('autoFill')">关闭自动成交</button>

                        <%
                        }else{
                        %>
                        	<button class="btn btn-danger" onclick="update('autoFill')">开启自动成交</button>
                       <% } %>
                	</div>
                    <div class="col-md-4">
                        <button class="btn btn-danger" onclick="update('quote')">更新行情</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="aotoUpdateMarketData()">自动更新行情</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="update('limit')">更新涨跌停</button>
                        <br><br>
                       	<button class="btn btn-danger" onclick="update('updateStockSecurity')">更新股票代码</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="updatePool('updateStockPool')">更新股票池</button>
						<br><br>
						<button class="btn btn-danger" onclick="update('updateAilyChangePercent')">更新昨日漲跌停股票</button>
						<br><br>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-danger" onclick="update('updateCustomerBalanceDay')">每日扣除资金方费用</button>
                        <br><br>
                        <button class="btn btn-danger" onclick="update('updateCustomerBalanceMonthly')">每月扣除资金方抵消
                        </button>
                        <br><br>
                        <button class="btn btn-danger" onclick="update('reloadPrivateBroker')">手动更新风控大盘信息</button>
                        <br><br>
                    </div>
                </div>
            </div>
		</div>
	</div>
</div>

<!-- 定义增配减配的模态框 -->
<div class="modal fade" id="tips">
	<div class="modal-dialog" style="text-align:center;">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">更新股票池信息结果</h4>
			</div>
			<div >
				<div class="col-md-8 col-md-offset-2">
					<div class="input-group">
						<label id="tipTxt"></label>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="col-md-4 col-md-offset-5">
					<div class="input-group">
						<button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>

	function update(task){
		if(confirm("确定要继续吗?")){
			$.ajax({
                "type": 'post',
                "url": "${ctx}/task/" + task,
                "dataType": "json",
                "success": function (result) {
                    alertx(result.message);
                    location.reload();
                }, "error": function (result) {
                    alertx("发生异常,或没权限...");
                }
            })
		}
        /* jBox.confirm(task + ":确定要继续吗？", "提示", function (v, h, f) {
            if (v == 'ok') {
                
            } else {
                return true;
            }
        }) */
	}
	
	function aotoUpdateMarketData(){
		setInterval(function(){
			$.ajax({
                "type": 'post',
                "url": "${ctx}/task/quote",
                "dataType": "json",
                "success": function (result) {
                    //alertx(result.message);
                }, "error": function (result) {
                    //alertx("发生异常,或没权限...");
                }
            })
		}, 5000);
	}
	
    function updatePool(task){
        $.ajax({
            "type" : 'post',
            "url" : "${ctx}/task/"+task,
            "dataType" : "json",
            "success" : function(result) {
                $("#tipTxt").html(result.message)
                $("#tips").modal();
            },"error" : function (result){
                alertx("发生异常,或没权限...");
            }
        });
    }

</script>
</body>
</html>