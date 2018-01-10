/*********盈利提取的js**********/
var MA = myAccount = {};
MA.flag=true;
MA.ylzc = function (){
	var moneyVal=$("#tradeAmountInput").val();
	if($.trim(moneyVal)==""){
		$("#errorDiv").html("提取金额不能为空！");
		return;
	}
	if(!valid.isMoney(moneyVal) || /^0+/ig.test(moneyVal)){
		$("#errorDiv").html("您输入的提取金额不正确！");
		return;
	}
	if(moneyVal==0||moneyVal==null){
		$("#errorDiv").html("提取金额不能为0！");
		return;
	}
	moneyVal=DM.utils.formateMoneyToDecimal(moneyVal);
	moneyVal=moneyVal.toFixed(2);
	var maxTransferAmount = $("#maxTransferAmount").val();
	maxTransferAmount=DM.utils.formateMoneyToDecimal(maxTransferAmount);
	maxTransferAmount=maxTransferAmount.toFixed(2);
	if(eval(moneyVal)>eval(maxTransferAmount)){
		$("#errorDiv").html("提取金额不能大于盈利可提取金额！");
		return;
	}
	if(eval(maxTransferAmount)<=100){
		if(eval(moneyVal)<eval(maxTransferAmount)){
			$("#errorDiv").html("盈利可提取金额少于100元，只能一次性提取");
			return;
		}
		moneyVal=maxTransferAmount;
	}
	var portfolioId=$("#portfolioId").val();
	if(!MA.flag){
		return false;
	}
	MA.flag = false;
	$.ajax({
		url:systemConfig.path+"/finance/FinancingAcctInOut.ajax",
		type:'post',
		async:false,
		data:{"tradeAmount":moneyVal,"type":"financingacct","tradeinout":"out","portfolioId":portfolioId},
		success:function(r){
			MA.flag=true;
			if(r.resCode=="login"){
				location.href=systemConfig.path+"/user/login.jsp";
			}else if(r.resCode=="success"){
				ShowDialogSuccess("您的提取申请已经提交，请等待后台处理。。。", function(){
					$('.pop').remove();
					$('#tradeAmountInput').val('');
					window.location.reload();
				});
			}else if(r.errorInfo!=null){
				ShowDialogFail(r.errorInfo);
			}
		}
	});
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	$('#aside-nav li:eq(4)').addClass('active');
	
});