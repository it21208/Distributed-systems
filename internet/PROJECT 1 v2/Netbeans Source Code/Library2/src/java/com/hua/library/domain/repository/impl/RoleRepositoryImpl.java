package com.hua.library.domain.repository.impl;

import com.hua.library.domain.Role;
import com.hua.library.domain.repository.RoleRepository;
import com.hua.library.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
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
    public List<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("id"), rs.getString("name"), 
                        rs.getString("webview"), rs.getString("minusers"),
                        rs.getString("maxusers"));
                roleList.add(role);
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
        return roleList;    
    }

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
    
    @Override
    public Role getRoleByName(String name) {
        Role role = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_NAME);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if(rs.next()){
                role = new Role(rs.getInt("id"), name, 
                        rs.getString("webview"), rs.getString("minusers"),
                        rs.getString("maxusers"));
                System.out.println("Role Found : " + role);
            } else {
                System.out.println("No role found with name = " + name);
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

    // When we add a role, there are no services related to it yet!
    @Override
    public void addRole(Role role) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_INSERT);
            ps.setString(1, role.getName());
            ps.setString(2, role.getWebview());
            ps.setString(3, role.getMinUsers());
            ps.setString(4, role.getMaxUsers());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Role saved with name=" + role.getName());
            } else {
                System.out.println("Role save failed with name=" + role.getName());
            }
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
            }
        }    
    }

    // When we delete a role, the related services are cascade deleted by the DBMS
    @Override
    public void deleteRole(Role role) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_DELETE_BY_ID);
            ps.setInt(1, role.getId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Role delete with name=" + role.getName());
            } else {
                System.out.println("Role delete failed with name=" + role.getName());
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

    // return the role table contents as an HTML Option set
    // for use in an HTML Select tag
    @Override
    public String roleOptionSet() {
        return roleOptionSet(-1);
    }
    
    @Override
    public String roleOptionSet(int roleId) {
        String resultString = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            int id;
            String name, token;
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                token = (id == roleId ) ? " selected" : "";
                resultString = resultString + 
                        "<option value=\"" + id + "\"" + token + ">" +
                        name + "</option>";       
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
        return resultString;        
    }   

    @Override
    public void updateRoleServices(Role newRole) {
        String SQLInsert = "insert into role_has_SERVICE (role_id, service_id) select ? as 'role_id', id as 'service_id' from SERVICE where name = ?";
        String SQLDelete = "delete from role_has_SERVICE where (role_id = ?) and (service_id in (select id from SERVICE where name = ?))";
        // read the DB version of this role
        int roleId = newRole.getId();
        Role oldRole = new Role();
        oldRole.setId(roleId);
        Connection con = null;
        PreparedStatement psInsert = null, psDelete = null;
        boolean oldValue;
        try{
            con = dataSource.getConnection();
            psInsert = con.prepareStatement(SQLInsert);
            psDelete = con.prepareStatement(SQLDelete);
            for (Map.Entry<String, Boolean> newEntry : newRole.getServiceRightsMap().entrySet()) {
                oldValue = oldRole.getServiceRightsMap().get(newEntry.getKey());
                // compare newEntry.getValue() with oldValue
                if (newEntry.getValue()==null) newEntry.setValue(false);
                if (oldValue != newEntry.getValue()) {
                    if (oldValue) {
                        psDelete.setInt(1, roleId);
                        psDelete.setString(2, newEntry.getKey());
                        psDelete.execute();
                        System.out.printf("Deleted service %s from role %s\n", newEntry.getKey(), newRole.getName());
                    } else {
                        psInsert.setInt(1, roleId);
                        psInsert.setString(2, newEntry.getKey());
                        psInsert.execute();
                        System.out.printf("Inserted service %s into role %s\n", newEntry.getKey(), newRole.getName());
                    }
                }
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                psDelete.close();
                psInsert.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
