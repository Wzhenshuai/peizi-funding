package com.icaopan.admin.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.bean.AdminPermissionParams;
import com.icaopan.admin.model.AdminPermission;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.service.PermissionService;
import com.icaopan.util.page.Page;

@Controller
@RequestMapping("/permission")
public class PermissionAction {
	
	@Autowired
	private PermissionService permissionService;
	
	
	/**
	 * @Description 分页查询所有权限
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public Page findPermission(HttpServletRequest request, String aoData, String menuId){
		Page page = new Page(aoData);
		AdminPermissionParams params=new AdminPermissionParams(menuId);
		// 分页查询所有的用户		
		page = permissionService.findAdminPermissionByPage(page,params);
		return page;
	}
	
	
	/**
	 * @Description 保存后台用户信息
	 * @param		adminUser 后台用户类
	 * @return		符合条件的AdminUSer
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult deleteAdminPermission(HttpServletRequest request, int id){
		int i = permissionService.deleteAdminPermissionById(id);
		
		if (i > 0){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		}else{
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
		}
	}
	

	/**
	 * @Description 添加权限
	 * @param		adminPermission 权限
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult addPermission(HttpServletRequest request , AdminPermission adminPermission){

		int i = permissionService.saveAdminPermission(adminPermission);
		
		if (i > 0){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		}else{
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
		}
	}
	
}
