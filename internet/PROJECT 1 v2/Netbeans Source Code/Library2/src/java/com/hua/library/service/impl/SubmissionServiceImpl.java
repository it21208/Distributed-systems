package com.hua.library.service.impl;

import com.hua.library.domain.Submission;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hua.library.service.SubmissionService;
import com.hua.library.domain.repository.SubmissionRepository;
import java.sql.Connection;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.getAllSubmissions();
    }

    @Override
    public List<Submission> getAllDirectorSubmissions() {
        return submissionRepository.getAllDirectorSubmissions();
    }

    @Override
    public List<Submission> getAllManagerSubmissions() {
        return submissionRepository.getAllManagerSubmissions();
    }

    @Override
    public List<Submission> getSubmissionsByDissId(int dissId) {
        return submissionRepository.getSubmissionsByDissId(dissId);
    }

    @Override
    public Submission getSubmissionById(int id) {
        return submissionRepository.getSubmissionById(id);
    }

    @Override
    public void addSubmission(Connection connection, Submission submission) throws SQLException {
        submissionRepository.addSubmission(connection, submission);
    }

    @Override
    public void modifySubmission(Submission submission) throws SQLException {
        submissionRepository.modifySubmission(submission);
    } 

    @Override
    public Submission getLastSubmissionsByDissId(int dissId) {
        return submissionRepository.getLastSubmissionsByDissId(dissId);
    }

    @Override
    public void addSubmission(Submission submission) throws SQLException {
        submissionRepository.addSubmission(submission);
    }
}
