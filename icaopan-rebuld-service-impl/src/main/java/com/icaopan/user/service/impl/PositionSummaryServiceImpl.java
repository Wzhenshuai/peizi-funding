package com.icaopan.user.service.impl;

import com.icaopan.user.dao.ChannelSecurityPositionMapper;
import com.icaopan.user.dao.UserSecurityPositionMapper;
import com.icaopan.user.service.PositionSummaryService;
import com.icaopan.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/17.
 */
@Service("positionSummaryService")
public class PositionSummaryServiceImpl implements PositionSummaryService {

    @Autowired
    UserSecurityPositionMapper userSecurityPositionMapper;

    @Autowired
    ChannelSecurityPositionMapper channelSecurityPositionMapper;

    @Override
    public Page findPositionSummaryByPage(Page page) {
        return page;
    }
}
