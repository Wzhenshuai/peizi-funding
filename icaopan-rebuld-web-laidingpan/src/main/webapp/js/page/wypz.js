var RATE_USED;
var changeBtnStatus=function(){
	var bzj = DM.utils.formateMoneyToDecimal($('#ipt-bzj').val());//保证金
	if(bzj>0 && !$('.pzbs-wrap a.current').hasClass('noselect') && $("#cbx-pz").is(":checked") && RATE_USED<100){
		$('#submit-pz').removeClass('btn-gray').addClass('btn-orange');
	}else{
		$('#submit-pz').removeClass('btn-orange').addClass('btn-gray');
	}
};

var updataList = function(){
	var bzj = DM.utils.formateMoneyToDecimal($('#ipt-bzj').val());//保证金
	var pzbs=$('#pzbs').val()-0;
	var cycle = $('#cycle').val()-0;
	var pzje=bzj*pzbs;
	var zje=bzj*(pzbs+1);
	var pcx = pzje *1.16;
	var jjx = pzje *1.13;
	$('.data li:eq(0)').find('strong').html(DM.utils.farmatMoney(zje));
	$('.data li:eq(1)').find('strong').html(DM.utils.farmatMoney(pcx));
	$('.data li:eq(2)').find('strong').html(DM.utils.farmatMoney(jjx));
	//修改投资顾问协议的内容
	$('#bzj1').text(bzj);
	$('#bzj').text(bzj);
	$("#circles").text(cycle);
	var stockValueEm=bzj*(pzbs+1);
	$("#zcpzj").text(DM.utils.farmatMoney(stockValueEm));
	//计算利息
	var interest = bzj-0>0 ? DM.utils.calcInterest(pzje, pzbs,cycle, 'fix_mode') : 0;
	$('#interest').text(interest);
	$("#interest_to_xy").html(interest);
	changeBtnStatus();
	if( bzj<=0){
		$('#rePayList tbody').html('');
		//return false;
	}
	//updata table list
	if(pzje){
		$.ajax({
			url:systemConfig.path+"/finance/GetRepayPlantList.ajax",
			data:{"period":cycle,"financingAmount":parseFloat(pzje),"interest":interest,"pzbs":pzbs},
			success:function(r){
				var _tbody='';
				for(var i=0;i<r.repayList.length;i++){
					_tbody+=("<tr><td>"+r.repayList[i].repayDate+"</td><td>"+r.repayList[i].repayType+"</td><td>"+r.repayList[i].repayAmount+"</td></tr>");
				}
				$('#rePayList tbody').html(_tbody);
			}
		});
	}
};

function confirmFinancing() {
	var availabelPrincipalAmount = $("#availabelPrincipalAmount").val();
	var principal = $("#principal").val();
	var financeAmount = $("#financeAmount").val();
	var stockValue = $("#stockValue").val();
	var stockValueChange = $("#stockValueChange").val();
	var alertLine = $("#alertLine").val();
	var alertLineChange = $("#alertLineChange").val();
	var wrongLine = $("#wrongLine").val();
	var wrongLineChange = $("#wrongLineChange").val();
	var cycle = $("#cycle").val();
	var interest = $("#interest").val();
	var param={
			"financeAmount" : financeAmount,
			"principal" : principal,
			"cycle" : cycle,
			"interest" : interest,
			"type" : 0
		};
	
	$.ajax({
		url:systemConfig.path + "/finance/ApplyFinancingConfirm.do",
		data:param,
		success:function(r) {
			$('#confirm-pz').attr('disabled',false).removeAttr('disabled');
			if (r.result.resCode == 1) {//未登录
				var visitPath = systemConfig.path
					+ "/finance/ApplyFinancing.do?availabelPrincipalAmount="
					+ availabelPrincipalAmount + "&principal="
					+ principal + "&financeAmount=" + financeAmount
					+ "&stockValue=" + stockValue
					+ "&stockValueChange=" + stockValueChange
					+ "&alertLine=" + alertLine
					+ "&alertLineChange=" + alertLineChange
					+ "&wrongLine=" + wrongLine
					+ "&wrongLineChange=" + wrongLineChange
					+ "&cycle=" + cycle + "&interest=" + interest;
				//openLoginDia(visitPath);
				location.href=systemConfig.path+"/user/login.jsp?visitPath="+escape(visitPath);
			} else if (r.result.rescode == 14) {
				new ConfirmDialog("提示","您尚未申请实名认证，请先实名认证。","确定",function() {
					window.open(systemConfig.path+ "/account/wdzh_aqzx_smrz.jsp");
				}, "取消",null).show();
			} else if (r.result.rescode == 2) {//安全验证未通过
				ShowDialogFail("实名认证尚未通过，无法发起实盘申请，请联系客服处理。",function() {
					window.open(systemConfig.path+ "/account/wdzh_aqzx_smrz.jsp");
				});
			}else if (r.result.rescode == 10) {//成功
				var fDetailId = r.result.fDetailId;
				location.href = systemConfig.path + "/finance/FinanceSuccess.do?fDetailId="+fDetailId;
			} else if (r.result.rescode == 11) {//余额不足
				new ConfirmDialog("提示","您的现金账户余额不足，还差<span class='sure'><em>"+ DM.utils.farmatMoney(r.result.marginAmount)+ "</em></span>元。","去充值",function(){
					window.open(systemConfig.path + "/user/UserChargeInit.do?marginAmount="+$('#D-value').text());
					new ConfirmDialog("提示","您已经前往充值，若充值完成请点击【充值完成】","充值完成",function(){
						location.reload();
					},"返回",null).show();
				},"返回",null).show();
			} else{
				ShowDialogFail(r.result.result, null);
			}
		}
	});
}

$(function(){
	//导航焦点态
	$('#main-nav li:eq(2)').addClass('current');
	
	// 股贷圈协议
	$("#a-agreement").click(function(e) {
        Common.LightBox.show();
		var $agreement = $("#popup-agreement");
		CU.setObjAbsCenter($agreement);
		$agreement.fadeIn();
		e.stopPropagation();
    });
	$('#ipt-bzj').val('');
	//保证金数据框事件
	$('#ipt-bzj').blur(function(){
		var val = DM.utils.formateMoneyToDecimal($(this).val());
		var f_val = DM.utils.farmatMoney( DM.utils.formateNumberDivideN(val, 100) );
		if(f_val==0){
			$(this).val('');
		}else{
			$(this).val( f_val );
		}
		updataList(f_val);
	}).keyup(function(){
		var val = DM.utils.formateMoneyToDecimal($(this).val());
		val = val > 500000 ? 500000 : val;
		var f_val = DM.utils.farmatMoney( val );
		$(this).val( f_val );
		updataList();
	});
	
	//选择配资倍数
	$('.pzbs-wrap a').click(function(e){
		var $tar=$(this);
		var index=$tar.index('.pzbs-wrap a');
		$tar.siblings().removeClass('current').end().addClass('current');
		changeBtnStatus();
		$('#pzbs').val(index+1);
		updataList();
	});
	
	//保证金使用期限更改事件
	$('#cycle').change(function(){
		var val = DM.utils.formateMoneyToDecimal($('#ipt-bzj').val());
		if(val!=0){
			updataList();
		}
	});
	
	// 协议选中复选框
	$("#cbx-pz").click(function(e) {
		changeBtnStatus();
    });

	// 关闭弹出框
	$("[attr='popup-close']").click(function(e) {
		$(".popup").hide();
		Common.LightBox.hide();
	});
	
	// 关闭弹出框
	$(".agree-protocol").click(function(e) {
		$(".popup").hide();
		$('#cbx-pz').prop("checked", true);
		if(!$('.pzbs-wrap a.current').hasClass('noselect')){
			$("#submit-pz").removeClass("btn-gray").addClass("btn-orange");
		}
		Common.LightBox.hide();
	});
	
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
	
	//我要配资提交按钮
	$('#submit-pz').click(function(){
		if($(this).hasClass('btn-gray')){
			return false;
		}
		var principal = DM.utils.formateMoneyToDecimal($('#ipt-bzj').val());
		var pzbs=$('#pzbs').val();
		var financeAmount=principal*pzbs;
	    // 总操盘资金
	    var stockValue=principal+financeAmount;
		if(principal>999){
			if($('#cbx-pz').prop("checked")){
				location.href=systemConfig.path+"/finance/ApplyFinancing.do?principal="+principal+"&financeAmount="+financeAmount+"&stockValue="+stockValue+"&cycle="+$('#cycle').val()+"&pzbs="+$('#pzbs').val()+"&interest="+$('#interest').text();
			}else{
				ShowDialogFail('请先阅读并同意协议');
			}
		}else{
			ShowDialogFail('实盘申请金额不能少于1000！');
		}
	});
	
	//确认配资申请
	$('#confirm-pz').click(function(){
		$(this).attr('disabled','disabled');
		confirmFinancing();
	});
	
	//立即充值跳转链接
	var D_value=DM.utils.formateMoneyToDecimal($('#principal').val()) + (0.01*$('#financeAmount').val()*$('#interest').val()) - DM.utils.formateMoneyToDecimal($('#leftAmount').val());
	if(D_value>0){
		$('#D-value').text(D_value.toFixed(2));
		$('#recharge-link').attr('href',systemConfig.path+'/user/UserChargeInit.do?marginAmount='+D_value.toFixed(2));
		$('.not-enough').show();//账户余额大于配资金额，隐藏充值提示语
	}
	
	/*判断用户是否登录和身份认证*/
	$.ajax({
		url:systemConfig.path+'/user/IsVerifyID.ajax',
		success:function(res){
			//实名认证默认不显示；未登录--不显示，登陆已认证--不显示，登陆未认证--显示
			if(res.resCode=='fail'){//登录未认证
				$('.verified').show();
			}
		}
	});
	
	$.ajax({
		url:systemConfig.path+"/finance/getCurrentFinancing.ajax",
		success:function(r){
			if(r.rescode == "success"){
				RATE_USED = 100-parseFloat(r.maxFinancingAmount);
				$('#used-rate').html(RATE_USED+'%');
				$('#unused-rate').html(parseFloat(r.maxFinancingAmount)+'%');
				$('#progress-bar span').animate({'width':(RATE_USED)*5.72});
				$(".wypz-h2").show();
			}else {
				RATE_USED = 0;
			}
	    }
	});
	
});