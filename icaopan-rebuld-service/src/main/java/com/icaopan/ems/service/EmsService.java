package com.icaopan.ems.service;

import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.web.vo.TdxFillItemVO;

import java.math.BigDecimal;
import java.util.List;

public interface EmsService {

    public void placement(ChannelPlacement channelPlacement);

    public void dockPlacementCallBack(byte[] json);

    public void dockFillCallBack(byte[] json);

    public void dockPositionCallBack(byte[] json);

    public void cancel(ChannelPlacement channelPlacement);

    public void dockFillByHand(ChannelPlacement placement, String reportNo,
                               BigDecimal quantity, BigDecimal price,boolean isForceFill);

	void channelPlacementCheck(byte[] json);

	void channelPositionCheck(byte[] json);

	List<TdxFillItemVO> getCheckPlacementList();
}
