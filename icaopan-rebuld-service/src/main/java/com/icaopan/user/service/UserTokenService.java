package com.icaopan.user.service;

import com.icaopan.user.model.User;

public interface UserTokenService {

    User getUserByToken(String token);

    String makeToken(User user);

    void cleanToken(String token);
}
