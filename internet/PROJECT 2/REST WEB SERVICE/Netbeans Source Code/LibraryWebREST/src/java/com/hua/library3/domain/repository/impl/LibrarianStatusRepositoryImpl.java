package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.LibrarianStatus;
import com.hua.library3.domain.repository.LibrarianStatusRepository;
import com.hua.library3.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class LibrarianStatusRepositoryImpl implements LibrarianStatusRepository {
    private static final String FIELDS
            = "status";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS
            = "status=?";
    private static final String SQL_SELECT
            = "select " + PK_FIELDS + " from librarian_status";
    private static final String SQL_SELECT_BY_STATUS
            = SQL_SELECT + " where status = ?";
    private static final String SQL_SELECT_BY_ID
            = SQL_SELECT + " where id = ?";
    private static final String SQL_INSERT
            = "insert into librarian_status (" + FIELDS + ") values (?)";
    private static final String SQL_UPDATE
            = "update librarian_status set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID
            = SQL_UPDATE + " where id = ?";
    private static final String SQL_DELETE_BY_ID
            = "delete from librarian_status where id = ?";
    private DataSource dataSource = SingletonDataSource.getDataSource();

    public LibrarianStatusRepositoryImpl() { }
    
    @Override
    public List<LibrarianStatus> getAllLibrarianStatus() {
        List<LibrarianStatus> librarianStatusList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                librarianStatusList.add(new LibrarianStatus(rs.getInt("id"), rs.getString("status")));
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
        return librarianStatusList;    
    }
    
}
