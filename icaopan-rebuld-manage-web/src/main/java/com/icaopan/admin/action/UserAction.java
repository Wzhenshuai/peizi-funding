package com.icaopan.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.icaopan.admin.util.LogUtils;
import com.icaopan.admin.util.ServletUtil;
import com.icaopan.common.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.bean.AdminUserParams;
import com.icaopan.admin.model.AdminMenu;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.PermissionService;
import com.icaopan.admin.service.RoleService;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.common.CommonAction;
import com.icaopan.common.util.MD5;
import com.icaopan.customer.model.Customer;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.util.page.Page;

@Controller
@RequestMapping("/user")
public class UserAction extends CommonAction {
	
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CustomerService customerService;
	
	/**
	 * @Description 用于用户登录
	 * @param username : 用户名
	 * 		  password ：密码
	 * @return 登录信息
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, @Param("username") String username,
			@Param("password") String password , @Param("remember") boolean remember) {
		
		// 初始化创建用户密码的token，用于验证
		UsernamePasswordToken token = new UsernamePasswordToken(username, MD5.MD5Encrypt(password));
		token.setRememberMe(remember);

		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();

		// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "用户名或密码不正确", null);
		} catch (IncorrectCredentialsException ice) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "密码错误，请重试", null);
		} catch (LockedAccountException lae) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "账户已经锁定，请联系管理员", null);
		} catch (ExcessiveAttemptsException eae) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "用户名或密码错误次数过多", null);
		} catch (AuthenticationException ae) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "用户名或密码不正确", null);
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			// 登录成功后将token清理掉			
			token.clear();
			// 将登陆的用户 放到session中
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "用户登录成功", null);
		} else {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "登录失败，请重新登陆", null);
		}

	}
	
	/**
	 * @Description GET方式退出用户
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		SecurityUtils.getSubject().logout();
		throw new UnauthenticatedException();
	}
	
	
	/**
	 * @Description GET方式跳转到HOME页
	 * @return HOME页地址
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		//判断是否已经登录
		AdminUser user=LoginRealm.getCurrentUser();
		if(user==null){
			throw new UnauthenticatedException();
		}
		// 将同一父ID的菜单分到同一个组中		
		List<HashMap<String,Object>> groupList = new ArrayList<HashMap<String,Object>>();
		
		// 获取当前用户的所有的父级的菜单		
		List<AdminMenu> adminMenus = permissionService.selectAllParentMenuForShow(LoginRealm.getCurrentUser().getId());
		
		// 菜单分组		
		for (AdminMenu adminMenu : adminMenus){
		
			// 添加父菜单
			HashMap<String,Object> group = new HashMap<String,Object>();
			group.put("parentMenu", adminMenu);
			
			// 获取当前菜单的所有子菜单			
			List<AdminMenu> adminSubMenus = permissionService.selectAllSubMenuForShow(LoginRealm.getCurrentUser().getId(), adminMenu.getId());
			group.put("subMenu", adminSubMenus);
			
			// 将所的菜单组装配到组列表中			
			groupList.add(group);
		}
		
		//  将分装好的菜单列表 传到前台		
		request.setAttribute("adminMenus", groupList);
		if(adminUserService.isSuperAdmin(user)){
			request.setAttribute("showUserText", "Lever2");
		}else{
			Integer cId=user.getCustomerId();
			String name="";
			if(cId!=null){
				Customer ct=customerService.getCustomerById(cId);
				if(ct!=null){
					name=ct.getName();
					if(StringUtils.isNotEmpty(name)){
						if(name.length()>10){
							name=name.substring(0,10);
						}
					}
				}
			}
			if(!StringUtils.isEmpty(name)){
				request.setAttribute("showUserText", name);
			}else{
				request.setAttribute("showUserText", "Lever2");
			}
			
		}
		return "admin/home/home";
	}
	
	/**
	 * @Description 跳转到User页面
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String adminUser(){
		return "admin/user/user";
	}
	
	/**
	 * @Description 分页并条件查询所有的后台用户
	 * @param		page jquery-dataTable返回的分页参数 
	 * @return		符合条件的AdminUSer
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public Page findAdminUser(HttpServletRequest request,@Param("aoData") String aoData,@Param("realname") String realname, @Param("phone") String phone,
							  @Param("username") String username,@Param("email") String email, @Param("customerId") Integer customerId,@Param("status") String status){
		Page page = new Page(aoData);
		// 添加 条件查询
		if(StringUtils.isBlank(realname)){
			realname = null;
		}
		if(StringUtils.isBlank(phone)){
			phone = null;
		}
		AdminUser user=LoginRealm.getCurrentUser();
		AdminUserParams params=new AdminUserParams();
		if(!adminUserService.isSuperAdmin(user)){
			params.setCustomerId(user.getCustomerId());
		}else{
			params.setCustomerId(customerId);
		}
		params.setUser(LoginRealm.getCurrentUser());
		params.setUserName(StringUtils.isEmpty(username) ? null :"%" + username + "%");
		params.setRealName(StringUtils.isEmpty(realname) ? null : "%" + realname + "%");
		params.setPhone(StringUtils.isEmpty(phone) ? null : "%" + phone + "%");
		params.setEmail(StringUtils.isEmpty(email) ? null :"%"+email+"%");
		params.setStatus(StringUtils.isEmpty(status) ? null :status);
		// 分页查询所有的用户		
		page = adminUserService.findUserByPage(page,params);
		return page;
	}

	/**
	 * 跳转修改密码页面
	 * */
	@RequestMapping(value = "/forwardToRestPWD")
	@RequiresUser
	public String forwardToRestPWD(Model model){
		AdminUser adminUser = LoginRealm.getCurrentUser();
		if (adminUser.getPassWord()!=null){
			adminUser.setPassWord(null);
		}
		model.addAttribute("adminUser",adminUser);
		return "admin/user/editUserPwd";
	}
	
	
	/**
	 *重置密码
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public ResponseResult resetPwdAdminUser(HttpServletRequest request,AdminUser adminUser){
		try {
			AdminUser currentUser = LoginRealm.getCurrentUser();
			if (adminUserService.isSuperAdmin(currentUser)){
				adminUserService.updatePassword(adminUser);
				try {
					LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[用户详情]-[重置密码]");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "密码修改成功", null);
			}else {
				adminUserService.updatePassword(adminUser);
				SecurityUtils.getSubject().logout();
				return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "密码修改成功,请重新登陆", null);
			}
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	/**
	 * @Description 获取指定ID的用户类
	 * @param		id 需要查询的ID
	 * @return		符合条件的AdminUSer
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AdminUser getAdminUser(HttpServletRequest request,int id){
		// 获取数据
		AdminUser adminUser = adminUserService.getAdminUserById(id);
		return adminUser;
	}
	
	
	@RequestMapping(value = "/form")
	public String formAdminUser(HttpServletRequest request,Integer id){
		if(id!=null){
			AdminUser adminUser=adminUserService.getAdminUserById(id);
			request.setAttribute("adminUser", adminUser);
		}
		return "admin/user/userForm";
	}
	
	/**
	 * @Description 保存后台用户信息
	 * @param		adminUser 后台用户类
	 * @return		符合条件的AdminUSer
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult updateAdminUser(HttpServletRequest request, AdminUser adminUser){
		try {
			adminUserService.updateAdminUserById(adminUser);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	/**
	 * @Description 保存后台用户信息
	 * @param		adminUser 后台用户类
	 * @return		符合条件的AdminUSer
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult deleteAdminUser(HttpServletRequest request, int id){
		try {
			adminUserService.deleteAdminUserById(id);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	/**
	 * @Description 获取所有角色信息
	 */
	@RequestMapping(value = "/distribute/get", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public List<HashMap<String,Object>> distributeRole(HttpServletRequest request, int id){
		return roleService.findAdminRolesForUser(id);
	}
	
	/**
	 * @Description 分配角色
	 * @param		参数说明
	 * @return		返回值
	 * @exception 	异常描述
	 */
	@RequestMapping(value = "/distribute/save", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult distributeSave (HttpServletRequest request,int id, int []role_ids){
		int i = 0 ;
		if( role_ids != null && id != 0){
			 i =  adminUserService.saveRoleRelatePermission( id, role_ids);
		}
		if (i > 0){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		}else{
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "发生未知错误", null);
		}
	}
	
	/**
	 * @Description 跳转到User页面
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(AdminUser user,Model model){
		model.addAttribute("user", user);
		return "admin/test";
	}
}
