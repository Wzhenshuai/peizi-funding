package com.icaopan.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.api.service.ApiService;
import com.icaopan.common.util.Constants;
import com.icaopan.common.util.MD5;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.FillService;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.user.model.SelfStock;
import com.icaopan.user.model.User;
import com.icaopan.user.service.SelfStockService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.user.service.UserTokenService;
import com.icaopan.web.vo.AccountVO;
import com.icaopan.web.vo.FillVO;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.PlacementVO;
import com.icaopan.web.vo.SecurityPositionVO;
import com.icaopan.web.vo.StockDetailVO;
import com.icaopan.web.vo.StockVO;


@Service("apiService")
public class ApiServiceImpl implements ApiService {

	@Autowired
	private UserService userService;
	@Autowired
	private UserTokenService userTokenService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private PlacementService placementService;
	@Autowired
	private FillService fillService;
	@Autowired
	private UserPositionService userPositionService;
	@Autowired
	private SelfStockService selfStockService;
	@Override
	public Map<String, Object> login(String system, String chanelId,
			String userName, String passWord, String vCode,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user=userService.findUser(userName, MD5.MD5Encrypt(passWord));
		if(user==null){
			result.put(Constants.RESCODE, Constants.ERROR);
			result.put(Constants.RESULT, "用户名或者密码不正确");
		}else{
			// 更新登录次数
            userService.updateLoginCountAndLastTime(user);
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userName", user.getUserName());
			userMap.put("loginCount", 1);
			userMap.put("realName", user.getRealName());
			userMap.put("userId", user.getId());
			userMap.put("accessToken", userTokenService.makeToken(user));
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, userMap);
		}
		return result;
	}

	@Override
	public Map<String, Object> autoLogin(String system, String chanelId,
			String userName, String passWord, String vCode,
			HttpServletRequest request) {
		return login(system, chanelId, userName, passWord, vCode, request);
	}

	@Override
	public Map<String, Object> logOut(String token, String chanelId) {
		Map<String, Object> result = new HashMap<String, Object>();
		userTokenService.cleanToken(token);
		result.put(Constants.RESCODE, Constants.SUCCESS);
		result.put(Constants.RESULT, Constants.LOGOUT_SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> isLogin(String token) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user=userTokenService.getUserByToken(token);
		if (user!=null) {
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, Constants.LOGIN_SUCCESS);
		} else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}
		return result;
	}

	@Override
	public Map<String, Object> getAllSecurity() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<StockSecurity> list=securityService.findAllStockSecurity();
		List<StockVO> listStock=new ArrayList<StockVO>();
		for(int i=0;i<list.size();i++){
			StockSecurity ss=list.get(i);
			String stockCode=ss.getCode();
			String stockName=ss.getName();
			StockVO vo=new StockVO();
			String simple=ss.getFirstLetter();
			if(org.apache.commons.lang.StringUtils.isNotBlank(simple)){
				simple=simple.toUpperCase();
			}
			vo.setValue(stockCode+" "+stockName+" "+simple);
			vo.setData(stockCode);
			vo.setShortNameCN(ss.getFirstLetter());
			vo.setName(stockName);
			listStock.add(vo);
		}
		result.put(Constants.RESCODE, Constants.SUCCESS);
		result.put(Constants.RESULT, listStock);
		return result;
	}

	/**
	 * 增量获取股票信息
	 * 
	 * @param issueDate
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllSecurityModelByCreation(String issueDate) {
		Map<String, Object> result = new HashMap<String, Object>();

		Date issueDateByTime = null;
		if (!"".equals(issueDate)) {
			issueDateByTime = new Date(Long.parseLong(issueDate));
		}
		// 获取股票信息
		List<StockSecurity> list = securityService.findAllStockSecurity();
		// 定义输出格式
		Map<String, Object> result_data_map = new HashMap<String, Object>();
		if (list.isEmpty()) {
			result.put(Constants.RESCODE, Constants.ERROR);
			result.put(Constants.MESSAGE, "股票行情暂时为空，请稍候尝试刷新操作!");
		} else {
			// 设置初始时间
			Date LastDate = new Date();
			for (StockSecurity model : list) {
				if (model.getIssueDate() != null) {
					LastDate = model.getIssueDate();
					break;
				}
			}
			// 取出最后更新时间
			for (StockSecurity model : list) {
				if (model.getIssueDate() != null) {
					if (model.getIssueDate().after(LastDate)) {
						LastDate = model.getIssueDate();
					}
				}
			}
			result_data_map.put("updateTime", LastDate);
			if (issueDateByTime == null) {
				List<StockVO> listStock = new ArrayList<StockVO>();
				for (int i = 0; i < list.size(); i++) {
					StockSecurity sm = list.get(i);
					String stockCode = sm.getCode();
					String stockName = sm.getName();
					StockVO sb = new StockVO();
					sb.setData(stockCode);
					sb.setName(stockName);
					sb.setShortNameCN(sm.getFirstLetter());
					sb.setStockStatus(Constants.NORMAL);
					sb.setExChangeCode(sm.getExchangeCode());
					// 停牌
					if (sm.isSuspension()) {
						sb.setStockStatus(Constants.SUSPENSION);
					}
					listStock.add(sb);
				}
				result_data_map.put("data", listStock);
				result.put(Constants.RESCODE, Constants.SUCCESS);
				result.put(Constants.RESULT, result_data_map);
			} else {
				List<StockVO> listStock = new ArrayList<StockVO>();
				for (int i = 0; i < list.size(); i++) {
					StockSecurity sm = list.get(i);
					if (sm != null) {
						if (sm.getIssueDate() == null) {
							String stockCode = sm.getCode();
							String stockName = sm.getName();
							StockVO sb = new StockVO();
							sb.setData(stockCode);
							sb.setName(stockName);
							sb.setShortNameCN(sm.getFirstLetter());
							sb.setStockStatus(Constants.NORMAL);
							sb.setExChangeCode(sm.getExchangeCode());
							// 停牌
							if (sm.isSuspension()) {
								sb.setStockStatus(Constants.SUSPENSION);
							}
							listStock.add(sb);
						} else {
							if (issueDateByTime.before(sm.getIssueDate())) {
								String stockCode = sm.getCode();
								String stockName = sm.getName();
								StockVO sb = new StockVO();
								sb.setData(stockCode);
								sb.setName(stockName);
								sb.setShortNameCN(sm.getFirstLetter());
								sb.setStockStatus(Constants.NORMAL);
								sb.setExChangeCode(sm.getExchangeCode());
								// 停牌
								if (sm.isSuspension()) {
									sb.setStockStatus(Constants.SUSPENSION);
								}
								listStock.add(sb);
							}
						}
					}
				}
				result_data_map.put("data", listStock);
				result.put(Constants.RESCODE, Constants.SUCCESS);
				result.put(Constants.RESULT, result_data_map);
			}
		}
		return result;
	}
	
	@Override
	public Map<String, Object> queryMarketDataByCode(String securityCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		StockDetailVO model = securityService.queryStockDetail(securityCode);
		model.setDate(com.icaopan.util.DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		result.put(Constants.RESCODE, Constants.SUCCESS);
		result.put(Constants.RESULT, model);
		return result;
	}

	
	public Map<String, Object> queryAccount(String token) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			AccountVO account=userService.queryUserAccount(user);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, account);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryAccountAvailableBalance(String token) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			AccountVO account=userService.queryUserAccount(user);
			BigDecimal balance = account.getCashAvailableAmount();
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, balance);
		}
		return result;
	}

	@Override
	public Map<String, Object> doPlacementStockTrade(String token,
			String stockCode, BigDecimal quantity, int sellorbuy,
			BigDecimal price) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			try {
				if (!userService.isNormal(user)) {
					result.put(Constants.RESCODE, Constants.ERROR);
					result.put(Constants.RESULT, "用户状态异常，不允许交易");
		        }else{
		        	Placement placement=new Placement();
					placement.setUserId(user.getId());
					placement.setSide(TradeSide.getByCode(sellorbuy+""));
					placement.setSecurityCode(stockCode);
					placement.setPrice(price);
					placement.setQuantity(quantity);
					placement.setAmount(placement.getPrice().multiply(placement.getQuantity()));
					placementService.placement(placement);
					result.put(Constants.RESCODE, Constants.SUCCESS);
					result.put(Constants.RESULT, Constants.PLACEMENT_SUCCESS);
		        }
			} catch (Exception e) {
				result.put(Constants.RESCODE, Constants.ERROR);
				result.put(Constants.RESULT, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> cancelPlacement(String token,
											   String placementId) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			if (StringUtils.isEmpty(placementId)) {
				result.put(Constants.RESCODE, Constants.ERROR);
				result.put(Constants.RESULT, Constants.PLACEMENT_TO);
			} else {
				List<String> list = new ArrayList<String>();
				String[] strs = placementId.split(",");
				for (int i = 0; i < strs.length; i++) {
					String str = strs[i];
					if (StringUtils.isNoneBlank(str)) {
						list.add(str);
					}
				}
				try {
					placementService.cancelPlacement(Integer.valueOf(list.get(0)));
					result.put(Constants.RESCODE, Constants.SUCCESS);
					result.put(Constants.RESULT, "撤单委托已提交");
				} catch (Exception e) {
					result.put(Constants.RESCODE, Constants.ERROR);
					result.put(Constants.RESULT, e.getMessage());
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> queryPlacementCurrentDayList(String token,
															String tradeType) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			com.icaopan.web.vo.PageBean<PlacementVO> page=placementService.queryCurrentDayPlacementByPage(user, 1, 10000);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, page.getDataList());
		}
		return result;
	}
	
	@Override
    public Map<String, Object> queryCurrentDayPlacementNotEnd(String token) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			List<PlacementVO> list=placementService.queryCurrentDayPlacementNotEnd(user);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, list);
		}
		return result;
    }
	
	@Override
	public Map<String, Object> queryPlacementHistoryByPage(String token,
			String pStatusList, String stockCode, String tradeType,
			String startDate, String endDate,Integer page,Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESCODE, Constants.ERROR);
		result.put(Constants.RESULT, "");
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			PlacementHistoryParams params=new PlacementHistoryParams();
			params.setUserId(user.getId());
			params.setStatus(pStatusList);
			params.setSecurityCode(stockCode);
			if(StringUtils.isNoneEmpty(tradeType)){
				params.setSide(TradeSide.getByCode(tradeType).getName());
			}
			params.setStartTime(startDate);
			params.setEndTime(endDate);
			PageBean<PlacementVO> pageBean =placementService.queryPlacementHistoryByPage(params, page, pageSize);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, pageBean);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryFillCurrentDayList(String token) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			PageBean<FillVO> pageBean=fillService.queryFillByPage(user, 1, 10000);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, pageBean.getDataList());
		}
		return result;
	}

	@Override
	public Map<String, Object> queryFillHistoryByPage(String token,
			String stockCode, String tradeType, String startDate,
			String endDate, Integer page, Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			FillHistoryParams fillHistoryParams=new FillHistoryParams();
			fillHistoryParams.setUserId(user.getId());
			fillHistoryParams.setSecurityCode(stockCode);
			if(StringUtils.isNoneEmpty(tradeType)){
				fillHistoryParams.setSide(TradeSide.getByCode(tradeType).getName());
			}
			fillHistoryParams.setStartTime(startDate);
			fillHistoryParams.setEndTime(endDate);
			PageBean<FillVO> pageBean=fillService.queryFillHistoryByPage(fillHistoryParams, page, pageSize);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, pageBean);
		}
		return result;
	}
	
	@Override
	public Map<String, Object> queryFillByPage(String token, Integer page, Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			PageBean<FillVO> pageBean=fillService.queryFillByPage(user, page, pageSize);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, pageBean);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryHoldStockList(String token) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			List<SecurityPositionVO> list=userPositionService.findAllPosition(user);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, list);
			result.put(Constants.FREQUENCY, 5);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryHoldStockListByPage(String token, int page, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);
		if (user != null) {
			PageBean<SecurityPositionVO> pageBean=userPositionService.findUserPositionByPage(user, page, pageSize);
			result.put(Constants.FREQUENCY, 5);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, pageBean);
		} else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryHoldStockByStockCode(String token, String stockCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user == null) {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		} else {
			BigDecimal amount=userPositionService.findPositionAvailable(user, stockCode);
			SecurityPositionVO vo=new SecurityPositionVO();
			vo.setAvailableAmount(amount);
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, vo);
			result.put(Constants.FREQUENCY, 5);
		}
		return result;
	}
	
	@Override
	public Map<String, Object> makeSuggestions(String token, String title,
			String content) {
		return null;
	}

	@Override
	public Map<String, Object> getOptionalUnit(String token) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		if (user != null) {
			// 过滤数据，防止冗余字段返回造成安全问题
			List<SelfStock> resultList = selfStockService.selectByUserid(user.getId());
			result.put(Constants.RESCODE, Constants.SUCCESS);
			result.put(Constants.RESULT, resultList);
		} else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}

		return result;
	}

	@Override
	public Map<String, Object> saveOptionalUnit(String token, String stockCodes) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userTokenService.getUserByToken(token);
		List<String> suscessArray = new ArrayList<String>();
		List<String> errorRepeatArray = new ArrayList<String>();
		List<String> errorNoResultArray = new ArrayList<String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (user != null) {
			// 判断当前调用接口是否传出来数据
			if (StringUtils.isNotBlank(stockCodes)) {
				List<String> noRepeatStockCodeArr = new ArrayList<String>();
				String[] stockCodeArr = stockCodes.split(",");
				// 判断去重复
				for (String stockCode : stockCodeArr) {
					noRepeatStockCodeArr.add(stockCode);
				}

				Collections.reverse(noRepeatStockCodeArr);

				// 将非重复的数据添加到数据库
				for (String stockCode : noRepeatStockCodeArr) {
					// 添加search方法里精准匹配
					StockSecurity stock=securityService.findByNameAndCode(null, SecurityUtil.traseStockCodeBySelfStockCode(stockCode));
					if (stock!=null) {
						// 判断当前添加的用户下的自选股是否已经存在 对应的用户（和 组下 - 暂未实现“组”概念）
						SelfStock selfStock_stockCode = selfStockService.selectByStockCode(user.getId(),
								stockCode);
						if (selfStock_stockCode != null) {
							selfStockService.deleteByStockCode(user.getId(), stockCode);
							errorRepeatArray.add(stockCode);
						} else {
							suscessArray.add(stockCode);
						}
						// 添加自选股
						SelfStock selfStock = new SelfStock();
						selfStock.setUserid(user.getId()+"");
						selfStock.setStockcode(stockCode);
						selfStock.setStockname(stock.getName());
						selfStockService.save(selfStock);
					} else {
						errorNoResultArray.add(stockCode);
					}
				}
				result.put(Constants.RESCODE, Constants.SUCCESS);
				resultMap.put("suscessArray", suscessArray);
				resultMap.put("errorRepeatArray", errorRepeatArray);
				resultMap.put("errorNoResultArray", errorNoResultArray);
				result.put(Constants.RESULT, resultMap);
			} else {
				result.put(Constants.RESCODE, Constants.ERROR);
				result.put(Constants.RESULT, "您未选择添加的自选股");
			}
		} else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}
		return result;
	}

	@Override
	public Map<String, Object> delOptionalUnit(String token, String stockCodes) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);

		if (user != null) {
			// 判断当前调用接口是否传出来数据
			if (StringUtils.isNotBlank(stockCodes)) {
				String[] stockCodeArr = stockCodes.split(",");
				for (String stockCode : stockCodeArr) {
					stockCode=stockCode.substring(0,6);
					selfStockService.deleteByStockCode(user.getId(), stockCode);
				}
				result.put(Constants.RESCODE, Constants.SUCCESS);
				result.put(Constants.RESULT, "删除自选股成功");
			} else {
				result.put(Constants.RESCODE, Constants.ERROR);
				result.put(Constants.RESULT, "您未选择删除的自选股");
			}
		} else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}

		return result;
	}

	@Override
	public Map<String, Object> topOptionalUnit(String token, String stockCodes) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userTokenService.getUserByToken(token);

		if (user != null) {
			// 判断当前调用接口是否传出来数据
			if (StringUtils.isNotBlank(stockCodes)) {
				String[] stockCodeArr = stockCodes.split(",");
				for (String stockCode : stockCodeArr) {
					selfStockService.topByStockCode(user.getId(), stockCode);
				}
				result.put(Constants.RESCODE, Constants.SUCCESS);
				result.put(Constants.RESULT, "自选股置顶成功");
			} else {
				result.put(Constants.RESCODE, Constants.ERROR);
				result.put(Constants.RESULT, "您未选择置顶的自选股");
			}
		} else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}

		return result;
	}

	@Override
	public Map<String, Object> saveOptionalUnitOrder(String token,
			String stockCodes) {
		Map<String,Object> result = new HashMap<String,Object>();
		User user = userTokenService.getUserByToken(token);
		
		List<String> suscessArray = new ArrayList<String>();
		List<String> errorNoResultArray = new ArrayList<String>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(user != null){
			//判断当前调用接口是否传出来数据
			if(StringUtils.isNotBlank(stockCodes)){
				List<String> noRepeatStockCodeArr = new ArrayList<String>();
				String [] stockCodeArr = stockCodes.split(",");
				for (String stockCode : stockCodeArr){
					stockCode=SecurityUtil.traseToSelfStockCode(stockCode);
					noRepeatStockCodeArr.add(stockCode);
				}
				Collections.reverse(noRepeatStockCodeArr);
				//删除当前用户的所有的股票信息
				selfStockService.deleteByUserId(user.getId());
				//将非重复的数据添加到数据库
				for (String stockCode : noRepeatStockCodeArr){
					//添加search方法里精准匹配
					StockSecurity stock=securityService.findByNameAndCode(null, SecurityUtil.traseStockCodeBySelfStockCode(stockCode));
					if(stock!=null){
						suscessArray.add(stockCode);
						//添加自选股
						SelfStock selfStock = new SelfStock();
						selfStock.setUserid(String.valueOf(user.getId()));
						selfStock.setStockcode(stockCode);
						selfStock.setStockname(stock.getName());
						selfStockService.save(selfStock);
					}else{
						errorNoResultArray.add(stockCode);
					}
				}
				result.put(Constants.RESCODE, Constants.SUCCESS);
				resultMap.put("suscessArray", suscessArray);
				resultMap.put("errorNoResultArray", errorNoResultArray);
				result.put(Constants.RESULT, resultMap);
			}else {
				result.put(Constants.RESCODE, Constants.ERROR);
				result.put(Constants.RESULT, "您未选择添加的自选股");
			}
		}else {
			result.put(Constants.RESCODE, Constants.LOGIN);
			result.put(Constants.RESULT, Constants.LOGIN_DESC);
		}
		return result;
	}

	@Override
	public Map<String, Object> saveDeviceInfo(String token, String chanelId,
			String deviceType, String deviceVersion, String deviceModel,
			String resolution, String applicationVersion) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.RESCODE, Constants.SUCCESS);
		result.put(Constants.RESULT, "设备信息收集成功");
		return result;
	}

}
