package com.hua.library3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "status" })
public class ManagerStatus {
    public static final String NOTCHECKED = "Not Checked";
    public static final String ACCEPTED = "Accepted";
    public static final String REJECTED = "Rejected";
    public static final int NOTCHECKED_ID = 3;
    public static final int ACCEPTED_ID = 1;
    public static final int REJECTED_ID = 2;
    public static final String DEFAULT = NOTCHECKED;
    public static final int DEFAULT_ID = NOTCHECKED_ID;
    
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
        return "Manager Status [id=" + id + ", status=" + status + "]";
    }    
}
