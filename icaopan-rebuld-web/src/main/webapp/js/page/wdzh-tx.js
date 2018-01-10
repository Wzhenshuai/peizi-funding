var MA = myAccount = {
		barful :true
};
/*提现*/
MA.userWithDraw = function (){
	var val=$("#amount").val();
	
	var moneyVal=$.trim(val)-0;
	if(val==''){
		$("#errorDiv1").html("提现金额不能为空！");
		return;
	}
	if(!valid.isMoney(val)  || /^0+/ig.test(val)){
		$("#errorDiv1").html("您输入的提现金额不正确！");
		$("#wytx-sxf").html("0");
		return;
	}
	var leftmoney=$.trim($("#leftmoney").val())-0;
	var fee=0;
	if(val.charAt(val.length - 1)=="."){
		return;
	}
	var bankid=$("#wytx-yhk").val();

	if(bankid<1){
		$('#wytx-yhk').trigger('click');
	}
	if(moneyVal<=0){
		$("#errorDiv1").html("提款金额必须大于0元！");
		return;
	}
	if(bankid<1){
		$("#errorDiv1").html("请选择银行卡");
		return;
	}
	
	if(val!=0&&val!=null){
		fee=MA.calcTradeFee(parseFloat(val)).toFixed(2);
	} 
	$("#wytx-sxf").html(fee);
	leftmoney=DM.utils.formateMoneyToDecimal(leftmoney);
	var feeTd=DM.utils.formateMoneyToDecimal(fee);
	leftmoney=(leftmoney-feeTd).toFixed(2);
	moneyVal=DM.utils.formateMoneyToDecimal(moneyVal).toFixed(2);

	if(eval(moneyVal)>eval(leftmoney)){
		$("#errorDiv1").html("提现金额不能大于账户余额！");
		return;
	}
	if(leftmoney<=100){
		if(leftmoney<=2){
			$("#errorDiv1").html("您的现金账户余额必须大于2元");
			return;
		}
		if(moneyVal!=leftmoney){
			$("#errorDiv1").html("资金账户的钱少于100元，只能一次提取");
			return;
		}
	}
	if(moneyVal==''||moneyVal==0||moneyVal==null){
		$("#errorDiv1").html("提款金额不能为0！");
		return;
	}else{
		$("#errorDiv1").html("");
	}
	var title="提现验证码";
	var text ='<p>提现验证码将通过短信形式发到您的手机,请注意查收!</p>';
		text+='<p><input type="text" class="inp-code" id="msgVerifyCode"><input id="regCodeBtn" type="button" onclick="javascript:sendMobileValidateWithDraw(1);" value="获取验证码" class="inp-btn-blue mar-l-5"/>&nbsp;&nbsp;&nbsp;<a id="voiceCodeWithDraw" onclick="javascript:sendVoiceCodeWithDraw();" style="color:#0272c9;">语音验证码</a></p>';
		text+='<p class="error-2 text-orange" id="errorDiv3"></p>';
	var d=new UpdateDialog(title,text,"确定",function(){
		//第一次确认短信验证码
		var code = $("#msgVerifyCode").val();
		if (code == null || $.trim(code) == "") {
			$("#errorDiv3").html("请输入验证码");
			return;
		}
		$.ajax({
			url:systemConfig.path+"/user/ApplayWithDraw.ajax",
			data:{"bankid":bankid,"amount":moneyVal,"code" : code},
			success:function(r){
				//验证验证码的正确性
				if(r.resCode == "login") {
					location.href = systemConfig.path + "/user/LogOut.do";
				}else if(r.resCode=="success"){
					ShowDialogSuccess("提现申请已提交，等待平台处理...", function(){location.href=systemConfig.path+"/user/UserWithDrawInit.do";});
				}else if(r.resCode!=null){
					$("#errorDiv3").html(r.resCode);
					return;
				}else{
					ShowDialogFail("提现失败");
				}
			}
		});
	},"取消",null);
	d.show();
	$('#regCodeBtn').click();
};
//提现验证
MA.checkUserInfo = function (r){
	var text = "";
	if(r.resCode=="login"){
		location.href=systemConfig.path+"/user/login.jsp";
	}else if(r.resCode1=="verify_not_set"){
		text+="请先通过实名认证<a style='text-decoration: underline;' href='"+systemConfig.path+"/account/wdzh_aqzx_smrz.jsp'>【去认证】</a><br/>";
		ShowDialogConfirm("提醒", text, "确定", function(){
		}, "取消", null);
	}else if(r.resCode3=="bank_not_set"){
		text+="请先绑定银行卡<br/>";
		ShowDialogConfirm("提醒", text, "确定", function(){
			window.location.href = systemConfig.path + "/user/UserWithDrawInit.do#card_manage";
			window.location.reload();
		}, "取消", null);
	}
};

/*删除银行卡*/
MA.deleteBankCard = function (id){
	var dia=new ConfirmDialog("提示", "确定要删除当前银行卡吗?", "确定",
		function(){
			dia.closeDia();
			$.ajax({
				url:systemConfig.path +"/user/DeleteBindBankCard.ajax",
				data:{"id":id},
				success:function(r){
					if(r.resCode=="success"){
						$.post(systemConfig.path+"/user/GetBindBankCardListAjax.ajax",function(data){
						     if(data.cardList.length){
						    	 var _html='', _options='';
						    	 for(var i=0;i<data.cardList.length;i++){
						    		 var obj=data.cardList[i];
						    		 _html+=('<tr><td>'+obj.accountname+'</td><td>'+obj.bankname+'</td><td>'+obj.provice+'</td><td>'+obj.city+'</td><td>'+obj.cardnoHidePart+'</td><td><a class="btn3" href="javascript:;" onclick="MA.deleteBankCard('+obj.id+');">删除</a></td></tr>');
						    		 $('#wytx-yhk option[value="'+id+'"]').remove();
						    	 }
						    	 $('.yhk-list tbody').html(_html);
						     }else{
						    	 $('.yhk-list tbody').html('<tr><td colspan="6" class="text-c">暂无银行卡</td></tr>');
						    	 $('#wytx-yhk').html('<option value="-1" selected="selected">请选择</option>');
						     }
						     $('.add-card-wrap span').html(data.cardList.length);
						});
					}
				}
			});
		}, "返回", null);
	dia.show();
};

/*添加银行卡*/
MA.addBankcard = function (){
	if($('.yhk-list tr').length>=4){
		ShowDialogFail("最多只能绑定三张银行卡！");
		return ;
	}else{
		$.ajax({
			url:systemConfig.path + "/user/IsVerifyID.ajax",
			success: function(r){
				if(r.resCode=="login"){
					location.href = systemConfig.path + "/user/login.jsp";
				}else if(r.resCode=="faile"){
					var text = "";
					text+="请先通过实名认证<a style='text-decoration: underline;' href='"+systemConfig.path+"/account/wdzh_aqzx_smrz.jsp'>【去认证】</a><br/>";
					ShowDialogConfirm("提醒", text, "确定", function(){}, "取消", null);
				}else if(r.resCode=="success"){
					$(".addNewCard").show();
				}
			}
		});
		window.scrollTo(0, 200);
	}
};

/***************提现的js代码*****************/
/*费率计算*/
MA.calcTradeFee = function (tradeAmount){
	var fee=0;
	$.ajax({
	   url:systemConfig.path+"/user/cancUserDrawTradeFee.ajax",
	   async:false,
	   data: {"tradetype":"wd", "tradeway":0, "tradeAmount":tradeAmount},
	   success:function(r){
		   if(r.fee!=null){
			   fee=r.fee;
		   }else{
			   fee=0;
		   }
	   }
	});
	return fee;
};
/*绑定银行卡*/
MA.doBindCard = function (){
   var bankName=$("select[name='bankName']").val();
   var provice=$("#pro").val();
   var city=$("#city").val();
   var branchName=$("#branchName").val();
   var cardNo=$("#cardNo").val();
   var isdefault=undefined;
   if(bankName=="-1"){
	   $("#errorDiv4").html("请选择开户行");
	   return;
   }
   if(provice=="-1"){
	   $("#errorDiv4").html("请选择开户省");
	   return;
   }
   if(city=="-1"){
	   $("#errorDiv4").html("请选择开户市");
	   return;
   }
   if($.trim(branchName)==""){
	   $("#errorDiv4").html("必须填写分行信息");
	   return;
   }
   if(branchName.length>50){
	   $("#errorDiv4").html("支行信息不能大于50个字符");
	   return ;
   }
   if($.trim(cardNo)==""){
	   $("#errorDiv4").html("卡号输入不能为空");
	   return;
   }
   if(isNaN(cardNo)){
	   $("#errorDiv4").html("输入的银行卡号不正确");
	   return;
   }
   /*if(!valid.isBankCard(cardNo)){
	   $("#errorDiv4").html("卡号输入不正确");
	   return;
   }*/
   var  proviceIndex=parseInt(provice);
   provice=C._cityInfo[proviceIndex].n;
   cityIndex=parseInt(city);
   var cityArray=C._cityInfo[proviceIndex].c;
   city=cityArray[cityIndex];
   $.ajax({
	   url:systemConfig.path +"/user/BindBankCard.ajax",
	   data:{"bankName":bankName,"provice":provice,"city":city,"branchName":branchName,"cardNo":cardNo,"isdefault":isdefault},
	   type:'post',
	   success:function(r){
		   if(r.resCode=="login"){
			   location.href=systemConfig.path +"/user/login.jsp";
		   }else if(r.resCode=="verify_id"){
			   $("#errorDiv4").html("请先做实名认证");
			   return;
		   }else if(r.resCode=="success"){
			   $("#errorDiv4").html("");
			   ShowDialogSuccess("添加成功", function(){
				   location.href = systemConfig.path +"/user/UserWithDrawInit.do?action=card_manage";
			   });
		   }else {
			   $("#errorDiv4").html(r.resCode);
			   return;
		   }
	   }
   });
};


/*提现验证码的发送*/
function sendMobileValidateWithDraw(type) {
	
	if(!MA.barful){
		return false;
	}
	MA.barful=false;
	if (type == "1") {
		type = "WITH_DRAW_CODE";	
	}
	$.ajax({
		url:systemConfig.path + "/user/SendMobileValidateCodeWithDraw.ajax",
		data:{"type" : type},
		success:function(r){
			if(r.resCode=="login"){
				//openLoginDia();
			}else if(r.resCode=="success"){
				$("#errorDiv3").html("发送成功");
			}else{
				$("#errorDiv3").html(r.resCode);
				return;
			}
		}
	});
	// 更改样式
	MA.changeButtonStyleForSendCodeToWait("regCodeBtn");
}
//发送语音验证码

function sendVoiceCodeWithDraw(){
	if(!MA.barful){
		return false;
	}
	MA.barful=false;
	
	var type = "TEMPLATE_WITH_DRAW";
	var mobile = "";
	$.ajax({
		url:systemConfig.path + "/user/SendVoiceCode.ajax", 
		data:{
			"mobile" : mobile,
			"type" : type
			},
		dataType:'json',
		success:function(res){
			changeSendVoiceCodeWithDrawToWait("voiceCodeWithDraw");
			if(res.resCode=="login"){
				location.href = systemConfig.path + "/user/loginOut.do";
			}else if(res.result=="success"){
				$("#errorDiv3").html("您将收到的电话语音验证码，请接听！");
				return true;
			}else{
				$("#errorDiv3").html(res.result);
				return false;
			}
		}
	});
}
//语音验证码
function changeSendVoiceCodeWithDrawToWait(btnId) {
	

	var txt = "秒";
	var startData = 60;
	$("#" + btnId).removeAttr("onclick");
	inter1 = setInterval(function() {
		if (startData <= 0) {
			MA.barful=true;
			$("#" + btnId).html("语音验证码");
			$("#" + btnId).attr("onclick","sendVoiceCodeWithDraw();");
			clearInterval(inter1);
		} else {
			startData--;
			$("#" + btnId).html(startData + txt);
		}
	}, 1000);
}

// 获取验证码超时
MA.changeButtonStyleForSendCodeToWait = function (btnId) {
	var txt = "秒";
	var startData = 60;
	$("#" + btnId).attr("disabled", "disabled");
	inter = setInterval(function() {
		if (startData <= 0) {
			MA.barful=true;
			$("#" + btnId).removeAttr("disabled");
			$("#" + btnId).val("获取验证码");
			clearInterval(inter);
		} else {
			$("#" + btnId).attr("disabled", "disabled");
			startData--;
			$("#" + btnId).val(startData + txt);
		}
	}, 1000);
};
	
$(function(){
	$.ajax({
		url:systemConfig.path +'/user/GetLeftAmount.ajax', 
		success:function(e){
			$('#availableAmountDd').html(DM.utils.farmatMoney(e.leftAmount, 2));
		}
	});
	$('#aside-nav li:eq(2)').addClass('active');
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	
	if($('.tab_plugin').length){
		$('.tab_plugin').tabSwitched();
	}
	
	//添加开户银行卡信息
	C.initProv("pro","city",null,null);
	
	//为提现输入框和下拉银行选框添加验证事件
	$('#amount').click(function(){
		$.ajax({
			url:systemConfig.path+"/user/CheckUserInfo.ajax", 
			success:function(r){
				MA.checkUserInfo(r);
			}
		});
	});
	
	//提现手续费更新
	$("#amount").keyup(function(e){
		var val=$(this).val();
		if(val.charAt(val.length - 1)=="."){
			return;
		}
		var fee=0;
		if($.isNumeric(val)){
			fee=MA.calcTradeFee(parseFloat(val));
			//fee=fee.toFixed(2);
		}
		$("#wytx-sxf").html(fee);
	}); 
	
	//
	var _href=window.location.href;
	if(_href.indexOf('card_manage')>-1){
		$('#card_manage').click();
	}
	
	
});
