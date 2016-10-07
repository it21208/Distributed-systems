/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hua.library.domain.repository;

import com.hua.library.domain.User;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    List <User> getAllUsers();
    User getUserByUserId(String userId);
    User getStudentByUserId(String userId);
    User getUserByUserIdPwd(String userId, String password);
    void addUser(User user) throws SQLException;  
    void modifyUser(User user) throws SQLException;
    void deleteUser(User user) throws SQLException;
}
