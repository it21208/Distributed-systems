package com.hua.library.domain.repository.impl;

import com.hua.library.domain.Dissertation;
import com.hua.library.domain.repository.DissertationRepository;
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
public class DissertationRepositoryImpl implements DissertationRepository {
    private static final String FIELDS = 
        "dissertationId, title, supervisor, subject_areas, ispdfloaded, " +
        "pdf_pages, director_status, director_notes, managerstatusId, " +
        "manager_notes, studentId, submissionstatusId, studentinformmethod, " +
        "studentinformdate";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS = 
        "dissertationId=?, title=?, supervisor=?, subject_areas=?, ispdfloaded=?, " +
        "pdf_pages=?, director_status=?, director_notes=?, managerstatusId=?, " +
        "manager_notes=?, studentId=?, submissionstatusId=?, " +
        "studentinformmethod=?, studentinformdate=?"; 
    private static final String SQL_SELECT = 
        "select " + PK_FIELDS + ", userId, student_name, submission_status, manager_status from vdissertation";    // select from view!!!!
    private static final String SQL_DIRECTOR_SELECT = 
        "select " + PK_FIELDS + ", userId, student_name, submission_status, manager_status from vdissertation_director";    // select from view!!!!
    private static final String SQL_MANAGER_SELECT = 
        "select " + PK_FIELDS + ", userId, student_name, submission_status, manager_status from vdissertation_manager";    // select from view!!!!
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT + " where dissertationId = ?";
    private static final String SQL_INSERT =
        "insert into dissertation (" + FIELDS + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE =
        "update dissertation set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID = 
        SQL_UPDATE + " where id = ?";
  
    private DataSource dataSource = (new SingletonDataSource()).getDataSource();;

    public DissertationRepositoryImpl() { }
  
    @Override
    public List<Dissertation> getAllDissertations() {
        List<Dissertation> dissertationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dissertation dissertation = new Dissertation();
                dissertation.setId(rs.getInt("id"));
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setSubject_areas(rs.getString("subject_areas"));
                dissertation.setIspdfloaded(rs.getBoolean("ispdfloaded"));
                dissertation.setPdf_pages(rs.getInt("pdf_pages"));
                dissertation.setDirector_status(rs.getBoolean("director_status"));
                dissertation.setDirector_notes(rs.getString("director_notes"));
                dissertation.setManagerstatusId(rs.getInt("managerstatusId"));
                dissertation.setManager_notes(rs.getString("manager_notes"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setSubmissionstatusId(rs.getInt("submissionstatusId"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudent_name(rs.getString("student_name"));
                dissertation.setSubmission_status(rs.getString("submission_status"));
                dissertation.setManager_status(rs.getString("manager_status"));
                dissertation.setStudentinformmethod(rs.getString("studentinformmethod"));
                dissertation.setStudentinformdate(rs.getDate("studentinformdate"));                
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
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setString(1, dissId);
            rs = ps.executeQuery();
            if(rs.next()){
                dissertation = new Dissertation();
                dissertation.setId(rs.getInt("id"));
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setSubject_areas(rs.getString("subject_areas"));
                dissertation.setIspdfloaded(rs.getBoolean("ispdfloaded"));
                dissertation.setPdf_pages(rs.getInt("pdf_pages"));
                dissertation.setDirector_status(rs.getBoolean("director_status"));
                dissertation.setDirector_notes(rs.getString("director_notes"));
                dissertation.setManagerstatusId(rs.getInt("managerstatusId"));
                dissertation.setManager_notes(rs.getString("manager_notes"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setSubmissionstatusId(rs.getInt("submissionstatusId"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudent_name(rs.getString("student_name"));
                dissertation.setSubmission_status(rs.getString("submission_status"));
                dissertation.setManager_status(rs.getString("manager_status"));
                dissertation.setStudentinformmethod(rs.getString("studentinformmethod"));
                dissertation.setStudentinformdate(rs.getDate("studentinformdate"));                
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
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_INSERT);
            ps.setString(1, dissertation.getDissertationId());
            ps.setString(2, dissertation.getTitle());
            ps.setString(3, dissertation.getSupervisor());
            ps.setString(4, dissertation.getSubject_areas());
            ps.setBoolean(5, dissertation.isIspdfloaded());
            ps.setInt(6, dissertation.getPdf_pages());
            ps.setBoolean(7, dissertation.isDirector_status());
            ps.setString(8, dissertation.getDirector_notes());
            ps.setInt(9, dissertation.getManagerstatusId());
            ps.setString(10, dissertation.getManager_notes());
            ps.setInt(11, dissertation.getStudentId());
            ps.setInt(12, dissertation.getSubmissionstatusId());
            ps.setString(13, dissertation.getStudentinformmethod());
            // convert java.util.Date --> long --> create java.sql.Date(long)
            if (dissertation.getStudentinformdate() != null)
                ps.setDate(14,  new java.sql.Date(dissertation.getStudentinformdate().getTime()));
            else
                ps.setDate(14, null);
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Dissertation saved with id=" + dissertation.getDissertationId());
            } else {
                System.out.println("Dissertation save failed with id=" + dissertation.getDissertationId());
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
    public void modifyDissertation(Dissertation dissertation) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_UPDATE_BY_ID);
            ps.setString(1, dissertation.getDissertationId());
            ps.setString(2, dissertation.getTitle());
            ps.setString(3, dissertation.getSupervisor());
            ps.setString(4, dissertation.getSubject_areas());
            ps.setBoolean(5, dissertation.isIspdfloaded());
            ps.setInt(6, dissertation.getPdf_pages());
            ps.setBoolean(7, dissertation.isDirector_status());
            ps.setString(8, dissertation.getDirector_notes());
            ps.setInt(9, dissertation.getManagerstatusId());
            ps.setString(10, dissertation.getManager_notes());
            ps.setInt(11, dissertation.getStudentId());
            ps.setInt(12, dissertation.getSubmissionstatusId());
            ps.setString(13, dissertation.getStudentinformmethod());
            // convert java.util.Date --> long --> create java.sql.Date(long)
            if (dissertation.getStudentinformdate() != null)
                ps.setDate(14,  new java.sql.Date(dissertation.getStudentinformdate().getTime()));
            else
                ps.setDate(14, null);
            ps.setInt(15, dissertation.getId());
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
    public List<Dissertation> getAllDirectorDissertations() {
        List<Dissertation> dissertationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_DIRECTOR_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dissertation dissertation = new Dissertation();
                dissertation.setId(rs.getInt("id"));
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setSubject_areas(rs.getString("subject_areas"));
                dissertation.setIspdfloaded(rs.getBoolean("ispdfloaded"));
                dissertation.setPdf_pages(rs.getInt("pdf_pages"));
                dissertation.setDirector_status(rs.getBoolean("director_status"));
                dissertation.setDirector_notes(rs.getString("director_notes"));
                dissertation.setManagerstatusId(rs.getInt("managerstatusId"));
                dissertation.setManager_notes(rs.getString("manager_notes"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setSubmissionstatusId(rs.getInt("submissionstatusId"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudent_name(rs.getString("student_name"));
                dissertation.setSubmission_status(rs.getString("submission_status"));
                dissertation.setManager_status(rs.getString("manager_status"));
                dissertation.setStudentinformmethod(rs.getString("studentinformmethod"));
                dissertation.setStudentinformdate(rs.getDate("studentinformdate"));                
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
    public List<Dissertation> getAllManagerDissertations() {
        List<Dissertation> dissertationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_MANAGER_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dissertation dissertation = new Dissertation();
                dissertation.setId(rs.getInt("id"));
                dissertation.setDissertationId(rs.getString("dissertationId"));
                dissertation.setTitle(rs.getString("title"));
                dissertation.setSupervisor(rs.getString("supervisor"));
                dissertation.setSubject_areas(rs.getString("subject_areas"));
                dissertation.setIspdfloaded(rs.getBoolean("ispdfloaded"));
                dissertation.setPdf_pages(rs.getInt("pdf_pages"));
                dissertation.setDirector_status(rs.getBoolean("director_status"));
                dissertation.setDirector_notes(rs.getString("director_notes"));
                dissertation.setManagerstatusId(rs.getInt("managerstatusId"));
                dissertation.setManager_notes(rs.getString("manager_notes"));
                dissertation.setStudentId(rs.getInt("studentId"));
                dissertation.setSubmissionstatusId(rs.getInt("submissionstatusId"));
                dissertation.setUserId(rs.getString("userId"));
                dissertation.setStudent_name(rs.getString("student_name"));
                dissertation.setSubmission_status(rs.getString("submission_status"));
                dissertation.setManager_status(rs.getString("manager_status"));
                dissertation.setStudentinformmethod(rs.getString("studentinformmethod"));
                dissertation.setStudentinformdate(rs.getDate("studentinformdate"));                
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
    
}
