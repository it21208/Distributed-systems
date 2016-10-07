package com.hua.library3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "status" })
public class LibrarianStatus {
    public static final String ACCEPTED = "Accepted";
    public static final String NON_STANDARD = "Non Standard";
    public static final String OUT_OF_PAGE_RANGE = "Out of page range";
    public static final String STUDENT_SUBMITTED = "Student Submitted";
    public static final int ACCEPTED_ID = 2;
    public static final int NON_STANDARD_ID = 4;
    public static final int OUT_OF_PAGE_RANGE_ID = 3;
    public static final int STUDENT_SUBMITTED_ID = 5;
    public static final String DEFAULT_STATUS = STUDENT_SUBMITTED;
    public static final int DEFAULT_STATUS_ID = STUDENT_SUBMITTED_ID;
    
    private int id;
    private String status;

    public LibrarianStatus() {
        super();
    }

    public LibrarianStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Submission Status [id=" + id + ", status=" + status + "]";
    }
}
