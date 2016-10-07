package com.hua.library.service;

import com.hua.library.domain.User;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List <User> getAllUsers();
    User getUserByUserId(String userId);
    User getStudentByUserId(String userId);
    User getUserByUserIdPwd(String userId, String password);
    void addUser(User user) throws SQLException;  
    void modifyUser(User user) throws SQLException;  
    void deleteUser(User user) throws SQLException;
}