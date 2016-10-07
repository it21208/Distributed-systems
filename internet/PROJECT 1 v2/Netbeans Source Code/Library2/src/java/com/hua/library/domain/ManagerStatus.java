package com.hua.library.domain;

public class ManagerStatus {
    public static final String NOTCHECKED_STATUS = "Not Checked";
    public static final String ACCEPTED_STATUS = "Accepted";
    public static final String REJECTED_STATUS = "Rejected";
    public static final int NOTCHECKED_STATUS_ID = 3;
    public static final int ACCEPTED_STATUS_ID = 1;
    public static final int REJECTED_STATUS_ID = 2;
    public static final String DEFAULT_STATUS = NOTCHECKED_STATUS;
    public static final int DEFAULT_STATUS_ID = NOTCHECKED_STATUS_ID;
    private int id;
    private String status;

    public ManagerStatus() {
        super();
    }

    public ManagerStatus(int id, String status) {
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
        return "Manager Status [id=" + id + ", status=" + status + "]";
    }    
}
