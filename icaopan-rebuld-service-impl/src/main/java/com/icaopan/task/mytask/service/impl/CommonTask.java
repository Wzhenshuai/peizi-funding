package com.icaopan.task.mytask.service.impl;

import org.springframework.stereotype.Service;

import com.icaopan.log.LogUtil;
import com.icaopan.web.vo.PlacementCheck;

@Service
public class CommonTask extends LogUtil{
	public void clearPlacmentCheckMap(){
		PlacementCheck.clearMap();
    }
}
