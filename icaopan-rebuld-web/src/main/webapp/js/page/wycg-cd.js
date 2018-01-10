/* 历史委托查询 */
var MS=myStock={};

/* 撤单列表查询 */
MS.queryCurrentDayPlacementList = function (cancelPlacementTable,page,pageSize) {
	var node = $("#" + cancelPlacementTable);
	var html = "";
	$.ajax({
		url:systemConfig.path +"/trade/queryPlacementCurrentDayByPage.ajax",
		data:{
			"page":page,
			"pageSize":pageSize
		},
		success:function(r) {
			var list = r.dataList;
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, cancelPlacementTable, 'MS.queryCurrentDayPlacementList');
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
							+ securityName
							+ '</p>'
							+ securityCode
							+ '</td><td><p>'
							+ createTimeDate
							+ '</p>'+createTime+'</td><td>'
							+ tradeTypeDisplay
							+ '</td><td><p>'
							+ placementPrice+'</p><p>'+placementQty
							+ '</p></td><td><p>'
							+ filledQty+'</p><p>'+cancelQty
							+ '</p></td><td>'
							+ placementStatusDisplay
							+ '</td><td>';
							if(placementStatusDisplay=="正撤"||placementStatusDisplay=="已成"||placementStatusDisplay=="废单"||placementStatusDisplay=="已撤"){
								html+='<a href="javascript:;" class="btn btn-gray';
							}else{
								html+='<a href="javascript:;" class="btn btn-orange" onclick="cancelPlacement(';
								html+="'"+placementId+"','"+securityCode+"','"+securityName+"','"+cancelQty+"','"+placementPrice+"')";
							}
					        html +='">撤单</a></td></tr>';
				}
			} else {
				html += "<tr><td colspan='8'>暂无记录</td></tr>";
			}
			//node.find("tr").not(":first").remove();
			var digg=node.next('.digg');
			if(digg.html()!=''){
				node.find('tbody').html(html).end().closest('div').height(node.height()+digg.outerHeight(true)+'px');
			}else{
				node.find('tbody').html(html).end().closest('div').height(node.height()+'px');
			}
		}
	});
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(1)').addClass('current');
	//子导航焦点态
	$(".aside-nav li a:contains('股票撤单')").parent("li").addClass('active');
	
	MS.queryCurrentDayPlacementList('gpcdTable',1, 12);
	var isRefreshMarketDatas = function(){
		$.ajax({
			url:systemConfig.path + "/user/isRefreshMarketData.ajax",
			success:function(r){
				if(r.isRefresh){
					MS.queryCurrentDayPlacementList("gpcdTable",getCurrentPage('gpcdTable'),12);
				}
			}
		});
	}
	//定时刷新数据
	setInterval(function () {
		configTimer.proxy(isRefreshMarketDatas);
	},5000);
});



