package com.hua.library3.service.impl;

import com.hua.library3.domain.User;
import com.hua.library3.service.UserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();
    
    @Override
    public User getUserByUserIdPwd(String userId, String password) {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/users?userId=" + userId + "&password=" + password);
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;
        else
            return response.getEntity(User.class);
    }    
}
