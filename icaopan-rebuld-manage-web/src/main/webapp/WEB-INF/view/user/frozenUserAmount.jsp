<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<html>
<head>
	<title>资金冻结管理</title>
	<meta name="decorator" content="default"/>
	<style>
		body {  padding : 10px ;  }
		#exTab1 .tab-content {  width: 900px;  padding : 5px 15px;  }
	</style>
	<init:init-js/>
</head>
<body>
	<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>资金冻结管理
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<div class="tab-content clearfix">
				<ul  class="nav nav-tabs">
					<li class="active">
						<a  href="#1a" data-toggle="tab">资金冻结</a>
					</li>
					<li><a href="#2a" id="2alist" data-toggle="tab">冻结明细</a>
					</li>
				</ul>
				<div class="tab-pane active" id="1a">
					<div class="form-group col-md-8 col-md-offset-4">
						<label class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-2"><input type="hidden" name="id" id="id" value="${user.id}"/>
							<input style="width: 100px" type="text" class="clearInput" value="${user.userName}" id="userName" name="amount"  disabled maxlength="100"/>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-4">
						<label class="col-sm-2 control-label">可用金额</label>
						<div class="col-sm-2">
							<input style="width: 100px" type="text" value="${user.available}" class="clearInput" id="available" name="available" disabled  htmlEscape="false" maxlength="100"/>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-4">
						<label class="col-sm-2 control-label">已冻结金额</label>
						<div class="col-sm-2">
							<input style="width: 100px" type="text" value="${user.frozen}" class="clearInput" id="frozen" name="frozen" disabled  htmlEscape="false" maxlength="100"/>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-4">
						<label class="col-sm-2 control-label">调整类型</label>
						<div class="col-sm-4">
							<select style="width: 100px" id="type" class="clearInput" name="type">
								<option value="froze">冻结</option>
								<option value="unfroze">解冻</option>
							</select>
							<font color="red">*</font>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-4">
						<label class="col-sm-2 control-label">调整金额</label>
						<div class="col-sm-4">
							<input style="width: 100px" type="number" class="clearInput" id="froze" name="froze"  htmlEscape="false" maxlength="100"/>
							<font color="red">*</font>
						</div>
					</div>
					<div class="form-group col-md-8 col-md-offset-4">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-4">
							<textarea name="remark" id="remark" style="width: 100px"></textarea>
							<font color="red">*</font>
							<%--<input style="width: 100px" type="text" class="clearInput" id="remark" name="remark"  htmlEscape="false" maxlength="100"/>--%>
						</div>
					</div>
					<div class="form-group col-md-7 col-md-offset-5">
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="history.go(-1)">返回</button>
						<input onclick="frozen()" class="btn btn-primary" type="submit" value="确定"/>
					</div>
				</div>
				<div class="tab-pane" id="2a">
					<div class="row"></div>
					<table:table id="userTable" ajaxURI="${ctx}/frontUser/findFrozenLog?userId=${user.id}"
								 columnNames="userName,frozen,typeStr,createTimeStr,remark"
								 headerNames="用户名,操作金额,操作类型,操作时间,备注"/>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">



function frozen(){
    var id = $("#id").val();
    var frozen = $("#frozen").val();
    var available = $("#available").val();
    var adjustType = $("#type").val();
    var frozing = $("#froze").val();
    var remark = $("#remark").val();
    if (remark == '' || remark == ' '){
        alertx('请输入备注信息！')
		return false;
	}
	if (adjustType == "froze" && parseFloat(frozing) > parseFloat(available)){
        alertx('冻结金额不可大于可用金额！')
		return false;
	}else if (adjustType == "unfroze" && parseFloat(frozing) > parseFloat(frozen)){
	    alertx('解冻金额不可大于已冻结金额！')
		return false;
	}
	$.ajax({
        dataType: "json",//返回json格式的数据
        url: "${ctx}/frontUser/updateFrozen",//要访问的地址
		async:false,
        data: {
			'frozen' : frozing,
            'type' : adjustType,
            'id' : id,
			'remark':remark
		},
        success: function (data) {//data为返回的数据，在这里做数据绑定
            if(data.rescode=="success"){
        		alertx("调整成功");
        		window.location.reload()
            }else{
            	alertx(data.message);
                window.location.reload()
            }
        }
    });
}


	$("#2alist").click(function () {
        reloadTableById("userTable")
    })

</script>
</body>




