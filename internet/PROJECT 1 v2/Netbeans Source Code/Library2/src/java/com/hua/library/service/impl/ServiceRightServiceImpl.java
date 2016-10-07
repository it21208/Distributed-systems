package com.hua.library.service.impl;

import com.hua.library.domain.ServiceRight;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hua.library.service.ServiceRightService;
import com.hua.library.domain.repository.ServiceRightRepository;
import java.util.Map;
import org.springframework.stereotype.Service;



@Service
public class ServiceRightServiceImpl implements ServiceRightService {
    @Autowired
    private ServiceRightRepository serviceRightRepository;
    
    @Override
    public List<ServiceRight> getAllServiceRights() {
        return serviceRightRepository.getAllServiceRights();
    }

    @Override
    public ServiceRight getServiceRightById(int id) {
        return serviceRightRepository.getServiceRightById(id);
    }

    @Override
    public ServiceRight getServiceRightByName(String name) {
        return serviceRightRepository.getServiceRightByName(name);
    }

    @Override
    public void addServiceRight(ServiceRight service) {
        serviceRightRepository.addServiceRight(service);
    }

    @Override
    public void deleteServiceRight(ServiceRight service) throws SQLException {
        serviceRightRepository.deleteServiceRight(service);
    }

    @Override
    public Map<String, Boolean> getRoleServiceRights(int pRoleId) {
        return serviceRightRepository.getRoleServiceRights(pRoleId);
    } 
}
