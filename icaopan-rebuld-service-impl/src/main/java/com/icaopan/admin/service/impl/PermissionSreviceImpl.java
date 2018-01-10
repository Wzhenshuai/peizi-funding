package com.icaopan.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.admin.bean.AdminPermissionParams;
import com.icaopan.admin.dao.AdminMenuMapper;
import com.icaopan.admin.dao.AdminPermissionMapper;
import com.icaopan.admin.dao.AdminRoleRelatePermissionMapper;
import com.icaopan.admin.model.AdminMenu;
import com.icaopan.admin.model.AdminPermission;
import com.icaopan.admin.service.PermissionService;
import com.icaopan.util.page.Page;

@Service("permissionService")
public class PermissionSreviceImpl implements PermissionService {

	@Autowired
	private AdminMenuMapper adminMenuMapper;
	@Autowired
	private AdminPermissionMapper adminPermissionMapper;
	@Autowired
	private AdminRoleRelatePermissionMapper adminRoleRelatePermissionMapper;
	/**
	 * 查询所有的一级菜单(添加菜单时菜单树)
	 * 
	 * @author lifaxin
	 */
	public List<AdminMenu> selectAllParentMenu(int userId) {
		return adminMenuMapper.selectAllParentMenu(userId);
	}

	/**
	 * 通过FID查询所有的二级菜单（添加菜单时的菜单树）
	 * 
	 * @author lifaxin
	 */
	public List<AdminMenu> selectAllSubMenu(int userId, int menuId) {
		return adminMenuMapper.selectAllSubMenu(userId, menuId);
	}
	
	
	
	public List<AdminMenu> selectAllParentMenuForShow(int userId){
		return adminMenuMapper.selectAllParentMenuForShow(userId);
	}
	
	public List<AdminMenu> selectAllSubMenuForShow(int userId, int menuId){
		return adminMenuMapper.selectAllSubMenuForShow(userId, menuId);
	}

	/**
	 * 查询所有权限
	 * 
	 * @author lifaxin
	 */
	public Page findAdminPermissionByPage(Page page,AdminPermissionParams params) {
		page.setAaData(adminPermissionMapper.findAdminPermissionByPage(page,params));
		return page;
	}
	
	
	/**
	 * 删除权限
	 * 
	 * @author lifaxin
	 */
	public int deleteAdminPermissionById(int id) {
		return adminPermissionMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 查询所有的菜单 
	 * 
	 * @author lifaxin
	 */
	public List<AdminMenu> selectAllMenu() {
		return adminMenuMapper.selectAllMenu();
	}
	
	/**
	 * 添加权限
	 * 
	 * @author lifaxin
	 */
	public int saveAdminPermission(AdminPermission adminPermission) {
		return adminPermissionMapper.insertSelective(adminPermission);
	}
	
	/**
	 * 添加菜单
	 * 
	 * @author lifaxin
	 */
	public void saveAdminMenu(AdminMenu adminMenu){
		if(adminMenu.getId()!=null){
			adminMenuMapper.updateByPrimaryKeySelective(adminMenu);
		}else{
			adminMenuMapper.insert(adminMenu);
		}
	}
	
	/**
	 * 查询菜单
	 * 
	 * @author lifaxin
	 */
	public AdminMenu getAdminMenu(int menuId){
		return adminMenuMapper.selectByPrimaryKey(menuId);
	}
	
	/**
	 * 删除菜单
	 * 
	 * @author lifaxin
	 */
	public void deleteAdminMenu(int userId,int menuId){
		//如果是父节点，且有子节点则不允许删除此菜单
		AdminMenu menu=adminMenuMapper.selectByPrimaryKey(menuId);
		if(menu==null){
			throw new RuntimeException("菜单不存在");
		}
		if(StringUtils.equals(menu.getMenuClazz(), "0")){
			//查找子节点
			List<AdminMenu> adminSubMenus = selectAllSubMenu(userId,
					menu.getId());
			if(adminSubMenus.size()>0){
				throw new RuntimeException("请先删除子节点");
			}
		}
		//删除权限关联数据
		adminRoleRelatePermissionMapper.deleteByMenuId(menuId);
		//删除权限数据
		adminPermissionMapper.deleteByMenuId(menuId);
		//删除菜单
		adminMenuMapper.deleteByPrimaryKey(menuId);
	}

	/**
	 * 查询当前用户所有权限
	 */
	public List<AdminPermission> selectPermissionsByUser(Integer id) {
		return adminPermissionMapper.selectPermissionsByUser(id);
	}
	
	
}
