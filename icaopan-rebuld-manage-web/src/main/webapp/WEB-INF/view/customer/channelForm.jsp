<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
	<meta name="decorator" content="default"/>
	<init:init-js/>
	<script type="text/javascript">
        var isCorrectName = false;
        $(function () {
            //失去焦点时触发的验证事件
            $('#code').blur(function ()
            {
                var code = $("#code").val();
                code = code.replace(/ /g,'');
                if(code==null||code.length==0||code==""){
                    return false;
                }
                $.ajax({
                    "type" : 'post',
                    "url" : "${ctx}/channel/verifyCode",
                    "dataType" : "json",
                    "data" : {"code": code},
                    "success" : function(result) {
                        $('#codeTips').text(result.message);
                        if(result.rescode=="success"){
                            isCorrectName = true;
                        }else{
                            isCorrectName = false;
                        }
                    },"error" : function (result){
                        $('#codeTips').text(result.message)
                    }
                });
            })
        });
        function saveMQ(){
            /*if(!isCorrectName){
                alertx("通道代码不可用");
                return false;
            }*/
            var code = $("#code").val();
            var channelType = $("#channelType").val();
            console.log(code);
            if (code == ""){
                alertx("通道代码不能为空");
				return;
			}if (channelType == ""){
                alertx("必须选择通道类型");
                return;
            }
            confirmx("确定为账号：" + code + "新增MQ相关的信息吗？", "addMQToAction(" + '$("#code").val()' + "," + channelType + ")");
		}
        function addMQToAction(code, channelType) {
            console.log(code)
            ajaxRequestPost("${ctx}/channel/addMQ", {code: code, type: channelType}, function (result) {
                alertx(result.message);
            });
        }

        $(function(){
			$("select[name='customerId']").change(function(){
				var cId=$(this).val();
				if(cId==""){
					$("input[name='minCost']").removeAttr("readonly");
					$("#minCostTip").html("");
					return;
				}
				ajaxRequestPost("${ctx}/customer/getCustomerById",{id:cId},function(result){
					console.log(result);
					if(result.costPattern=="1"){
						$("input[name='minCost']").attr("readonly","readonly").val("0");
						$("#minCostTip").html("低消模式选择为按公司收取，通道最低收费不可设置");
					}else{
						$("input[name='minCost']").removeAttr("readonly");
						$("#minCostTip").html("");
					}
				});
			});
			$("#addForm").validate({ 
				rules:{
					code:{
						required: true,
                        number:true,
						rangelength:[2,50]
				    },
					name:{
						required: true,
						rangelength:[2,50]
				    },
                    chanelType:{
						required:true,
					},
				    minCost:{
				    	required: true,
				    	number:true,
				    	min:0
				    },
				    tatio:{
						required: true,
						number:true,
						max:1,
						min:0
				    },
                    notes:{
                        rangelength:[0,100]
					}
				},
				submitHandler: function(form){
					var channelId = $("input[id='channelId']").val();
                    if(!isCorrectName && channelId === ''){
                        alertx("通道代码不可用");
                        return false;
                    }
                    var autoFill=$("select[name='autoFill']").val();
                    if(autoFill=='true'){
                    	if(confirm("该通道将开启自动成交功能，请确认是否开启？")){
                    		$.ajax({
        						"type" : 'post',
        						"url" : "${ctx}/channel/save",
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
                    }else{
                    	$.ajax({
    						"type" : 'post',
    						"url" : "${ctx}/channel/save",
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
				}
			});
	});
	</script>
	</head>
	<body>
		<div class="portlet box red">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-coffee"></i>交易通道<c:choose>
													   <c:when test="${empty channel.id }">  
													       添加
													   </c:when>
													   <c:otherwise> 
													   	编辑
													   </c:otherwise>
													</c:choose>
				</div>
			</div>
			<div class="portlet-body">
				<form id="addForm" class="form-horizontal">
				    <input type="hidden" id = "channelId" name="id" value= "${channel.id }"/>
					<div class="form-group">
						<label class="col-sm-2 control-label">通道代码</label>
						<div class="col-sm-6">
							<input name="code" id = "code"  type="text" value="${channel.code }"  class="form-control" <c:if test="${not empty channel }"> readonly="readonly"</c:if>/>
						</div>
						<div class="col-sm-4 control-label"><font color="blue"><label id="codeTips"></label></font></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">通道名称</label>
						<div class="col-sm-6">
							<input name="name" value="${channel.name }"  class="form-control" <c:if test="${not empty channel }"> readonly="readonly"</c:if> >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">通道类型</label>
						<div class="col-sm-6">
							<select class="form-control" id="channelType" name="channelType" _value="${channel.channelType }"  <c:if test="${not empty channel }"> disabled="disabled"</c:if> required>
								<option value="">请选择</option>
								${fns:getDicOptions("channel_type")}
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">资金方</label>
						<div class="col-sm-6">
							<select name="customerId" value="${channel.customerId }" _value="${channel.customerId }" class="form-control" <c:if test="${not empty channel }"> disabled="disabled"</c:if> required>
								<option value="">请选择</option>
								${fns:getCustomers()}
							</select>
						</div>
					</div>
					<c:if test="${not empty channel }">
						<div class="form-group">
							<label class="col-sm-2 control-label">可用余额</label>
							<div class="col-sm-6">
								<input name="cashAvailable" value="${channel.cashAvailable }" class="form-control"  class="required"/>
							</div>
						</div>
					</c:if>
					<div class="form-group">
						<label class="col-sm-2 control-label">最低消费</label>
						<div class="col-sm-6">
							<input name="minCost" value="${channel.minCost }" class="form-control"  class="required"/>
						</div>
						<label class="col-sm-4" style="color:red;font-size:10px;line-height: 30px" id="minCostTip"></label>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">收费比例</label>
						<div class="col-sm-6">
							<input name="tatio" value="${channel.tatio }" class="form-control"  maxlength="50" class="required"/>
						</div>
					</div>
					<div class="form-group">
                        <label class="col-sm-2 control-label">券商佣金比例</label>
                        <div class="col-sm-6">
                            <input name="tradeCommissionRate" value="${channel.tradeCommissionRate }"
                                   class="form-control"
                                   maxlength="50"/>
                        </div>
                    </div>
					<div class="form-group">
						<label class="col-sm-2 control-label">佣金低消</label>
						<div class="col-sm-6">
							<input name="tradeMinCost" value="${channel.tradeMinCost }" class="form-control"
								   class="required"/>
						</div>
					</div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-6">
                            <select name="isAvailable" value="${channel.isAvailable }" _value="${channel.isAvailable }" class="form-control" required>
                                <option value="">请选择</option>
                                ${fns:getDicOptions("available_type")}
                            </select>
                        </div>
                    </div>
					<div class="form-group">
						<label class="col-sm-2 control-label">是否自动成交</label>
						<div class="col-sm-6">
							<select name="autoFill" value="${channel.autoFill }" _value="${channel.autoFill }" class="form-control" required>
								<option value="false">否</option>
								<option value="true">是</option>
							</select>
						</div>
						<div class="col-sm-3 alert-danger">注意：若开启自动成交，后台程序会扫描该通道未对接的委托记录，符合市场要求的则会以现价成交。</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-6">
							<input name="notes" value="${channel.notes }" class="form-control"  maxlength="50"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-6">
							<button type="button" id="addmq" onclick="saveMQ()" class="btn btn-primary btn-sm" <c:if test="${not empty channel }"> style="display:none"</c:if>>创建MQ
							</button>
							<button type="submit" id="queren" class="btn btn-danger btn-sm" ><c:choose>
								<c:when test="${empty channel.id }">
									创建通道
								</c:when>
								<c:otherwise>
									保存
								</c:otherwise>
							</c:choose></button>
							<button type="button" class="btn btn-default btn-sm" onclick="history.go(-1)">取消</button>

						</div>
					</div>
			    </form>
			</div>
		</div>
	</body>
</html>

