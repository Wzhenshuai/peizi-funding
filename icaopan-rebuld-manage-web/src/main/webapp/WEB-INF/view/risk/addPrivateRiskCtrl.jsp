<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>添加个人风控账户</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<div class="portlet box red">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-coffee"></i>添加账户
		</div>
	</div>
	<div class="portlet-body">
		<div class="table-responsive">
			<form:form id="inputForm" class="form-horizontal">
				<div class=" panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">基本信息</h3>
					</div>
					<div class="panel-body">
						<div class="col-md-5" style="width:39%">
							<div class="form-group">
								<label class="col-sm-4 control-label">账户名称<span class="help-inline"><font color="white">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="nickName" id="nickName" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">登陆账户<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="accountNo" id="accountNo" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">交易账户<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="tradeAccount" id="tradeAccount" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">交易密码<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="jYPassWord" id="jYPassWord" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">通信密码<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="txPassWord" id="txPassWord" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
						</div>
						<div class="col-md-7">
							<div class="form-group">
								<label class="col-sm-3 control-label">深市股东代码<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="gddmSz" id="gddmSz" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">沪市股东代码<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<input name="gddmSh" id="gddmSh" value="" class="form-control bg-success" maxlength="50" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">券商<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<select class="form-control" name="brokerName" id="brokerName" onchange="if(this.value != '') setInfo(this.options[this.selectedIndex].value);">
										<option value="">-请选择-</option>
											${fns:getBrokers()}
									</select>
								</div>
							</div>
							<input type="text" name="serverId" id="serverId" style="display:none"/>
							<div class="form-group">
								<label class="col-sm-3 control-label">营业部<span class="help-inline"><font color="red">*</font> </span>:</label>
								<div class="col-sm-4">
									<select class="form-control" name="yybId" id="yybId">
										<option value="">-请选择-</option>
									</select>
								</div>
							</div>
							<c:if test="${admin == true}">
								<div class="form-group">
									<label class="col-sm-3 control-label">管理员<span class="help-inline"><font color="red">*</font> </span>:</label>
									<div class="col-sm-4">
										<select class="form-control" name="customerId" id="customerId-search">
											<option value="">-请选择-</option>
												${fns:getAdminUsers()}
										</select>
									</div>
								</div>
							</c:if>
						</div>
					</div>

				</div>
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">风控信息设置</h3>
					</div>
					<div class="panel-body">
						<div class="col-md-5">
							<div class="form-group">
								<label class="col-sm-4 control-label">警戒线 <font color="red">*</font>:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="0.00" name="warnLine" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
						<div class="col-md-7">
							<div class="form-group">
								<label class="col-sm-3 control-label">平仓线 <font color="red">*</font>:</label>
								<div class="col-sm-4">
									<input type="number" step="0.01" value="0.00" name="openLine" class="form-control" htmlEscape="false" maxlength="100"/>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-5 control-label"></label>
					<div class="col-sm-6">
						<button type="button" onclick="verifyInput()" class="btn btn-info btn-sm">验证</button>
						<button type="submit" id="subBtn" disabled class="btn btn-danger btn-sm">提交</button>
						<button type="button" class="btn btn-default btn-sm" onclick="history.go(-1)">取消</button>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>
<script>

        /**
         * 保存用户信息
         * */
        $("#inputForm").validate({
            rules:{
                openLine:{
                    required: true,
                    min:0
                },
                nickName:{
                    required: true
                },
                tradeAccount:{
                    required: true,
                    min:0
                },
                accountNo:{
                    required: true,
                    min:0
                },
                txPassWord:{
                    min:0
                },
                jYPassWord:{
                    min:0
                },
                gddmSh:{
                    required: true,
                },
                gddmSz:{
                    required: true,
                },
                brokerName:{
                    required: true,
                    min:0
                },
                serverId:{
                    required: true,
                    min:0
                },
                yybId:{
                    required: true,
                    min:0
                }
            },
            messages: {
                openLine: "请输入平仓线",
                warnLine: "请输入警告线",
                yybId: "请输入营业部信息",
                nickName: "请输入用户名称",
                brokerName:"请输入券商信息",
                tradeAccount: "请输入交易账号(一般与登陆账号相同)",
                accountNo: "请输入登陆账号(一般与交易账号相同)",
                jYPassWord: {
                    minlength: "密码长度不能小于 6 个字符(可为空)",
                },
                gddmSh: "请输入深市股东交易代码",
                gddmSz: "请输入沪市股东交易代码",
            },
            submitHandler: function(form){
                $.ajax({
                    "type" : 'post',
                    "url" : "${ctx }/privateRiskCtrl/addRiskCtrlForm",
                    "dataType" : "json",
                    "data" : $("#inputForm").serialize(),
                    "success" : function(result) {
                        console.log(result)
                        alertx(result.message);
                        window.location.href="${ctx }/privateRiskCtrl/riskCtrl";
                    },"error" : function (result){
                        alert("发生异常,或没权限...");
                    }
                });
            }
        });


    function verifyInput() {
        var accountNo = $('#accountNo').val();
        var tradeAccount = $('#tradeAccount').val();
        var jYPassWord = $('#jYPassWord').val();
        var txPassWord = $('#txPassWord').val();
        var yybId = $('#yybId').val();
        var serverId = $('#serverId').val();
        var nickName = $('#nickName').val();
        var warnLine = $('#warnLine').val();
        var openLine = $('#openLine').val();
        var gddmSz = $('#gddmSz').val();
        var gddmSh = $('#gddmSh').val();
        if (accountNo==""||tradeAccount==""||jYPassWord==""||yybId==""||serverId==""||nickName==""||warnLine==""||openLine==""||gddmSh==""||gddmSz==""){
            alert("必填信息不可为空，请检查");
            return false;
        }
        $.ajax({
            "type" : 'post',
            "url": "${ctx }/privateRiskCtrl/validateInfo",
            "dataType" : "json",
            "data" : {
                "accountNo":accountNo,
                "tradeAccount":tradeAccount,
                "jYPassWord":jYPassWord,
                "txPassWord":txPassWord,
                "yybId":yybId,
                "serverId":serverId
            },
            "success" : function(result) {
                if (result.rescode=="success"){
                    $("#subBtn").removeAttr("disabled");
                    alert("验证通过,请提交");
                }else{
                    alert(result.message);
                }
            },"error" : function (result){
                alert("发生异常,或没权限...");
            }
        });
    }


    //根据券商编号查找券商服务器信息
    function setServerInfo(belongId){
        var servers = [];
        $.ajax({
            "type" : 'post',
            "url" : "${ctx }/privateRiskCtrl/getServerByBelongId?belongId="+belongId,
            "dataType" : "json",
            "success" : function(result) {
                servers = eval(result);
                $('#serverId').val(servers[0].id)
                console.log($('#serverId').val())

            },"error" : function (result){
                alert("发生异常,或没权限...");
            }
        });
    }

    function setYybInfo(belongId){
        var yybs = [] ;
        $.ajax({
            "type" : 'post',
            "url" : "${ctx }/privateRiskCtrl/getYybByBelongId?belongId="+belongId,
            "dataType" : "json",
            "success" : function(result) {
                yybs = eval(result);
                var arr = [];
                for(var i = 0 ; i < yybs.length ;i++){
                    var data = {};
                    data["txt"] = yybs[i].yybName;
                    data["val"] = yybs[i].id;
                    arr.push(data);
                    data = null;
                }
                if (arr.length==1){
                    setSelectOption('yybId', arr, '-请选择-',arr[0].val);
                }else {
                    setSelectOption('yybId', arr, '-请选择-');
                }
            },"error" : function (result){
                alert("发生异常,或没权限...");
            }
        });
    }


    function setInfo(belongId){
        setServerInfo(belongId);
        setYybInfo(belongId);
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