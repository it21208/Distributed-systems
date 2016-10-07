package com.hua.library3.service.impl;

import com.hua.library3.domain.ManagerStatus;
import com.hua.library3.domain.ManagerStatusList;
import com.hua.library3.service.ManagerStatusService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class ManagerStatusServiceImpl implements ManagerStatusService {
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();
    
    @Override
    public List<ManagerStatus> getAllManagerStatus() {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/managerstatus");
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;
        else {
            ManagerStatusList managerStatusList = response.getEntity(ManagerStatusList.class);
            return managerStatusList.getList();
        }    
    }
}
