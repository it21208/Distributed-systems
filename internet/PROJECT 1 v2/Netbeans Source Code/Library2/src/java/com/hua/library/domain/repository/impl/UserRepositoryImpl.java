package com.hua.library.domain.repository.impl;

import com.hua.library.domain.User;
import com.hua.library.domain.repository.UserRepository;
import com.hua.library.singleton.SingletonDataSource;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
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
    
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserId(rs.getString("userid"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAdt(rs.getString("adt"));
                user.setAm(rs.getString("am"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setRoleId(rs.getInt("roleId"));
                user.setRoleName(rs.getString("roleName"));
                user.setOnlinesubmit(rs.getBoolean("onlinesubmit"));
                userList.add(user);
            }
        } catch (SQLException e) {
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
        return userList;    
    }

    @Override
    public User getUserByUserId(String userId) {
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
    public void addUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_INSERT);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAdt());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getTelephone());
            ps.setString(7, user.getAm());
            ps.setInt(8, user.getRoleId());
            ps.setBoolean(9, user.isOnlinesubmit());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("User saved with id=" + user.getUserId());
            } else {
                System.out.println("User save failed with id=" + user.getUserId());
            }
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                throw e;
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
            }
        }    
    }

    @Override
    public User getUserByUserIdPwd(String userId, String password) {
        User user = getUserByUserId(userId);
        if ((user != null) && (user.getPassword().equals(password)))
            return user;
        else
            return null;
    }

    @Override
    public void modifyUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_UPDATE_BY_ID);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAdt());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getTelephone());
            ps.setString(7, user.getAm());
            ps.setInt(8, user.getRoleId());
            ps.setBoolean(9, user.isOnlinesubmit());
            ps.setInt(10, user.getId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("User updated with id=" + user.getUserId());
            } else {
                System.out.println("User update failed with id=" + user.getUserId());
            }
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                throw e;
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
            }
        }        
    }


    @Override
    public void deleteUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_DELETE_BY_ID);
            ps.setInt(1, user.getId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("User delete with id=" + user.getUserId());
            } else {
                System.out.println("User delete failed with id=" + user.getUserId());
            }
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                throw e;
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
            }
        }            
    }

    @Override
    public User getStudentByUserId(String userId) {
        User user = getUserByUserId(userId);
        if (user != null) {
            if (user.getRoleName().equalsIgnoreCase("STUDENT"))
                return user;
            else
                return null;
        }
        return user;
    }
}