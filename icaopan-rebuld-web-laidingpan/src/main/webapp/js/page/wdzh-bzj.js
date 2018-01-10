var MA = myAccount = {};
//我的账户 ---我的配资分页显示
MA.gszr = function (){
	var moneyVal=$("#recharge-input").val();
	if(moneyVal==""){
		$("#errorDiv").html("追加金额不能为空！");
		return;
	}
	if(!valid.isMoney(moneyVal)  || /^0+/ig.test(moneyVal)){
		$("#errorDiv").html("您输入的追加金额不正确！");
		return;
	}
	if(moneyVal<0){
		$("#errorDiv").html("追加金额不能小于0！");
		return;
	}
	moneyVal=DM.utils.formateMoneyToDecimal(moneyVal);
	var leftmoney=$("#leftmoney").val();
	if(!valid.isMoney(moneyVal)){
		$("#errorDiv").html("您输入的追加金额不正确！");
		return;
	}
	if(moneyVal>leftmoney){
		$("#errorDiv").html("您当前现金账户可用余额不足，请<a href="+systemConfig.path+"/user/UserChargeInit.do style='color:black'>【充值】</a>");
		return;
	}
	if(moneyVal==''||moneyVal==0||moneyVal==null){
		$("#errorDiv").html("追加金额不能为0");
		return;
	}else{
		var tradeAmount=moneyVal;
		var type="financingacct";
		var tradeinout="in";
		var portfolioId=$("#portfolioId").val();
		$.ajax({
			url:systemConfig.path+"/finance/FinancingAcctInOut.ajax",
			type:'post',
			data:{"tradeAmount":tradeAmount,"type":type,"tradeinout":tradeinout,"portfolioId":portfolioId},
			success:function(r){
				if(r.resCode=="login"){
					location.href=systemConfig.path+"/user/login.jsp";
				}else if(r.resCode=="stock_account_is_not_set"){
					$("#errorDiv").html("对不起，您还没有开通股票账户");
					return;
				}else if(r.resCode=="project_is_not_set"){
					$("#errorDiv").html("对不起，您还没有申请实盘项目,不允许追加保证金");
					return;
				}else if(r.resCode=="success"){
					ShowDialogSuccess("您的申请已提交，审核通过后将短信通知您。", function(){
						location.href=systemConfig.path+"/finance/FinancingAcctInOutInit.do?tradeInOut=in";
					});
				}else if(r.resCode=="principal"){
					ShowDialogFail("对不起，本金账户余额不足请先<a href="+systemConfig.path+"/user/UserChargeInit.do style='color:red'>【充值】</a>");
				}else if(r.resCode=="stock"){
					$("#errorDiv").html("对不起，股票账户余额不足。");
					return;
				}else{
					ShowDialogFail(r.errorInfo);
					return;
				}
			}
		});
	}
};


$(function(){
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	$('#aside-nav li:eq(3)').addClass('active');
	
	$.ajax({
		url:systemConfig.path +'/user/GetLeftAmount.ajax', 
		success:function(e){
			$('#availableAmountDd').html(DM.utils.farmatMoney(e.leftAmount, 2));
		}
	});
});