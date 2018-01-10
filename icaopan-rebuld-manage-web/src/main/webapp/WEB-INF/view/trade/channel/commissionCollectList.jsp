<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<init:init-js/>
<html>
<head>
    <title>佣金差额汇总</title>
    <meta name="decorator" content="default"></meta>
    <script type="text/javascript" src="${ctxStatic}/relatedSelect-js\relectedSelect-js.js"></script>
</head>
<body>
<div class="portlet box red">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-coffee"></i>佣金差额汇总
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-responsive">
            <!-- 搜索框 start -->
            <div class="container-fuild">
                <div class="row">
                    <div class="col-md-3">
                        <c:choose>
                            <c:when test="${ empty admin}">
                                <div class="input-group">
                                    <span class="input-group-addon">资金方</span>
                                    <select class="form-control" name="customerId" id="customerId-search"
                                            onchange="if(this.value != '') setChannel(this.options[this.selectedIndex].value);">
                                        <option value="">请选择</option>
                                            ${fns:getCustomers()}
                                    </select>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="input-group">
                                    <span class="input-group-addon">资金方</span>
                                    <select class="form-control" name="customerId" disabled=disabled>
                                            ${fns:getCustomers()}
                                    </select>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon3">通道</span>
                            <select class="form-control" name="channelId" id="channelId">
                                <option value="">请选择</option>
                                ${fns:getChanelOpt()}
                            </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-start">开始日期</span> <input
                                type="text" class="form-control" name="startTime"
                                onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'date-search-end\')}'})"
                                id="date-search-start" placeholder="开始日期"/>
                        </div>
                    </div>
                    <div class="col-md-2 col-lg2">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-end">结束日期</span> <input
                                type="text" class="form-control" name="endTime"
                                onfocus="WdatePicker({minDate:'#F{$dp.$D(\'date-search-start\')}',maxDate:'%y-%M-%d'})"
                                id="date-search-end" placeholder="结束日期"/>
                        </div>
                    </div>
                    <div class="col-md-2 pull-right text-right">
                        <button class="btn btn-danger" onclick="reloadTableData();">
                            <i class="glyphicon glyphicon-search"></i>查询
                        </button>
                        <%--<button class="btn btn-danger"
                            onclick="exportExcel('${ctx }/channelPlacement/exportChannelHistory')">
                            <i class="glyphicon glyphicon-search"></i>导出
                        </button>--%>
                    </div>
                </div>
                <div class="container-fuild">
                    <!-- 搜索框 end -->
                    <!-- table 数据展示 start -->
                    <table:table id="channelTable"
                                 ajaxURI="${ctx}/channelPlacement/commissionCollect"
                                 columnNames="channelName,fillAmount,tradeCommissionRate,tradeCommissionFee,sysCommissionFee,differenceBanlance"
                                 headerNames="通道名称,成交总金额,券商佣金比例,券商佣金总额,系统佣金总额,佣金差额"
                                 searchInputNames="customerId,channelId,startTime,endTime"/>
                    <!-- table 数据展示 end-->
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function setChannel(customerId) {
        $.ajax({
            "type": 'post',
            "url": "${ctx }/channel/findByCustomerId?customerId="
            + customerId,
            "dataType": "json",
            "success": function (result) {
                var arr = [];
                for (var i = 0; i < result.length; i++) {
                    var data = {};
                    data["txt"] = result[i].name;
                    data["val"] = result[i].id;
                    arr.push(data);
                    data = null;
                }
                if (arr.length == 1) {
                    setSelectOption('channelId', arr, '-请选择-',
                        arr[0].val);
                } else {
                    setSelectOption('channelId', arr, '-请选择-');
                }
            },
            "error": function (result) {
                alert("发生异常,或没权限...");
            }
        });
    }
</script>
</body>
</html>



