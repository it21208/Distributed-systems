package com.hua.library.domain.repository.impl;

import com.hua.library.domain.Submission;
import com.hua.library.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import com.hua.library.domain.repository.SubmissionRepository;

@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {
    private static final String FIELDS = 
        "submissionNo, dissertationId, subject_areas, pdf, pdf_pages, " +
        "librarianstatusId, director_status, director_notes, managerstatusId, " +
        "manager_notes, studentinformmethod, studentinformdate";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String UPDATE_FIELDS = 
        "subject_areas=?, pdf=?, pdf_pages=?, " +
        "librarianstatusId=?, director_status=?, director_notes=?, managerstatusId=?, " +
        "manager_notes=?, studentinformmethod=?, studentinformdate=?";
    private static final String SQL_SELECT = 
        "select " + PK_FIELDS + ", dissertation_ID, title, supervisor, studentname, " +
        "userid, studentcansubmitonline, studentemail, librarianstatus, managerstatus " +
        "from vsubmission";    // select from view!!!!
    private static final String SQL_DIRECTOR_SELECT = 
        "select " + PK_FIELDS + ", dissertation_ID, title, supervisor, studentname, " +
        "userid, studentcansubmitonline, studentemail, librarianstatus, managerstatus " +
        "from vsubmission_director";    // select from view!!!!
    private static final String SQL_MANAGER_SELECT = 
        "select " + PK_FIELDS + ", dissertation_ID, title, supervisor, studentname, " +
        "userid, studentcansubmitonline, studentemail, librarianstatus, managerstatus " +
        "from vsubmission_manager";    // select from view!!!!
    private static final String SQL_SELECT_BY_DISSID = 
        SQL_SELECT + " where dissertationId = ? order by submissionNo desc";
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT + " where id = ?";
    private static final String SQL_INSERT =
        "insert into submission (" + FIELDS + ") values (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE =
        "update submission set " + UPDATE_FIELDS;
    private static final String SQL_UPDATE_BY_ID = 
        SQL_UPDATE + " where id = ?";
  
    private DataSource dataSource = SingletonDataSource.getDataSource();

    public SubmissionRepositoryImpl() { }

    @Override
    public List<Submission> getAllSubmissions() {
        List<Submission> submissionList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Submission submission = new Submission();
                submission.setId(rs.getInt("id"));
                submission.setSubmissionNo(rs.getInt("submissionNo"));
                submission.setDissertationId(rs.getInt("dissertationId"));
                submission.setSubject_areas(rs.getString("subject_areas"));
                submission.setPdf(rs.getBytes("pdf"));
                submission.setPdf_pages(rs.getInt("pdf_pages"));
                submission.setLibrarianstatusId(rs.getInt("librarianstatusId"));
                submission.setDirector_status(rs.getBoolean("director_status"));
                submission.setDirector_notes(rs.getString("director_notes"));
                submission.setManagerstatusId(rs.getInt("managerstatusId"));
                submission.setManager_notes(rs.getString("manager_notes"));
                submission.setStudentinformmethod(rs.getString("studentinformmethod"));
                submission.setStudentinformdate(rs.getDate("studentinformdate"));
                submission.setDissertation_ID(rs.getString("dissertation_ID"));
                submission.setTitle(rs.getString("title"));
                submission.setSupervisor(rs.getString("supervisor"));
                submission.setStudentname(rs.getString("studentname"));
                submission.setUserId(rs.getString("userId"));
                submission.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                submission.setStudentemail(rs.getString("studentemail"));
                submission.setLibrarianstatus(rs.getString("librarianstatus"));
                submission.setManagerstatus(rs.getString("managerstatus"));
                submissionList.add(submission);
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
        return submissionList;    
    }

    @Override
    public List<Submission> getAllDirectorSubmissions() {
        List<Submission> submissionList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_DIRECTOR_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Submission submission = new Submission();
                submission.setId(rs.getInt("id"));
                submission.setSubmissionNo(rs.getInt("submissionNo"));
                submission.setDissertationId(rs.getInt("dissertationId"));
                submission.setSubject_areas(rs.getString("subject_areas"));
                submission.setPdf(rs.getBytes("pdf"));
                submission.setPdf_pages(rs.getInt("pdf_pages"));
                submission.setLibrarianstatusId(rs.getInt("librarianstatusId"));
                submission.setDirector_status(rs.getBoolean("director_status"));
                submission.setDirector_notes(rs.getString("director_notes"));
                submission.setManagerstatusId(rs.getInt("managerstatusId"));
                submission.setManager_notes(rs.getString("manager_notes"));
                submission.setStudentinformmethod(rs.getString("studentinformmethod"));
                submission.setStudentinformdate(rs.getDate("studentinformdate"));
                submission.setDissertation_ID(rs.getString("dissertation_ID"));
                submission.setTitle(rs.getString("title"));
                submission.setSupervisor(rs.getString("supervisor"));
                submission.setStudentname(rs.getString("studentname"));
                submission.setUserId(rs.getString("userId"));
                submission.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                submission.setStudentemail(rs.getString("studentemail"));
                submission.setLibrarianstatus(rs.getString("librarianstatus"));
                submission.setManagerstatus(rs.getString("managerstatus"));
                submissionList.add(submission);
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
        return submissionList;    
    }

    @Override
    public List<Submission> getAllManagerSubmissions() {
        List<Submission> submissionList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_MANAGER_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                Submission submission = new Submission();
                submission.setId(rs.getInt("id"));
                submission.setSubmissionNo(rs.getInt("submissionNo"));
                submission.setDissertationId(rs.getInt("dissertationId"));
                submission.setSubject_areas(rs.getString("subject_areas"));
                submission.setPdf(rs.getBytes("pdf"));
                submission.setPdf_pages(rs.getInt("pdf_pages"));
                submission.setLibrarianstatusId(rs.getInt("librarianstatusId"));
                submission.setDirector_status(rs.getBoolean("director_status"));
                submission.setDirector_notes(rs.getString("director_notes"));
                submission.setManagerstatusId(rs.getInt("managerstatusId"));
                submission.setManager_notes(rs.getString("manager_notes"));
                submission.setStudentinformmethod(rs.getString("studentinformmethod"));
                submission.setStudentinformdate(rs.getDate("studentinformdate"));
                submission.setDissertation_ID(rs.getString("dissertation_ID"));
                submission.setTitle(rs.getString("title"));
                submission.setSupervisor(rs.getString("supervisor"));
                submission.setStudentname(rs.getString("studentname"));
                submission.setUserId(rs.getString("userId"));
                submission.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                submission.setStudentemail(rs.getString("studentemail"));
                submission.setLibrarianstatus(rs.getString("librarianstatus"));
                submission.setManagerstatus(rs.getString("managerstatus"));
                submissionList.add(submission);
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
        return submissionList;    
    }

    @Override
    public List<Submission> getSubmissionsByDissId(int dissId) {
        List<Submission> submissionList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_DISSID);
            ps.setInt(1, dissId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Submission submission = new Submission();
                submission.setId(rs.getInt("id"));
                submission.setSubmissionNo(rs.getInt("submissionNo"));
                submission.setDissertationId(rs.getInt("dissertationId"));
                submission.setSubject_areas(rs.getString("subject_areas"));
                submission.setPdf(rs.getBytes("pdf"));
                submission.setPdf_pages(rs.getInt("pdf_pages"));
                submission.setLibrarianstatusId(rs.getInt("librarianstatusId"));
                submission.setDirector_status(rs.getBoolean("director_status"));
                submission.setDirector_notes(rs.getString("director_notes"));
                submission.setManagerstatusId(rs.getInt("managerstatusId"));
                submission.setManager_notes(rs.getString("manager_notes"));
                submission.setStudentinformmethod(rs.getString("studentinformmethod"));
                submission.setStudentinformdate(rs.getDate("studentinformdate"));
                submission.setDissertation_ID(rs.getString("dissertation_ID"));
                submission.setTitle(rs.getString("title"));
                submission.setSupervisor(rs.getString("supervisor"));
                submission.setStudentname(rs.getString("studentname"));
                submission.setUserId(rs.getString("userId"));
                submission.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                submission.setStudentemail(rs.getString("studentemail"));
                submission.setLibrarianstatus(rs.getString("librarianstatus"));
                submission.setManagerstatus(rs.getString("managerstatus"));
                submissionList.add(submission);
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
        return submissionList;    
    }

    @Override
    public Submission getLastSubmissionsByDissId(int dissId) {
        List<Submission> submissionList = getSubmissionsByDissId(dissId);
        return submissionList.get(0);
    }

    @Override
    public Submission getSubmissionById(int id) {
        Submission submission = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                submission = new Submission();
                submission.setId(rs.getInt("id"));
                submission.setSubmissionNo(rs.getInt("submissionNo"));
                submission.setDissertationId(rs.getInt("dissertationId"));
                submission.setSubject_areas(rs.getString("subject_areas"));
                submission.setPdf(rs.getBytes("pdf"));
                submission.setPdf_pages(rs.getInt("pdf_pages"));
                submission.setLibrarianstatusId(rs.getInt("librarianstatusId"));
                submission.setDirector_status(rs.getBoolean("director_status"));
                submission.setDirector_notes(rs.getString("director_notes"));
                submission.setManagerstatusId(rs.getInt("managerstatusId"));
                submission.setManager_notes(rs.getString("manager_notes"));
                submission.setStudentinformmethod(rs.getString("studentinformmethod"));
                submission.setStudentinformdate(rs.getDate("studentinformdate"));
                submission.setDissertation_ID(rs.getString("dissertation_ID"));
                submission.setTitle(rs.getString("title"));
                submission.setSupervisor(rs.getString("supervisor"));
                submission.setStudentname(rs.getString("studentname"));
                submission.setUserId(rs.getString("userId"));
                submission.setStudentcansubmitonline(rs.getBoolean("studentcansubmitonline"));
                submission.setStudentemail(rs.getString("studentemail"));
                submission.setLibrarianstatus(rs.getString("librarianstatus"));
                submission.setManagerstatus(rs.getString("managerstatus"));
                System.out.println("Submission Found : " + submission);
            } else {
                System.out.println("No submission found with id = " + id);
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
        return submission;    
    }


    // Add should be called through addDissertation() or
    // modifyDisertation() which will calculate the correct value for
    // submissionNo (auto number by our logic). It should also provide
    // the connection object which should have started the transaction
    @Override
    public void addSubmission(Connection connection, Submission submission) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = (PreparedStatement) connection.prepareStatement(SQL_INSERT);
            ps.setInt(1, submission.getSubmissionNo());
            ps.setInt(2, submission.getDissertationId());
            ps.setString(3, submission.getSubject_areas());
            ps.setBytes(4, submission.getPdf());
            ps.setInt(5, submission.getPdf_pages());
            ps.setInt(6, submission.getLibrarianstatusId());
            ps.setBoolean(7, submission.isDirector_status());
            ps.setString(8, submission.getDirector_notes());
            ps.setInt(9, submission.getManagerstatusId());
            ps.setString(10, submission.getManager_notes());
            ps.setString(11, submission.getStudentinformmethod());
            // convert java.util.Date --> long --> create java.sql.Date(long)
            if (submission.getStudentinformdate() != null)
                ps.setDate(12,  new java.sql.Date(submission.getStudentinformdate().getTime()));
            else
                ps.setDate(12, null);
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Submission saved with id=" + submission.getId());
            } else {
                System.out.println("Submission save failed with id=" + submission.getId());
            }
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                throw e;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }    
    }

    @Override
    public void addSubmission(Submission submission) throws SQLException {
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            addSubmission(con, submission);
        } catch (SQLException e) {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                throw e;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }    
    }

    // Modify should not alter id, submissionNo and dissertationId
    @Override
    public void modifySubmission(Submission submission) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_UPDATE_BY_ID);
            ps.setString(1, submission.getSubject_areas());
            ps.setBytes(2, submission.getPdf());
            ps.setInt(3, submission.getPdf_pages());
            ps.setInt(4, submission.getLibrarianstatusId());
            ps.setBoolean(5, submission.isDirector_status());
            ps.setString(6, submission.getDirector_notes());
            ps.setInt(7, submission.getManagerstatusId());
            ps.setString(8, submission.getManager_notes());
            ps.setString(9, submission.getStudentinformmethod());
            // convert java.util.Date --> long --> create java.sql.Date(long)
            if (submission.getStudentinformdate() != null)
                ps.setDate(10,  new java.sql.Date(submission.getStudentinformdate().getTime()));
            else
                ps.setDate(10, null);
            ps.setInt(11, submission.getId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Submission updated with id=" + submission.getId());
            } else {
                System.out.println("Submission update failed with id=" + submission.getId());
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

}
