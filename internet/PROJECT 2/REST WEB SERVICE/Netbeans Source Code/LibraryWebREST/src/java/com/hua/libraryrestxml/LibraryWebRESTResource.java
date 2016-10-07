/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hua.libraryrestxml;

import com.hua.library3.domain.Dissertation;
import com.hua.library3.domain.EventEmailRecipient;
import com.hua.library3.domain.Library;
import com.hua.library3.domain.Role;
import com.hua.library3.domain.User;
import com.hua.library3.domain.LibrarianStatusList;
import com.hua.library3.domain.ManagerStatusList;
import com.hua.library3.domain.Submission;
import com.hua.library3.domain.repository.DissertationRepository;
import com.hua.library3.domain.repository.EventEmailRecipientRepository;
import com.hua.library3.domain.repository.LibrarianStatusRepository;
import com.hua.library3.domain.repository.LibraryEventRepository;
import com.hua.library3.domain.repository.LibraryRepository;
import com.hua.library3.domain.repository.ManagerStatusRepository;
import com.hua.library3.domain.repository.RoleRepository;
import com.hua.library3.domain.repository.SubmissionRepository;
import com.hua.library3.domain.repository.UserRepository;
import com.hua.library3.domain.repository.impl.DissertationRepositoryImpl;
import com.hua.library3.domain.repository.impl.EventEmailRecipientRepositoryImpl;
import com.hua.library3.domain.repository.impl.LibrarianStatusRepositoryImpl;
import com.hua.library3.domain.repository.impl.LibraryEventRepositoryImpl;
import com.hua.library3.domain.repository.impl.LibraryRepositoryImpl;
import com.hua.library3.domain.repository.impl.ManagerStatusRepositoryImpl;
import com.hua.library3.domain.repository.impl.RoleRepositoryImpl;
import com.hua.library3.domain.repository.impl.SubmissionRepositoryImpl;
import com.hua.library3.domain.repository.impl.UserRepositoryImpl;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXB;

/**
 * REST Web Service
 *
 * @author tioa
 */
@Path("/library")
public class LibraryWebRESTResource {
    LibraryRepository libraryRepository = new LibraryRepositoryImpl();
    LibraryEventRepository libraryEventRepository = new LibraryEventRepositoryImpl();
    EventEmailRecipientRepository eventEmailRecipientRepository =
            new EventEmailRecipientRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    RoleRepository roleRepository = new RoleRepositoryImpl();
    LibrarianStatusRepository librarianStatusRepository = new LibrarianStatusRepositoryImpl();
    ManagerStatusRepository managerStatusRepository = new ManagerStatusRepositoryImpl();
    DissertationRepository dissertationRepository = new DissertationRepositoryImpl();
    SubmissionRepository submissionRepository = new SubmissionRepositoryImpl();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LibraryWebRESTResource
     */
    public LibraryWebRESTResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Library getLibrary() { 
        return libraryRepository.getLibrary();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/users")
    public User getUserByUserIdPwd(@QueryParam("userId") String userId,
            @QueryParam("password") String password) { 
        return userRepository.getUserByUserIdPwd(userId, password);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/roles")
    public Role getRoleById(@QueryParam("id") int id) { 
        return roleRepository.getRoleById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/librarianstatus")
    public LibrarianStatusList getAllLibrarianStatus() { 
        LibrarianStatusList librarianStatusList = new LibrarianStatusList();
        librarianStatusList.setList(librarianStatusRepository.getAllLibrarianStatus());
        return librarianStatusList;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/managerstatus")
    public ManagerStatusList getAllManagerStatus() { 
        ManagerStatusList managerStatusList = new ManagerStatusList();
        managerStatusList.setList(managerStatusRepository.getAllManagerStatus());
        return managerStatusList;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/dissertations")
    public Dissertation getDissertationByStudentId(@QueryParam("studentId") int studentId) { 
        Dissertation dissertation = dissertationRepository.getDissertationByStudentId(studentId);
        return dissertation;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/dissertations/{id}")
    public Dissertation getDissertationById(@PathParam("id") int id) { 
        Dissertation dissertation = dissertationRepository.getDissertationById(id);
        return dissertation;
    }  
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/events/register")
    public Response addEventEmailRecipient(EventEmailRecipient eventEmailRecipient) {
        try {
            eventEmailRecipientRepository.addEventEmailRecipient(eventEmailRecipient);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000"))
                return Response.status(Status.CONFLICT).entity("Error: Duplicate record").build();
            else
                return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.status(Status.CREATED).entity("Event Email Recipient successfully added").build();
    }    
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/submissions")
    public Response addSubmission(Submission submission) {
        try {
            submissionRepository.addSubmission(submission);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000"))
                return Response.status(Status.CONFLICT).entity("Error: Duplicate record").build();
            else
                return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.status(Status.CREATED).entity("Submission successfully added").build();
    }    
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/dissertations")
    public Response addDissertation(Dissertation dissertation) {
        try {
            dissertationRepository.addDissertation(dissertation);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000"))
                return Response.status(Status.CONFLICT).entity("Error: Duplicate record").build();
            else
                return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.status(Status.CREATED).entity("Dissertation successfully added").build();
    }    
}
