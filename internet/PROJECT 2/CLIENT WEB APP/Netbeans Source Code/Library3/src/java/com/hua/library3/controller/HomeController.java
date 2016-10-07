package com.hua.library3.controller;

import com.hua.library3.domain.EventEmailRecipient;
import com.hua.library3.domain.Library;
import com.hua.library3.domain.Role;
import com.hua.library3.domain.User;
import com.hua.library3.service.EventEmailRecipientService;
import com.hua.library3.service.RoleService;
import com.hua.library3.service.UserService;
import com.sun.jersey.api.client.Client;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXB;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String CLASS_REQUEST_PATH = ""; // same as view name
    private static final String LIBRARYWEBREST_URL = "http://localhost:8080/LibraryWebREST/webresources/library";
    Client client = Client.create();
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EventEmailRecipientService eventEmailRecipientService;
    
    private static URI getBaseURI() {
        return UriBuilder.fromUri(LIBRARYWEBREST_URL).build();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String welcome(Model model) throws MalformedURLException, IOException {
        String url = LIBRARYWEBREST_URL;
        model.addAttribute("library", JAXB.unmarshal(url, Library.class));
        return "welcome";
    }
    
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String listLibraryEvents(Model model) {
        String url = LIBRARYWEBREST_URL;
        Library library = JAXB.unmarshal(url, Library.class);
        model.addAttribute("libraryevents", library.getLibraryEventsList());
        model.addAttribute("backBtnPath", "");
        return "libraryevents";
    }
  
    @RequestMapping(value = "/events/register", method = RequestMethod.GET)
    public String registerEventEmailRecipient(Model model) {
        EventEmailRecipient newEventEmailRecipient = new EventEmailRecipient();
        model.addAttribute("newEventEmailRecipient", newEventEmailRecipient);
        model.addAttribute("errorMessage", "");
        return "registereventemailrecipient";
    }

    @RequestMapping(value = "/events/register", method = RequestMethod.POST)
    public String processRegisterEventEmailRecipient(@ModelAttribute("newEventEmailRecipient") EventEmailRecipient newEventEmailRecipient,
            Model model) {
        try {
            eventEmailRecipientService.addEventEmailRecipient(newEventEmailRecipient);
        } catch (SQLException e) {
            model.addAttribute("newEventEmailRecipient", newEventEmailRecipient);
            model.addAttribute("errorMessage", e.getMessage());
            return "registereventemailrecipient";
        }
        return "redirect:/" + CLASS_REQUEST_PATH;
    } 
   
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        User identifyUser = new User();
        model.addAttribute("identifyUser", identifyUser);
        model.addAttribute("ErrorMessage", "");
        model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
        return "login";
    }
   
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(@ModelAttribute("identifyUser") User identifyUser,
            RedirectAttributes redirectAttrs, Model model) {
        User logonUser = userService.getUserByUserIdPwd(identifyUser.getUserId(), 
                                                    identifyUser.getPassword());
        if (logonUser == null) {
            model.addAttribute("identifyUser", identifyUser);
            model.addAttribute("ErrorMessage", "User does not exist or incorrect password!");
            return "login";
        } else if (logonUser.getRoleId() != Role.STUDENT_ID) {
            model.addAttribute("identifyUser", identifyUser);
            model.addAttribute("ErrorMessage", "User is not a student!");
            return "login";
        } else {
            // put logic based of user role participation
            redirectAttrs.addFlashAttribute("logonUser", logonUser);
            return "redirect:/" + roleService.getRoleById(logonUser.getRoleId()).getWebview() + "/validate";
        }
    } 
    
    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat,
                true);
        binder.registerCustomEditor(java.util.Date.class, orderDateEditor);
    } 
}