/* 历史委托查询 */
var MS=myStock={};

MS.query_jgd_list = function (dataTableId,pageNo,pageSize){
	var trs=$("#" + dataTableId).find("tr");
	trs.not(trs.eq(0)).remove();
	$("#" + dataTableId).append("<tr><td colspan='9' id='waitTr'>加载中...</td></tr>");
	var stockCode=$('#stockCode').val(),
		businessType=$('#tradeType').val(),
		startDate=$('#startDate').val(),
		endDate=$("#endDate").val();
	$.ajax({
		url:systemConfig.path +"/trade/queryDeliveryOrder.ajax",
		data:{
			"stockCode":stockCode,
			"businessType":businessType,
			"startDate":startDate,
			"endDate":endDate,
			"pageNo":pageNo,
			"pageSize":pageSize
		},
		type:'post',
		async:false,
		success:function(r){
			
			var list = r.dataList;
			var page=r;
			// 显示分页
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId, 'MS.query_jgd_list');
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var flowId=obj.flowId;
					var businessTypeName=obj.businessTypeName;
					var filledTime=obj.executionTime;
					var filledTimeDate=DF.formateDateToShowDate(filledTime);
					var filledTimeTime=DF.formateDateToTime(filledTime);
					var amount=obj.amount;
					var securityShortName=obj.securityShortName;
					html += "<tr class=";
					if (i % 2 == 0) {
						html += "'odd'>";
					} else {
						html += ">";
					}
					html+='<td>'+flowId+'</td><td><p>'+filledTimeDate+'</p>'+filledTimeTime+'</td><td>';
					if(businessTypeName=='证券买入'){
						html+='<span class="text-orange">买入'+'</span></td><td><p>'+securityShortName+'</p>'+obj.localCode;
					}else if(businessTypeName=='证券卖出'){
						html+='<span class="text-blue">卖出'+'</span></td><td><p>'+securityShortName+'</p>'+obj.localCode;
					}else{
						html+='<span>'+businessTypeName+'</span></td><td><p>'+securityShortName+'</p>'+obj.localCode;
					}
					html+='</td><td>'+obj.quantity+'</td><td>'+DM.utils.toFixedNumber(amount, 3)+'</td><td>'+DM.utils.toFixedNumber(obj.commission, 3)+'</td><td>'+DM.utils.toFixedNumber(obj.stampDuty,3)+'</td><td>'+DM.utils.toFixedNumber(obj.transferFee,3)+'</td></tr>';
				}
			}else{
				html += "<tr><td colspan='9'>暂无记录</td></tr>";
			}
			$("#" + dataTableId).find("tr").not(":first").remove();
			$("#" + dataTableId).append(html);
		}
	});
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(1)').addClass('current');
	//子导航焦点态
	$(".aside-nav li a:contains('交割单')").parent("li").addClass('active');
	
	//股票查询输入框填充
	var stocksList = queryAllSecurity();
	bindStockResource('stockCode', stocksList);
	
	var date=DF.getTodayDate();
	var lastMonthDate=DF.getLastMonthDate();
	$("#startDate").val(lastMonthDate);
	$("#endDate").val(date);
	
	MS.query_jgd_list('jgd-table', 1 ,12);
});