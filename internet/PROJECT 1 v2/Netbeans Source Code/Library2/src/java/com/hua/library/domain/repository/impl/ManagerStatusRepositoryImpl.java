package com.hua.library.domain.repository.impl;

import com.hua.library.domain.ManagerStatus;
import com.hua.library.domain.repository.ManagerStatusRepository;
import com.hua.library.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerStatusRepositoryImpl implements ManagerStatusRepository {
    private static final String FIELDS
            = "status";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS
            = "status=?";
    private static final String SQL_SELECT
            = "select " + PK_FIELDS + " from manager_status";
    private static final String SQL_SELECT_BY_STATUS
            = SQL_SELECT + " where status = ?";
    private static final String SQL_SELECT_BY_ID
            = SQL_SELECT + " where id = ?";
    private static final String SQL_INSERT
            = "insert into manager_status (" + FIELDS + ") values (?)";
    private static final String SQL_UPDATE
            = "update manager_status set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID
            = SQL_UPDATE + " where id = ?";
    private static final String SQL_DELETE_BY_ID
            = "delete from manager_status where id = ?";

    private DataSource dataSource = SingletonDataSource.getDataSource();
    
    @Override
    public List<ManagerStatus> getAllManagerStatus() {
        List<ManagerStatus> managerStatusList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                managerStatusList.add(new ManagerStatus(rs.getInt("id"), rs.getString("status")));
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
        return managerStatusList;     
    }
}
