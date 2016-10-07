package com.hua.library.service.impl;

import com.hua.library.domain.ManagerStatus;
import com.hua.library.domain.repository.ManagerStatusRepository;
import com.hua.library.service.ManagerStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerStatusServiceImpl implements ManagerStatusService {
    @Autowired
    ManagerStatusRepository managerStatusRepository;
    
    @Override
    public List<ManagerStatus> getAllManagerStatus() {
        return managerStatusRepository.getAllManagerStatus();
    }
    
}
