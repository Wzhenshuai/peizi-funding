// JavaScript Document

var Common = {};

Common.LightBox = {
    element: null,
    init: function () {
        var html = '<div id="lightbox" style="filter:alpha(opacity=50);"></div>';
        this.element = $(html).appendTo(document.body);
        this.count = 0;
    },
    show: function () {
        if (!this.element) {
            this.init();
        };
        this.element.fadeIn();
        this.count++;
    },
    hide: function () {
        this.count--;
        if (this.count <= 0)
            this.element.fadeOut();
    }
};

CU = Common.Util = {
	// 获取对象相对于窗口绝对居中坐标
    getAbsCenterAxis: function (el) {
        var bd = $(document.body);
        return {
            left: (bd.width() - el.width()) / 2,
            top: (bd.outerHeight() - el.outerHeight()) / 2
        };
    },
	
	// 设置对象绝对居中
    setObjAbsCenter: function (el) {
        var poxy = this.getAbsCenterAxis(el);
        el.css({ "left": poxy.left, "top": poxy.top });
		//窗体改变大小时自动居中
        $(window).on("resize", function () {
            var axis = CU.getAbsCenterAxis(el);
            el.css({ left: axis.left + "px", top: axis.top + "px" });
        });
    },
    
    //获取URL参数
    getUrlParam: function (name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r!=null)
			return unescape(r[2]);
		return null;
	} 
};

var JPlaceHolder = {
    //检测
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //修复
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.wrap($('<div class="auto"></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
            var pos = self.position(),
				h = self.outerHeight(true),
				paddingleft = self.css('padding-left');
            var holder = $('<span></span>')
				.text(txt)
				.css({position:'absolute', left:pos.left, top:pos.top, height:h, lienHeight:h, paddingLeft:paddingleft, color:'#aaa'})
				.appendTo(self.parent());
            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if(!self.val()){
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//执行
jQuery(function(){
    JPlaceHolder.init();
});

