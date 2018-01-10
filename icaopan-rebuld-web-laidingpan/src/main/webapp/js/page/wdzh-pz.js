var MA = myAccount = {};
MA.flag = true;
/*我的帐户-我的配资-操盘中-提前还款*/
MA.ViewPreRepayDetail = function (financingId){
	$.ajax({
		url:systemConfig.path+"/finance/ViewPreRepayDetail.ajax",
		data:{"financingId":financingId},
		success:function(r){
			if(r.result=="login"){
				location.href=systemConfig.path+"/user/login.jsp";
			}else if(r.result=="repayplan_state_invalide"){
				ShowDialogFail("提前还款无法申请，请检测还款计划表状态是否正常。");
			}else {
				var principal=r.prd.repayAmount+"";
				var weiyuejin=r.prd.totalInterest+"";
				var leftAmount=r.leftAmount+"";
				var stockBalance=r.stockBalance+"";
				var principalDisplay=DM.utils.farmatMoney(principal,2);
				var weiyuejinDisplay=DM.utils.farmatMoney(weiyuejin,2);
				var leftAmountDisplay=DM.utils.farmatMoney(leftAmount,2);
				var stockBalanceDisplay=DM.utils.farmatMoney(stockBalance,2);
				var text ='<table border="0" cellspacing="0" cellpadding="0" class="zhcc-table-2" style="margin-left:40px;">'
						+ '<tr> <td width="130">现金账户余额:</td><td width="270">'+leftAmountDisplay+'</td> </tr>'
						+ ' <tr><td>股票账户余额:</td><td>'+stockBalanceDisplay+'</td></tr>'
						+ ' <tr><td>应还本金:</td><td>'+principalDisplay+'</td></tr>'
						+ ' <tr><td>应还违约金:</td><td>'+weiyuejinDisplay+'</td></tr>'
						+ '</table>'
						+ '<div class="error" id="errorDiv"></div>';
				var confirmDialog=new UpdateDialog("终止项目确认", text, "确认", function(){
					if(!MA.flag){
						return false;
					}
					MA.flag = false;
					
					$.ajax({
						url:systemConfig.path+"/finance/PreRepay.ajax",
						data:{"financingId":financingId},
						success:function(r){
							MA.flag = true;
							confirmDialog.closeDia();
							if(r.resCode=="login"){
								location.href=systemConfig.path+"/user/login.jsp";
							}else if(r.resCode=="success"){
								ShowDialogSuccess("恭喜您，终止项目申请已提交，请等待后台处理...", function(){
									location.href=systemConfig.path+"/finance/ViewFinancingHistory.do?type=financing";
								});
							}else{
								ShowDialogFail(r.resCode);
							}
							/*else if(r.resCode==1){
								ShowDialogFail("股票账户，应还融资本金余额不足。");
							}else if(r.resCode==2){
								ShowDialogFail("暂不能做提前还款。");
							}*/
						}
					});
				}, "取消", null);
				confirmDialog.show();
			}
		}
	});
};
DF.calInterest = function (cycle ,lever,amount) {
	var interest = DM.utils.calcInterest(amount, lever,parseInt(cycle),"fix_mode");
	return interest;
};

/*我的帐户-我的配资-操盘中-续配*/
MA.renew = function (financeId){
	$('#circle').val('1');
	$.ajax({
		url:systemConfig.path + "/finance/preRenew.ajax",
		data:{"financeId" : financeId},
		success:function(r) {
			if(r.rescode == "success"){
				interest = DF.calInterest(1 ,r.result.level,r.result.amount);
				duedate  = r.result.dueDate;
				var date  = DF.getNewDate(duedate, 1);
				new ShowWypzDia("申请延期", r.result.amount, r.result.level,r.result.availableDays , DF.calInterest(1 ,r.result.level ,r.result.amount), date, "确认延期", function(){
					$.ajax({
						url:systemConfig.path +"/finance/doPreRenew.ajax",
						data:{
							"interest" : interest,
							"circle" : $('#circle').val(),
							"financeId" : r.result.financeId,
							"projectId" : r.result.projectId
						},
						success:function(r) {
							//清空全局变量
							if(r.rescode == "success"){
								ShowDialogSuccess(r.msg , function(){var page=$('.digg .current') ? $('.digg .current').text() : 1; MA.getFinancingHistory('wdpz',page,12);});
							}else if(r.rescode == "error"){
								ShowDialogFail(r.msg);
							}
						}
					});
				},"关闭", null).show();
			}else if (r.rescode == "error"){
				ShowDialogFail(r.msg);
			}else if(r.rescode == "login"){
				location.href = systemConfig.path + "/user/login.jsp";
			}
		}
	});
};

//我的账户 ---我的配资分页显示
MA.getFinancingHistory = function (dataTableId, pageNo, pageSize){
	var _node=$("#"+dataTableId);
	var trs=_node.find("tr");
	trs.not(trs.eq(0)).remove();
	_node.append("<tr id='waitTr'><td colspan='8' class='txt-c'>加载中...</td></tr>");
	$.ajax({
		url:systemConfig.path +"/finance/GetFinancingHistory1.ajax",
		data:{"pageNo":pageNo,"pageSize":pageSize},
		success:function(r){
			if(r.resCode=="login"){
				location.href=systemConfig.path +"/user/login.jsp";
			}
			var list = r.pageBean.dataList;
			var html="";
			if (list != null && list.length > 0) {
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var appDate;
					/*if(!obj.financingDate)
						appDate="";
					else*/
						appDate=DF.formateDateAllowStringForPeizi(obj.appDate);
					var financeName=obj.financeNameDisplay;
					var financingAmount=DM.utils.farmatMoney(obj.financingAmount,0);
					var repayAmount=DM.utils.farmatMoney(obj.repayAmount,0);
					var financingRate=DF.formateDecimalToPercent(obj.financingRate/12);
					var dueDate;
					if(!obj.financingDate){
						dueDate="--";
					}else{
						dueDate=DF.formateDateAllowStringForPeizi(obj.dueDate);
					}
					var status=obj.status;
					var statusStr=obj.statusStr;
					var financingId=obj.financingId;
					var renrewnum=obj.renrewnum;
					html += "<tr";
					if (i % 2 == 0) {
						html += " class='odd'>";
					} else {
						html += ">";
					}
					if(obj.statusDisplay==0){
						html+="<td>"+appDate.substr(0,10)+'<p>'+appDate.substr(10)+'</p>'+"</td><td>"+dueDate.substr(0,10)+'<p>'+dueDate.substr(10)+'</p>'+"</td><td class='orderNum'>"+financeName+"</td><td>"+financingAmount+"</td><td>"+repayAmount+"</td><td>"+financingRate+"</td><td>审核中</td><td></td></tr>";
					}else if(obj.statusDisplay==1){
						html+="<td>"+appDate.substr(0,10)+'<p>'+appDate.substr(10)+'</p>'+"</td><td>"+dueDate.substr(0,10)+'<p>'+dueDate.substr(10)+'</p>'+"</td><td class='orderNum' data-financingId='"+financingId+"'>"+financeName+"</td><td>"+financingAmount+"</td><td>"+repayAmount+"</td><td>"+financingRate+"</td>";
		                var status = obj.status;
						if(status==6){
							if(renrewnum){
								html+="<td>延期申请审核中</td><td class='rel'><div class='pz_detail'><p class='orderDetail'>详情<i></i></p><a href='javascript:;' class='text-gray'>终止项目</a><a href='javascript:;' class='text-gray'>申请延期</a></div></td></tr>";
							}else{
								html+="<td>操盘中</td><td class='rel'><div class='pz_detail'><p class='orderDetail'>详情<i></i></p><a href='javascript:;' onclick='MA.ViewPreRepayDetail("+financingId+")'>终止项目</a><a href='javascript:;' onclick='MA.renew("+financingId+")'>申请延期</a></div></td></tr>";
							}
		                }else{
		            	    html+="<td>"+statusStr+"</td><td></td></tr>";
		                }
					}else if(obj.statusDisplay==2){
						html+="<td>"+appDate.substr(0,10)+'<p>'+appDate.substr(10)+'</p>'+"</td><td>"+dueDate.substr(0,10)+'<p>'+dueDate.substr(10)+'</p>'+"</td><td class='orderNum' data-financingId='"+financingId+"'>"+financeName+"</td><td>"+financingAmount+"</td><td>"+repayAmount+"</td><td>"+financingRate+"</td><td>"+statusStr+"</td><td class='rel' status="+status+"><div class='pz_detail'><p class='orderDetail'>详情</p></div></td></tr>";
					}else{
						html+="<td>"+appDate.substr(0,10)+'<p>'+appDate.substr(10)+'</p>'+"</td><td>"+dueDate.substr(0,10)+'<p>'+dueDate.substr(10)+'</p>'+"</td><td class='orderNum'>"+financeName+"</td><td>"+financingAmount+"</td><td>"+repayAmount+"</td><td>"+financingRate+"</td><td>"+statusStr+"</td><td></td></tr>";
					}
				}
			}else{
				html += "<tr><td colspan='8' class='txt-c'>暂无记录</td></tr>";
			}
			loadPages(r.pageBean.pageNo, r.pageBean.pageSize, r.pageBean.sumPages, r.pageBean.pageList, dataTableId,"MA.getFinancingHistory");
			$("#waitTr").remove();
			$("#wdpz").append(html);
		},
		error:function(r){
			if(r.hasError){
				$("#waitTr td").text('请求出错啦，稍后再试试看吧☺');
			}
		}
	});
};


$(function(){
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	$('#aside-nav li:eq(6)').addClass('active');
	
	MA.getFinancingHistory('wdpz',1,12);
	
	$('.pz_detail').live('mouseenter',function(){
		$(this).addClass('pz_detail_hover');
	}).live('mouseleave',function(){
		$(this).removeClass('pz_detail_hover');
	});
	
	$('.orderDetail').live('click',function(){
		var orderNum=$(this).closest('tr').find('.orderNum').attr('data-financingId');
		var financingId=$(this).closest('tr').find('.orderNum').text();
		var rel=$(this).closest('tr').find('.rel').attr('status');
		var status=$(this).closest('tr').find('.rel').attr('status');
		window.open(systemConfig.path +"/account/wdzh_pzxq.jsp?financingId="+financingId+'#'+orderNum+'#'+status);
	});
});