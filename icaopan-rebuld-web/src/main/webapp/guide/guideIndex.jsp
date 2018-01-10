<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link  href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<title>新手指南</title>
</head>
<body>
<div id="container">
<jsp:include page="../common/header.jsp"></jsp:include>
<div id="main">
    	<div class="guide-wrap">
          	<aside class="guide-side">
              	<nav>
                  	<ul>
                      	<li class="current"><a href="guide/guideIndex.jsp"><i class="ui-icon i-square"></i><span>炒股流程</span></a></li>
                      	<li><a href="guide/guide-question.jsp"><i class="ui-icon i-question"></i><span>热门问题</span></a></li>
                      	<li><a href="guide/guide-safe.jsp"><i class="ui-icon i-safe"></i><span>安全保障</span></a></li>
                      </ul>
                  </nav>
              </aside>
              <div class="guide-main">
				<nav class="guide-step">
                  	<ul class="clearfix">
                      	<li class="gs1 current">
                          	<a href="javascript:;">
                              	<i class="ui-icon i-nav-s1"></i>
                              	<span>1.选择实盘方案</span>
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
                              	<span>4.结束实盘方案</span>
                              </a>
                          </li>
                      </ul>
                  </nav>
                  <div class="guide-con guide-con-pz">
                      <img src="<%=basePath%>/images/guide-step1-1.png" alt="">
                      <img src="<%=basePath%>/images/guide-step1-2.png" alt="">
                      <img src="<%=basePath%>/images/guide-step1-3.png" alt="">
                      <img src="<%=basePath%>/images/guide-step2-1.png" alt="">
                      <img src="<%=basePath%>/images/guide-step2-2.png" alt="">
                      <img src="<%=basePath%>/images/guide-step2-3.png" alt="">
                      <img src="<%=basePath%>/images/guide-step3.png" alt="">
                      <img src="<%=basePath%>/images/guide-step4.png" alt="">
                  </div>
              </div>
          </div>
      </div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/jquery.slides.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/page/guide.js"></script>
</body>
</html>
