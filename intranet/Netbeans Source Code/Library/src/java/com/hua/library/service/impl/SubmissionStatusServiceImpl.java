package com.hua.library.service.impl;

import com.hua.library.domain.SubmissionStatus;
import com.hua.library.domain.repository.SubmissionStatusRepository;
import com.hua.library.service.SubmissionStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionStatusServiceImpl implements SubmissionStatusService {
    @Autowired
    SubmissionStatusRepository submissionStatusRepository;
    
    @Override
    public List<SubmissionStatus> getAllSubmissionStatus() {
        return submissionStatusRepository.getAllSubmissionStatus();
    }
    
}
