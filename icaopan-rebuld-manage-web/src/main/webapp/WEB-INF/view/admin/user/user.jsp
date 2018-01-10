<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default"/>
		<script type="text/javascript" src="${ctxStatic}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
		<link href="${ctxStatic}/assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css"/>

		<script type="text/javascript">
		function columnDefs(row){
			 userId=row.id;
           	 var options = "";
           	 options += "<button type='button' id='update' class='btn btn-info' onclick='getUserForUpdate("+userId+")'><i class='glyphicon glyphicon-edit'></i> 修改</button> &nbsp;";
           	 options += "<button type='button' id='update' class='btn btn-warning' onclick='resetPwd("+userId+")'><i class='glyphicon glyphicon-edit'></i>重置密码</button> &nbsp;";
           	 options += "<button type='button' id='delete' class='btn btn-danger' onclick=confirmx('确定要删除吗？','deleteAdminUser("+userId+")')><i class='glyphicon glyphicon-remove'></i> 删除</button> &nbsp;";
           	 options += "<button type='button'  class='btn btn-primary' onclick='distributeRole("+userId+")'><i class='glyphicon glyphicon-pushpin'></i> 分配角色</button> &nbsp;";
             return options;
		}
		/* 重置密码  */
		function resetPwd(id){
			$("#modelForResetPwd #id").val(id);
			$("input[name='passWord']").val("");
			$("input[name='rePassWord']").val("");
			$("#modelForResetPwd").modal();
		}
		/* 查询用于修改的数据 */
		function getUserForUpdate(id){
			location.href="${ctx}/user/form?id="+id;
		}
		
		
		/* 删除数据*/
		function deleteAdminUser(id){
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/user/delete",
				"dataType" : "json",
				"data" : {'id':id},
				"success" : function(result) {
					alertx(result.message);
					/* 重新查询 */
					reloadTableData();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		function distributeRole(id){
			$('#custom-headers').html("");
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/user/distribute/get",
				"dataType" : "json",
				"data" : {'id':id},
				"success" : function(result) {
					var select_content = "";
					result.forEach(function(e){  
						select_content +=  "<option value='"+e.id+"'"
						if (e.role_id != null && e.role_id != ""){
							select_content += " selected "
						}
					    select_content += ">"+e.roledesc+"</option>" ;
					})  
					$('#custom-headers').html(select_content);
					console.log(select_content);
					init_select();
					$("#user-id").val(id);
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
			$("#distribute-modal").modal();
		}
		
		/* 保存权限 */
		function savePermission(){
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/user/distribute/save",
				"dataType" : "json",
				"data" : $("#distribute-form").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 关闭弹窗  */
					$('#distribute-modal').modal('hide');
					/* 重新查询 */
					reloadTableData();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		/* 初始化权限分配的multiselect */
		function init_select(){
			
			$('.searchable').multiSelect({
				  selectableHeader: "<label>暂未分配的角色</label><br/><input type='text' class='form-control' autocomplete='off' placeholder='试试输入角色名~'>",
				  selectionHeader:  "<label>已经分配的角色</label><br/><input type='text' class='form-control' autocomplete='off' placeholder='试试输入角色名~'>",
				  afterInit: function(ms){
				    var that = this,
				        $selectableSearch = that.$selectableUl.prev(),
				        $selectionSearch = that.$selectionUl.prev(),
				        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
				        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

				    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
				    .on('keydown', function(e){
				      if (e.which === 40){
				        that.$selectableUl.focus();
				        return false;
				      }
				    });

				    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
				    .on('keydown', function(e){
				      if (e.which == 40){
				        that.$selectionUl.focus();
				        return false;
				      }
				    });
				  },
				  afterSelect: function(){
				    this.qs1.cache();
				    this.qs2.cache();
				  },
				  afterDeselect: function(){
				    this.qs1.cache();
				    this.qs2.cache();
				  }
			});
			$('.searchable').multiSelect('refresh');
			$("#ms-s2id_custom-headers").remove();
		}
		
		$(function(){
			/* 添加用户 */
			$("#add").click(function(){
				$('#modelForAdd').modal();
			});
		});
	</script>
	<script type="text/javascript">
		$(function(){
			$("#addForm").validate({ 
				rules:{
					userName:{
						required: true,
						rangelength:[5,10]
				    },
				    realName:{
				    	required: true,
						rangelength:[5,30]
				    },
					passWord:{
						required: true,
						rangelength:[5,16]
				    },
					phone:{number:true}
				},
				submitHandler: function(form){
					$.ajax({
						"type" : 'post',
						"url" : "${ctx}/user/save",
						"dataType" : "json",
						"data" : $("#addForm").serialize(),
						"success" : function(result) {
							alertx(result.message);
							/* 清空form表单 */
							$("#addForm .form-control").val("");
							/* 关闭弹窗  */
							$('#modelForAdd').modal('hide');
							/* 重新查询 */
							reloadTableData();
						},"error" : function (result){
							alertx("发生异常,或没权限...");
						}
					});
				}
			});
			$("#updateForm").validate({ 
				rules:{
					userName:{
						required: true,
						rangelength:[5,30]
				    },
				    realName:{
				    	required: true,
						rangelength:[5,30]
				    },
					phone:{number:true}
				},
				submitHandler: function(form){
					$.ajax({
						"type" : 'post',
						"url" : "${ctx}/user/update",
						"dataType" : "json",
						"data" : $("#updateForm").serialize(),
						"success" : function(result) {
							alertx(result.message);
							/* 清空form表单 */
							$(".updateForm .form-control").val("");
							/* 关闭弹窗  */
							$('#modelForUpdate').modal('hide');
							/* 重新查询 */
							reloadTableData();
						},"error" : function (result){
							alertx("发生异常,或没权限...");
						}
					});
				}
			});
		$("#resetPwdForm").validate({ 
			rules:{
				passWord:{
					required: true,
					rangelength:[5,10]
			    },
			    rePassWord:{
					required: true,
					rangelength:[6,16],
					equalTo:"#resetPwdForm input[name='passWord']"
			    }
			},
			submitHandler: function(form){
				$.ajax({
					"type" : 'post',
					"url" : "${ctx}/user/resetPwd",
					"dataType" : "json",
					"data" : $("#resetPwdForm").serialize(),
					"success" : function(result) {
						alertx(result.message);
						/* 清空form表单 */
						$(".addForm .form-control").val("");
						/* 关闭弹窗  */
						$('#modelForResetPwd').modal('hide');
						reloadTableData();
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
					<i class="fa fa-coffee"></i>系统用户
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
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">状态</span>
									<select
											class="form-control" name="status">
										<option value="">请选择</option>
										<option value="0">正常</option>
										<option value="1">锁定</option>
									</select>
								</div>
							</div>
							<div class="col-md-2">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">资金方</span>
									<select
											class="form-control" name="customerId">
										<option value="">请选择</option> ${fns:getCustomers()}
									</select>
								</div>
							</div>
							<div class="col-md-2">
								<button class="btn btn-danger" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
								<button type='button' id='add' class='btn btn-danger' onclick='location.href="${ctx}/user/form"'><i class='glyphicon glyphicon-plus'></i>添加</button>
							</div>		
						</div>
					<table:table headerNames="用户名,姓名,资金方,状态,手机号,备注,创建时间,更新时间,操作"
								 columnNames="userName,realName,customerName,statusStr,phone,notes,createTimeStr,modifyTimeStr"
								 ajaxURI="${ctx}/user/find" id="showTable"
								 searchInputNames="username,realname,phone,status,customerId" makeDefsHtmlFunc="columnDefs"></table:table>
			    </div>
			</div>
		</div>	    
		<!-- 定义添加时的模态框 -->
		<div class="modal fade" id="modelForResetPwd">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">重置密码</h4>
		      </div>
		      <form id="resetPwdForm" class="add-form">
		         <input type="hidden" id="id" name="id"/>
		     	 <div class="modal-body">
		      			<div class="form-group">
						  <label for="passWord">密码</label>
						  <input type="password" class="form-control" name="passWord" placeholder="密码" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
						  <label for="rePassWord">确认密码</label>
						  <input type="passWord" class="form-control" name="rePassWord" placeholder="确认密码" aria-describedby="basic-addon1">
						</div>
		      	  </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			        <input type="submit" class="btn btn-primary" value="确认">
			      </div>
		      </form>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<!-- 定义角色分配的modal -->
		<div class="modal fade" id="distribute-modal">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">分配角色</h4>
		      </div>
		      <div class="modal-body">
		      		<div class="row">
		      			<div class="col-sm-2">
		      			</div>
		      			<div class="col-sm-8">
		      				<form id="distribute-form">
				      			<input type="hidden" name='id' id="user-id" />
				      			<select id='custom-headers' name="role_ids" class="searchable" multiple='multiple'>
								</select>
				      		</form>
		      			</div>
		      			<div class="col-sm-2">
		      			</div>
		      		</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		        <button type="button" class="btn btn-primary" onclick="savePermission()">确认</button>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</body>
</html>
