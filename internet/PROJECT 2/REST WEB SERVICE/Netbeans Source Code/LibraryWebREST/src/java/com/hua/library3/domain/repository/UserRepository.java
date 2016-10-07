/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hua.library3.domain.repository;

import com.hua.library3.domain.User;

public interface UserRepository {
    User getUserByUserIdPwd(String userId, String password);
}
