package com.icaopan.stock.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.common.util.ChineseCharToEnUtil;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.log.LogUtil;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.marketdata.security.SecurityDataService;
import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.bean.StockSecurityParams;
import com.icaopan.stock.dao.StockPoolMapper;
import com.icaopan.stock.dao.StockSecurityMapper;
import com.icaopan.stock.model.StockPool;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.StockDetailVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;


/**
 * Created by RoyLeong@royleo.xyz on 2016/11/15.
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private Logger logger = LogUtil.getLogger(getClass());

    @Autowired
    private StockPoolMapper stockPoolMapper;

    @Autowired
    private StockSecurityMapper stockSecurityMapper;

    @Autowired
    private MarketdataService marketdataService;

    @Autowired
    private SecurityDataService securityDataService;
 
    /*
    * 保存股票信息
    * */
    @Override
    public int saveStockPool(StockPool stockPool) {
        return 0;
    }

    @Override
    public int saveBatchStockPool(List<StockPool> listStockPool) {
        return 0;
    }

    @Override
    public Page findStockPoolByPage(Page page,StockPoolParams params) {
        page.setAaData(stockPoolMapper.findByPage(page, params));
        return page;
    }

    @Override
    public int deleteStockPool(StockPool stockPool) {
        return 0;
    }

    @Override
    public List<StockPool> findStockPoolByCustomerId(Integer customerId) {
        return null;
    }

    @Override
    public boolean saveStockSecurity(StockSecurity stockSecurity) {
        return stockSecurityMapper.insert(stockSecurity);
    }

    @Override
    public boolean saveBatchStockSecurity(List<StockSecurity> stockSecurity) {
        return stockSecurityMapper.insertBatch(stockSecurity);
    }


    @Override
    public StockSecurity findStockSecurityById(String internalSecurityId) {
        return stockSecurityMapper.selectByPrimaryKey(internalSecurityId);
    }

    @Override
    public StockSecurity findByNameAndCode(String name, String code) {
        return stockSecurityMapper.findByNameAndCode(name,code);
    }

    @Override
    public Page findStockSecurityByPage(Page page, StockSecurityParams params) {
        List<StockSecurity> list = stockSecurityMapper.findAllByPage(page,params);
        page.setAaData(list);
        return page;
    }

    @Override
    public List<StockSecurity> findByCodeStartIndex(String codeStartIndex) {
        return stockSecurityMapper.findByCodeStartIndex(codeStartIndex);
    }


    @Override
    public boolean update(String id,String name,String firstLetter,Boolean suspensionFlag) {
        boolean bool = stockSecurityMapper.update(id,name,firstLetter,suspensionFlag);
        return bool;
    }

    @Override
    public int updateFlag(String internalSecurityId) {
        return 0;
    }

    /**
     * 验证股票名是否存在,如果存在就返回true,否则返回false
     * @param name
     * @return boolean
     * */
    @Override
    public boolean containName(String name) {
        return stockSecurityMapper.findByNameAndCode(name,null)!=null;
    }

    @Override
    public boolean containCode(String code) {
        return stockSecurityMapper.findByNameAndCode(null,code) != null;
    }

	@Override
	public List<StockSecurity> findAllStockSecurity() {
		return stockSecurityMapper.findAllStockSecurity();
	}

	@Override
	public StockDetailVO queryStockDetail(String securityCode) {
		StockDetailVO vo=new StockDetailVO();
		StockSecurity stock=findByNameAndCode(null, securityCode);
		//查询现价
        BigDecimal latestPrice = marketdataService.getLatestPrice(securityCode);
        vo.setLastPrice(latestPrice);
        vo.setShortNameCn(stock.getName());
		return vo;
	}

	@Override
	public String queryNameByCode(String securityCode) {
		StockSecurity stock=findByNameAndCode(null, securityCode);
		if(stock!=null){
			return stock.getName();
		}
		return "";
	}

//    @Override
//    public String updateAll() {
//        StringBuffer sb = new StringBuffer();
//        List<SecurityData> szSecurityList = securityDataService.getSecurityDatas(ExchangeConsts.SHEN_JIAO_SUO);
//        List<SecurityData> shSecurityList = securityDataService.getSecurityDatas(ExchangeConsts.SHANG_JIAO_SUO);
//        List<SecurityData> all = new ArrayList<SecurityData>();
//        all.addAll(szSecurityList);
//        all.addAll(shSecurityList);
//        int updateNum = 0;
//        int addNum = 0;
//        for(SecurityData securityData : all){
//            String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(securityData.getSymbol());
//            StockSecurity stockSecurity =  stockSecurityMapper.selectByPrimaryKey(internalSecurityId);
//            if(stockSecurity!=null){
//                String firstLetter = stockSecurity.getFirstLetter();
//                if(StringUtils.equals(securityData.getName(),stockSecurity.getName())==false){
//                    firstLetter = ChineseCharToEnUtil.getAllFirstLetter(securityData.getName());
//                }
//                this.update(internalSecurityId,securityData.getName(),firstLetter,securityData.getSuspensionFlag());
//                logger.info("security update:"+internalSecurityId+"-"+securityData.getName()+"-"+firstLetter+"-"+securityData.getSuspensionFlag());
//                updateNum++;
//            }else {
//                String firstLetter = ChineseCharToEnUtil.getAllFirstLetter(securityData.getName());
//                stockSecurity = new StockSecurity();
//                stockSecurity.setInternalSecurityId(internalSecurityId);
//                stockSecurity.setCode(securityData.getSymbol());
//                stockSecurity.setExchangeCode(securityData.getExchange());
//                stockSecurity.setFirstLetter(firstLetter);
//                stockSecurity.setIssueDate(securityData.getIssueDate());
//                stockSecurity.setName(securityData.getName());
//                if(securityData.getSuspensionFlag()){
//                    stockSecurity.setSuspensionFlag(Boolean.valueOf("1"));
//                }else{
//                    stockSecurity.setSuspensionFlag(Boolean.valueOf("0"));
//                }
//
//                this.saveStockSecurity(stockSecurity);
//                logger.info("security add:" + internalSecurityId + "-" + securityData.getName() + "-" + firstLetter + "-" + securityData.getSuspensionFlag());
//                addNum++;
//                sb.append("[").append(internalSecurityId).append("-").append(securityData.getName()).append("]");
//            }
//        }
//
//        return "更新数量:"+updateNum+",新增数量:"+addNum+sb.toString();
//    }
    
	/**
	 * 从内存更新
	 */
    @Override
    public String updateAll() {
    	
        StringBuffer sb = new StringBuffer();
        List<MarketDataSnapshot> all=marketdataService.getAllMarketData();
        int updateNum = 0;
        int addNum = 0;
        for(MarketDataSnapshot securityData : all){
            String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(securityData.getSymbol());
            StockSecurity stockSecurity =  stockSecurityMapper.selectByPrimaryKey(internalSecurityId);
            if(stockSecurity!=null){
                String firstLetter = stockSecurity.getFirstLetter();
                if(StringUtils.equals(securityData.getStockName(),stockSecurity.getName())==false){
                    firstLetter = ChineseCharToEnUtil.getAllFirstLetter(securityData.getStockName());
                }
                this.update(internalSecurityId,securityData.getStockName(),firstLetter,securityData.isSuspensionFlag());
                logger.info("security update:"+internalSecurityId+"-"+securityData.getStockName()+"-"+firstLetter+"-"+securityData.isSuspensionFlag());
                updateNum++;
            }else {
                String firstLetter = ChineseCharToEnUtil.getAllFirstLetter(securityData.getStockName());
                stockSecurity = new StockSecurity();
                stockSecurity.setInternalSecurityId(internalSecurityId);
                stockSecurity.setCode(securityData.getSymbol());
                stockSecurity.setExchangeCode(securityData.getExchange());
                stockSecurity.setFirstLetter(firstLetter);
//                stockSecurity.setIssueDate(securityData.get);
                stockSecurity.setName(securityData.getStockName());
                if(securityData.isSuspensionFlag()){
                    stockSecurity.setSuspensionFlag(Boolean.valueOf("1"));
                }else{
                    stockSecurity.setSuspensionFlag(Boolean.valueOf("0"));
                }

                this.saveStockSecurity(stockSecurity);
                logger.info("security add:" + internalSecurityId + "-" + securityData.getStockName() + "-" + firstLetter + "-" + securityData.isSuspensionFlag());
                addNum++;
                sb.append("[").append(internalSecurityId).append("-").append(securityData.getStockName()).append("]");
            }
        }

        return "更新数量:"+updateNum+",新增数量:"+addNum+sb.toString();
    }

    @Override
    public List<StockSecurity> findAllStockSecurityByExchange(String exchange){
    	if(StringUtils.isNotEmpty(exchange)){
    		exchange=exchange.toUpperCase();
    	}
    	return stockSecurityMapper.findAllStockSecurityByExchange(exchange);
    }
    
    @Override
    public List<String> findAllStockCode(){
    	return stockSecurityMapper.findAllStockCode();
    }

}
