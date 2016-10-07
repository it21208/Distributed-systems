package com.hua.library3.service.impl;

import com.hua.library3.domain.EventEmailRecipient;
import com.hua.library3.service.EventEmailRecipientService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import org.springframework.stereotype.Service;

@Service
public class EventEmailRecipientServiceImpl implements EventEmailRecipientService {

    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();

    @Override
    public void addEventEmailRecipient(EventEmailRecipient eventEmailRecipient) throws SQLException {
        WebResource webResource = client.resource(LIBRARYWEBREST_URL + "/events/register");
        StringWriter writer = new StringWriter();
        JAXB.marshal(eventEmailRecipient, writer);
        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, writer.toString());
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new SQLException(response.getEntity(String.class));
        }
    }
}
