package com.hua.library.domain;

import com.hua.library.domain.repository.ServiceRightRepository;
import com.hua.library.domain.repository.impl.ServiceRightRepositoryImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class Role {
    private int id;
    private String name; 
    private String webview;
    private String minUsers;
    private String maxUsers;
    private Map<String, Boolean> serviceRightsMap;
    @Autowired
    private ServiceRightRepository serviceRightRepository = new ServiceRightRepositoryImpl(); 

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
    public void setId(int id) {
        this.id = id;
        this.serviceRightsMap = serviceRightRepository.getRoleServiceRights(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebview() {
        return webview;
    }

    public String getMinUsers() {
        return minUsers;
    }

    public void setMinUsers(String minUsers) {
        this.minUsers = minUsers;
    }

    public String getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(String maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setWebview(String webview) {
        this.webview = webview;
    }

    public Map<String, Boolean> getServiceRightsMap() {
        return serviceRightsMap;
    }

    public void setServiceRightsMap(Map<String, Boolean> serviceRightsMap) {
        this.serviceRightsMap = serviceRightsMap;
    }

    public ServiceRightRepository getServiceRightRepository() {
        return serviceRightRepository;
    }

    public void setServiceRightRepository(ServiceRightRepository serviceRightRepository) {
        this.serviceRightRepository = serviceRightRepository;
    }
    
    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name +", web view=" + webview + 
                ", minUsers=" + minUsers + ", maxUsers=" + maxUsers + "]";
    }
}
