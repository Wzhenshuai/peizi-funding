package com.icaopan.risk.dao;

import com.icaopan.risk.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/11.
 * 个人风控管理接口类
 */
@Repository
public interface PrivateRiskCtrlMapper {

    /**
     * 根据条件查询个人风控用户信息
     * @param param
     * @return
     * */
    List<TdxPrivateUser> selectPrivateUsers(@Param("param") InfoParam param);

    /**
     * 查询个人风控customer info
     * */
    List<PrivateCustomer> selectPrivateCustomers();

    /**
     * 根据券商查询对应的服务器信息
     * @param belongId
     * @return
     * */
    List<TdxServerInfo> getServerInfoByBelongId(@Param("belongId") String belongId);

    /**
     * 根据主键获取券商基本信息
     * @param id
     * @return
     * */
    TdxBrokerBase getBrokerBaseInfoById(@Param("id") Integer id);

    /**
     * 根据所属券商编号检索对应的营业部信息
     * @param belongId
     * @return
     * */
    List<TdxYyb> getYYBByBelongId(@Param("belongId") String belongId);

    /**
     * 查询券商信息(券商编号，券商名字)
     * */
    List<TdxBrokerBase> getAllBrokerBaseInfo();

    /**
     * 根据主键获取基础服务器数据
     * @param Id
     * @return
     * */
    TdxServerInfo getBaseServerInfoById(@Param("id") Integer Id);

    /**
     * 根据主键查询营业部信息
     * @param Id
     * @return
     * */
    TdxYyb getYybById(@Param("id") Integer Id);

    /**
     * 添加个人风控信息
     * @param tdxPrivateUser
     * @return
     * */
    boolean addPrivateUser(TdxPrivateUser tdxPrivateUser);

    /**
     * 更新个人风控信息
     * @param tdxPrivateUser
     * @return
     * */
    boolean updatePrivateRiskCtrl(TdxPrivateUser tdxPrivateUser);

    /**
     * 更新密码
     * @param passWordParam
     * @return
     * */
    void updatePassWord(PassWordParam passWordParam);










}
