package com.hua.library3.service.impl;

import com.hua.library3.domain.Role;
import com.hua.library3.service.RoleService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();

    @Override
    public Role getRoleById(int id) {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/roles?id=" + id);
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;
        else
            return response.getEntity(Role.class);        
    }    
}
