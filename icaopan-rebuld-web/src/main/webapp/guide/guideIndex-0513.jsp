<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta charset="utf-8" />
<link rel="stylesheet" href="css/gudaiquan.css">
<link href="css/gudaiquan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-jslides.js"></script>
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<title>i操盘--新手指导</title>
</head>
<body>
<div id="container">
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div id="main">
    	<div class="guide-wrap">
        	<aside class="guide-side">
            	<nav>
                	<ul>
                    	<li class="current"><a href="guide-step1.html"><i class="ui-icon i-square"></i><span>炒股流程</span></a></li>
                    	<li><a href="guide-question.html"><i class="ui-icon i-question"></i><span>热门问题</span></a></li>
                    	<li><a href="guide-safe.html"><i class="ui-icon i-safe"></i><span>安全保障</span></a></li>
                    </ul>
                </nav>
            </aside>
            <div class="guide-main">
				<nav class="guide-step">
                	<ul class="clearfix">
                    	<li class="gs1 current">
                        	<a href="javascript:;">
                            	<i class="ui-icon i-nav-s1"></i>
                            	<span>1.选择配资方案</span>
                            </a>
                        </li>
                    	<li class="gs2">
                        	<a href="javascript:;">
                            	<i class="ui-icon i-nav-s2"></i>
                            	<span>2.充值</span>
                            </a>
                        </li>
                    	<li class="gs3">
                        	<a href="javascript:;">
                            	<i class="ui-icon i-nav-s3"></i>
                            	<span>3.开始实盘炒股</span>
                            </a>
                        </li>
                    	<li class="gs4">
                        	<a href="javascript:;" class="bd-none">
                            	<i class="ui-icon i-nav-s4"></i>
                            	<span>4.结束配资</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <div class="guide-con guide-con-pz">
                    <img src="../images/guide-step1-1.png" alt="">
                    <img src="../images/guide-step1-2.png" alt="">
                    <img src="../images/guide-step1-3.png" alt="">
                    <img src="../images/guide-step2-1.png" alt="">
                    <img src="../images/guide-step2-2.png" alt="">
                    <img src="../images/guide-step2-3.png" alt="">
                    <img src="../images/guide-step3.png" alt="">
                    <img src="../images/guide-step4.png" alt="">
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</div>
</body>
<script type="text/javascript">
$(function(){
	//菜单样式
	$('#main-nav li:eq(4)').addClass('current');
	
	// 新手指南滚动,if用户循环消除滚动效果
	$('.guide-con-pz').slidesjs({
        width: 671,
        height: 450,
        navigation: {
        	effect: "slide"
        },
        pagination: {
        	active: false
        },
        effect: {
        	fade: {
            	speed: 400
        	}
        },
		callback: {
			loaded: function(index, obj) {
				if(index==1){
					$(".slidesjs-previous").hide();
				}
				var $obj = obj;
				$(".gs1").click(function(e) {
                    $obj.goto(1);
                });
				$(".gs2").click(function(e) {
                    $obj.goto(4);
                });
				$(".gs3").click(function(e) {
                    $obj.goto(7);
                });
				$(".gs4").click(function(e) {
                    $obj.goto(8);
                });
			},
			complete: function(currentSlide) {
				if(currentSlide==8){
					$(".slidesjs-next").hide();
					$(".slidesjs-previous").show();
				}else if(currentSlide==1){
					$(".slidesjs-next").show();
					$(".slidesjs-previous").hide();
				}else{
					$(".slidesjs-next, .slidesjs-previous").show();
				}
				switch(currentSlide) {
					case 1:
						$(".gs1").addClass("current").siblings("li").removeClass("current");
						break;
					case 4:
						$(".gs2").addClass("current").siblings("li").removeClass("current");
						break;
					case 7:
						$(".gs3").addClass("current").siblings("li").removeClass("current");
						break;
					case 8:
						$(".gs4").addClass("current").siblings("li").removeClass("current");
						break;
				}
			}
		}
    });
});
</script>
</html>
