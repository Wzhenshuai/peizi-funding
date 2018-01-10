package com.icaopan.user.action;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.util.LogUtils;
import com.icaopan.admin.util.ServletUtil;
import com.icaopan.enums.enumBean.UserStatus;
import com.icaopan.stock.model.StockUser;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.stock.service.StockUserWhitelistService;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserChannel;
import com.icaopan.user.model.UserFrozenLog;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserChannelService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.page.Page;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/24.
 */
@Controller
@RequestMapping("/frontUser")
public class FrontUserAction {

    @Autowired
    UserService userService;

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    UserChannelService userChannelService;

    @Autowired
    SecurityService securityService;

    @Autowired
    StockUserBlacklistService blacklistService;

    @Autowired
    StockUserWhitelistService whitelistService;

    @Autowired
    ChannelPositionService channelPositionService;

    /**
     * 请求跳转前台用户页面
     *
     * @param
     * @return String (view PageName)
     */
    @RequestMapping("/userIndex")
    @RequiresUser
    public String userIndex(HttpServletRequest request) {
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)) {
            request.setAttribute("admin", "true");
        }
        return "user/userIndex";
    }
    
    /**
     * 请求跳转前台用户页面
     *
     * @param
     * @return String (view PageName)
     */
    @RequestMapping("/userFront")
    @RequiresUser
    public String userFront(HttpServletRequest request) {
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)) {
            request.setAttribute("admin", "true");
        }
        return "user/userFront";
    }

    /**
     * 请求跳转资金冻结管理页面
     * @param request
     * @return String (view PageName)
     * */
    @RequestMapping(value = "/frozen")
    @RequiresUser
    public String frozen(HttpServletRequest request, @Param("userId")Integer userId, ModelMap modelMap){
        User user = userService.findUserById(userId);
        modelMap.addAttribute("user",user);
        return "user/frozenUserAmount";
    }

    /**
     * 更新冻结资金
     * @param id
     * @param frozen
     * @param type
     * @return
     * */
    @RequestMapping("/updateFrozen")
    @ResponseBody
    @RequiresUser
    public ResponseResult updateFrozen(@Param("frozen")double frozen,@Param("type")String type,@Param("id")Integer id,@Param("remark")String remark) {
        User user = userService.findUserById(id);
        try {
            remark = new String(remark.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (user==null){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新冻结金额失败", null);
        }
        BigDecimal freezing = new BigDecimal(frozen);
        if (StringUtils.equals("unfroze",type)){    //如果是解冻，则用当前冻结金额减请求解冻的金额，并将结果更新到数据库中，作为需要更新的冻结金额
            BigDecimal frozenResult = user.getFrozen().subtract(freezing);      //用于二次核算
            freezing = freezing.negate();               //如果是解冻，取负数
            if (BigDecimalUtil.compareTo(frozenResult,BigDecimalUtil.ZERO)<0){
                return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "解冻金额不可大于已冻结金额", null);
            }
        }else {
            if (BigDecimalUtil.compareTo(freezing,user.getAvailable())>0){
                return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "冻结金额不可大于可用金额", null);
            }
        }
        try {
            AdminUser currentUser = LoginRealm.getCurrentUser();
            String frozeType = StringUtils.equals("unfroze", type) ? "解冻" : "冻结";
            LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[用户管理-资金冻结]-[" + frozeType + "金额:" + freezing + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int fs = userService.updateUserFrozen(id,freezing);
        if (fs>0){
            userService.saveUserFrozenLog(new UserFrozenLog(id,user.getUserName(),new BigDecimal(frozen),type,remark));
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新冻结金额成功", null);
        }else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新冻结金额失败", null);
        }
    }

    /**
     * 查询资金冻结记录
     * */
    @RequestMapping("/findFrozenLog")
    @ResponseBody
    @RequiresUser
    public Page findFrozenLog(@Param("aoData")String aoData,@Param("userId")Integer userId){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        UserFrozenLog log = new UserFrozenLog();
        if(!adminUserService.isSuperAdmin(user)){
            log.setCustomerId(user.getCustomerId());
        }
        log.setUserId(userId);
        page = userService.findUserFrozenLogByPage(log,page);
        return page;
    }


    /**
     * 分页查询用户信息
     *
     * @param aoData     分页配置信息
     * @param userName   用户名
     * @param customerId 资金方编号
     * @param channelId  通道编号
     * @param status     状态
     * @return Page           返回分页页面对象
     */
    @RequestMapping("/find")
    @ResponseBody
    @RequiresUser
    public Page findUser(@Param("aoData") String aoData, @Param("userName") String userName, @Param("customerId") Integer customerId, @Param("channelId") Integer channelId, @Param("status") String status) {
        Page page = new Page(aoData);
        if (StringUtils.isBlank(userName)) {
            userName = null;
        }
        UserPageParams params = new UserPageParams();
        params.setUserName(userName);
        params.setChannelId(channelId);
        params.setCustomerId(customerId);
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)) {
            params.setCustomerId(adminUser.getCustomerId());
        }
        if (StringUtils.equals(status,"-1")){
            params.setStatus(null);
        }else if (status != null && status.length() != 0) {
            params.setStatus(UserStatus.getByCode(status).getCode());
        }
        else {
            params.setStatus(UserStatus.Normal.getCode());
        }
        page = userService.findUserByPage(page, params);
        return page;
    }


    /**
     * 根据通道ID查询所有绑定在该通道下的用户
     * */
    @RequestMapping(value = "/findUsersByChannelId")
    @ResponseBody
    @RequiresUser
    public Page findUsersByChannelId(@Param("aoData") String aoData,@Param("channelIdNo") String channelIdNo){
        Page page = new Page(aoData);
        if (channelIdNo==null||channelIdNo.length()==0) return page;
        Integer channelId = Integer.valueOf(channelIdNo);
        UserPageParams pageParams = new UserPageParams(channelId);
        page = userService.findUserByPage(page,pageParams);
        return page;
    }

    /**
     * 根据资金方编号查询用户信息
     */
    @RequestMapping("/findByCustomerId")
    @ResponseBody
    @RequiresUser
    public List<User> findByCustomerId(@Param("customerId") Integer customerId) {
        if (customerId == null)
            return null;
        List<User> userList = userService.findByCustomerId(customerId);
        return userList;
    }

    /**
     * 增加关联用户
     *
     * @param idA 当前用户编号
     * @param idB 待添加用户的用户名
     * @return
     */
    @RequestMapping("/addRelatedUser")
    @RequiresUser
    @ResponseBody
    public ResponseResult addRelatedUser(@RequestParam("idA") Integer idA, @RequestParam("idB") Integer idB) {
        try {
            AdminUser currentUser = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[关联用户]-[增加关联用户]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (idA.intValue() == idB.intValue()) {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "关联账户失败!不可关联自身账户", null);
        }
        boolean bool = userService.updateRelatedId(idA, idB);
        return bool ? new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "关联账户成功", null) : new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "关联账户失败!请重试", null);
    }

    /**
     * 查询关联用户
     *
     * @param userId 用户编号
     * @return List<User> 用户对象集合
     */
    @RequestMapping("/findRelatedUsers")
    @ResponseBody
    @RequiresUser
    public List<User> findRelatedUsers(@RequestParam("userId") Integer userId) {
        List<User> list = userService.findRelatedUsers(userId);
        return list;
    }

    /**
     * 取消关联用户
     *
     * @param userId 待取消用户编号
     * @return
     */
    @RequestMapping("/unlink")
    @ResponseBody
    @RequiresUser
    public ResponseResult unlink(@RequestParam("userId") Integer userId) {
        try {
            AdminUser currentUser = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[关联用户]-[取消关联用户]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean bool = userService.unLink(userId);
        return bool ? new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "取消关联成功", null) : new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "取消关联账户失败!请重试", null);
    }

    /**
     * 跳转增加用户信息页面
     *
     * @param request
     * @param model
     * @return String
     */
    @RequestMapping(value = "/add")
    @RequiresUser
    public String add(HttpServletRequest request, Model model) {
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (adminUserService.isSuperAdmin(adminUser)) {
            request.setAttribute("admin", "true");
        }
        if (!adminUserService.isSuperAdmin(adminUser)) {
            model.addAttribute("customerId", adminUser.getCustomerId());
        }
        return "user/addUser";
    }

    /**
     * 保存增加用户信息
     *
     * @param user 用户对象
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @RequiresUser
    public ResponseResult save(User user) {
        AdminUser adminUser = LoginRealm.getCurrentUser();
        try {
            LogUtils.saveLog(ServletUtil.getRequest(), adminUser.getId(), "[用户管理]-[增加用户]");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!adminUserService.isSuperAdmin(adminUser)) {
            user.setCustomerId(adminUser.getCustomerId());
        }
        if (user != null && user.getAmount() != null) {
            user.setAvailable(user.getAmount());
        } else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "保存信息失败，请重试", null);
        }
        if (user.getRiskUpAmplitude()!=null) user.setRiskUpAmplitude(user.getRiskUpAmplitude().divide(BigDecimalUtil.HUNDRED));
        if (user.getRiskDownAmplitude()!=null) user.setRiskDownAmplitude(user.getRiskDownAmplitude().divide(BigDecimalUtil.HUNDRED));
        int i = userService.saveUser(user);
        if (i > 0) {
            userChannelService.saveUserChannel(processChannelInfoSnippet(user));
            if ((user.getRiskFlag() & 8)==8){
                whitelistService.insertBatch(processStockListInfoSnippet(user.getId(),user.getCustomerId(),user.getWhiteList()));
            }
            if ((user.getRiskFlag() & 4)==4){
                blacklistService.insertBatch(processStockListInfoSnippet(user.getId(),user.getCustomerId(),user.getBlackList()));
            }
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "保存成功", null);
        } else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "保存信息失败，请重试", null);
        }
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    @RequiresUser
    public ResponseResult update(User user) {
        try {
            AdminUser currentUser = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[用户管理]-[用户详情修改]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getRiskUpAmplitude() != null) {
            user.setRiskUpAmplitude(user.getRiskUpAmplitude().divide(BigDecimalUtil.HUNDRED));
        }
        ;
        if (user.getRiskDownAmplitude() != null) {
            user.setRiskDownAmplitude(user.getRiskDownAmplitude().divide(BigDecimalUtil.HUNDRED));
            /*try {
                AdminUser currentUser = LoginRealm.getCurrentUser();
                LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[用户管理]-[跌停振幅比例:" + user.getRiskDownAmplitude().divide(BigDecimalUtil.HUNDRED) + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
        ;
        boolean bool = userService.updateUser(user);
        if (bool) {
            /*try {
                AdminUser currentUser = LoginRealm.getCurrentUser();
                LogUtils.saveLog(ServletUtil.getRequest(),currentUser.getId(),"[用户管理]-[更新用户通道]");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            userChannelService.updateUserChannel(processChannelInfoSnippet(user));

            if ((user.getRiskFlag() & 8)==8){
               /* try {
                    AdminUser currentUser = LoginRealm.getCurrentUser();
                    LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[用户管理]-[更新用户白名单:" + user.getWhiteList() + "]");
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                whitelistService.updateBatch(processStockListInfoSnippet(user.getId(),user.getCustomerId(),user.getWhiteList()));
            }
            if ((user.getRiskFlag() & 4)==4){
                /*try {
                    AdminUser currentUser = LoginRealm.getCurrentUser();
                    LogUtils.saveLog(ServletUtil.getRequest(), currentUser.getId(), "[用户管理]-[更新用户黑名单" + user.getBlackList() + "]");
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                blacklistService.updateBatch(processStockListInfoSnippet(user.getId(),user.getCustomerId(),user.getBlackList()));
            }
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新信息成功", null);
        } else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新信息失败，请重试", null);
        }
    }

    private List<UserChannel> processChannelInfoSnippet(User user) {
        int userId = user.getId();
        int level = 1;
        List<UserChannel> channelList = new ArrayList<UserChannel>();
        String channelIds = user.getChannelIds();
        String[] ids = channelIds.split(":");
        for (int j = 0; ids != null && j < ids.length; j++) {
            String channelInfo = ids[j];
            if (StringUtils.isNotEmpty(channelInfo)) {
                String[] channelInfoArr = channelInfo.split(",");
                String channelId = channelInfoArr[0];
                String userChannelType = channelInfoArr[1];
                String available = "0";
                String proportion = null;
                if (channelInfoArr.length >= 3) {
                    available = channelInfoArr[2];
                    if(available == null || StringUtils.isEmpty(available) || StringUtils.equals(available,"null")){
                        available = "0";
                    }

                }
                if (channelInfoArr.length >= 4) {
                    proportion = channelInfoArr[3];
                }
                if (StringUtils.isNoneBlank(channelId)) {
                    channelList.add(new UserChannel(userId, Integer.parseInt(channelId), level, new BigDecimal(available), Integer.valueOf(userChannelType), proportion == null? null : Integer.valueOf(proportion)));
                    level += 1;
                }
            }
        }
        return channelList;
    }

    private List<StockUser> processStockListInfoSnippet(Integer userId,Integer customerId,String stockList){
        List<StockUser> stockUsers = new ArrayList<>();
        if (stockList==null) return null;
        String[] stockCodes = stockList.split(":");
        for (String code : stockCodes){
            if ("end".equals(code)) continue;
            String name = securityService.queryNameByCode(code);
            if (name==null||name.length()==0) continue;
            StockUser stockUser = new StockUser(code,name,userId,customerId);
            stockUsers.add(stockUser);
        }
        return stockUsers;
    }

    /**
     * 跳转到修改密码页面
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "editPwd")
    @RequiresUser
    public String editPwd(@RequestParam("userId") Integer userId, Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "user/editPwd";
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @return
     */
    @RequestMapping(value = "updatePwd")
    @RequiresUser
    @ResponseBody
    public ResponseResult updatePwd(@RequestParam("id") Integer userId, @RequestParam("passWord") String password,@RequestParam("rePassword")String rePassword) {
        if (!StringUtils.equals(password,rePassword)){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "修改密码成功失败，请重试", null);
        }
        try {
            userService.updatePwd(userId, password);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "修改密码成功", null);
        } catch (Exception e) {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "修改密码成功失败，请重试", null);
        }
    }

    /**
     * 跳转金额(增配减配)
     *
     * @param userId
     * @param dbAmount 增额/减额
     * @return
     */
    @RequestMapping(value = "/editAssets", method = RequestMethod.POST)
    @RequiresUser
    @ResponseBody
    public ResponseResult add(@RequestParam("userId") Integer userId, @RequestParam("amount") double dbAmount, @RequestParam("changeCause") String changeCause) {
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[用户管理]-[金额调整]-[金额:" + dbAmount + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigDecimal amount = BigDecimal.valueOf(dbAmount);
        boolean bool = false;
        try {
            bool = userService.updateAmountAndAvailable(userId, amount, amount,changeCause);
        } catch (Exception e) {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
        }
        if (bool) {
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "金额调整成功", null);
        } else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "金额调整成功，请重试", null);
        }

    }

    /**
     * 跳转编辑用户信息页面
     *
     * @param request
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresUser
    public String edit(HttpServletRequest request, @RequestParam("userId") Integer userId, Model model) {
        User user = userService.findUserById(userId);
        if (user.getRiskUpAmplitude()!=null) user.setRiskUpAmplitude(user.getRiskUpAmplitude().multiply(BigDecimalUtil.HUNDRED).setScale(2,BigDecimal.ROUND_FLOOR));
        if (user.getRiskDownAmplitude()!=null) user.setRiskDownAmplitude(user.getRiskDownAmplitude().multiply(BigDecimalUtil.HUNDRED).setScale(2,BigDecimal.ROUND_FLOOR));
        user.setTotalMaketValue(channelPositionService.getMaketValueByUserId(userId));
        user.setAmount(user.getAmount().add(user.getTotalMaketValue()));
        model.addAttribute("user", user);
        return "user/userDetail";
    }

    /**
     * 验证用户增加用户的时候的用户名是否重复
     *
     * @param request
     * @param userName
     * @return
     */
    @RequestMapping(value = "/verify")
    @RequiresUser
    @ResponseBody
    public ResponseResult verify(HttpServletRequest request, @RequestParam("userName") String userName) {
        if (userService.verifyUserName(userName))
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "该用户名可用", null);
        else
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "该用户名已经存在", null);
    }

    /**
     * 导出用户信息
     *
     * @param response
     * @param
     */
    @RequestMapping(value = "/exportUser")
    @ResponseBody
    @RequiresUser
    public void exportUser(HttpServletResponse response,@Param("userPageParams")UserPageParams userPageParams) {
        AdminUser user = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(user)) {
            userPageParams.setCustomerId(user.getCustomerId());
        }
        if (StringUtils.equals(userPageParams.getStatus(),"-1")){
            userPageParams.setStatus(null);
        }
        List<User> userList = userService.findAllUser( userPageParams);
        JSONArray data = JSONArray.fromObject(userList);
        com.icaopan.util.ExcelUtil.exportExcel("用户信息",
                new String[]{"用户名","姓名","用户类型","资金方","通道","委托策略","状态","帐户总金额","可用金额","本金","融资金额","佣金费率","佣金下限","警戒/平仓线","单/中小/中小单/创/创单","登陆次数","上次登陆"},
                new String[]{"userName","realName","userClassTypeDisplay","customerName","channelName","userTradeTypeDisplay","statusStr","amount","available","cashAmount","financeAmount","ratioFeeDisplay","minCost","line","stockScale","loginCount","lastLoginTimeStr"},
                data,
                response,
                "yyyy-MM-dd HH:mm:ss");
    }
}