<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<script type="text/javascript">
		$(function(){
			$("#addForm").validate({ 
				rules:{
					name:{
						required: true,
						rangelength:[2,50]
				    },
				    minCost:{
				    	number:true
				    },
				    defaultTatio:{
						required: true,
						number:true,
						max:1
				    },
				    defaultMinCost:{
				    	required: true,
				    	number:true,
				    },
				},
				submitHandler: function(form){
					$.ajax({
						"type" : 'post',
						"url" : "${ctx}/customer/save",
						"dataType" : "json",
						"data" : $("#addForm").serialize(),
						"success" : function(result) {
							alertx(result.message,function(){
								if(result.rescode=='success'){
									history.go(-1);
								}
							});
						},"error" : function (result){
							alertx("发生异常,或没权限...");
						}
					});
				}
			});
	});
	</script>
	</head>
	<body>
		<div class="portlet box red">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-coffee"></i>资金方账户<c:choose>
													   <c:when test="${empty customer.id }">  
													       添加
													   </c:when>
													   <c:otherwise> 
													   	编辑
													   </c:otherwise>
													</c:choose>
				</div>
			</div>
			<div class="portlet-body">
				<form id="addForm" class="form-horizontal">
				    <input type="hidden" name="id" value= "${customer.id }"/>
					<div class="form-group">
						<label class="col-sm-2 control-label">资金方</label>
						<div class="col-sm-6">
							<input name="name" value="${customer.name }"  class="form-control" maxlength="50" class="required"/>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">低消模式</label>
						<div class="col-sm-6">
							<select name="costPattern" id="costPattern" value="${customer.costPattern }" _value="${customer.costPattern }" class="form-control" onchange="costPattenSwitch()">
								${fns:getDicOptions("cost_pattern_type")}
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">通道最低收费</label>
						<div class="col-sm-6">
							<input type="number" id="minCost" name="minCost" value="${customer.minCost }" class="form-control"  maxlength="50" class="required"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">用户默认最低佣金</label>
						<div class="col-sm-6">
							<input type="number" name="defaultMinCost" value="${customer.defaultMinCost }" class="form-control"  maxlength="50" class="required"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">用户默认费率</label>
						<div class="col-sm-6">
							<input type="number" name="defaultTatio" value="${customer.defaultTatio }" class="form-control"  maxlength="50" class="required"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">用户状态</label>
						<div class="col-sm-6">
							<select class="form-control" name="status" value="${customer.status }" _value="${customer.status }">
								  ${fns:getDicOptions("user_status_type")}
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-6">
							<input name="notes" value="${customer.notes }" class="form-control"  maxlength="50"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-6">
							<button type="submit" class="btn btn-danger btn-sm">提交</button>
							<button type="button" class="btn btn-default btn-sm" onclick="history.go(-1)">取消</button>
						</div>
					</div>
			    </form>
			</div>
		</div>

	<script>
		function costPattenSwitch(){
			var selectV = $('#costPattern').find("option:selected").val();
			if(selectV=='0'){
				$("#minCost").attr("readOnly","true");
			}else if(selectV=='1'){
				$("#minCost").removeAttr("readOnly");
			}
		}
	</script>

	</body>
</html>
