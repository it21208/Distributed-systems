package com.hua.library.domain.repository.impl;

import com.hua.library.domain.ServiceRight;
import com.hua.library.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import com.hua.library.domain.repository.ServiceRightRepository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ServiceRightRepositoryImpl implements ServiceRightRepository {
    private static final String FIELDS = 
        "name";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS = 
        "name=?";
    private static final String SQL_SELECT = 
        "select " + PK_FIELDS + " from service";
    private static final String SQL_SELECT_BY_NAME = 
        SQL_SELECT + " where name = ?";
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT + " where id = ?";     
    private static final String SQL_INSERT =
        "insert into service (" + FIELDS + ") values (?)";
    private static final String SQL_UPDATE =
        "update service set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID = 
        SQL_UPDATE + " where id = ?";
    private static final String SQL_DELETE_BY_ID =
        "delete from service where id = ?";
    private DataSource dataSource = (new SingletonDataSource()).getDataSource();;

    public ServiceRightRepositoryImpl() {}

    @Override
    public List<ServiceRight> getAllServiceRights() {
        List<ServiceRight> serviceList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                ServiceRight service = new ServiceRight();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                serviceList.add(service);
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
        return serviceList;    
    }

    @Override
    public ServiceRight getServiceRightById(int id) {
        ServiceRight service = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                service = new ServiceRight();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                System.out.println("Service Found : " + service);
            } else {
                System.out.println("No service found with id = " + id);
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
        return service;
    }

    @Override
    public ServiceRight getServiceRightByName(String name) {
        ServiceRight service = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_NAME);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if(rs.next()){
                service = new ServiceRight();
                service.setId(rs.getInt("id"));
                service.setName(name);
                System.out.println("Service Found : " + service);
            } else {
                System.out.println("No service found with name = " + name);
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
        return service;    }

    @Override
    public void addServiceRight(ServiceRight service) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_INSERT);
            ps.setString(1, service.getName());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Service saved with name=" + service.getName());
            } else {
                System.out.println("Service save failed with name=" + service.getName());
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

    @Override
    public void deleteServiceRight(ServiceRight service) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_DELETE_BY_ID);
            ps.setInt(1, service.getId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Service delete with name=" + service.getName());
            } else {
                System.out.println("Service delete failed with name=" + service.getName());
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

    /* Returns a map with the ServiceRight name as key and
       as value whether it applies to the specific roleId */
    @Override
    public Map<String, Boolean> getRoleServiceRights(int pRoleId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Boolean> roleServiceRightsMap = new HashMap<>();
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("select service.*, table2.ROLE_id as `roleId`\n" +
                        "from service left join \n" +
                        "(select * from role_has_service\n" +
                        " where role_has_service.ROLE_id=?) table2 \n" +
                        "on (service.id = table2.service_id)\n" +
                        "order by service.id");
            ps.setInt(1, pRoleId);
            rs = ps.executeQuery();           
            int roleId, serviceId;
            String serviceName;
            while (rs.next()) {
                // roleId may be NULL --> getInt("roleId") returns 0
                roleId = rs.getInt("roleId");
                serviceId = rs.getInt("id");
                serviceName = rs.getString("name");
                // append a new pair <String, Boolean> to the map
                roleServiceRightsMap.put(serviceName, (roleId==pRoleId)?true:false);
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
        return roleServiceRightsMap;                
   }
}
