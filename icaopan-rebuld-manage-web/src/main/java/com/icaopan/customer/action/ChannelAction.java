package com.icaopan.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.util.LogUtils;
import com.icaopan.admin.util.ServletUtil;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.customer.model.ChannelApply;
import com.icaopan.customer.model.ChannelLimit;
import com.icaopan.enums.enumBean.ChannelApplyStatus;
import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.model.Flow;
import com.icaopan.trade.service.FlowService;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserChannel;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserChannelService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.common.CommonAction;
import com.icaopan.customer.bean.ChannelParams;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.util.page.Page;

import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;


@Controller
@RequestMapping("/channel")
public class ChannelAction extends CommonAction {

	@Autowired
	UserService userService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private UserChannelService userChannelService;

	@Autowired
	private ChannelPositionService channelPositionService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserPositionService userPositionService;

	@Autowired
	private MarketdataService marketdataService;


	@Autowired
	private FlowService flowService;
	
	@RequestMapping(value = "/channel", method = RequestMethod.GET)
	private String customer(){
		return "customer/channelList";
	}
	/**
	 * 查找通道列表
	 * @param aoData
	 * @param channelParams
	 * @return
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	@RequiresUser
	public Page findList(@Param("aoData") String aoData,@Param("channelParams")ChannelParams channelParams,
						 @Param("code")String code,@Param("name")String name,@Param("isAvailable")Boolean isAvailable,
						 @Param("channelType")String channelType,@Param("customerId")Integer customerId,
						 @Param("autoFill")Boolean autoFill,
						 @Param("channelId")Integer channelId){
		Page page=new Page(aoData);
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			channelParams.setCustomerId(adminUser.getCustomerId());
		}else {
			channelParams.setCustomerId(customerId);
		}
		channelParams.setCode(code);
		channelParams.setName(name);
		channelParams.setChannelType(channelType);
		channelParams.setChannelId(channelId);
		channelParams.setIsAvailable(isAvailable);
		channelParams.setAutoFill(autoFill);
		page=channelService.getChannelByPage(page, channelParams);
		return page;
	}
	
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public Object findAll(){
		return channelService.selectAll();
	}
	
	@RequestMapping(value = "/form")
	public String form(HttpServletRequest request,Integer id){
		if(id!=null){
			Channel channel=channelService.getChannelById(id);
			request.setAttribute("channel", channel);
		}
		return "customer/channelForm";
	}

	/**
	 * 根据资金方编号查询所拥有的通道
	 * @param customerId
	 * @return
	 * */
	@RequestMapping(value = "/findByCustomerId")
	@ResponseBody
	@RequiresUser
	public List<Channel> findByCustomerId(@Param("customerId")Integer customerId){
		if (customerId==null){
			return null;
		}
		List<Channel> channelList = channelService.selectChannelsBuCustomerId(customerId);
		return channelList;
	}

	/**
	 * 根据资金方编号查询多个资金方拥有的通道
	 * @param customerIds
	 * @return
	 * */
	@RequestMapping(value = "/findByCustomerIds")
	@ResponseBody
	@RequiresUser
	public List<Channel> findByCustomerIds(@Param("customerIds")String customerIds){
		if (customerIds==null||customerIds.length()==0) return null;
		String[] csIds = customerIds.split(",");
		Integer[] customerIdd = new Integer[csIds.length];
		for (int i = 0; i < csIds.length; i++) {
			customerIdd[i] = Integer.valueOf(csIds[i]);
		}
		List<Channel> channels = channelService.selectChanelsByCustomerIds(customerIdd);
		return channels;
	}

	/**
	 * 根据用户编号查询所拥有的通道(关联用户通道)
	 * @param userId
	 * @return
	 * */
	@RequestMapping(value = "/findByUserId")
	@ResponseBody
	@RequiresUser
	public List<Channel> findByUserId(@Param("userId")Integer userId){
		if (userId==null){
			return null;
		}
		List<Channel> channelList = channelService.selectChannelByUserId(userId);
		return channelList;
	}

	/**
	 * 查找该资金方尚未分配给该用户的通道
	 * @param userId
	 * @param customerId
	 * @return
	 * */
	@RequestMapping(value = "/findByCustomerIdNotInUserId")
	@ResponseBody
	@RequiresUser
	public List<Channel> findByCustomerIdNotInUserId(@Param("customerId")Integer customerId,@Param("userId")Integer userId){
		if (userId==null||customerId==null){
			return null;
		}
		List<Channel> channelList = channelService.findByCustomerIdNotInUserId(customerId,userId);
		return channelList;
	}


	/**
	 *
	 * @Description (盘前未通过测试单的账户)
	 * @param aoData
	 * @return
	 */
	@RequestMapping("/findUnchecked_AM")
	@ResponseBody
	@RequiresUser
	public Page findUnchecked_AM(@Param("aoData") String aoData){
		Page page = new Page(aoData);
		page = channelService.findUnchecked_AM(page);
		return page;
	}

	/**
	 * 跳转盘前未通过测试单的账户页面
	 * */
	@RequestMapping("/Unchecked_AM")
	@RequiresUser
	public String findUnchecked(ModelMap modelMap, HttpServletRequest request){
		return "trade/channel/placementTest_AM";
	}



	/**
	 * 新增或者修改客户
	 * @param request
	 * @param channel
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult save(HttpServletRequest request,Channel channel){
		try {
			channelService.saveChannel(channel);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "添加失败,请联系管理员", null);
		}
	}

	/**
	 * 新增MQ
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addMQ", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult addMQ(HttpServletRequest request,String code, String type){
		try {
			channelService.addMQ(code, type);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "MQ添加成功", null);
		} catch (Exception e) {
			logger.error("MQ添加失败",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}

	@RequestMapping(value = "/verifyCode")
	@RequiresUser
	@ResponseBody
	public ResponseResult verify(@RequestParam("code")String code){
		if (!channelService.verifyCode(code))
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "该通道代码可用", null);

		else
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "该通道代码已经存在", null);

	}

	/**
	 * 跳转增加持仓信息页面
	 * */
	@RequestMapping("/add")
	@RequiresUser
	public String add(ModelMap modelMap, HttpServletRequest request){
		AdminUser user = LoginRealm.getCurrentUser();
		if(adminUserService.isSuperAdmin(user)){
			request.setAttribute("admin", "true");
		}
		AdminUser adminUser = LoginRealm.getCurrentUser();
		if (!adminUserService.isSuperAdmin(adminUser)){
			modelMap.addAttribute("customerId",adminUser.getCustomerId());
		}
		return "customer/addChannelPosition";
	}

	/**
	 * 从用户详情跳转增加持仓
	 * */
	@RequestMapping("/addForUser")
	@RequiresUser
	public String addForUser(@Param("userId")Integer userId, ModelMap modelMap, HttpServletRequest request){
		AdminUser user = LoginRealm.getCurrentUser();
		if(adminUserService.isSuperAdmin(user)){
			request.setAttribute("admin", "true");
		}
		User frontUser = userService.findUserById(userId);
		modelMap.addAttribute("customerId",frontUser.getCustomer().getId());
		modelMap.addAttribute("user",frontUser);
		return "customer/addChannelPositionForUser";
	}

	/**
	 * 验证增加通道持仓信息的时候输入的股票代码是否已经存在
	 * @param internalSecurityId
	 * @return
	 * */
	@RequestMapping("/verify")
	@RequiresUser
	@ResponseBody
	public ResponseResult verify(@RequestParam("userId")Integer userId,@RequestParam("channelId")Integer channelId,@RequestParam("internalSecurityId")String internalSecurityId){
		String securityId = SecurityUtil.getInternalSecurityIdBySecurityCode(internalSecurityId);
		if (securityId==null||securityId.length()==0){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "输入股票代码无效", null);
		}
		if (channelId==null){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "请选择通道", null);
		}if (userId==null){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "请选择用户", null);
		}
		boolean bool = channelPositionService.verifyChannel(userId,channelId,securityId);
		StockSecurity security = securityService.findByNameAndCode(null, internalSecurityId);
		if (security == null||bool){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "输入股票代码无效", null);
		}
		return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "验证通过", null);
	}

	/**
	 * 保存通道持仓信息
	 * @param securityPosition
	 * @return
	 * */
	@RequestMapping("/savePosition")
	@RequiresUser
	@ResponseBody
	public ResponseResult save(ChannelSecurityPosition securityPosition,@RequestParam("isHidden") String isHidden){
        AdminUser adminUser = null;
        try {
            adminUser = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), adminUser.getId(), "[通道持仓]-[添加通道头寸]-[股票:" + securityPosition.getInternalSecurityId() + "]-[数量:" + securityPosition.getAmount() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!adminUserService.isSuperAdmin(adminUser)){
			securityPosition.setCustomerId(adminUser.getCustomerId());
		}
		try {
			securityPosition.setInternalSecurityId(SecurityUtil.getInternalSecurityIdBySecurityCode(securityPosition.getInternalSecurityId()));
			UserSecurityPosition userSecurityPosition = new UserSecurityPosition(securityPosition.getCustomerId(),securityPosition.getInternalSecurityId(),
					securityPosition.getUserId(),securityPosition.getAvailable(),securityPosition.getAmount(),securityPosition.getCostPrice());
			securityPosition.setTotalCost(BigDecimalUtil.multiply(securityPosition.getAmount(),securityPosition.getCostPrice()));
			channelPositionService.saveSecurityPosition(securityPosition);
			UserSecurityPosition userPositions = userPositionService.findUserSecurityPositionById(userSecurityPosition.getUserId(),null,userSecurityPosition.getInternalSecurityId());
			if (userPositions==null){
				userPositionService.saveUserSecurityPosition(userSecurityPosition);
			}else {
				UserPositionParams params = new UserPositionParams(userSecurityPosition.getUserId(), userSecurityPosition.getInternalSecurityId(), userSecurityPosition.getAmount(),userSecurityPosition.getAvailable(), userSecurityPosition.getCostPrice());
				userPositionService.changeAmountAndCostPrice(params);
				userPositionService.changeAvailable(params);
			}
			//添加交割单流水信息
			Flow flow = new Flow();
			flow.setUserId(securityPosition.getUserId());
			flow.setType(TradeFowType.STOCK_ADD);
			flow.setNotes(TradeFlowNote.CASH_ADDCREATEHAND);

            flow.setChannelId(securityPosition.getChannelId());
            flow.setCostPrice(userSecurityPosition.getCostPrice());
            flow.setCustomerId(securityPosition.getCustomerId());
			flow.setAdjustQuantity(securityPosition.getAmount());
			flow.setAdjustAmount(BigDecimalUtil.multiply(securityPosition.getAmount(), securityPosition.getCostPrice()));
			flow.setSecurityCode(SecurityUtil.getSecurityCodeById(securityPosition.getInternalSecurityId()));
			flow.setCreateTime(DateUtils.parseDate(securityPosition.getCreateTime()));
			
			if(StringUtils.isNotEmpty(isHidden)){
	        	flow.setIsHidden(isHidden);
	        }else{
	        	flow.setIsHidden("0");
	        }
			flowService.saveFlow(flow);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "保存成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "保存信息失败，请重试", null);
		}
	}

	/**
	 * 跳转通道持仓查询页面
	 * */
	@RequestMapping("/channelPosition")
	@RequiresUser
	public String positionChannelIndex(HttpServletRequest request){
		AdminUser user = LoginRealm.getCurrentUser();
		if(adminUserService.isSuperAdmin(user)){
			request.setAttribute("admin", "true");
		}
		return "user/channelPosition";
	}

	/**
	 * 通道单票限制查询
	 * @date 2017-08-29
	 * */
	@RequestMapping("/findChannelSinglePositionLimitByChannelId")
	@RequiresUser
	public ModelAndView findChannelSinglePositionLimitByChannelId(@Param("channelId") Integer channelId,@Param("channelName") String channelName,@Param("customerId")Integer customerId){
		try {
			channelName = new String(channelName.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<ChannelLimit> channelLimits = channelService.findChannelLimit(channelId,customerId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("channelLimitList",channelLimits);
		modelAndView.addObject("channelName",channelName);
		modelAndView.addObject("channelId",channelId);
		modelAndView.setViewName("customer/channelSinglePositionLimit");

		return modelAndView;
	}

	/**
	 * 通道单票限制查询
	 * */
	@RequestMapping("/findChannelLimitByChannelId")
	@RequiresUser
	public ModelAndView findChannelLimitByChannelId(@Param("channelId") Integer channelId,@Param("channelName") String channelName){
		try {
			channelName = new String(channelName.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<ChannelLimit> channelLimits = channelService.findChannelLimitByChannelId(channelId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("channelLimitList",channelLimits);
		modelAndView.addObject("channelName",channelName);
		modelAndView.addObject("channelId",channelId);
		modelAndView.setViewName("customer/channelLimit");

		return modelAndView;
	}
	/**
	 * 更新通道单票限制
	 * */
	@RequestMapping("/updateChannelLimit")
	@ResponseBody
	public ResponseResult updateChannelLimit(@Param("newAmount") BigDecimal newAmount, @Param("limitId")Integer limitId){
	    if (newAmount.intValue()<0 || limitId == null){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "金额有或者通道有问题", null);
        }
        ChannelLimit channelLimit = new ChannelLimit(limitId,newAmount);
		try {
			channelService.updateChannelLimit(channelLimit);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}

	/**
	 * 删除通道单票限制
	 * */
	@RequestMapping("/deleteChannelLimit")
	@ResponseBody
	public ResponseResult deleteChannelLimit(@Param("limitId") Integer limitId){
		try {
			channelService.deleteChannelLimit(limitId);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "删除成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}

	@RequestMapping("/addChannelLimit")
	@ResponseBody
	public ResponseResult addChannelLimit(BigDecimal addAmount, String securityCodeAdd , Integer channelId){
		StockSecurity security = securityService.findByNameAndCode(null, securityCodeAdd);
		if (security == null){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "输入股票代码无效", null);
		}else if (channelId == null || addAmount.intValue()<0){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "输入的参数无效", null);
		}
		ChannelLimit channelLimit = new ChannelLimit(channelId,addAmount,securityCodeAdd);
		try {
			channelService.addChannelLimit(channelLimit);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("单票限制金额失败",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}

	@RequestMapping(value = "/channelApply", method = RequestMethod.GET)
	public String initChannelApply(){
		AdminUser adminUser = LoginRealm.getCurrentUser();
		if (!adminUserService.isSuperAdmin(adminUser)){
			return "customer/channelApplyUserList";
		}else {
			return "customer/channelApplyAdminList";
		}

	}

	@RequestMapping(value = "findChannelApply")
	@ResponseBody
	@RequiresUser
	public Page findChannelApply(@Param("aoData") String aoData, @Param("status") String status){
		Page page=new Page(aoData);
		Integer customerId = null;

		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			customerId = adminUser.getCustomerId();
		}

		page=channelService.findChannelApplyByPage(page, status, customerId);
		return page;

	}

	@RequestMapping(value = "/applyForm", method = RequestMethod.GET)
	public String applyForm(){
		return "customer/addApplyForm";
	}

	@RequestMapping("/addChannelApply")
	@ResponseBody
	public ResponseResult addChannelApply(ChannelApply channelApply){

		AdminUser adminUser = LoginRealm.getCurrentUser();
		if (!adminUserService.isSuperAdmin(adminUser)){
			channelApply.setCustomerId(adminUser.getCustomerId());
		}
		try {
			channelApply.setStatus(ChannelApplyStatus.HANDLEING.getCode());
			channelService.addChannelApply(channelApply);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e){
			System.out.print(e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	/**
	 * 更新通道申请的状态
	 * */
	@RequestMapping("/updateChannelApply")
	@ResponseBody
	public ResponseResult updateChannelApply(@Param("applyId") Integer applyId, @Param("adminNotes")String adminNotes, @Param("status") String status){
		try {
			channelService.updateApplyStatus(status,adminNotes,applyId);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}

	/**
	 * 解绑通道对应的用户，如果该用户只绑定一个通道，则不进行解除绑定操作
	 * @param userId
	 * @param channelId
	 * @return
	 * */
	@RequestMapping(value = "/unbindChannel")
	@ResponseBody
	@RequiresUser
	public ResponseResult unbindUserChannel(@Param("channelId")Integer channelId,@Param("userId")Integer userId){
		List<UserChannel> userChannels = userChannelService.findByUserId(userId);
		User user = userService.findUserById(userId);
		if (userChannels.size()<=1){
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "该用户("+user.getUserName()+")不符合解绑条件", null);
		}
		boolean bool = userChannelService.deleteByUserIdAndChannelId(userId,channelId);
		if (bool){
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "解除绑定用户("+user.getUserName()+")成功", null);
		}else {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "解除绑定用户("+user.getUserName()+")失败，请联系管理员", null);
		}
	}

	/**
	 * 跳转到前台截图
	 * @param response
	 */
	@RequestMapping(value = "/turnToSnapshot")
	public void turnToSnapshot(HttpServletResponse response,int channelPositionId,BigDecimal available){
		String userName;
		String securityName;
		String securityCode;
		BigDecimal marketValue;
		BigDecimal totalValue;
		BigDecimal marketProfit = BigDecimal.ZERO;
		BigDecimal marketProfitPercent=BigDecimal.ZERO;
		BigDecimal positionAmount;
		BigDecimal t0PlacementQuantity;
		BigDecimal latestPrice;
		BigDecimal costPrice=BigDecimal.ZERO;
		ChannelSecurityPosition position=channelPositionService.findSecurityPositionById(channelPositionId);
		positionAmount=position.getAmount();
		t0PlacementQuantity=position.getAvailable();
		userName=position.getUserName();
		String secId=position.getInternalSecurityId();
		StockSecurity ss=securityService.findStockSecurityById(secId);
		securityName=ss.getName();
		securityCode=ss.getCode();
		latestPrice=marketdataService.getLatestPrice(securityCode);
        marketValue = latestPrice.multiply(positionAmount);
        totalValue=marketValue.add(available);
        BigDecimal totalCost=position.getTotalCost();
        if (positionAmount == null||positionAmount.doubleValue()==0) {
        	if(totalCost!=null){
        		marketProfit= totalCost.negate();
        	}
        }else{
        	marketProfit=latestPrice.multiply(positionAmount).subtract(totalCost);
        }
        try {
			securityName=URLEncoder.encode(securityName,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        String url="http://www.hangqingjingling.com/stocks/wycg_snapshot.jsp?userName=%s&securityName=%s&securityCode=%s&available=%s&marketValue=%s&totalValue=%s&marketProfit=%s&marketProfitPercent=%s"
        		+ "&positionAmount=%s&t0PlacementQuantity=%s&latestPrice=%s&costPrice=%s";
        String urlParam=String.format(url, userName,securityName,securityCode,available.setScale(2, BigDecimal.ROUND_HALF_UP),marketValue.setScale(2, BigDecimal.ROUND_HALF_UP),totalValue.setScale(2, BigDecimal.ROUND_HALF_UP),marketProfit.setScale(2, BigDecimal.ROUND_HALF_UP),marketProfitPercent,positionAmount,t0PlacementQuantity,latestPrice,costPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        try {
			response.sendRedirect(urlParam);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
