<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>股票池管理</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>股票池管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="row">
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >股票名称</span>
						<input type="text" class="form-control" name="stockName" id="stockName-search" placeholder="股票名称" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" >股票代码</span>
						<input type="text" class="form-control" name="stockCode" id="stockCode-search" placeholder="股票代码" aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">类型</span>
						<select name="type" class="form-control">
							<option value="">请选择</option>
							<option value="0">禁买股</option>
							<option value="1">中小板</option>
							<option value="2">创业板</option>
						</select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon1">资金方</span>
						<select class="form-control" name="customerId" id="customerId-search">
							<option value="">请选择</option>
							${fns:getCustomers()}
						</select>
					</div>
				</div>
				<div class="col-md-3">
					<button class="btn btn-primary" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
					<button id="delBatch" class="btn btn-primary" onclick="dels()">批量删除</button>
				</div>
			</div>
			<form id="deleteBatchForm" method="post">
				<table:table id="userTable" ajaxURI="${ctx}/pool/find"
							 columnNames="placeHolder,stockName,stockCode,typeStr"
							 headerNames="\<input type='checkbox' id='checkAll' onclick='selectAllChecxkBox(this)'\>,股票名称,股票代码,股票类型,资金方"
							 searchInputNames="stockCode,stockName,type,customerId"
							 makeDefsHtmlFunc="defs"
							 loadEnd="LoadEnd()"
				/>
			</form>
		</div>
	</div>
</div>
	<!-- 定义上传导入时的模态框 -->
	<div class="modal fade" id="modelForUpload">
		<div class="modal-dialog" style="padding-left:20px;text-align:center;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">导入数据</h4>
				</div>
				<div class="modal-body">
					<form id="importForm" method="post" action="${ctx}/pool/import" onsubmit="return uploadVerity();" enctype="multipart/form-data"
						  class="form-search"><br/>
						<div class="modal-body">
							<div class="form-group col-md-8 col-md-offset-3">
								<label class="col-sm-3 control-label">类型</label>
								<div class="col-sm-5">
									<select style="width: 100px" class="clearInput" name="typeImport" id="typeImport">
										<option value="">请选择</option>
										<option value="0">禁买股</option>
										<option value="1">中小板</option>
										<option value="2">创业板</option>
									</select>
								</div>
							</div>
							<div class="form-group col-md-8 col-md-offset-2">
								<label id="alertLabel" class="badge-warning"></label>
							</div>
							<div class="form-group">
								<div class="input-group col-md-3 col-md-offset-4">
									<input id="uploadFile" name="excelFile" type="file" style="width:330px"/>
								</div>
								<input  id="btnImportSubmit" class="btn btn-primary" type="submit" value="导入"/>
							</div>
						</div>
						<br/>
						<a href="#" onclick="exportExcel('${ctx }/pool/import/template')">下载模板</a><br/>
						<label class="warning">导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！</label>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		var ids = new Array();
		function defs(rowdata){
			console.log(rowdata);
			ids.push(rowdata.id);
//			return rowdata.customerName;
			if (rowdata.customerName==null||rowdata.customerName==""){
                return ""+"<input name='delIds' class='box' type=\"checkbox\" value='"+rowdata.id+"'>";
            }else {
                return rowdata.customerName+"<input name='delIds' class='box' type=\"checkbox\" value='"+rowdata.id+"'>";
            }
		}

        function LoadEnd(){
            $(":checkbox[name=delIds]").each(function(){
                var tr = $(this).parents('tr');
                var td = tr.children("td:first");
                td.empty();
                $(this).prependTo(td);
//                $(this).parents('tr').children("td:first").append('<lable>'+i+'</lable>');
            });
        }


		$(document).ready(function() {
			$("#btnImport").click(function(){
				$("#modelForUpload").modal();
			});
			var message = "${message}";
			if(message!=null&&message!="success"){
				alertx(message);
			}
		});

		function uploadVerity(){
			var typeValue = $("#typeImport option:selected").val();
			var customerValue = $("#customerImportId option:selected").val();
			if(typeValue.length==0 || customerValue.length==0){
				document.getElementById("alertLabel").innerText="请选择股票类型和资金方";
				return false;
			}
			return true;
		}

		function selectAllChecxkBox(obj){
			if (obj.checked) {
				$("input[name='delIds']:checkbox").each(function() { //遍历所有的name为delIds的 checkbox
					$(this).attr("checked", true);
				})
			} else {   //反之 取消全选
				$("input[name='delIds']:checkbox").each(function() { //遍历所有的name为delIds的 checkbox
					$(this).attr("checked", false);
				})
			}
		}


		//并进行批量删除
		function dels(){
			var count = $('input:checked').length;
			if(count == 0){
				alertx("请选择需要删除的股票选项");
				return false;
			}
			$.ajax({
				"type" : 'post',
				"url" : "${ctx}/pool/delBatch",
				"dataType" : "json",
				"data" : $("#deleteBatchForm").serialize(),
				"success" : function(result) {
					document.getElementById("delBatch").disabled=true;
					alertx(result.message);
					/*刷新*/
					window.location.href="${ctx}/pool/pool";
				},"error" : function (result){
					alertx("发生异常,或没权限...");
				}
			});
		}
	</script>

</body>




