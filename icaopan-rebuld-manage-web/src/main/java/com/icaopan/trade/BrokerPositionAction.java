package com.icaopan.trade;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.common.CommonAction;
import com.icaopan.common.util.DataUtils;
import com.icaopan.trade.bean.BrokerPositionQueryParams;
import com.icaopan.trade.bean.BrokerPositionResult;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.service.BrokerPositionService;
import com.icaopan.util.page.Page;

@Controller
@RequestMapping("/brokerPosition")
public class BrokerPositionAction extends CommonAction {

	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private BrokerPositionService brokderPositionService;
	
	@RequestMapping("/brokerPositionJsp")
	public String brokerPositionJsp(HttpServletRequest request){
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			request.setAttribute("admin", "true");
		}
		return "trade/check/checkBrokerPosition";
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public Object query(@Param("aoData") String aoData, BrokerPositionQueryParams brokerPositionQueryParams){
		Page<BrokerPositionResult> page=new Page<BrokerPositionResult>(aoData);
		AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
        	int customerId=adminUser.getCustomerId();
        	brokerPositionQueryParams.setCustomerId(customerId);
        }
        page.setAaData(brokderPositionService.selectByCondition(brokerPositionQueryParams));
        return page;
	}

    @RequestMapping(value = "/exportBrokerPosition")
    @ResponseBody
    @RequiresUser
	public void exportBrokerPosition(HttpServletResponse response, @Param("aoData") String aoData, BrokerPositionQueryParams brokerPositionQueryParams) {
		AdminUser adminUser = LoginRealm.getCurrentUser();
		if (!adminUserService.isSuperAdmin(adminUser)) {
			int customerId = adminUser.getCustomerId();
			brokerPositionQueryParams.setCustomerId(customerId);
		}
		JSONArray data = JSONArray.fromObject(brokderPositionService.selectByCondition(brokerPositionQueryParams));
		com.icaopan.util.ExcelUtil.exportExcel("券商与平台持仓对账",
				new String[]{"通道名称", "股票代码", "股票名称", "平台持仓数量", "券商持仓数量", "对账日期", "比对结果", "相差数量"},
				new String[]{"channelName", "stockCode", "stockName", "icpAmount", "amount", "createDateStr", "checkResult", "minusAmount"},
				data, response, "yyyy-MM-dd HH:mm:ss");
    }
    
	@RequestMapping("/queryDetail")
	public String queryDetail(HttpServletRequest request,int id){
		BrokerPositionResult position= brokderPositionService.queryDetailByBrokerPositionId(id);
		request.getSession().setAttribute("result", position);
		return "/trade/check/adjustChannelPositionByBrokerPostion";
	}
	
	
	@RequestMapping("/adjustChannelPositionByBrokerPosition")
	@ResponseBody
	public Object adjustChannelPositionByBrokerPosition(String brokerPositionId,String channelPositionIds,String sides,String amounts,String costPrices){
		try {
			int _brokerPositionId=Integer.valueOf(brokerPositionId);
			BrokerPositionResult result=brokderPositionService.queryDetailByBrokerPositionId(_brokerPositionId);
			List<String> _channelPositionIds=DataUtils.transeStringToStringListByDotSpliter(channelPositionIds, ",");
			List<String> _sides=DataUtils.transeStringToStringListByDotSpliter(sides, ",");
			List<String> _amounts=DataUtils.transeStringToStringListByDotSpliter(amounts, ",");
			List<String> _costPrices=DataUtils.transeStringToStringListByDotSpliter(costPrices, ",");
			brokderPositionService.adjustChannelPositionByBrokerPosition(result, _channelPositionIds, _sides, _amounts, _costPrices);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("placementRepeat",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
	}
}
