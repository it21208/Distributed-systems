package com.hua.library.domain;

import java.util.Date;

public class Submission {            
    private int id;
    private int submissionNo;
    private int dissertationId;     // DISSERTATION.id
    private String subject_areas;
    private byte[] pdf;
    private int pdf_pages;
    private int librarianstatusId = LibrarianStatus.DEFAULT_STATUS_ID;  // LIBRARIAN_STATUS.id
    private boolean director_status;
    private String director_notes;
    private int managerstatusId = ManagerStatus.DEFAULT_STATUS_ID;    // MANAGER_STATUS.id
    private String manager_notes;
    private String studentinformmethod;
    private Date studentinformdate;
    private String dissertation_ID;   // DISSERTATION.dissertationId  
    private String title;
    private String supervisor;
    private String studentname;
    private String userId;         // USER.userId
    private boolean studentcansubmitonline;
    private String studentemail;
    private String librarianstatus;
    private String managerstatus;

    public Submission() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubmissionNo() {
        return submissionNo;
    }

    public void setSubmissionNo(int submissionNo) {
        this.submissionNo = submissionNo;
    }

    public int getDissertationId() {
        return dissertationId;
    }

    public void setDissertationId(int dissertationId) {
        this.dissertationId = dissertationId;
    }

    public String getSubject_areas() {
        return subject_areas;
    }

    public void setSubject_areas(String subject_areas) {
        this.subject_areas = subject_areas;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public int getPdf_pages() {
        return pdf_pages;
    }

    public void setPdf_pages(int pdf_pages) {
        this.pdf_pages = pdf_pages;
    }

    public int getLibrarianstatusId() {
        return librarianstatusId;
    }

    public void setLibrarianstatusId(int librarianstatusId) {
        this.librarianstatusId = librarianstatusId;
    }

    public boolean isDirector_status() {
        return director_status;
    }

    public void setDirector_status(boolean director_status) {
        this.director_status = director_status;
    }

    public String getDirector_notes() {
        return director_notes;
    }

    public void setDirector_notes(String director_notes) {
        this.director_notes = director_notes;
    }

    public int getManagerstatusId() {
        return managerstatusId;
    }

    public void setManagerstatusId(int managerstatusId) {
        this.managerstatusId = managerstatusId;
    }

    public String getManager_notes() {
        return manager_notes;
    }

    public void setManager_notes(String manager_notes) {
        this.manager_notes = manager_notes;
    }

    public String getStudentinformmethod() {
        return studentinformmethod;
    }

    public void setStudentinformmethod(String studentinformmethod) {
        this.studentinformmethod = studentinformmethod;
    }

    public String getStudentinformmethodLong() {
        if ((studentinformmethod == null) || (studentinformmethod.equalsIgnoreCase("X"))) {
            return "Not notified yet";
        } else {
            return (studentinformmethod.equalsIgnoreCase("E")) ? "Email" : "Telephone";
        }
    }
    
    public Date getStudentinformdate() {
        return studentinformdate;
    }

    public void setStudentinformdate(Date studentinformdate) {
        this.studentinformdate = studentinformdate;
    }

    public String getDissertation_ID() {
        return dissertation_ID;
    }

    public void setDissertation_ID(String dissertation_ID) {
        this.dissertation_ID = dissertation_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean canNotifyStudent() {
        boolean res = ((!getManagerstatus().equals(ManagerStatus.NOTCHECKED_STATUS)) &&
                (getStudentinformdate() == null));
        return res;
    }
    
    public boolean isStudentcansubmitonline() {
        return studentcansubmitonline;
    }

    public void setStudentcansubmitonline(boolean studentcansubmitonline) {
        this.studentcansubmitonline = studentcansubmitonline;
    }

    public String getStudentemail() {
        return studentemail;
    }

    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public String getLibrarianstatus() {
        return librarianstatus;
    }

    public void setLibrarianstatus(String librarianstatus) {
        this.librarianstatus = librarianstatus;
    }

    public String getManagerstatus() {
        return managerstatus;
    }

    public void setManagerstatus(String managerstatus) {
        this.managerstatus = managerstatus;
    }
   
    @Override
    public String toString() {
        return "Submission [id=" + id + ", submissionNo=" + submissionNo + ", dissertationId=" +  dissertationId + 
            ", student_name=" + studentname + "]";
    }      
}
