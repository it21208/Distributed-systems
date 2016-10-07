package com.hua.library.domain;

public class Dissertation {
    private int id;
    private String dissertationId;
    private String title;
    private String supervisor;
    private int studentId;
    private String studentname;
    private String userId;
    private boolean studentcansubmitonline;
    private String studentemail;
    
    private Submission lastSubmission;

    public Dissertation() {
        super();
    }

    public Dissertation(int id, String dissertationId, String title, String supervisor, int studentId, String studentname, String userId, boolean studentcansubmitonline, String studentemail) {
        this.id = id;
        this.dissertationId = dissertationId;
        this.title = title;
        this.supervisor = supervisor;
        this.studentId = studentId;
        //------- related fields from master table <user>
        this.studentname = studentname;
        this.userId = userId;
        this.studentcansubmitonline = studentcansubmitonline;
        this.studentemail = studentemail;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public Submission getLastSubmission() {
        return lastSubmission;
    }

    public void setLastSubmission(Submission lastSubmission) {
        this.lastSubmission = lastSubmission;
    }
           
    @Override
    public String toString() {
        return "Dissertation [id=" + id + ", dissertationId=" +  dissertationId + 
            ", student_name=" + studentname + ", title=" + title +
            ", supervisor=" + supervisor + "]";
    }   
}
