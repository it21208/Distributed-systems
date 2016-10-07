package com.hua.library.domain;

import java.util.Date;

public class Dissertation {
    private int id;
    private String dissertationId;
    private String title;
    private String supervisor;
    private String subject_areas;
    // private blob pdf;       // we will need it later on
    private boolean ispdfloaded;
    private int pdf_pages;
    private boolean director_status;
    private String director_notes;
    private int managerstatusId = 3;
    private String manager_status;
    private String manager_notes;
    private int studentId;      // USER.id
    private String student_name;    // USER.name
    private String userId;         // USER.userId
    private int submissionstatusId;     // SUBMISSION_STATUS.id
    private String submission_status;   // SUBMISSION_STATUS.status
    private String studentinformmethod;
    private Date studentinformdate;

    public Dissertation() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDissertationId() {
        return dissertationId;
    }

    public void setDissertationId(String dissertationId) {
        this.dissertationId = dissertationId;
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

    public String getSubject_areas() {
        return subject_areas;
    }

    public void setSubject_areas(String subject_areas) {
        this.subject_areas = subject_areas;
    }

    public boolean isIspdfloaded() {
        return ispdfloaded;
    }

    public void setIspdfloaded(boolean ispdfloaded) {
        this.ispdfloaded = ispdfloaded;
    }

    public int getPdf_pages() {
        return pdf_pages;
    }

    public void setPdf_pages(int pdf_pages) {
        this.pdf_pages = pdf_pages;
    }

    public boolean isDirector_status() {
        return director_status;
    }
    
    public String getDirectorStatus() {
        return (isDirector_status()? "Approved" : "Not yet or does not apply");
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

    public String getManager_status() {
        return manager_status;
    }

    public void setManager_status(String manager_status) {
        this.manager_status = manager_status;
    }

    public String getManager_notes() {
        return manager_notes;
    }

    public void setManager_notes(String manager_notes) {
        this.manager_notes = manager_notes;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSubmissionstatusId() {
        return submissionstatusId;
    }

    public void setSubmissionstatusId(int submissionstatusId) {
        this.submissionstatusId = submissionstatusId;
    }

    public String getSubmission_status() {
        return submission_status;
    }

    public void setSubmission_status(String submission_status) {
        this.submission_status = submission_status;
    }

    public String getStudentinformmethod() {
        return studentinformmethod;
    }

    public void setStudentinformmethod(String studentinformmethod) {
        this.studentinformmethod = studentinformmethod;
    }

    public String getStudentinformmethodLong() {
        if ((studentinformmethod == null) || (studentinformmethod.equalsIgnoreCase("X")))
            return "Not notified yet";
        else
            return (studentinformmethod.equalsIgnoreCase("E"))? "Email":"Telephone";
    }
    
    public Date getStudentinformdate() {
        return studentinformdate;
    }

    public void setStudentinformdate(Date studentinformdate) {
        this.studentinformdate = studentinformdate;
    }

    public boolean canNotifyStudent() {
        return !(manager_status.equalsIgnoreCase("NOT CHECKED"));
    }
    
    @Override
    public String toString() {
        return "Dissertation [id=" + id + ", dissertationId=" +  dissertationId + 
            ", student_name=" + student_name + ", title=" + title +
            ", supervisor=" + supervisor + "]";
    }      
}
