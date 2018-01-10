/* 历史委托查询 */
var MS=myStock={};

MS.loadCurrentDayPlacementList = function (dataTableId,page,pageSize){
	var node = $("#" + dataTableId);
	$("div.digg").html("");
	$.ajax({
		url:systemConfig.path +"/trade/queryPlacementCurrentDayByPage.ajax",
		data:{
			"page":page,
			"pageSize":pageSize
			},
		type:'post',
		async:false,
		success:function(r){
			var list = r.dataList;
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'MS.loadCurrentDayPlacementList');
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var securityName=obj.securityName;
					var tradeTypeDisplay=obj.tradeTypeDisplay;
					var placementPrice=obj.placementPrice;
					var filledTime=obj.createTime;
					var filledTimeTime=DF.formateDateToTime(filledTime);
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
					html+='<td><p>'+securityName+'</p>'+filledTimeTime+'</td><td>';
					if(tradeTypeDisplay=='买入'){
						html+='<span class="text-orange">';
					}else{
						html+='<span class="text-blue">';
					}
					html+=tradeTypeDisplay+'</span></td><td><p>'+placementPrice+'</p>'+placementQty+'</td><td><p>'+filledPrice+'</p>'+filledQty+'</td><td>'+filledAmount+'</td><td>'+placementStatusDisplay+'</td></tr>';
				}
			}else{
				html += "<tr><td colspan='6'>暂无记录</td></tr>";
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

MS.loadHistoryPlacementList = function (dataTableId,page,pageSize){
	var node = $("#" + dataTableId);
	// 分页隐藏
	$("div.digg").html("");
	var startDate=$("#startDate").val();
	var stockCode=$('#stockCode').val();
	var tradeType=$('#tradeType').val();
	var endDate=$("#endDate").val();
	$.ajax({
		url:systemConfig.path +"/trade/queryPlacementHistoryByPage.ajax",
		data:{"startDate":startDate,"endDate":endDate,"page":page,"pageSize":pageSize,"tradeType":tradeType,"stockCode":stockCode},
		type:'get',
		success:function(r){
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'MS.loadHistoryPlacementList');
			// 显示数据
			var list = r.dataList;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var securityName=obj.securityName;
					var filledTime=obj.createTime;
					var filledTimeTime=DF.formateDateToShowDate(filledTime);
					var tradeTypeDisplay=obj.tradeTypeDisplay;
					var placementAmount=obj.placementAmount;
					placementAmount=DM.utils.toFixedNumber(placementAmount);
					var placementPrice=obj.placementPrice;
					placementPrice=DM.utils.toFixedNumber(placementPrice, 3);
					var placementQty=obj.placementQty;
					var filledPrice=obj.filledPrice;
					filledPrice=DM.utils.toFixedNumber(filledPrice, 3);
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
					html+='<td><p>'+securityName+'</p>'+filledTimeTime+'</td><td>';
					if(tradeTypeDisplay=='买入'){
						html+='<span class="text-orange">';
					}else{
						html+='<span class="text-blue">';
					}
					html+=tradeTypeDisplay+'</span></td><td><p>'+placementPrice+'</p>'+placementQty+'</td><td><p>'+filledPrice+'</p>'+filledQty+'</td><td>'+filledAmount+'</td><td>'+placementStatusDisplay+'</td></tr>';
				}
			}else{
				html += "<tr><td colspan='6'>暂无记录</td></tr>";
			}
/*			$("#waitTr").remove();
			$("#" + dataTableId).append(html);*/
			
			node.find("tr:gt(0)").remove();
			var digg=node.next('.digg');
			if(digg.html()!=''){
				node.append(html).closest('div').height($('.zjmx-tit').outerHeight(true)+node.height()+digg.outerHeight(true)+'px');
			}else{
				node.append(html).closest('div').height($('.zjmx-tit').outerHeight(true)+node.height()+'px');
			}
		}
	});
};


MS.tabFn=function(){
	if($('.tab_plugin .active').index()==0){
		clearInterval(MS.timer);
		MS.loadCurrentDayPlacementList('currentDayPlacementTable',1,12);
		var tabPost = function(){
			$.ajax({
				url:systemConfig.path + "/user/isRefreshMarketData.ajax",
				success:function(r){
					if(r.isRefresh){
						MS.loadCurrentDayPlacementList('currentDayPlacementTable',getCurrentPage('currentDayPlacementTable'),12);
					}
				}
			});
		}
		
		MS.timer = setInterval(function() {
			configTimer.proxy(tabPost);
		},5000);
	}else{
		clearInterval(MS.timer);
		MS.loadHistoryPlacementList('historyPlacementTable',1,12);
	}
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(1)').addClass('current');
	//子导航焦点态
	$(".aside-nav li a:contains('委托记录')").parent("li").addClass('active');
	
	$('.tab_plugin').tabSwitched(MS.tabFn);
	
	MS.tabFn();//因为有定时器，所以做了关闭访问接口的优化
	
	//股票查询输入框填充
	var stocksList = queryAllSecurity();
	bindStockResource('stockCode', stocksList);
	
	var date=DF.GetDateStr(-1);
	var lastMonthDate=DF.getLastMonthDate();
	$("#startDate").val(lastMonthDate);
	$("#endDate").val(date);
});