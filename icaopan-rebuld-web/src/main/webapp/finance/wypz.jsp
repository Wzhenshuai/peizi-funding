


<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.isoftstone.com/sdc/toft3" prefix="t"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
    <base href="<%=basePath%>" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>实盘申请</title>
    <link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon" />
    <link rel="stylesheet" href="css/icaopan.css" type="text/css" />
    <!--[if lt IE 9]><script src="/js/html5.js"></script><![endif]-->
    <script type="text/javascript">
        var systemConfig = {
            path:"<%=basePath%>"

        }

    </script>

</head>
<body>

<div id="container">
    <jsp:include page="../common/header.jsp" />
    <input type="hidden" value="${principal }" id="principal" />
    <!--主要内容-->
    <div id="main">
        <div class="wrap">
            <div class="step">
                <ul class="clearfix">
                    <li class="s1 current"><em>1</em><span>选择实盘方案</span></li>
                    <li class="s2"><em>2</em><span>确认实盘方案</span></li>
                    <li class="s3"><em>3</em><span>完成申请</span></li>
                </ul>
            </div>
            <div class="wypz-h2">
                <p><span id="used-icon"></span>已使用资金<span id="used-rate">100%</span><span id="unused-icon"></span>可用资金<span id="unused-rate">0%</span></p>
                <div class="clearfix" style="margin-left:125px; font-size: 16px;"><span class="fl" style="margin-top:-3px;">今日平台资金情况</span><span id="progress-bar"><span></span></span></div>
            </div>

            <div class="MT40 clearfix">
                <div class="half-l bd-dashed-r">
                    <!-- 请输入您的保证金 -->
                    <section class="tt-radius">
                        <h2><span class="inline-block num-radius">1</span><span class="v-mid">输入您的保证金</span></h2>
                        <div class="m bzj">
                            <input name="" id="ipt-bzj" type="text" placeholder="1千-50万" value="" min="1000" max="500000"/>
                            <span class="v-top">元</span>
                        </div>
                    </section>

                    <!-- 选择您的配资倍数 -->
                    <section class="tt-radius">
                        <h2><span class="inline-block num-radius">2</span><span class="v-mid">请选择您的申请倍数</span></h2>
                        <div class="m pzbs-wrap">

                            <!-- 判断如果当前2倍禁用将1倍设置成默认 -->
                            <c:choose>
                                <c:when test="${pzbs2 eq true}">
                                    <input id="pzbs" type="hidden" value="1"/>
                                    <a href="javascript:;" class="a-pzbs inline-block current" data-val="1">
                                        <strong>1</strong>倍
                                        <span>申请倍数</span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="javascript:;" class="a-pzbs inline-block" data-val="1">
                                        <strong>1</strong>倍
                                        <span>申请倍数</span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            <!-- 判断如果当前2倍禁用 将展示信息改成额度用完 并置成不可点 -->
                            <c:choose>
                                <c:when test="${pzbs2 eq true}">
                                    <a href="javascript:;" class="a-pzbs inline-block noselect" data-val="2">
                                        <strong style="color: gray;!important">2</strong>倍
                                        <span style="color: red;!important">今日额度用尽</span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <input id="pzbs" type="hidden" value="2"/>
                                    <a href="javascript:;" class="a-pzbs inline-block current" data-val="2">
                                        <strong>2</strong>倍
                                        <span>申请倍数</span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${pzbs3 eq true}">
                                    <a href="javascript:;" class="a-pzbs inline-block noselect" data-val="3">
                                        <strong style="color: gray;!important">3</strong>倍
                                        <span style="color: red;!important">今日额度用尽</span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="javascript:;" class="a-pzbs inline-block" data-val="3">
                                        <strong>3</strong>倍
                                        <span>申请倍数</span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </section>
                </div>
                <div class="half-r">
                    <section class="tt-radius">
                        <h2><span class="inline-block num-radius">3</span><span class="v-mid">操盘信息</span></h2>
                        <div class="m">
                            <div class="note">
                                <b>操盘须知</b>
                                <p>总申请金额50万以下，沪深A股实盘交易，仓位不限制，盈利全归您</p>
                                <p>总申请金额50万以上，投资沪深A股，仓位有限制，盈利全归您，主板单股不超总操盘资金的60%（创业板30%）</p>
                            </div>

                            <!-- 文字空格勿删 -->
                            <ul class="data">
                                <li><span class="label">本次总操盘资金<em class="question" tips="本次总操盘资金=保证金+实盘申请金额">?</em>：</span><strong class="total-amount">0</strong>元</li>
                                <li><span class="label">本次亏损警告线<em class="question" tips="当股票账户总资产触及或低于亏损警告线时，您将只能卖出股票，不能买入股票。">?</em>：</span><strong class="loss-warning">0</strong>元</li>
                                <li><span class="label">本次亏损平仓线<em class="question" tips="当股票账户总资产触及或低于亏损平仓线时，您将不能买入或卖出股票，平台会对操盘账户内的证券采取强制平仓的操作。">?</em>：</span><strong class="loss-close">0</strong>元</li>
                                <li>
                                    <span class="label">实盘操作期限：</span>
                                    <select class="v-mid mar-t5" name="cycle" id="cycle">
                                        <c:forEach var="i" begin="1" end="6" step="1">
                                            <option value="${i }" <c:if test="${i==cycle }"> selected="selected" </c:if>>${i }个月</option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li><span class="label">系统使用费率：</span><span class="v-mid pad-t5" id="interest">0</span><span class="v-mid pad-t5">分/月(<span class="text-red">优惠</span>)</span></li>
                                <li class="ft MT40 hide"><input type="checkbox" id="cbx-pz" autocomplete="off" checked /> <span class="v-mid"><label for="cbx-pz">我已阅读并同意</label><a href="javascript:;" id="a-agreement">《i 操盘投资顾问协议》</a></span></li>
                                <li class="ft">
                                    <a href="javascript:;" class="btn btn-gray" id="submit-pz">提交</a>
                                    <div class="inline-block lx">
                                        <a href="javascript:;">系统使用费试算<em>&#9660;</em></a>
                                        <div class="pop-gray pop-lx">
                                            <em class="e1">&#9650;</em>
                                            <!-- <em class="e2">&#9660;</em> -->
                                            <table id="rePayList">
                                                <thead>
                                                <tr><th>日期</th><th>类型</th><th>金额</th></tr>
                                                </thead>
                                                <tbody></tbody>
                                            </table>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </section>
                </div>
            </div>

            <!-- 配置须知 -->
            <section class="pz-note">
                <h2>温馨提示</h2>
                <ol>
                    <li>实盘炒股需要实名认证，请在注册时完成，或登录后在“我的账户——安全中心”验证您的姓名和身份证</li>
                    <li>您可以多次申请实盘项目，您在平台的最大操盘金额为200万元</li>
                    <li>操盘保证金：最少1000，且必须是100的整数倍</li>
                    <li>资金使用期限和费用：资金使用期限以月为单位，按计费月计算，如您在5月18日申请实盘三个月，资金使用期限从5月18日-8月17日。系统使用费分别在5月18日，6月18日，7月18日，从您的现金账户扣除</li>
                    <li>亏损警告线：当总操盘资金低于亏损警告线时，只能平仓不能建仓 </li>
                    <li>亏损平仓线：当总操盘资金低于亏损平仓线时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注总操盘资金是否充足</li>
                </ol>
            </section>
        </div>
    </div>
    <!--主要内容结束-->
    <jsp:include page="../common/footer.jsp" />
</div>
<!-- 协议弹出框 -->
<%-- <jsp:include page="../common/agreement.jsp" /> --%>
<script type="text/javascript" src="<%=basePath%>js/util/jquery-1.8.0.min.js"></script>

</body>
<script type="text/javascript" src="http://t.icaopan.com/dist/script/application.js?v=20150709"></script>

</body>
</html>

