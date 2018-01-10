var MA = myAccount = {};

//查询冻结明细
MA.queryFrozenTable = function (dataTableId,pageNo,pageSize){
	$.ajax({
		url:systemConfig.path +"/user/GetAcctFreezeDetailList1.ajax", 
		data:{"pageNo":pageNo,"pageSize":pageSize},
		success: function(r){
			var result = r.acctFreezeDetailVo,
				page = result.page;
			console.log(page);
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId,"MA.queryFrozenTable");
			var frozenAmount=DM.utils.farmatMoney(result.totalFrozenAmount,2);
			$("#frozenAmount").html(frozenAmount);
			var list=page.dataList;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var updatelasttime=DF.formateDate(obj.updatelasttime);
					var frozenamount=DM.utils.farmatMoney(obj.frozenamount,2);
					var accountType=obj.accountType;
					var frozenremark=obj.frozenremark;
					html+="<tr><td></td><td>"+updatelasttime+"</td><td>"+accountType+"</td><td>"+obj.frozentypeStr+"</td><td>"+frozenamount+"</td><td>"+frozenremark+"</td></tr>";
				}
			}else{
				html += "<tr><td colspan='6' class='txt-c'>暂无记录</td></tr>";
			}
			$("#" + dataTableId+' tbody').html(html);
		}
	});
};

//更新交易类型
MA.queryTradeTypes = function (accountTypeId,tradeTypeId){
	var accountType=$("#"+accountTypeId).val();
	var tradeTypeSelect=$("#"+tradeTypeId);
	$.ajax({
		url:systemConfig.path +"/user/QueryUserAllTradeTypes.ajax",
		data:{"accountType":accountType},
		success: function(r){
			var types=r.result;
			var html="<option value=''>请选择</option>";
			if(types!=null && types.length>0){
				for(var i=0;i<types.length;i++){
					var type=types[i];
					html+="<option value='"+type.value+"'>"+type.name+"</option>";
				}
			}
			tradeTypeSelect.html(html);
		}
	});
};

//查询资金明细
MA.queryPrincipalChangeRecord = function (dataTableId,pageNo,pageSize){
	// 分页隐藏
	var trs=$("#" + dataTableId).find("tr");
	trs.not(trs.eq(0)).remove();
	$("#" + dataTableId).append("<tr id='waitTr'><td colspan='6' class='txt-c'>加载中...</td></tr>");
	//var accountType=$("#tradeAccountType").val();
	var accountType="stock";
	var tradeType=$("#tradeType").val();
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	$.ajax({
		url: systemConfig.path +"/user/SelectAcctInfoChangeListByConditionsAjax.ajax", 
		data:{"tradeAccountType":accountType,"tradeType":tradeType,"startDate":startDate,"endDate":endDate,"pageNo":pageNo,"pageSize":pageSize},
		success:function(r){
			var page=r.result;
			if(page=='login'){
				location.href=systemConfig.path+"/user/LogOut.do";
			}
			loadPages(page.pageNo, page.pageSize, page.sumPages, page.pageList, dataTableId,"MA.queryPrincipalChangeRecord");
			var list=page.dataList;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					html += "<tr><td></td><td>"+obj.createTimeStr+"</td><td>"+obj.tradeTypeStr+"</td>";
					if(obj.addOrMinus==0){
						html+="<td>"+obj.tradeAmount+"</td><td></td>";
					}else{
						html+="<td></td><td>"+obj.tradeAmount+"</td>";
					}
		            html += "<td>"+obj.tradeAccountStr+"</td></tr>";
				}
			}else{
				html += "<tr><td colspan='6' class='txt-c'>暂无记录</td></tr>";
			}
			$("#waitTr").remove();
			$("#" + dataTableId).append(html);
		}
	});
};

$(function(){
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	$('#aside-nav li:eq(5)').addClass('active');
	if($('.tab_plugin').length){
		$('.tab_plugin').tabSwitched();
	}
	
	var date=DF.getTodayDate();
	var lastMonthDate=DF.getLastMonthDate();
	$("#startDate").val(lastMonthDate);
	$("#endDate").val(date);
	//默认点击一下按钮，防止页面没数据
	MA.queryPrincipalChangeRecord('principalChangeTable',1,12);
	
	MA.queryFrozenTable('frozenTable', 1 ,12);
	$("#tradeAccountType").change(function(){
		MA.queryTradeTypes("tradeAccountType","tradeType");
	});
});