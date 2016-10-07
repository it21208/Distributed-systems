package com.hua.library3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "webview", "minUsers", "maxUsers" })
public class Role {
    public static final String ADMINISTRATOR = "Administrator";
    public static final String STUDENT = "Student";
    public static final String MANAGER = "Manager";
    public static final String DIRECTOR = "Director";
    public static final String LIBRARIAN = "Librarian";
    public static final int ADMINISTRATOR_ID = 6;
    public static final int STUDENT_ID = 7;
    public static final int MANAGER_ID = 9;
    public static final int DIRECTOR_ID = 10;
    public static final int LIBRARIAN_ID = 11;
    
    private int id;
    private String name; 
    private String webview;
    private String minUsers;
    private String maxUsers;

    public Role() {
        super();
    }
    
    public Role(int id, String name, String webview, String minUsers, String maxUsers) {
        this.setId(id);
        this.name = name;
        this.webview = webview;
        this.minUsers = minUsers;
        this.maxUsers = maxUsers; 
    }

    public int getId() {
        return id;
    }

    // whenever the PK field is set we refresh the related ServiceRights map
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getWebview() {
        return webview;
    }

    public String getMinUsers() {
        return minUsers;
    }

    @XmlElement
    public void setMinUsers(String minUsers) {
        this.minUsers = minUsers;
    }

    public String getMaxUsers() {
        return maxUsers;
    }

    @XmlElement
    public void setMaxUsers(String maxUsers) {
        this.maxUsers = maxUsers;
    }

    @XmlElement
    public void setWebview(String webview) {
        this.webview = webview;
    }
    
    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name +", web view=" + webview + 
                ", minUsers=" + minUsers + ", maxUsers=" + maxUsers + "]";
    }
}
