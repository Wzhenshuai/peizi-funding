// JavaScript Document

$(function() {
	
	// 利息试算
	$(".tt-radius .data .lx").hover(function() {
		$(this).find("em:first").html("&#9650;");
	}, function() {
		$(this).find("em:first").html("&#9660;");
	});
	
	// 协议选中复选框
	$("#cbx-pz").click(function(e) {
    	var $this = $(this),
			flag = !!$this.attr("checked"),
			$btn_pz = $("#btn-pz");
		flag ? $this.attr("checked", true) : $this.attr("checked", false);
		if (flag) {
			$this.attr("checked", true);
			$btn_pz.removeClass("btn-gray").addClass("btn-orange");
		} else {
			$this.attr("checked", false);
			$btn_pz.removeClass("btn-orange").addClass("btn-gray");
		}
    });
	
	// i操盘协议
	$("#a-agreement").click(function(e) {
        Common.LightBox.show();
		var $agreement = $("#popup-agreement");
		CU.setObjAbsCenter($agreement);
		$agreement.fadeIn();
		e.stopPropagation();
    });

	// 关闭弹出框
	$("[attr='popup-close']").click(function(e) {
		$(".popup").hide();
		Common.LightBox.hide();
	});
	
	// 问题悬浮
	$(".question").hover(function() {
		var $el = $(this),
			txt = $el.attr("tips");
			html = '<div class="pop-gray-tip"><em class="e1">&#9660;</em><em class="e2">&#9660;</em>' + txt + '</div>';
		$(html).appendTo("body");
		var h = $(".pop-gray-tip").height();
		$(".pop-gray-tip").css({ "left": ($el.offset().left + $el.width() - 115) + "px", "top": (($el.offset().top)-h-33) + "px" });
	}, function() {
		$(".pop-gray-tip").remove();
	});
	
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
        }
    });
});