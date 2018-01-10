function doCharge(){
	var moneyVal=$("#tradeAmountInput").val();
	if($.trim(moneyVal)==""){
		$(".recharge-extend").html("请输入充值金额！");
		return;
	}
	if(!$.isNumeric(moneyVal) || !valid.isMoney(moneyVal) || /^0+/ig.test(moneyVal)){
		$(".recharge-extend").html("您输入的充值金额不正确！");
		return;
	}
	if(moneyVal<1){
		$(".recharge-extend").html("每笔的充值金额最少为1元!");
		return;
	}
	if(moneyVal>500000){
		$(".recharge-extend").html("每笔的充值金额最大为500000元!");
		return;
	}else{
		$(".recharge-extend").html('');
		var isDevelop=$("#isDevelop").val();
	    $.ajax({
	    	url:systemConfig.path+"/user/MakePrincalTrade.ajax",
	    	data: {"tradeAmount":moneyVal,"thirdpayername":"huichao"},
	    	success:function(r){
		    	if(r.resCode=="login"){
		    		location.href=systemConfig.path+"/user/login.jsp";
		    	}else if(r.resCode=="validateUser"){
		    		ShowDialogNotify("请先通过实名认证,<a style='text-decoration: underline;' href='"+systemConfig.path+"/account/wdzh_aqzx_smrz.jsp'>去认证>></a>", null);
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
			    		window.open(systemConfig.path+"/user/DoUserCharge.do?tradeNo="+r.tradeNo);
		    		}
		    	}
		    }
	    });
	}
}

/******支付宝充值相关代码********/
function doAliCharge(){
	var moneyVal=$("#aliTradeAmount").val();
	var bankCode=$("#epay-charge-amount").val();
	if($.trim(bankCode)==""){
		$("#alipayErrorDiv").html("充值账号不能为空");
		return;
	}
	if($.trim(moneyVal)==""){
		$("#alipayErrorDiv").html("充值金额不能为空");
		return;
	}
	if($.trim(bankCode).length<6||$.trim(bankCode).length>50){
		$("#alipayErrorDiv").html("请输入正确的支付宝账号");
		return;
	}
	if(!$.isNumeric(moneyVal)){
		$("#alipayErrorDiv").html("您输入的充值金额不正确");
		return;
	}else if(moneyVal<0){
		$("#alipayErrorDiv").html("您输入的充值金额不正确");
		return;
	}
	//moneyVal=formateMoneyToDecimal(moneyVal);
	if(moneyVal==''||moneyVal==0||moneyVal==null){
		$("#alipayErrorDiv").html("充值金额不能为0");
		return;
	}
	if(moneyVal<1){
		$("#alipayErrorDiv").html("每笔的充值金额最少为1元");
		return;
	}
	if(moneyVal<1||moneyVal>500000){
		$("#alipayErrorDiv").html("每笔的充值金额最大为500000元");
		return;
	}else{
		$("#alipayErrorDiv").html("");
		$.ajax({
			url:systemConfig.path+"/user/MakePrincalTrade.ajax",
			data:{"tradeAmount":moneyVal,"thirdpayername":"alipay","bankCode":bankCode,"isOffLinePay":"true"},
			success:function(r){
		    	if(r.resCode=="login"){
		    		location.href=systemConfig.path+"/user/login.jsp";
		    	}else if(r.resCode=="validateUser"){
		    		ShowDialogNotify("请先通过实名认证,<a style='text-decoration: underline;' href='"+systemConfig.path+"/account/wdzh_aqzx_smrz.jsp'>去认证>></a>", null);
		    	}else if(r.resCode=="success"){
		    		$(".epay-recharge").hide();
		    		$(".epay-recharge-inner").show();
		    		$("#aliAccountNoConfirm").html(r.bankCode);
		    		$("#aliTradeAmountConfirm").html(r.tradeAmount);
		    	}else{
		    		ShowDialogFail(r.errorInfo);
		    	}
		    }
		});
	}
}

function trial(dataId){
	var availableBalance = $("#availableBalance").val();
	availableBalance = DM.utils.formateMoneyToDecimal(availableBalance);
	var text='';
	var title='充值计算器';
	text+='<table width="300" border="0" cellspacing="0" cellpadding="0" class="zhcc-table-2" style="margin-left:40px;">';
	text+='<tr><td width="90">保证金：</td><td><input type="text" id="tryChargeAmount" onkeyup="changetryChargeAmount();"></td></tr>';
	text+='<tr><td>申请倍数：</td><td><select id="tryPzbs" style="width: 60px;" onchange="changetryPzbs();"><option value="1" selected="selected">1倍</option><option value="2">2倍</option><option value="3">3倍</option></select></td></tr>';
	text+='<tr><td>申请月份：</td><td><select id="tryMonth"  onchange="changetryMonth();"><option value="1" selected="selected">1个月</option><option value="2">2个月</option><option value="3">3个月</option><option value="4">4个月</option><option value="5">5个月</option><option value="6">6个月</option></select></td></tr>';
	text+='<tr><td>系统使用费率：</td><td><div id="interest">0</div></td></tr>';
	text+='</table>';
	text+='<div class="error txt-c text-orange" id="errorDiv_chargeTry"></div>';
	var dia = new TryChargeDialog(title, text, "试算", function(){
		var availableBalance = $("#availableBalance").val();
		availableBalance = DM.utils.formateMoneyToDecimal(availableBalance);
		var tryChargeAmount = $.trim($("#tryChargeAmount").val());
		tryChargeAmount = DM.utils.formateMoneyToDecimal(tryChargeAmount);
		 // 判断本金和申请金额是否是100的整数倍
	    if(!DM.utils.isNumberByN(tryChargeAmount, 100)){
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
		var interest = DM.utils.calcInterest(financeAmount,tryPzbs, parseInt(cycle),modeType);
		var charge = tryChargeAmount+(interest/100)*financeAmount;
		var tryCharge;
		if(charge > availableBalance){
			tryCharge = charge - availableBalance;
		}else{
			tryCharge = 0;
		}
		tryCharge = Math.ceil(tryCharge);                                                                                                                                    
		$("#"+dataId).val(tryCharge);
/*		var availableAmount=DM.utils.formateMoneyToDecimal($('#availableAmountDd').text());
		if(tryCharge<=availableAmount){
			$("#"+dataId).val('0');
		}else{
			$("#"+dataId).val(tryCharge-availableAmount);
		}*/
		dia.closeDia();
	},"取消", null);
	dia.show();
};

function changetryChargeAmount(financeAmount,cycle,modeType){
	var tryChargeAmount = $("#tryChargeAmount").val();
	tryChargeAmount = DM.utils.formateMoneyToDecimal(tryChargeAmount);
	var tryPzbs = parseInt($("#tryPzbs").val());
	var cycle = parseInt($("#tryMonth").val());
	var modeType = "fix_mode";
	var financeAmount = tryChargeAmount*tryPzbs;
	var interest = DM.utils.calcInterest(financeAmount,tryPzbs,cycle,modeType);
	$("#interest").html(interest);
}

function changetryPzbs(){
	var tryChargeAmount = $("#tryChargeAmount").val();
	tryChargeAmount = DM.utils.formateMoneyToDecimal(tryChargeAmount);
	var tryPzbs = parseInt($("#tryPzbs").val());
	var cycle = parseInt($("#tryMonth").val());
	var modeType = "fix_mode";
	var financeAmount = tryChargeAmount*tryPzbs;
	var interest = DM.utils.calcInterest(financeAmount,tryPzbs,cycle,modeType);
	$("#interest").html(interest);
}

function changetryMonth(){
	var tryChargeAmount = $("#tryChargeAmount").val();
	tryChargeAmount = DM.utils.formateMoneyToDecimal(tryChargeAmount);
	var tryPzbs = parseInt($("#tryPzbs").val());
	var cycle = parseInt($("#tryMonth").val());
	var modeType = "fix_mode";
	var financeAmount = tryChargeAmount*tryPzbs;
	var interest = DM.utils.calcInterest(financeAmount,tryPzbs,cycle,modeType);
	$("#interest").html(interest);
}

$(function(){
	$.ajax({
		url:systemConfig.path +'/user/GetLeftAmount.ajax', 
		success:function(e){
			$('#availableAmountDd').html(DM.utils.farmatMoney(e.leftAmount, 2));
		}
	});
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	$('#aside-nav li:eq(1)').addClass('active');
	$('.tab_plugin').tabSwitched();
	
	var marginAmount=window.location.href.split('marginAmount=')[1];
	if(marginAmount){
		$('#tradeAmountInput').val(marginAmount);
	}
	
	
	$('#epay-charge').click(function(){
		var epay_account=$('#epay-account');
		var epay_charge=$('#epay-charge-amount');
		if(epay_account=='' || epay_account == null){
			$("#errorDiv").html("支付宝帐户不能为空!");
			return;
		}
		if(epay_charge<100 || epay_charge>500000){
			$("#errorDiv").html("单笔的充值金额最大为50万元<br />充值金额最小单位为100元");
			return;
		}
		if(epay_charge!=0 && valid.isNumber(epay_charge)){
			$('#aliAccountNoConfirm').html(epay_account);
			$('#aliTradeAmountConfirm').html(epay_charge);
			$('.epay-recharge').hide();
			$('.epay-recharge-inner').show();
		}else{
			$("#errorDiv").html("充值金额不正确!");
		}
	});
	$('#change-amount').click(function(){
		$('.epay-recharge').show();
		$('.epay-recharge-inner').hide();
	});
	
	$.ajax({
		url: systemConfig.path + "/user/ViewMyAccountCenterIndex.ajax",
		success: function(r) {
			$("#availableAmountDd").html(DM.utils.farmatMoney(r.accountInfo.leftAmount, 2));
		}
	});
});