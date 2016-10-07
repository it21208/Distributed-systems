package com.hua.library3.service.impl;

import com.hua.library3.domain.Dissertation;
import com.hua.library3.domain.Submission;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
import com.hua.library3.service.SubmissionService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.StringWriter;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();

/*
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
*/
    @Override
    public void addSubmission(Submission submission) throws SQLException {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/submissions");
        StringWriter writer = new StringWriter();
        JAXB.marshal(submission, writer);
        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, writer.toString());
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new SQLException(response.getEntity(String.class));
        } 
    }
}
