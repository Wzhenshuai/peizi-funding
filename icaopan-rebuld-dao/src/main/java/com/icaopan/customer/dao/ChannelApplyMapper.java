package com.icaopan.customer.dao;

import com.icaopan.customer.model.ChannelApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public interface ChannelApplyMapper {

    boolean save(ChannelApply channelApply);

    List<ChannelApply> queryByStatus(@Param("status") String status, @Param("customerId") Integer customerId);

    boolean updateStatus(@Param("status") String status, @Param("adminNotes")String adminNotes, @Param("applyId")Integer applyId );

}
