var Configs = {
	// 股票行情刷新次数
	market_data_fresh_counts : 0,
	// 股票行情刷新间隔
	market_data_fresh_interval : 5000,
	market_data_interval : 0,
	// 撤单列表刷新
	cancel_placement_list_fresh_interval:5000,
	// 今日委托列表刷新
	placement_list_fresh_interval:5000,
	// 持仓刷新间隔
	hold_stocks_List_fresh_interval:5000,
	// 查询行情地址
	market_data_server:"//md.icaopan.com/openapi/queryMarketDataBySecurityCode",
	// 分时查询行情接口
	maket_data_time_server:"//md.icaopan.com/openapi/queryTimeMarketData"
};
function getCurrentPage(tableId){
	var page=$("#"+tableId).next("div.digg").find("span.current").html();
	if(page!=undefined){
		return page;
	}
	return 1;
}
/* ******************注册相关************************** */
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
	$("#"+ele).hide();
}
function checkUsername() {
	var username = $.trim($("#username").val());
	var userValid = valid.isUsernameValid(username);
	if (username.length == 0 || username == '4-16位字母、数字、下划线或中文') {
		$("#username_tips").html("<i></i>请输入用户名").show();
		return false;
	}
	if (!userValid.valid) {
		$("#username_tips").html(userValid.msg).show();
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
				$("#username_tips").show().find('span').html("用户名已存在");
				nameResult = false;
			}
		}
	});
	return nameResult;
};
function checkMobile() {
	var mobile = $.trim($("#mobile").val());
	if (mobile == "" || mobile == "请输入你的手机号") {
		$("#mobile_tips").show().find('span').html("请输入你的手机号");
		return false;
	}
	if (!valid.isMobile(mobile)) {
		$("#mobile_tips").show().find('span').html("手机号不正确");
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
				$("#mobile_tips").hide();
				mobileResult = true;
			} else {
				$("#mobile_tips").show().find('span').html("手机已经被注册");
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
		$("#password_tips").show().find('span').html("密码不能为空");
		return false;
	}
	if(password.length<=5 || password.length>16){
		$("#password_tips").show().find('span').html("密码由6-16位数字和字母组成");
		return false;
	}
	if(!strExp.test(password)){
		$("#password_tips").show().find('span').html("密码只能由数字和字母组成");
		return false;
	}
	$("#password_tips").hide();
	return true;
};
function checkRePass() {
	var password = $.trim($("#password").val());
	var repassword = $.trim($("#repassword").val());
	if (repassword == "" || repassword == null ) {
		$("#repassword_tips").show().find('span').html("确认密码不能为空");
		return false;
	}
	if (password != repassword) {
		$("#repassword_tips").show().find('span').html("两次密码不相同");
		return false;
	}
	$("#repassword_tips").hide();
	return true;
};
// 发送手机验证码
function sendMobileValidateCode() {
	var mobile = $.trim($("#mobile").val());
	if (mobile == "" || mobile == "请输入你的手机号") {
		$("#mobile_tips").show().find('span').html("手机号不能为空");
		return false;
	}
	if (!valid.isMobile(mobile)) {
		$("#mobile_tips").show().find('span').html("手机号不正确");
		return false;
	}
	if (!checkMobile()) {
		$("#mobile_tips").show().find('span').html("手机号被注册");
		return false;
	}
	$.post(systemConfig.path + "/user/SendMobileValidateCode.do", {
		"mobile" : mobile
	}, function(res) {
		changeButtonStyleForSendCodeToWait("codeBtn");
		if (res.resCode == "not") {
			$("#code_tips").show().find('span').html("手机号不能为空");
			return false;
		} else if (res.resCode == "invalid") {
			$("#code_tips").show().find('span').html("发送太频繁");
			return false;
		} else if (res.resCode == "fail") {
			$("#code_tips").show().find('span').html("发送失败，请重新发送");
			return false;
		} else if(res.resCode == "many"){
			$("#code_tips").show().find('span').html("调用次数过多,请联系客服");
			return false;
		}else if (res.resCode == "success") {
			//$("#code_tips").show().find('span').html("发送成功").removeClass('tips-error');
			return true;
		}
	}, "json");
}
// 获取验证码超时
function changeButtonStyleForSendCodeToWait(btnId) {
	var startData = 60;
	var node=$("#" + btnId);
	node.css({'background-color':'#e6e6e6','color':'#666'}).attr("disabled", "disabled");
	inter = setInterval(function() {
		if (startData <= 0) {
			node.removeAttr("disabled").removeAttr('style').val("获取验证码");
			clearInterval(inter);
		} else {
			startData--;
			node.val(startData + "秒");
		}
	}, 1000);
}
function checkCode() {
	var code = $.trim($("#code").val());
	var mobile = $.trim($("#mobile").val());
	if (code == "" || code == "请输入你的验证码") {
		$("#code_tips").show().find('span').html("验证码不能为空");
		return false;
	}
	if (!valid.isMobile(mobile)) {
		$("#mobile_tips").show().find('span').html("请输入正确的手机号码");
		return false;
	}
	if (!checkMobile()) {
		$("#mobile_tips").show().find('span').html("手机号被注册，请输入其他手机号");
		return false;
	}
	var checkCode=false;
	$.ajax({
		url:systemConfig.path + "/user/VerifyMobile.ajax", 
		data:{"mobile" : mobile,"msgCode" : code},
		type:'post',
		async:false,
		success:function(res) {
			if (res.resCode == "not") {
				$("#code_tips").show().find('span').html("请输入短信验证码");
				checkCode=false;
			} else if (res.resCode == "timeout") {
				$("#code_tips").show().find('span').html("验证码超时，请重新获取");
				checkCode=false;
			} else if (res.resCode == "invalidate") {
				$("#code_tips").show().find('span').html("短信验证码验证失败");
				checkCode=false;
			}else if(res.resCode=="fail" || res.resCode=='verifyCode_empty'){
				$("#code_tips").show().find('span').html("请先发送短信验证码");
				checkCode=false;
			} else if (res.resCode == "success") {
				$("#code_tips").hide();
				checkCode=true;
			}
		}
	});
	return checkCode;
};

/* ******************我要配资相关************************** */
function checkVal(obj){
    var val=obj.val();
    if(val==""){
        obj.val("1000-500000").css("color","#ccc");
    }else{
        val =val.replace(/[^\d]/g,'');
        obj.val(val).css("color","#ff5858");
    }
}

/** ******************我要炒股相关************************** */
function getCurrentPortfolioId() {
	if($("#account_mod_div li.actived").length){
		return $("#account_mod_div li.actived").attr("portid");
	}
	return null;
}

function updateProfit(profitValue){
	$("#profitValueDd").html(DM.utils.farmatMoney(profitValue, 2));
	if(profitValue>0){
		$("#profitValueDd").attr("class","text-red");
	}else if(profitValue<0){
		$("#profitValueDd").attr("class","text-green");
	}else{
		$("#profitValueDd").attr("class","");
	}
}


function queryAllSecurity() {
	var storage = window.localStorage;
	var sList = new Array();
	var t=storage.getItem("dt");
	if(storage.getItem("stockList")!=null&&storage.getItem("stockList")!="[]"){
		if(t!=null){
			if((new Date().getTime()-t)<=8*60*60*1000){
				sList=storage.getItem("stockList");
				sList=JSON.parse(sList);
				return sList;
			}
		}
	}
	/* 获取所有股票数据 */
	$.ajax({
		url:systemConfig.path + "/stock/QueryAllSecurityModel.ajax",
		async:false,
		success:function(r) {
			var sL = r;
			for (var i = 0; i < sL.length; i++) {
				var stock = sL[i];
				var value = stock.value;
				var data = stock.data;
				var obj = new Object();
				obj.value = value;
				obj.data = data;
				sList.push(obj);
			}
		}
	});
	storage.setItem("stockList",JSON.stringify(sList));
	storage.setItem("dt",new Date().getTime());
	return sList;
};

function bindStockResource(stockCodeId, stocksList) {
	//var jquery = $.noConflict(true);
	/* 设置股票代码搜索加载 */
	$("#" + stockCodeId).autocomplete({
		lookup : stocksList,
		width : 180,
		onSelect : function(suggestion) {
			$('#' + stockCodeId).val(suggestion.data).blur();
		},
		formatItem : function(item, i, max) {
			return "<span style='border-bottom:1px solid red'>" + item.Text + "</span>";
		},
		formatMatch : function(row, i, max) {
			return /^2/g;
		}
	});
};


function formateRemoveRmbSysbol(money) {
	money = money.replace("￥", "");
	money = DM.utils.formateMoneyToDecimal(money);
	return money;
}
/**
 * 计算最大可卖
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
	}else{
		var amount = parseInt(balanceAmount / currentPriceVal/ 100/ 1.01);
		amount = amount * 100;
		quantity.html(amount);
	}
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
	$.ajax({
		url:systemConfig.path+"/trade/QueryHoldedStockByStockCode.ajax", 
		data: {"stockCode":stockCode,"channelId":channelId},
		success:function(r){
			var liObj=$("#maxQuantity");
			if(r==null){
				liObj.html("0");
			}else{
				liObj.html(r);
			}
		}
	});
}

/*登陆查询*/
function checkVisitOpenApiLogin(r){
	if(r.info.rescode=="login"){
		location.href=systemConfig.path+"/user/login.jsp";
	}
}

/* 撤单列表查询 */
function queryCurrentDayPlacementList(cancelPlacementTable) {
	var trs=$("#" + cancelPlacementTable).find("tr");
	$("#" + cancelPlacementTable).find("tr").not(trs.eq(0)).remove();
	$("#" + cancelPlacementTable).append("<tr><td colspan='10' id='waitTr'>加载中...</td></tr>");
	var html = "";
	$.ajax({
		url:systemConfig.path + "/openapi/queryPlacementCurrentDayList.ajax",
		data:{
				"portfolioId" : getCurrentPortfolioId(),
				"showCancel":"true"
			},
		type:'post',
		success:function(r) {
			checkVisitOpenApiLogin(r);
			var list = r.info.result;
			if (list != null && list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					var obj = list[i];
					var placementId=obj.placementId;
					var securityName = obj.securityName;
					var securityCode = obj.securityCode;
					var createTime = obj.createTime;
					//createTime=formateDate(createTime);
					var createTimeDate=DF.formateDateToShowDate(createTime);
					var createTime=DF.formateDateToTime(createTime);
					var tradeTypeDisplay = obj.tradeTypeDisplay;
					var placementPrice = obj.placementPrice;
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
					html += '<td><p>'
							+ securityCode
							+ '</p><p>'
							+ securityName
							+ '</p></td><td><p>'
							+ createTimeDate
							+ '</p><p>'+createTime
							+'</p></td><td>'
							+ tradeTypeDisplay
							+ '</td><td>'
							+ placementPrice
							+ '</td><td>'
							+ placementQty
							+ '</td><td>'
							+ placementTotal
							+ '</td><td>'
							+ filledQty
							+ '</td><td>'
							+ cancelQty
							+ '</td><td>'
							+placementStatusDisplay
							+ '</td><td><label>';
							if(placementStatusDisplay=="正撤"||placementStatusDisplay=="已成"||placementStatusDisplay=="废单"||placementStatusDisplay=="已撤"){
								html+='<input type="submit" name="button" id="button" value="" class="btn-cd-gray" onclick="javascript:;';
							}else{
								html+='<input type="submit" name="button" id="button" value="" class="btn-cd" onclick="javascript:';
								html+= "cancelPlacement('"+placementId+"','"+securityCode+"','"+securityName+"','"+cancelQty+"','"+placementPrice+"');";
							}
					        html +='"/></label></td></tr>';
				}
			} else {
				html += "<tr><td colspan='10'>暂无记录</td></tr>";
			}
			$("#waitTr").remove();
			$("#" + cancelPlacementTable).append(html);
		}
	});
}

/* 今日委托查询 */
function loadCurrentDayPlacementList(dataTableId){
	var trs=$("#" + dataTableId).find("tr");
	trs.not(trs.eq(0)).remove();
	$("#" + dataTableId).append("<tr><td colspan='7' id='waitTr'>加载中...</td></tr>");
	$.ajax({
		url:systemConfig.path +"/openapi/queryPlacementCurrentDayList.ajax",
		data:{"portfolioId" : getCurrentPortfolioId()},
		type:"post", 
		success: function(r){
			checkVisitOpenApiLogin(r);
			var list = r.info.result;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var securityName=obj.securityName;
					//var securityCode=obj.securityCode;
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
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					html+="<td><p>"+securityName+"</p><p>"+time+"</p></td>"
		              +"<td>"+tradeTypeDisplay+"</td>"
		              +"<td>"+placementAmount+"</td>"
		              +"<td><p>"+placementPrice+"</p><p>"+placementQty+"</p></td>"
		              +"<td><p>"+filledPrice+"</p><p>"+filledQty+"</p></td>"
		              +"<td>"+filledAmount+"</td>"
		              +"<td>"+placementStatusDisplay+"</td>";
				}
			}else{
				html += "<tr><td colspan='7'>暂无记录</td></tr>";
			}
			$("#waitTr").remove();
			$("#" + dataTableId).append(html);
		}
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
			html+='<a href="javascript:;" onclick="'+turnPageFunctionName+'('+dataTableId+','+frontPage+','+pageSize+');"> &lt; 上一页 </a>';
		}
		for(var i=0;i<pageList.length;i++){
			var page=pageList[i];
			if(page==pageNo){
				html+='<span class="current">'+page+'</span>';
			}else{
				html+='<a href="javascript:;" onclick="'+turnPageFunctionName+'('+dataTableId+','+page+','+pageSize+');">'+page+'</a>';
			}
		}
		if(pageNo==sumPages){
			html+='<span class="disabled"> 下一页 &gt;</span>';
		}else{
			var nextPage=pageNo+1;
			html+='<a href="javascript:;" onclick="'+turnPageFunctionName+'('+dataTableId+','+nextPage+','+pageSize+');">下一页 &gt;</a>&nbsp;&nbsp;&nbsp;';
		}
		html+="共"+sumPages+"页";
		$("#"+tid).next("div.digg").html(html);
	}else{
		$("#"+tid).next("div.digg").html('');
	}
}

/* 委托买入卖出合并 */
function doTrade(stockCode,price,quantity,sellOrBuy,txt,channelId){
	MS.tradeFlag=false;
	showStockMarketDataTip("");
	var url=systemConfig.path+"/trade/DoPlacementStocksTrade.ajax";
	var params="stockCode="+stockCode+"&price="+price+"&quantity="+quantity+"&sellOrBuy="+sellOrBuy+"&channelId="+channelId;
	$.ajax({
		url:url, 
		data:params,
		type:"post",
		async:false,
		success:function(msg){
			var rescode=msg.rescode;
			if(rescode=="success"){
				ShowDialogSuccess("恭喜您，委托已提交。",function(){
					//$("#quantity").focus();
					if(sellOrBuy=="0"){
						calcMaxStocksAmount("stockPrice", "stockBalanceDd", "maxQuantity");
					}else{
						//calcMaxStocksAmountToSell();
						MS.sellStockCodeInputBlur("stockCode")
					}
				});
			}else{
				ShowDialogFail(msg.message);
				MS.tradeFlag=true;
				return;
			}
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
	    $.ajax({
	    	url:systemConfig.path+"/trade/DoPlacementCancel.ajax",
	    	type:'post', 
	    	data:{"placementId":placementId}, 
	    	success:function(msg){
				ShowDialogNotify(msg);
			}
	    });
	}, "取消", null);
}

/********************我的账户相关************************** */
function viewMyAccount() {
	ajaxReuqestAsync(systemConfig.path + "/user/ViewMyAccountCenter.ajax",null, function(r) {
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

function bindStockInput(stockInputId){
	$("#"+stockInputId).keyup(function(){
		var stockCode=$(this).val();
		if(stockCode.length>=6){
			$("#"+stockInputId).blur();
		}
	});
}


//发送语音验证码
function sendVoiceCodeWithDraw(){
	var type = "template_with_draw";
	var mobile = "";
	$.ajax({
		url:systemConfig.path + "/user/SendVoiceCode.ajax",
		data:{
			"mobile" : mobile,
			"type" : type
			},
		type:'post',
		dataType:'json',
		success:function(res){
			changeSendVoiceCodeWithDrawToWait("voiceCodeWithDraw");
			if(res.resCode=="login"){
				location.href = systemConfig.path + "/user/loginOut.do";
			}else if(res.result=="success"){
				$("#errorDiv").html("发送成功");
				return true;
			}else{
				$("#errorDiv").html(res.result);
				return false;
			}
		}
	});
}
function changeSendVoiceCodeWithDrawToWait(btnId) {
	var txt = "秒";
	var startData = 60;
	$("#" + btnId).removeAttr("onclick");
	inter1 = setInterval(function() {
		if (startData <= 0) {
			$("#" + btnId).html("语音验证码").attr("onclick","sendVoiceCodeWithDraw();");
			clearInterval(inter1);
		} else {
			startData--;
			$("#" + btnId).html(startData + txt);
		}
	}, 1000);
}
$(function (){
	$('#codeBtn').removeAttr('disabled');
});
