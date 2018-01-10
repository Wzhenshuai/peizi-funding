<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function () {
			

			$("#addForm").validate({
				rules:{
                    securityName:{
						required: true
				    },
                    cashAccount:{
						required: true
				    },
                    jyPass: {
                        required: true
                    },
					notes:{
                        rangelength:[0,300]
					}
				},
				submitHandler: function(form){
					$.ajax({
						"type" : 'post',
						"url" : "${ctx}/channel/addChannelApply",
						"dataType" : "json",
						"data" : $("#addForm").serialize(),
						"success" : function(result) {
							alertx(result.message,function(){
								if(result.rescode=='success'){
									window.history.go(-1);
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
					<i class="fa fa-coffee"></i>提交通道信息
				</div>
			</div>
			<div class="portlet-body">
				<form id="addForm" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label">券商名称</label>
						<div class="col-sm-6">
							<input name="securityName"  type="text"  class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">资金账号</label>
						<div class="col-sm-6">
							<input name="cashAccount"  class="form-control"  >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">交易密码</label>
						<div class="col-sm-6">
							<input name="jyPass"  class="form-control"  >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">所属营业部</label>
						<div class="col-sm-6">
							<input name="yybName"  class="form-control"  >
						</div>
					</div>
					<hr>
					<div class="form-group">
						<label class="col-sm-2 control-label">交易账号</label><span><font color="red">*</font>如果没有不用填写</span>
						<div class="col-sm-6">
							<input name="tradeAccount"  class="form-control"  >
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">通信密码</label><span><font color="red">*</font>如果没有不用填写</span>
						<div class="col-sm-6">
							<input name="txPass"  class="form-control"  >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-6">
							<input name="notes" class="form-control"  maxlength="50"/>
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
	</body>
</html>
