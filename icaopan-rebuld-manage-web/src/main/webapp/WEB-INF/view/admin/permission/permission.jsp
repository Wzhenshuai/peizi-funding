<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>爱操盘后台管理系统 | 管理</title>
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>权限管理
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
						  <span class="input-group-addon" id="basic-addon1">权限名称</span>
						  <input type="text" class="form-control" name="permissionName" id="permissionName-search" placeholder="权限名称" aria-describedby="basic-addon1">
						</div>
					</div>
					
					<div class="col-md-3">
						<button class="btn btn-danger" onclick="searchForUser();"> <i class="glyphicon glyphicon-search"></i> 查询</button>
						<button type='button' id='add' class='btn btn-danger'><i class='glyphicon glyphicon-plus'></i> 添加</button>
					</div>		
				</div>
				<table id="showTable" class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>权限名称</th>
							<th>菜单名称</th>
							<th>菜单链接</th>
							<th>菜单等级</th>
							<th>可否查询</th>
							<th>可否新增</th>
							<th>可否修改</th>
							<th>可否删除</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>		
	</div>
	
	<script type="text/javascript">
		var showTable = $("#showTable").dataTable({
			"bFilter" : false,// 搜索栏
			"bLengthChange" : false,// 每行显示记录数
			"bSort" : false,// 排序
			"bInfo" : true,// Showing 1 to 10 of 23 entries 总记录数没也显示多少等信息
			"bScrollCollapse" : true,
			"sPaginationType" : "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : '${pageContext.request.contextPath}/permission/find',
			"aoColumns" : [ 
				{
					"mData" : "permissionName",'sClass':'center'
				}, {
					"mData" : "menuName",'sClass':'center'
				}, {
					"mData" : "menuUrl",'sClass':'center'
				}
			],
			"columnDefs": [
						{
						    "targets": [3],
						    "data": "menuClazz",
						    "render": function(data, type, full) {
						    	 if (data != "1"){
						    		 return "一级菜单";
						    	 }else{
						    		 return "二级菜单";
						    	 }
						    }
						  }, {
			                 "targets": [4],
			                 "data": "canQuery",
			                 "render": function(data, type, full) {
			                	 var switch_b = "<div class='switch' data-on='info' data-off='success'><input class='switch-item' onchange='changeStatus(this)' type='checkbox' data-flag='canQuery' data-id='"+full.id+"'";
			                  	 if ( data != 0 ){
			                  		switch_b += " checked='"+data+"' ";
			                  	 }
			                	 switch_b += " /></div>"
			                	 return switch_b;
			                 }
			             }, {
			                 "targets": [5],
			                 "data": "canSave",
			                 "render": function(data, type, full) {
			                	 var switch_b = "<div class='switch' data-on='info' data-off='success'><input class='switch-item' onchange='changeStatus(this)' type='checkbox' data-flag='canSave' data-id='"+full.id+"'";
			                  	 if ( data != 0 ){
			                  		switch_b += " checked='"+data+"' ";
			                  	 }
			                	 switch_b += " /></div>"
			                	 return switch_b;			                 }
			             }, {
			                 "targets": [6],
			                 "data": "canUpdate",
			                 "render": function(data, type, full) {
			                  	 var switch_b = "<div class='switch' data-on='info' data-off='success'><input class='switch-item' onchange='changeStatus(this)' type='checkbox' data-flag='canUpdate' data-id='"+full.id+"'";
			                  	 if ( data != 0 ){
			                  		switch_b += " checked='"+data+"' ";
			                  	 }
			                  	 switch_b += " /></div>"
			                	 return switch_b;
			                 
			                 }
			             }, {
			                 "targets": [7],
			                 "data": "canDelete",
			                 "render": function(data, type, full) {
			                	 var switch_b = "<div class='switch' data-on='info' data-off='success'><input class='switch-item' onchange='changeStatus(this)' type='checkbox' data-flag='canDelete' data-id='"+full.id+"'";
			                	 if ( data != 0){
			                		 switch_b += " checked='"+data+"' ";
			                	 }
			                	 switch_b += " /></div> ";
			                  	 return switch_b;
			                 }
			             }, {
			                 "targets": [8],
			                 "data": "id",
			                 "render": function(data, type, full) {
			                	 var options = "";
			                	 options += "<button type='button' id='delete' class='btn btn-danger' onclick='deleteAdminPermission("+data+")'><i class='glyphicon glyphicon-remove'></i> 删除</button> &nbsp;";
			                  	 return options;
			                 }
			            }
		   ],
		   "fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : 'post',
					"url" : sSource,
					"dataType" : "json",
					"data" : {
						aoData : JSON.stringify(aoData),
						'permissionName':$("#permissionName-search").val()
					},
					"success" : function(resp) {
						fnCallback(resp);
						/* 初始化开关样式 */
						$('.switch-item').bootstrapSwitch();
					},"error" : function (result){
						alertx("发生异常,或没权限...");
					}
				});
			}
		});
		
		/* 条件查询 */
		function searchForUser(){
			showTable.fnDraw(false);
		}
		
		/* 修改权限	*/
		function changeStatus(obj) {
			   var params = {}
			   params[obj.getAttribute("data-flag")] = obj.checked;
			   params['id'] = obj.getAttribute("data-id");
			   $.ajax({
					"type" : 'post',
					"url" : "${pageContext.request.contextPath}/permission/update",
					"dataType" : "json",
					"data" :params ,
					"success" : function(result) {
						alertx(result.message);
						/* 重新查询 */
						searchForUser();
					},"error" : function (result){
						alertx("发生异常,或没权限...");
					}
				});
		}
		
		/* 删除权限 */
		function deleteAdminPermission(id){
			 $.ajax({
					"type" : 'post',
					"url" : "${pageContext.request.contextPath}/permission/delete",
					"dataType" : "json",
					"data" :{'id':id} ,
					"success" : function(result) {
						alertx(result.message);
						/* 重新查询 */
						searchForUser();
					},"error" : function (result){
						alertx("发生异常,或没权限...");
					}
			 });
		}
		
		/* 添加权限 */
		function add(){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/permission/save",
				"dataType" : "json",
				"data" : $("#paramsForAdd").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 清空form表单 */
					$(".paramsForAdd .form-control").val("");
					/* 关闭弹窗  */
					$('#modelForAdd').modal('hide');
					/* 重新查询 */
					searchForUser();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});	
		}
		
		
		$(function(){
			/* 切换添加的Modal */
			$("#add").click(function(){
				$("#modelForAdd").modal();
			});
			
			/*预处理添加时菜单*/
			/* $.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/permission/menus",
				"dataType" : "json",
				"success" : function(result) {
					var options = "";
					result.forEach(function(menu){  
						options += "<option value="+menu.id+">"+menu.menuName+"</option>";
					}); 
					$("#menuId").html(options);
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
		 	});
			
			/* 加载菜单下拉框  */
			/*$("#menuId").select2(); */
				 
		});
		
	</script>
</body>
</html>