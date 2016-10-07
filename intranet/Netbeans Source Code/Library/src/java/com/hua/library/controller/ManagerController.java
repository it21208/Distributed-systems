package com.hua.library.controller;

import com.hua.library.domain.Dissertation;
import com.hua.library.domain.Role;
import com.hua.library.domain.User;
import com.hua.library.service.DissertationService;
import com.hua.library.service.ManagerStatusService;
import com.hua.library.service.RoleService;
import com.hua.library.service.SubmissionStatusService;
import com.hua.library.service.UserService;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    private static final String ROLE_ALLOWED = "Manager";
    private static final String CLASS_REQUEST_PATH = "manager"; // same as view name
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DissertationService dissertationService;
    @Autowired
    private SubmissionStatusService submissionStatusService;
    @Autowired
    private ManagerStatusService managerStatusService;

    private User logonUser = null;
    private Role logonRole = null;     

    private boolean isAuthenticated() {
        Boolean isOK = (logonUser != null) && (logonRole != null) && (logonRole.getName() != null);
        return isOK && (logonRole.getName().equalsIgnoreCase(ROLE_ALLOWED));
    }
    
    @RequestMapping("/logout")
    private String logout() {
        logonUser = null;
        logonRole = null;
        return "redirect:/";
    }

    @RequestMapping()
    public String roleConsole() {
        if (isAuthenticated())
            return CLASS_REQUEST_PATH;
        else
            return logout();
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String validate(@ModelAttribute("logonUser") User logonUser, 
            Model model) {
        this.logonUser = logonUser;
        this.logonRole =  roleService.getRoleById(logonUser.getRoleId());;  
        return "redirect:/" + CLASS_REQUEST_PATH;
    } 
    
//------------------- DISSERTATIONS -------------------------------------
    @RequestMapping("/dissertations")
    public String listDissertations(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            model.addAttribute("issuingRole", ROLE_ALLOWED);
            model.addAttribute("dissertations", dissertationService.getAllManagerDissertations());
            return "dissertations";
        } else
            return logout();    
    }    
    
    @RequestMapping(value = "/dissertations/modify", method = RequestMethod.GET)
    public String getModifyDissertationForm(@RequestParam("dissertationId") String dissertationId,
            Model model) {
        if (isAuthenticated()) {
            Dissertation modifyDissertation = dissertationService.getDissertationByDissId(dissertationId);
            model.addAttribute("modifyDissertation", modifyDissertation);
            model.addAttribute("dissertationId", dissertationId);
            model.addAttribute("allSubmissionStatus", submissionStatusService.getAllSubmissionStatus());
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("backBthURL", "/" + CLASS_REQUEST_PATH + "/dissertations");
            model.addAttribute("issuingRole", ROLE_ALLOWED);
            return "modifyDissertation";
        } else
            return logout();
    }    
    
    @RequestMapping(value = "/dissertations/modify", method = RequestMethod.POST)
    public String processModifyDissertationForm(@ModelAttribute("modifyDissertation") Dissertation modifyDissertation,
            @ModelAttribute("dissertationId") String dissertationId, Model model) {
        // check if the old dissertationId value is used as prefix and remove it
        if (modifyDissertation.getDissertationId().contains(",")) {
            // remove the old dissertationId and the comma from the modifyDissertation.dissertationId
            java.util.Scanner sc = new Scanner(modifyDissertation.getDissertationId());
            sc.useDelimiter(",");
            sc.next();
            modifyDissertation.setDissertationId(sc.next());
        }
        String errorMessage = "";        
        try {
            dissertationService.modifyDissertation(modifyDissertation);
        } catch(SQLException e){
            errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage); 
            return "forward:/" + CLASS_REQUEST_PATH + "/dissertations";            
        }
        return "redirect:/" + CLASS_REQUEST_PATH + "/dissertations";
    }    
    
    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat,
                true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }    
}
