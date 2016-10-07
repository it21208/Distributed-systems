package com.hua.library.domain;

public class LibrarianStatus {
    public static final String DEFAULT_STATUS = "Student Submitted";
    public static final int DEFAULT_STATUS_ID = 5;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Submission Status [id=" + id + ", status=" + status + "]";
    }
}
