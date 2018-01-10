$(function(){
	resizeRoot();
	handleResize();
	
	function handleResize() {
		$(window).resize(function() {
			resizeRoot();
		});
	}
	function resizeRoot() {
		var fs = $(window).width()/320*20;
		if(fs >= 40) {
			fs = 40;
		}
		$("html").css("font-size", fs+"px");
	}
	function is_weixin(){
		var ua = navigator.userAgent.toLowerCase();
		if(ua.match(/MicroMessenger/i)=="micromessenger") {
			return true;
	 	} else {
			return false;
		}
	}
	
	$('.download').click(function(){
		if(is_weixin()){
			$('#floatLayer').show();
		}else{
			href=$(this).attr("hf");
		    location.href=href;
		}
	})
})

    