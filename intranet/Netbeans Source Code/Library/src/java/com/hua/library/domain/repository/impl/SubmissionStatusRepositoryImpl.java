package com.hua.library.domain.repository.impl;

import com.hua.library.domain.SubmissionStatus;
import com.hua.library.domain.repository.SubmissionStatusRepository;
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
public class SubmissionStatusRepositoryImpl implements SubmissionStatusRepository {
    private static final String FIELDS
            = "status";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS
            = "status=?";
    private static final String SQL_SELECT
            = "select " + PK_FIELDS + " from submission_status";
    private static final String SQL_SELECT_BY_STATUS
            = SQL_SELECT + " where status = ?";
    private static final String SQL_SELECT_BY_ID
            = SQL_SELECT + " where id = ?";
    private static final String SQL_INSERT
            = "insert into submission_status (" + FIELDS + ") values (?)";
    private static final String SQL_UPDATE
            = "update submission_status set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID
            = SQL_UPDATE + " where id = ?";
    private static final String SQL_DELETE_BY_ID
            = "delete from submission_status where id = ?";
    private DataSource dataSource = (new SingletonDataSource()).getDataSource();

    public SubmissionStatusRepositoryImpl() { }
    
    @Override
    public List<SubmissionStatus> getAllSubmissionStatus() {
        List<SubmissionStatus> submissionStatusList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                submissionStatusList.add(new SubmissionStatus(rs.getInt("id"), rs.getString("status")));
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
        return submissionStatusList;    
    }
    
}
