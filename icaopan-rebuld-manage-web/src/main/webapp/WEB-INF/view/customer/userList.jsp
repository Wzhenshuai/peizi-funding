<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
	<meta name="decorator" content="default"/>
	</head>
	<body>
		<div class="portlet box red">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-coffee"></i>后台用户
				</div>
			</div>
			<div class="portlet-body">
				<div class="table-responsive">
						<div class="row">
							<div class="col-md-2">
								<div class="input-group">
								  <span class="input-group-addon" id="basic-addon1">用户名</span>
								  <input type="text" class="form-control" name="username" id="username-search" placeholder="用户名" aria-describedby="basic-addon1">
								</div>
							</div>
							<div class="col-md-2">
								<div class="input-group">
								  <span class="input-group-addon" id="basic-addon1">姓名</span>
								  <input type="text" class="form-control" name="realname" id="realname-search" placeholder="用户名" aria-describedby="basic-addon1">
								</div>
							</div>
							<div class="col-md-2">
								<div class="input-group">
								  <span class="input-group-addon" id="basic-addon1">手机号</span>
								  <input type="text" class="form-control" name="phone" id="phone-search" placeholder="手机号" aria-describedby="basic-addon1">
								</div>
							</div>	
							<div class="col-md-2">
								<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
							</div>		
						</div>
					<table:table headerNames="用户名,姓名,资金方,手机号,备注,创建时间,更新时间"
								 columnNames="userName,realName,customerName,phone,notes,createTimeStr,modifyTimeStr"
								 ajaxURI="${ctx}/user/find"
								 id="showTable"
								 searchInputNames="username,realname,phone">
					</table:table>
			    </div>
			</div>
		</div>
		<!-- 定义修改密码的模态框 -->
		<div class="modal fade" id="modifyPwd">
			<div class="modal-dialog" style="padding-left:20px;text-align:center;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">修改密码</h4>
					</div>
					<div class="modal-body">
						<div class="form-group col-md-8 col-md-offset-3">
							<label class="col-sm-4 control-label">用户名</label>
							<div class="col-sm-4">
								<label id="name" class="clearInput control-label col-md-2">****</label>
								<input type="hidden" id="id">
							</div>
						</div>
						<div class="form-group col-md-8 col-md-offset-3">
							<label class="col-sm-4 control-label">密码</label>
							<div class="col-sm-4">
								<input style="width: 100px" type="password" class="clearInput" id="pwd" name="pwd" placeholder="请输入新密码"  htmlEscape="false" maxlength="100"/>
							</div>
						</div>
						<div class="form-group col-md-8 col-md-offset-3">
							<label class="col-sm-4 control-label"></label>
							<div class="col-sm-4">
								<input style="width: 100px" type="password" class="clearInput" id="repwd" name="repwd" placeholder="请再次输入新密码"  htmlEscape="false" maxlength="100"/>
							</div>
						</div>
						<div class="form-group col-md-8 col-md-offset-3">
							<label class="col-sm-3 control-label"></label>
							<div class="col-sm-4">
								<label id="tips" style="color: red"></label>
							</div>
						</div>
						<div class="form-group">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<input onclick="updatePwd()" class="btn btn-primary" type="submit" value="确定"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	<script>
//		function defs(rowdata){
//			console.log(rowdata);
//			return "<input type='button' onclick=\"initForm("+rowdata.id+",'"+rowdata.userName+"')\" class='btn btn-warning btn-xs' value='修改密码'/>";
//		}
		function initForm(id,name) {
			$("#modifyPwd").modal();
			$('#name').text(name);
			$('#id').val(id);
		}
		function updatePwd(){
			$('#tips').text("");
			var id = $('#id').val();
			var pwd = $('#pwd').val();
			var repwd = $('#repwd').val();
			if(pwd==null||repwd==null||pwd==""||repwd==""){
				$('#tips').text("密码不可为空");
				return false;
			}
			if(pwd!=repwd){
				$('#tips').text("前后两次密码不一致");
				return false;
			}
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/user/resetPwd",
				"dataType" : "json",
				"data" : {
					'id':id,
					"passWord":pwd
				},
				"success" : function(result) {
					$('#modifyPwd').modal('hide');
					alertx(result.message);
					/* 清空 */
					window.location.reload();
				},"error" : function (result){
					alertx(result.message);
				}
			});

		}



	</script>
	</body>
</html>
