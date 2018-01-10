package com.icaopan.web.stock.action;

import java.util.ArrayList;
import java.util.List;

import com.icaopan.stock.model.StockUser;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.stock.service.StockUserWhitelistService;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.marketdata.market.MarketDataManager;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.web.common.BaseAction;
import com.icaopan.web.vo.StockDetailVO;
import com.icaopan.web.vo.StockVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;


@Controller
@RequestMapping("/stock")
public class StockAction extends BaseAction {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MarketDataManager marketDataManager;

    @Autowired
    private MarketdataService marketdataService;

    @Autowired
    private StockUserWhitelistService whitelistService;

    @Autowired
    private StockUserBlacklistService blacklistService;

    /**
     * 查询所有股票列表
     */
    @RequestMapping(value = "/QueryAllSecurityModel")
    @ResponseBody
    public Object queryAllSecurityModel() {
        List<StockSecurity> list = securityService.findAllStockSecurity();
        List<StockVO> listStock = new ArrayList<StockVO>();
        for (int i = 0; i < list.size(); i++) {
            StockSecurity ss = list.get(i);
            String stockCode = ss.getCode();
            String stockName = ss.getName();
            StockVO vo = new StockVO();
            String simple = ss.getFirstLetter();
            if (org.apache.commons.lang.StringUtils.isNotBlank(simple)) {
                simple = simple.toUpperCase();
            }
            vo.setValue(stockCode + " " + stockName + " " + simple);
            vo.setData(stockCode);
            vo.setShortNameCN(ss.getFirstLetter());
            vo.setName(stockName);
            listStock.add(vo);
        }
        return listStock;
    }

    @RequestMapping(value = "/QueryStockDetail")
    @ResponseBody
    public Object queryStockDetailAction(String stockCode) {
        StockDetailVO vo = securityService.queryStockDetail(stockCode);
        return vo;
    }

    @RequestMapping(value = "/QueryUserBlackStock")
    @ResponseBody
    @RequiresUser
    public List<StockUser> QueryUserBlackStock(@Param("userId")Integer userId){
        List<StockUser> stockUsers = blacklistService.queryByUserId(userId);
        return stockUsers;
    }

    @RequestMapping(value = "/QueryUserWhiteStock")
    @ResponseBody
    @RequiresUser
    public List<StockUser> QueryUserWhiteStock(@Param("userId")Integer userId){
        List<StockUser> stockUsers = whitelistService.queryByUserId(userId);
        return stockUsers;
    }
    
    @RequestMapping(value = "/updateMarketData")
    @ResponseBody
    public Object updateMarketData(String stockCode) {
    	try {
			marketDataManager.readMarketData();
			return "更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "更新失败,"+e.getMessage();
		}
        
    }
    
    @ResponseBody
    @RequestMapping(value = "/monitorOneStockMarketData")
    public Object monitorOneStockMarketData(String stockCode){
    	MarketDataSnapshot shot=marketdataService.getBySymbol(stockCode);
    	return shot;
    }
}
