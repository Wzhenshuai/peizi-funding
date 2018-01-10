package com.icaopan.stock;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.common.util.Constants;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.bean.StockSecurityParams;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.model.StockUser;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.stock.service.StockUserWhitelistService;
import com.icaopan.sys.model.Dict;
import com.icaopan.sys.util.DictUtils;
import com.icaopan.util.DateUtils;
import com.icaopan.util.WebUtil;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.MonitorStockMarketDataVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/9.
 */
@Controller
@RequestMapping("/stock")
public class SecurityAction {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private MarketdataService marketdataService;
    @Autowired
    private StockUserBlacklistService blacklistService;
    @Autowired
    private StockUserWhitelistService whitelistService;
    
    @RequestMapping("/index")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public String index(){
        return "stock/security";
    }

    @RequestMapping("/add")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public String addStockSecurity(){
        return "stock/addStockSecurity";
    }

    @RequestMapping("/edit")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public String editStockSecurity(@RequestParam("id")String id,Model model){
        StockSecurity stockSecurity =  securityService.findStockSecurityById(id);
        model.addAttribute("stockSecurity",stockSecurity);
        return "stock/editSecurity";
    }

    @RequestMapping("/find")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public Page find(@Param("aoData") String aoData,@Param("exchangeCode")String exchangeCode,@Param("name")String name,@Param("code")String code,@Param("suspensionFlag")String suspensionFlag){
        Page page = new Page(aoData);
        StockSecurityParams params ;
        Boolean bool = null;
        if (StringUtils.isBlank(name)){
            name = null;
        }
        if (StringUtils.isBlank(exchangeCode)){
            exchangeCode = null;
        }
        if (StringUtils.isBlank(code)){
            code=null;
        }
        if(StringUtils.equals(suspensionFlag,"1")){
            bool = true;
        }else if(StringUtils.equals(suspensionFlag,"0")){
            bool = false;
        }
        params = new StockSecurityParams(exchangeCode,code,name,bool);
        page = securityService.findStockSecurityByPage(page, params);
        return page;
    }



    @RequestMapping("/get")
    @ResponseBody
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public StockSecurity edit(HttpServletRequest request,Model model,@Param("id")String internalSecurityId){
        StockSecurity stockSecurity =  securityService.findStockSecurityById(internalSecurityId);
        return stockSecurity;
    }

    @RequestMapping("/getStockName")
    @ResponseBody
    @RequiresUser
    public String getStockName(@Param("stockCode")String stockCode){
        if (StringUtils.isBlank(stockCode)) return null;
        String stockName = securityService.queryNameByCode(stockCode);
        return stockName;
    }

    /**
     * 根据用户ID查询该用户的黑白名单股票数据信息
     * */
    @RequestMapping("/getStock")
    @ResponseBody
    @RequiresUser
    public List<StockUser> getStockByUserId(@Param("type")String type,@Param("userId")Integer userId){
        if (Constants.BLACKLIST.equals(type)){
            return blacklistService.queryByUserId(userId);
        }else if (Constants.WHITELIST.equals(type)){
            return whitelistService.queryByUserId(userId);
        }
        return null;
    }

    @RequestMapping("/update")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public ResponseResult update(StockSecurity stockSecurity){
        boolean bool = securityService.update(stockSecurity.getInternalSecurityId(), stockSecurity.getName(), stockSecurity.getFirstLetter(),stockSecurity.getSuspensionFlag());
        if (bool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新信息成功", null);
        }else {
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新信息失败，请重试", null);
        }
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public ResponseResult save(@RequestParam("internalSecurityId") String internalSecurityId,
                               @RequestParam("exchangeCode") String exchangeCode,
                               @RequestParam("code") String code,
                               @RequestParam("name") String name,
                               @RequestParam("firstLetter") String firstLetter,
                               @RequestParam("issueDate") String issueDate,
                               @RequestParam("suspensionFlag") String suspensionFlag) throws ParseException {
        Date strtodate = DateUtils.parseDate(issueDate, "yyyy-MM-dd");
        StockSecurity stockSecurity = new StockSecurity(internalSecurityId,exchangeCode,code,name,strtodate,Boolean.valueOf(suspensionFlag),firstLetter);
        boolean bool = securityService.saveStockSecurity(stockSecurity);
        if (bool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "保存成功", null);
        }else{
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "保存失败，请重试", null);
        }
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public ResponseResult verify(@RequestParam("name")String name){
        if (name != null && name.length()!=0 && securityService.containName(name)){
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "股票名已存在", null);
        }
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
    }
    
    @ResponseBody
    @RequestMapping(value = "/monitorOneStockMarketData")
    public Object monitorOneStockMarketData(String stockCode){
    	MarketDataSnapshot shot=marketdataService.getBySymbol(stockCode);
    	return shot;
    }
    
    @RequestMapping(value = "/monitorMarketData")
    public String monitorMarketData(HttpServletRequest request){
    	List<MonitorStockMarketDataVO> list=new ArrayList<MonitorStockMarketDataVO>();
    	List<Dict> dictList=DictUtils.getDictList("monitor_marketdata");
    	String[] stockPoll=new String[]{"600022","601398","601318"};
    	for (Dict dict : dictList) {
			String serverUrl=dict.getValue();
			String lable=dict.getLabel();
			MonitorStockMarketDataVO vo=new MonitorStockMarketDataVO();
			vo.setServerName(lable);
			for(String stock:stockPoll){
				try {
					String str=WebUtil.readContentFromGet(serverUrl+"?stockCode="+stock);
					str=new String(str.getBytes(), "utf-8");
					JSONObject object=JSONObject.parseObject(str);
					MarketDataSnapshot shot=JSONObject.toJavaObject(object, MarketDataSnapshot.class);
					if(shot!=null){
						boolean flag=shot.isSuspensionFlag();
						if(flag){
							continue;
						}else{
							long time=shot.getTime();
							vo.setStockCode(stock);
							vo.setStockName(shot.getStockName());
							vo.setUpdateTime(DateUtils.formatDate(new Date(time), "yyyy-MM-dd HH:mm:ss"));
							//判断更新时间是否超时
							//1.先判断是否是在交易时间段 2.判断是否是否超时
							if(DateUtils.isTimeInTrade()){
								long minus=new Date().getTime()-time;
								if(minus>30*1000){
									vo.setOk(false);
								}
							}
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					vo.setOk(false);
					break;
				}
			}
			list.add(vo);
		}
    	request.getSession().setAttribute("dataList", list);
    	return "/stock/stockMonitor";
    }
    
    @ResponseBody
    @RequestMapping(value = "/getAllByExchange")
    public Object getAllByExchange(String exchange){
    	return securityService.findAllStockSecurityByExchange(exchange);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getAllStockCode")
    public Object getAllStockCode(){
    	return securityService.findAllStockCode();
//    	return StringUtils.join(list.toArray(), ",");
    }
}
