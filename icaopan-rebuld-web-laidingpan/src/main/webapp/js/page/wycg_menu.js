var MA1 = myAccount = {};
//我的账户 ---我的配资分页显示
MA1.updateLoginPass = function (){
	var title = "修改登录密码";
	var text ='<table width="300" cellspacing="0" cellpadding="0" class="zhcc-table-2">';
		text+= '<tr> <td width="50">旧密码:</td><td width="213"><input type="password" id="oldPwd"></td></tr>';
		text+=' <tr><td>新密码:</td><td><input type="password" id="newPwd"></td></tr>';
		text+=' <tr><td>确认密码:</td><td><input type="password" id="repNewPwd"></td></tr>';
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
					if (r.rescode == "success") {
						ShowDialogSuccess("更改成功", function(){location.href = systemConfig.path +"/user/LogOut.do";});
					}else {
						$("#errorDiv").html(r.message);
					}
				}
			});
		},"取消",null);
		d.show();
};


$(function(){
	
	$('#updatePW').click(function(){
		MA1.updateLoginPass();
	});
});