package com.hua.library.service.impl;

import com.hua.library.domain.LibrarianStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hua.library.service.LibrarianStatusService;
import com.hua.library.domain.repository.LibrarianStatusRepository;

@Service
public class LibrarianStatusServiceImpl implements LibrarianStatusService {
    @Autowired
    LibrarianStatusRepository librarianStatusRepository;
    
    @Override
    public List<LibrarianStatus> getAllLibrarianStatus() {
        return librarianStatusRepository.getAllLibrarianStatus();
    }
    
}
