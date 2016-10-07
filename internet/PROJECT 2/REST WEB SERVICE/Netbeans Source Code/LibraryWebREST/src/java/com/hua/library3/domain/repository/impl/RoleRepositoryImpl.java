package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.Role;
import com.hua.library3.domain.repository.RoleRepository;
import com.hua.library3.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class RoleRepositoryImpl implements RoleRepository {
    private static final String FIELDS = 
        "name, webview, minusers, maxusers";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS = 
        "name=?, webview=?, minusers=?, maxusers=?";
    private static final String SQL_SELECT = 
        "select " + PK_FIELDS + " from role";
    private static final String SQL_SELECT_BY_NAME = 
        SQL_SELECT + " where name = ?";
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT + " where id = ?";     
    private static final String SQL_INSERT =
        "insert into role (" + FIELDS + ") values (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
        "update role set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID = 
        SQL_UPDATE + " where id = ?";
    private static final String SQL_DELETE_BY_ID =
        "delete from role where id = ?";
    private DataSource dataSource = SingletonDataSource.getDataSource();

    public RoleRepositoryImpl() {}

    @Override
    public Role getRoleById(int id) {
        Role role = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                role = new Role(id, rs.getString("name"), 
                        rs.getString("webview"), rs.getString("minusers"),
                        rs.getString("maxusers"));
                System.out.println("Role Found : " + role);
            } else {
                System.out.println("No role found with id = " + id);
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
        return role;
    }   
}
