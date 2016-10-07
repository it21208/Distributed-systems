package com.hua.library.domain.repository;

import java.sql.SQLException;
import java.util.List;
import com.hua.library.domain.ServiceRight;
import java.util.Map;

public interface ServiceRightRepository {
    public List<ServiceRight> getAllServiceRights();
    public ServiceRight getServiceRightById(int id);
    public ServiceRight getServiceRightByName(String name);
    public void addServiceRight(ServiceRight service);  
    public void deleteServiceRight(ServiceRight service) throws SQLException;
    public Map<String, Boolean> getRoleServiceRights(int pRoleId);  
}