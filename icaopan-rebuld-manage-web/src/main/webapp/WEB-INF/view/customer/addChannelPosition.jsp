<%@ page import="com.icaopan.admin.model.AdminUser"%>
<%@ page import="com.icaopan.admin.realm.LoginRealm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
<title>添加通道头寸</title>
<meta name="decorator" content="default" />
</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>添加通道头寸
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<form id="inputForm" class="form-horizontal">
					<c:if test="${admin == 'true'}">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="customerId">资金方</label>
							<div class="col-sm-4">
								<select class="form-control" name="customerId" id="customerId"
									onchange="if(this.value != '') setInitialInfo(this.options[this.selectedIndex].value);">
									<option value="">-请选择-</option> ${fns:getCustomers()}
								</select>
							</div>
						</div>
					</c:if>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="userId">用户</label>
						<div class="col-sm-4">
							<select class="form-control" name="userId" id="userId">
								<option value="">-请选择-</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="channelId">通道</label>
						<div class="col-sm-4">
							<select class="form-control" name="channelId" id="channelId">
								<option value="">-请选择-</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="internalSecurityId">证券代码</label>
						<div class="col-sm-4">
							<input id="internalSecurityId" type="number" class="form-control"
								name="internalSecurityId" placeholder="股票代码"
								aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="amount">总头寸</label>
						<div class="col-sm-4">
							<input type="number" class="form-control" id="amount"
								name="amount" placeholder="总计头寸" aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="available">可用头寸</label>
						<div class="col-sm-4">
							<input type="number" class="form-control" id="available"
								name="available" placeholder="可用头寸"
								aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="costPrice">成本价</label>
						<div class="col-sm-4">
							<input id="costPrice" type="number" class="form-control"
								name="costPrice" placeholder="成本价"
								aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							调整记录是否在网站前台显示
						</div>
						<div class="col-sm-4">
							<input type="radio" value="0" name="isHidden" checked="checked" />显示
							&nbsp;&nbsp;<input type="radio" value="1" name="isHidden" />不显示
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 control-label">
							设置时间
						</div>
						<div class="col-sm-3">
							<input class="Wdate" style="height: 30px; width: 80%"
								readonly="readonly" name="createTime" type="text" id="start"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:''})" />
						</div>
						<div class="col-sm-3">
							<p style="color: red">
								<label id="cos"></label>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-6">
							<input type="submit" class="btn btn-danger btn-sm" value="提 交">
							<button type="button" class="btn btn-default btn-sm"
								data-dismiss="modal" onclick="history.go(-1)">返 回</button>

						</div>
					</div>
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
		$("#inputForm").validate({
			onsubmit:true,// 是否在提交时验证,默认也是true
			rules:{
				internalSecurityId:{
					required: true,
					rangelength:[2,50]
				},
				userId:{
					required: true,
				},
				customerId:{
					required: true,
				},
				channelId:{
					required: true,
				},
			},
			submitHandler: function(form) {
				var channelId = $('#channelId').val();
				var userId = $('#userId').val();
				var internalSecurityId = $('#internalSecurityId').val();
				$.ajax({
					"type" : 'post',
					"url" : "${ctx}/channel/verify",
					"dataType" : "json",
					"data" : {
						"userId":userId,
						"channelId":channelId,
						"internalSecurityId":internalSecurityId
					},
					"success" : function(result) {
						if(result.rescode == "success") {
							$.ajax({
								"type" : 'post',
								"url" : "${ctx}/channel/savePosition",
								"dataType" : "json",
								"data" : $("#inputForm").serialize(),
								"success" : function(result) {
                                    alertx(result.message,function(){
                                        if(result.rescode=='success'){
                                            window.history.go(-1);
                                        }
                                    });
									/* 清空form表单 */
									//$(".inputForm .form-control").val("");
									/*跳转*/

								},"error" : function (result){
									alertx("发生异常,或没权限...");
								}
							});
						}else{
							$('#tips').text(result.message);
						}
					},"error" : function (result){
						$('#tips').text(result.message);
					}
				});

			},
		});
	});
	if(!${admin == 'true'}){
		var customerId = "${customerId}";
		setInitialInfo(parseInt(customerId));
	}
	function setInitialInfo(customerId){
		setChannel(customerId);
		setUsers(customerId);
	}

	function setChannel(customerId){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx }/channel/findByCustomerId?customerId="+customerId,
			"dataType" : "json",
			"success" : function(result) {
				var arr = [];
				for(var i = 0 ; i < result.length ;i++){
					var data = {};
					data["txt"] = result[i].name;
					data["val"] = result[i].id;
					arr.push(data);
					data = null;
				}
				if (arr.length==1){
					setSelectOption('channelId', arr, '-请选择-',arr[0].val);
				}else {
					setSelectOption('channelId', arr, '-请选择-');
				}
			},"error" : function (result){
				alert("发生异常,或没权限...");
			}
		});
	}

	function setUsers(customerId){
		$.ajax({
			"type" : 'post',
			"url" : "${ctx }/frontUser/findByCustomerId?customerId="+customerId,
			"dataType" : "json",
			"success" : function(result) {
				var arr = [];
				for(var i = 0 ; i < result.length ;i++){
					var data = {};
					data["txt"] = result[i].userName;
					data["val"] = result[i].id;
					arr.push(data);
					data = null;
				}
				if (arr.length==1){
					setSelectOption('userId', arr, '-请选择-',arr[0].val);
				}else {
					setSelectOption('userId', arr, '-请选择-');
				}
			},"error" : function (result){
				alert("发生异常,或没权限...");
			}
		});
	}

	//联动菜单:选择券商，后面对应出现相应的营业部信息，券商服务器信息
	/*
	 * 说明：将指定下拉列表的选项值清空
	 * 作者：royleo.xyz
	 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须
	 */
	function removeOptions(selectObj){
		if (typeof selectObj != 'object')
		{
			selectObj = document.getElementById(selectObj);
		}
		for (var i=0; i < selectObj.options.length; i++)
		{
			// 移除当前选项
			selectObj.options[0] = null;
		}
	}
	/*
	 * 说明：设置传入的选项值到指定的下拉列表中
	 * 作者：royleo.xyz
	 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须
	 * @param {Array} optionList 选项值设置 格式：[{txt:'北京', val:'010'}, {txt:'上海', val:'020'}] ，必须
	 * @param {String} firstOption 第一个选项值，如：“请选择”，可选，值为空
	 * @param {String} selected 默认选中值，可选
	 */
	function setSelectOption(selectObj, optionList, firstOption, selected)
	{
		if (typeof selectObj != 'object')
		{
			selectObj = document.getElementById(selectObj);
		}
		removeOptions(selectObj); // 清空选项
		var start = 0;   // 选项计数
		var len = optionList.length;
		if (firstOption)  // 如果需要添加第一个选项
		{
			selectObj.options[0] = new Option(firstOption, '');
			start ++;   // 选项计数从 1 开始
		}
		for (var i=0; i < len; i++)
		{

			selectObj.options[start] = new Option(optionList[i].txt, optionList[i].val); // 设置 option
			if(selected == optionList[i].val)  // 选中项
			{
				selectObj.options[start].selected = true;
			}
			start ++;  // 计数加 1
		}

	}


</script>
</body>
</html>