/* 历史委托查询 */
var MS=myStock={};

/* 持仓查询 */
MS.queryHoldStockList = function (holdStockTable,page,pageSize) {
	var node = $("#" + holdStockTable);
	var html = '';
	$.ajax({
		url:systemConfig.path + "/trade/queryHoldStockListByPage.ajax",
		data:{
				"page":page,
				"pageSize":pageSize
			},
		success:function(r) {
			var list = r.dataList;
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, holdStockTable, 'MS.queryHoldStockList');
			var refreshFrequency=5;
			refreshFrequency=refreshFrequency*1000;
			Configs.hold_stocks_List_fresh_interval=refreshFrequency;
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
					if(positionAmount==0){
						continue;
					}
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
					html += "<td></td><td class='txt-l'><p>" +securityShortName + "</p><p>" + securityLocalCode + "</p></td>";
					html += "<td><p>" + marketValue + "</p></td><td class='text-"+redOrGreen+"'><p>" + marketProfit + "</p>" + marketProfitPercent + "</td>";
					html += "<td><p>" + positionAmount + "</p><p>" + t0PlacementQuantity + "</p></td>";
					html += "<td>" + latestPrice + "</td>";
					html += "<td>" + costPrice + "</td><td>";
					html+='<a class="btn4 btn4-red" href='+systemConfig.path+"/trade/BuyStocksInit.do?stockCode="+securityLocalCode+'>买入</a>';
					html+='<a class="btn4 btn4-blue mar-l3" href='+systemConfig.path+"/trade/SellStocksInit.do?stockCode="+securityLocalCode+'>卖出</a>';
					html += "</td></tr>";
					isOdd = !isOdd;
				}
				updateProfit(sumProfitValue);
			} else {
				html += "<tr><td colspan='8'>暂无记录</td></tr>";
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

MS.queryStockAccount = function () {
	$.ajax({
		url: systemConfig.path + "/user/ViewMyAccountCenterIndex.ajax",
		success: function(r) {
			$("#wdcc-kyye").html(DM.utils.farmatMoney(r.cashAvailableAmount, 2));
			$("#wdcc-gpsz").html(DM.utils.farmatMoney(r.marketValue, 2));
			if(r.profitValue>0){
				$("#wdcc-ccyk").removeClass('text-green').addClass('text-red');
			}else if(r.profitValue<0){
				$("#wdcc-ccyk").removeClass('text-red').addClass('text-green');
			}
			$("#wdcc-ccyk").html(DM.utils.farmatMoney(r.profitValue, 2));
		}
	});
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(1)').addClass('current');

	//子导航焦点态
	$(".aside-nav li a:contains('持仓')").parent("li").addClass('active');
	MS.queryHoldStockList('wdccTable', 1, 12);
	MS.queryStockAccount();
	var rePath = function(){
		$.ajax({
			url:systemConfig.path + "/user/isRefreshMarketData.ajax",
			success:function(r){
				if(r.isRefresh){
					MS.queryHoldStockList("wdccTable",getCurrentPage('wdccTable'),12);
					MS.queryStockAccount();
				}
			}
		});
	}
	//定时刷新持仓数据
	setInterval(function () {
		configTimer.proxy(rePath);
	},5000);
	
});