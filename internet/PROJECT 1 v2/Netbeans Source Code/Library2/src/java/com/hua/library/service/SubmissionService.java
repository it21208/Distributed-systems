package com.hua.library.service;

import com.hua.library.domain.Submission;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SubmissionService {
    List <Submission> getAllSubmissions();
    List <Submission> getAllDirectorSubmissions();
    List <Submission> getAllManagerSubmissions();
    List <Submission> getSubmissionsByDissId(int dissId);
    Submission getLastSubmissionsByDissId(int dissId);
    Submission getSubmissionById(int id);
    void addSubmission(Connection connection, Submission submission) throws SQLException; 
    void addSubmission(Submission submission) throws SQLException;    
    void modifySubmission(Submission submission) throws SQLException;    
}
