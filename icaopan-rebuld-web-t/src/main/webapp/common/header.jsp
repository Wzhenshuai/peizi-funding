<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
var systemConfig = {
	    path:"<%=path%>"
}

//setTimeout(checkoutBrowser,1000);

function checkoutBrowser() {
    if(/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))){
        if(window.location.href.indexOf("?mobile")<0){
            try{
                if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)){
                    alert('检测到您正在使用移动设备，正在跳转到下载页面');
                    window.location.href="<%=basePath%>mobile/download.html";
                }else if(/iPad/i.test(navigator.userAgent)){
                    window.location.href="<%=basePath%>mobile/download.html";
                }
            }catch(e){}
        }
    }
    if(is360()){
    	alert("若您使用360浏览器，请使用极速模式访问或者使用其他浏览器");
    }
    
}

function is360(){  
    if((window.navigator.mimeTypes[40] || !window.navigator.mimeTypes.length)){
		return true;
	}
}  

function checkLoginAndOpenDia(url){
	var login=false;
	$.ajax({
		url:systemConfig.path+"/user/CheckIsLogin.ajax",
		success:function(r){
			if(r.isLogin==true){
				if(url!=null&&url!="undefined"){
					location.href=url;
				};
			}else{
				location.href=systemConfig.path+"/user/login.jsp?visitPath="+url;
			};
		}
	});
	return login;
}

function switchUser(userId){
	$('#userId').val(userId);
	document.getElementById('userSwitch').submit();
}


/*
*author:Null
*DATE:2013.5.24
*/

$(function(){		   
	//头页登录
	$("#navul > li").not(".navhome").hover(function(){
		$(this).addClass("navmoon");
		$('#downArr').attr('id', 'topArr');
	},function(){
		$(this).removeClass("navmoon");
		$('#topArr').attr('id', 'downArr');
	});
	if($('div.login-con').length > 0){
		$('div.nav_z').hide();
	}	
}); 


(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
        var FixedFun = function(element) {
            var top = opts.top;
            element.css({
                "top":top
            });
            $(window).scroll(function() {
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
                }else {
                    element.css({
                        position: "absolute",
                        top: top
                    });
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.capacityFixed.deflunt={
		right : 0,//相对于页面宽度的右边定位
        top:0
	};
})(jQuery);
</script>

<!--[if lt IE 9]>
   <script type="text/javascript" src="<%=basePath%>/js/html5.js"></script>
 <![endif]-->

 <header id="hd">
  	<div class="bar">
  	
     	<div class="wrap clearfix">
     		
			<div sytle="display:block;float:right;">
				 <c:choose>
				<c:when test="${!empty sessionScope.user }">
				
					<c:choose>
						<c:when test="${!empty sessionScope.user.realName}">
							<c:choose>
								<c:when test="${!empty sessionScope.associatedUsers }">
									<div class="nav_z tab" >
										<ul id="navul" class="cl">
											<li><a href="javascript:;" class="tablink arwlink"> <span>${sessionScope.user.realName}</span><b id="downArr"></b></a>
												<form id="userSwitch" name="userSwitch" method="post"
													  action="<%=basePath%>user/SwitchUser.do">
													<ul>
														<c:forEach var="item" items="${sessionScope.associatedUsers}">
															<li><a href="javascript:void(0);" onclick="switchUser(${item.id})">
																<c:choose>
																	<c:when test="${!empty item.realName  }">
																		${item.realName}
																	</c:when>
																	<c:otherwise>
																		${item.usernameHidePart}
																	</c:otherwise>
																</c:choose>
															</a></li>
														</c:forEach>
													</ul>
													<input type="hidden" id="userId" name="userId">
													<input type="hidden" name="visitPath"
														   value="<c:choose><c:when test='${empty visitPath }'>${param.visitPath }</c:when><c:otherwise>${visitPath }</c:otherwise></c:choose>" />
												</form>
											</li>
										</ul>
									</div>

								</c:when>
								<c:otherwise>
									当前用户：<b>${sessionScope.user.realName }</b>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${!empty sessionScope.associatedUsers}">
									<div class="nav_z tab" >
										<ul id="navul" class="cl">
											<li><a href="javascript:;" class="tablink arwlink"> <span>${sessionScope.user.realName}</span><b id="downArr"></b></a>
												<form id="userSwitch" name="userSwitch" method="post"
													  action="<%=basePath%>user/UserSwitch.do">
													<ul>
														<c:forEach var="item" items="${sessionScope.associatedUsers}">
															<li><a href="javascript:void(0);" onclick="switchUser(${item.id})">
																<c:choose>
																	<c:when test="${!empty item.realName  }">
																		${item.realName}
																	</c:when>
																	<c:otherwise>
																		${item.usernameHidePart}
																	</c:otherwise>
																</c:choose>
															</a></li>
														</c:forEach>
													</ul>
													<input type="hidden" id="userId" name="userId">
													<input type="hidden" name="visitPath"
														   value="<c:choose><c:when test='${empty visitPath }'>${param.visitPath }</c:when><c:otherwise>${visitPath }</c:otherwise></c:choose>" />
												</form>
											</li>
										</ul>
									</div>

								</c:when>
								<c:otherwise>
									当前用户：<b>${sessionScope.Toft_SessionKey_UserData.usernameHidePart }</b>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
			&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="<%=basePath%>user/LogOut.do" style="text-decoration:underline">安全退出</a>
		    </div>
         </div>
         
     </div>
     <%-- <div class="wrap">
         <nav>
         	<%=basePath%>user/ViewIndex.do logo链接地址 
             <!-- <a href="javascript:;" id="logo"><img src="images/logo.png" alt=""></a> -->
             <ul id="main-nav">
             	<li>提示：360浏览器下单功能不稳定，建议使用以下浏览器</li>
             	<li><a href="https://www.baidu.com/link?url=SHiTn2hpyZNpiaXt5aAM1oFA682brkQzLfiiMPBseeoOILhtzS3Q7VF9mU0-ZO3p1aXtQj1NFZQRGZuov_pDMAPUw3AxNVv3qQMRaQ5z4S_&wd=&eqid=e317726f000da3480000000258db119d" target="_blank">Chrome浏览器下载</a></li>
             	<li><a href="https://dldir1.qq.com/invc/tt/QQBrowser_Setup_SEM1.exe" target="_blank">QQ浏览器下载</a></li>
             </ul>
         </nav>
     </div> --%>
</header>