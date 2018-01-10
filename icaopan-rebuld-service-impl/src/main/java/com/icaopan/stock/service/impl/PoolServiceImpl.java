package com.icaopan.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.icaopan.enums.enumBean.StockPoolType;
import com.icaopan.log.LogUtil;
import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.dao.StockPoolMapper;
import com.icaopan.stock.model.StockPool;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.PoolService;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.util.page.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.icaopan.enums.enumBean.StockPoolType.Gem;
import static com.icaopan.enums.enumBean.StockPoolType.SmallPlates;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
@Service("poolService")
public class PoolServiceImpl implements PoolService {

    private Logger logger = LogUtil.getLogger(getClass());


    @Autowired
    StockPoolMapper stockPoolMapper;

    @Autowired
    SecurityService securityService;

    @Override
    public boolean save(StockPool stockPool) {
        return stockPoolMapper.insert(stockPool);
    }

    @Override
    public boolean saveBatch(List<StockPool> stockPool) {
        return stockPoolMapper.inertBatch(stockPool);
    }


    @Override
    public boolean update(Integer id, String name, String code, Integer customerId, String type) {
        return stockPoolMapper.update(id,name,code,customerId,type);
    }

    @Override
    public boolean containName(String name) {
        List<StockPool> stockPoolList = stockPoolMapper.findByNameAndCode(name,null);
        return stockPoolList.size()>0;
    }

    @Override
    public boolean containCode(String code) {
        return stockPoolMapper.findByNameAndCode(null,code) != null;
    }

    @Override
    public boolean isBannedStock(String stockCode, Integer customerId) {
        int cnt = stockPoolMapper.findBannedStockCnt(stockCode, customerId);
        if(cnt > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isSmallPlatesStock(String stockCode) {
        int cnt = stockPoolMapper.selectCntByCodeAndType(stockCode, SmallPlates.getCode());
        if(cnt > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isGemStock(String stockCode) {
        int cnt = stockPoolMapper.selectCntByCodeAndType(stockCode, Gem.getCode());
        if(cnt > 0){
            return true;
        }
        return false;
    }

    @Override
    public StockPool findByPrimaryId(Integer id) {
        return stockPoolMapper.findByPrimaryId(id);
    }

    @Override
    public List<StockPool> findByCode(String code) {
        List<StockPool> stockPoolList = stockPoolMapper.findByNameAndCode(null,code);
        return stockPoolList;
    }

    @Override
    public Page findByPage(Page page, StockPoolParams params) {
        page.setAaData(stockPoolMapper.findByPage(page,params));
        return page;
    }

    @Override
    public boolean delete(Integer customerId, StockPoolType type) {
        return false;
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        List<Integer> listIds = Arrays.asList(ids);
        return stockPoolMapper.deleteBatch(listIds);
    }

    @Override
    public List<StockPool> findByIdAndType(Integer customerId, StockPoolType types) {
        return stockPoolMapper.findByIdAndStockType(customerId,types.getCode());
    }

    /**
     * 更新股票池
     * */
    @Override
    public String updateStockSecurity() {
        int insertNum = 0;
        int updateNum = 0;
        int keepNum = 0;
        int errorNum = 0;
        StringBuffer error = new StringBuffer();
        List<StockSecurity> smallPlatestockSecurities = securityService.findByCodeStartIndex("0");
        List<StockSecurity> gemStockSecurities = securityService.findByCodeStartIndex("3");
        List<StockSecurity> stockSecurities = new ArrayList<StockSecurity>();
        stockSecurities.addAll(gemStockSecurities);
        stockSecurities.addAll(smallPlatestockSecurities);
        for (StockSecurity stockSecurity : stockSecurities){
            List<StockPool> stockPools = findByCode(stockSecurity.getInternalSecurityId());
            try {
                if (stockPools.size()==0){
                    StockPool stockPool = new StockPool(stockSecurity.getInternalSecurityId(),stockSecurity.getName(),null);
                    if (stockSecurity.getInternalSecurityId().startsWith("0")){
                        stockPool.setType("1");
                    }else {
                        stockPool.setType("2");
                    }
                    boolean bool = save(stockPool);
                    if (bool) {
                        insertNum++;
                    }else {
                        errorNum++;
                        error.append(stockSecurity.getInternalSecurityId()+";");
                    }
                    logger.info("stock pool insert:"+ JSON.toJSONString(stockPool)+" insertStatus: "+bool);
                    continue;
                }
                StockPool stockPool = stockPools.get(0);
                if (!(StringUtils.equals(stockSecurity.getInternalSecurityId(),stockPool.getStockCode())&&StringUtils.equals(stockSecurity.getName(),stockPool.getStockName()))){
                    stockPool.setStockCode(stockSecurity.getInternalSecurityId());
                    stockPool.setStockName(stockSecurity.getName());
                    boolean bool = update(stockPool.getId(),stockPool.getStockName(),stockPool.getStockCode(),stockPool.getCustomerId(),null);
                    if (stockSecurity.getInternalSecurityId().startsWith("0")){
                        stockPool.setType("1");
                    }else {
                        stockPool.setType("2");
                    }
                    if (bool) {
                        updateNum++;
                    }else {
                        errorNum++;
                        error.append(stockSecurity.getInternalSecurityId()+";");
                    }
                    logger.info("stock pool update:"+ JSON.toJSONString(stockPool)+" updateStatus: "+bool);
                    continue;
                }else {
                    keepNum++;
                }
            } catch (Exception ex){
                logger.info("stock pool update ERROR :"+ JSON.toJSONString(stockSecurity));
                errorNum++;
                error.append(stockSecurity.getInternalSecurityId()+";");
            }
        }
        if (error!=null&&error.toString().length()!=0) error.insert(0,"</br>更新失败的股票代码如下：");
        logger.info("检查更新了"+stockSecurities.size()+"条记录，其中中小板："+smallPlatestockSecurities.size()+
                " 条 创业板："+gemStockSecurities.size()+" 条。"
                +"增加数量："+insertNum+" 更新数量："+updateNum+"不更新："+keepNum+" 更新失败："+errorNum+error.toString());
        return "</br>检查更新了"+stockSecurities.size()+"条记录，其中中小板："+smallPlatestockSecurities.size()+
                " 条 创业板："+gemStockSecurities.size()
                +" 条</br>增加数量："+insertNum+";更新数量："+updateNum+";不更新："+keepNum+";更新失败："+errorNum+error.toString();
    }

}
