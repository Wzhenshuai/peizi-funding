var Configs = {
	// 股票行情刷新次数
	market_data_fresh_counts : 0,
	// 股票行情刷新间隔
	market_data_fresh_interval : 3000,
	market_data_interval : 0,
	// 撤单列表刷新
	cancel_placement_list_fresh_interval:10000,
	// 今日委托列表刷新
	placement_list_fresh_interval:10000,
	// 持仓刷新间隔
	hold_stocks_List_fresh_interval:10000,
	// 股票账户信息刷新间隔
	stock_balance_fresh_interval:10000,
	// 查询行情地址
	//market_data_server:"http://192.168.1.62:8080/marketdata/openapi/queryMarketDataBySecurityCode",
	market_data_server:"http://120.132.58.163/openapi/queryMarketDataBySecurityCode",
};
function getCurrentPage(tableId){
	var page=$("#"+tableId).next("div.digg").find("span.current").html();
	if(page!=undefined){
		return page;
	}
	return 1;
}
/** ******************注册相关************************** */
function empty(ele,type){
	var temp=$("#"+ele).val();
	$("#"+ele).removeClass('color-h');
	if(type=='mobile'){
		if(temp=='手机号'||temp==''||temp==null){
			$("#"+ele).val('');
		}
	}else if(type=='sourcecode'){
		if(temp=='填邀请码，送好礼'||temp==''||temp==null){
			$("#"+ele).val('');
		}
	}else if(type=='realname'){
		if(temp=='请输入真实姓名'||temp==''||temp==null){
			$("#"+ele).val('');
		}
	}else if(type=='idnumber'){
		if(temp=='请输入身份证号'||temp==''||temp==null){
			$("#"+ele).val('');
		}
	}
}
function onFocus(ele){
	$("#"+ele).html('');
}
function checkUsername() {
	var username = $.trim($("#username").val());
	var userValid = valid.isUsernameValid(username);
	if (username.length == 0 || username == '4-16位字母、数字、下划线或中文') {
		$("#username_tips").html("请输入用户名");
		return false;
	}
	if (!userValid.valid) {
		$("#username_tips").html(userValid.msg);
		return false;
	}
	var nameResult = false;
	$.ajax({
		url : systemConfig.path + "/user/CheckUserName.ajax",
		type : 'post',
		dataType : "json",
		data : {"userName" : username},
		async : false,
		success : function(res) {
			if (res.checkUserName) {
				$("#username_tips").hide();
				nameResult = true;
			} else {
				$("#username_tips").html("用户名已存在");
				nameResult = false;
			}
		}
	});
	return nameResult;
};
function checkMobile() {
	var mobile = $.trim($("#mobile").val());
	if (mobile == "" || mobile == "请输入你的手机号") {
		$("#mobile_tips").html("请输入你的手机号");
		return false;
	}
	if (!valid.isMobile(mobile)) {
		$("#mobile_tips").html("手机号不正确");
		return false;
	}
	var mobileResult = false;
	$.ajax({
		url : systemConfig.path + "/user/CheckMobile.ajax",
		type : 'post',
		dataType : "json",
		data : {"mobile" : mobile},
		async : false,
		success : function(res) {
			if (res.checkMobile) {
				$("#mobile_tips").html("");
				mobileResult = true;
			} else {
				$("#mobile_tips").html("手机已经被注册");
				mobileResult = false;
			}
		}
	});
	return mobileResult;
};
function checkPass() {
	var password = $.trim($("#password").val());
	var strExp=/^[a-zA-Z0-9]{6,16}$/;
	if (password == "" || password == null) {
		$("#password_tips").html("密码不能为空");
		return false;
	}
	if(password.length<6||password.length>16){
		$("#password_tips").html("密码只能6-16位数字和字母混合组成");
		return false;
	}
	if(!strExp.test(password)){
		$("#password_tips").html("密码只能由数字和字母组成");
		return false;
	}
	$("#password_tips").html("");
	return true;
};
function checkRePass() {
	var password = $.trim($("#password").val());
	var repassword = $.trim($("#repassword").val());
	var strExp=/^(?!\d+$)(?![A-Za-z]+$)[a-zA-Z0-9]{6,}$/;
	if (repassword == "" || repassword == null ) {
		$("#repassword_tips").html("确认密码不能为空");
		return false;
	}
	if (password != repassword) {
		$("#repassword_tips").html("两次密码不相同");
		return false;
	}
	$("#repassword_tips").html("");
	return true;
};
// 发送手机验证码
function sendMobileValidateCode() {
	var mobile = $.trim($("#mobile").val());
	if (mobile == "" || mobile == "请输入你的手机号") {
		$("#mobile_tips").html("手机号不能为空");
		return false;
	}
	if (!valid.isMobile(mobile)) {
		$("#mobile_tips").html("手机号不正确");
		return false;
	}
	if (!checkMobile()) {
		$("#mobile_tips").html("手机号被注册，请输入其他手机号");
		return false;
	}
	$.post(systemConfig.path + "/user/SendMobileValidateCode.ajax", {
		"mobile" : mobile
	}, function(res) {
		changeButtonStyleForSendCodeToWait("codeBtn");
		if (res.resCode == "not") {
			$("#code_tips").html("手机号不能为空");
			return false;
		} else if (res.resCode == "invalid") {
			$("#code_tips").html("发送太频繁");
			return false;
		} else if (res.resCode == "fail") {
			$("#code_tips").html("发送失败，请重新发送");
			return false;
		} else if(res.resCode == "many"){
			$("#code_tips").html("调用次数过多,请联系客服处理");
			return false;
		}else if (res.resCode == "success") {
			$("#code_tips").html("发送成功");
			return true;
		}
	}, "json");
}
// 获取验证码超时
function changeButtonStyleForSendCodeToWait(btnId) {
	/*
	 * if (inter) { clearInterval(inter); }
	 */
	var txt = "秒";
	var startData = 60;
	$("#" + btnId).attr("disabled", "disabled");
	inter = setInterval(function() {
		if (startData <= 0) {
			$("#" + btnId).removeAttr("disabled");
			$("#" + btnId).val("获取验证码");
			clearInterval(inter);
		} else {
			$("#" + btnId).attr("disabled", "disabled");
			startData--;
			$("#" + btnId).val(startData + txt);
		}
	}, 1000);
}
function checkCode() {
	var code = $.trim($("#code").val());
	var mobile = $.trim($("#mobile").val());
	if (code == "" || code == "请输入你的验证码") {
		$("#code_tips").html("验证码不能为空");
		return false;
	}
	if (!valid.isMobile(mobile)) {
		$("#mobile_tips").html("请输入正确的手机号码");
		return false;
	}
	if (!checkMobile()) {
		$("#mobile_tips").html("手机号被注册，请输入其他手机号");
		return false;
	}
	var checkCode=false;
	ajaxReuqestSynchronize(systemConfig.path + "/user/VerifyMobile.ajax",{"mobile" : mobile,"msgCode" : code},
		function(res) {
			if(res.resCode == "mobile_empty"){
				$("#code_tips").html("手机号不能为空");
				checkCode=false;
			}else if(res.resCode == "code_empty"){
				$("#code_tips").html("验证码不能为空");
				checkCode=false;
			}else if(res.resCode == "verifyCode_empty"){
				$("#code_tips").html("请先获取验证码");
				checkCode=false;
			}else if (res.resCode == "timeout") {
				$("#code_tips").html("验证码超时，请重新获取验证码");
				checkCode=false;
			}else if (res.resCode == "invalidate") {
				$("#code_tips").html("验证码验证失败");
				checkCode=false;
			} else if (res.resCode == "success") {
				$("#code_tips").html("");
				checkCode=true;
			}
		}
	);
	return checkCode;
};

/** ******************我要配资相关************************** */
function checkVal(obj){
    var val=obj.val();
    if(val==""){
        obj.val("1000-500000").css("color","#ccc");
    }else{
        val =val.replace(/[^\d]/g,'');
        obj.val(val);
        obj.css("color","#ff5858");
    }
}
var aviliableAmount = "1500000";
var isLockedByAviliableAmount = false;
var isLockedByXY = false;
function setaviliableAmount(){
	ajaxReuqestSynchronize(systemConfig.path+"/finance/getCurrentFinancing.ajax",{"principal":principal},function(r){
		//console.log(r);
		if(r.rescode == "success"){
			aviliableAmount = r.maxFinancingAmount;
			if(r.maxFinancingAmount == 0 || r.maxFinancingAmount == "0"){
				isLockedByAviliableAmount = true;
				$("#button2").attr("disabled" , true);
				$("#button2").css("background-image","url("+systemConfig.path+"/images/btn_wypz_dis.png)");
			}else {
				isLockedByAviliableAmount = false;
				if(!isLockedByXY){
					$("#button2").attr("disabled" , false);
					$("#button2").css("background-image","url("+systemConfig.path+"/images/wypz_20.png)");
				}
			}
			$('#used-rate').html(100-parseFloat(r.maxFinancingAmount)+'%');
			$('#unused-rate').html(parseFloat(r.maxFinancingAmount)+'%');
			$('#progress-bar span').animate({'width':(100-parseFloat(r.maxFinancingAmount))*5.72});
			//$("#aviliableAmount").html("当日剩余可配额度为 ： <font color='red'>" +(parseFloat(r.maxFinancingAmount)) + "%</font>");
		}else {
			$(".wypz-h2").css("display","none");
		}
    });
}


var principal=0;
var alertLineRate=0;
var wrongLineRate=0;
/** 投资本金输入变化事件 */
function principalInputChange(isPrincipalInputChange){
    var principal=$("#principalInput").val();
	var pzbs=$("#pzbs").val();
	pzbs=formateMoneyToDecimal(pzbs);
    if($.trim(principal)==""){
        principal=0;
    }else{
        // 本金
        principal=parseFloat(formateMoneyToDecimal(principal));
        if(principal>=finalAmount){
            principal=finalAmount;
        }
        $("#principalInput").val(DM.utils.farmatMoney(principal,0));
    }
    var modeType="fix_mode";
    if(modeType=="fix_mode"){
        // 配资金额
        var financeAmount=principal*pzbs;
        // 总操盘资金
        var stockValueEm=principal*(pzbs+1);
        stockValueEm=parseFloat(formateMoneyToDecimal(stockValueEm));
        if(stockValueEm>=finalAmount*4){
        	stockValueEm=finalAmount*4;
        }
        $("#stockValueEm").html(DM.utils.farmatMoney(stockValueEm,0));
        $("#zcpzj").text(DM.utils.farmatMoney(stockValueEm,0));
        $("#thisfinanceAmount").val(financeAmount);
        // 警告线后
        var alertLineChange=financeAmount*alertLineRate;
        // 止损线后
        var wrongLineChange=financeAmount*wrongLineRate;
        // 显示到页面中
        $("#alertLineChange").html(DM.utils.farmatMoney(alertLineChange, 0));
        $("#wrongLineChange").html(DM.utils.farmatMoney(wrongLineChange, 0));
        // 利息改变
        var cycle=$("#cycle").val();
        var interest=DM.utils.calcInterest(financeAmount, pzbs,parseInt(cycle),modeType);
        $("#interest").html(interest+"<span>分（<font color='red'>优惠</font>）</span>");
        $("#interest_to_xy").html(interest);
    }
}
/** 进行融资操作 */
function goToFinance(){
    // 判断协议是否有勾选
    var b=$("input[name=agree]").prop("checked");
    if(b==false){
    	ShowDialogFail("请先阅读并同意融资协议")
        return;
    } 
    // 可用最大本金
    var availabelPrincipalAmount=formateMoneyToDecimal($("#availabelPrincipalAmount").val());
    var principal=$("#principalInput").val();
    var pzbs=$("#pzbs").val();
    // 本金
    principal=formateMoneyToDecimal(principal);
    pzbs=formateMoneyToDecimal(pzbs);
    // 配资金额
    var financeAmount=principal*pzbs;
    var checkmsg = "";
    ajaxReuqestSynchronize(systemConfig.path+"/finance/checkRule.ajax",{"principal":principal},function(r){
    	if(r.rescode == "login"){
    		checkmsg = "login";
    	}else{
    		if(r.msg != null && r.msg != ""){
        		checkmsg = r.msg;
        	}
    	}
    });
    if(checkmsg == "login"){
    	location.href=systemConfig.path+"/user/login.jsp";
    }
    if(checkmsg != ""){
    	ShowDialogFail(checkmsg, null);
    	return ;
    }
    //原来的配资校验
  /*if(principal>500000||principal<1000){
    	ShowDialogFail("保证金应在1千-50万元", null);
        return;
    }*/
    if(financeAmount==0){
    	ShowDialogFail("融资金额不能为零", null);
        return;
    }
    // 判断本金和配资金额是否是1000的整数倍
    if(!isNumberBy100(principal)){
    	ShowDialogFail("保证金须为100的整数倍", null);
        return;
    }
    var first_money = "1000-500000";
    var val = $("#principalInput").val();
	if ($.trim(val) == first_money) {
    	ShowDialogFail("保证金须为100的整数倍", null);
        return;
    }
    if(!isNumberBy100(financeAmount)){
    	ShowDialogFail("配资金额须为100的整数倍", null);
        return;
    } 
    if(!isFinanceAmountInLimits(principal,financeAmount)){
    	ShowDialogFail("配资金额须为投资本金的1-3倍", null);
        return;
    }
    // 总操盘资金
    var stockValue=principal+financeAmount;
    // 警戒线
    var alertLine=formateMoneyToDecimal($("#alertLine").html());
    // 止损线
    var wrongLine=formateMoneyToDecimal($("#wrongLine").html());
    var amountTemp=0;
    if((principal-availabelPrincipalAmount)>0){
        amountTemp=principal-availabelPrincipalAmount;
    }
    // 操盘金额后
    var stockValueChange=parseFloat(0);;
    // 警告线后
    var alertLineChange=parseFloat(0);; 
    // 止损线后
    var wrongLineChange=parseFloat(0);;
    var cycle=$("#cycle").val();
    var mode="fix_mode";
    var interest=DM.utils.calcInterest(financeAmount, parseInt(pzbs),parseInt(cycle),mode);
    var type=0;
    location.href=systemConfig.path+"/finance/ApplyFinancing.do?principal="+principal+"&financeAmount="+financeAmount+"&stockValue="+stockValue+"&cycle="+cycle+"&interest="+interest+"&type="+type+"&pzbs="+pzbs;
}
/**利息试算*/
function tryRepay(){
	var pzbs = $("#pzbs").val();
	pzbs = formateMoneyToDecimal(pzbs);
    var period=$("#cycle").val();
    var principal=formateMoneyToDecimal($("#principalInput").val());
    var interest=parseFloat($("#interest").html());
    if(principal==""||period==""||interest==""){
        return;
    }
    var financingAmount=principal*pzbs;
	$("#tryRepayTable1 tr:gt(0)").remove();
    $("#tryRepayTable1").css("display","block");
    $("#tryRepayTable2 tr:gt(0)").remove();
    $("#tryRepayTable2").css("display","block");
    $("#tryRepayTable3 tr:gt(0)").remove();
    $("#tryRepayTable3").css("display","block");
    ajaxReuqestAsync(systemConfig.path+"/finance/GetRepayPlantList.ajax",{"period":period,"financingAmount":parseFloat(financingAmount),"interest":interest,"pzbs":pzbs},function(r){
        var repayList=r.repayList;
        var trs1="<table width='250' border='0' cellspacing='0' cellpadding='0' id='tryRepayTable1' class='zhcc-table-2 right'> <tr> <th align='left'>还款时间</th></tr>";
        var trs2="<table width='200' border='0' cellspacing='0' cellpadding='0' id='tryRepayTable2' class='zhcc-table-2 left'>  <tr> <th align='left'>还款类型</th></tr>";
        var trs3="<table width='200' border='0' cellspacing='0' cellpadding='0' id='tryRepayTable2' class='zhcc-table-2 left'>  <tr> <th align='left'>还款金额</th></tr>";
        for(var i=0;i<repayList.length;i++){
            var repayPlan=repayList[i];
            var tr1 ="<tr><td>"+repayPlan.repayDate+"</td></tr>";
            var tr2 ="<tr><td>"+repayPlan.repayType+"</td></tr>";
            var tr3 ="<tr><td>"+DM.utils.farmatMoney(repayPlan.repayAmount, 2)+"元</td></tr>";
            trs1+=tr1;
            trs2+=tr2;
            trs3+=tr3;
        }
        trs1 += "</table>";
        trs2 += "</table>";
        trs3 += "</table>";
        $("#tryRepayTable1").html(trs1);
        $("#tryRepayTable2").html(trs2);
        $("#tryRepayTable3").html(trs3);
        changeTipsByMoney();
        $("#circles").text(period);
        $("#lixi").text(interest);
    });
}
/** 判断融资金额是否是本金的1-3倍 */
function isFinanceAmountInLimits(principal,financeAmount){
    var ts=financeAmount/principal;
    if(ts<1||ts>3){
        return false;
    }
    return true;
}
function backToUpdateFinancing() {
	var availabelPrincipalAmount = $("#availabelPrincipalAmount").val();
	var principal = $("#principal").val();
	var financeAmount = $("#financeAmount").val();
	var stockValue = $("#stockValue").val();
	var alertLine = $("#alertLine").val();
	var wrongLine = $("#wrongLine").val();
	var cycle = $("#cycle").val();
	var interest = $("#interest").val();
	var type = $("#type").val();
	var pzbs = $("#pzbs").val();
	location.href = systemConfig.path
			+ "/finance/UpdateFinancing.do?availabelPrincipalAmount="
			+ availabelPrincipalAmount + "&principal=" + principal
			+ "&financeAmount=" + financeAmount + "&stockValue="
			+ stockValue + "&stockValueChange=" + stockValueChange
			+ "&alertLine=" + alertLine + "&alertLineChange="
			+ alertLineChange + "&wrongLine=" + wrongLine
			+ "&wrongLineChange=" + wrongLineChange + "&cycle=" + cycle
			+ "&interest=" + interest + "&type=" + type + "&pzbs=" + pzbs;
}
function confirmFinancing() {
	var principal = $("#principal").val();
	var financeAmount = $("#financeAmount").val();
	var cycle = $("#cycle").val();
	var interest = $("#interest").val();
	var type = $("#type").val();
	var pzbs = $("#pzbs").val();
	var stockValue = $("#stockValue").val();
	ajaxReuqestAsync(systemConfig.path + "/finance/ApplyFinancingConfirm.do",{
		"financeAmount" : financeAmount,
		"principal" : principal,
		"cycle" : cycle,
		"interest" : interest,
		"type" : type
	},function(r) {
		if (r.result.resCode == 1) {//未登录
			var visitPath = systemConfig.path
				+ "/finance/ApplyFinancing.do?principal="
				+ principal + "&financeAmount=" + financeAmount
				+ "&cycle=" + cycle + "&interest=" + interest + "&type=" + type + "&pzbs=" + pzbs + "&stockValue=" + stockValue;
			openLoginDia(visitPath);
		} else if (r.result.resCode == 14) {
			new ConfirmDialog("提示","您尚未申请实名认证，请先实名认证。","确定",function() {
				location.href = systemConfig.path+ "/account/wdzh_aqzx_smrz.jsp";
			}, "取消",null).show();
		} else if (r.result.resCode == 15) {//投资金额 《１０００
			ShowDialogFail("保证金应在1千-"+(finalAmount/10000)+"万元", null);
	        return;
		} else if (r.result.resCode == 2) {//安全验证未通过
			ShowDialogFail("实名认证尚未通过，无法发起配资，请联系客服处理。",function() {
				location.href = systemConfig.path+ "/account/wdzh_aqzx_smrz.jsp";
			});
		} else if (r.result.resCode == 3) {//投资金额，融资金额不是1000的整数倍
			ShowDialogFail("投资本金和融资金额需为100的整数倍", null);
			return;
		} else if (r.result.resCode == 4) {//融资杠杆不在1-5倍这个范围内
			ShowDialogFail("融资金额需为投资本金的1-3倍", null);
			return;
		} else if (r.result.resCode == 5) {//融资杠杆不在1-5倍这个范围内
			ShowDialogFail("参数非法", null);
			return;
		} else if (r.result.resCode == 6) {
			ShowDialogFail("借款时长为1-6个月", null);
			return;
		} else if (r.result.resCode == 10) {//成功
			var fDetailId = r.result.fDetailId;
			location.href = systemConfig.path + "/finance/FinanceSuccess.do?fDetailId="+fDetailId;
		} else if (r.result.resCode == 11) {//余额不足
			new ConfirmDialog("提示","您的现金账户余额不足，还差<span class='sure'><em>"+ DM.utils.farmatMoney(r.marginAmount)+ "</em></span>元。","去充值",function(){
				window.open(systemConfig.path + "/user/ViewMyAccount.do?marginAmount="+r.marginAmount);
				new ConfirmDialog("提示","您已经前往充值，若充值完成请点击【充值完成】","充值完成",function(){
					location.reload();
				},"返回",null).show();
			},"返回",null).show();
		} else if (r.result.resCode == 12) {
			ShowDialogFail("分成模式配资最多同时进行不超过四个", null);
		} else if (r.result.resCode == 13) {
			ShowDialogFail(r.result.errorInfo, null);
		} else if(r.result.resCode == 16){
			ShowDialogFail("您的配资额度不能超过今日最大的可用配资额度", null);
		}else{
			ShowDialogFail(r.result.errorInfo, null);
		}
	});
}
function doConfirmFinance() {
	confirmFinancing();
}


/** ******************我要炒股相关************************** */
function getCurrentPortfolioId() {
	return $("#account_mod_div li.actived").attr("portid");
}
function queryStockAccount() {
	var portfolioId = getCurrentPortfolioId();
	ajaxReuqestAsync(systemConfig.path + 
			"/trade/GetStockBalance.ajax", {
		"portfolioId" : portfolioId
	}, function(r) {
		$("#stockBalanceDd").html(DM.utils.farmatMoney(r.stockBalance.availAmount, 2));
		$("#t0DayPositonAmountDd").html(DM.utils.farmatMoney(r.stockBalance.cashPositionAmount, 2));
//		$("#frzoneAmountDd").html(DM.utils.farmatMoney(r.frzoneAmount, 2));
		$("#marketValueDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue, 2));
		$("#sumAmountDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue + r.stockBalance.cashPositionAmount, 2));
		$("#principalDd").html(DM.utils.farmatMoney(r.stockBalance.principalAmount, 2));
		$("#financeAmountDd").html(DM.utils.farmatMoney(r.stockBalance.financingAmount, 2));
		$("#alertLineDd").html(DM.utils.farmatMoney(r.stockBalance.alertLine, 2));
		$("#wrongLineDd").html(DM.utils.farmatMoney(r.stockBalance.wrongLine, 2));
		$("#stockBalanceDd").html(DM.utils.farmatMoney(r.stockBalance.availAmount, 2));
		$("#t0DayPositonAmountDd").html(DM.utils.farmatMoney(r.stockBalance.cashPositionAmount, 2));
		$("#frzoneAmountDd").html(DM.utils.farmatMoney(r.frzoneAmount, 2));
		$("#marketValueDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue, 2));
		$("#sumAmountDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue + r.stockBalance.cashPositionAmount, 2));
		$("#principalDd").html(DM.utils.farmatMoney(r.stockBalance.principalAmount, 2));
		$("#financeAmountDd").html(DM.utils.farmatMoney(r.stockBalance.financingAmount, 2));
		$("#alertLineDd").html(DM.utils.farmatMoney(r.stockBalance.alertLine, 2));
		$("#wrongLineDd").html(DM.utils.farmatMoney(r.stockBalance.wrongLine, 2));
		updateProfit(r.stockBalance.profitValue);
	});
}
function updateProfit(profitValue){
	$("#profitValueDd").html(
			DM.utils.farmatMoney(profitValue, 2));
	if(profitValue>0){
		$("#profitValueDd").attr("class","col-red");
	}else if(profitValue<0){
		$("#profitValueDd").attr("class","col-green");
	}else{
		$("#profitValueDd").attr("class","");
	}
//	var cashPositionAmount=parsetFloat($("#t0DayPositonAmountDd").html());
//	var marketValue=parseFloat($("#marketValueDd").html());
//	$("#sumAmountDd").html(
//			DM.utils.farmatMoney(marketValue
//					+ cashPositionAmount, 2));
}
function jsoncallback(r){
	if (r.info != null) {
		checkVisitOpenApiLogin(r);
		var result = r.info.result;
		var lastPrice=result.lastPrice;
		var dailyChange=result.dailyChange;
		var dailyChangePercent=result.dailyChangePercent;
		if(lastPrice==0){
			dailyChange=0;
			dailyChangePercent=0;
		}
		dailyChangePercent=formateDecimalToPercent(dailyChangePercent);
		$("#securityName").html(result.stockName);
		$("#securityCode").html(result.stockCode);
		$("#lastPrice").html(DM.utils.farmatMoney(result.lastPrice, 2));
		$("#dailyChange").html(DM.utils.farmatMoney(dailyChange), 2);
		$("#dailyChangePercent").html(dailyChangePercent);
		$("#sprice5").html(DM.utils.farmatMoney(result.askPrice5), 2);
		$("#samount5").html(result.askVolume5);
		$("#sprice4").html(DM.utils.farmatMoney(result.askPrice4), 2);
		$("#samount4").html(result.askVolume4);
		$("#sprice3").html(DM.utils.farmatMoney(result.askPrice3), 2);
		$("#samount3").html(result.askVolume3);
		$("#sprice2").html(DM.utils.farmatMoney(result.askPrice2), 2);
		$("#samount2").html(result.askVolume2);
		$("#sprice1").html(DM.utils.farmatMoney(result.askPrice1), 2);
		$("#samount1").html(result.askVolume1);
		$("#bprice1").html(DM.utils.farmatMoney(result.bidPrice1), 2);
		$("#bamount1").html(result.bidVolume1);
		$("#bprice2").html(DM.utils.farmatMoney(result.bidPrice2), 2);
		$("#bamount2").html(result.bidVolume2);
		$("#bprice3").html(DM.utils.farmatMoney(result.bidPrice3), 2);
		$("#bamount3").html(result.bidVolume3);
		$("#bprice4").html(DM.utils.farmatMoney(result.bidPrice4), 2);
		$("#bamount4").html(result.bidVolume4);
		$("#bprice5").html(DM.utils.farmatMoney(result.bidPrice5), 2);
		$("#bamount5").html(result.bidVolume5);
		$("#upLimit").html(result.upLimit);
		$("#downLimit").html(result.downLimit);
		$("#date").html(result.marketDataDateTime);
		if(dailyChange>0){
			$("#upOrFallImg").attr("src","images/up.png").css("visibility","visible");
			$("#lastPrice").removeClass("col-green").addClass("col-red");
			$("#dailyChange").removeClass("col-green").addClass("col-red");
			$("#dailyChangePercent").removeClass("col-green").addClass("col-red");
		}else if(dailyChange<0){
			$("#upOrFallImg").attr("src","images/fall.png").css("visibility","visible");;
			$("#lastPrice").removeClass("col-red").addClass("col-green");
			$("#dailyChange").removeClass("col-red").addClass("col-green");
			$("#dailyChangePercent").removeClass("col-red").addClass("col-green");
		}else{
			$("#upOrFallImg").attr("src","").css("visibility","hidden");;
			$("#lastPrice").removeClass("col-green").removeClass("col-red");
			$("#dailyChange").removeClass("col-green").removeClass("col-red");
			$("#dailyChangePercent").removeClass("col-green").removeClass("col-red");
		}
   }
}
function queryStockMarketData(stockCode) {
	$.ajax({
		   async:false, 
		   type: "POST",
		   data:{"is_web":1,"securityCode":stockCode},
		   url: Configs.market_data_server,
		   cache: false,
		   dataType:"jsonp",
		   jsonp:"jsoncallback", 
		 });
};

function queryAllSecurity() {
	var sList = new Array();
	/* 获取所有股票数据 */
	ajaxReuqestAsync(systemConfig.path + "/trade/QueryAllSecurityModel.ajax",
			null, function(r) {
				var sL = r.info.result;
				for (var i = 0; i < sL.length; i++) {
					var stock = sL[i];
					var value = stock.value;
					var data = stock.data;
					var obj = new Object();
					obj.value = value;
					obj.data = data;
					sList.push(obj);
				}
			});
	return sList;
}
function bindStockResource(stockCodeId, stocksList) {
	//var jquery = $.noConflict(true);
	/* 设置股票代码搜索加载 */
	$("#" + stockCodeId).autocomplete({
		lookup : stocksList,
		width : 170,
		onSelect : function(suggestion) {
			$('#' + stockCodeId).val(suggestion.data).blur();
		},
		formatItem : function(item, i, max) {
			return "<span style='border-bottom:1px solid red'>"
					+ item.Text + "</span>";
		},
		formatMatch : function(row, i, max) {
			return /^2/g;
		}
	});
}
function adjustPrice(addOrMinus, priceInputId,isSell) {
	var obj = $("#" + priceInputId);
	var adjustStep = 0.01;
	var price = obj.val();
	if(price==null||price==""){
		return;
	}
	price = parseFloat(price);
	if (addOrMinus == "add") {
		price += adjustStep;
	} else {
		price -= adjustStep;
	}
	price=DM.utils.toFixedNumber(price);
	if(price<=0.01){
		price=0.01;
	}
	if(price!="NaN"){
		obj.val(price);
	}
	if(!isSell){
		//calcMaxStocksAmount(priceInputId, "stockBalanceDd", "maxQuantity");
	}
}
function formateRemoveRmbSysbol(money) {
	money = money.replace("￥", "");
	money = DM.utils.formateMoneyToDecimal(money);
	return money;
}
/**
 * 计算最大可买
 * @param priceId
 * @param stockBalanceId
 * @param maxQuantityId
 */
function calcMaxStocksAmount(priceId, stockBalanceId, maxQuantityId) {
	var quantity = $("#" + maxQuantityId);
	var currentPriceVal = parseFloat($("#currentPrice").val());
	if (currentPriceVal == "") {
		quantity.html("0");
		return;
	}
	var balanceObj = $("#" + stockBalanceId);
	var balanceAmount = parseFloat(formateRemoveRmbSysbol(balanceObj.html()));
	if (balanceAmount == 0 || currentPriceVal == 0) {
		quantity.html("0");
	} else {
		var amount = parseInt(balanceAmount / currentPriceVal/ 100/ 1.01);
		amount = amount * 100;
		quantity.html(amount);
	}
	;
}
function calcMaxStocksAmountToPlancement(priceId, stockBalanceId) {
	var placementPrice = parseFloat($("#"+priceId).val());
	if (placementPrice == "") {
		return 0;
	}
	var balanceObj = $("#" + stockBalanceId);
	var balanceAmount = parseFloat(formateRemoveRmbSysbol(balanceObj.html()));
	if (balanceAmount == 0 || placementPrice == 0) {
		return 0;
	} else {
		var amount = parseInt(balanceAmount / placementPrice/1.01/100);
		amount = amount * 100;
	}
	return amount;
}
function calcMaxStocksAmountToSell(){
	var stockCode=$("#stockCode").val();
	var channelId=$("#channelSelect").val();
	ajaxReuqestAsync(systemConfig.path+"/trade/QueryHoldedStockByStockCode.ajax", {"stockCode":stockCode,"channelId":channelId}, function(r){
		if(r.isLogin==false){
			location.href=systemConfig.path+"/user/login.jsp";
		}
		var liObj=$("#maxQuantity");
		if(r.fsr==null){
			liObj.html("0");
		}else{
			var qt=r.fsr.t0PlacementQuantity;
			//qt=DM.utils.farmatMoney(qt, 0);
			liObj.html(qt);
		}
	});
}
function buyStockCodeInputBlur(stockCodeId) {
	showStockMarketDataTip("");
	$("#quantity").val("");
	var obj = $("#" + stockCodeId);
	Configs.market_data_fresh_counts=0;
	var stockCode = obj.val();
	if (stockCode.length < 6) {
		$("#stockName").html("&nbsp;");
		$("#maxQuantity").html("");
		$("#stockPrice").val("");
		$("#quantity").val("");
		return;
	}
	ajaxReuqestAsync(systemConfig.path + "/trade/QueryStockDetailAction.ajax",
			"stockCode=" + stockCode, function(msg) {
				if (msg.result == null) {
					// showStockMarketDataTip("*您输入的股票代码无效");
					$(".stockSearch_2 input").val("");
					$("#stockName").html("&nbsp;");
					$("#maxQuantity").html("&nbsp;");
				} else {
					$("#stockName").html(msg.result.securityModel.shortNameCn);
					$("#stockPrice").val(
							msg.result.marketDataModel.lastPrice);
					$("#currentPrice").val(msg.result.marketDataModel.lastPrice);
					queryStockMarketData(stockCode);
					/* 定时刷新 */
					showStockMarketData(stockCode);
					/* 计算可买股票数量 */
					calcMaxStocksAmount("stockPrice", "stockBalanceDd", "maxQuantity");
				}
			});
};
function sellStockCodeInputBlur(stockCodeId) {
	var stockCode=$("#"+stockCodeId).val();
	Configs.market_data_fresh_counts=0;
	if($.trim(stockCode).length<6){
		$("#stockName").html("&nbsp;");
		$("#maxQuantity").html("");
		 $("#stockPrice").val("");
		 $("#quantity").val("");
		 $("#maxQuantity").html("");
		 if($.trim(stockCode) == ""){
		 	return;
		 }
	}
	ajaxReuqestAsync(systemConfig.path+"/trade/QueryStockDetailAction.ajax", {"stockCode":stockCode}, function(msg){
		if(msg.result==null){
			$("#stockName").html("&nbsp;");
			$("#maxQuantity").html("");
		 }else{
			 $("#stockName").html(msg.result.securityModel.shortNameCn);
		     $("#stockPrice").val(msg.result.marketDataModel.askPrice1);
		     queryStockMarketData(stockCode);
		     /* 定时刷新 */
		     showStockMarketData(stockCode);
		     /* 计算可买股票数量 */
		     calcMaxStocksAmountToSell();
		 }
	});
};
/* 股票行情显示 */
function showStockMarketData(stockCode) {
	if (stockCode === undefined || stockCode.length < 6) {
		return;
	}
	if (Configs.market_data_fresh_counts == 0) {
		if ($.trim(stockCode) != "") {
			queryStockMarketData(stockCode);
		}
	} else {
		var nowTime = new Date();
		var day = nowTime.getDay();
		var hour = nowTime.getHours();
		if (day != 0 && day != 6) {
			if (hour >= 9 && hour <= 18) {
				if ($.trim(stockCode) != "") {
					queryStockMarketData(stockCode);
					clearInterval(Configs.market_data_interval);
					//定时刷新行情
					Configs.market_data_interval  = setInterval(function() {
						queryStockMarketData(stockCode);
					}, Configs.market_data_fresh_interval);
				}
			}
		}
	}
	Configs.market_data_fresh_counts++;
}
/* 持仓查询 */
function queryHoldStockList(holdStockTable,page,pageSize) {
	var trs=$("#" + holdStockTable).find("tr");
	trs.not(trs.eq(0)).remove();
	$("#" + holdStockTable).append("<tr><td colspan='10' id='waitTr'><p>加载中...</p></td></tr>");
	var html = "";
	//ajaxReuqestAsync(url, params, callback)
	ajaxReuqestSynchronize(systemConfig.path + "/openapi/queryHoldStockListByPage.ajax",
			{
				"portfolioId" : getCurrentPortfolioId(),
				"page":page,
				"pageSize":pageSize
			},
			function(r) {
				checkVisitOpenApiLogin(r);
				var list = r.info.result.dataList;
				var page=r.info.result;
				// 显示分页
				loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, holdStockTable, 'queryHoldStockList');
				var refreshFrequency=r.info.refreshFrequency;
				refreshFrequency=refreshFrequency*1000;
				Configs.hold_stocks_List_fresh_interval=refreshFrequency;
				var sumProfitValue=0;
				if (list != null && list.length > 0) {
					var isOdd=true;
					for (var i = 0; i < list.length; i++) {
						var obj = list[i];
						var securityShortName = obj.securityShortName;
						var securityLocalCode = obj.securityLocalCode;
						var marketValue = obj.marketValue;
						marketValue=DM.utils.toFixedNumber(marketValue);
						var marketProfit = obj.marketProfit;
						marketProfit=DM.utils.toFixedNumber(marketProfit);
						var marketProfitPercent = obj.marketProfitPercent;
						marketProfitPercent=formateDecimalToPercent(marketProfitPercent);
						var positionAmount = obj.positionAmount;
						if(positionAmount==0){
							continue;
						}
						sumProfitValue=sumProfitValue+marketProfit;
						//positionAmount=toFixedNumber(positionAmount);
						var t0PlacementQuantity = obj.t0PlacementQuantity;
						var latestPrice = obj.latestPrice;
						latestPrice=DM.utils.toFixedNumber(latestPrice);
						var costPrice = obj.costPrice;
						costPrice=DM.utils.toFixedNumber(costPrice);
						html += "<tr";
						if (isOdd) {
							html += " class='odd'>";
						} else {
							html += "''>";
						}
						var redOrGreen="";
						if(marketProfit<0){
							redOrGreen="green";
						}else if(marketProfit>0){
							redOrGreen="red";
						}
						html += "<td><p>" +securityLocalCode + "</p><p>"
								+ securityShortName + "</p></td>";
						html += "<td><p>" + marketValue
								+ "</p><p></p></td><td><p class='col-"+redOrGreen+"'>"
								+ marketProfit + "<p class='col-"+redOrGreen+"'>"
								+ marketProfitPercent + "</p></td>";
						html += "<td><p>" + positionAmount + "</p><p>"
								+ t0PlacementQuantity + "</p></td>";
						html += "<td><p>" + latestPrice + "</p><p></p></td>";
						html += "<td><p>" + costPrice + "</p><p></p></td>";
						html += "<td>";
						html += "<label>";
						var buyJs="location.href='"+systemConfig.path+"/trade/BuyStocksInit.do?stockCode="+securityLocalCode+"'";
						html += "<input type='submit' name='button' id='button' value='' class='btn-in' onclick=javascript:"+buyJs+";"+">";
						html += "</label>";
						html += " <label>";
						var sellJs="location.href='"+systemConfig.path+"/trade/SellStocksInit.do?stockCode="+securityLocalCode+"'";
						html += "  <input type='submit' name='button' id='button' value='' class='btn-out mar-t-5' onclick=javascript:"+sellJs+";"+">";
						html += " </label>";
						html += " </td></tr>";
						if(isOdd){
							isOdd=false;
						}else{
							isOdd=true;
						}
					}
					//updateProfit(sumProfitValue);
				} else {
					html += "<tr><td colspan='10'><p>暂无记录</p></td></tr>";
				}
				$("#waitTr").remove();
				if($.trim(html)==''){
					html = "<tr><td colspan='10'><p>暂无记录</p></td></tr>";
				}
				$("#" + holdStockTable).find("tr").not(trs.eq(0)).remove();
				$("#" + holdStockTable).append(html);
			});
}

/* 买入卖出页面委托查询 */
function loadCurrentDayPlacementListShowBtn(dataTableId,page,pageSize){
	$("#" + dataTableId).find("tr").not(":first").remove();
	$("#" + dataTableId).append("<tr><td colspan='10' id='waitTr'>加载中...</td></tr>");
	ajaxReuqestSynchronize(systemConfig.path +"/openapi/queryPlacementCurrentDayListByPage.ajax", {
		"portfolioId" : getCurrentPortfolioId(),
		"page":page,
		"pageSize":pageSize
		}, function(r){
		checkVisitOpenApiLogin(r);
		var list = r.info.result.dataList;
		var page=r.info.result;
		// 显示分页
		loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'loadCurrentDayPlacementListShowBtn');
		var html="";
		if (list != null && list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				var obj = list[i];
				var placementId=obj.placementId;
				var securityName = obj.securityName;
				var securityCode = obj.securityCode;
				var createTime = obj.createTime;
				//createTime=formateDate(createTime);
				var createTimeDate=formateDateToShowDate(createTime);
				var createTime=formateDateToTime(createTime);
				var tradeTypeDisplay = obj.tradeTypeDisplay;
				var placementPrice = obj.placementPrice;
				placementPrice=DM.utils.toFixedNumber(placementPrice);
				var placementQty = obj.placementQty;
				var placementTotal = placementPrice * placementQty;
				placementTotal=placementTotal.toFixed(2);
				var filledQty = obj.filledQty;
				var cancelQty = placementQty - filledQty;
				var placementStatusDisplay=obj.placementStatusDisplay;
				html += "<tr";
				if (i % 2 == 0) {
					html += " class='odd'>";
				} else {
					html += ">";
				}
				html += '<td>' + '  <p>'
						+ securityCode
						+ '</p><p>'
						+ securityName
						+ '</p></td>'
						+ '<td><p>'
						+ createTimeDate
						+ '</p><p>'+createTime+'</p></td>'
						+ ' <td><p>'
						+ tradeTypeDisplay
						+ '</p></td>'
						+ '<td><p>'
						+ placementPrice
						+ '</p></td>'
						+ '<td><p>'
						+ placementQty
						+ '</p></td>'
						+ ' <td><p>'
						+ placementTotal
						+ '</p></td>'
						+ ' <td>'
						+ filledQty
						+ '</td>'
						+ '<td>'
						+ cancelQty
						+ '</td><td>'
						+placementStatusDisplay
						+ '</td><td><label>';
						if(placementStatusDisplay=="正撤"||placementStatusDisplay=="已成"||placementStatusDisplay=="废单"||placementStatusDisplay=="已撤"){
							html+='<input type="submit" name="button" id="button" value="" class="btn-cd-gray" onclick="javascript:';
							html+=';';
						}else{
							html+='<input type="submit" name="button" id="button" value="" class="btn-cd" onclick="javascript:';
							html+= "cancelPlacement('"+placementId+"','"+securityCode+"','"+securityName+"','"+cancelQty+"','"+placementPrice+"');";
						}
				        html +='"/>'
						+ '</label>' + '</td>' + '</tr>';
			}
		}else{
			html += "<tr><td colspan='10'>暂无记录</td></tr>";
		}
		$("#waitTr").remove();
		$("#" + dataTableId).find("tr").not(":first").remove();
		$("#" + dataTableId).append(html);
	});
}
/* 今日委托查询 */
function loadCurrentDayPlacementList(dataTableId,page,pageSize){
	var trs=$("#" + dataTableId).find("tr");
	trs.not(trs.eq(0)).remove();
	$("#" + dataTableId).append("<tr><td colspan='10' id='waitTr'>加载中...</td></tr>");
	ajaxReuqestSynchronize(systemConfig.path +"/openapi/queryPlacementCurrentDayListByPage.ajax", {
		"portfolioId" : getCurrentPortfolioId(),
		"page":page,
		"pageSize":pageSize
		}, function(r){
		checkVisitOpenApiLogin(r);
		var list = r.info.result.dataList;
		var page=r.info.result;
		// 显示分页
		loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'queryCurrentDayPlacementList');
		var html="";
		if (list != null && list.length > 0) {
			for(var i=0;i<list.length;i++){
				var obj=list[i];
				var securityName=obj.securityName;
				var securityCode=obj.securityCode;
				var time=formateDateToTime(obj.createTime);
				var tradeTypeDisplay=obj.tradeTypeDisplay;
				var placementAmount=obj.placementAmount;
				var placementPrice=obj.placementPrice;
				placementPrice=DM.utils.toFixedNumber(placementPrice);
				var placementQty=obj.placementQty;
				var filledPrice=obj.filledPrice;
				filledPrice=DM.utils.toFixedNumber(filledPrice);
				var filledQty=obj.filledQty;
				var filledAmount=obj.filledAmount;
				filledAmount=DM.utils.toFixedNumber(filledAmount);
				var placementStatusDisplay=obj.placementStatusDisplay;
				html += "<tr class=";
				if (i % 2 == 0) {
					html += "odd>";
				} else {
					html += ">";
				}
				html+="<td><p>"+securityName+"</p><p>"+time+"</p></td>"
	              +"<td><p>"+tradeTypeDisplay+"</p></td>"
	              +"<td><p>"+placementAmount+"</p></td>"
	              +"<td><p>"+placementPrice+"</p><p>"+placementQty+"</p></td>"
	              +" <td><p>"+filledPrice+"</p><p>"+filledQty+"</p></td>"
	              +"<td><p>"+filledAmount+"</p></td>"
	              +"<td>"+placementStatusDisplay+"</td>";
			}
		}else{
			html += "<tr><td colspan='10'>暂无记录</td></tr>";
		}
		$("#" + dataTableId).find("tr").not(":first").remove();
		$("#" + dataTableId).append(html);
	});
}
/* 显示分页 */
function loadPages(pageNo,pageSize,sumPages,pageList,dataTableId,turnPageFunctionName){
	var html="";
	var tid=dataTableId;
	dataTableId="'"+dataTableId+"'";
	if(sumPages>1){
		if(pageNo==1){
			html+='<span class="disabled"> &lt; 上一页 </span>';
		}else{
			var frontPage=pageNo-1;
			html+='<a href="javascript:'+turnPageFunctionName+'('+dataTableId+','+frontPage+','+pageSize+');"> &lt; 上一页 </a>';
		}
		for(var i=0;i<pageList.length;i++){
			var page=pageList[i];
			if(page==pageNo){
				html+='<span class="current">'+page+'</span>';
			}else{
				html+='<a href="javascript:'+turnPageFunctionName+'('+dataTableId+','+page+','+pageSize+');">'+page+'</a>';
			}
		}
		if(pageNo==sumPages){
			html+='<span class="disabled"> &lt; 下一页 </span>';
		}else{
			var nextPage=pageNo+1;
			html+='<a href="javascript:'+turnPageFunctionName+'('+dataTableId+','+nextPage+','+pageSize+');">下一页 &gt;</a>&nbsp;&nbsp;&nbsp;';
		}
		html+="<span>"+sumPages+"</span>页";
		$("#"+tid).next("div.digg").html(html);
	}else{
		$("#"+tid).next("div.digg").html('');
	}
}
/* 历史委托查询 */
function loadHistoryPlacementList(dataTableId,page,pageSize){
	// 分页隐藏
	$("div.digg").html("");
	var trs=$("#" + dataTableId).find("tr");
	trs.not(trs.eq(0)).remove();
	$("#" + dataTableId).append("<tr><td colspan='10' id='waitTr'>加载中...</td></tr>");
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	ajaxReuqestAsync(systemConfig.path +"/openapi/queryPlacementHistoryByConditionsByPage.ajax", {"portfolioId" : getCurrentPortfolioId(),
		"startDate":startDate,"endDate":endDate,"page":page,"pageSize":pageSize
	}, function(r){
		var page=r.info.result;
		// 显示分页
		loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'loadHistoryPlacementList');
		// 显示数据
		checkVisitOpenApiLogin(r);
		var list = r.info.result.dataList;
		var html="";
		if (list != null && list.length > 0) {
			for(var i=0;i<list.length;i++){
				var obj=list[i];
				var securityName=obj.shortNameCn;
				var securityCode=obj.securityCode;
				var tradeTypeDisplay=obj.tradeTypeDisplay;
				var placementAmount=obj.placementAmount;
				placementAmount=DM.utils.toFixedNumber(placementAmount);
				var placementPrice=obj.placementPrice;
				placementPrice=DM.utils.toFixedNumber(placementPrice);
				var placementQty=obj.placementQty;
				var filledPrice=obj.fillPrice;
				filledPrice=DM.utils.toFixedNumber(filledPrice);
				var filledQty=obj.fillQty;
				var filledAmount=obj.fillAmount;
				filledAmount=DM.utils.toFixedNumber(filledAmount);
				var placementStatusDisplay=obj.placementStatusDisplay;
				var date=formateDateToShowDate(obj.createTime);
				html += "<tr class=";
				if (i % 2 == 0) {
					html += "odd>";
				} else {
					html += ">";
				}
				html+="<td><p>"+securityName+"</p><p>"+date+"</p></td>"
	              +"<td><p>"+tradeTypeDisplay+"</p></td>"
	              +"<td><p>"+placementAmount+"</p></td>"
	              +"<td><p>"+placementPrice+"</p><p>"+placementQty+"</p></td>"
	              +" <td><p>"+filledPrice+"</p><p>"+filledQty+"</p></td>"
	              +"<td><p>"+filledAmount+"</p></td>"
	              +"<td>"+placementStatusDisplay+"</td>";
			}
		}else{
			html += "<tr><td colspan='10'>暂无记录</td></tr>";
		}
		$("#waitTr").remove();
		$("#" + dataTableId).append(html);
	});
}
/* 成交查询 */
function loadPlacementFillList(dataTableId,page,pageSize){
	// 分页隐藏
	$("div.digg").html("");
	var trs=$("#" + dataTableId).find("tr");
	trs.not(trs.eq(0)).remove();
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	$("#" + dataTableId).append("<tr><td colspan='10' id='waitTr'>加载中...</td></tr>");
	var stockCode = $("#stockCode").val();
	var tradeType =$("#tradeType").val();
	ajaxReuqestAsync(systemConfig.path +"/openapi/queryPlacementFillHistoryByConditionsByPage.ajax", {"portfolioId" : getCurrentPortfolioId(),
		"startDate":startDate,"endDate":endDate,"page":page,"pageSize":pageSize,"tradeType":tradeType,"stockCode":stockCode
	}, function(r){
		checkVisitOpenApiLogin(r);
		var page=r.info.result;
		// 显示分页
		loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'loadPlacementFillList');
		var list = r.info.result.dataList;
		var html="";
		if (list != null && list.length > 0) {
			for(var i=0;i<list.length;i++){
				var obj=list[i];
				var securityName=obj.securityName;
				var securityCode=obj.securityCode;
				var filledTime=obj.filledTime;
				var filledTimeDate=formateDateToShowDate(filledTime);
				var filledTimeTime=formateDateToTime(filledTime);
				filledTime=formateDate(filledTime);
				var filledPrice=obj.filledPrice;
				filledPrice=DM.utils.toFixedNumber(filledPrice);
				var filledQty=obj.filledQty;
				var filledAmount=obj.filledAmount;
				filledAmount=DM.utils.toFixedNumber(filledAmount);
				var tradeTypeDisplay=obj.tradeTypeDisplay;
				html += "<tr class='";
				if (i % 2 == 0) {
					html += "'odd'" + "'>";
				} else {
					html += "''" + "'>";
				}
				html+="<td><p>"+securityCode+"</p><p>"+securityName+"</p></td>"
	              +"<td><p>"+filledTimeDate+"</p><p>"+filledTimeTime+"</p></td>"
	              +"<td><p>"+filledPrice+"</p></td>"
	              +"<td><p>"+filledQty+"</p></td>"
	              +" <td><p>"+filledAmount+"</p></td>"
	              +"<td><p>"+tradeTypeDisplay+"</p></td>";
			} 
		}else{
			html += "<tr><td colspan='10'>暂无记录</td></tr>";
		}
		$("#waitTr").remove();
		$("#" + dataTableId).append(html);
	});
}
function showStockMarketDataTip(tip){
	$("div.error span").html(tip);
}
/* 股票委托交易 */
function doBuyPlacementStocksTrade(){
	var stockName=$("#stockName").html();
	var price=$("#stockPrice").val();
	var quantity=$("#quantity").val();
	var stockCode=$("#stockCode").val();
	if($.trim(stockName)==""){
		showStockMarketDataTip("对不起，您输入的股票代码有误。");
		return;
	}
	if(!valid.isMoney(price)){
		showStockMarketDataTip("对不起，您输入的股票价格格式不正确。");
		return;
	}
	if(!valid.isInteger(quantity)){
		showStockMarketDataTip("对不起，您输入的委托数量格式不正确。");
		return;
	}
	if(!DM.utils.isNumberByN(quantity, 100)){
		showStockMarketDataTip("对不起，您输入的委托数量格式应为100的整数倍。");
		return;
	}
	var maxAmount=calcMaxStocksAmountToPlancement("stockPrice", "stockBalanceDd");
	if(quantity>maxAmount){
		showStockMarketDataTip("对不起，您输入的委托数量超过最大可买数量。");
		return;
	}else if(quantity==0){
		showStockMarketDataTip("对不起，您输入的委托数量必须大于0。");
		return;
	}
	// 判断可用余额是否为零
	var balance=formateRemoveRmbSysbol($("#stockBalanceDd").html());
	if(balance=="0"){
		showStockMarketDataTip("对不起，账户余额不足。");
		return;
	}
	// 判断购买余额总额是否超出余额
	var needMoney=price*quantity;
	if(needMoney>balance){
		showStockMarketDataTip("对不起，账户余额不足。");
		return;
	}
	showStockMarketDataTip("");
	var text=
	 '<li><lable>股票名称：</lable><span class="a-18">'+stockName+'</span></li>'
	+'<li><lable>委托数量：</lable><span class="a-18">'+quantity+'</span></li>'
	+'<li><lable>股票代码：</lable><span class="a-18">'+stockCode+'</span></li>'
	+'<li><lable>委托价格：</lable><span class="a-18">'+price+'</span></li>';
	ShowDialogConfirm("确认买入信息", text, "确认买入", function(){
		doBuyTrade(stockCode,price,quantity);
	}, "取消", null);
}
// 验证交易密码是否正确
function doBuyTrade(stockCode,price,quantity){
	var sellOrBuy="0";// 买入
	showStockMarketDataTip("");
	var url=systemConfig.path+"/trade/DoPlacementStocksTrade.ajax";
	var params="stockCode="+stockCode+"&price="+price+"&quantity="+quantity+"&sellOrBuy="+sellOrBuy+"&portfolioId="+getCurrentPortfolioId();
	ajaxReuqestAsync(url, params, function(msg){
		if(msg.tradeResult==null){
			ShowDialogSuccess("恭喜您，委托交易成功。",function(){
				location.href=systemConfig.path+"/trade/BuyStocksInit.do";
			});
		}else if(msg.tradeResult=="login"){
			location.href = systemConfig.path+ "/user/login.jsp";
		}else if(msg.tradeResult=="last_active_project"){
			ShowDialogFail("对不起，请配资炒股。");
			return;
		}else if(msg.tradeResult!=null){
			ShowDialogFail(msg.tradeResult);
			return;
		}
	});
}
/* 股票委托交易 */
function doSellPlacementStocksTrade(){
	// 清除错误信息提示
	$("div.error span").html("");
	var stockName=$("#stockName").html();
	var stockCode=$("#stockCode").val();
	var price=$("#stockPrice").val();
	var quantity=$("#quantity").val();
	if($.trim(stockName)==""){
		showStockMarketDataTip("对不起，您输入的股票代码有误。");
		return;
	}
	if(!valid.isMoney(price)){
		showStockMarketDataTip("对不起，您输入的股票价格格式不正确。");
		return;
	}
	if(!valid.isInteger(quantity)){
		showStockMarketDataTip("对不起，您输入的委托数量格式不正确。");
		return;
	}
	var maxAmount=parseInt($("#maxQuantity").html());
	var q=parseInt(quantity);
	if(q>maxAmount){
		showStockMarketDataTip("对不起，您输入的委托数量超过最大可卖数量。");
		return;
	}
	var text=
		 '<li><lable>股票名称：</lable><span class="a-18">'+stockName+'</span></li>'
		+'<li><lable>委托数量：</lable><span class="a-18">'+quantity+'</span></li>'
		+'<li><lable>股票代码：</lable><span class="a-18">'+stockCode+'</span></li>'
		+'<li><lable>委托价格：</lable><span class="a-18">'+price+'</span></li>';
		ShowDialogConfirm("确认卖出信息", text, "确认卖出", function(){
			doSellTrade(stockCode,price,quantity);
		}, "取消", null);
	;
}
/* 委托卖出 */
function doSellTrade(stockCode,price,quantity){
	var sellOrBuy="1";// 卖出
	showStockMarketDataTip("");
	var url=systemConfig.path+"/trade/DoPlacementStocksTrade.ajax";
	var params="stockCode="+stockCode+"&price="+price+"&quantity="+quantity+"&sellOrBuy="+sellOrBuy+"&portfolioId="+getCurrentPortfolioId();
	ajaxReuqestAsync(url, params, function(msg){
		if(msg.tradeResult==null){
			ShowDialogSuccess("恭喜您，委托交易成功。",function(){
				location.href=systemConfig.path+"/trade/SellStocksInit.do";
			});
		}else if(msg.tradeResult=="login"){
			location.href = systemConfig.path+ "/user/login.jsp";
		}else if(msg.tradeResult=="last_active_project"){
			ShowDialogFail("没有配资不允许炒股");
			return;
		}else{
			ShowDialogFail(msg.tradeResult);
			return;
		}
	});
}
/* 委托撤单 */
function cancelPlacement(placementId,stockCode,stockName,quantity,price){
	var text=
		 '<li><lable>股票名称：</lable><span class="a-18">'+stockName+'</span></li>'
		+'<li><lable>可撤数量：</lable><span class="a-18">'+quantity+'</span></li>'
		+'<li><lable>股票代码：</lable><span class="a-18">'+stockCode+'</span></li>'
		+'<li><lable>委托价格：</lable><span class="a-18">'+price+'</span></li>';
	ShowDialogConfirm("确认撤单操作", text, "确认", function(){
		var params="";
		var url=systemConfig.path+"/trade/DoPlacementCancel.ajax";
	    params=placementId;
		ajaxReuqestAsync(url, {"placementId":params}, function(msg){
			ShowDialogNotify(msg);
		});
	}, "取消", null);
}

/********************我的账户相关************************** */
function viewMyAccount() {
	ajaxReuqestAsync(systemConfig.path + "/user/ViewMyAccountCenter.ajax",null, function(r) {
		if(r.resCode=="login" || r.resCode=="error"){
			location.href=systemConfig.path+"/user/login.jsp";
			return;
		}else{
			$("#totalAmountDd").html(DM.utils.farmatMoney(r.acctInfo.totalAmount, 2));
			$("#availableAmountDd").html(DM.utils.farmatMoney(r.acctInfo.leftAmount, 2));
			$("#availidAmount").html(DM.utils.farmatMoney(r.acctInfo.leftAmount, 2));
			$("#frzoneAmountDd").html(DM.utils.farmatMoney(r.acctInfo.frozenAmount, 2));
			$("#stockBalanceDd").html(DM.utils.farmatMoney(r.stockBalance.availAmount, 2));
			$("#marketValueDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue, 2));
			$("#sumAmountDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue+r.stockBalance.cashPositionAmount, 2));
			$("#alertLineDd").html(DM.utils.farmatMoney(r.stockBalance.alertLine, 2));
			$("#wrongLineDd").html(DM.utils.farmatMoney(r.stockBalance.wrongLine, 2));
		}
		$("#totalAmountDd").html(DM.utils.farmatMoney(r.acctInfo.totalAmount, 2));
		$("#availableAmountDd").html(DM.utils.farmatMoney(r.acctInfo.leftAmount, 2));
		$("#availidAmount").html(DM.utils.farmatMoney(r.acctInfo.leftAmount, 2));
		$("#frzoneAmountDd").html(DM.utils.farmatMoney(r.acctInfo.frozenAmount, 2));
		$("#stockBalanceDd").html(DM.utils.farmatMoney(r.stockBalance.availAmount, 2));
		$("#marketValueDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue, 2));
		$("#sumAmountDd").html(DM.utils.farmatMoney(r.stockBalance.marketValue+r.stockBalance.cashPositionAmount, 2));
		$("#alertLineDd").html(DM.utils.farmatMoney(r.stockBalance.alertLine, 2));
		$("#wrongLineDd").html(DM.utils.farmatMoney(r.stockBalance.wrongLine, 2));
	});
}
function queryTradeTypes(accountTypeId,tradeTypeId){
	var accountType=$("#"+accountTypeId).val();
	var tradeTypeSelect=$("#"+tradeTypeId);
	ajaxReuqestAsync(systemConfig.path +"/user/QueryUserAllTradeTypes.ajax", {"accountType":accountType}, function(r){
		var types=r.result;
		var html="<option value=''>请选择</option>";
		if(types!=null&&types.length>0){
			for(var i=0;i<types.length;i++){
				var type=types[i];
				html+="<option value='"+type.value+"'>"+type.name+"</option>";
			}
		}
		tradeTypeSelect.html(html);
	});
}


function bindStockInput(stockInputId){
	$("#"+stockInputId).keyup(function(){
		var stockCode=$(this).val();
		if(stockCode.length>=6){
			$("#"+stockInputId).blur();
			$("div.autocomplete-suggestions").css("display","none");
		}
	});
}
/******充值相关代码********/
function doCharge(){
	var moneyVal=$("#tradeAmountInput").val();
	if(!valid.isMoney(moneyVal)){
		$("#chargeErrorDiv span").html("您输入的充值金额不正确");
		return;
	}
	moneyVal=DM.utils.formateMoneyToDecimal(moneyVal);
	if(moneyVal==''||moneyVal==0||moneyVal==null){
		$("#chargeErrorDiv span").html("充值金额不能为0");
		return;
	}
	if(moneyVal<1){
		$("#chargeErrorDiv span").html("每笔的充值金额最少为1元");
		return;
	}
	if(moneyVal<1||moneyVal>500000){
		$("#chargeErrorDiv span").html("每笔的充值金额最大为500000元");
		return;
	}else{
		$("#chargeErrorDiv span").html("");
		var tradeAmount=moneyVal;
		var isDevelop=$("#isDevelop").val();
	    ajaxReuqestAsync(systemConfig.path+"/user/MakePrincalTrade.ajax", {"tradeAmount":tradeAmount,"thirdpayername":"huichao"}, function(r){
	    	if(r.resCode=="login"){
	    		location.href=systemConfig.path+"/user/login.jsp";
	    	}else if(r.resCode=="validateUser"){
	    		ShowDialogNotify("请先通过实名认证,<a style='text-decoration: underline;' href='"+systemConfig.path+"/user/GetUserAccount.do?action=verifyid'>去认证>></a>", null);
	    	}else if(r.resCode=="success"){
	    		if(isDevelop=="false"){
	    			$("#tradeAmountInput").val("");
	    			var text='';
	    				text+='<h5 style="font-size:16px; color:#ff9900; width:300px; margin:0px auto; text-align:center; font-weight:normal; line-height:50px;">你已跳转到第三方支付页面</h5>';
	    				text+='<p>充值需要跳转到第三方支付平台，请设置您的浏览器允许跳转到新页面</p>';
	    			var d= new UpdateDialog("", text, "充值成功", function(){location.href=systemConfig.path+"/user/ViewMyAccount.do";},"充值失败", null);
		    		d.show();
	    		}else{
	    			var text='';
    					text+='<h5 style="font-size:16px; color:#ff9900; width:300px; margin:0px auto; text-align:center; font-weight:normal; line-height:50px;">你已跳转到第三方支付页面</h5>';
    					text+='<p>充值需要跳转到第三方支付平台，请设置您的浏览器允许跳转到新页面</p>';
	    			var d= new UpdateDialog("", text, "充值成功", function(){location.href=systemConfig.path+"/user/ViewMyAccount.do";},"充值失败", null);
		    		d.show();
		    		var el = document.createElement("a");
		    		document.body.appendChild(el);
		    		el.href = systemConfig.path+"/user/DoUserCharge.do?tradeNo="+r.tradeNo; //url 是你得到的连接
		    		el.target = '_new'; //指定在新窗口打开
		    		el.click();
		    		document.body.removeChild(el);
//		    		$("#openWin").attr("action",systemConfig.path+"/user/DoUserCharge.do?tradeNo="+r.tradeNo);
//		    		$("#openWin").submit();
//		    		$("#jumpA").attr("href",systemConfig.path+"/user/DoUserCharge.do?tradeNo="+r.tradeNo);
//		    		var a=$("#jumpA")[0];
//		    		obj.onclick();
//		    		document.getElementById("jumpA").click();
//		    		 var e = document.createEvent('MouseEvents');
//		    		 e.initEvent('click', true, true);
//		    		 a.dispatchEvent(e);
	    		}
	    	}
	    });
	}
}
/******支付宝充值相关代码********/
function doAliCharge(){
	var moneyVal=$("#aliTradeAmount").val();
	var bankCode=$("#aliAccountNo").val();
	if($.trim(bankCode)==""){
		$("#alipayErrorDiv span").html("充值账号不能为空");
		return;
	}
	if($.trim(moneyVal)==""){
		$("#alipayErrorDiv span").html("充值金额不能为空");
		return;
	}
	if($.trim(bankCode).length<6||$.trim(bankCode).length>50){
		$("#alipayErrorDiv span").html("请输入正确的支付宝账号");
		return;
	}
	if(!valid.isMoney(moneyVal)){
		$("#alipayErrorDiv span").html("您输入的充值金额不正确");
		return;
	}
	moneyVal=formateMoneyToDecimal(moneyVal);
	if(moneyVal==''||moneyVal==0||moneyVal==null){
		$("#alipayErrorDiv span").html("充值金额不能为0");
		return;
	}
	if(moneyVal<1){
		$("#alipayErrorDiv span").html("每笔的充值金额最少为1元");
		return;
	}
	if(moneyVal<1||moneyVal>500000){
		$("#alipayErrorDiv span").html("每笔的充值金额最大为500000元");
		return;
	}else{
		$("#alipayErrorDiv span").html("");
		var tradeAmount=moneyVal;
	    ajaxReuqestAsync(systemConfig.path+"/user/MakePrincalTrade.ajax", {"tradeAmount":tradeAmount,"thirdpayername":"alipay","bankCode":bankCode,"isOffLinePay":"true"}, function(r){
	    	if(r.resCode=="login"){
	    		location.href=systemConfig.path+"/user/login.jsp";
	    	}else if(r.resCode=="validateUser"){
	    		ShowDialogNotify("请先通过实名认证,<a style='text-decoration: underline;' href='"+systemConfig.path+"/user/GetUserAccount.do?action=verifyid'>去认证>></a>", null);
	    	}else if(r.resCode=="success"){
	    		$("#alipayStep1Div").css("display","none");
	    		$("#alipayStep2Div").css("display","block");
	    		$("#aliAccountNoConfirm").html(r.bankCode);
	    		$("#aliTradeAmountConfirm").html(r.tradeAmount);
	    	}else{
	    		ShowDialogFail(r.errorInfo);
	    	}
	    });
	}
}
function updateAlipayAmount(){
	$("#alipayStep1Div").css("display","block");
	$("#alipayStep2Div").css("display","none");
}
function trial(dataId){
	var availableBalance = $("#availableBalance").val();
	availableBalance = formateMoneyToDecimal(availableBalance);
	var text='';
	var title='充值计算器';
	text+='<table width="300" border="0" cellspacing="0" cellpadding="0" class="zhcc-table-2" style="margin-left:40px;">';
	text+='<tr><td width="150" ><span>账户余额：</span></td><td width="150"><div>'+availableBalance+'</div></td></tr>';
	text+='<tr><td><span>保证金：</span></td><td><input type="text" id="tryChargeAmount" onkeyup="changetryChargeAmount();"></td></tr>';
	text+='<tr><td><span>配资倍数：</span></td><td><select id="tryPzbs" style="width: 60px;" onchange="changetryPzbs();"><option value="1" selected="selected">1倍</option><option value="2">2倍</option><option value="3">3倍</option></select></td></tr>';
	text+='<tr><td><span>配资月份：</span></td><td><select id="tryMonth"  onchange="changetryMonth();"><option value="1" selected="selected">1个月</option><option value="2">2个月</option><option value="3">3个月</option><option value="4">4个月</option><option value="5">5个月</option><option value="6">6个月</option></select></td></tr>';
	text+='<tr><td><span>利率：</span></td><td><div id="interest">0</div></td></tr>';
	text+='</table>';
	text+='<div class="error-div"><div class="error" id="errorDiv_chargeTry"></div></div>';
	var dia = new TryChargeDialog(title, text, "试算", function(){
		var availableBalance = $("#availableBalance").val();
		availableBalance = formateMoneyToDecimal(availableBalance);
		var tryChargeAmount = $.trim($("#tryChargeAmount").val());
		tryChargeAmount = formateMoneyToDecimal(tryChargeAmount);
		 // 判断本金和配资金额是否是100的整数倍
	    if(!isNumberBy100(tryChargeAmount)){
	    	$("#errorDiv_chargeTry").html("保证金须为100的整数倍");
	        return;
	    }
	    // 保证金
	    if(tryChargeAmount>500000||tryChargeAmount<1000){
	    	$("#errorDiv_chargeTry").html("您输入的的保证金必须在1000-500000之间");
			return;
	    }
		var tryPzbs = parseInt($("#tryPzbs").val());
		var cycle = parseInt($("#tryMonth").val());
		var modeType = "fix_mode";
		var financeAmount = tryChargeAmount*tryPzbs;
		var interest = DM.utils.calcInterest(financeAmount, tryPzbs,parseInt(cycle),modeType);
		var charge = tryChargeAmount+(interest/100)*financeAmount;
		var tryCharge;
		if(charge > availableBalance){
			tryCharge = charge - availableBalance;
		}else{
			tryCharge = 0;
		}
		tryCharge = Math.ceil(tryCharge);
		$("#"+dataId).val(tryCharge);
		dia.closeDia();
	},"取消", null);
	dia.show();
}
function changetryChargeAmount(financeAmount,cycle,modeType){
	var tryChargeAmount = $("#tryChargeAmount").val();
	tryChargeAmount = formateMoneyToDecimal(tryChargeAmount);
	var tryPzbs = parseInt($("#tryPzbs").val());
	var cycle = parseInt($("#tryMonth").val());
	var modeType = "fix_mode";
	var financeAmount = tryChargeAmount*tryPzbs;
	var interest = DM.utils.calcInterest(financeAmount,tryPzbs,cycle,modeType);
	$("#interest").html(interest);
	$("#interest_to_xy").html(interest);
}

function changetryPzbs(){
	var tryChargeAmount = $("#tryChargeAmount").val();
	tryChargeAmount = formateMoneyToDecimal(tryChargeAmount);
	var tryPzbs = parseInt($("#tryPzbs").val());
	var cycle = parseInt($("#tryMonth").val());
	var modeType = "fix_mode";
	var financeAmount = tryChargeAmount*tryPzbs;
	var interest = DM.utils.calcInterest(financeAmount,tryPzbs,cycle,modeType);
	$("#interest").html(interest);
	$("#interest_to_xy").html(interest);
}

function changetryMonth(){
	var tryChargeAmount = $("#tryChargeAmount").val();
	tryChargeAmount = formateMoneyToDecimal(tryChargeAmount);
	var tryPzbs = parseInt($("#tryPzbs").val());
	var cycle = parseInt($("#tryMonth").val());
	var modeType = "fix_mode";
	var financeAmount = tryChargeAmount*tryPzbs;
	var interest = DM.utils.calcInterest(financeAmount,tryPzbs,cycle,modeType);
	$("#interest").html(interest);
	$("#interest_to_xy").html(interest);
}
/***************提现的js代码*****************/
/*费率计算*/
function calcTradeFee(tradeAmount){
   var fee=0;
   var tradetype="wd";
   var tradeway=0;
   ajaxReuqestSynchronize(systemConfig.path+"/user/cancUserDrawTradeFee.ajax", {"tradetype":tradetype,"tradeway":tradeway,"tradeAmount":tradeAmount}, function(r){
	   if(r.fee!=null){
		   fee=r.fee;
	   }else{
		   fee=0;
	   }
   });
   return fee;
}
/*绑定银行卡*/
function doBindCard(){
   var bankName=$("select[name='bankName']").val();
   var provice=$("select[name='provice']").val();
   var city=$("select[name='city']").val();
   var branchName=$("input[name='branchName']").val();
   var cardNo=$("input[name='cardNo']").val();
   var isdefault=$("#isdefault").val();
   if(bankName=="-1"){
	   $("#errorDiv3").html("请选择开户行");
	   return;
   }
   if(provice=="-1"){
	   $("#errorDiv3").html("请选择开户省");
	   return;
   }
   if(city=="-1"){
	   $("#errorDiv3").html("请选择开户市");
	   return;
   }
   if($.trim(branchName)==""){
	   $("#errorDiv3").html("必须填写分行信息");
	   return;
   }
   if(branchName.length>50){
	   $("#errorDiv3").html("支行信息不能大于50个字符");
	   return ;
   }
   if($.trim(cardNo)==""){
	   $("#errorDiv3").html("卡号输入不能为空");
	   return;
   }
   var test = /^[0-9]+.?[0-9]*$/;
   if(!test.test(cardNo)){
	   $("#errorDiv3").html("卡号必须为数字");
	   return;
   }
   if(cardNo.length != 16 && cardNo.length != 19){
	   $("#errorDiv3").html("银行卡的位数必须为16位或19位");
	   return;
   }
   if(!valid.isBankCard(cardNo)){
	   $("#errorDiv3").html("卡号输入不正确");
	   return;
   }
   var  proviceIndex=parseInt(provice);
   provice=C._cityInfo[proviceIndex].n;
   cityIndex=parseInt(city);
   var cityArray=C._cityInfo[proviceIndex].c;
   city=cityArray[cityIndex];
   ajaxReuqestAsync(systemConfig.path +"/user/BindBankCard.ajax", {"bankName":bankName,"provice":provice,"city":city,"branchName":branchName,"cardNo":cardNo,"isdefault":isdefault}, function(r){
	   if(r.resCode=="login"){
		   location.href=systemConfig.path +"/user/LogOut.do";
	   }else if(r.resCode=="verify_id"){
		   $("#errorDiv3").html("请先做实名认证");
		   return;
	   }else if(r.resCode=="success"){
		   $("#errorDiv3").html("");
		   ShowDialogSuccess("添加成功", function(){
			   location.href = systemConfig.path +"/user/UserWithDrawInit.do?action=manageCard";
		   });
	   }else{
		   $("#errorDiv3").html(r.resCode);
	   }
   });
}
/*删除银行卡*/
function deleteBankCard(id){
	var dia=new ConfirmDialog("提示", "确定要删除吗?", "确定", function(){
		dia.closeDia();
		ajaxReuqestAsync(systemConfig.path +"/user/DeleteBindBankCard.ajax", {"id":id}, function(r){
			if(r.resCode=="success"){
				location.reload();
			}
 	});
	}, "返回", null);
	dia.show();
}
/*提现验证码的发送*/
function sendMobileValidateWithDraw(type) {
	if (type == "1") {
		type = "WITH_DRAW_CODE";	
	}
	ajaxReuqestAsync(systemConfig.path + "/user/SendMobileValidateCodeWithDraw.ajax", {
		"type" : type
	}, function(r) {
		if(r.resCode=="login"){
			openLoginDia();
		}else if(r.resCode=="success"){
			$("#errorDiv2").html("发送成功");
		}else{
			$("#errorDiv2").html(r.resCode);
			return;
		}
	}, "json");
	// 更改样式
	changeButtonStyleForSendCodeToWait("regCodeBtn");
}
/*添加银行卡*/
function add(){
	ajaxReuqestSynchronize(systemConfig.path + "/user/IsVerifyID.ajax",null, function(r){
		if(r.resCode=="login"){
			location.href = systemConfig.path + "/user/login.jsp";
		}else if(r.resCode=="faile"){
			var text = "";
			text+="请先通过实名认证<a style='text-decoration: underline;' href='"+systemConfig.path+"/user/ViewUserSecurityInfo.do'>【去认证】</a><br/>";
			ShowDialogConfirm("提醒", text, "确定", function(){
			}, "取消", null);
		}else if(r.resCode=="success"){
			$("#tabe2").css("display","block");
		}
	});
}
/*提现*/
function userWithDraw(){
	var val=$("input[name=amount]").val();
	var moneyVal=$.trim(val);
	if(moneyVal==''||moneyVal==null){
		$("#errorDiv1").html("请输入提款金额！");
		return;
	}
	var leftmoney=$.trim($("#leftmoney").val());
	var fee;
	if(val.charAt(val.length - 1)=="."){
		return;
	}
	if(val!=0&&val!=null){
		fee=calcTradeFee(parseFloat(val));
		fee=fee.toFixed(2);
		$("#feeTd").html(fee+"元");
	}else{
		$("#feeTd").html(0+"元");
	} 
	leftmoney=formateMoneyToDecimal(leftmoney);
	var feeTd=formateMoneyToDecimal(fee);
	leftmoney=leftmoney-feeTd;
	leftmoney=leftmoney.toFixed(2);
	moneyVal=formateMoneyToDecimal(moneyVal);
	moneyVal=moneyVal.toFixed(2);
	var bankid=$("select[name='bankid']").val();
	if(moneyVal==0){
		$("#errorDiv1").html("提款金额不能为0！");
		return;
	}
	if(bankid<1){
		$("#errorDiv1").html("请选择银行卡");
		return;
	}
	if(!valid.isMoney(moneyVal)){
		$("#errorDiv1").html("您输入的提现金额不正确！");
		return;
	}
	if(eval(moneyVal)>eval(leftmoney)){
		$("#errorDiv1").html("提现金额不能大于账户余额！");
		return;
	}
	if(leftmoney<=100){
		if(moneyVal!=leftmoney){
			$("#errorDiv1").html("资金账户的钱少于100元，只能一次提取");
			return;
		}
	}
	var title="提现验证码";
	var text ='<p>提现验证码将通过短信形式发到您的手机,请注意查收!</p>';
		text+='<p><input type="text" class="inp-code" id="msgVerifyCode"><input id="regCodeBtn" type="button" onclick="javascript:sendMobileValidateWithDraw(1);" value="获取验证码" class="inp-btn-blue mar-l-5"/>&nbsp;&nbsp;&nbsp;<a id="voiceCodeWithDraw" onclick="javascript:sendVoiceCodeWithDraw();" style="color:#0272c9;">语音验证码</a></p>';
		text+='<div class="error-2" id="errorDiv2"></div>';
	var d=new UpdateDialog(title,text,"确定",function(){
		//第一次确认短信验证码
		var code = $("#msgVerifyCode").val();
		if (code == null || $.trim(code) == "") {
			$("#errorDiv2").html("请输入验证码");
			return;
		}
		ajaxReuqestSynchronize(systemConfig.path+"/user/ApplayWithDraw.ajax", {"bankid":bankid,"amount":moneyVal,"code" : code}, function(r){
			//验证验证码的正确性
			if(r.resCode == "login") {
				location.href = systemConfig.path + "/user/login.jsp";
			}else if(r.resCode=="success"){
				ShowDialogSuccess("提现申请已提交，等待平台处理...", function(){location.href=systemConfig.path+"/user/UserWithDrawInit.do";})
			}else if(r.resCode!=null){
				$("#errorDiv2").html(r.resCode);
				return;
			}else{
				ShowDialogFail("提现失败");
			}
		},"json");
	},"取消",null);
	d.show();
}
//发送语音验证码
function sendVoiceCodeWithDraw(){
	var type = "template_with_draw";
	var mobile = "";
	ajaxReuqestAsync(systemConfig.path + "/user/SendVoiceCode.ajax", {
		"mobile" : mobile,
		"type" : type
		},function(res){
			changeSendVoiceCodeWithDrawToWait("voiceCodeWithDraw");
			if(res.resCode=="login"){
				location.href = systemConfig.path + "/user/loginOut.do";
			}else if(res.result=="success"){
				$("#errorDiv2").html("发送成功");
				return true;
			}else{
				$("#errorDiv2").html(res.result);
				return false;
			}
		},"json");
}
function changeSendVoiceCodeWithDrawToWait(btnId) {
	var txt = "秒";
	var startData = 60;
	$("#" + btnId).removeAttr("onclick");
	inter1 = setInterval(function() {
		if (startData <= 0) {
			$("#" + btnId).html("语音验证码");
			$("#" + btnId).attr("onclick","sendVoiceCodeWithDraw();");
			clearInterval(inter1);
		} else {
			startData--;
			$("#" + btnId).html(startData + txt);
		}
	}, 1000);
}
/**********安全中心的js代码***************/
function updateLoginPass(){
	var title = "修改登录密码";
	var text ='<table width="300" cellspacing="0" cellpadding="0" class="zhcc-table-2" style="margin-left:40px;">';
		text+= '<tr> <td width="87"><span>旧密码</span></td><td width="213"><input type="password" id="oldPwd"></td></tr>';
		text+=' <tr><td><span>新密码</span></td><td><input type="password" id="newPwd"></td></tr>';
		text+=' <tr><td><span>确认密码</span></td><td><input type="password" id="repNewPwd"></td></tr>';
		text+='</table>';
		text+='<div class="error-div"><div class="error" id="errorDiv"></div></div>';
		var d=new UpdateDialog(title,text,"确定",function(){
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			var repNewPwd=$("#repNewPwd").val();
			var reg =/^[a-zA-Z0-9]{6,16}$/;
			if ($.trim(oldPwd) == "") {
				$("#errorDiv").html("旧密码不能为空");
				return;
			}
			if ($.trim(newPwd) == "") {
				$("#errorDiv").html("新密码不能为空");
				return;
			}
			if(newPwd.length<6||newPwd.length>16){
				$("#errorDiv").html("新密码只能6-16位数字和字母混合组成");
				return false;
			}
			if (!reg.test(newPwd)) {
				$("#errorDiv").text("新密码只能由数字和字母组成");
				return;
			}
			if ($.trim(repNewPwd) == "") {
				$("#errorDiv").html("确认密码不能为空");
				return;
			}
			if(repNewPwd!=newPwd){
				$("#errorDiv").text("两次新密码输入不一致");
				return;
			}
			ajaxReuqestSynchronize(systemConfig.path + "/user/ChangePwd.ajax", {
				"oldPwd" : oldPwd,
				"newPwd" : newPwd
			}, function(r) {
				if (r.resCode == "login") {
					location.href = systemConfig.path + "/user/login.jsp";
				}else if (r.resCode == "oldPwdError") {
					$("#errorDiv").html("旧密码输入不正确");
					return;
				}else if(r.resCode == "newPwdError"){
					$("#errorDiv").html("修改密码不能和原密码相同");
					return;
				}else if (r.resCode == "success") {
					d.closeDia();
					ShowDialogSuccess("更改成功", function(){location.href = systemConfig.path +"/user/LogOut.do";});
				}
			}, "json");
		},"取消",null);
		d.show();
}
/********我的配资和提前还款的js************/
var interest = null;
var duedate = null;
var circle = 1;
function calInterest(cycle ,lever,amount) {
	var interest = DM.utils.calcInterest(amount, lever,parseInt(cycle),"fix_mode");
	return interest;
}
function renew(financeId){
	//获取需要显示的数据
	ajaxReuqestAsync(systemConfig.path + "/finance/preRenew.ajax",{"financeId" : financeId},function(r) {
		if(r.rescode == "success"){
			interest = calInterest(1 ,r.result.amount);
			duedate  = r.result.dueDate;
			var date  = getNewDate(duedate, 1);
			new ShowWypzDia("续配", r.result.amount,r.result.availableDays , calInterest(1 ,r.result.amount), date, "确认续配", function(){
				ajaxReuqestAsync(systemConfig.path +"/finance/doPreRenew.ajax",{
					"interest" : interest,
					"circle" : circle,
					"financeId" : r.result.financeId,
					"projectId" : r.result.projectId
				},function(r) {
					//清空全局变量
					circle = 1;
					if(r.rescode == "success"){
						ShowDialogSuccess(r.msg , null);
						getFinancingHistory("financing");
					}else if(r.rescode == "error"){
						ShowDialogFail(r.msg);
					}
				});
			},"关闭", null).show();
		}else if (r.rescode == "error"){
			ShowDialogFail(r.msg);
		}else if(r.rescode == "login"){
			location.href = systemConfig.path + "/user/login.jsp";
		}
	});
}
function changeCircle(obj ,amount){
	var date = getNewDate(duedate, obj.value);
	interest = calInterest(obj.value , amount);
	circle = obj.value;
	$("#interest").html(interest);
	$("#interest_to_xy").html(interest);
	$("#duedate_dia").html(date);
}

function getNewDate(date , amount){
	date = date.replace(/-/g, '/');
	var d = new Date(date);
	//计算 第amount月 的最后时间
	//得到下个月的第一天
	var y = d.getFullYear();
	var m = d.getMonth() +  parseInt(amount) +1;
	if(m > 12){
		m = m-12;
		y = y + 1;
	}
	var day = 1;
	var nextDate = y+"/"+m+"/"+day;
	var nextMonthDate = getCurrentMonthLastDay(new Date(nextDate));
	//判断当前日期的天数 是否小于 下个月天数  ，如果小于 月份正常+1  否则当前日期 则作为最后日期
	if(d.getDate() <= nextMonthDate.getDate()){
		d.setMonth(d.getMonth() + parseInt(amount));
		//d.setDate(d.getDate()-1);
		time =dateFormat(d);
	}else {
		//nextMonthDate.setDate(nextMonthDate.getDate()-1);
		time = dateFormat(nextMonthDate);
	}
	return time;
}

//格式化日期
function dateFormat(d){
	var y = d.getFullYear();
	var m = d.getMonth() + 1;
	var day = d.getDate();
	if (m < 10){
		m = "0"+m;
	}
	if(day < 10){
		day = "0" + day;
	}
	return y+"-" + m+"-" + day;
}
//获取 d 月的最后一天的日期
function getCurrentMonthLastDay(d){
     var current=d;
     var currentMonth=current.getMonth();
     var nextMonth=++currentMonth;
 	 var nextMonthDayOne =new Date(current.getFullYear(),nextMonth,1);
     var minusDate=1000*60*60*24;
     return new Date(nextMonthDayOne.getTime()-minusDate);
}

function select(showContent,selfObj,type){
	selectTag(showContent,selfObj);
	getFinancingHistory(type);
}
function selectTag(showContent,selfObj){
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for(var i=0; i<taglength; i++){
		tag[i].className = "";
	}
	selfObj.parentNode.className = "selectTag";
	for(var i=0; j=document.getElementById("tagContent"+i); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";
}
function turnPageNo1(page){
	$("#pageNo1").val(page);
	$('#tag1').click();	
}
function turnPageNo2(page){
	$("#pageNo2").val(page);
	$('#tag2').click();	
}
function turnPageNo3(page){
	$("#pageNo3").val(page);
	$('#tag3').click();	
}
/*获取配资记录*/
function getFinancingHistory(type,pageNo){
	if(type=="apping"){
		var status=2;
		var pageNo=pageNo;
		var pageSize=10;
		ajaxReuqestAsync(systemConfig.path +"/finance/GetFinancingHistory.do", {"status":status,"pageNo":pageNo,"pageSize":pageSize}, function(r){
			if(r.resCode=="login"){
				location.href=systemConfig.path +"/user/login.jsp";
			}
			var list = r.financingList;
			var sumPages = r.sumPages;
			var pageNo = r.pageNo;
			var pageList = r.pageList;
			var htmPage = "";
			$("#page1").html("");
			if (list != null && list.length > 0){
				if(pageNo==1){
					htmPage+='<span class="disabled"> &lt; 上一页 </span>';
				}else{
					var frontPage=pageNo-1;
					htmPage+='<a href="javascript:turnPageNo1('+frontPage+');"> &lt; 上一页 </a>';
				}
				for(var i=0;i<pageList.length;i++){
					var page=pageList[i];
					if(page==pageNo){
						htmPage+='<span class="current">'+page+'</span>';
					}else{
						htmPage+='<a href="javascript:turnPageNo1('+page+');">'+page+'</a>';
					}
				}
				if(pageNo==sumPages){
					htmPage+='<span class="disabled"> &lt; 下一页 </span>';
				}else{
					var nextPage=pageNo+1;
					htmPage+='<a href="javascript:turnPageNo1('+nextPage+');">下一页 &gt;</a>&nbsp;&nbsp;&nbsp;';
				}
				htmPage+="<span>"+sumPages+"</span>页";
				$("#page1").append(htmPage);
			}
			var html="";
			var trs=$("#" + type).find("tr");
			trs.not(trs.eq(0)).remove();
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var appDate=formateDateAllowString(obj.appDate);
					var financingDate = obj.financingDate;
					var financeName=obj.financeNameDisplay;
					var financingAmount=DM.utils.farmatMoney(obj.financingAmount,0);
					var repayAmount=DM.utils.farmatMoney(obj.repayAmount,0);
					var financingRate=formateDecimalToPercent(obj.financingRate/12);
					var dueDate=formateDateAllowString(obj.dueDate);
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					if(financingDate != null && financingDate !=""){
						appDate = formateDateAllowString(obj.financingDate);
					}
					html+="<td><p>"+appDate+"</p><p></p></td>"
					  +"<td><p>"+dueDate+"</p><p></p></td>"
		              +"<td><p>"+financeName+"</p></td>"
		              +"<td><p>"+financingAmount+"</p></td>"
		              +"<td><p>"+repayAmount+"</p></td>"
		              +" <td><p>"+financingRate+"</p></td>";
				}
			}else{
				html += "<tr><td colspan='10'>暂无记录</td></tr>";
			}
			$("#" + type).append(html);
		});
	}
	if(type=="financing"){
		var status=6;
		ajaxReuqestAsync(systemConfig.path +"/finance/GetFinancingHistory.do", {"status":status,"pageNo":pageNo,"pageSize":pageSize}, function(r){
			if(r.resCode=="login"){
				location.href=systemConfig.path +"/user/login.jsp";
			}
			var list = r.financingList;
			var sumPages = r.sumPages;
			var pageNo = r.pageNo;
			var pageList = r.pageList;
			var htmPage = "";
			$("#page2").html("");
			if (list != null && list.length > 0){
				if(pageNo==1){
					htmPage+='<span class="disabled"> &lt; 上一页 </span>';
				}else{
					var frontPage=pageNo-1;
					htmPage+='<a href="javascript:turnPageNo2('+frontPage+');"> &lt; 上一页 </a>';
				}
				for(var i=0;i<pageList.length;i++){
					var page=pageList[i];
					if(page==pageNo){
						htmPage+='<span class="current">'+page+'</span>';
					}else{
						htmPage+='<a href="javascript:turnPageNo2('+page+');">'+page+'</a>';
					}
				}
				if(pageNo==sumPages){
					htmPage+='<span class="disabled"> &lt; 下一页 </span>';
				}else{
					var nextPage=pageNo+1;
					htmPage+='<a href="javascript:turnPageNo2('+nextPage+');">下一页 &gt;</a>&nbsp;&nbsp;&nbsp;';
				}
				htmPage+="<span>"+sumPages+"</span>页";
				$("#page2").append(htmPage);
			}
			var html="";
			var trs=$("#" + type).find("tr");
			trs.not(trs.eq(0)).remove();
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var appDate=formateDateAllowString(obj.appDate);
					var financeName=obj.financeNameDisplay;
					var financingAmount=DM.utils.farmatMoney(obj.financingAmount,0);
					var repayAmount=DM.utils.farmatMoney(obj.repayAmount,0);
					var financingDate = obj.financingDate;
					var financingRate=formateDecimalToPercent(obj.financingRate/12);
					var dueDate=formateDateAllowString(obj.dueDate);
					var financingId=parseInt(obj.financingId);
					var status=parseInt(obj.status);
					var num   =parseInt(obj.num);
					var renrewnum = parseInt(obj.renrewnum);
					var statusStr=obj.statusStr;
						html += "<tr";
						if (i % 2 == 0) {
							html += " class='odd'>";
						} else {
							html += ">";
						}
						if(financingDate != null && financingDate !=""){
							appDate = formateDateAllowString(obj.financingDate);
						}
						html+="<td><p>"+appDate+"</p><p></p></td>"
							+"<td><p>"+dueDate+"</p><p></p></td>"
							+"<td><p>"+financeName+"</p></td>"
							+"<td><p>"+financingAmount+"</p></td>"
							+"<td><p>"+repayAmount+"</p></td>"
							+"<td><p>"+financingRate+"</p></td>";
						if(renrewnum != 0){
							html+="<td><p><span/>项目续配审核中</span></p></td>";
						}else {
							if(status==6){
				            	  html+="<td><label><input id='prePary' type='button' class='btn-in-2' value='提前还款' onclick='ViewPreRepayDetail("+financingId+")'/></label>";
				            	  if(num < 6){
					            	  html+="<label><input id='prePary' type='button' class='btn-out-2 mar-t-5' value='续配' onclick='renew("+financingId+")'/></label></td>";
				            	  }else{
					            	  html+="<label><input id='prePary' type='button' class='btn-out-2 mar-t-5' value='续配' style='color:#d8d8d8;border-color:#d8d8d8'/></label></td>";
				            	  }
				            	  
				              }else{
				            	  html+="<td><p><span/>"+statusStr+"</span></p></td>";
				              }
						}
				}
			}else{
				html += "<tr><td colspan='10'>暂无记录</td></tr>";
			}
			$("#" + type).append(html);
		});
	}
	if(type=="end"){
		var status=8;
		ajaxReuqestAsync(systemConfig.path +"/finance/GetFinancingHistory.do", {"status":status,"pageNo":pageNo,"pageSize":pageSize}, function(r){
			if(r.resCode=="login"){
				location.href=systemConfig.path +"/user/login.jsp";
			}
			var list = r.financingList;
			var sumPages = r.sumPages;
			var pageNo = r.pageNo;
			var pageList = r.pageList;
			var htmPage = "";
			$("#page3").html("");
			if (list != null && list.length > 0){
				if(pageNo==1){
					htmPage+='<span class="disabled"> &lt; 上一页 </span>';
				}else{
					var frontPage=pageNo-1;
					htmPage+='<a href="javascript:turnPageNo3('+frontPage+');"> &lt; 上一页 </a>';
				}
				for(var i=0;i<pageList.length;i++){
					var page=pageList[i];
					if(page==pageNo){
						htmPage+='<span class="current">'+page+'</span>';
					}else{
						htmPage+='<a href="javascript:turnPageNo3('+page+');">'+page+'</a>';
					}
				}
				if(pageNo==sumPages){
					htmPage+='<span class="disabled"> &lt; 下一页 </span>';
				}else{
					var nextPage=pageNo+1;
					htmPage+='<a href="javascript:turnPageNo3('+nextPage+');">下一页 &gt;</a>&nbsp;&nbsp;&nbsp;';
				}
				htmPage+="<span>"+sumPages+"</span>页";
				$("#page3").append(htmPage);
			}
			var html="";
			var trs=$("#" + type).find("tr");
			trs.not(trs.eq(0)).remove();
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var appDate=formateDateAllowString(obj.appDate);
					var financeName=obj.financeNameDisplay;
					var financingAmount=DM.utils.farmatMoney(obj.financingAmount,0);
					var repayAmount=DM.utils.farmatMoney(obj.repayAmount,0);
					var financingRate=formateDecimalToPercent(obj.financingRate/12);
					var dueDate=formateDateAllowString(obj.dueDate);
					var statusStr=obj.statusStr;
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					if(financingDate != null && financingDate !=""){
						appDate = formateDateAllowString(obj.financingDate);
					}
					html+="<td><p>"+appDate+"</p><p></p></td>"
						+"<td><p>"+dueDate+"</p><p></p></td>"
		             	+"<td><p>"+financeName+"</p></td>"
		             	+"<td><p>"+financingAmount+"</p></td>"
		              	+"<td><p>"+repayAmount+"</p></td>"
		              	+" <td><p>"+financingRate+"</p></td>"
		              	+"<td><p>"+statusStr+"</p></td>";
				}
			}else{
				html += "<tr><td colspan='10'>暂无记录</td></tr>";
			}
			$("#" + type).append(html);
		});
	}
}
/*提前还款*/
function ViewPreRepayDetail(financingId){
	ajaxReuqestAsync(systemConfig.path+"/finance/ViewPreRepayDetail.ajax", {"financingId":financingId}, function(r){
		if(r.result=="login"){
			location.href=systemConfig.path+"/user/login.jsp";
		}else if(r.result=="repayplan_state_invalide"){
			ShowDialogFail("提前还款无法申请，请检测还款计划表状态是否正常。");
		}else {
			var principal=r.prd.repayAmount+"";
			var weiyuejin=r.prd.totalInterest+"";
			var leftAmount=r.leftAmount+"";
			var stockBalance=r.stockBalance+"";
			var principalDisplay=DM.utils.farmatMoney(principal,2);
			var weiyuejinDisplay=DM.utils.farmatMoney(weiyuejin,2);
			var leftAmountDisplay=DM.utils.farmatMoney(leftAmount,2);
			var stockBalanceDisplay=DM.utils.farmatMoney(stockBalance,2);
			var title = "设置登录密码";
			var text ='<table width="400" border="0" cellspacing="0" cellpadding="0" class="zhcc-table-2" style="margin-left:40px;">';
				text+= '<tr> <td width="130"><span>现金账户余额:</span></td><td width="270"><div>'+leftAmountDisplay+'</div></td> </tr>';
				text+=' <tr><td><span>股票账户余额:</span></td><td><div>'+stockBalanceDisplay+'</div></td></tr>';
				text+=' <tr><td><span>应还本金:</span></td><td><div>'+principalDisplay+'</div></td></tr>';
				text+=' <tr><td><span>应还违约金:</span></td><td><div>'+weiyuejinDisplay+'</div></td></tr>';
				text+='</table>';
				text+='<div class=""><div class="error" id="errorDiv"></div><div>';
			var confirmDialog=new UpdateDialog("提前还款确认", text, "确认", function(){
				ajaxReuqestAsync(systemConfig.path+"/finance/PreRepay.ajax", {"financingId":financingId}, function(r){
					confirmDialog.closeDia();
					if(r.resCode==-1){
						location.href=systemConfig.path+"/user/login.jsp";
					}else if(r.resCode==0){
						ShowDialogSuccess("恭喜您，提前还款申请已提交，请等待后台处理...", function(){
							location.href=systemConfig.path+"/finance/ViewFinancingHistory.do?type=financing";
						});
					}else if(r.resCode==1){
						ShowDialogFail("股票账户，应还融资本金余额不足。");
					}else if(r.resCode==2){
						ShowDialogFail("暂不能做提前还款。");
					}
				});
			}, "取消", null);
			confirmDialog.show();
		}
	});
}
/*********盈利转出的js**********/
function ylzc(){
	var moneyVal=$("#tradeAmountInput").val();
	if(moneyVal==""){
		$("#errorDiv span").html("<img src='images/icon_07.png'>转出金额不能为空！");
		return;
	}
	if(!valid.isMoney(moneyVal)){
		$("#errorDiv span").html("<img src='images/icon_07.png'>您输入的转出金额不正确！");
		return;
	}
	moneyVal=DM.utils.formateMoneyToDecimal(moneyVal);
	moneyVal=moneyVal.toFixed(2);
	if(moneyVal<=0){
		$("#errorDiv span").html("<img src='images/icon_07.png'>转出金额不能小于等于0！");
		return;
	}
	var maxTransferAmount = $("#maxTransferAmount").val();
	maxTransferAmount=DM.utils.formateMoneyToDecimal(maxTransferAmount);
	maxTransferAmount=maxTransferAmount.toFixed(2);
	if(eval(moneyVal)>eval(maxTransferAmount)){
		$("#errorDiv span").html("<img src='images/icon_07.png'>转出金额不能大于盈利可转出金额！");
		return;
	}
	if(eval(maxTransferAmount)<=100){
		if(eval(moneyVal)<eval(maxTransferAmount)){
			$("#errorDiv span").html("<img src='images/icon_07.png'>盈利可转出金额少于100元，只能一次性提取");
			return;
		}
		moneyVal=maxTransferAmount;
	}
	var tradeAmount=moneyVal;
	var type="financingacct";
	var tradeinout="out";
	var portfolioId=$("#portfolioId").val();
	ajaxReuqestSynchronize(systemConfig.path+"/finance/FinancingAcctInOut.do", {"tradeAmount":tradeAmount,"type":type,"tradeinout":tradeinout,"portfolioId":portfolioId}, function(r){
		if(r.resCode=="login"){
			location.href=systemConfig.path+"/user/login.jsp";
		}else if(r.resCode=="success"){
			ShowDialogSuccess("您的转出申请已经提交，请等待后台处理。。。", function(){
				location.href=systemConfig.path+"/finance/FinancingAcctInOutInit.do?tradeInOut=out.do";
			});
		}else if(r.errorInfo!=null){
			ShowDialogFail(r.errorInfo);
		}
	});
}
/**********追加保证金js***********/
function goToCharge(){
	location.href=systemConfig.path+"/user/UserChargeInit.do";
}
function gszr(){
	var moneyVal=$("#tradeAmountInput").val();
	if(moneyVal==""){
		$("#errorDiv span").html("<img src='images/icon_07.png'>转入金额不能为空！");
		return;
	}
	if(!valid.isMoney(moneyVal)){
		$("#errorDiv span").html("<img src='images/icon_07.png'>您输入的转入金额不正确！");
		return;
	}
	moneyVal=DM.utils.formateMoneyToDecimal(moneyVal);
	var leftmoney=$("#leftmoney").val();
	if(moneyVal<=0){
		$("#errorDiv span").html("<img src='images/icon_07.png'>转出金额不能小于等于0！");
		return;
	}
	if(moneyVal>leftmoney){
		$("#errorDiv span").html('您当前现金账户可用余额不足，请<a href="javascript:goToCharge();" style="color:black">【充值】</a>');
		return;
	}
	else{
		var tradeAmount=moneyVal;
		var type="financingacct";
		var tradeinout="in";
		var portfolioId=$("#portfolioId").val();
		ajaxReuqestAsync(systemConfig.path+"/finance/FinancingAcctInOut.do", {"tradeAmount":tradeAmount,"type":type,"tradeinout":tradeinout,"portfolioId":portfolioId}, function(r){
			if(r.resCode=="login"){
				location.href=systemConfig.path+"/user/login.jsp";
			}else if(r.resCode=="stock_account_is_not_set"){
				$("#errorDiv span").html("对不起，您还没有开通股票账户");
				return;
			}else if(r.resCode=="project_is_not_set"){
				$("#errorDiv span").html("对不起，您还没有配资项目,不允许追加保证金");
				return;
			}else if(r.resCode=="success"){
				ShowDialogSuccess("您的转入申请已经提交，请等待后台处理。。。", function(){
					location.href=systemConfig.path+"/finance/FinancingAcctInOutInit.do?tradeInOut=in";
				});
			}else if(r.resCode=="principal"){
				ShowDialogFail("对不起，本金账户余额不足请先<a href='javascript:goToCharge();' style='color:red'>【充值】</a>");
			}else if(r.resCode=="stock"){
				$("#errorDiv span").html("对不起，股票账户余额不足。");
				return;
			}else{
				ShowDialogFail(r.errorInfo);
				return;
			}
		});
	}
}
/**查询历史支付宝账户*/
function findAlipayAccountList(selectId){
	var optsHtml="";
	ajaxReuqestSynchronize(systemConfig.path+"/openapi/findAlipayAccountList.ajax", {}, function(r){
		if(r.info.rescode=="success"){
			var list=r.info.result;
			if(list.length>0){
				optsHtml+="<option>请选择</option>";
				for(var i=0;i<list.length;i++){
					var opt=list[i];
					optsHtml+="<option value='"+opt+"'>"+opt+"</option>";
				}
			}else{
				optsHtml="<option>暂无记录</option>";
			}
			$("#"+selectId).html(optsHtml);
		}
	});
}
