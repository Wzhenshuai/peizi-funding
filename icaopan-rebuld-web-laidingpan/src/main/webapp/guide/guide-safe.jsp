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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link  href="<%=basePath%>css/icaopan.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=basePath%>/images/gdq.ico" mce_href="<%=basePath%>/images/gdq.ico" type="image/x-icon"/>
<title>新手指南</title>
<script>
	window.onload=function(){
		var li=document.getElementById('main-nav').getElementsByTagName('li')[4];
		li.setAttribute('class','current');
	};
</script>
</head>
<body>
    <body>
    	<div id="container">
		<jsp:include page="../common/header.jsp" />
            <div id="main">
            	<div class="guide-wrap">
                	<aside class="guide-side">
                    	<nav>
                        	<ul>
                            	<li><a href="guide/guideIndex.jsp"><i class="ui-icon i-square"></i><span>炒股流程</span></a></li>
                            	<li><a href="guide/guide-question.jsp"><i class="ui-icon i-question"></i><span>热门问题</span></a></li>
                            	<li class="current"><a href="javascript:;"><i class="ui-icon i-safe"></i><span>安全保障</span></a></li>
                            </ul>
                        </nav>
                    </aside>
                    <div class="guide-main">
						<div class="guide-QA">
                        	<section class="section1">
                            	<h2>综合实力保障</h2>
                                <dl>
                                	<dt>专业化精英团队</dt>
                                    <dd>i操盘平台核心团队成员主要来自国外著名投资银行、对冲基金，以及国内著名互联网科技公司、金融机构，互联网与金融经验丰富，值得您的信赖。</dd>
                                </dl>
                                <dl>
                                	<dt>对细节把控到位</dt>
                                    <dd>i操盘平台立志为客户提供全网最优的用户体验，事无巨细，您的一切需求我们都将尽力满足，只为给您提供极致股票投资服务。</dd>
                                </dl>
                            </section>
                        	<section class="section2">
                            	<h2>资金安全保障</h2>
                                <dl>
                                	<dt>资金银行监管</dt>
                                    <dd>对客户交易资金的保管完全按照“专户专款专用"的标准模式进行运作，客户在i操盘的交易资金是可以完全放心的。</dd>
                                </dl>
                                <dl>
                                	<dt>交易券商监管</dt>
                                    <dd>平台保证实盘交易，股票交易平台自主研发交易软件分别下单到各合作券商。</dd>
                                </dl>
                                <dl>
                                	<dt>专业风控及时止损</dt>
                                    <dd>i操盘平台设置了预警线及平仓线，每个交易日平台会计算投资者的信用账户中抵押证券的市值与债务之间的比率，保证投资资金安全，及时为您止损。</dd>
                                </dl>
                            </section>
                        	<section class="section3">
                            	<h2>平台技术保障</h2>
                                <dl>
                                	<dt>自主研发系统</dt>
                                    <dd>区别于全网所有股票交易平台，i操盘平台采用独立自主研发的交易系统，自动化运营管理及监控平台，支持直连境内外券商，支持多账户管理，完备的头寸管理和风险控制体系，架构完全适应互联网金融需求，基于公有云，易于扩展。</dd>
                                </dl>
                                <dl>
                                	<dt>信息加密技术</dt>
                                    <dd>i操盘平台采用了最先进的信息安全技术，能够最大程度上保护用户个人信息安全，平台启用三层防火墙隔离系统的访问层、应用层和数据层集群，有效防范入侵风险，并设立容灾备份机制，确保交易数据安全无虞。</dd>
                                </dl>
                                <dl>
                                	<dt>身份认证机制</dt>
                                    <dd>所有投资者进行股票交易操作前都要进行实名身份认证，有效防范恶意操作，违规行为所带来的风险，维护平台健康安全发展。</dd>
                                </dl>
                            </section>
                        	<section class="section4">
                            	<h2>用户注意事项</h2>
                                <dl>
                                	<dt>遵守用户协议</dt>
                                    <dd>实盘申请前，请您仔细阅读平台服务协议及投资顾问协议， 若您有任何不明白的地方，在工作时间内我们将十分乐意随时向您提供帮助。 签署电子协议之后，我们将严格遵守协议内容，也请您遵守用户协议，维护您的自身利益。</dd>
                                </dl>
                                <dl>
                                	<dt>牢记平台网址</dt>
                                    <dd>请您牢记 i操盘平台的唯一网址：<span class="text-red">www.icaopan.com</span>,不要轻易打开来历不明的链接访问i操盘网站，谨防钓鱼和欺诈网址。我们建议您将i操盘平台的网址添加到收藏夹，方便您的访问操作。</dd>
                                </dl>
                                <dl>
                                	<dt>密码强度管理</dt>
                                    <dd>为了您在平台的账户及资金安全，我们强烈建议您使用复杂密码进行操作，密码设置时，最好使用数字与字母结合，密码长度不低于6位。</dd>
                                </dl>
                                <dl>
                                	<dt>注意网络安全</dt>
                                    <dd>请您及时为您的电脑安装补丁，防止出现漏洞被黑客盗取个人信息，并请定时查毒，杀毒。此外请您保护好个人隐私，不要轻易向其他用户透露自己的关键信息，以防造成损失。</dd>
                                </dl>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
		<jsp:include page="../common/footer.jsp" />
        </div>
        <script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
    </body>
</html>
