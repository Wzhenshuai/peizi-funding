(function($){
	$.fn.extend({
		'tabSwitched':function(fn){
			var _this = $(this);//当前插件
			$(this).find('.tab_plugin_title').click(function(e){
				var $tar=$(e.target);
				if($tar.get(0).tagName.toLowerCase()=='li' && $tar.get(0).className.indexOf('active') < 0){//后半句判断是防止重复点击
					$tar.siblings().removeClass('active');
					$tar.addClass('active');
					
					var _index=$tar.index();
					var P=$(this).closest('.tab_plugin');//当前被点击元素的父元素，为了匹配对应的内容项而不影响其他插件对应内容
					if(_index > -1){
						P.find('.tab_plugin_list').hide();
						P.find('.tab_plugin_list:eq('+_index+')').show();
					}
				}
				if(fn){
					fn();
				}
			});
		},
		'scrollPlugin':function(defaults){ //只能向上和向左滚动，另外两个方向有待扩展
			var options = {//默认值
					'animateTime':500,
					'delay':2000,
					'dir':'top'
				};
			var opts = $.extend(options, defaults), dir=opts.dir;
			var _this = $(this);//当前插件
			var scrollStep;
			if(dir=='top' || dir=='bottom'){
				scrollStep = _this.find('li:first').outerHeight();
				var timer = setInterval(function(){
					clearInterval(timer);
					_this.animate({
						top: '-' + scrollStep+'px'
					}, opts.animateTime, function(){
						_this.find('li:first').appendTo(this);
						_this.css(dir,0).scrollPlugin(defaults);
					});
				}, opts.delay);
			}else{
				scrollStep = _this.find('li:first').outerWidth();
				var timer = setInterval(function(){
					clearInterval(timer);
					_this.animate({
						left: '-' + scrollStep+'px'
					}, opts.animateTime, function(){
						_this.find('li:first').appendTo(this);
						_this.css(dir,0).scrollPlugin(defaults);
					});
				}, opts.delay);
			}
		},
		'pageBar':function(pageObj){
			var curPage=parseInt(pageObj.curPage);
			var totalPage=parseInt(pageObj.totalPage);
			if(curPage>0 && totalPage>1){
				var str='<li class="prevPage'+((curPage==1) ? ' grayPage' : '')+'">上一页</li>';
				if(totalPage<11){//无省略号的情况，页码全部显示
					for(var i=1;i<totalPage+1;i++){
						str += (((curPage==i) ? '<li class="currentPage">' : '<li>')+i+'</li>');
					};
				}else{//带省略号的情况
					if(curPage<8){//前面连续，后面带一个省略号
						for(var i=1;i<9;i++){
							str += (((curPage==i) ? '<li class="currentPage">' : '<li>')+i+'</li>');
						}
						str+='<span class="omit">...</span><li>'+totalPage+'</li>';
					}else if(totalPage-curPage>6){//两端加省略号
						str+='<li>1</li><span class="omit">...</span>';
							for(var i=curPage-3;i<curPage+4;i++){
								str += (((curPage==i) ? '<li class="currentPage">' : '<li>')+i+'</li>');
							}
							str+='<span class="omit">...</span><li>'+totalPage+'</li>';
					}
					else{
						str+='<li>1</li><span class="omit">...</span>';
						for(var i=totalPage-7;i<totalPage+1;i++){
							str += (((curPage==i) ? '<li class="currentPage">' : '<li>')+i+'</li>');
						}
					}
				}
				str+='<li class="nextPage'+((curPage==totalPage) ? ' grayPage' : '')+'">下一页</li>';
				$(this).html(str);
			}
		}
	});
})(jQuery);