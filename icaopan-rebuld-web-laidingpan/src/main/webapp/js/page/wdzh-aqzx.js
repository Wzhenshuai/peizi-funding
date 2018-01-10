var MA = myAccount = {};
MA.bindEmail = function(){
	var title = "绑定邮箱";
	var text ='<table width="250" cellspacing="0" cellpadding="0" class="zhcc-table-2">';
		text+= '<tr><td width="50">绑定邮箱</td><td width="200"><input type="text" id="email" /></td></tr>';
		text+='</table>';
		text+='<div class="error txt-c text-orange" id="errorDiv"></div>';
		var d=new UpdateDialog(title,text,"确定",function(){
			var email = $("#email").val();
			if ($.trim(email)=='') {
				$("#errorDiv").html("邮箱不能为空");
				return;
			}
			if (!valid.isEmail(email)) {
				$("#errorDiv").html("邮箱格式不正确");
				return;
			}
			$.ajax({
				url:systemConfig.path + "/user/SendVerifyMail.ajax",
				//async:false,
				data:{"email" : email},
				type:'post',
				success:function(r) {
					if(r.resCode){
						$("div.pop").remove();
						ShowDialogSuccess('激活链接已经发送到您的'+email+'邮箱，请登陆邮箱后，点击链接完成邮箱绑定！',null);
					}else if (r.resCode == "email_empty") {
						$('.errorDiv').html('邮箱为空！');
					}
				}
			});
		},"取消",null);
		d.show();
};
//我的账户 ---我的配资分页显示
MA.updateLoginPass = function (){
	var title = "修改登录密码";
	var text ='<table width="300" cellspacing="0" cellpadding="0" class="zhcc-table-2">';
		text+= '<tr> <td width="50">旧密码</td><td width="213"><input type="password" id="oldPwd"></td></tr>';
		text+=' <tr><td>新密码</td><td><input type="password" id="newPwd"></td></tr>';
		text+=' <tr><td>确认密码</td><td><input type="password" id="repNewPwd"></td></tr>';
		text+='</table>';
		text+='<div class="error txt-c text-orange" id="errorDiv"></div>';
		var d=new UpdateDialog(title,text,"确定",function(){
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			var repNewPwd=$("#repNewPwd").val();
			var reg =/^[a-zA-Z0-9]{6,16}$/;
			if ($.trim(oldPwd) == "") {
				$("#errorDiv").html("旧密码不能为空");
				return;
			}
			if ($.trim(newPwd) == "") {
				$("#errorDiv").html("新密码不能为空");
				return;
			}
			if(newPwd.length<6 || newPwd.length>16){
				$("#errorDiv").html("新密码只能由6-16位数字和字母混合组成");
				return false;
			}
			if (!reg.test(newPwd)) {
				$("#errorDiv").text("新密码只能由6-16数字和字母混合组成");
				return;
			}
			if ($.trim(repNewPwd) == "") {
				$("#errorDiv").html("确认密码不能为空");
				return;
			}
			if(repNewPwd!=newPwd){
				$("#errorDiv").text("两次新密码输入不一致");
				return;
			}
			$.ajax({
				url:systemConfig.path + "/user/ChangePwd.ajax",
				data:{"oldPwd" : oldPwd, "newPwd" : newPwd },
				dataType:'json',
				success:function(r) {
					if (r.resCode == "login") {
						location.href = systemConfig.path + "/user/login.jsp";
					}else if (r.resCode == "oldPwdError") {
						$("#errorDiv").html("旧密码输入不正确");
						return;
					}else if(r.resCode == "newPwdError"){
						$("#errorDiv").html("修改密码不能和原密码相同");
						return;
					}else if (r.resCode == "success") {
						d.closeDia();
						ShowDialogSuccess("更改成功", function(){location.href = systemConfig.path +"/user/LogOut.do";});
					}
				}
			});
		},"取消",null);
		d.show();
};


$(function(){
	$('#aside-nav li:eq(7)').addClass('active');
	//导航焦点态
	$('#main-nav li:eq(3)').addClass('current');
	
	/*//用户是否已绑定邮箱
	$.ajax({
		url:systemConfig.path + "/user/IsBindMail.ajax",
		success:function(r){
			if(r.resCode){
				$('#email-verify').addClass('col3-right');
			}else{
				$('#email-verify').addClass('col3-wrong');
			}
		}
	});*/
});