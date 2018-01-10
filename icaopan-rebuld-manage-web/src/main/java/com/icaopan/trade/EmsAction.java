package com.icaopan.trade;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.other.ResponseResult;
import com.icaopan.common.CommonAction;
import com.icaopan.common.util.DataUtils;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.ems.service.EmsService;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.util.DateUtils;
import com.icaopan.web.vo.PlacementCheck;
import com.icaopan.web.vo.TdxFillItemVO;


@Controller
@RequestMapping("/ems")
public class EmsAction extends CommonAction{

	@Autowired
	private ChannelPlacementService channelPlacementService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private EmsService emsService;

	@Autowired
	AdminUserService adminUserService;
	
	@RequestMapping(value = "/emsManage", method = RequestMethod.GET)
	public String emsManage(HttpServletRequest request){
		AdminUser adminUser= LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			request.setAttribute("admin", "true");
		}
        return "trade/ems/channelPlacementForEms";
    }
	
	@RequestMapping(value = "/viewLogs")
	public String viewLogs(HttpServletRequest request){
		List<Channel> channelList=channelService.selectAll();
		request.setAttribute("channelList", channelList);
		request.setAttribute("today", DateUtils.getDate("yyyyMMdd"));
        return "trade/ems/placementLogs";
    }
	/**
	 * 填写委托编号
	 * @param placementNo
	 * @return
	 */
	@RequestMapping("/signPlacementNo")
	@ResponseBody
	public Object signPlacementNo(Integer placementId,String placementNo){
		try {
			if(StringUtils.isEmpty(placementNo)){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "委托编号不能为空", null);
			}
			channelPlacementService.fillPlacementCode(placementId, placementNo);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("signPlacementNo",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
	
	
	/**
	 * 委托补单
	 * @param placementId
	 * @return
	 */
	@RequestMapping("/placementRepeat")
	@ResponseBody
	public Object placementRepeat(Integer placementId){
		try {
			ChannelPlacement placement=channelPlacementService.getById(placementId);
			if(placement==null){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "通道委托记录没有找到", null);
			}
			emsService.placement(placement);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("placementRepeat",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
		
	}
	
	/**
	 * 撤单补单
	 * @param placementId
	 * @return
	 */
	@RequestMapping("/cancelRepeat")
	@ResponseBody
	public Object cancelRepeat(Integer placementId){
		try {
			ChannelPlacement placement=channelPlacementService.getById(placementId);
			if(placement==null){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "通道委托记录没有找到", null);
			}
			emsService.cancel(placement);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("cancelRepeat",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
	
	/**
	 * 撤单回报
	 * @param placementId
	 * @return
	 */
	@RequestMapping("/cancelPlacement")
	@ResponseBody
	public Object cancelPlacement(Integer placementId,String quantity){
		try {
			ChannelPlacement placement=channelPlacementService.getById(placementId);
			BigDecimal _quantity=BigDecimal.ZERO;
			if(placement==null){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "通道委托记录没有找到", null);
			}
			if(StringUtils.isEmpty(quantity)){
				_quantity=placement.getQuantity().subtract(placement.getFillQuantity());
			}else{
				_quantity=new BigDecimal(quantity);
			}
            channelPlacementService.fill(placement, TransactionType.CANCELLED, _quantity, null, null,false);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("cancelPlacement",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
	
	/**
	 * 成交回报
	 * @param placementId
	 * @param reportNo
	 * @param price
	 * @param quantity
	 * @return
	 */
	@RequestMapping("/sendFilledReport")
	@ResponseBody
	public Object sendFilledReport(Integer placementId,String reportNo,String price,String quantity){
		try {
			ChannelPlacement placement=channelPlacementService.getById(placementId);
			if(placement==null){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "通道委托记录没有找到", null);
			}
			if(!DataUtils.validePrice(price)||!DataUtils.valideQuantity(quantity)){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "价格或者数量输入格式不正确", null);
			}
			if(StringUtils.isEmpty(reportNo)){
				//return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "成交编号不能为空", null);
				reportNo=System.currentTimeMillis()+"";
			}
			if(StringUtils.isEmpty(placement.getPlacementCode())){
				channelPlacementService.fillPlacementCode(placementId, "-1");
			}
			placement=channelPlacementService.getById(placementId);
			emsService.dockFillByHand(placement, reportNo, new BigDecimal(quantity), new BigDecimal(price),false);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("sendFilledReport",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
	
	
	/**
	 * 废单
	 * @param placementId
	 * @param quantity
	 * @return
	 */
	@RequestMapping("/sendRejectReport")
	@ResponseBody
	public Object sendRejectReport(Integer placementId,String quantity){
		try {
			ChannelPlacement placement=channelPlacementService.getById(placementId);
			BigDecimal _quantity=BigDecimal.ZERO;
			if(placement==null){
				return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "通道委托记录没有找到", null);
			}
			if(StringUtils.isEmpty(quantity)){
				_quantity=placement.getQuantity().subtract(placement.getFillQuantity());
			}else{
				_quantity=new BigDecimal(quantity);
			}
            channelPlacementService.fill(placement, TransactionType.INVALID, _quantity, null, null,false);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("sendRejectReport",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
	
	/**
	 * PB自废单
	 * @param account
	 * @param symbol
	 * @param price
	 * @param quantity
	 * @param side
	 * @param executionSno
	 * @param errorMessage
	 * @return
	 */
	@RequestMapping("/sendRejectReportForPB")
	@ResponseBody
	public Object sendRejectReportForPB(String account,String symbol,String price,String quantity,String side,String executionSno,String errorMessage){
		try {
			if(StringUtils.isNotEmpty(errorMessage)){
				errorMessage=new String(errorMessage.getBytes("iso8859-1"),"utf-8");
			}
			channelPlacementService.sendRejectReportForPB(account, symbol, new BigDecimal(price), new BigDecimal(quantity), side, executionSno, errorMessage);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("sendRejectReportForPB",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
	
	@RequestMapping("/updatePBBalance")
	@ResponseBody
	public String updatePBBalance(String account,String availableBalance){
		if(StringUtils.isNotEmpty(account)&&StringUtils.isNotEmpty(availableBalance)){
			if(availableBalance.contains(",")){
				availableBalance=availableBalance.replaceAll(",", "");
				channelService.updateCashAvailable(new BigDecimal(availableBalance), account, null);
			}
		}
		return "success";
	}
	
	 /**
     * 下载所有通道用户股票成交汇总
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/downloadCheckPlacmentSummary")
    public void downloadCheckPlacmentSummary(HttpServletResponse response) throws UnsupportedEncodingException{
    	
    	List<TdxFillItemVO> list=PlacementCheck.getList();
    	JSONArray data=JSONArray.fromObject(list);
        com.icaopan.util.ExcelUtil.exportExcel("通道当日委托未对接数量汇总",
                new String[]{"通道名称", "通道账号", "股票代码","股票名称", "买入成交总数量","卖出成交总数量"},
                new String[]{"accountName", "accountNo", "stockCode", "stockName", "buyFillQuantity", "sellFillQuantity"},
                data, response, "HH:mm:ss");
    }
	
}
