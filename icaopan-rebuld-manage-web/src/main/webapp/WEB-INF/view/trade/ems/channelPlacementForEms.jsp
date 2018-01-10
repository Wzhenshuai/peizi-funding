<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="default"></meta>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style>
			
		</style>
		<script type="text/javascript" src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
		<script type="text/javascript">
		
		function rowCallBack(nRow,aData){
        	var msg=aData.rejectMessage;
        	if(msg){
        		msg=msg.replace(/\r\n/ig,"");
            	if(msg.length>10){
            		var showMsg=msg.substring(0,10)+"...";
    	        	$("td:eq(12)",nRow).html("<a href='javascript:;' onclick='showRejectMessage(\""+msg+"\")'>"+showMsg+"</a>");
            	}
        	}
        }
		//定时刷新数据
		function autoFresh(){
			setInterval(function(){
				var check=$("#autoFresh").is(':checked');
				if(check){
					reloadTableData();
				}
			}, 10000);
		}
		function showSide(side){
		    switch(side){
		        case "BUY": return "买入";
		        case "SELL": return "卖出";
		        default: return side;
		    }
		}
		function loadEnd(){
			console.log("loadEnd--call");
			//将行情价格有问题的背景色设置为紫红色
			$("a[is_price_warn='true']").parent("td").parent("tr").css("background-color","fuchsia");
			//将正撤的背景色设置为黄色
	   		$("td:contains('正撤')").parent("tr").css("background-color","yellow");
			//将没有拿到委托编号的背景色设置为红色
			$("a[is_get_pc='false']").parent("td").parent("tr").css("background-color","red");

			if($("a[is_price_warn='true']").length > 0 && $('#warnSounds_id').is(':checked')){
				sounds();
			}else if($("a[is_get_pc='false']").length > 0 && $('#warnSounds_id').is(':checked')){
				sounds();
			}else if($("td:contains('正撤')").length > 0 && $('#warnSounds_id').is(':checked')){
				sounds();
			}
			$(".symbolLink").each(function(){
				var td = $(this).parent('td').siblings('td').eq(2).html(symbolProcess($(this).html()));
				$(this).remove();
			});
			$(".symbolLink").show();
			$("#pageTop").html($("#channelTable_info").html());
			$("#pageBar").html($("#channelTable_paginate").html());
		}
		function columnDefs(row){
	       	var side=row.side;
	       	var sideStr=row.sideStr;
	       	var symbol=row.securityCode;
       	    var cashAccountName=row.channelName;
       	    var channelName=row.channelName;
     	    var price=row.price;
     	    var quantity=row.quantity;
     	    var executionId=row.id;
     	    var is_get_pc=true;
	      	if(row.placementCode=="" || row.placementCode==null){
	      		is_get_pc=false;
	      	}
			var is_price_warn = true;
			var last_price = new Number(row.latestPrice);
			var price_num = new Number(price);
			if(side== 'BUY' && price_num <= last_price){
				is_price_warn = false;
			}
			if(side== 'SELL' && price_num >= last_price){
				is_price_warn = false;
			}
			if(is_get_pc==false || isFinish(row.status)){
				is_price_warn = false;
			}
	      	var action="<a is_get_pc='"+is_get_pc+"'></a><a is_price_warn='"+is_price_warn+"'></a>";
     	    action += "<a href='javascript:;'  onclick=showSignModal('"+executionId+"')>填委托编号</a>&nbsp;";
            if(isFinish(row.status)==false){
                action += "<a href='javascript:;' onclick=showFilledModal('"+executionId+"','"+channelName+"','"+symbol+"','"+side+"')>回报</a>&nbsp;";//实际应该弹出窗口填写价格及数量
                action += "<a href='javascript:;' onclick=sendRejectReport('"+executionId+"','"+channelName+"','"+symbol+"','"+sideStr+"','"+quantity+"','"+price+"')>废单</a>&nbsp;";
                action+="<a href='javascript:;' onclick=forceCancel('"+executionId+"','"+channelName+"','"+sideStr+"','"+symbol+"','"+price+"','"+quantity+"')>强撤</a>&nbsp;";
                action+="<a href='javascript:;' onclick=placementRepeat('"+executionId+"','"+channelName+"','"+side+"','"+symbol+"','"+price+"','"+quantity+"')>补单</a>&nbsp;";
                action+="<a href='javascript:;' onclick=cancelRepeat('"+executionId+"','"+channelName+"','"+symbol+"','"+sideStr+"','"+quantity+"','"+price+"')>补发撤单</a>&nbsp;";
            }
			action += symbolProcess(symbol);
            return action;
		}

		function symbolProcess(symbol){
			var sb=symbol;
			var fstLeter=sb.substring(0,1)
			if(fstLeter=="0"||fstLeter=="3"){
				sb="SZ"+sb;
			}else{
				sb="SH"+sb;
			}
			return "<a target='_blank' style='display:none;' class='symbolLink' href='http://xueqiu.com/S/"+sb+"'>"+symbol+"</a>";
		}

		function isFinish(status){
		    if(status=="FILLED"||status=="CANCELLED"||status=="INVALID"){
		        return true;
		    }
		    return false;
		}
		
		function showRejectMessage(msg){
			$("#rejectMessage").html(msg);
			$("#rejectMessageModal").modal('show');
		}
		//委托补单
		function placementRepeat(executionId,cashAccountName,tradeType,symbol,price,quantity){
			var str="委托通道："+cashAccountName+"<br/>股票代码："+symbol+"<br/>委托方向："+showSide(tradeType)+"<br/>委托价格："+price+"<br/>委托数量："+quantity+"<br/>"+"确认要向券商发送补单委托吗？";
			confirmxNew('补单确认',str,'placementRepeatToServer('+executionId+')');
		}
		function placementRepeatToServer(executionId){
			$.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: "${ctx}/ems/placementRepeat",//要访问的地址
		        data: "placementId="+executionId,//要发送的数据
		        success: function (data) {//data为返回的数据，在这里做数据绑定
		        	if(data.rescode=="success"){
                        alertx("补单请求成功");
		        		reloadTableData();
		            }else{
		            	alertx(data.message);
		            }
		        }
		    });
		}
		//弹出填委托编号对话框
		function showSignModal(executionId){
		    $("#signExecutionId").val(executionId);
		    $("#signModal").modal('show');
		}
		//填写委托编号
		function signFillPlacement(){
		    var executionId =  $("#signExecutionId").val();
		    var sno =  $("#sno").val();
		    $.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: "${ctx}/ems/signPlacementNo",//要访问的地址
		        data: "placementId="+executionId+"&placementNo="+sno,//要发送的数据
		        success: function (data) {//data为返回的数据，在这里做数据绑定
		            $("#signModal").modal('hide');
		            alertx(data.message);
		            reloadTableData();
		        }
		    });
		}
		
		//废单回报
		function sendRejectReport(executionId,channelName,symbol,side,quantity,price){
			var info=[];
			info.push("委托ID：");
			info.push(executionId+"<br/>");
			info.push("通道名称：");
			info.push(channelName+"<br/>");
			info.push("股票代码：");
			info.push(symbol+"<br/>");
			info.push("委托数量：");
			info.push(quantity+"<br/>");
			info.push("委托价格：");
			info.push(price+"<br/>");
			info.push("交易方向：");
			info.push(side+"<br/>");
			info=info.join("");
			confirmxNew('废单确认',info,'sendRejectReportToServer('+executionId+','+symbol+')');
		}
		function sendRejectReportToServer(executionId,symbol){
			$.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: "${ctx}/ems/sendRejectReport",//要访问的地址
		        data: "placementId="+executionId+"&symbol="+symbol,//要发送的数据
		        success: function (data) {//data为返回的数据，在这里做数据绑定
		            alertx(data.message);
		            reloadTableData();
		        }
	   		 });
		}		
		
		function showFilledModal(executionId,cashAccountName,symbol,side){
		    $("#fillExecutionId").val(executionId);
		    $("#fillCashAccountName").html(cashAccountName);
		    $("#fillSymbol").html(symbol);
		    $("#fillSide").html(showSide(side));
		    $("#fillModal").modal('show');
		}

		//成交回报
		function sendFilledReport(_this){
		    var executionId =  $("#fillExecutionId").val();
		    var price =  $("#fillPrice").val();
		    var quantity =  $("#fillQuantity").val();
		    //var reportNo=$("#reportNo").val();
		    if(!checkPrice(price)){
		    	alertx("请输入正确的价格");
		    	return;
		    }
		    if(!checkQuantity(quantity)){
		    	alertx("请输入正确的数量");
		    	return;
		    }
		    _this.disabled=true;
		    $.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: "${ctx}/ems/sendFilledReport",//要访问的地址
		        data: "placementId="+executionId+"&price="+price+"&quantity="+quantity,//要发送的数据
		        success: function (data) {//data为返回的数据，在这里做数据绑定
		            $("#fillModal").modal('hide');
		            $("#fillPrice").val("");
		            $("#fillQuantity").val("");
		            //$("#reportNo").val("");
		            alertx(data.message);
		            reloadTableData();
		            _this.disabled=false;
		        }
		    });
		}
		//撤单补单确认
		function cancelRepeat(executionId,channelName,symbol,side,quantity,price){
			//var str="确认要向券商发送撤单委托吗？";
			var info=[];
			info.push("委托ID：");
			info.push(executionId+"<br/>");
			info.push("通道名称：");
			info.push(channelName+"<br/>");
			info.push("股票代码：");
			info.push(symbol+"<br/>");
			info.push("委托数量：");
			info.push(quantity+"<br/>");
			info.push("委托价格：");
			info.push(price+"<br/>");
			info.push("交易方向：");
			info.push(side+"<br/>");
			info=info.join("");
			info+="确定补发撤单吗？";
			confirmxNew('补发撤单确认',info,'cancelRepeatToServer('+executionId+')');
		}
		function cancelRepeatToServer(executionId){
			$.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: "${ctx}/ems/cancelRepeat",//要访问的地址
		        data: "placementId="+executionId,//要发送的数据
		        success: function (data) {//data为返回的数据，在这里做数据绑定
		        	if(data.rescode=="success"){
		        		alertx("撤单请求成功");
		        		reloadTableData();
		            }else{
		            	alertx(data.message);
		            }
		        }
		    });	
		}
		
		
		//强撤确认
		function forceCancel(executionId,channelName,side,symbol,price,quantity){
			var info=[];
			info.push("委托ID：");
			info.push(executionId+"<br/>");
			info.push("通道名称：");
			info.push(channelName+"<br/>");
			info.push("股票代码：");
			info.push(symbol+"<br/>");
			info.push("委托数量：");
			info.push(quantity+"<br/>");
			info.push("委托价格：");
			info.push(price+"<br/>");
			info.push("交易方向：");
			info.push(side+"<br/>");
			info=info.join("");
			info+="<br/>确定执行强撤吗？";
			confirmxNew('强撤确认？',info,'forceCancelConfirm('+executionId+')');
		}
		//强撤执行
		function forceCancelConfirm(executionId){
			$.ajax({
		        type: "get",//使用get方法访问后台
		        dataType: "json",//返回json格式的数据
		        url: "${ctx}/ems/cancelPlacement",//要访问的地址
		        data: "placementId="+executionId,
		        success: function (data) {//data为返回的数据，在这里做数据绑定
		            if(data.rescode=="success"){
		        		alertx("强撤成功");
		        		reloadTableData();
		            }else{
		            	alertx(data.message);
		            }
		        }
		    });
		}
		function sounds(){
			var sound = new Howl({
				  src: ['${ctxStatic}/sounds/emsWarn.wav'],
				  loop: false,
				  onend: function() {
					    console.log('Finished!');
					  }
			});
		    sound.play();
		}
		$(function(){
			autoFresh();
		});

        function setChannel(customerId){
            var value = $("#customerId-search").val();
            console.log(value)
            var customeIds="";
            for (var i = 0 ; i< value.length ;i++){
                customeIds+=value[i];
                if (i!=value.length-1){
                    customeIds += ",";
                }
            }
            console.log(customeIds)
            $.ajax({
                "type" : 'post',
                "url" : "${ctx }/channel/findByCustomerIds?customerIds="+customeIds,
                "dataType" : "json",
                "success" : function(result) {
                    var arr = [];
                    for(var i = 0 ; i < result.length ;i++){
                        var data = {};
                        data["txt"] = result[i].name;
                        data["val"] = result[i].id;
                        arr.push(data);
                        data = null;
                    }
                    if (arr.length==1){
                        setSelectOption('channelId-search', arr, '-请选择-',arr[0].val);
                    }else {
                        setSelectOption('channelId-search', arr, '-请选择-');
                    }
                    mutipleSelect('channelId-search');
                },"error" : function (result){
                    alert("发生异常,或没权限...");
                }
            });
        }

        function mutipleSelect(objj) {
            $("#"+objj).multiselect("destroy").multiselect({selectAllValue:'',includeSelectAllOption: true,selectAll:true, allSelected:'\u5168\u90e8',numberDisplayed: 1,nonSelectedText:'\u8bf7\u9009\u62e9',selectAllText:'\u5168\u90e8',checkAllText: '\u5168\u90e8',selectAll:true,selectedList:100,allSelectedText:'\u5168\u90e8'});
        }
        
        mutipleSelect('customerId-search')

		</script>
	</head>
<body>
	<div class="portlet box red">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-coffee"></i>EMS管理
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<!-- 搜索框 start -->
				<div class="row">
					<div class="col-md-2">
						<c:choose>
							<c:when test="${ empty admin}">
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerIdList" id="customerId-search" onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);" multiple="multiple">
										<option value="">请选择</option>
											${fns:getCustomers()}
									</select>
								</div>
							</c:when>
							<c:otherwise>
								<div class="input-group">
									<span class="input-group-addon">资金方</span>
									<select class="form-control" name="customerIdList" id="-search" disabled = disabled onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
											${fns:getCustomers()}
									</select>
								</div>
							</c:otherwise>
						</c:choose>

					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon">交易通道</span>
							<select class="form-control" name="channelList" id="channelId-search" multiple="multiple">
								<option value="">请选择</option>
								${fns:getAvailableChannelsOpt()}
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">证券代码</span> <input
								type="text" class="form-control" name="securityCode"
								id="securityCode-search" placeholder="证券代码"
								aria-describedby="basic-addon1">
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon3">委托状态</span>
							<select class="form-control" name="statusList" id="status-search" multiple="multiple">
								${fns:getDicOptions("trade_status")}
							</select>
						</div>
					</div>
					<div class="col-md-1" style="margin-left:5px">
						<div class="input-group">
							自动刷新：<br/>
							<input type="checkbox" id="autoFresh"/>
						</div>
					</div>
					<div class="col-md-1">
						<div class="input-group">
							报警声音：<br/>
							<input type="checkbox" id="warnSounds_id" checked="true"/>
						</div>
					</div>
					<div class="col-md-1">
						<button class="btn btn-danger" onclick="reloadTableData();">
							<i class="glyphicon glyphicon-search"></i>查询
						</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 pull-left" id="pageTop">
						
					</div>
					<%--<div class="col-md-5 pull-right" style="text-align:right" id="pageBar">--%>
						<%----%>
					<%--</div>--%>
				</div>
				<!-- table 数据展示 start -->
				<table:table id="channelTable" 
					ajaxURI="${ctx}/channelPlacement/placementFind"
					columnNames="id,userName,securityCode,securityName,latestPrice,price,quantity,sideStr,fillQuantity,fillPrice,statusStr,placementCode,rejectMessage,customerName,channelName,dateTimeStr"
					headerNames="ID,用户名,证券代码,证券名称,现价,委托价格,委托数量,方向,成交数量,成交价格,状态,委托编号,拒绝原因,资金方,通道,创建时间,操作"
					searchInputNames="customerIdList,securityCode,side,statusList,channelList"
					pageLength="200" 
					makeDefsHtmlFunc="columnDefs" 
					rowCallBack="rowCallBack" 
					loadEnd="loadEnd()"
					/>
				<!-- table 数据展示 end-->
			</div>
		</div>
	</div>
	<!-- Modal -->
<div class="modal fade" id="fillModal" tabindex="-1" role="dialog" aria-labelledby="fillModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fillModalLabel">输入价格及数量</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="fillExecutionId"/>
			                交易通道： <span id="fillCashAccountName" class="b"></span><br/><br/>
			                股票代码：<span id="fillSymbol" class="b"></span><br/><br/>
			                委托方向：<span id="fillSide" class="b"></span><br/><br/>
			                成交价格：<input type="text" id="fillPrice" type="number"/><br/><br/>
			                成交数量：<input type="text" id="fillQuantity" type="number"/><br/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sendFilledReport(this);">确认提交</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="signModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="signModalLabel">委托编号</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="signExecutionId"/>
                委托编号：<input type="text" id="sno" value="-1"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="signFillPlacement();">确认提交</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="rejectMessageModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
        	<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="">废单原因</h4>
            </div>
            <div class="modal-body">
                <div id="rejectMessage">
                
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 强撤Modal -->
<div class="modal fade" id="forceCancelModal" tabindex="-1" role="dialog" aria-labelledby="forceCancelModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="forceCancelModalLabel">输入强撤数量</h4>
            </div>
            <div class="modal-body form-group">
                                       交易通道： <span id="forceCancelCashAccountName" class="b"></span><br/><br/>
                                        股票代码：<span id="forceCancelSymbol" class="b"></span><br/><br/>
                                        委托方向：<span id="forceCancelSide" class="b"></span><br/><br/>
                                        撤单数量：<input type="text" id="forceCancelQuantity"/>
            <input type="hidden" id="forceCancelExecutionId"/>
		    <input type="hidden" id="forceCancelSymbolCode"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="forceCancelConfirm();">确认提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="rejectMessageModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
        	<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="">废单原因</h4>
            </div>
            <div class="modal-body">
                <div id="rejectMessage">
                
                </div>
            </div>
        </div>
    </div>
</div>
<init:init-js></init:init-js>
</body>
</html>



