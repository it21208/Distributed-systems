package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.Dissertation;
import com.hua.library3.domain.repository.DissertationRepository;
import com.hua.library3.domain.repository.SubmissionRepository;
import com.hua.library3.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class DissertationRepositoryImpl implements DissertationRepository {
    private static final String FIELDS = "dissertationId, title, supervisor, studentId";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS = 
        "dissertationId=?, title=?, supervisor=?, studentId=?"; 
    private static final String SQL_SELECT = 
        "select " + PK_FIELDS + ", studentname, userId, studentcansubmitonline, studentemail from vdissertation";    // select from view!!!!
    private static final String SQL_SELECT_DIRECTOR = 
        "select " + PK_FIELDS + ", studentname, userId, studentcansubmitonline, studentemail from vdissertation_director";    // select from view!!!!
    private static final String SQL_SELECT_MANAGER = 
        "select " + PK_FIELDS + ", studentname, userId, studentcansubmitonline, studentemail from vdissertation_manager";    // select from view!!!!
    private static final String SQL_SELECT_BY_DISSID = 
        SQL_SELECT + " where dissertationId = ?";
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT + " where id = ?";
    private static final String SQL_SELECT_BY_STUDENTID = 
        SQL_SELECT + " where studentId = ?";
    private static final String SQL_INSERT =
        "insert into dissertation (" + FIELDS + ") values (?,?,?,?)";
    private static final String SQL_UPDATE =
        "update dissertation set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID = 
        SQL_UPDATE + " where id = ?";
    private DataSource dataSource = SingletonDataSource.getDataSource();
    SubmissionRepository submissionRepository = new SubmissionRepositoryImpl();

    @Override
    public List<Dissertation> getAllDissertations() {
        List<Dissertation> dissertationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dissertation dissertation = new Dissertation();
                id = rs.getInt("id");
                dissertation.setId(id);
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setStudentname(rs.getString("studentname"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                dissertation.setStudentemail(rs.getString("studentemail"));
                dissertation.setLastSubmission(submissionRepository.getLastSubmissionsByDissId(id));
                dissertationList.add(dissertation);
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
        return dissertationList;    
    }

    @Override
    public List<Dissertation> getAllDirectorDissertations() {
        List<Dissertation> dissertationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_DIRECTOR);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dissertation dissertation = new Dissertation();
                id = rs.getInt("id");
                dissertation.setId(id);
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setStudentname(rs.getString("studentname"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                dissertation.setStudentemail(rs.getString("studentemail"));
                dissertation.setLastSubmission(submissionRepository.getLastSubmissionsByDissId(id));
                dissertationList.add(dissertation);
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
        return dissertationList;    
    }

    @Override
    public Dissertation getDissertationByDissId(String dissId) {
        Dissertation dissertation = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_DISSID);
            ps.setString(1, dissId);
            rs = ps.executeQuery();
            if(rs.next()){
                dissertation = new Dissertation();
                dissertation.setId(rs.getInt("id"));
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setStudentname(rs.getString("studentname"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                dissertation.setStudentemail(rs.getString("studentemail"));
                dissertation.setLastSubmission(submissionRepository.getLastSubmissionsByDissId(rs.getInt("id")));
                System.out.println("Dissertation Found : " + dissertation);
            } else {
                System.out.println("No dissertation found with dissId = " + dissertation);
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
        return dissertation;    
    }
    
    @Override
    public void addDissertation(Dissertation dissertation) throws SQLException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        String sqlStatement = "insert into dissertation (" + FIELDS + ") values (";
        try {
            int newDissertationAutoGeneratedId = -1;
            con = this.dataSource.getConnection();
            stm = con.createStatement();
            // STEP 0: Start transaction by disabling Autocommit
            con.setAutoCommit(false);
            // STEP 1: Add new record in DISSERTATION table
            sqlStatement += "'" + dissertation.getDissertationId() + "',";
            sqlStatement += "'" + dissertation.getTitle() + "',";
            sqlStatement += "'" + dissertation.getSupervisor() + "',";
            sqlStatement += dissertation.getStudentId() + ")";
            stm.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            // STEP 2: Retrieve the autoincremented id from the DISSERTATION table
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                newDissertationAutoGeneratedId = rs.getInt(1);
            } else { 
                System.out.println("Dissertation save failed with dissertationId=" + dissertation.getDissertationId());
                throw new UnsupportedOperationException("addDissertation() unsupported exception!");
            }
            System.out.println("Dissertation saved with id=" + newDissertationAutoGeneratedId);
            // STEP 3: Set the foreign key value in the SUBMISSION object
            dissertation.getLastSubmission().setDissertationId(newDissertationAutoGeneratedId);
            // STEP 4: Add new record in SUBMISSION table
            submissionRepository.addSubmission(con, dissertation.getLastSubmission());
            // STEP 5.1: Committ transaction
            con.commit();
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                // STEP 5.2: Rollback transaction
                con.rollback();
                throw e;
        } finally {
            try {
                rs.close();
                stm.close();
                con.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public void modifyDissertation(Dissertation dissertation) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_UPDATE_BY_ID);
            ps.setString(1, dissertation.getDissertationId());
            ps.setString(2, dissertation.getTitle());
            ps.setString(3, dissertation.getSupervisor());
            ps.setInt(4, dissertation.getStudentId());
            ps.setInt(5, dissertation.getId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Dissertation updated with id=" + dissertation.getId());
            } else {
                System.out.println("Dissertation update failed with id=" + dissertation.getId());
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
    public Dissertation getDissertationById(int id) {
        Dissertation dissertation = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                dissertation = new Dissertation();
                dissertation.setId(id);
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setStudentname(rs.getString("studentname"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                dissertation.setStudentemail(rs.getString("studentemail"));
                dissertation.setLastSubmission(submissionRepository.getLastSubmissionsByDissId(rs.getInt("id")));
                System.out.println("Dissertation Found : " + dissertation);
            } else {
                System.out.println("No dissertation found with id = " + id);
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
        return dissertation;    
    }

    @Override
    public List<Dissertation> getAllManagerDissertations() {
        List<Dissertation> dissertationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_MANAGER);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dissertation dissertation = new Dissertation();
                id = rs.getInt("id");
                dissertation.setId(id);
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setStudentname(rs.getString("studentname"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                dissertation.setStudentemail(rs.getString("studentemail"));
                dissertation.setLastSubmission(submissionRepository.getLastSubmissionsByDissId(id));
                dissertationList.add(dissertation);
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
        return dissertationList;    
    }

    @Override
    public Dissertation getDissertationByStudentId(int studentId) {
        Dissertation dissertation = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_STUDENTID);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            if(rs.next()){
                dissertation = new Dissertation();
                dissertation.setId(rs.getInt("id"));
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setStudentId(studentId);
                dissertation.setStudentname(rs.getString("studentname"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                dissertation.setStudentemail(rs.getString("studentemail"));
                dissertation.setLastSubmission(submissionRepository.getLastSubmissionsByDissId(rs.getInt("id")));
                System.out.println("Dissertation Found : " + dissertation);
            } else {
                System.out.println("No dissertation found for student with id = " + studentId);
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
        return dissertation;    
    }

}
