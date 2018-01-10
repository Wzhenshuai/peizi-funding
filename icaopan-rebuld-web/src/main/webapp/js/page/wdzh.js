var isVerified=function(e){
	var r;
	var _node=$('.verify_icon');
	if(e.length>0){
		r='（'+e+'）';
		_node.addClass('right-icon');
		$('#verify_status').html('已填写');
	}else{
		r="未认证，我要<a class='text-orange' href='"+ systemConfig.path +"/account/wdzh_aqzx_smrz.jsp'>身份认证</a>";
		_node.addClass('wrong-icon');
		$('#verify_status').html('未填写');
	}
	return r;
};

function queryStockAccount() {
	$.ajax({
		url: systemConfig.path + "/user/ViewMyAccountCenterIndex.ajax",
		success: function(r) {
			$("#login-user").html(r.accountInfo.mobileHidePart);
			$("#realName").html(r.accountInfo.realNameHidePart);
			var verifyInfo=isVerified(r.accountInfo.idNumberHidePart);
			$("#idNumber").html(verifyInfo);
			$("#yhk-num").html(r.accountInfo.bankCardNum);
			$("#totalAmountDd").html(DM.utils.farmatMoney(r.accountInfo.totalAmount, 2));
			$("#availableAmountDd").html(DM.utils.farmatMoney(r.accountInfo.leftAmount, 2));
			$("#frzoneAmountDd").html(DM.utils.farmatMoney(r.accountInfo.frozenAmount,2));
			$("#sumAmountDd").html(DM.utils.farmatMoney((r.accountInfo.stockBalance.cashPositionAmount + r.accountInfo.stockBalance.marketValue),2));
			$("#stockBalanceDd").html(DM.utils.farmatMoney(r.accountInfo.stockBalance.availAmount, 2));
			$("#pureAmount").html(DM.utils.farmatMoney(r.accountInfo.stockBalance.pureAmount, 2));
			$("#marketValueDd").html(DM.utils.farmatMoney(r.accountInfo.stockBalance.marketValue, 2));
			if(r.accountInfo.stockBalance.profitValue-0>0){
				$("#profitValue").addClass('text-red');
			}else if(r.accountInfo.stockBalance.profitValue-0<0){
				$("#profitValue").addClass('text-green');
			}
			$("#profitValue").html(DM.utils.farmatMoney(r.accountInfo.stockBalance.profitValue, 2));
			$('#frozenAmount').html(DM.utils.farmatMoney(r.accountInfo.stockBalance.frozenAmount, 2));
		}
	});
}

$(function(){
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	$('#aside-nav li:eq(0)').addClass('active');
	//获取数据信息
	queryStockAccount();
	setInterval(function () {
		configTimer.proxy(queryStockAccount);
	},5000);
	
	// 问题悬浮
	$(".question").hover(function() {
		var $el = $(this),
			txt = $el.attr("tips");
			html = '<div class="pop-gray-tip"><em class="e1">&#9660;</em><em class="e2">&#9660;</em>' + txt + '</div>';
		$(html).appendTo("body");
		var h = $(".pop-gray-tip").height();
		$(".pop-gray-tip").css({ "left": ($el.offset().left + $el.width() - 115) + "px", "top": (($el.offset().top)-h-33) + "px" });
	}, function() {
		$(".pop-gray-tip").remove();
	});
	
	if($('.tab_plugin').length){
		$('.tab_plugin').tabSwitched();
	}
	
});