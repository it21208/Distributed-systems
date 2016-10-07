package com.hua.library.service.impl;

import com.hua.library.domain.Dissertation;
import com.hua.library.domain.repository.DissertationRepository;
import com.hua.library.service.DissertationService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DissertationServiceImpl implements DissertationService {
    @Autowired
    private DissertationRepository dissertationRepository;
    
    @Override
    public List<Dissertation> getAllDissertations() {
        return dissertationRepository.getAllDissertations();
    }

    @Override
    public Dissertation getDissertationByDissId(String dissId) {
        return dissertationRepository.getDissertationByDissId(dissId);
    }

    @Override
    public void addDissertation(Dissertation dissertation) throws SQLException {
        dissertationRepository.addDissertation(dissertation);
    }

    @Override
    public void modifyDissertation(Dissertation dissertation) throws SQLException {
        dissertationRepository.modifyDissertation(dissertation);
    }

    @Override
    public Dissertation getDissertationById(int id) {
        return dissertationRepository.getDissertationById(id);
    }

    @Override
    public List<Dissertation> getAllDirectorDissertations() {
        return dissertationRepository.getAllDirectorDissertations();
    }

    @Override
    public List<Dissertation> getAllManagerDissertations() {
        return dissertationRepository.getAllManagerDissertations();
    }
}
