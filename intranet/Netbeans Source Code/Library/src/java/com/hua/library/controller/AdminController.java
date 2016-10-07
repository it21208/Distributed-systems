package com.hua.library.controller;

import com.hua.library.domain.Role;
import com.hua.library.domain.ServiceRight;
import com.hua.library.domain.User;
import com.hua.library.service.RoleService;
import com.hua.library.service.ServiceRightService;
import com.hua.library.service.UserService;
import java.sql.SQLException;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Scanner;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String ROLE_ALLOWED = "Administrator";
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ServiceRightService serviceRightService;
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
            return "admin";
        else
            return logout();
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String validate(@ModelAttribute("logonUser") User logonUser, 
            Model model) {
        this.logonUser = logonUser;
        this.logonRole =  roleService.getRoleById(logonUser.getRoleId());;  
        return "redirect:/admin";
    } 
//------------------- USERS -------------------------------------
    //TODO : Get Role name for Role ID
    @RequestMapping("/users")
    public String listUsers(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("users", userService.getAllUsers());
            return "users";
        } else
            return logout();    
    }
    
    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String getAddNewUserForm(Model model) {
        if (isAuthenticated()) {
            User newUser = new User();
            model.addAttribute("newUser", newUser);
            model.addAttribute("roleOptionSet", roleService.roleOptionSet());
            return "addUser";
        } else 
            return logout();
    }    

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String processAddNewUserForm(@ModelAttribute("newUser") User newUser,
            Model model) {
        String errorMessage = "";        
        try {
            userService.addUser(newUser);
        } catch (SQLException e) {
            errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage); 
            return "forward:/admin/users";
        }
        return "redirect:/admin/users";
    }
 
    @RequestMapping(value = "/users/modify", method = RequestMethod.GET)
    public String getModifyUserForm(@RequestParam("userId") String userId,
            Model model) {
        if (isAuthenticated()) {
            User modifyUser = userService.getUserByUserId(userId);
            model.addAttribute("modifyUser", modifyUser);
            model.addAttribute("userId", userId);
            model.addAttribute("roleOptionSet", roleService.roleOptionSet(modifyUser.getRoleId()));
            return "modifyUser";
        } else
            return logout();
    }    

    @RequestMapping(value = "/users/modify", method = RequestMethod.POST)
    public String processModifyUserForm(@ModelAttribute("modifyUser") User modifyUser,
            @ModelAttribute("userId") String userId, Model model) {
        // check if the old userId value is used as prefix and remove it
        if (modifyUser.getUserId().contains(",")) {
            // remove the old userId and the comma from the modifyUser.userId
            java.util.Scanner sc = new Scanner(modifyUser.getUserId());
            sc.useDelimiter(",");
            sc.next();
            modifyUser.setUserId(sc.next());
        }
        String errorMessage = "";        
        try {
            userService.modifyUser(modifyUser);
        } catch(SQLException e){
            errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage); 
            return "forward:/admin/users";            
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.GET)
    public String getDeleteUserForm(@RequestParam("userId") String userId,
            Model model) {
        if (isAuthenticated()) {
            User deleteUser = userService.getUserByUserId(userId);
            model.addAttribute("deleteUser", deleteUser);
            model.addAttribute("roleOptionSet", roleService.roleOptionSet(deleteUser.getRoleId()));
            return "deleteUser";
        } else
            return logout();
    }    

    @RequestMapping(value = "/users/delete", method = RequestMethod.POST)
    public String processDeleteUserForm(@ModelAttribute("deleteUser") User deleteUser,
            Model model) {
        // check if the old userId value is used as prefix and remove it
        if (deleteUser.getUserId().contains(",")) {
            // remove the old userId and the comma from the modifyUser.userId
            java.util.Scanner sc = new Scanner(deleteUser.getUserId());
            sc.useDelimiter(",");
            sc.next();
            deleteUser.setUserId(sc.next());
        }        
        String errorMessage = "";        
        try {
            userService.deleteUser(deleteUser);
        } catch (SQLException e) {
            errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage); 
            return "forward:/admin/users";            
        }
        return "redirect:/admin/users";
    }    

//------------------- ROLES -------------------------------------
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("ErrorMessage", "");
            return "roles";
        } else
            return logout();
    }

    @RequestMapping(value = "/roles/add", method = RequestMethod.GET)
    public String getAddNewRoleForm(Model model) {
        if (isAuthenticated()) {
            Role newRole = new Role();
            model.addAttribute("newRole", newRole);
            return "addRole";
        } else
            return logout();
    }    

    @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
    public String processAddNewRoleForm(@ModelAttribute("newRole") Role newRole) {
        roleService.addRole(newRole);
        return "redirect:/admin/roles";
    }

    @RequestMapping(value = "/roles/delete", method = RequestMethod.GET)
    public String getDeleteRoleForm(@RequestParam("id") int id,
            Model model) {
        if (isAuthenticated()) {
            Role deleteRole = roleService.getRoleById(id);
            model.addAttribute("deleteRole", deleteRole);
            model.addAttribute("ErrorMessage", "");
            return "deleteRole";
        } else
            return logout();
    }    

    @RequestMapping(value = "/roles/delete", method = RequestMethod.POST)
    public String processDeleteRoleForm(@ModelAttribute("deleteRole") Role deleteRole,
            Model model) {
        String errorMessage = "";
        try {
            roleService.deleteRole(deleteRole);
        } catch (SQLException e) {
            errorMessage = String.format("Role %s has dependent users and cannot be deleted!", deleteRole.getName());
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("ErrorMessage", errorMessage);
            return "roles";
        }
        return "redirect:/admin/roles";
    }  
    
    @RequestMapping(value = "/roles/services", method = RequestMethod.GET)
    public String getModifyRoleServiceRightsForm(@RequestParam("id") int id,
            Model model) {
        if (isAuthenticated()) {
            Role modifyRole = roleService.getRoleById(id);
            model.addAttribute("modifyRole", modifyRole);
            return "modifyRoleServiceRights";
        } else
            return logout();
    }    

    @RequestMapping(value = "/roles/services", method = RequestMethod.POST)
    public String processModifyRoleServiceRightsForm(@ModelAttribute("modifyRole") Role modifyRole,
            Model model) {
        roleService.updateRoleServices(modifyRole);
        return "redirect:/admin/roles";
    }     
    
//------------------- SERVICERIGHTS -------------------------------------
    @RequestMapping("/servicerights")
    public String listServiceRights(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("serviceRights", serviceRightService.getAllServiceRights());
            model.addAttribute("ErrorMessage", "");
            return "serviceRights";
        } else
            return logout();
    }

    @RequestMapping(value = "/servicerights/add", method = RequestMethod.GET)
    public String getAddNewServiceRightForm(Model model) {
        if (isAuthenticated()) {
            ServiceRight newServiceRight = new ServiceRight();
            model.addAttribute("newServiceRight", newServiceRight);
            return "addServiceRight";
        } else
            return logout();
    }    

    @RequestMapping(value = "/servicerights/add", method = RequestMethod.POST)
    public String processAddNewServiceRightForm(@ModelAttribute("newServiceRight") ServiceRight newServiceRight) {
        serviceRightService.addServiceRight(newServiceRight);
        return "redirect:/admin/servicerights";
    }

    @RequestMapping(value = "/servicerights/delete", method = RequestMethod.GET)
    public String getDeleteServiceRightForm(@RequestParam("id") int id,
            Model model) {
        if (isAuthenticated()) {
            ServiceRight deleteServiceRight = serviceRightService.getServiceRightById(id);
            model.addAttribute("deleteServiceRight", deleteServiceRight);
            model.addAttribute("ErrorMessage", "");
            return "deleteServiceRight";
        } else
            return logout();
    }    

    @RequestMapping(value = "/servicerights/delete", method = RequestMethod.POST)
    public String processDeleteServiceRightForm(@ModelAttribute("deleteServiceRight") ServiceRight deleteServiceRight,
            Model model) {
        String errorMessage = "";
        try {
            serviceRightService.deleteServiceRight(deleteServiceRight);
        } catch (SQLException e) {
            errorMessage = String.format("Service %s has dependent roles and cannot be deleted!", deleteServiceRight.getName());
            model.addAttribute("serviceRights", serviceRightService.getAllServiceRights());
            model.addAttribute("ErrorMessage", errorMessage);
            return "serviceRights";
        }
        return "redirect:/admin/servicerights";
    }
}