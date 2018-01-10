function ajaxReuqestSynchronize(url,params,callback){
	$.ajax({
		   type: "POST",
		   url: url,
		   cache: false,
		   data: params,
		   dataType:"json",
		   async:false,
		   success: function(msg){
			   if(msg.hasError){
				  // alert("服务器异常");
			   }else if(msg.result=="login"){
				   openLoginDia(url);
			   }else{
				   callback(msg);
			   }
		   }
		 });
}
function ajaxReuqestAsync(url,params,callback){
	$.ajax({
		   type: "POST",
		   url: url,
		   cache: false,
		   data: params,
		   dataType:"json",
		   async:true,
		   success: function(msg){
			   if(msg.hasError){
				  // alert("服务器异常");
			   }else if(msg.result=="login"){
				   openLoginDia(url);
			   }else{
				   callback(msg);
			   }
		   }
		 });
}
function ajaxReuqestNotShowBack(url,params,callback){
	$.ajax({
		   type: "POST",
		   url: url,
		   cache: false,
		   data: params,
		   dataType:"json",
		   async:false,
		   success: function(msg){
			   if(msg.hasError){
				  // alert("服务器异常");
			   }else{
				   callback(msg);
			   }
		   }
		 });
}
function ajaxDivLoad(divIdOrClass,url,param,callback){
	showWaitBack();
	if(!param){
		param={};
	}
	param.date=new Date().getTime();
	if($("#"+divIdOrClass).length>0){
		$("#"+divIdOrClass).load(url,param,function(){
			if(callback){
				callback();
			}
			 closeWaitBack();
		});
	}
	else{
		$("."+divIdOrClass).load(url,param,function(){
			if(callback){
				callback();
			}
			 closeWaitBack();
		});
	}
}
function ajaxDivLoadNotShowBack(divIdOrClass,url,param,callback){
	if(!param){
		param={};
	}
	param.date=new Date().getTime();
	if($("#"+divIdOrClass).length>0){
		$("#"+divIdOrClass).load(url,param,function(){
			if(callback){
				callback();
			}
			 closeWaitBack();
		});
	}
	else{
		$("."+divIdOrClass).load(url,param,function(){
			if(callback){
				callback();
			}
		});
	}
}

function openLoginDia(url){
	location.href=systemConfig.path+"/user/login.jsp?visitPath="+escape(url), null, null;
	/*var loginDiv="<div class='loginDialogDiv'></div>";
	$("body").append(loginDiv);
	ajaxDivLoadNotShowBack("loginDialogDiv", systemConfig.path+"/LoginForDialog.jsp?visitPath="+escape(url), null, null);*/
}
function closeLoginDia(){
	$("div.loginDialogDiv").remove();
}
function checkVisitOpenApiLogin(r){
	if(r.info.rescode=="login"){
		location.href=systemConfig.path+"/user/login.jsp";
	}
}