package com.icaopan.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.admin.bean.AdminRoleConts;
import com.icaopan.admin.bean.AdminUserParams;
import com.icaopan.admin.dao.AdminUserMapper;
import com.icaopan.admin.dao.AdminUserRelateRoleMapper;
import com.icaopan.admin.model.AdminRole;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.model.AdminUserRelateRole;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.service.RoleService;
import com.icaopan.util.page.Page;

@Service("adminUserService")
public  class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AdminUserRelateRoleMapper adminUserRelateRoleMapper;
	@Autowired
	private RoleService roleService;
	/**
	 * desc: 用户登录
	 * @author geek
	 */
	public AdminUser selectUserByUsernameAndPassword(AdminUser adminUser) {
        return adminUserMapper.selectUserByUsernameAndPassword(adminUser);
	}
	/**
	 * desc : 分页查询所有后台用户
	 */
	public Page findUserByPage(Page page,AdminUserParams params) {
		page.setAaData(adminUserMapper.selectUserByPage(page,params));
		return page;
	}
	
	/**
	 * desc : 添加用户
	 */
	public int saveAdminUser(AdminUser adminUser) {
		//判断用户名是否存在
		if(isUserNameExist(adminUser.getUserName())){
			throw new RuntimeException("用户名已存在");
		}
		if(adminUser.getStatus()==null){
			adminUser.setStatus("0");
		}
		adminUser.setCreateTime(new Date());
		adminUser.setModifyTime(new Date());
		return adminUserMapper.insert(adminUser);
	}
	
	/**
	 * desc : 获取单独一条的后台用户
	 */
	public AdminUser getAdminUserById(int id) {
		return adminUserMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * desc ： 修改用户
	 */
	public void updateAdminUserById(AdminUser adminUser){
		//非管理员需要判断客户id是否为空
//		if(!isSuperAdmin(adminUser)){
//			Integer customerId=adminUser.getCustomerId();
//			if(customerId==null){
//				throw new RuntimeException("客户通道不能为空");
//			}
//		}
		//密码加密处理
		if(org.apache.commons.lang.StringUtils.isNotEmpty(adminUser.getPassWord())){
			adminUser.setPassWord(com.icaopan.common.util.MD5.MD5Encrypt(adminUser.getPassWord()));
		}
		if(adminUser.getId()==null){
			saveAdminUser(adminUser);
		}else{
			adminUserMapper.updateByPrimaryKey(adminUser);
		}
	}
	
	/**
	 * desc : 删除用户
	 */
	public void deleteAdminUserById(int id) {
		//判断是否是超级管理员
		AdminUser adminUser=getAdminUserById(id);
		if(adminUser!=null){
			String userName=adminUser.getUserName();
			if(StringUtils.equals(userName, "admin")){
				throw new RuntimeException("超级管理员不能删除");
			}
		}
		//删除角色关联表数据
		adminUserRelateRoleMapper.deleteByUserId(id);
		//删除用户数据
		adminUserMapper.deleteByPrimaryKey(id);
	}
	public int saveRoleRelatePermission(int id, int[] role_ids) {
		int counts =0 ;
		adminUserRelateRoleMapper.deleteByUserId(id);
		for (int role_id : role_ids){
			AdminUserRelateRole adminUserRelateRole = new AdminUserRelateRole();
			adminUserRelateRole.setRoleId(String.valueOf(role_id));
			adminUserRelateRole.setUserId(String.valueOf(id));
			int i = adminUserRelateRoleMapper.insertSelective(adminUserRelateRole);
			counts+=i;
		}
		return counts;
	}
	@Override
	public boolean isSuperAdmin(AdminUser adminUser) {
		if(adminUser==null){
			return false;
		}
		if(adminUser.getCustomerId()!=null){
			return false;
		}
//		AdminRole role=roleService.findRolesByUserId(adminUser.getId());
//		if(role==null){
//			return false;
//		}
//		
//		if(!StringUtils.equalsIgnoreCase(role.getRolename(), AdminRoleConts.ROLE_CUSTOMER)){
//			return true;
//		}
		List<AdminRole> roles=roleService.findAllRolesByUserId(adminUser.getId());
		for (AdminRole adminRole : roles) {
			String roleName=adminRole.getRolename();
			if(StringUtils.equalsIgnoreCase(roleName, AdminRoleConts.ROLE_ADMIN)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void updatePassword(AdminUser adminUser) {
		adminUser.setPassWord(com.icaopan.common.util.MD5.MD5Encrypt(adminUser.getPassWord()));
		adminUserMapper.updatePassword(adminUser);
	}
	
	@Override
	public boolean isUserNameExist(String userName) {
		List<AdminUser> list=adminUserMapper.selectUserByUserName(userName);
		if(list.isEmpty()){
			return false;
		}
		return true;
	}
	
	
}
