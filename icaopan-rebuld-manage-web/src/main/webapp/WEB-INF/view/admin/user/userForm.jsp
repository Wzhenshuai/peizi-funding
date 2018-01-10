<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<script type="text/javascript">
		$(function(){
			$("#updateForm").validate({
                rules:{
                    userName:{
                        required: true
                    },
                    realName:{
                        required: true,
                        rangelength:[2,10]
                    },
                    passWord:{
                        required: true,
                        rangelength:[6,16]
                    }
                },
                submitHandler: function(form){
                    $.ajax({
                        "type" : 'post',
                        "url" : "${ctx}/user/update",
                        "dataType" : "json",
                        "data" : $("#updateForm").serialize(),
                        "success" : function(result) {
                            alertx(result.message,function(){
                                if(result.rescode=='success'){
                                    history.go(-1);
                                }
                            });
                        },"error" : function (result){
                            alertx("发生异常,或没权限...");
                        }
                    });
                }
			});
            $.validator.addMethod("checkPhone",function(value,element,params){
                var phone = /^1[3,5,7,8]\d{9}$/;
                return this.optional(element)||(phone.test(value));
            },"*请输入正确的手机号！");
        });
	</script>
	</head>
	<body>	
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>系统用户<c:choose>
													   <c:when test="${empty adminUser.id }">  
													       添加
													   </c:when>
													   <c:otherwise> 
													   	编辑
													   </c:otherwise>
													</c:choose>
			</div>
		</div>
		<div class="portlet-body">
			<form id="updateForm" class="form-horizontal">
			    <input type="hidden" name="id" value= "${adminUser.id }"/>
			    <div class="form-group">
      			  <label class="col-sm-2 control-label">用户名</label>
      			  <div class="col-sm-6">
				  	<input type="text" class="form-control" name="userName" id="userName" placeholder="用户名" value="${adminUser.userName }"<c:if test="${not empty adminUser }"> readonly="readonly"</c:if>/>
      			  </div><span style="color:red;">*</span>
				</div>
				<c:if test="${empty adminUser.id }">
					<div class="form-group">
	      			  <label class="col-sm-2 control-label">密码</label>
	      			  <div class="col-sm-6">
					  	<input type="password" class="form-control" name="passWord" id="passWord" placeholder="密码"/>
	      			  </div><span style="color:red;">*</span>
					</div>
				</c:if>
				<div class="form-group">
				  <label class="col-sm-2 control-label">姓名</label>
      			  <div class="col-sm-6">
      			  	<input type="text" class="form-control" name="realName" id="realName" placeholder="姓名" value="${adminUser.realName }" />
      			  </div><span style="color:red;">*</span>
				</div>
				<div class="form-group">
				  <label class="col-sm-2 control-label">用户状态</label>
      			  <div class="col-sm-6">
	      			  <select name="status" id="status" class="form-control" value="${adminUser.status }" _value="${adminUser.status }">
					  	<option value="0">正常</option>
					  	<option value="1">锁定</option>
					  </select>
      			  </div><span style="color:red;">*</span>
				</div>
				<div class="form-group">
				  <label class="col-sm-2 control-label">资金方</label>
      			  <div class="col-sm-6">
					  <select name="customerId" id="customerId" class="form-control" value="${adminUser.customerId }" _value="${adminUser.customerId }" <c:if test="${not empty adminUser }"> disabled</c:if> title="请选择资金方方" required>
					  	<option value="">请选择</option>
					  	${fns:getCustomers()}
					</select>
      			  </div><span style="color:red;">*</span>
				</div>
				<div class="form-group">
				  <label class="col-sm-2 control-label">手机号</label>
      			  <div class="col-sm-6">
      			  	<input type="tel" class="form-control" name="phone"  id="phone" placeholder="手机号" value="${adminUser.phone }">
      			  </div>
				</div>
				<div class="form-group">
				  <label class="col-sm-2 control-label">备注</label>
      			  <div class="col-sm-6">
				  	<input type="text" class="form-control" name="notes" id="notes" placeholder="备注" value="${adminUser.notes }">
      			  </div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-6">
						<button type="submit" class="btn btn-danger btn-sm">确认</button>
						<button type="button" class="btn btn-default btn-sm" onclick="history.go(-1)">取消</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	</body>
</html>
