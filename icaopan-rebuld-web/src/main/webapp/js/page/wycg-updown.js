var MA = myAccount = {};
MA.queryUpdownStock = function(){
	var html = '<tr>' +
		'<th width="116">股票简称</th>' +
		'<th width="115">涨跌停</th>' +
		/*'<th width="115">最高价</th>' +*/
		/*'<th width="128">涨跌幅度</th>' +
		'<th width="104">委比</th>' +*/
		/*'<th width="104">现价</th>' +*/
		/*'<th width="98">行情时间</th>' +*/
		'<th></th>' +
		'</tr>';
	$.ajax({
		url:systemConfig.path + "/trade/queryUpdownStocksInitByPage.ajax",
		data:{"page":1, "pageSize":130 },
		type:'post',
		success:function(r) {
			var list = r.dataList;
			console.log(list);
			if (list != null && list.length > 0) {
				var isOdd=true;
				for (var i = 0; i < list.length; i++) {
					var obj = list[i];
					var stockName = obj.stockName;
					var securityCode = obj.symbol;
					var preClose = obj.preClose;
                    preClose=DM.utils.toFixedNumber(preClose, 3);
                    /*var dailyChangePercent = obj.dailyChangePercent;
                    dailyChangePercent=DM.utils.toFixedNumber(dailyChangePercent, 3);

					var netBuyPercent = obj.netBuyPercent;
                    netBuyPercent=DF.formateDecimalToPercent(netBuyPercent);

					var marketDataDate = obj.marketDataDate;*/
					html += "<tr";
					if (isOdd) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					var redOrGreen="";
					/*if(dailyChangePercent<0){
						redOrGreen="green";
					}else if(dailyChangePercent>0){
						redOrGreen="red";
					}*/
					html += "<td><p>" +stockName + "</p><p>" + securityCode + "</p></td>";
                    html += "<td>" + preClose + "</td>";
					/*html += "<td>" + dailyChangePercent + "</td><td class='text-"+redOrGreen+"'></td>";
					html += "<td>" + netBuyPercent + "</td>";
					html += "<td>" + marketDataDate + "</td><td>";*/
					html+='<a class="btn4 btn4-red" href='+systemConfig.path+"/trade/BuyStocksInit.do?stockCode="+securityCode+'>买入</a>';
					html+='<a class="btn4 btn4-blue mar-t5" href='+systemConfig.path+"/trade/SellStocksInit.do?stockCode="+securityCode+'>卖出</a></td></tr>';
					isOdd = !isOdd;
				}
			} else {
				html += "<tr><td colspan='7' class='txt-c'>暂无记录</td></tr>";
			}
			$("#updownStockTable").html(html);
		}
	});
};

$(function(){
	//导航焦点态 + 子导航焦点态
	$('#main-nav li:eq(1)').addClass('current');
	$('.aside-nav li:eq(0)').addClass('active');
	
	//获取指数数据接口 + 获取数据信息
	MA.queryUpdownStock();
	
	/*function pageGetContent(){
		
		$.ajax({
			url:systemConfig.path + "/user/isRefreshMarketData.ajax",
			success:function(r){
				if(r.isRefresh){
					MA.queryUpdownStock();
				}
			}
		});
	}*/
	
	//定时刷新数据
	/*setInterval(function () {
		configTimer.proxy(pageGetContent)
	},5000);*/
	
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
});