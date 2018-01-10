function Page(containerId,requestUrl,params,sumPages,pageNo,pageSize,callback){
	this.init=function(){
		var _this=this;
		if(params==null){
			params={};
		}
		if(pageNo>=sumPages){
			$("#nextPage").addClass("disabled");
			$("#nextPage").click(function(){});
		}else{
			$("#nextPage").click(function() {
				_this.nextPage();
			});
		}
		if(pageNo<=1){
			$("#frontPage").addClass("disabled");
			$("#frontPage").click(function(){});
		}else{
			$("#frontPage").click(function() {
				_this.upPage();
			});
		}
		// 页码样式
		$(".pageA").each(function(index, domEle) {
			if ($(domEle).text() == pageNo) {
				$(domEle).addClass("selected");
				return false;
			} else {
				$(domEle).removeClass("selected");
			}
		});
		//绑定点击事件
		$(".pageA").click(function(){
			pageNo=parseInt($(this).html());
			params.pageNo=pageNo;
			ajaxDivLoad(containerId, requestUrl, params, function(){
				if(callback){
					callback();
				}
			});
		});
	};
	this.nextPage=function(){
		pageNo=parseInt(pageNo)+1;
		params.pageNo=pageNo;
		ajaxDivLoad(containerId, requestUrl, params, function(){
			if(callback){
				callback();
			}
		});
	};
	this.upPage=function(){
		pageNo=parseInt(pageNo)-1;
		params.pageNo=pageNo;
		ajaxDivLoad(containerId, requestUrl, params, function(){
			if(callback){
				callback();
			}
		});
	};
}