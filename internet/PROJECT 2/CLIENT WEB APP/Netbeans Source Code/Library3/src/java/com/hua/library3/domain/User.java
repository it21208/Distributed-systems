package com.hua.library3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "userId", "name", "password", "adt", "email", "telephone", "am", "roleId", "roleName", "onlinesubmit" })
public class User {
    private int id;
    private String userId;
    private String name;
    private String password;
    private String adt;
    private String email;
    private String telephone;
    private String am;    
    private int roleId;
    private String roleName;
    private boolean onlinesubmit;

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    @XmlElement
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdt() {
        return adt;
    }

    @XmlElement
    public void setAdt(String adt) {
        this.adt = adt;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    @XmlElement
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAm() {
        return am;
    }

    @XmlElement
    public void setAm(String am) {
        this.am = am;
    }

    public int getRoleId() {
        return roleId;
    }

    @XmlElement
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    @XmlElement
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isOnlinesubmit() {
        return onlinesubmit;
    }

    @XmlElement
    public void setOnlinesubmit(boolean onlinesubmit) {
        this.onlinesubmit = onlinesubmit;
    }
    
    @Override
    public String toString() {
        return "User [id=" + id + ", user_id=" +  userId + ", name=" + name +"]";
    }    
}
