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
			<i class="fa fa-coffee"></i>通道持仓调整
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<form id="inputForm" modelAttribute="position" class="form-horizontal">
				<%--<div class="modal-body col-md-9 col-md-offset-2">--%>
					<input type="hidden" name="id" value="${position.id}"/>
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
						<div class="col-sm-3 control-label"><label>可用头寸</label></div>
						<div class="col-sm-4">
							<label class="control-label" id="vorigin">${position.available}</label>
						</div>
					</div><input type="hidden" name="amount" value="${position.amount}">
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label>操 作</label>
						</div>
							<div class="col-sm-4">
								<select class="form-control" id="vmark" name="vmark">
									<option value="+">增加</option>
									<option value="-">减少</option>
								</select>
							</div>
							<div class="col-sm-2">
								<p style="color: red"><label id="ava"></label></p>
							</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label>数 量</label>
						</div>
						<div class="col-sm-4">
							<input class="form-control" type="number" name="availableChanged" id="availableChanged" placeholder="数量" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label id="corigin">成本价</label>
						</div>
						<div class="col-sm-4">
							<input class="form-control" type="number" name="costPrice" value="${position.costPrice}" id="costPrice" placeholder="成本价" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label id="corigin">调整记录是否在网站前台显示</label>
						</div>
						<div class="col-sm-4">
							<input type="radio" value="0" name="isHidden" checked="checked"/>显示  &nbsp;&nbsp;<input type="radio" value="1" name="isHidden"/>不显示
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							<label>设置时间</label>
						</div>
						<div class="col-sm-4">
							<input class="Wdate" style="height: 30px; width: 80%" readonly="readonly" name="createTime"
								   type="text" id="start"
								   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:''})"/>
						</div>
						<div class="col-sm-2">
							<p style="color: red"><label id="cos"></label></p>
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
    $(function () {
        $("#start").val(CurentTime());
    });
    function CurentTime() {
        var now = new Date();

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒

        var clock = year + "-";

        if (month < 10)
            clock += "0";

        clock += month + "-";

        if (day < 10)
            clock += "0";

        clock += day + " ";

        if (hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;
        return (clock);
    }
	$(function(){
		$("#availableChanged").blur(function(){
			var origin = $("#vorigin").text();
			var mark = $("#vmark").val();
			var input = $("#availableChanged").val();
			var temp = (mark == "+"||mark==""||mark==null) ? new Number(origin) + new Number(input):new Number(origin) - new Number(input);
			if(temp<0){
				$("#ava").text("减配量不能小于可用头寸值");
				$("#btnSubmitOn").attr('disabled',"true");
			}else {
                $("#ava").text("");
                $("#btnSubmitOn").removeAttr("disabled");
			}
			if(mark==null || mark.length==0){
				$("#vmark").val("+");
			}
		});
        $("#vmark").blur(function(){
            var input = $("#availableChanged").val();
            if (input==null||input=="") return false;
            var origin = $("#vorigin").text();
            var mark = $("#vmark").val();
            var temp = (mark == "+"||mark==""||mark==null) ? new Number(origin) + new Number(input):new Number(origin) - new Number(input);
            if(temp<0){
                $("#ava").text("减配量不能小于可用头寸值");
                $("#btnSubmitOn").attr('disabled',"true");
            }else {
                $("#ava").text("");
                $("#btnSubmitOn").removeAttr("disabled");
            }
            if(mark==null || mark.length==0){
                $("#vmark").val("+");
            }
        });
	});
	
	$(function(){
		$("#inputForm").validate({
			onsubmit:true,// 是否在提交时验证,默认也是true
			rules:{
				availableChanged:{
					required: true
				},
				costPrice:{
					required:true
				}

			},
			submitHandler: function(form) {
                var origin = $("#vorigin").text();
                var mark = $("#vmark").val();
                var input = $("#availableChanged").val();
                var temp = (mark == "+"||mark==""||mark==null) ? new Number(origin) + new Number(input):new Number(origin) - new Number(input);
                if(temp<0){
                    $("#ava").text("减配量不能小于可用头寸值");
                    $("#btnSubmitOn").attr('disabled',"true");
                    return false;
                }else {
                    $("#ava").text("");
                    $("#btnSubmitOn").removeAttr("disabled");
                }
                if(mark==null || mark.length==0){
                    $("#vmark").val("+");
                }
                $("#btnSubmitOn").attr('disabled',"true");
                $.ajax({
                    "type" : 'post',
                    "url" : "${ctx}/position/updateChannelPosition",
                    "dataType" : "json",
                    "data" : $("#inputForm").serialize(),
                    "success" : function(result) {
                        alertx(result.message);
                        if(result.rescode=="success"){
							history.go(-1)
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