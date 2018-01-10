package com.icaopan.risk.service;

import com.icaopan.risk.bean.*;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.TdxFillItemVO;
import org.apache.commons.codec.EncoderException;

import java.io.IOException;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/11.
 * 个人风控管理接口
 */
public interface PrivateRiskCtrlService {

    Page findPrivateRiskCtrlByPage(Page page, InfoParam param);

    TdxBrokerBase getBrokerBaseInfoById(String id);

    TdxPrivateUser getTdxPrivateUserInfoById(Integer id);

    List<PrivateCustomer> getPrivateCustomers();

    List<TdxServerInfo> getServerInfoByBelongId(String belongId);

    List<TdxYyb> getYYBByBelongId(String belongId);

    List<TdxBrokerBase> getAllBrokerBaseInfo();

    TdxServerInfo getBaseServerInfoById(Integer Id);

    TdxYyb getYybById(Integer Id);

    boolean addUserRiskCtrl(TdxPrivateUser user);

    boolean updatePrivateUserRiskCtrl(TdxPrivateUser user);

    void updatePassWord(PassWordParam param);

    String queryInfoByUserIdAndType(Integer userId,String type);

	List<TdxFillItemVO> queryTdxFillSummary();

	String openPosition(RiskCtrlPlacement placement);

	String getLimitDownPrice(String code) throws IOException, EncoderException;

	String manualUpdateBroker();

	String verityRiskInfo(String accountNo,String tradeAccount,String txPassWord,String jYPassWord,String serverId,String yybId);

}
