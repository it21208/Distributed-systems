package com.hua.library3.service;

import com.hua.library3.domain.User;

public interface UserService {
    User getUserByUserIdPwd(String userId, String password);
}