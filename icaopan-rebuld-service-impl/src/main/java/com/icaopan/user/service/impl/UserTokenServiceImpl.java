package com.icaopan.user.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.icaopan.user.model.User;
import com.icaopan.user.service.UserTokenService;

@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {

	private static Map<String,User> tokenMap=new HashMap<String,User>();
	
	@Override
	public User getUserByToken(String token) {
		
		return tokenMap.get(token);
	}

	@Override
	public String makeToken(User user) {
		UUID uuid=UUID.randomUUID();
		String token=uuid.toString();
		tokenMap.put(token, user);
		return token;
	}

	@Override
	public void cleanToken(String token) {
		tokenMap.put(token, null);
	}


}
