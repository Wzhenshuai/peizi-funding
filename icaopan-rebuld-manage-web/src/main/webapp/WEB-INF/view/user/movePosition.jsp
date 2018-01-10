<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>通道持仓调整</title>
	<meta name="decorator" content="default"/>
	<init:init-js/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>通道持仓转移
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<form id="inputForm" modelAttribute="position" class="form-horizontal">
				<%--<div class="modal-body col-md-9 col-md-offset-2">--%>
					<input type="hidden" name="id" value="${position.id}"/>
					<input type="hidden" name="userId" value="${position.userId}"/>
					<input type="hidden" name="currentId" id="currentId" value="${position.channelId}"/>
					<input type="hidden" name="internalSecurityId" value="${position.internalSecurityId}"/>
					<div class="form-group">
						<label class="col-sm-3 control-label">证券代码</label>
						<div class="col-sm-4">
							<label class="control-label">${position.internalSecurityId}</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label"><label>用     户</label></div>
						<div class="col-sm-4">
							<label class="control-label">${position.userName}</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label"><label>通道名称</label></div>
						<div class="col-sm-4">
							<label class="control-label">${position.channelName}</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label"><label>目标通道</label></div>
						<div class="col-sm-4">
							<select class="form-control" name="targetId" id="targetId-search">

							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label"><label>可用头寸</label></div>
						<div class="col-sm-4">
							<label class="control-label" id="available">${position.available}</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label>转让数量</label>
						</div>
						<div class="col-sm-4">
							<input class="form-control" type="number" name="moveAmount" id="moveAmount" placeholder="数量" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label id="corigin">成本价</label>
						</div>
						<div class="col-sm-4">
							<label class="control-label">${position.costPrice}</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label>调整记录是否在网站前台显示</label>
						</div>
						<div class="col-sm-4">
							<input type="radio" value="0" name="isHiddenRecord" checked="checked"/>显示  &nbsp;&nbsp;<input type="radio" value="1" name="isHiddenRecord"/>不显示
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
						</div>
						<div class="col-sm-2">
							<p style="color: red"><label id="ava"></label></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-5 col-md-offset-3">
							<input type="submit" id="btnSubmitOn" class="btn btn-danger btn-sm" value="提 交">
							<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="history.go(-1)">返 回</button>
						</div>
					</div>
				<%--</div>--%>
			</form>
		</div>
	</div>
</div>
<script>

    window.onload=getTargetChannel();
    
    function getTargetChannel() {
        var iHtml = "<option value=''>请选择</option>";
        var customerId = ${position.customerId};
        var cID = ${position.channelId}
        $.ajax({
            "type" : 'post',
            "url" : "${ctx}/channel/findByCustomerId",
            "dataType" : "json",
            "data" : {
                "customerId":customerId
            },
            "success" : function(result) {
                var channels = eval(result);
                for (var i = 0 ; i < channels.length ; i ++){
                    var channel = channels[i]
					if (cID == channel.id) continue
                    iHtml += "<option value='"+channel.id+"'>"+channel.name+"</option>"
				}
				document.getElementById("targetId-search").innerHTML=iHtml;
            },"error" : function (result){
                alertx("发生异常,或没权限...");
            }
        });
    }
    
	$(function(){
		$("#moveAmount").blur(function(){
			var moveAmount = $("#moveAmount").val();
			var available = $("#available").val();
			if (parseFloat(moveAmount)>parseFloat(available)){
                $("#ava").text("迁移数量不能大于可用头寸值");
			}else {
                $("#ava").text("");
                $("#btnSubmitOn").removeAttr("disabled");
			}
		});
        $("#targetId-search").change(function(){
            var currentId = $("#currentId").val();
            var targetId = $("#targetId-search").val();
            if (targetId==currentId){
                $("#ava").text("不允许在本通道转移");
            }else {
                $("#ava").text("");
            }
        });
	});
	
	$(function(){
		$("#inputForm").validate({
			onsubmit:true,// 是否在提交时验证,默认也是true
			rules:{
                targetId:{
					required: true
				},
                moveAmount:{
					required:true
				}

			},
			submitHandler: function(form) {
                var moveAmount = $("#moveAmount").val();
                var available = $("#available").val();
                var currentId = $("#currentId").val();
                var targetId = $("#targetId-search").val();
                if (targetId==currentId){
                    $("#ava").text("不允许在本通道转移");
                    return false;
				}
                var temp = parseFloat(available)-parseFloat(moveAmount);
                if (parseFloat(moveAmount)==parseFloat(0)){
                    $("#ava").text("迁移数量为0");
                    $("#btnSubmitOn").attr('disabled',"true");
                    return false;
				}
                if(temp<0){
                    $("#ava").text("迁移数量不能大于可用头寸值");
                    $("#btnSubmitOn").attr('disabled',"true");
                    return false;
                }else {
                    $("#ava").text("");
                    $("#btnSubmitOn").removeAttr("disabled");
                }
                $("#btnSubmitOn").attr('disabled',"true");
                $.ajax({
                    "type" : 'post',
                    "url" : "${ctx}/position/moveStock",
                    "dataType" : "json",
                    "data" : $("#inputForm").serialize(),
                    "success" : function(result) {
                        alertx(result.message);
                        if(result.rescode=="success"){
                            sleep(1000)
							history.go(-1);
						}
                    },"error" : function (result){
                        alertx("发生异常,或没权限...");
                    }
                });
			},
		});
	});
</script>
</body>
</html>