package com.icaopan.trade;

import com.icaopan.clearing.service.CheckLogService;
import com.icaopan.trade.model.CheckLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Controller
@RequestMapping("/checkPosition")
public class CheckPositionAction {

    @Autowired
    CheckLogService checkLogService;

    @RequestMapping("init")
    public ModelAndView initCheckLog() {

        List<CheckLog> checkLogLists = checkLogService.queryCheckLog();
        ModelAndView modelAndView = new ModelAndView();
        if (checkLogLists.size() != 0) {
            modelAndView.addObject("checkLogLists", checkLogLists);
        }
        modelAndView.setViewName("trade/check/checkPosition");
        return modelAndView;
    }

    /*@RequestMapping("/query")
    @ResponseBody
    public ModelAndView queryCheckLogs(){
        List<CheckLog> checkLists = checkLogService.queryCheckLog();
        ModelAndView modelAndView = new ModelAndView();
        if (checkLists.size() !=0){
            modelAndView.addObject("checkLists",checkLists);
        }
        modelAndView.addObject("channelName",channelName);
    }*/
}
