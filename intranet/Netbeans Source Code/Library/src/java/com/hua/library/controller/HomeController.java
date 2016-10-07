package com.hua.library.controller;

import com.hua.library.domain.User;
import com.hua.library.service.RoleService;
import com.hua.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    

    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model) {
        User identifyUser = new User();
        model.addAttribute("identifyUser", identifyUser);
        model.addAttribute("ErrorMessage", "");
        return "login";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String processLoginForm(@ModelAttribute("identifyUser") User identifyUser,
            RedirectAttributes redirectAttrs, Model model) {
        User logonUser = userService.getUserByUserIdPwd(identifyUser.getUserId(), 
                                                    identifyUser.getPassword());
        if (logonUser == null) {
            model.addAttribute("identifyUser", identifyUser);
            model.addAttribute("ErrorMessage", "User does not exist or incorrect password!");
            return "login";
        } else {
            // put logic based of user role participation
            redirectAttrs.addFlashAttribute("logonUser", logonUser);
            return "redirect:/" + roleService.getRoleById(logonUser.getRoleId()).getWebview() + "/validate";
        }
    } 
}
