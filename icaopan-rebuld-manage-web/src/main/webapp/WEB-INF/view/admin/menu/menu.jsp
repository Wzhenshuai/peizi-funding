<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>爱操盘后台管理系统 | 菜单管理</title>
</head>
<body>
	<div class="row">
		<!-- 左侧菜单树 -->
		<div class="col-md-4">
			<div class="portlet box red">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-coffee"></i>菜单管理
					</div>
				</div>
				<div class="portlet-body">
					<div id="menu-tree">
					</div>
				</div>		
			</div>
		</div>
		<!-- 右侧权限管理 -->
		<div class="col-md-8">
			<div class="portlet box red">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-coffee"></i>权限管理
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-responsive">
						<table id="showTable" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>权限名称</th>
									<th>权限CODE</th>
									<th>权限描述</th>
									<th>操作</th>
								</tr>
							</thead>
						</table>
					</div>	
				</div>		
			</div>
		</div>
	</div>
	<input type="hidden" id="menuId-change"/>
	<!-- 添加菜单 -->
	<div id="addMenu" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="menu-modal-title">添加菜单</h4>
	      </div>
	      <div class="modal-body">
	        <form id="menu-form">
	          <input type="hidden" id="menu_id" name="menuId" />
	          <input type="hidden" id="menu_way" name="menuWay" />
	          <input type="hidden" value="admin" id="menuCode" name="menuCode" />
			  <!-- <div class="form-group">
			    <label for="menu_code">菜单权限关键字：</label>
			    <input type="text" class="form-control" id="menu_code" name="menuCode" placeholder="菜单权限关键字">
			  </div> -->
			  <div class="form-group">
			    <label for="menu_name">菜单名称：</label>
			    <input type="text" class="form-control" id="menu_name" name="menuName" placeholder="菜单名称">
			  </div>
			  <div class="form-group">
			    <label for="menu_url">菜单链接：</label>
			    <input type="text" class="form-control" id="menu_url" name="menuUrl" placeholder="菜单链接">
			  </div>
			  <div class="form-group">
			    <label for="menu_order">菜单顺序：</label>
			    <input type="text" class="form-control" id="menu_order" name="menuOrder" placeholder="菜单链接">
			  </div>
			  <div>
			  	<label for="menu_icon">菜单图标：</label>
			  	<div id="menu_icon">
			  		<jsp:include page="../../../../STATICS/common/icon.jsp"></jsp:include>
			  	</div>
			  </div>
			  <div class="form-group">
			    <label for="menu_hidden">开启菜单：</label>
			    <div class="checkbox">
				    <label>
				      <input  id="menu_hidden" type="checkbox" name="menuHiddenE" checked> 是否显示
				    </label>
			  	</div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal"> 取消 </button>
	        <button type="button" class="btn btn-primary" onclick="saveMenu()"> 保存 </button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- 添加菜单 -->
	<div id="modifyMenu" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="menu-modal-title">编辑菜单</h4>
	      </div>
	      <div class="modal-body">
	        <form id="m-menu-form">
	          <input type="hidden" id="m-menu_id" name="id" />
	          <input type="hidden" id="m-menu_way" name="menuWay" />
	          <input type="hidden" id="m-menu_code" name="menuCode" />
			 <!--  <div class="form-group">
			    <label for="m-menu_code">菜单权限关键字：</label>
			    <input type="text" class="form-control" id="m-menu_code" name="menuCode" placeholder="菜单权限关键字">
			  </div> -->
			  <div class="form-group">
			    <label for="m-menu_name">菜单名称：</label>
			    <input type="text" class="form-control" id="m-menu_name" name="menuName" placeholder="菜单名称">
			  </div>
			  <div class="form-group">
			    <label for="m-menu_url">菜单链接：</label>
			    <input type="text" class="form-control" id="m-menu_url" name="menuUrl" placeholder="菜单链接">
			  </div>
			  <div class="form-group">
			    <label for="m-menu_order">菜单顺序：</label>
			    <input type="text" class="form-control" id="m-menu_order" name="menuOrder" placeholder="菜单链接">
			  </div>
			  <div class="form-group">
			    <label for="m-menu_hidden">开启菜单：</label>
			    <div class="checkbox">
				    <label>
				      <input  id="m-menu_hidden" type="checkbox" name="menuHiddenE" checked> 是否显示
				    </label>
			  	</div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal"> 取消 </button>
	        <button type="button" class="btn btn-primary" onclick="modifyMenu()"> 保存 </button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<div id="addPermission" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="menu-modal-title">添加权限</h4>
	      </div>
	      <div class="modal-body">
	        <form id="permission-form">
	          <input type="hidden" id="menuId-permission" name="menuId" />
	          
			  <div class="form-group">
			    <label for="permissionName">权限名称：</label>
			    <input type="text" class="form-control" id="permissionName" name="permissionName" placeholder="权限名称">
			  </div>
			  <div class="form-group">
			    <label for="permissionCode">权限：</label>
			    <select class="form-control" id="permissionCode" name="permissionCode">
			    	<option value="find">查询 </option>
			    	<option value="update">修改 </option>
			    </select>
			  </div>
			  <div class="form-group">
			    <label for="permissionDesc">权限描述：</label>
			    <input type="text" class="form-control" id=permissionDesc name="permissionDesc" placeholder="权限描述">
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal"> 取消 </button>
	        <button type="button" class="btn btn-primary" onclick="onlyoneclick(this)"> 保存 </button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script type="text/javascript">
		/* 查看菜单*/
		function readerMenu(data){
			$("#m-menu_id").val(data.id);
			$("#m-menu_code").val(data.original.menuCode);
			$("#m-menu_name").val(data.original.menuName);
			$("#m-menu_url").val(data.original.menuUrl);
			$("#m-menu_order").val(data.a_attr.order);
			if(data.menuHidden==1){
				$("#m-menu_hidden").attr("checked",'true');//全选  
			}else{
				$("#m-menu_hidden").attr("checked",'false');//全选  
			}
		}
		/* 实例化菜单树 */
		var tree = $("#menu-tree").jstree({  
		    core : {  
		        check_callback : true,  
		        data : {
		        	'url':"${pageContext.request.contextPath}/menu/menu/tree"
		        } 
		    },  
		    plugins : ["wholerow","contextmenu"],  
		    "contextmenu": {    
		            "items": {    
		                "create": null,    
		                "rename": null,    
		                "remove": null,    
		                "ccp": null,    
		                "add-same-clazz": {    
		                    "label": "添加同级菜单",    
		                    "action": function (obj) {  
		                        var inst = jQuery.jstree.reference(obj.reference);    
		                        var clickedNode = inst.get_node(obj.reference);   
		                        $("#menu-modal-title").text("添加同级菜单");
		                       	$("#menu_id").val(clickedNode.id);
		                       	$("#menu_way").val("same");
 								$("#addMenu").modal();
		                    }    
		                },    
		                "add-next-clazz": {    
		                    "label": "添加下级菜单",    
		                    "action": function (obj) {  
		                        var inst = jQuery.jstree.reference(obj.reference);    
		                        var clickedNode = inst.get_node(obj.reference); 
		                        var menuParent=clickedNode.menuParent;
		                        if(menuParent!="0"){
		                        	alertx("目前只支持二级菜单");
		                        	return;
		                        }
		                        $("#menu-modal-title").text("添加下级菜单");
		                        $("#menu_id").val(clickedNode.id);
		                       	$("#menu_way").val("next");
		                        $("#addMenu").modal();
		                    }    
		                },    
		                "modify-this": {    
		                    "label": "修改当前菜单",    
		                    "action": function (obj) {  
		                        var inst = jQuery.jstree.reference(obj.reference);    
		                        var clickedNode = inst.get_node(obj.reference); 
		                        console.log(JSON.stringify(clickedNode));
		                        $("#menu-modal-title").text("修改当前菜单");
		                        readerMenu(clickedNode);
		                        $("#modifyMenu").modal();
		                    }    
		                },"delete-this": {    
		                    "label": "删除当前菜单",    
		                    "action": function (obj) {  
		                        var inst = jQuery.jstree.reference(obj.reference);    
		                        var clickedNode = inst.get_node(obj.reference); 
		                        deleteMenu(clickedNode.id);
		                    }    
		                },"add-permission": {    
		                    "label": "添加权限",    
		                    "action": function (obj) {  
		                        var inst = jQuery.jstree.reference(obj.reference);    
		                        var clickedNode = inst.get_node(obj.reference); 
		                        $("#menuId-permission").val(clickedNode.id);
		                       	$("#addPermission").modal();
		                    }    
		                }          
		            }    
		        }   
		}).on("ready.jstree", function (e, data) {  
		  data.instance.open_all();  
		}).on('changed.jstree', function (e, data) {
		    var i, j, r = [];
		    for(i = 0, j = data.selected.length; i < j; i++) {
		      r.push(data.instance.get_node(data.selected[i]).id);
		    }
		    $("#menuId-change").val(r[0]);
		    searchForMenu();
		});  
	
		/* 保存菜单 */
		function modifyMenu(){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/menu/modify",
				"dataType" : "json",
				"data" : $("#m-menu-form").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 关闭弹窗  */
					$("#modifyMenu").modal('hide');
					tree.jstree('refresh');
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		/* 保存菜单 */
		function saveMenu(){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/menu/save",
				"dataType" : "json",
				"data" : $("#menu-form").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 关闭弹窗  */
					$("#addMenu").modal('hide');
					tree.jstree('refresh');
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		
		/* 删除菜单 */
		function deleteMenu(menuId){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/menu/delete",
				"dataType" : "json",
				"data" : {"menuId":menuId},
				"success" : function(result) {
					alertx(result.message);
					tree.jstree('refresh');
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		/* 条件查询 */
		function searchForMenu(){
			showTable.fnDraw(false);
		}
		
		/* 删除权限 */
		function deletePermission(permissionId){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/permission/delete",
				"dataType" : "json",
				"data" : {'id':permissionId},
				"success" : function(result) {
					alertx(result.message);
					/* 重新查询 */
					searchForMenu();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		/*  添加用户 */
		function onlyoneclick(o){
			addPermission();
			o.off('onclick'); 	//限制onclick单次生效
		}

		function addPermission(){
			$.ajax({
				"type" : 'post',
				"url" : "${pageContext.request.contextPath}/permission/save",
				"dataType" : "json",
				"data" : $("#permission-form").serialize(),
				"success" : function(result) {
					alertx(result.message);
					/* 关闭弹窗  */
					$('#addPermission').modal('hide');
					/* 重新查询 */
					searchForMenu();
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
		
		/* 加载权限信息 */
		showTable = $("#showTable").dataTable({
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
					"mData" : "permissionCode",'sClass':'center'
				}, {
					"mData" : "permissionDesc",'sClass':'center'
				}
			],
			"columnDefs": [
			               {
			                 "targets": [3],
			                 "data": "id",
			                 "render": function(data, type, full) {
			                	 var options = "";
			                	 options += "<button type='button' class='btn btn-danger' onclick='deletePermission("+data+")'><i class='glyphicon glyphicon-remove'></i> 删除</button> &nbsp;";
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
						'menuId':$("#menuId-change").val(),
					},
					"success" : function(resp) {
						fnCallback(resp);
					},"error" : function (result){
						alertx("发生异常,或没权限...");
					}
				});
			}
		});
		
	</script>			
</body>
</html>