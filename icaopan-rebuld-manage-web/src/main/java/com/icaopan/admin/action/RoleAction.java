package com.icaopan.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.bean.AdminRoleParams;
import com.icaopan.admin.model.AdminRole;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.service.RoleService;
import com.icaopan.util.page.Page;

@Controller
@RequestMapping("/role")
public class RoleAction {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * @Description 跳转到role页面
	 */
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String permission(){
		return "admin/role/role";
	}
	
	/**
	 * @Description 分页查询
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public Page find(HttpServletRequest request, String aoData, String rolename){
		Page page = new Page(aoData);
		//拼接条件查询
		if(StringUtils.isBlank(rolename)){
			rolename = null;
		}
		AdminRoleParams params=new AdminRoleParams(rolename);
		return  roleService.findAdminRoles(page,params);
	}
	
	/**
	 * @Description 添加角色
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult save(HttpServletRequest request, AdminRole adminRole){
		int i = roleService.saveAdminRole(adminRole);
		
		if (i > 0){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		}else{
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
		}
	}
	
	/**
	 * @Description 删除角色
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult delete(HttpServletRequest request, int id){
		
		try {
			int i = roleService.deleteAdminRoleById(id);
			
			if (i > 0){
				return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
			}else{
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
			}
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	/**
	 * @Description 获取角色
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AdminRole get(HttpServletRequest request, int id){
		return roleService.getAdminRoleById(id);
	}
	
	/**
	 * @Description 修改角色
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult update(HttpServletRequest request, AdminRole adminRole){
		int i = roleService.updateAdminRole(adminRole);
		
		if (i > 0){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		}else{
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
		}
	}
	
	/**
	 * 获取权限的表
	 * @return
	 */
	@RequestMapping(value = "/assign/find", method = RequestMethod.POST)
	@ResponseBody
	public Object getRolePermissionTable(int id , String permissionName){
		if (StringUtils.isBlank(permissionName)){
			permissionName = null;
		}
		List<HashMap<String,Object>> permissions = roleService.selectAllPermissionForDistribute(id,permissionName);
		return permissions;
	}
	
	/**
	 * 保存权限的表
	 * @return
	 */
	@RequestMapping(value = "/assign/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult saveRolePermissionTable(int id , int[] permission_id){
		
		int i = roleService.saveRoleRelatePermission(id, permission_id);
		
		if (i > 0){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		}else{
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
		}
	}
	
}
