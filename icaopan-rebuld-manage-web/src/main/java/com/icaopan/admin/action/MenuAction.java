package com.icaopan.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.model.AdminMenu;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.PermissionService;
@Controller
@RequestMapping("/menu")
public class MenuAction {

	protected org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PermissionService permissionService;

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menus() {
		return "admin/menu/menu";
	}

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/menu/tree", method = RequestMethod.GET)
	@ResponseBody
	@RequiresUser
	public Object menu_tree() {

		// 将同一父ID的菜单分到同一个组中
		List<HashMap<String, Object>> groupList = new ArrayList<HashMap<String, Object>>();

		// 获取当前用户的所有的父级的菜单
		List<AdminMenu> adminMenus = permissionService.selectAllParentMenu(LoginRealm.getCurrentUser().getId());

		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("id", "0");
		root.put("parent", "#");
		root.put("text", "易财富后台管理系统");
		groupList.add(root);
		// 菜单分组
		for (AdminMenu adminMenu : adminMenus) {
			// 添加父菜单
			HashMap<String, Object> group = new HashMap<String, Object>();
			HashMap<String, Object> attr = new HashMap<String, Object>();
			group.put("id", adminMenu.getId());
			group.put("parent", adminMenu.getMenuParent());
			group.put("text", adminMenu.getMenuName());
			group.put("menuCode", adminMenu.getMenuCode());
			group.put("menuName", adminMenu.getMenuName());
			group.put("menuUrl", adminMenu.getMenuUrl());
			attr.put("order", adminMenu.getMenuOrder());
			attr.put("clazz", adminMenu.getMenuClazz());
			group.put("a_attr", attr);
			// 将所的菜单组装配到组列表中
			groupList.add(group);
			// 获取当前菜单的所有子菜单
			List<AdminMenu> adminSubMenus = permissionService.selectAllSubMenu(LoginRealm.getCurrentUser().getId(),
					adminMenu.getId());
			for (AdminMenu amenu : adminSubMenus) {
				HashMap<String, Object> group1 = new HashMap<String, Object>();
				HashMap<String, Object> attr1 = new HashMap<String, Object>();
				group1.put("id", amenu.getId());
				group1.put("parent", amenu.getMenuParent());
				group1.put("text", amenu.getMenuName());
				group1.put("menuCode", amenu.getMenuCode());
				group1.put("menuName", amenu.getMenuName());
				group1.put("menuUrl", amenu.getMenuUrl());
				attr1.put("order", amenu.getMenuOrder());
				attr1.put("clazz", amenu.getMenuClazz());
				group1.put("a_attr", attr1);
				groupList.add(group1);
			}
		}
		return groupList;
	}

	/**
	 * 添加菜单
	 * 
	 * @param request
	 * @param adminMenu
	 * @param menuId
	 * @param menuWay
	 * @param menuHiddenE
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult addMenu(HttpServletRequest request, AdminMenu adminMenu, String menuId, String menuWay,
			boolean menuHiddenE) {
		AdminMenu adminMenuOld = permissionService.getAdminMenu(Integer.parseInt(menuId));

		if (menuHiddenE) {
			adminMenu.setMenuHidden(1);
		} else {
			adminMenu.setMenuHidden(0);
		}

		if ("same".equals(menuWay)) {
			adminMenu.setMenuParent(adminMenuOld.getMenuParent());
			adminMenu.setMenuClazz(adminMenuOld.getMenuClazz());
		} else if ("next".equals(menuWay)) {
			// 判断当前是否已经是2级菜单
			if ("1".equals(adminMenuOld.getMenuClazz())) {
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "暂时只支持两级菜单", null);
			}
			adminMenu.setMenuParent(String.valueOf(adminMenuOld.getId()));
			adminMenu.setMenuClazz(String.valueOf(Integer.parseInt(adminMenuOld.getMenuClazz()) + 1));
		}
		try {
			permissionService.saveAdminMenu(adminMenu);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}

	/**
	 * 修改菜单
	 * 
	 * @param request
	 * @param adminMenu
	 * @param menuId
	 * @param menuWay
	 * @param menuHiddenE
	 * @return
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult modifyMenu(HttpServletRequest request, AdminMenu adminMenu,boolean menuHiddenE) {
		try {
			if (menuHiddenE) {
				adminMenu.setMenuHidden(1);
			} else {
				adminMenu.setMenuHidden(0);
			}
			permissionService.saveAdminMenu(adminMenu);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param request
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult deleteMenu(HttpServletRequest request, int menuId) {

		try {
			permissionService.deleteAdminMenu(LoginRealm.getCurrentUser().getId(),menuId);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
}
