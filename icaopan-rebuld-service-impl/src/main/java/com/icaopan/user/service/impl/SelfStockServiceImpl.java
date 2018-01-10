package com.icaopan.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.user.dao.SelfStockMapper;
import com.icaopan.user.model.SelfStock;
import com.icaopan.user.service.SelfStockService;

@Service("selfStockService")
public class SelfStockServiceImpl implements SelfStockService {

	@Autowired
	private SelfStockMapper selfStockMapper;
	
	@Override
	public List<SelfStock> selectByUserid(Integer userid) {
		return selfStockMapper.selectByUserid(userid);
	}

	@Override
	public void save(SelfStock selfStock) {
		//判断是否已经存在
		SelfStock ss=selectByStockCode(Integer.valueOf(selfStock.getUserid()), selfStock.getStockcode());
		if(ss!=null){
			return;
		}
		selfStockMapper.save(selfStock);
	}

	@Override
	public void deleteByStockCode(Integer userid, String stockCode) {
		selfStockMapper.deleteByStockCode(userid, stockCode);
	}

	@Override
	public void topByStockCode(Integer userid, String stockCode) {
		selfStockMapper.topByStockCode(userid, stockCode);
	}

	@Override
	public SelfStock selectByStockCode(Integer userid, String stockCode) {
		return selfStockMapper.selectByStockCode(userid, stockCode);
	}

	@Override
	public void deleteByUserId(Integer userId) {
		selfStockMapper.deleteByUserId(userId);
	}

}
