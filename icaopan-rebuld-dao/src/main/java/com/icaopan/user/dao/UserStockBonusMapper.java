package com.icaopan.user.dao;

import com.icaopan.user.bean.StockBonusParams;
import com.icaopan.user.model.UserStockBonus;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStockBonusMapper {

    /**
     * 保存红利分配数据
     *
     * @param user
     * @return boolean
     */
    boolean insert(UserStockBonus bonus);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    UserStockBonus findById(Integer id);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @param remark
     */
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status, @Param("remark") String remark);

    /**
     * 查找待处理的记录
     *
     * @return
     */
    List<UserStockBonus> findAllToDeal();

    /**
     * 条件查询
     *
     * @param page
     * @param params
     * @return
     */
    List<UserStockBonus> findByPage(@Param("page") Page<UserStockBonus> page, @Param("params") StockBonusParams params);

}
