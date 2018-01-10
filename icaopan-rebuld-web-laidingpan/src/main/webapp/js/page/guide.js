$(function() {
	//导航焦点态
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
				$(".gs1").click(function(e) {
					obj.goto(1);
                });
				$(".gs2").click(function(e) {
					obj.goto(4);
                });
				$(".gs3").click(function(e) {
					obj.goto(7);
                });
				$(".gs4").click(function(e) {
					obj.goto(8);
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
				};
				switch(currentSlide) {
					case 1:
					case 3:
						$(".gs1").addClass("current").siblings("li").removeClass("current");
						break ;
					case 4:
					case 6:
						$(".gs2").addClass("current").siblings("li").removeClass("current");
						break ;
					case 7:
						$(".gs3").addClass("current").siblings("li").removeClass("current");
						break ;
					case 8:
						$(".gs4").addClass("current").siblings("li").removeClass("current");
						break ;
				};
			}
		}
    });
});