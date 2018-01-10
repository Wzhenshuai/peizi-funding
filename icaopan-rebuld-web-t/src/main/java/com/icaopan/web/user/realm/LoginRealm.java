package com.icaopan.web.user.realm;


import com.icaopan.user.model.User;
import com.icaopan.user.service.UserService;
import com.icaopan.web.common.Constants;
import com.icaopan.web.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
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


public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * @return 当前登录的用户
     * @throws 若未登录抛出UnauthenticatedException
     * @Description 获取当前登录的用户
     */
    public static User getCurrentUser() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new UnauthenticatedException();
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_KEY);
        return user;
    }

    /**
     * @param principals: 用户信息（继承自Shrio）
     * @return 授权之后的信息
     * @Description 对用户进行授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 初始化权限类		
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        // 判断当前用户是否认证		
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new UnauthenticatedException();
        }
        // 授权成功，将授权后的信息返回		
        return simpleAuthorInfo;
    }

    /**
     * @param authcToken: 用户登录信息
     * @return 登录的用户
     * @Description 用于用户登录登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {

        // 获取基于用户名和密码的令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 判断当前用户是否登录成功
        User user = userService.findUser(token.getUsername(), String.valueOf(token.getPassword()));
        if (user != null) {
            if (StringUtils.equals("2", user.getStatus())) {
                throw new LockedAccountException("账号被停用，前联系管理员");
            }
            LoggerUtil.authLog(user, "用户开始登录验证成功！");
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(),
                                                                        token.getPassword(), user.getRealName());
            this.setSession(Constants.SESSION_KEY, user);
            // 更新登录次数
            userService.updateLoginCountAndLastTime(user);
            return authcInfo;
        }
        return null;
    }

    /**
     * @param key-value键值对
     * @return 空
     * @Description 将信息保存在Session中
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

}
