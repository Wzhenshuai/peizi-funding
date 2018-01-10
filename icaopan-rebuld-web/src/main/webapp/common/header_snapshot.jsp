<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script type="text/javascript">
    var systemConfig = {
        path: "<%=path%>"
    }

    $(function () {
        //头页登录
        $("#navul > li").not(".navhome").hover(function () {
            $(this).addClass("navmoon");
            $('#downArr').attr('id', 'topArr');
        }, function () {
            $(this).removeClass("navmoon");
            $('#topArr').attr('id', 'downArr');
        });
        if ($('div.login-con').length > 0) {
            $('#userFlagPic').hide();
            $('div.nav_z').hide();
        }
    });


    (function ($) {
        $.fn.capacityFixed = function (options) {
            var opts = $.extend({}, $.fn.capacityFixed.deflunt, options);
            var FixedFun = function (element) {
                var top = opts.top;
                element.css({
                    "top": top
                });
                $(window).scroll(function () {
                    var scrolls = $(this).scrollTop();
                    if (scrolls > top) {

                        if (window.XMLHttpRequest) {
                            element.css({
                                position: "fixed",
                                top: 0
                            });
                        } else {
                            element.css({
                                top: scrolls
                            });
                        }
                    } else {
                        element.css({
                            position: "absolute",
                            top: top
                        });
                    }
                });
                element.find(".close-ico").click(function (event) {
                    element.remove();
                    event.preventDefault();
                })
            };
            return $(this).each(function () {
                FixedFun($(this));
            });
        };
        $.fn.capacityFixed.deflunt = {
            right: 0,//相对于页面宽度的右边定位
            top: 0
        };
    })(jQuery);
</script>

<!--[if lt IE 9]>
<script type="text/javascript" src="<%=basePath%>/js/html5.js"></script>
<![endif]-->

<header id="hd">
    <div class="bar">

        <div class="wrap clearfix">
            <div class="tab dropdown">
                <a href="javascript:;" class="tablink arwlink">
                    <img src="<%=basePath%>images/phone.png">
                    <span>客户端下载</span>
                    <b></b>
                </a>
                <ul class="subnav">
                    <div class="ewm-xs-left">
                        <a href="<%=basePath%>mobile/download.jsp"><img src="<%=basePath%>images/app_download.png"
                                                                        width="107px" height="107px"></a>
                    </div>
                    <div class="ewm-xs-right">
                        <p><a href="<%=basePath%>mobile/download.jsp" style="text-decoration: underline;">去下载</a></p>
                    </div>
                </ul>
            </div>
            <div id="userFlagPic" style="float:left;display:block;margin-left:630px; margin-top:8px;height:26px;"><i
                    class="ui-icon i-user-gray12"></i></div>
            <span class="mar-l5" style="display:block;float:left;">${param.userName }</span>
            <div sytle="display:block;float:right;">
                <a href="javascript:;">安全退出</a>
            </div>
        </div>

    </div>
    <div class="wrap">
        <nav>
            <%-- <%=basePath%>user/ViewIndex.do logo链接地址 --%>
            <!-- <a href="javascript:;" id="logo"><img src="images/logo.png" alt=""></a> -->
            <ul id="main-nav">
                <li>提示：360浏览器下单功能不稳定，建议使用以下浏览器</li>
                <li>
                    <a href="https://www.baidu.com/link?url=SHiTn2hpyZNpiaXt5aAM1oFA682brkQzLfiiMPBseeoOILhtzS3Q7VF9mU0-ZO3p1aXtQj1NFZQRGZuov_pDMAPUw3AxNVv3qQMRaQ5z4S_&wd=&eqid=e317726f000da3480000000258db119d"
                       target="_blank">Chrome浏览器下载</a></li>
                <li><a href="https://dldir1.qq.com/invc/tt/QQBrowser_Setup_SEM1.exe" target="_blank">QQ浏览器下载</a></li>
            </ul>
        </nav>
    </div>
</header>