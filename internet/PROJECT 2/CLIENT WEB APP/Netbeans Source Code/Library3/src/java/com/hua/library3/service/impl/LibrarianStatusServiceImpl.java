package com.hua.library3.service.impl;

import com.hua.library3.domain.LibrarianStatus;
import com.hua.library3.domain.LibrarianStatusList;
import com.hua.library3.service.LibrarianStatusService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class LibrarianStatusServiceImpl implements LibrarianStatusService {
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();

    @Override
    public List<LibrarianStatus> getAllLibrarianStatus() {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/librarianstatus");
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;
        else {
            LibrarianStatusList librarianStatusList = response.getEntity(LibrarianStatusList.class);
            return librarianStatusList.getList();
        }
    }    
}
