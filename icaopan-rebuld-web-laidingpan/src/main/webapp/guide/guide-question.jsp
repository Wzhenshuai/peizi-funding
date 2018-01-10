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
	<div id="container">
    	<jsp:include page="../common/header.jsp" />
		<div id="main">
			<div class="guide-wrap">
				<aside class="guide-side">
					<nav>
						<ul>
							<li><a href="guide/guideIndex.jsp"><i class="ui-icon i-square"></i><span>炒股流程</span></a></li>
							<li class="current"><a href="javascript:;"><i class="ui-icon i-question"></i><span>热门问题</span></a></li>
							<li><a href="guide/guide-safe.jsp"><i class="ui-icon i-safe"></i><span>安全保障</span></a></li>
						</ul>
					</nav>
				</aside>
				<div class="guide-main">
					<div class="guide-QA">
						<dl>
							<dt>1、我的资金安全吗？</dt>
							<dd>您的每一笔保证金的流转在银行均有记录，i操盘与您签署的协议符合相关法律规定。您的股票交易受券商监管，每一笔交易都可以在level2行情查到。</dd>
						</dl>
						<dl>
							<dt>2、收不到验证码短信怎么办？</dt>
							<dd>
								<ol>
									<li>1）确认手机号码是否填写正确；</li>
									<li>2）请确认手机是否安装短信拦截或过滤软件；</li>
									<li>3）请确认手机是否能够正常接收短信（信号问题、欠费、停机等）；</li>
									<li>4）短信收发过程中可能会存在延迟，请耐心等待，短信在5分钟内均有效；</li>
									<li>5）请联系在线客服或致电4001-667-067。</li>
								</ol>
							</dd>
						</dl>
						<dl>
							<dt>3、为什么点击充值，第三方支付页面没有出现？</dt>
							<dd>点击充值后会跳转到第三方支付充值页面，请检查浏览器是否阻止弹出窗口，如有，请选择允许i操盘网站的所有弹窗操作。</dd>
						</dl>
                         <dl>
							<dt>4、为什么显示充值失败？</dt>
							<dd>
								<ol>
									<li>1）充值金额超过银行限额。请注意第三方支付充值页面下方所选择银行的支付限额，不同银行的限额可能不同；</li>
									<li>2）如没有超过银行限额，有可能是您使用的网络延迟导致，建议您稍后再试；</li>
									<li>3）充值过程中如有问题请联系在线客服或致电4001-667-067。</li>
								</ol>
							</dd>
						</dl>
                        <dl>
							<dt>5、我在充值的时候提示“异常”或者“充值失败”，但是钱已经扣了怎么办？</dt>
							<dd>请您登录i操盘账户，查询现金账户资产与充值明细，如确实与您的充值金额不符，请截图并发送给在线客服,并提供您的注册手机号、姓名和身份证号码。我们会查询后尽快处理并给您反馈。</dd>
						</dl>
                        <dl>
							<dt>6、如何提取盈利？</dt>
							<dd>申请提取盈利，必须满足【股票总资产>总操盘资金】时才可以操作。
                                <ol>
                                <li>A：股票账户可用余额 &gt; 股票总资产 − 总操盘资金 * 110%<br>
										盈利可转出金额=股票总资产−总操盘资金 * 110%<br>
										例如：股票总资产35000元，总操盘资金30000元，股票账户可用余额3000元，盈利可转出金额就为2000元</li>
                               <li>B：股票账户可用余额 &lt; 股票总资产−总操盘资金 * 110%<br>
										提取盈利金额 =股票账户可用余额<br>
										例如：股票总资产35000元，总操盘资金30000元，股票账户可用余额1000元，盈利可转出金额就为1000元。
								</li>
								</ol>
							</dd>
						</dl>						
                        <dl>
							<dt>7、提现时提示“取现失败”或“异常”怎么办？</dt>
							<dd>请您登录i操盘账户，查询现金账户资产与提现明细，如确实提现失败，请截图并发送给在线客服，并提供您的注册手机号、姓名、身份证号码。我们会及时处理并给您反馈。
							</dd>
						</dl>
						<dl>
							<dt>8、为什么我的提现未到账？</dt>
							<dd>
								<ol>
									<li>1） 银行开户行信息填写错误；</li>
									<li>2） 银行账号/户名填写错误，或是账号与户名不符；</li>
									<li>3） 银行账户冻结或正在办理挂失；<li>
									<li>如果遇到以上情况，我们会在收到银行转账失败的通知后解除您的提现资金冻结，并及时通知您相关信息，请您不必担心资金安全。如有问题请联系在线客服或致电4001-667-067。</li>
								</ol>
							</dd>
						</dl>
						<dl>
							<dt>9、设定实名后，还可以修改吗？</dt>
							<dd>根据国家金融监管机构要求，为保障您的账户资金安全，需要您进行实名认证。实名认证后认证信息不可修改。</dd>
						</dl>
						<dl>
							<dt>10、实盘申请需要支付哪些费用？</dt>
							<dd>在i操盘进行实盘申请，平台只收取系统使用费，不收取任何其他费用。股票交易还需缴纳交易手续费，包含印花税、沪市股票过户费和交易佣金，印花税和过户费按财政部和交易所规定收取，交易佣金0.08%（万分之八）由券商收取。炒股赚钱之后，所有的盈利100%都归您。</dd>
						</dl>
						<dl>
							<dt>11、实盘审核需要多久？</dt>
							<dd>交易日工作时间（9:00-18:00）申请，正常情况下1小时内即可完成审核；非交易日申请，审核会在下一个交易日上午完成。</dd>
						</dl>
						<dl>
							<dt>12、实盘申请后何时开始收取系统使用费？</dt>
							<dd>从平台审核通过起开始计算。</dd>
						</dl>
						<dl>
							<dt>13、多个实盘炒股项目是否可以使用一个股票交易账户？</dt>
							<dd>可以。平台支持多次申请实盘项目，一个实盘炒股项目到期时只需将此项目的相关金额归还平台，剩余资金可以继续用于股票操盘，最后一个实盘炒股项目到期时，您的盈利将会全部结算至现金账户中。</dd>
						</dl>
						<dl>
							<dt>14、我可以提前终止实盘项目吗？</dt>
							<dd>
								<ol>
									<li>您可以选择在项目到期前的任何一天提前终止，在“我的账户——实盘项目——操盘中”的项目中选择相应的项目“提前终止”即可，提前终止需缴纳违约金：</li>
									<li>当项目周期剩余0个月内时，提前终止无需缴纳违约金；</li>
									<li>当项目周期剩余2个月内时，提前终止需缴纳下个月系统使用费20%的违约金；</li>
									<li>当项目周期剩余3-6个月时，提前终止需缴纳下个月系统使用费40%的违约金；</li>
								</ol>
							</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</div>
	<script type="text/javascript" src="<%=basePath%>/js/util/jquery-1.8.0.min.js"></script>
</body>
</html>
