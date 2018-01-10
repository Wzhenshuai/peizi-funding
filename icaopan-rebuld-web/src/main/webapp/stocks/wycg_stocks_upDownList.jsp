<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <base href="<%=basePath%>" />
    <title>股票交易</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
    <link href="<%=basePath%>css/icaopan.css?<%= System.currentTimeMillis()%>" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</head>
<body>
<div id="container">
    <jsp:include page="../common/header.jsp" />

    <!--主要内容-->
    <div id="main">
        <div class="wrap clearfix">
            <jsp:include page="wycg_menu.jsp" />
            <div class="main-cont fr">
                <h2 class="rel wycg-title MT12">持仓<a href="<%=basePath%>trade/QueryHoldStocksInit.do">查看更多</a></h2>
                <table class="S-wtjl wycg-cctable" id="updownStockTable">
                    <tr>
                        <th width="116">股票简称</th>
                        <th width="115">涨跌停</th>
                        <%--<th width="115">最高价</th>
                        <th width="128">涨跌幅度</th>
                        <th width="120">振幅</th>
                        <th width="104">委比</th>
                        <th width="104">现价</th>
                        <th width="98">行情时间</th>--%>
                        <th></th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <!--主要内容结束-->
    <jsp:include page="../common/footer.jsp" />
</div>
<!--离开页面停止轮询-->
<script type="text/javascript" src="<%=basePath%>/js/util/visibility.js"></script>
<!--主要离开页面停止轮询-->

<script type="text/javascript" src="<%=basePath%>js/util/icp-dealmoney.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util/util-common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/wycg-updown.js"></script>
</body>
</html>
