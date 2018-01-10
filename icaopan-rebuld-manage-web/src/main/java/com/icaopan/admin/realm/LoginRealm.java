package com.icaopan.admin.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.icaopan.admin.model.AdminMenu;
import com.icaopan.admin.model.AdminPermission;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.Constants;
import com.icaopan.admin.service.PermissionService;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.util.LoggerUtil;


public class LoginRealm extends AuthorizingRealm {

	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private PermissionService permissionService;
	

	/**
	 * @Description 对用户进行授权
	 * @param principals: 用户信息（继承自Shrio）
	 * @return 授权之后的信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 初始化权限类		
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		// 判断当前用户是否认证		
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			throw new UnauthenticatedException();
		}
		// 获取用户信息		
		AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_KEY);
		LoggerUtil.authLog(adminUser, "用户开始授权...");
		// 获取权限，并进行授权		
		List<String> permissionList = new ArrayList<String>();		
		List<AdminPermission> permissions= permissionService.selectPermissionsByUser(adminUser.getId());
		// 拼接授权字符串
		for( AdminPermission permission : permissions){
			AdminMenu menu = permissionService.getAdminMenu(Integer.parseInt(permission.getMenuId()));
			if(menu==null){
				continue;
			}
			permissionList.add(menu.getMenuCode()+":"+permission.getPermissionCode());
		}
		LoggerUtil.authLog(adminUser, "授权成功 ： " + permissionList.toString());
		simpleAuthorInfo.addStringPermissions(permissionList);
		// 授权成功，将授权后的信息返回		
		return simpleAuthorInfo;
	}

	
	/**
	 * @Description 用于用户登录登录验证
	 * @param authcToken: 用户登录信息
	 * @return 登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		// 获取基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		AdminUser queryParam = new AdminUser();
		queryParam.setUserName(token.getUsername());
		queryParam.setPassWord(String.valueOf(token.getPassword()));
		LoggerUtil.authLog(queryParam, "用户开始登录验证...");
		AdminUser adminUser = new AdminUser();
		// 判断当前用户是否登录成功
		try {
			adminUser = adminUserService.selectUserByUsernameAndPassword(queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (adminUser != null) {
			if(StringUtils.equals(adminUser.getStatus(), "1")){
				throw new LockedAccountException("账户已经锁定，请联系管理员");
			}
			LoggerUtil.authLog(adminUser, "用户开始登录验证成功！");
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(adminUser.getUserName(),
					token.getPassword(), adminUser.getRealName());
			this.setSession(Constants.SESSION_KEY, adminUser);
			return authcInfo;
		}
		LoggerUtil.authLog(queryParam, "用户开始登录验证失败！");
		// 没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
		return null;
	}

	/**
	 * 
	 * @Description 将信息保存在Session中
	 * @param key-value键值对
	 * @return 空
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	/**
	 * @Description 清空session
	 * @param
	 * @return null
	 * */
	public static void removeSession(){
		Subject currentUser = SecurityUtils.getSubject();
		if (null!=currentUser){
			Session session = currentUser.getSession();
			if (null!=session){
				session.removeAttribute(Constants.SESSION_KEY);
			}
		}
	}

	/**
	 * @Description 获取当前登录的用户
	 * @return 当前登录的用户
	 * @exception 若未登录抛出UnauthenticatedException
	 */
	public static AdminUser getCurrentUser() {
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			throw new UnauthenticatedException();
		}
		AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_KEY);
		return adminUser;
	}

}
