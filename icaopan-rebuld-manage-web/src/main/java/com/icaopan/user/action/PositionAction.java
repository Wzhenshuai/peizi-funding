package com.icaopan.user.action;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.common.CommonAction;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.log.LogUtil;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.model.Flow;
import com.icaopan.trade.service.FlowService;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.admin.util.LogUtils;
import com.icaopan.admin.util.ServletUtil;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.SecurityPositionVO;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
@Controller
@RequestMapping("/position")
public class PositionAction extends CommonAction {

    protected Logger logger= LogUtil.getLogger(getClass());

    @Autowired
    ChannelPositionService channelPositionService;

    @Autowired
    UserPositionService userPositionService;

    @Autowired
    SecurityService securityService;

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    FlowService flowService;

    /**
     * 跳转用户持仓查询页面
     * */
    @RequestMapping("/userPosition")
    @RequiresUser
    public String positionUserIndex(HttpServletRequest request){
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return "user/userPosition";
    }

    @RequestMapping("/positionSummary")
    @RequiresUser
    public String positionSummaryIndex(HttpServletRequest request){
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return "user/positionSummary";
    }


//    /**
//     * 跳转通道持仓查询页面
//     * */
//    @RequestMapping("/channelPosition")
//    @RequiresUser
//    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
//    public String positionChannelIndex(HttpServletRequest request){
//        AdminUser user = LoginRealm.getCurrentUser();
//        if(adminUserService.isSuperAdmin(user)){
//            request.setAttribute("admin", "true");
//        }
//        return "user/channelPosition";
//    }

    /**
     * 保存通道持仓信息
     * @param securityPosition
     * @return
     * */
    @RequestMapping("/save")
    @RequiresUser
    @ResponseBody
    public ResponseResult save(ChannelSecurityPosition securityPosition){
        AdminUser adminUser = LoginRealm.getCurrentUser();
        // LogUtils.saveLog(ServletUtil.getRequest(), adminUser.getId(), "通道持仓信息保存");
        if (!adminUserService.isSuperAdmin(adminUser)){
            securityPosition.setCustomerId(adminUser.getCustomerId());
        }
        securityPosition.setInternalSecurityId(SecurityUtil.getInternalSecurityIdBySecurityCode(securityPosition.getInternalSecurityId()));
        UserSecurityPosition userSecurityPosition = new UserSecurityPosition(securityPosition.getCustomerId(), securityPosition.getInternalSecurityId(),
                                                                             securityPosition.getUserId(), securityPosition.getAvailable(), securityPosition.getAmount(), securityPosition.getCostPrice());
        //boolean channelBool = channelPositionService.saveSecurityPosition(securityPosition);
        boolean userBool = userPositionService.saveUserSecurityPosition(userSecurityPosition);

        //添加交割单流水信息
        Flow flow = new Flow();
        flow.setUserId(securityPosition.getUserId());
        flow.setType(TradeFowType.STOCK_ADD);
        flow.setNotes(TradeFlowNote.CASH_ADDCREATEHAND);

        flow.setCustomerId(securityPosition.getCustomerId());
        flow.setAdjustQuantity(securityPosition.getAmount());
        flow.setAdjustAmount(BigDecimal.ZERO);
        flow.setSecurityCode(SecurityUtil.getSecurityCodeById(securityPosition.getInternalSecurityId()));
        
        boolean flowBool = flowService.saveFlow(flow);

        if (userBool&&flowBool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "保存成功", null);
        }else{
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "保存信息失败，请重试", null);
        }
    }

    /**
     * 分页查询通道持仓信息
     * @param aoData
     * @param userName
     * @param customerId
     * @param channelId
     * @param securityCode
     * @return page
     * */
    @RequestMapping("/findChannelPosition")
    @RequiresUser
    @ResponseBody
    public Page findChannelPosition(@Param("aoData") String aoData,
                                    @Param("userName")String userName,
                                    @Param("customerId")Integer customerId,
                                    @Param("channelId")Integer channelId,
                                    @Param("userCPId")Integer userCPId,@Param("securityCode")String securityCode) throws Exception {
        Page page = new Page(aoData);
        if (StringUtils.isBlank(securityCode)){
            securityCode=null;
        }
        PositionParams params = new PositionParams();
        params.setUserName(userName);
        params.setInternalSecurityId(securityCode);
        params.setCustomerId(customerId);
        params.setChannelId(channelId);
        params.setUserId(userCPId);
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)){
            params.setCustomerId(adminUser.getCustomerId());
        }
        page = channelPositionService.findPositionByPage(page,params);
        return page;
    }

    /**
     * 分页查询用户持仓信息
     * @param aoData
     * @param userName
     * @param customerId
     * @param securityCode
     * @return page
     * */
    @RequestMapping("/findUserPosition")
    @RequiresUser
    @ResponseBody
    public Page findUserPosition(@Param("aoData") String aoData,@Param("userName")String userName,@Param("userId")Integer userId,@Param("customerId")Integer customerId,@Param("securityCode")String securityCode) throws Exception {
        Page page = new Page(aoData);
        if (StringUtils.isBlank(securityCode)){
            securityCode=null;
        }
        UserPositionParams params = new UserPositionParams();
        params.setUserName(userName);
        params.setInternalSecurityId(securityCode);
        params.setCustomerId(customerId);
        params.setUserId(userId);
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)){
            params.setCustomerId(adminUser.getCustomerId());
        }
        page = userPositionService.findUserPositionByPage(page,params);
        return page;
    }

    /**
     * 持仓汇总分页查询
     * @param aoData
     * @return
     * */
    @RequestMapping("/findSummaryPosition")
    @ResponseBody
    @RequiresUser
    public Page findSummaryByPage(@Param("aoData") String aoData,@Param("customerId")Integer customerId,@Param("securityCode")String securityCode){
        AdminUser user = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(user)){
            customerId = user.getCustomerId();
        }
        Page page = new Page(aoData);
        if (securityCode==null||securityCode.length()==0||securityCode==""){
            securityCode = null;
        }else {
            securityCode = SecurityUtil.getInternalSecurityIdBySecurityCode(securityCode);
        }
        page = userPositionService.querySummaryPositionByCustomerId(page,new PositionParams(customerId,securityCode));
        return page;
    }

    /**
     * 查询某个资金方某个股票下的用户持仓明细
     * @param customerIdParam
     * @param securityCodeParam
     * @return
     * */
    @RequestMapping("/findUserPositionDetail")
    @ResponseBody
    @RequiresUser
    public Page findUserPositionDetail(HttpServletRequest request,
                                       @Param("aoData") String aoData,
                                       @Param("userNameParam")String userNameParam,
                                       @Param("customerIdParam")Integer customerIdParam,
                                       @Param("securityCodeParam")String securityCodeParam){
        Page page = new Page(aoData);
        if (securityCodeParam==null||customerIdParam==null) return page;
        String secCode = SecurityUtil.getInternalSecurityIdBySecurityCode(securityCodeParam);
        PositionParams params = new PositionParams();
        params.setCustomerId(customerIdParam);
        params.setInternalSecurityId(secCode);
        params.setUserName(userNameParam);
        page = userPositionService.queryByCustomerIdAndInternalSecurityId(page,params);
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return page;
    }

    /**
     * 根据资金方编号，股票编号，查询通道持仓明细
     * */
    @RequestMapping("/findChannelPositionDetail")
    @ResponseBody
    @RequiresUser
    public Page findChannelPositionDetail(HttpServletRequest request,
                                       @Param("aoData") String aoData,
                                       @Param("channelId")Integer channelId,
                                       @Param("customerIdP")Integer customerIdP,
                                       @Param("securityCodeP")String securityCodeP){
        Page page = new Page(aoData);
        if (securityCodeP==null||customerIdP==null) return page;
        String secCode = SecurityUtil.getInternalSecurityIdBySecurityCode(securityCodeP);
        PositionParams params = new PositionParams(customerIdP,secCode);
        params.setChannelId(channelId);
        page = channelPositionService.findByCustomerIdAndInternalId(page,params);
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return page;
    }

    /**
     * 跳转增加持仓信息页面
     * */
    @RequestMapping("/add")
    @RequiresUser
    public String add(ModelMap modelMap,HttpServletRequest request){
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)){
            modelMap.addAttribute("customerId",adminUser.getCustomerId());
        }
        return "user/addUserPosition";
    }

    /**
     * 验证增加持仓信息的时候输入的股票代码是否有效
     * @param internalSecurityId
     * @return
     * */
    @RequestMapping("/verify")
    @RequiresUser
    @ResponseBody
    public ResponseResult verify(@RequestParam("userId")Integer userId,@RequestParam("internalSecurityId")String internalSecurityId){
        String securityId = SecurityUtil.getInternalSecurityIdBySecurityCode(internalSecurityId);
        if (securityId==null||securityId.length()==0){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "输入股票代码无效", null);
        }
        if (userId==null){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "请选择用户", null);
        }
        boolean bool = channelPositionService.verify(userId,securityId);
        StockSecurity security = securityService.findByNameAndCode(null, internalSecurityId);
        if (security == null||bool){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "输入股票代码无效", null);
        }
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "验证通过", null);
    }

    /**
     * 根据用户编号(userId)查询通道持仓信息
     * @param id
     * @return
     * */
    @RequestMapping("/get")
    @RequiresUser
    @ResponseBody
    public ChannelSecurityPosition get(@RequestParam("id")Integer id){
        return channelPositionService.findSecurityPositionById(id);
    }

    /**
     * 跳转编辑页面
     */
    @RequestMapping("/editPrice")
    @RequiresUser
    public String editPrice(@RequestParam("id") Integer id, Model model) {
        ChannelSecurityPosition position = channelPositionService.findSecurityPositionById(id);
        model.addAttribute("position", position);
        return "user/editChannelPositionPrice";
    }

    /**
     * 跳转编辑页面
     */
    @RequestMapping("/editUserPositionPrice")
    @RequiresUser
    public String editUserPositionPrice(@RequestParam("id") Integer id, Model model) {
        UserSecurityPosition position = userPositionService.findById(id);
        model.addAttribute("position", position);
        return "user/editUserPositionPrice";
    }

    /**
     * 跳转编辑页面
     * */
    @RequestMapping("/edit")
    @RequiresUser
    public String edit(@RequestParam("id")Integer id, Model model){
        ChannelSecurityPosition position = channelPositionService.findSecurityPositionById(id);
        model.addAttribute("position", position);
        return "user/editPosition";
    }

    /**
     * 跳转持仓转移页面
     * */
    @RequestMapping("/movePosition")
    @RequiresUser
    public String moveStock(@RequestParam("id")Integer id, Model model){
        ChannelSecurityPosition position = channelPositionService.findSecurityPositionById(id);
        model.addAttribute("position", position);
        return "user/movePosition";
    }

    /**
     * 持仓转移接口
     * */
    @RequestMapping("/moveStock")
    @RequiresUser
    @ResponseBody
    public ResponseResult moveStock(@RequestParam("userId")Integer userId,
                                    @RequestParam("targetId")Integer targetId,
                                    @RequestParam("currentId")Integer currentId,
                                    @RequestParam("internalSecurityId")String internalSecurityId,
                                    @RequestParam("moveAmount")double moveAmount,
                                    @RequestParam("isHiddenRecord")String isHiddenRecord){
        Boolean bool = false;
        BigDecimal mvAmount = new BigDecimal(moveAmount);
        ChannelSecurityPosition currentChannelPosition = channelPositionService.find(userId,currentId,internalSecurityId);
        if (currentChannelPosition==null|| BigDecimalUtil.compareTo(currentChannelPosition.getAvailable(),mvAmount)<0){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "迁移持仓失败：迁移持仓量不足", null);
        }
        try {
            bool = channelPositionService.movePosition(currentChannelPosition,targetId,mvAmount,isHiddenRecord);
        }catch (Exception e){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
        }
        if (bool){
            try {
                AdminUser user = LoginRealm.getCurrentUser();
                LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[通道持仓]-[持仓转移]-[通道:" + currentChannelPosition.getChannelName() + "][股票" + currentChannelPosition.getInternalSecurityId() + "][数量:" + mvAmount + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "转移持仓数据成功!", null);
        }else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "迁移持仓失败：迁移持仓量不足", null);
        }
    }


    /**
     * 更新通道持仓信息
     * */
    @RequestMapping("/updateChannelPosition")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateByChanged(@RequestParam("id")Integer id,
                                          @RequestParam("availableChanged")double availableChanged,
                                          @RequestParam("availableChanged")double amount,
                                          @RequestParam("vmark")String vmark,
                                          @RequestParam("createTime") String createTime,
                                          @RequestParam("isHidden") String isHidden,
                                          @RequestParam("costPrice")double costPrice){
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            String direction = (vmark.equals("-")) ? "减少" : "增加";
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[通道持仓]-[持仓调整]-[" + direction + ":" + amount + "]-[成本价:" + costPrice + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        double available = 0;
        available = (vmark.equals("-")) ? (-availableChanged) : availableChanged;
        try {
        	channelPositionService.updatePosition(id, BigDecimal.valueOf(available), BigDecimal.valueOf(available), BigDecimal.valueOf(costPrice), createTime,isHidden);
        	logger.info("更新通道持仓信息："+"{id:"+id+",available:"+available+",costPrice:"+costPrice+"}");
        	if ((amount+available)<=0) { //如果当前调整持仓操作将持仓操作调整为0，删除该持仓记录
                logger.info("调整"+"({id:"+id+",available:"+available+",costPrice:"+costPrice+"})"+"之后通道持仓为空，对持仓进行清除空持仓处理！");
                channelPositionService.deleteSecurityPosition();
                userPositionService.deleteUserSecurityPosition();
            }
        	return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新持仓数据成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
    }


    /**
     * 更新通道持仓成本价
     */
    @RequestMapping("/updateChannelPositionPrice")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateChannelPositionPrice(@RequestParam("id") Integer id,
                                                     @RequestParam("costPrice") double costPrice) {
        channelPositionService.updatePositionPrice(id, BigDecimal.valueOf(costPrice));
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[通道持仓]-[成本价调整]-[id:" + id + "]-[成本价：" + costPrice + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("更新通道持仓成本价："+"{id:"+id+",costPrice:"+costPrice+"}");
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新持仓成本价成功", null);
    }

    /**
     * 更新用户持仓成本价
     */
    @RequestMapping("/updateUserPositionPrice")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateUserPositionPrice(@RequestParam("id") Integer id,
                                                     @RequestParam("costPrice") double costPrice) {
        userPositionService.updatePositionPrice(id, BigDecimal.valueOf(costPrice));
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[用户持仓]-[成本价调整]-[成本价：" + costPrice + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("用户持仓持仓成本价："+"{id:"+id+",costPrice:"+costPrice+"}");
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新用户持仓成本价成功", null);
    }

    /**
     * 导出用户持仓汇总信息
     *
     * @param response
     * @param
     */
    @RequestMapping(value = "/exportUserPositionSummary")
    @ResponseBody
    @RequiresUser
    public void exportUserPositionSummary(HttpServletResponse response, @Param("userPositionParams")PositionParams positionParams) {
        AdminUser user = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(user)) {
            positionParams.setCustomerId(user.getCustomerId());
        }
        String innternalSecurityId = positionParams.getInternalSecurityId();
        if (innternalSecurityId==null||innternalSecurityId.length()<5){
            innternalSecurityId=null;
        }else {
            innternalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(innternalSecurityId);
        }
        positionParams.setInternalSecurityId(innternalSecurityId);
        Page<SecurityPositionVO> userPositionList = userPositionService.querySummaryPositionByCustomerId(null,positionParams);
        JSONArray data = JSONArray.fromObject(userPositionList.getAaData());
        com.icaopan.util.ExcelUtil.exportExcel("持仓汇总",
                new String[]{"证券名称","证券代码","持仓数量","持仓可用数量","资金方"},
                new String[]{"securityName","securityCode","amount","availableAmount","customerName"},
                data,
                response,
                "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 导出用户持仓
     *
     * @param response
     * @param
     */
    @RequestMapping(value = "/exportUserPosition")
    @ResponseBody
    @RequiresUser
    public void exportUserPosition(HttpServletResponse response, @Param("userPositionParams")UserPositionParams userPositionParams) {
        AdminUser user = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(user)) {
            userPositionParams.setCustomerId(user.getCustomerId());
        }
        List<SecurityPositionVO> userPositionList = userPositionService.queryUserPositionByParams(userPositionParams);
        JSONArray data = JSONArray.fromObject(userPositionList);
        com.icaopan.util.ExcelUtil.exportExcel("用户持仓",
                new String[]{"用户名","证券名称","证券代码","股票市值","持仓盈亏","持仓盈亏比例","持仓数量","持仓可用数量","现价","成本价","资金方"},
                new String[]{"userName","securityName","securityCode","marketValue","marketProfit","marketProfitPercent","amount","availableAmount","latestPrice","costPrice","customerName"},
                data,
                response,
                "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 导出通道持仓
     *
     * @param response
     * @param
     */
    @RequestMapping(value = "/exportChannelPosition")
    @ResponseBody
    @RequiresUser
    public void exportChannelPosition(HttpServletResponse response, @Param("positionParams")PositionParams positionParams) {
        AdminUser user = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(user)) {
            positionParams.setCustomerId(user.getCustomerId());
        }
        List<SecurityPositionVO> channelPositionList= channelPositionService.findPositionByParams(positionParams);
        JSONArray data = JSONArray.fromObject(channelPositionList);
        com.icaopan.util.ExcelUtil.exportExcel("通道持仓",
                new String[]{"用户名","证券名称","证券代码","数量","可用数量","股票市值","盈亏","盈亏比例","现价","成本价","资金方","通道名称"},
                new String[]{"userName","securityName","securityCode","amount","availableAmount","marketValue","marketProfit","marketProfitPercent","latestPrice","costPrice","customerName","channelName"},
                data,
                response,
                "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据通道编号一键清空通道持仓，并调整用户持仓数据
     * */
    @RequestMapping("/oneKeyClearPosition")
    @ResponseBody
    @RequiresUser
    public ResponseResult oneKeyClearPosition(@Param("channelId")Integer channelId){
        boolean bool = channelPositionService.clearPositionByChannelId(channelId);
        if (bool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "一键清空通道持仓成功", null);
        }else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "一键清空通道持仓失败", null);
        }
    }
    
    /**
     * 查询所有持仓股票代码
     * @return
     */
    @RequestMapping("/queryAllPositionStockCode")
    @ResponseBody
    public Object queryAllPositionStockCode(){
    	List<String> list= channelPositionService.queryAllStockCode();
    	String str=StringUtils.join(list.toArray(), ",");
    	return str;
    }
}
