package com.hua.library.service.impl;

import com.hua.library.domain.User;
import com.hua.library.domain.repository.UserRepository;
import com.hua.library.service.UserService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserByUserId(String userId) {
        return userRepository.getUserByUserId(userId);
    }

    @Override
    public void addUser(User user) throws SQLException {
        userRepository.addUser(user);
    }

    @Override
    public User getUserByUserIdPwd(String userId, String password) {
        return userRepository.getUserByUserIdPwd(userId, password);
    }

   @Override
    public void modifyUser(User user) throws SQLException {
        userRepository.modifyUser(user);
    }
    
    @Override
    public void deleteUser(User user) throws SQLException {
        userRepository.deleteUser(user);
    }

    @Override
    public User getStudentByUserId(String userId) {
        return userRepository.getStudentByUserId(userId);
    }
    
}
