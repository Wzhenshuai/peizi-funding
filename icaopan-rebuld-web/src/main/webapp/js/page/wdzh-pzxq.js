var orderDetail = function (dataTableId, pageNo, pageSize){
		var _node=$("#"+dataTableId);
		var params=window.location.href.split('financingId=')[1];
		var status=params.split('#')[2];
		var financingId=params.split('#')[1];
		$(".pzbh").html(params.split('#')[0]);
		var trs=_node.find("tr");
		trs.not(trs.eq(0)).remove();
		_node.append("<tr id='waitTr'><td colspan='8' class='txt-c'>加载中...</td></tr>");
		if(status==8){
			$.ajax({
				url:systemConfig.path+"/finance/getRepayDetailList.ajax",
				data:{"pageNo":pageNo,"pageSize":pageSize,"financingId":financingId},
				success:function(r){
					
					if(r.resCode=="login"){
						location.href=systemConfig.path +"/user/login.jsp";
					}
					var list = r.planList.dataList;
					var html="";
					if (list != null && list.length > 0) {
						for(var i=0;i<list.length;i++){
							var obj=list[i];
							var orderTime = obj.confirmdateStr;
							var orderName = obj.tradetypeStr;
							var orderAmount = obj.replayamount;
							html += "<tr";
							if (i % 2 == 0) {
								html += " class='odd'>";
							} else {
								html += ">";
							}
							html+="<td class='txt-c'>"+orderTime+"</td><td>"+orderName+"</td><td>"+orderAmount+"</td><td>";
							if(obj.status){
								html+='<span class="text-red">';
							}else{
								html+='<span class="text-green">';
							}
							html+='已付'+"</span></td>";
						}
					}else{
						html += "<tr><td colspan='4' class='txt-c'>暂无记录</td></tr>";
					}
					loadPages(r.planList.pageNo, r.planList.pageSize, r.planList.sumPages, r.planList.pageList, dataTableId,"orderDetail");
					
					$("#waitTr").remove();
					$("#"+dataTableId).append(html);
					$('#zcpzj').html(r['vo']['stockValue']);
					$('#bzj').html(r['vo']['principal']);
					$('#bzj1').html(r['vo']['principal']);
					$('#circles').html(r['vo']['cycle']);
				}
			});
		}else{
			$.ajax({
				url:systemConfig.path+"/finance/GetRepayPlanList.ajax",
				data:{"pageNo":pageNo,"pageSize":pageSize,"financingId":financingId},
				success:function(r){
					
					$('#zcpzj').html(r['vo']['stockValue']);
					$('#bzj').html(r['vo']['principal']);
					$('#bzj1').html(r['vo']['principal']);
					$('#circles').html(r['vo']['cycle']);
					if(r.resCode=="login"){
						location.href=systemConfig.path +"/user/login.jsp";
					}
					var list = r.planList.dataList;
					var html="";
					if (list != null && list.length > 0) {
						for(var i=0;i<list.length;i++){
							var obj=list[i];
							var orderTime = obj.planreplaydatestr;
							var orderName = obj.remark;
							var orderAmount = obj.replayamount;
							var orderStatus = obj.statusStr;
							html += "<tr";
							if (i % 2 == 0) {
								html += " class='odd'>";
							} else {
								html += ">";
							}
							html+="<td class='txt-c'>"+orderTime+"</td><td>"+orderName+"</td><td>"+orderAmount+"</td><td>";
							if(obj.status){
								html+='<span class="text-red">';
							}else{
								html+='<span class="text-green">';
							}
							html+=orderStatus+"</span></td>";
						}
					}else{
						html += "<tr><td colspan='4' class='txt-c'>暂无记录</td></tr>";
					}
					loadPages(r.planList.pageNo, r.planList.pageSize, r.planList.sumPages, r.planList.pageList, dataTableId,"orderDetail");
					$("#waitTr").remove();
					$("#"+dataTableId).append(html);
				}
			});
		}
	};
	
	$(function(){
		//导航焦点态
		$('#main-nav li:eq(3)').addClass('current');
		// 股贷圈协议
		$("#a-agreement").click(function(e) {
	        Common.LightBox.show();
			var $agreement = $("#popup-agreement");
			CU.setObjAbsCenter($agreement);
			$agreement.fadeIn();
			e.stopPropagation();
	    });
		
		orderDetail('pzxq',1,12);
	});
	
	// 关闭弹出框
	$(".close-pop").click(function(e) {
		$(".popup").hide();
		Common.LightBox.hide();
	});