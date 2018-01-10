package com.icaopan.web.common;

import com.icaopan.log.LogUtil;
import com.icaopan.user.model.User;
import com.icaopan.web.user.realm.LoginRealm;

import org.slf4j.Logger;

public class BaseAction {

    protected Logger logger = LogUtil.getLogger(getClass());

    protected User getUser() {
    	User user=null;
    	try {
    		user= LoginRealm.getCurrentUser();
		} catch (Exception e) {
		}
    	return user;
    }
}
