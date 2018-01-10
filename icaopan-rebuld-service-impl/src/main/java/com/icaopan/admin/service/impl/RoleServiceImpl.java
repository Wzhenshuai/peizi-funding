package com.icaopan.admin.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.admin.bean.AdminRoleParams;
import com.icaopan.admin.dao.AdminPermissionMapper;
import com.icaopan.admin.dao.AdminRoleMapper;
import com.icaopan.admin.dao.AdminRoleRelatePermissionMapper;
import com.icaopan.admin.model.AdminRole;
import com.icaopan.admin.model.AdminRoleRelatePermission;
import com.icaopan.admin.service.RoleService;
import com.icaopan.util.page.Page;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private AdminRoleMapper adminRoleMapper;
	@Autowired
	private AdminPermissionMapper adminPermissionMapper;
	@Autowired
	private AdminRoleRelatePermissionMapper adminRoleRelatePermissionMapper;
	
	/**
	 * 保存角色
	 */
	public int saveAdminRole(AdminRole adminRole) {
		return adminRoleMapper.insertSelective(adminRole);
	}

	/**
	 * 删除角色
	 */
	public  int deleteAdminRoleById(int id) {
		//判断是否是超级管理员
		AdminRole role=adminRoleMapper.selectByPrimaryKey(id);
		if(role!=null){
			if(StringUtils.equals("管理员", role.getRolename())){
				throw new RuntimeException("管理员角色不能删除");
			}
		}
		adminRoleRelatePermissionMapper.deleteByRoleId(id);
		return adminRoleMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改角色
	 */
	public int updateAdminRole(AdminRole adminRole) {
		return adminRoleMapper.updateByPrimaryKeySelective(adminRole);
	}

	/**
	 * 获取一条角色
	 */
	public AdminRole getAdminRoleById(int id) {
		return adminRoleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 分页查询角色
	 */
	public Page findAdminRoles(Page page,AdminRoleParams params) {
		page.setAaData(adminRoleMapper.findAdminRoles(page,params));;
		return page;
	}
	

	/**
	 * 分配权限
	 */
	public int saveRoleRelatePermission(int id, int[] permission_ids) {
		int counts =0 ;
		adminRoleRelatePermissionMapper.deleteByRoleId(id);
		for (int permission_id : permission_ids){
			AdminRoleRelatePermission adminRoleRelatePermission = new AdminRoleRelatePermission();
			adminRoleRelatePermission.setRoleId(String.valueOf(id));
			adminRoleRelatePermission.setPermissionId(String.valueOf(permission_id));
			int i = adminRoleRelatePermissionMapper.insertSelective(adminRoleRelatePermission);
			counts+=i;
		}
		return counts;
	}

	/**
	 * 查询当前用户所有角色
	 */
	public List<HashMap<String, Object>> findAdminRolesForUser(int id) {
		return adminRoleMapper.findAdminRolesForUser(id);
	}

	/**
	 * 查询当前角色的所有权限（包含已经分配 和 未分配的权限）
	 */
	public List<HashMap<String, Object>> selectAllPermissionForDistribute(int id,String permissionName) {
		if (permissionName != null){
			permissionName = "%"+permissionName+"%";
		}
		return adminRoleRelatePermissionMapper.selectAllPermissionForDistribute(id, permissionName);
	}

	@Override
	public AdminRole findRolesByUserId(Integer userId) {
		return adminRoleMapper.findRolesByUserId(userId);
	}
	
	@Override
	public List<AdminRole> findAllRolesByUserId(Integer userId) {
		return adminRoleMapper.findAllRolesByUserId(userId);
	}

}
