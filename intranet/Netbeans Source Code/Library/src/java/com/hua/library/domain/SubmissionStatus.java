package com.hua.library.domain;

public class SubmissionStatus {
    private static final String STATUS_ACCEPTED = "Accepted";
    private int id;
    private String status = STATUS_ACCEPTED;

    public SubmissionStatus() {
        super();
    }

    public SubmissionStatus(int id, String status) {
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
