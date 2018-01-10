/* 历史委托查询 */
var MS=myStock={};

/* 成交查询 */
MS.loadPlacementFillList = function (dataTableId,page,pageSize){
	// 分页隐藏
	$("div.digg").html("");
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var stockCode, tradeType;
	var isCurrentDay=true;
	if(dataTableId == 'PlacementFillTable'){
		stockCode = null;
		tradeType = null;
	}else{
		stockCode = $("#stockCode").val();
		tradeType = $("#tradeType").val();
		isCurrentDay=false;
	}
	$.ajax({
		url:systemConfig.path +"/trade/queryFillOrFillHistoryByPage.ajax",
		data:{"startDate":startDate,"endDate":endDate,"page":page,"pageSize":pageSize,"tradeType":tradeType,"stockCode":stockCode,"isCurrentDay":isCurrentDay },
		success:function(r){
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'MS.loadPlacementFillList');
			var list = r.dataList;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var securityName=obj.securityName;
					var securityCode=obj.securityCode;
					var filledTime=obj.filledTime;
					var filledTimeDate=DF.formateDateToShowDate(filledTime);
					var filledTimeTime=DF.formateDateToTime(filledTime);
					filledTime=DF.formateDate(filledTime);
					var filledPrice=obj.filledPrice;
					filledPrice=DM.utils.toFixedNumber(filledPrice);
					var filledQty=obj.filledQty;
					var filledAmount=obj.filledAmount;
					filledAmount=DM.utils.toFixedNumber(filledAmount);
					var tradeTypeDisplay=obj.tradeTypeDisplay;
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html +=">";
					}
					html+="<td><p>"+securityName+"</p>"+securityCode+"</td><td>"+filledTimeDate+"</p>"+filledTimeTime+"</td><td>";
					if(tradeTypeDisplay=='买入'){
						html+='<span class="text-orange">';
					}else{
						html+='<span class="text-blue">';
					}
					html+=tradeTypeDisplay+"</td><td>"+filledPrice+"</td><td>"+filledQty+"</td><td>"+filledAmount+"</td></tr>";
				}
			}else{
				html += "<tr><td colspan='6'>暂无记录</td></tr>";
			}
			$("#" + dataTableId+' tbody').html(html);
		}
	});
};

/* 成交查询 */
MS.loadPlacementFillSummaryList = function (dataTableId,page,pageSize){
	// 分页隐藏
	$("div.digg").html("");
	var startDate=$("#startDateS").val();
	var endDate=$("#endDateS").val();
	var stockCode, tradeType;
	if(dataTableId == 'PlacementFillTable'){
		stockCode = null;
		tradeType = null;
	}else{
		stockCode = $("#stockCodeS").val();
		tradeType = $("#tradeTypeS").val();
	}
	$.ajax({
		url:systemConfig.path +"/trade/queryFillSummaryByConditionsByPage.ajax",
		data:{"startDate":startDate,"endDate":endDate,"page":page,"pageSize":pageSize,"tradeType":tradeType,"stockCode":stockCode },
		success:function(r){
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'MS.loadPlacementFillSummaryList');
			var list = r.dataList;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var securityName=obj.securityName;
					var securityCode=obj.securityCode;
					var filledPrice=obj.filledPrice;
					filledPrice=DM.utils.toFixedNumber(filledPrice);
					var filledQty=obj.filledQty;
					var filledAmount=obj.filledAmount;
					filledAmount=DM.utils.toFixedNumber(filledAmount);
					var tradeTypeDisplay=obj.tradeTypeDisplay;
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html +=">";
					}
					html+="<td><p>"+securityName+"</p>"+securityCode+"</td><td>";
					if(tradeTypeDisplay=='买入'){
						html+='<span class="text-orange">';
					}else{
						html+='<span class="text-blue">';
					}
					html+=tradeTypeDisplay+"</td><td>"+filledPrice+"</td><td>"+filledQty+"</td><td>"+filledAmount+"</td><td>"+obj.filledTimes+"</td></tr>";
				}
			}else{
				html += "<tr><td colspan='6'>暂无记录</td></tr>";
			}
			//$("#waitTr").remove();
			$("#" + dataTableId+' tbody').html(html);
		}
	});
};

MS.tabFn=function(){
	var today=DF.getTodayDate();
	var date = DF.GetDateStr(-1);
	if($('.tab_plugin .active').index()==0){
		$("#startDate").val(date);
		$("#endDate").val(today);
		MS.loadPlacementFillList('PlacementFillTable', 1, 12);
	}else if($('.tab_plugin .active').index()==1){
		var lastMonthDate=DF.getLastMonthDate();
		$("#startDate").val(lastMonthDate);
		$("#endDate").val(date);
		MS.loadPlacementFillList('PlacementFillHistoryTable', 1, 12);
	}else{
		var lastMonthDate=DF.getLastMonthDate();
		$("#startDateS").val(lastMonthDate);
		$("#endDateS").val(today);
		MS.loadPlacementFillSummaryList('PlacementFillSummaryTable', 1, 12);
	}
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(1)').addClass('current');
	//子导航焦点态
	$(".aside-nav li a:contains('成交记录')").parent("li").addClass('active');
	$('.tab_plugin').tabSwitched(MS.tabFn);
	$('.tab_plugin li:eq(0)').trigger('click');//初始化页面数据
	
	//股票查询输入框填充
	var stocksList = queryAllSecurity();
	bindStockResource("stockCode", stocksList);
	
});