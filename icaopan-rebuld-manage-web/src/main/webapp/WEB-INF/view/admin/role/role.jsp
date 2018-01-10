<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>爱操盘后台管理系统 | 角色管理</title>
<style>
.qx{
	max-height:20em;
	overflow: scroll;
}
</style>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>角色管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
					  <span class="input-group-addon" id="basic-addon1">角色名称</span>
					  <input type="text" class="form-control" name="rolename" id="rolename-search" placeholder="角色名称" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<button class="btn btn-danger" onclick="searchForUser();"> <i class="glyphicon glyphicon-search"></i> 查询</button>
					<button type='button' id='add' class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i> 添加</button>
				</div>		
			</div>
			<table:table headerNames="角色名称,角色描述,操作" columnNames="rolename,roledesc" ajaxURI="${ctx}/role/find" id="showTable" makeDefsHtmlFunc="columnDefs" searchInputNames="rolename"></table:table>
		</div>
	</div>
</div>				
<!-- 定义添加时的模态框 -->
<div class="modal fade" id="modelForAdd">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">添加角色</h4>
      </div>
      <form id="addForm" class="add-form">
      <div class="modal-body">
    	<div class="form-group">
		  <input type="text" class="form-control required" name="rolename"  placeholder="角色名称" aria-describedby="basic-addon1">
		</div>
		<div class="form-group">
		  <input type="text" class="form-control required" name="roledesc" placeholder="角色描述" aria-describedby="basic-addon1">
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <input type="submit" class="btn btn-primary" value="保存">
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 定义修改时的模态框 -->
<div class="modal fade" id="modelForUpdate">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">修改用户</h4>
      </div>
      <form id="updateForm" class="add-form">
	      <div class="modal-body">
     		<input type="hidden" id="id" name="id"/>
     		<div class="form-group">
              <label for="rolename">角色名称</label>
			  <input type="text" class="form-control required col-sm-4" name="rolename" id="rolename" placeholder="角色名称" aria-describedby="basic-addon1">
			</div>
			<div class="form-group">
			  <label for="roledesc">角色描述</label>
			  <input type="text" class="form-control required" name="roledesc" id="roledesc" placeholder="角色描述" aria-describedby="basic-addon1">
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <input type="submit" class="btn btn-primary" value="修改">
	      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 定义权限分配的modal -->
<div class="modal fade" id="distribute-modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">分配权限</h4>
      </div>
      <div class="modal-body">
      		<form id="distribute-form" style="text-align: -webkit-center;">
      			<input type="hidden" name='id' id="role-id" />
      			<div class="portlet box red">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-coffee"></i> 分配权限
						</div>
					</div>
					<div class="portlet-body">
						<div class="row">
								<div class="col-xs-6">
									<input type="text" class="form-control" name="permissionName" id="permissionName" placeholder="请输入权限名称"/>
								</div>
								<div class="col-xs-6">
									 <button class="btn btn-primary" onclick="researchForPermission();return false;">查询</button>
								</div>
						</div>
						<br/>
						<div class="table-responsive qx">
							<table class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<td><a href="javascript:selectAll('inputcheck')">全选</a>/<a href="javascript:selectNotAll('inputcheck')">全不选</a></td>
										<td>权限名称</td>
										<td>权限描述</td>
									</tr>
								</thead>
								<tbody id="quanxian">
									<tr>
										<td>权限名称</td>
										<td>权限描述</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>		
      		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="savePermission()">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	<script type="text/javascript">
		function columnDefs(rowdata){
			 rowid=rowdata.id;
			 var options = "";
	       	 options += "<button type='button' id='update' class='btn btn-info' onclick='getUserForUpdate("+rowid+")'><i class='glyphicon glyphicon-edit'></i> 修改</button> &nbsp;";
	       	 options += "<button type='button' id='delete' class='btn btn-danger' onclick=confirmx('确定要删除角色吗？','deleteAdminUser("+rowid+")')><i class='glyphicon glyphicon-remove'></i> 删除</button> &nbsp;";
	       	 options += "<button type='button' id='disbute' class='btn btn-primary' onclick='disbutePermission("+rowid+")'><i class='glyphicon glyphicon-pushpin'></i> 分配权限</button> &nbsp;";
	       	 return options;
		}
		
		/* 条件查询 */
		function searchForUser(){
			showTable.fnDraw(false);
		}
		
		
		/* 查询用于修改的数据 */
		function getUserForUpdate(id){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/role/get",
				"dataType" : "json",
				"data" : {'id':id},
				"success" : function(result) {
					console
					$("#id").val(id);
					$("#rolename").val(result.rolename);
					$("#roledesc").val(result.roledesc);
					$("#modelForUpdate").modal();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		/* 删除数据*/
		function deleteAdminUser(id){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/role/delete",
				"dataType" : "json",
				"data" : {'id':id},
				"success" : function(result) {
					alertx(result.message);
					/* 重新查询 */
					searchForUser();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		function researchForPermission(){
			disbutePermission($("#role-id").val(),$("#permissionName").val());
		}
		
		/* 加载权限表*/
		function disbutePermission(id,permissionName){
			
			/* 清空权限表 */
			$("#quanxian").html("");
			
			/* 加载权限树 */
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/role/assign/find",
				"dataType" : "json",
				"data" : {"id":id,"permissionName":permissionName},
				"success" : function(result) {
					/* 添加角色ID */
					$("#role-id").val(id);
					
					var htmls = "";
					for (var permission in result){
						htmls += "<tr>";
						
						if (result[permission].role_id == "" || result[permission].role_id == null){
							htmls += "<td><input name='permission_id' class='inputcheck' type='checkbox'  value="+result[permission].id+"></td>";
						}else {
							htmls += "<td><input name='permission_id' class='inputcheck' type='checkbox'  value="+result[permission].id+" checked></td>";
						}
						
						htmls += "<td>"+result[permission].permission_name+"</td>";
						htmls += "<td>"+result[permission].permission_desc+"</td>";
						htmls += "</tr>";
					}
					
					$("#quanxian").html(htmls)
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
			$('#distribute-modal').modal();
		}
		
		/* 保存权限 */
		function savePermission(){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/role/assign/save",
				"dataType" : "json",
				"data" : $("#distribute-form").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 清空form表单 */
					$(".distribute-modal .form-control").val("");
					/* 关闭弹窗  */
					$('#distribute-modal').modal('hide');
					/* 重新查询 */
					searchForUser();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		
		/* 全选  */
		function selectAll(clazz){
			$("."+clazz).attr("checked", true);
		}
		/* 全不选 */
		function selectNotAll(clazz){
			$("."+clazz).attr("checked", false);
		}
		
		$(function(){
			/* 添加切换modal */
			$("#add").click(function(){
				$('#modelForAdd').modal();
			});
		});
		
		
	</script>
	
	<script type="text/javascript">
		$(function(){
			$("#addForm").validate({ 
				rules:{
					rolename:"required",
					roledesc:"required"
				},
				submitHandler: function(form){
					$.ajax({
						"type" : 'post',
						"url" : "${pageContext.request.contextPath}/role/save",
						"dataType" : "json",
						"data" : $("#addForm").serialize(),
						"success" : function(result) {
							alertx(result.message);
							/* 清空form表单 */
							$(".addForm .form-control").val("");
							/* 关闭弹窗  */
							$('#modelForAdd').modal('hide');
							/* 重新查询 */
							searchForUser();
						},"error" : function (result){
							alertx("发生异常,或没权限...");
						}
					});
				}
			});
			$("#updateForm").validate({ 
				rules:{
					rolename:"required",
					roledesc:"required"
				},
				submitHandler: function(form){
					$.ajax({
						"type" : 'post',
						"url" : "${pageContext.request.contextPath}/role/update",
						"dataType" : "json",
						"data" : $("#updateForm").serialize(),
						"success" : function(result) {
							alertx(result.message);
							/* 清空form表单 */
							$(".updateForm .form-control").val("");
							/* 关闭弹窗  */
							$('#modelForUpdate').modal('hide');
							/* 重新查询 */
							searchForUser();
						},"error" : function (result){
							alertx("发生异常,或没权限...");
						}
					});
				}
			});
		});
	</script>
</body>
</html>