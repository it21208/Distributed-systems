package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.User;
import com.hua.library3.domain.repository.UserRepository;
import com.hua.library3.singleton.SingletonDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    private static final String FIELDS = 
        "userid, name, password, adt, email, telephone, am, roleId, onlinesubmit";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS = 
        "userid=?, name=?, password=?, adt=?, email=?, telephone=?, am=?, roleId=?, onlinesubmit=?";
    private static final String SQL_SELECT = 
        "select " + PK_FIELDS + ", roleName from vuser";    // select from view!!!!
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT + " where userid = ?";
    private static final String SQL_INSERT =
        "insert into user (" + FIELDS + ") values (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE =
        "update user set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID = 
        SQL_UPDATE + " where id = ?";
    private static final String SQL_DELETE_BY_ID =
        "delete from user where id = ?";
  
    private DataSource dataSource = SingletonDataSource.getDataSource();

    public UserRepositoryImpl() {}
    

    private User getUserByUserId(String userId) {
        User user = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserId(userId);
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAdt(rs.getString("adt"));
                user.setAm(rs.getString("am"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setRoleId(rs.getInt("roleId"));
                user.setRoleName(rs.getString("roleName"));
                user.setOnlinesubmit(rs.getBoolean("onlinesubmit"));
                System.out.println("User Found : " + user);
            } else {
                System.out.println("No user found with id = " + userId);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User getUserByUserIdPwd(String userId, String password) {
        User user = getUserByUserId(userId);
        if ((user != null) && (user.getPassword().equals(password)))
            return user;
        else
            return null;
    }

}