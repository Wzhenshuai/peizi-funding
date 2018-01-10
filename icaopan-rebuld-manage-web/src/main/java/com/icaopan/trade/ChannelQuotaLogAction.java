package com.icaopan.trade;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.common.CommonAction;
import com.icaopan.trade.bean.ChannelQuotaLog;
import com.icaopan.trade.service.ChannelQuotaLogService;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.icaopan.sys.util.ChannelUtils.adminUserService;

/**
 * Created by RoyLeong @royleo.xyz on 2017/10/9.
 */
@Controller
@RequestMapping(value = "/channelQuotaLog")
public class ChannelQuotaLogAction extends CommonAction {

    @Autowired
    ChannelQuotaLogService quotaLogService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String channelQuotaLogIndex(HttpServletRequest request){
        AdminUser adminUser= LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "trade/channel/channelQuotaLog";
    }

    /**
     * 查找通道限额列表
     * @param aoData
     * @return
     */
    @RequestMapping(value = "/find")
    @ResponseBody
    @RequiresUser
    public Page<ChannelQuotaLog> findUserChannelQuotaLog(@Param("aoData") String aoData, ChannelQuotaLog channelQuotaLog){
        Page<ChannelQuotaLog> page = new Page<ChannelQuotaLog>(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            channelQuotaLog.setCustomerId(user.getCustomerId());
        }
        page = quotaLogService.selectQuotaLogByPage(page,channelQuotaLog);
        return page;
    }

}
