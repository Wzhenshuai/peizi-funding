<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>添加证券信息</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>添加证券信息
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="col-md-4 col-md-offset-4">
				<form id="inputForm" class="add-form">
					<div class="modal-body">
						<div class="form-group">
							<label>2市唯一</label>
							<input type="text" class="form-control" name="internalSecurityId" placeholder="2市唯一" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label>所属交易所</label>
							<input type="text" class="form-control" name="exchangeCode" placeholder="所属交易所" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label for="code">股票代码</label>
							<input type="text" id="code" class="form-control" name="code" placeholder="股票代码" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label for="name">股票名称:</label>
							<input type="tel" id="name" class="form-control"  name="name" placeholder="股票名称" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label>上市时间</label>
							<input type="text" class="form-control" onclick="WdatePicker()" name="issueDate" placeholder="上市时间" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label>拼音首字母缩写</label>
							<input type="text" class="form-control" name="firstLetter" placeholder="拼音首字母缩写" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label>用户状态</label>
							<select name="suspensionFlag" class="form-control">
								<option value="0">正常</option>
								<option value="1">停牌</option>
							</select>
						</div>
						<div class="form-group">
							<label id="tips" style="color: red"></label>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" onclick="history.go(-1)">返回</button>
							<input type="submit" class="btn btn-primary" value="保存">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>


	$(function(){
		$("#inputForm").validate({
			onsubmit:true,// 是否在提交时验证,默认也是true
			rules:{
				internalSecurityId:{
					required: true,
					rangelength:[2,50]
				},
			},
			submitHandler: function(form) {
				verify();
			},
		});
	});


		//验证事件
		function verify()
		{
			var name = $('#name').val();
			var code = $('#code').val();
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/stock/verify",
				"dataType" : "json",
				"data" : {
					"name":name,
					"code":code
				},
				"success" : function(result) {
					if(result.rescode == "success") {
						$.ajax({
						"type" : 'post',
						"url" : "${ctx}/stock/save",
						"dataType" : "json",
						"data" : $("#inputForm").serialize(),
						"success" : function(result) {
						alertx(result.message);
						/* 清空form表单 */
						$(".inputForm .form-control").val("");
						/*跳转*/
						window.location.href="${ctx}/stock/index";
						},"error" : function (result){
						alertx("发生异常,或没权限...");
						}
						});
					}else{
						$('#tips').text(result.message);
					}

				},"error" : function (result){
					$('#tips').text(result.message);
				}
			});
		}


</script>
</body>
</html>