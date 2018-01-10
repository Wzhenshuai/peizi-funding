package com.icaopan.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icaopan.customer.bean.CustomerBalance;
import com.icaopan.customer.model.CustomerBill;
import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.common.CommonAction;
import com.icaopan.customer.bean.CustomerBillParams;
import com.icaopan.customer.bean.CustomerParams;
import com.icaopan.customer.model.Customer;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.util.page.Page;

import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerAction extends CommonAction {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdminUserService adminUserService;
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String customer(){
		return "customer/customerList";
	}
	
	
	@RequestMapping(value = "/customerForCustomer", method = RequestMethod.GET)
	@RequiresUser
	public String customerForCustomer(HttpServletRequest requst){
		AdminUser adminUser=LoginRealm.getCurrentUser();

		Customer customer=customerService.getCustomerById(adminUser.getCustomerId());
		CustomerBalance customerBalance = customerService.getCustomerBalance(adminUser.getCustomerId());
		requst.setAttribute("customer", customer);
		requst.setAttribute("customerBalance", customerBalance);
		return "customer/customerListForCustomer";
	}
	
	/**
	 * 查找客户列表
	 * @param aoData
	 * @param customerParams
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public Page findList(@Param("aoData") String aoData,@Param("customerParams")CustomerParams customerParams){
		Page page=new Page(aoData);
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			customerParams.setId(adminUser.getCustomerId());
		}
		page=customerService.getCustomerByPage(page, customerParams);
		return page;
	}
	
	/**
	 * @Description 跳转到User页面
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String adminUser(){
		return "customer/userList";
	}
	
	/**
	 * @Description 跳转到User页面
	 */
	@RequestMapping(value = "/channelListForCustomer", method = RequestMethod.GET)
	public String channelListForCustomer(){
		return "customer/channelListForCustomer";
	}

	/**
	 * @Description 跳转到通道持仓监控页面
	 */
	@RequestMapping(value = "/channelPositionMonitor", method = RequestMethod.GET)
	public String channelPositionMonitor(HttpServletRequest request){
		AdminUser adminUser = LoginRealm.getCurrentUser();
		if (!adminUserService.isSuperAdmin(adminUser)) {
			request.setAttribute("admin", "true");
		}
		return "customer/channelPositionMonitor";
	}
	
	@RequestMapping(value = "/form")
	public String form(HttpServletRequest request,Integer id){
		if(id!=null){
			Customer customer=customerService.getCustomerById(id);
			request.setAttribute("customer", customer);
		}
		return "customer/customerForm";
	}
	
	@RequestMapping(value = "/getCustomerById")
	@ResponseBody
	@RequiresUser
	public Object getCustomerById(HttpServletRequest request,Integer id){
		Customer customer=null;
		if(id!=null){
			customer=customerService.getCustomerById(id);
		}
		return customer;
	}
	
	/**
	 * 新增或者修改客户
	 * @param request
	 * @param custormer
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult save(HttpServletRequest request,Customer custormer){
		try {
			if(custormer.getId()==null){
				customerService.saveCustomer(custormer);
			}else{
				customerService.updateCustomer(custormer);
			}
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/customerBill", method = RequestMethod.GET)
	@RequiresUser
	public String customerBill(HttpServletRequest request){
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			request.setAttribute("admin", "true");
		}

		return "customer/customerBillList";
	}
	
	/**
	 * 查找客户扣费账单
	 * @param aoData
	 * @param customerBillParams
	 * @return
	 */
	@RequestMapping(value = "/findBill", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public Page findBillList(@Param("aoData") String aoData,@Param("customerBillParams")CustomerBillParams customerBillParams){
		Page page=new Page(aoData);
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			customerBillParams.setCustomerId(adminUser.getCustomerId());
		}
		page=customerService.getCustomerBillByPage(page, customerBillParams);
		return page;
	}
	
	@RequestMapping(value = "/exportBill")
	@ResponseBody
	@RequiresUser
	public void exportBill(HttpServletResponse response,@Param("customerBillParams")CustomerBillParams customerBillParams){
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			customerBillParams.setCustomerId(adminUser.getCustomerId());
		}
		Page page=customerService.getCustomerBillByPage(null, customerBillParams);
		JSONArray data=JSONArray.fromObject(page.getAaData());
		com.icaopan.util.ExcelUtil.exportExcel("客户扣费账单", new String[]{"客户名称","交易通道","成交金额","扣除费用","操作类型","余额","操作时间"}, new String[]{"customerName","channelName","fillAmount","fee","operationType","balance","operationTimeStr"}, data,response, "yyyy-MM-dd HH:mm:ss");
	}

	@RequestMapping(value = "/customerBalance", method = RequestMethod.GET)
	public String findCustomerBalance(){
		return "customer/customerBalance";
	}

	@RequestMapping(value = "/queryCustomerBalance", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public Page queryCustomerBalance(@Param("aoData") String aoData,@Param("customerParams")CustomerParams customerParams){

		Page page=new Page(aoData);
		AdminUser adminUser=LoginRealm.getCurrentUser();
		if(!adminUserService.isSuperAdmin(adminUser)){
			customerParams.setId(adminUser.getCustomerId());
		}
		page=customerService.getCustomerBalanceByPage(page, customerParams);
		return page;

	}

	@RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public ResponseResult updateBalance(HttpServletRequest request, @Param("customerAmount")BigDecimal customerAmount, @Param("txtType")Integer txtType,  @Param("customerId")Integer customerId){
		AdminUser adminUser=LoginRealm.getCurrentUser();
		String billType = null;
		try {
			if (txtType == 0){
				customerAmount = customerAmount.abs();
				billType = "增加";
			}else if (txtType == 1){
				customerAmount = customerAmount.negate();
				billType = "减少";
			}
			customerService.updateBalance(customerId, customerAmount);

			CustomerBill record = new CustomerBill(billType,customerAmount,adminUser.getUserName(),new Date(),customerId);
			record.setFillAmount(customerAmount);
			customerService.saveCustomerBill( record);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "资金调整成功", null);
		} catch (Exception e) {
			logger.error("资金调整失败",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}

	}
}
