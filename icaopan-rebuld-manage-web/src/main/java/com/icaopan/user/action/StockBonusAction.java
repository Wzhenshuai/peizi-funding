package com.icaopan.user.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icaopan.admin.other.ResponseResult;
import com.icaopan.common.CommonAction;
import com.icaopan.enums.enumBean.StockBonusStatus;
import com.icaopan.user.bean.StockBonusParams;
import com.icaopan.user.bean.StockBonusScheme;
import com.icaopan.user.model.UserStockBonus;
import com.icaopan.user.service.StockBonusService;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;


@Controller
@RequestMapping("/stockBonus")
public class StockBonusAction extends CommonAction{

	@Autowired
	private StockBonusService stockBonusService;
	
	
	@RequestMapping("/stockBonusList")
	public String bonusList(){
		return "user/stockBonusList";
	}
	
	@RequestMapping("/find")
    @RequiresUser
    @ResponseBody
	public Page<UserStockBonus> find(@Param("aoData") String aoData,@Param("securityCode")String securityCode,@Param("status")String status){
		Page<UserStockBonus> page = new Page<UserStockBonus>(aoData);
        StockBonusParams params=new StockBonusParams();
        params.setSecurityCode(securityCode);
        Integer _status=StockBonusStatus.Wait.getNum();
        if(!StringUtils.isBlank(status)){
        	_status=Integer.valueOf(status);
        	params.setStatusList(Arrays.asList(_status));
        }
        page=stockBonusService.findStockBonusByPage(page, params);
        return page;
	}
	
	@RequestMapping("/makeBonusInput")
	public String makeBonusInput(){
		return "user/makeBonusInput";
	}
	
	
	@RequestMapping("/makeBonus")
    @RequiresUser
    @ResponseBody
	public Object makeBonus(@Param("scheme")StockBonusScheme scheme){
		try {
			if(scheme.getDistributeStockAmount().compareTo(BigDecimal.ZERO)<0||scheme.getBonusProfit().compareTo(BigDecimal.ZERO)<0||scheme.getDonationStockAmount().compareTo(BigDecimal.ZERO)<0){
				return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "参数不合法", null);
			}
			stockBonusService.makeBonus(scheme);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "处理成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	@RequestMapping("/doBonusAdjust")
    @RequiresUser
    @ResponseBody
	public Object doBonusAdjust(Integer id){
		try {
			stockBonusService.doBonusAdjust(id);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "处理成功", null);
		} catch (Exception e) {
			logger.error("", e);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL,getErrorMsg(e), null);
		}
	}
	
	@RequestMapping("/updateUserStockBonusToInvalid")
    @RequiresUser
    @ResponseBody
	public Object updateUserStockBonusToInvalid(){
		try {
			stockBonusService.updateUserStockBonusToInvalid();
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "处理成功", null);
		} catch (Exception e) {
			logger.error("", e);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL,getErrorMsg(e), null);
		}
	}
	
	@RequestMapping(value = "/uploadBonusFile")  
	@ResponseBody
    public Object uploadBonusFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) { 
		if(!file.isEmpty()){
			try {
				InputStream is=file.getInputStream();
				BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				String line=null;
				List<StockBonusScheme> list=new ArrayList<StockBonusScheme>();
				while((line=reader.readLine())!=null){
					String[] str=line.split(",");
					if(str.length!=4){
						return "请检测输入格式是否有误";
					}
					StockBonusScheme scheme=new StockBonusScheme(str[0],new BigDecimal(str[1]),new BigDecimal(str[2]),new BigDecimal(str[3]));
					list.add(scheme);
				}
				stockBonusService.makeBonusBatch(list);
				return "导入成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "导入失败";
			}
		}
		return "导入失败";
	}
	
	@RequestMapping(value = "/autoGetScheme")  
	public String autoGetScheme(HttpServletRequest request,String tradeDate){
		List<StockBonusScheme> list=new ArrayList<StockBonusScheme>();
		if(!StringUtils.isEmpty(tradeDate)){
			list=stockBonusService.getDistributeFromMarketData(tradeDate);
		}
		request.getSession().setAttribute("dataList", list);
		request.getSession().setAttribute("tradeDate",tradeDate);
		return "/user/viewStockBonusScheme";
	}
	
	@RequestMapping(value = "/importToICP")  
	@ResponseBody
	public Object importToICP(HttpServletRequest request,String tradeDate){
		try {
			List<StockBonusScheme> list=stockBonusService.getDistributeFromMarketData(tradeDate);
			stockBonusService.makeBonusBatch(list);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "处理成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL,getErrorMsg(e), null);
		}
	}
	
	@RequestMapping(value = "/downLoadTxt")  
	public void downLoadTxt(HttpServletRequest request,HttpServletResponse response,String tradeDate){
		
		OutputStream out = null;
		try { 
			out = response.getOutputStream();
			String sourceData="";
			List<StockBonusScheme> list=stockBonusService.getDistributeFromMarketData(tradeDate);
			for (StockBonusScheme stockBonusScheme : list) {
				sourceData+=stockBonusScheme.getSecurityCode()+","+stockBonusScheme.getBonusProfit()+","+stockBonusScheme.getDistributeStockAmount()+","+stockBonusScheme.getDonationStockAmount();
				sourceData+="\r\n";
			}
			String title="新红配";
			title=title+"-"+DateUtils.getDate();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(title+".txt", "UTF-8"));
			out.write(sourceData.getBytes());
		} catch (IOException e) {
			logger.error("",e);
		}finally{
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				logger.error("",e);
			}
		}
	}
}
