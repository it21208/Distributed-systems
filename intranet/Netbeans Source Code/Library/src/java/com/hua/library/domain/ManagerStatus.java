package com.hua.library.domain;

public class ManagerStatus {
    private static final String STATUS_NOTCHECKED = "Not Checked";
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
