<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>

</head>
	<body>
		<div class="portlet box red">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-coffee"></i>下单测试
				</div>
			</div>
			<div class="portlet-body">
				<div class="table-responsive">
					<div class="row">
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" >股票代码</span>
								<input type="text" class="form-control" name="stockCode" value="600022" id="stockCode" placeholder="股票代码" aria-describedby="basic-addon1">
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" >数量</span>
								<input type="number" class="form-control" value="100" name="quantity" id="quantity" placeholder="数量" aria-describedby="basic-addon1">
							</div>
						</div>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon" >价格</span>
								<input type="" class="form-control" name="price" id="price" placeholder="价格" aria-describedby="basic-addon1">
							</div>
						</div>

							<div class="col-md-3">
							<button class="btn btn-primary" onclick="reloadTableData();"> <i class="glyphicon glyphicon-search"></i>查询</button>
							<button id="delBatch" class="btn btn-primary" onclick="doPlace()">批量下单</button>
						</div>
					</div>
					<div id="back_esult"></div>
					<form id="placementForm" method="post">
						<table:table id="userTable" ajaxURI="${ctx}/personPlacement/findTestTrader"
									 columnNames="placeHolder,userName,realName,channelName"
									 headerNames="\<input type='checkbox' id='checkAll' onclick='selectAllChecxkBox(this)'\>id,用户名,真实姓名,通道名称,备注"
									 makeDefsHtmlFunc="columnDef"
									 loadEnd="LoadEnd()"/>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript">

            function columnDef(rowdata){
                //ids.push(rowdata.id);
               // console.log("ids===="+ids+"--------rowdataID==="+rowdata.id+"------rowdata.userName===="+rowdata.userName+"--------rowdata.channel.name==="+rowdata.channelName);
                if (rowdata.userName==null||rowdata.userName==""){
                    return ""+"<input name='checkId' class='box' type=\"checkbox\" value='"+rowdata.id+"'>";
                }else {
                    return rowdata.realName+"<input name='checkId' class='box' type=\"checkbox\" value='"+rowdata.id+"'>";
                }

            }
            
            function f_result(r_data) {
                $("#back_esult").html(r_data);
            }

            function LoadEnd(){
                $(":checkbox[name=checkId]").each(function(){
                    var tr = $(this).parents('tr');
                    var td = tr.children("td:first");
                    td.empty();
                    $(this).prependTo(td);
                    // $(this).parents('tr').children("td:first").append('<lable>'+i+'</lable>');
                });
            }
            function selectAllChecxkBox(obj){
                if (obj.checked) {
                    $("input[name='checkId']:checkbox").each(function() { //遍历所有的name为delIds的 checkbox
                        $(this).attr("checked", true);
                    })
                } else {   //反之 取消全选
                    $("input[name='checkId']:checkbox").each(function() { //遍历所有的name为delIds的 checkbox
                        $(this).attr("checked", false);
                    })
                }
            }
            function doPlace(){


                var stockCode= $("#stockCode").val().trim();
                var quantity = $("#quantity").val();
                var price = $("#price").val();

                var ids = new Array();
                var count = $('input:checked').length;
                $("input[name='checkId']:checked").each(function() {
                    ids.push($(this).val());
                });

                if(count == 0) {
                    alertx("请选择下单的用户名");
                    return false;
                }
                if (stockCode==""){
                    alertx("股票代码不能为空");
					return false;
                }
                if (quantity==""){
                    alertx("数量不能为空");
					return false;
                }
                if (price==""){
                    alertx("价格不能为空");
                    return false;
                }
                console.log(ids);
                $.ajax({
                    type : 'post',
                    url : "${ctx}/personPlacement/autoDoPlacement",
                    dataType : "json",
                    "traditional": "true",
                    "data" : {"stockCode":stockCode,"quantity":quantity,"price":price,"ids":ids},
                    "success" : function(result) {
                        //document.getElementById("delBatch");
                        f_result(result.message);
						/*刷新*/
                        //window.location.href="${ctx}/personPlacement/initAutoPlacement";
                    },"error" : function (result){
                        alertx("发生异常,或没权限...");
                    }
                });
            }


		</script>
	</body>
</html>

