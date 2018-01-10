<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<init:init-js/>
<div class="portlet box red">
	<div class="portlet-body">
		<div class="table-responsive">
				<div class="row">
                    <div class="col-md-2">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-start">开始日期</span> <input
                                type="text" class="form-control" name="startTime"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'date-search-end\')}'})"
                                id="date-search-start" placeholder="开始日期" />
                        </div>
                    </div>
                    <div class="col-md-2 col-lg2">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-end">结束日期</span> <input
                                type="text" class="form-control" name="endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'date-search-start\')}',maxDate:'2120-10-01'})"
                                id="date-search-end" placeholder="结束日期" />
                        </div>
                    </div>
				</div>
			<table:table headerNames="用户名,姓名,手机号,邮箱,备注,创建时间,更新时间,操作" columnNames="userName,realName,phone,email,notes,createTimeStr,modifyTimeStr" ajaxURI="${ctx}/user/find" id="showTable" searchInputNames="username,realname,phone" makeDefsHtmlFunc="columnDefs"></table:table>
	    </div>
	</div>
</div>	
<div class="portlet box red">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-coffee"></i>用户管理
				</div>
			</div>
			<div class="portlet-body">
				<div class="table-responsive">
					<table:table id="userTable" ajaxURI="${ctx}/user/find" columnNames="userName,realName" headerNames="用户名,姓名,操作" makeDefsHtmlFunc="defs"/>
				</div>
			</div>
			
</div>
<div>
	<select>
		${fns:getDicOptions("user_type")}
	</select>
</div>
<script>
function defs(rowdata){
	console.log(rowdata);
	return "<input type='button' class='btn-info' value='"+rowdata.userName+"'/>";
}
</script>
