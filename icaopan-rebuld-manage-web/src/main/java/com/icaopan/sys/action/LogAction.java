package com.icaopan.sys.action;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.log.service.LogService;
import com.icaopan.sys.model.LogParams;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("sysLog")
public class LogAction {
    @Autowired
    AdminUserService adminUserService;
    @Autowired
    private LogService logService;

    /**
     * 跳转通道自动委托页面
     * */
    @RequestMapping(value = "/initSysLog", method = RequestMethod.GET)
    public String initSysLog(HttpServletRequest request){
        AdminUser adminUser= LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "sys/sysLogList";
    }
        /**
         *
         * @Description (后台日志查询)
         * @param aoData
         * @param logParams
         * @return
         */
/*        @RequestMapping("/findSysLog")
        @ResponseBody
        @RequiresUser
        public Page findSysLog(@Param("aoData") String aoData, @Param("logParams")LogParams logParams){
            Page page = new Page(aoData);
            AdminUser user = LoginRealm.getCurrentUser();
            if(!adminUserService.isSuperAdmin(user)){
                logParams.setCustomerId(user.getCustomerId());
            }
            page = logService.findByLogParams(page,logParams);
            return page;
        }*/
}
