package com.hua.library3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "dissertationId", "title", "supervisor", "studentId", "studentname", "userId", "studentcansubmitonline", "studentemail", "lastSubmission" })
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

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getDissertationId() {
        return dissertationId;
    }

    @XmlElement
    public void setDissertationId(String dissertationId) {
        this.dissertationId = dissertationId;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSupervisor() {
        return supervisor;
    }

    @XmlElement
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public int getStudentId() {
        return studentId;
    }

    @XmlElement
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentname() {
        return studentname;
    }

    @XmlElement
    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getUserId() {
        return userId;
    }

    @XmlElement
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isStudentcansubmitonline() {
        return studentcansubmitonline;
    }

    @XmlElement
    public void setStudentcansubmitonline(boolean studentcansubmitonline) {
        this.studentcansubmitonline = studentcansubmitonline;
    }

    public String getStudentemail() {
        return studentemail;
    }

    @XmlElement
    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public Submission getLastSubmission() {
        return lastSubmission;
    }

    @XmlElement
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
