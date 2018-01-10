package com.icaopan.web.user.action;

import com.icaopan.common.util.MD5;
import com.icaopan.enums.enumBean.UserStatus;
import com.icaopan.user.model.User;
import com.icaopan.user.service.UserService;
import com.icaopan.web.common.BaseAction;
import com.icaopan.web.common.Constants;
import com.icaopan.web.common.ResponseResult;
import com.icaopan.web.user.realm.LoginRealm;
import com.icaopan.web.vo.AccountVO;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/UserLogin")
    public String loginUser(HttpServletRequest request, HttpServletResponse response, String name, String pwd,
                            String visitPath,String userType,String source) {
    	String _pwd="";
    	if("sys".equalsIgnoreCase(source)){
    		_pwd=pwd;
		}else{
			_pwd=MD5.MD5Encrypt(pwd);
		}
        // 初始化创建用户密码的token，用于验证
        UsernamePasswordToken token = new UsernamePasswordToken(name, _pwd);
        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (LockedAccountException e) {
            request.setAttribute("errorInfo", Constants.LOCKED_ACCOUNT_ERROR);
            return "user/login";
        } catch (Exception e) {
            request.setAttribute("errorInfo", Constants.USERNAME_PASS_ERROR);
            return "user/login";
        }
        User user = LoginRealm.getCurrentUser();
        request.getSession().setAttribute("user", user);
        List<User> associatedUser = userService.findRelatedUsers(user.getId());
        associatedUser = userService.removeUserByStatus(associatedUser, UserStatus.Logout);
        request.getSession().setAttribute("associatedUsers", associatedUser);
        request.getSession().setAttribute("userType", userType);
        return "redirect:/user/ViewIndex.do";
    }

    @RequestMapping(value = "/SwitchUser")
    public String switchUser(HttpServletRequest request, HttpServletResponse response, Integer userId) {
        SecurityUtils.getSubject().logout();    //退出登陆
        // 初始化创建用户密码的token，用于验证
        User user = userService.findUserById(userId);
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (LockedAccountException e) {
            request.setAttribute("errorInfo", Constants.LOCKED_ACCOUNT_ERROR);
            return "user/login";
        } catch (Exception e) {
            request.setAttribute("errorInfo", Constants.USERNAME_PASS_ERROR);
            return "user/login";
        }
        request.getSession().setAttribute("user", user);
        List<User> associatedUser = userService.findRelatedUsers(user.getId());
        associatedUser = userService.removeUserByStatus(associatedUser, UserStatus.Logout);
        request.getSession().setAttribute("associatedUsers", associatedUser);
        userService.updateLoginCount(user.getId()); // 更改登陆次数
        return "redirect:/user/ViewIndex.do";
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/LogOut")
    public String logOut(HttpServletRequest request) {
    	String userType=(String) request.getSession().getAttribute("userType");
        SecurityUtils.getSubject().logout();
        request.setAttribute("userType", userType);
        return "/user/login";
    }

    @RequestMapping(value = "/ViewIndex")
    @RequiresUser
    public String viewIndex(HttpServletRequest request) {
        User user = LoginRealm.getCurrentUser();
        request.setAttribute("user", user);
        return "index";
    }

    /**
     * 查询账户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ViewMyAccountCenterIndex")
    @RequiresUser
    @ResponseBody
    public Object viewMyAccountCenterIndex(HttpServletRequest request) {
        User user = LoginRealm.getCurrentUser();
        AccountVO accountInfo = userService.queryUserAccount(user);
        return accountInfo;
    }


    @RequestMapping(value = "/isRefreshMarketData")
    @ResponseBody
    public Object isRefreshMarketData() {
        boolean isRefresh = false;
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        User user=getUser();
        if(user==null){
        	map.put("login", true);
        	return map;
        }
        try {
            Date date = new Date();
            int dayOfWeek = date.getDay();
            int hours = date.getHours();
            if (dayOfWeek >= 1 && dayOfWeek <= 5) {
                if (hours >= 9 && hours <= 15) {
                    isRefresh = true;
                }
            }
        } catch (Exception e) {
            isRefresh = true;
        }
        
        map.put("isRefresh", isRefresh);
        return map;
    }

    @RequestMapping(value = "/ChangePwd")
    @RequiresUser
    @ResponseBody
    public Object changePwd(String oldPwd, String newPwd) {
        User user = getUser();
        user = userService.findUser(user.getUserName(), MD5.MD5Encrypt(oldPwd));
        if (user == null) {
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "旧密码输入不正确", null);
        }
        try {
            userService.updatePwd(user.getId(), newPwd);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "", null);
        } catch (Exception e) {
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, e.getMessage(), null);
        }
    }
}
