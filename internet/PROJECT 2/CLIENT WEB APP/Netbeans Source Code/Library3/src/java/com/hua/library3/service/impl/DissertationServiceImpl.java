package com.hua.library3.service.impl;

import com.hua.library3.domain.Dissertation;
import com.hua.library3.service.DissertationService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import org.springframework.stereotype.Service;

@Service
public class DissertationServiceImpl implements DissertationService {
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();

/*    
    @Override
    public List<Dissertation> getAllDissertations() {
        return dissertationRepository.getAllDissertations();
    }

    @Override
    public Dissertation getDissertationByDissId(String dissId) {
        return dissertationRepository.getDissertationByDissId(dissId);
    }
*/
    @Override
    public void addDissertation(Dissertation dissertation) throws SQLException {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/dissertations");
        StringWriter writer = new StringWriter();
        JAXB.marshal(dissertation, writer);
        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, writer.toString());
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new SQLException(response.getEntity(String.class));
        } 
    }
/*
    @Override
    public void modifyDissertation(Dissertation dissertation) throws SQLException {
        dissertationRepository.modifyDissertation(dissertation);
    }
*/
    @Override
    public Dissertation getDissertationById(int id) {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/dissertations/" + id);
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;
        else 
            return response.getEntity(Dissertation.class);    
    }

/*
    @Override
    public List<Dissertation> getAllDirectorDissertations() {
        return dissertationRepository.getAllDirectorDissertations();
    }

    @Override
    public List<Dissertation> getAllManagerDissertations() {
        return dissertationRepository.getAllManagerDissertations();
    }
*/
    @Override
    public Dissertation getDissertationByStudentId(int studentId) {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/dissertations?studentId=" + studentId);
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;
        else 
            return response.getEntity(Dissertation.class);
    }


}
