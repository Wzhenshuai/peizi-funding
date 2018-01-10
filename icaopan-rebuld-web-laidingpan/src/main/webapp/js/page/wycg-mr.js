/* 历史委托查询 */
var MS=myStock={};

var queryInterval, entrustTimer ;
MS.tabFlag=0, MS.figureFlag=false, MS.tradeFlag = true;
/* 股票行情显示 */
MS.showStockMarketData = function (stockCode) {
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
					Configs.market_data_interval = setInterval(function() {
						if(MS.figureFlag){
							queryStockMarketData(stockCode);
						}
					}, Configs.market_data_fresh_interval);
				}
			}
		}
	}
	Configs.market_data_fresh_counts++;
};
function showStockMarketDataTip(tip){
	$(".error").html(tip);
}
MS.buyStockCodeInputBlur = function (stockCodeId) {
	showStockMarketDataTip("");
	$("#quantity").val("");
	$("#stockPrice, #currentPrice").val("");
	var obj = $("#" + stockCodeId);
	Configs.market_data_fresh_counts=0;
	var stockCode = obj.val();
	if (stockCode.length < 6) {
		$("#stockName, #maxQuantity").html("");
		//$("#stockPrice").val("");
		$("input[type='radio']:checked").attr("checked", false);
		return;
	}
	MS.figureFlag=true;
	queryTimeShare(stockCode, 'new', 'buy');
	drawKLine(stockCode);
	$.ajax({
		url:systemConfig.path + "/stock/QueryStockDetail.ajax", 
		data:{'stockCode':stockCode}, 
		success:function(msg) {
			if (msg == null) {
				$("#stockName, #maxQuantity").html("");
			} else {
				$("#stockName").html(msg.shortNameCn);
				//if($("#stockPrice").val()==""){
					$("#stockPrice, #currentPrice").val(DM.utils.farmatMoney(msg.lastPrice, 2));
				//}
				$("#quantity").focus();
				queryStockMarketData(stockCode);
				/* 定时刷新 */
				MS.showStockMarketData(stockCode);
				/* 计算可买股票数量 */
				calcMaxStocksAmount("stockPrice", "stockBalanceDd", "maxQuantity");
			}
		}
	});
};

MS.buyPriceInputBlur=function(){
	if($.trim($("#stockPrice").val())!=""){
		$("#currentPrice").val($("#stockPrice").val())
		calcMaxStocksAmount("stockPrice", "stockBalanceDd", "maxQuantity");
	}
}

MS.sellStockCodeInputBlur = function (stockCodeId) {
	var stockCode=$("#"+stockCodeId).val();
	Configs.market_data_fresh_counts=0;
	$("input[type='radio']:checked").attr("checked", false);
	if($.trim(stockCode).length<6){
		$("#stockName, #maxQuantity").html("");
		$("#quantity").val("");
		return;
	}
	MS.figureFlag=true;
	queryTimeShare(stockCode, 'new', 'sell');
	drawKLine(stockCode);
	$("#stockPrice").val("");
	$.ajax({
		url:systemConfig.path+"/stock/QueryStockDetail.ajax", 
		data:{"stockCode":stockCode}, 
		type:'post',
		success: function(msg){
			if(msg==null){
				$("#stockName").html("");
				$("#maxQuantity").html("");
			 }else{
				 $("#stockName").html(msg.shortNameCn);
				 //if($("#stockPrice").val()==""){
					 $("#stockPrice").val(DM.utils.farmatMoney(msg.lastPrice, 2));
				 //}
				 $("#quantity").focus();
			     queryStockMarketData(stockCode);
			     /* 定时刷新 */
			     MS.showStockMarketData(stockCode);
			     /* 计算可卖股票数量 */
			     calcMaxStocksAmountToSell();
			 }
		}
	});
};

/* 股票委托交易 */
MS.doBuyPlacementStocksTrade = function (){
	var stockName=$("#stockName").html();
	var price=$("#stockPrice").val();
	var quantity=$("#quantity").val();
	var stockCode=$("#stockCode").val();
	if($.trim(stockName)==""){
		showStockMarketDataTip("您输入的股票代码有误。");
		return;
	}
	if(!valid.isMoney(price)){
		showStockMarketDataTip("您输入的股票价格格式不正确。");
		return;
	}
	if(!valid.isInteger(quantity)){
		showStockMarketDataTip("您输入的委托数量格式不正确。");
		return;
	}
	if(!DM.utils.isNumberByN(quantity, 100)){
		showStockMarketDataTip("您输入的委托数量格式应为100的整数倍。");
		return;
	}
	var maxAmount=calcMaxStocksAmountToPlancement("stockPrice", "stockBalanceDd");
	if(quantity>maxAmount){
		showStockMarketDataTip("您输入的委托数量超过最大可买数量。");
		return;
	}else if(quantity==0){
		showStockMarketDataTip("您输入的委托数量必须大于0。");
		return;
	}
	// 判断可用余额是否为零
	var balance = formateRemoveRmbSysbol($("#stockBalanceDd").html());
	if(balance=="0"){
		showStockMarketDataTip("账户余额不足。");
		return;
	}
	// 判断购买余额总额是否超出余额
	var needMoney=price*quantity;
	if(needMoney>balance){
		showStockMarketDataTip("账户余额不足。");
		return;
	}
	showStockMarketDataTip("");
	var text=
	 '<li><lable>股票名称：</lable><span class="a-18">'+stockName+'</span></li>'
	+'<li><lable>委托数量：</lable><span class="a-18">'+quantity+'</span></li>'
	+'<li><lable>股票代码：</lable><span class="a-18">'+stockCode+'</span></li>'
	+'<li><lable>委托价格：</lable><span class="a-18">'+price+'</span></li>';
	ShowDialogConfirm("确认买入信息", text, "确认买入", function(){
		var txt="";
		doTrade(stockCode,price,quantity,'0',txt);
	}, "取消", null);
};

/* 股票委托交易 */
MS.doSellPlacementStocksTrade = function (){
	// 清除错误信息提示
	$("div.error span").html("");
	var stockName=$("#stockName").html();
	var stockCode=$("#stockCode").val();
	var price=$("#stockPrice").val();
	var quantity=$("#quantity").val();
	if($.trim(stockName)==""){
		showStockMarketDataTip("您输入的股票代码有误。");
		return;
	}
	if(!valid.isMoney(price)){
		showStockMarketDataTip("您输入的股票价格格式不正确。");
		return;
	}
	if(!valid.isInteger(quantity)){
		showStockMarketDataTip("您输入的委托数量格式不正确。");
		return;
	}
	var maxAmount=parseInt($("#maxQuantity").html());
	var q=parseInt(quantity);
	if(q>maxAmount){
		showStockMarketDataTip("您输入的委托数量超过最大可卖数量。");
		return;
	}
	var text='<li><lable>股票名称：</lable><span class="a-18">'+stockName+'</span></li>'
			+'<li><lable>委托数量：</lable><span class="a-18">'+quantity+'</span></li>'
			+'<li><lable>股票代码：</lable><span class="a-18">'+stockCode+'</span></li>'
			+'<li><lable>委托价格：</lable><span class="a-18">'+price+'</span></li>';
	ShowDialogConfirm("确认卖出信息", text, "确认卖出", function(){
		var txt="";
		doTrade(stockCode,price,quantity,'1',txt);
	}, "取消", null);
};

MS.adjustPrice= function (addOrMinus, priceInputId,isSell) {
	var obj = $("#" + priceInputId);
	var adjustStep = 0.01;
	var price = obj.val();
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
};

MS.setColorValue = function (dom, domValue, threshold){
	if(domValue > threshold){
		dom.removeClass('text-green').addClass('text-red');
	}else if(domValue < threshold){
		dom.removeClass('text-red').addClass('text-green');
	}else{
		dom.removeClass('text-red text-green');
	}
	dom.html(DM.utils.farmatMoney(domValue, 2));
};

function jsoncallback(r){
	if (r.info != null) {
		checkVisitOpenApiLogin(r);
		var result = r.info.result;
		if(!result.suspensionFlag){
			var dailyChange=result.dailyChange;
			var dailyChangePercent=result.dailyChangePercent;
			dailyChangePercent=DF.formateDecimalToPercent(dailyChangePercent);
			var closePrice=result.preClosePrice;
			MS.setColorValue($('#lastPrice'), result.lastPrice, closePrice);
			MS.setColorValue($('#sprice5'), result.askPrice5, closePrice);
			MS.setColorValue($('#sprice4'), result.askPrice4, closePrice);
			MS.setColorValue($('#sprice3'), result.askPrice3, closePrice);
			MS.setColorValue($('#sprice2'), result.askPrice2, closePrice);
			MS.setColorValue($('#sprice1'), result.askPrice1, closePrice);
			MS.setColorValue($('#bprice5'), result.bidPrice5, closePrice);
			MS.setColorValue($('#bprice4'), result.bidPrice4, closePrice);
			MS.setColorValue($('#bprice3'), result.bidPrice3, closePrice);
			MS.setColorValue($('#bprice2'), result.bidPrice2, closePrice);
			MS.setColorValue($('#bprice1'), result.bidPrice1, closePrice);
			MS.setColorValue($('#dailyChange'), dailyChange, 0);
			if(result.dailyChangePercent>0){
				$('#dailyChangePercent').removeClass('text-green').addClass('text-red');
			}else if(result.dailyChangePercent<0){
				$('#dailyChangePercent').removeClass('text-red').addClass('text-green');
			}
			$('#dailyChangePercent').html(dailyChangePercent);
			$('#highPrice').html(DM.utils.farmatMoney(result.highPrice, 2));
			$('#lowPrice').html(DM.utils.farmatMoney(result.lowPrice, 2));
			$('#openPrice').html(DM.utils.farmatMoney(result.openPrice, 2));
			$("#samount5").html(result.askVolume5);
			$("#samount4").html(result.askVolume4);
			$("#samount3").html(result.askVolume3);
			$("#samount2").html(result.askVolume2);
			$("#samount1").html(result.askVolume1);
			$("#bamount1").html(result.bidVolume1);
			$("#bamount2").html(result.bidVolume2);
			$("#bamount3").html(result.bidVolume3);
			$("#bamount4").html(result.bidVolume4);
			$("#bamount5").html(result.bidVolume5);
			$("#date").html(result.lastModifyTime);
		}else{
			$('#lastPrice').removeClass().addClass('pad-l13').html('停牌');
			//$('#stockPrice').val('');
			$('#sprice5, #sprice4, #sprice3, #sprice2, #sprice1, #date').html('--');
			$('#bprice5, #bprice4, #bprice3, #bprice2, #bprice1').html('--');
			//$("#maxQuantity").html('0');
			$('#dailyChange, #dailyChangePercent').html('--');
			$('#highPrice, #lowPrice, #openPrice').html('--');
			$("#samount5, #samount4, #samount3, #samount2, #samount1").html('--');
			$("#bamount5, #bamount4, #bamount3, #bamount2, #bamount1").html('--');
		}
		$("#securityName, #stockName").html(result.stockName);
		$("#securityCode").html(result.stockCode);
		$('#preClosePrice').html(DM.utils.farmatMoney(result.preClosePrice, 2));
		$("#upLimit").html(DM.utils.farmatMoney(result.upLimit));
		$("#downLimit").html(DM.utils.farmatMoney(result.downLimit));
   }
}

function queryStockMarketData(stockCode) {
	$.ajax({
	   async:false, 
	   type: "post",
	   url: Configs.market_data_server+'?is_web=1&securityCode='+stockCode,
	   dataType:"jsonp"
	});
};

MS.timeShare = null;
MS.currentStockCode = null;
MS.count = 0;
MS.timer = null;
MS.loadingClass = 'loading';

function returnjson(data) {
	if(MS.type == 'new' && MS.currentStockCode == data.info.result.s1) {
		return;
	}
	if(!/^\d{6}$/ig.test($.trim(data.info.result.s1))) {
		if($.trim($('#stockCode').val()) == '') {
			$('#time-share').addClass('time-share-none');
			$('#vkContent2').addClass('time-share-none');
		}
		return;
	} 
	if($('#stockCode').val() == data.info.result.s1) {
		if(MS.timeShare){
			MS.timeShare.destroy();
			MS.timeShare = null;
		}
		MS.timeShare = new TimeShare('time-share', 400, 150, data, {type: 1});
		MS.timeShare.draw();
		MS.currentStockCode = data.info.result.s1;
		//如果是停牌就不发请求了
		if(MS.timeShare.suspended == 'true') {
			return;
		}
		MS.timer = setTimeout($.proxy(queryTimeShare, this, MS.currentStockCode), 1000*30);
	} 
}




/* 持仓查询 */
MS.queryHoldStockList = function (holdStockTable,page,pageSize) {
	var node=$("#" + holdStockTable);
	var html = "";
	$.ajax({
		url:systemConfig.path + "/trade/queryHoldStockListByPage.ajax", 
		data:{'page': page,'pageSize':pageSize}, 
		success:function(r) {
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, holdStockTable, 'MS.queryHoldStockList');
			var list = page.dataList;
			var sumProfitValue=0;
			if (list != null && list.length > 0) {
				var isOdd=true;
				for (var i = 0; i < list.length; i++) {
					var obj = list[i];
					var securityShortName = obj.securityName;
					var securityLocalCode = obj.securityCode;
					var marketValue = obj.marketValue;
					marketValue=DM.utils.toFixedNumber(marketValue);
					var marketProfit = obj.marketProfit;
					marketProfit=DM.utils.toFixedNumber(marketProfit, 3);
					var marketProfitPercent = obj.marketProfitPercent;
					marketProfitPercent=DF.formateDecimalToPercent(marketProfitPercent);
					var positionAmount = obj.amount;
					sumProfitValue=sumProfitValue+marketProfit;
					var t0PlacementQuantity = obj.availableAmount;
					var latestPrice = obj.latestPrice;
					latestPrice=DM.utils.toFixedNumber(latestPrice);
					var costPrice = obj.costPrice;
					costPrice=DM.utils.toFixedNumber(costPrice, 3);
					html += "<tr";
					if (isOdd) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					var redOrGreen="";
					if(marketProfit<0){
						redOrGreen="green";
					}else if(marketProfit>0){
						redOrGreen="red";
					}
					html += "<td class='txt-l'><p>" +securityShortName + "</p><p>" + securityLocalCode + "</p></td>";
					html += "<td><p>" + marketValue + "</p></td><td class='text-"+redOrGreen+"'><p>" + marketProfit + "</p>" + marketProfitPercent + "</td>";
					html += "<td><p>" + positionAmount + "</p><p>" + t0PlacementQuantity + "</p></td>";
					html += "<td>" + latestPrice + "</td>";
					html += "<td>" + costPrice + "</td><td>";
					html+='<a class="btn4 btn4-red" href='+systemConfig.path+"/trade/BuyStocksInit.do?stockCode="+securityLocalCode+'>买入</a>';
					html+='<a class="btn4 btn4-blue mar-l3" href='+systemConfig.path+"/trade/SellStocksInit.do?stockCode="+securityLocalCode+'>卖出</a>';
					html += "</td></tr>";
					isOdd = !isOdd;
				};
			} else {
				html += "<tr><td colspan='7'>暂无记录</td></tr>";
			}
			if($.trim(html)==''){
				html = "<tr><td colspan='7'>暂无记录</td></tr>";
			}
			node.find("tr:gt(0)").remove();
			var digg=node.next('.digg');
			if(digg.html()!=''){
				node.append(html).closest('div').height(node.height()+digg.outerHeight(true)+'px');
			}else{
				node.append(html).closest('div').height(node.height()+'px');
			}
		}
	});
};

/* 买入卖出页面委托查询 */
MS.loadCurrentDayPlacementListShowBtn = function (dataTableId, page, pageSize){
	var node = $("#" + dataTableId);
	MS.tabFlag=0;
	$.ajax({
		url:systemConfig.path +"/trade/queryPlacementCurrentDay.ajax",
		success:function(r){
			var list = r;
			var html="";
			if (list != null && list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					var obj=list[i];
					var placementId=obj.placementId;
					var securityCode = obj.securityCode;
					var securityName=obj.securityName;
					var date=DF.formateDateToTime(obj.createTime);
					var tradeTypeDisplay=obj.tradeTypeDisplay;
					var placementPrice=obj.placementPrice;
					placementPrice=DM.utils.toFixedNumber(placementPrice);
					var placementQty=obj.placementQty;
					var filledPrice=obj.filledPrice;
					filledPrice=DM.utils.toFixedNumber(filledPrice);
					var filledQty=obj.filledQty;
					var cancelQty = placementQty - filledQty;
					var filledAmount=obj.filledAmount;
					filledAmount=DM.utils.toFixedNumber(filledAmount);
					var placementStatusDisplay=obj.placementStatusDisplay;
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					html+='<td><p>'+securityName+'</p>'+date+'</td><td>';
					if(tradeTypeDisplay=='买入'){
						html+='<span class="text-orange">';
					}else{
						html+='<span class="text-blue">';
					}
					html+=tradeTypeDisplay+'</span></td><td><p>'+placementPrice+'</p>'+placementQty+'</td><td><p>'+filledPrice+'</p>'+filledQty+'</td><td>'+filledAmount+'</td><td>'+placementStatusDisplay+'</td><td>';
					if(placementStatusDisplay=="正撤"||placementStatusDisplay=="已成"||placementStatusDisplay=="废单"||placementStatusDisplay=="已撤"){
						html+='<a href="javascript:;" class="btn btn-gray';
					}else{
						html+='<a href="javascript:;" class="btn btn-orange" onclick="cancelPlacement(';
						html+="'"+placementId+"','"+securityCode+"','"+securityName+"','"+cancelQty+"','"+placementPrice+"')";
					}
			        html +='">撤单</a></td></tr>';
				};
			}else{
				html += "<tr><td colspan='6'>暂无记录</td></tr>";
			}
			node.find("tr:gt(0)").remove();
			node.append(html).closest('div').height(node.height()+'px');
		}
	});
};

MS.queryHoldStockListFunction = function (page,pageSize){
	MS.tabFlag=1;
	MS.queryHoldStockList("holdStockTable",page,pageSize);
};

MS.queryStockAccount = function () {
	$.ajax({
		url: systemConfig.path + "/user/ViewMyAccountCenterIndex.ajax",
		success: function(r) {
			$("#profitValueDd").html(DM.utils.farmatMoney(r.profitValue, 2));
			$("#stockBalanceDd").html(DM.utils.farmatMoney(r.cashAvailableAmount, 2));
			$("#marketValueDd").html(DM.utils.farmatMoney(r.marketValue, 2));
			if(r.profitValue > 0){
				$("#profitValueDd").removeClass('text-green').addClass('text-red');
			}else if(r.profitValue < 0){
				$("#profitValueDd").removeClass('text-red').addClass('text-green');
			}
		}
	});
};
MS.recover = function(){
	$('.price-selected').removeClass('price-selected');
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(1)').addClass('current');
	//如果有参数则直接显示该股票信息
	//var stockCode=window.location.search.split('=')[1];
	if(stockCode!=undefined){
		$('#stockCode').val(stockCode).blur();
	}
	
	//绑定顶部数据
	MS.queryStockAccount();
	
	//tab切换
	$('.tab_plugin').tabSwitched();
	
	//股票代码
	var stocksList = queryAllSecurity();
	bindStockResource('stockCode', stocksList);
	MS.stocksList = [];

	setTimeout(function() {
		for(var i = 0; i < stocksList.length; i++) {
			MS.stocksList.push(stocksList[i].data);
		};
	}, 1000);
	
	//绑定股票信息列表
	MS.loadCurrentDayPlacementListShowBtn('cancelPlacementTable',1,8);
	
	var isRefreshMarketData =  function(){
		$.ajax({
			url:systemConfig.path + "/user/isRefreshMarketData.ajax",
			success:function(r){
				if(r.login){
					location.href=systemConfig.path+"/user/LogOut.do";
					return;
				}
				if(r.isRefresh){
					MS.queryStockAccount();
					MS.figureFlag=true;
					if(MS.tabFlag){
						MS.queryHoldStockList('holdStockTable',getCurrentPage('holdStockTable'),8);
					}else{
						MS.loadCurrentDayPlacementListShowBtn("cancelPlacementTable",getCurrentPage('cancelPlacementTable'),8);
					}
				}
			}
		});
	}
	
	
	//定时刷新委托列表
	entrustTimer = setInterval(function () {
		configTimer.proxy(isRefreshMarketData);
	}, 5000);
	
	//监听键盘输入
	bindStockInput("stockCode");
	
	var stockCode=$("#stockCode").val();
	$("#stockPrice").blur(function(){
		var price=$(this).val();
		if(!valid.isMoney(price, true)){
			showStockMarketDataTip("请输入正确的价格。");
		}else{
			if(price<=0.01){
				price=0.01;
			};
		};
	});
	if($.trim(stockCode)!=""){
		$("#stockCode").val(stockCode);
		//判断余额是否加载完毕
		var inter=setInterval(function(){
			var balance=$("#stockBalanceDd").html();
			if(balance!="--"){
				clearInterval(inter);
				$("#stockCode").blur();
			};
		}, 500);
	}else{
		$("#stockCode").val("").blur();
	}
	//获取焦点
	if($("#stockCode").val()==""){
		$("#stockCode").focus();
	}else{
		if($("#quantity").val()==""){
			$("#quantity").focus();
		}
	}
	
	$("input[type='radio']").change(function(){
		var maxQuantity=$('#maxQuantity').html()-0;
		if(maxQuantity>0){
			var _id=$(this).attr('id');
			if(_id=='all'){
				$('#quantity').val(maxQuantity);
			}else if(_id=='harf'){
				if(maxQuantity>=200){
					$('#quantity').val(DM.utils.formateNumberDivideN(Math.floor(maxQuantity*0.5), 100));
				}else{
					$('#quantity').val('0');
				};
			}else if(_id=='third'){
				if(maxQuantity>=300){
					$('#quantity').val(DM.utils.formateNumberDivideN(Math.floor(maxQuantity/3), 100));
				}else{
					$('#quantity').val('0');
				};
			}else if(_id=='quarter'){
				if(maxQuantity>=400){
					$('#quantity').val(DM.utils.formateNumberDivideN(Math.floor(maxQuantity*0.25),100));
				}else{
					$('#quantity').val('0');
				};
			};
		};
	});
	
	var timer;
	$('.price-select').click(function(){
		clearTimeout(timer);
		$('.price-select').removeClass('price-selected');
		$(this).addClass('price-selected');
		$('#stockPrice').val($(this).html());
		timer = setTimeout(MS.recover,1000);
	});
	
	$('#forbidStock').click(function(){
		if($('#forbid-list').find('li').length==0){
			$.ajax({
				url:systemConfig.path +'/openapi/queryBlackListSecutities.ajax',
				type:'post',
				success:function(e){
					if(e.info.rescode=='success'){
						var len=e.info.result.length;
						var html='';
						for(var i=0; i<len; i++){
							var obj=e.info.result[i];
							html+=("<li>"+obj.securityCode+"<span class='securityName'>"+obj.securityName+"</span></li>");
						}
						$('#forbid-list').html(html);
						$('#forbid-wrap').show();
					};
				}
			});
		}else{
			$('#forbid-wrap').show();
		}
	});
	
	$('#forbid-wrap .forbid-close').click(function(){
		$('#forbid-wrap').hide();
	});
	
	$('.chart-config .type-list li').click(function(){
		$('.chart-config .type-list li').removeClass("select");
		$(this).addClass("select");
		$(".time-share-pc").css("display","none");
		var _id=$(this).attr("id");
		if(_id=="time-li"){
			$("#time-share").css("display","block");
		}else if(_id=="day-li"){
			$("#vkContent2").css("display","block");
		}
	});
	
	$(window).focus(function() {
		if(MS.timer ==null && MS.currentStockCode) {
			MS.timer = setTimeout($.proxy(queryTimeShare, this, MS.currentStockCode), 1000*30);
		}
	});
	$(window).blur(function() {
		clearTimeout(MS.timer);
		MS.timer = null;
	});
	
});


function formateCode(code){
	pf="";
	fstLetter=code.substring(0,1);
	if(fstLetter=="0"){
		pf="SZSE";
	}else if(fstLetter=="6"){
		pf="SSE";
	}else if(fstLetter=="3"){
		pf="SZSE";
	}
	return pf+code;
}

