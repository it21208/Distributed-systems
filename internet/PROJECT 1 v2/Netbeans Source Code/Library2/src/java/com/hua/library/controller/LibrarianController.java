package com.hua.library.controller;

import com.hua.library.domain.Dissertation;
import com.hua.library.domain.Role;
import com.hua.library.domain.Submission;
import com.hua.library.domain.User;
import com.hua.library.service.DissertationService;
import com.hua.library.service.ManagerStatusService;
import com.hua.library.service.RoleService;
import com.hua.library.service.UserService;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import com.hua.library.service.LibrarianStatusService;
import com.hua.library.service.SubmissionService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import javax.servlet.ServletContext;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private static final String ROLE_ALLOWED = "Librarian";
    private static final String CLASS_REQUEST_PATH = "librarian"; // same as view name
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DissertationService dissertationService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private LibrarianStatusService librarianStatusService;
    @Autowired
    private ManagerStatusService managerStatusService;
    @Autowired
    ServletContext context;

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
        if (isAuthenticated()) {
            return CLASS_REQUEST_PATH;
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String validate(@ModelAttribute("logonUser") User logonUser,
            Model model) {
        this.logonUser = logonUser;
        this.logonRole = roleService.getRoleById(logonUser.getRoleId());;
        return "redirect:/" + CLASS_REQUEST_PATH;
    }

//------------------- DISSERTATIONS -------------------------------------
    @RequestMapping("/dissertations")
    public String listDissertations(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            model.addAttribute("issuingRole", ROLE_ALLOWED);
            model.addAttribute("dissertations", dissertationService.getAllDissertations());
            return "dissertations";
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/searchstudent", method = RequestMethod.GET)
    public String searchStudentForm(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            model.addAttribute("errorMessage", "");
            return "searchStudent";
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/searchstudent", method = RequestMethod.POST)
    public String processSearchStudentForm(@RequestParam("userId") String userId, Model model) {
        if (isAuthenticated()) {
            String errorMessage = "";
            if (!userId.equals("")) {   // some userId was supplied
                User student = userService.getStudentByUserId(userId);  // get student with userId
                if (student != null) {  // student with userId was found
                    return "redirect:/" + CLASS_REQUEST_PATH + "/dissertations/loadpdf?userId=" + userId
                            + "&id=-1";
                } else {                // no student with userId was found
                    errorMessage = String.format("There exists no student with user ID = %s", userId);
                    model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
                    model.addAttribute("errorMessage", errorMessage);
                    return "searchStudent";
                }
            } else {                    // no userId was supplied
                errorMessage = "No user id was specified!";
                model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
                model.addAttribute("errorMessage", errorMessage);
                return "searchStudent";
            }

        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/loadpdf", method = RequestMethod.GET)
    public String loadPDFForm(@RequestParam("userId") String userId,
            @RequestParam("id") int id, Model model) {
        if (isAuthenticated()) {
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            model.addAttribute("errorMessage", "");
            model.addAttribute("userId", userId);
            model.addAttribute("id", id);
            return "loadpdf";
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/loadpdf", method = RequestMethod.POST)
    public String processLoadPDFForm(@RequestParam("userId") String userId,
            @RequestParam("id") int id, @RequestParam("pdfFile") MultipartFile pdfFile,
            RedirectAttributes redirectAttrs, Model model) {
        if (isAuthenticated()) {
            String errorMessage = "";
            if (!pdfFile.isEmpty()) {
                try {
                    byte[] bytes = pdfFile.getBytes();
                    File storeFile = new File("pdf_" + userId + ".pdf");
                    BufferedOutputStream stream
                            = new BufferedOutputStream(new FileOutputStream(storeFile));
                    stream.write(bytes);
                    stream.close();
                    int count = PDDocument.load(storeFile).getNumberOfPages();
                    if ((count >= 60) && (count <= 260)) {
                        //model.addAttribute("pdf", bytes);
                        redirectAttrs.addFlashAttribute("pdf", bytes);
                        if (id == -1) { // this is a new dissertation
                            return "redirect:/" + CLASS_REQUEST_PATH
                                    + "/dissertations/add?userId=" + userId
                                    + "&pdfpages=" + count;
                        } else { // it is an existing dissertation, go add a new submission
                            return "redirect:/" + CLASS_REQUEST_PATH
                                    + "/dissertations/submission/add?id=" + id
                                    + "&pdfpages=" + count;
                        }

                    } else {
                        errorMessage = "Error: The PDF has " + count + " pages. Allowed range is [60,260] pages!";
                        model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
                        model.addAttribute("errorMessage", errorMessage);
                        model.addAttribute("userId", userId);
                        return "loadpdf";
                    }
                } catch (Exception e) {
                    errorMessage = "You failed to upload the pdf file => " + e.getMessage();
                    model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
                    model.addAttribute("errorMessage", errorMessage);
                    model.addAttribute("userId", userId);
                    return "loadpdf";
                }
            } else {
                errorMessage = "You failed to upload the pdf because the file was empty.";
                model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("userId", userId);
                return "loadpdf";
            }
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/add", method = RequestMethod.GET)
    public String getAddNewDissertationForm(@RequestParam("userId") String userId,
            @RequestParam("pdfpages") int pdfpages, @ModelAttribute("pdf") byte[] pdf, Model model) {
        if (isAuthenticated()) {
            Dissertation newDissertation = new Dissertation();

            User student = userService.getStudentByUserId(userId);
            newDissertation.setStudentId(student.getId());
            newDissertation.setUserId(userId);
            newDissertation.setStudentname(student.getName());
            newDissertation.setStudentcansubmitonline(student.isOnlinesubmit());
            newDissertation.setStudentemail(student.getEmail());

            newDissertation.setLastSubmission(new Submission());
            newDissertation.getLastSubmission().setSubmissionNo(1);
            newDissertation.getLastSubmission().setPdf(pdf);
            newDissertation.getLastSubmission().setPdf_pages(pdfpages);
            model.addAttribute("newDissertation", newDissertation);
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("errorMessage", "");
            return "addDissertation";
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/add", method = RequestMethod.POST)
    public String processAddNewDissertationForm(@ModelAttribute("newDissertation") Dissertation newDissertation,
            Model model) {
        String errorMessage = "";
        try {
            dissertationService.addDissertation(newDissertation);
        } catch (SQLException e) {
            errorMessage = e.getMessage();
            model.addAttribute("newDissertation", newDissertation);
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("errorMessage", errorMessage);
            return "addDissertation";
        }
        return "redirect:/" + CLASS_REQUEST_PATH + "/dissertations";
    }

    @RequestMapping(value = "/dissertations/inform", method = RequestMethod.GET)
    public String getInformDissertationForm(@RequestParam("dissertationId") String dissertationId,
            Model model) {
        if (isAuthenticated()) {
            Dissertation modifyDissertation = dissertationService.getDissertationByDissId(dissertationId);
            model.addAttribute("modifyDissertation", modifyDissertation);
            model.addAttribute("dissertationId", dissertationId);
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("backBthURL", "/" + CLASS_REQUEST_PATH + "/dissertations");
            model.addAttribute("issuingRole", ROLE_ALLOWED);
            model.addAttribute("errorMessage", "");
            return "modifyDissertation";
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/inform", method = RequestMethod.POST)
    public String processInformDissertationForm(@ModelAttribute("modifyDissertation") Dissertation modifyDissertation,
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
            submissionService.modifySubmission(modifyDissertation.getLastSubmission());
        } catch (SQLException e) {
            errorMessage = e.getMessage();
            model.addAttribute("modifyDissertation", modifyDissertation);
            model.addAttribute("dissertationId", dissertationId);
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("backBthURL", "/" + CLASS_REQUEST_PATH + "/dissertations");
            model.addAttribute("issuingRole", ROLE_ALLOWED);
            model.addAttribute("errorMessage", errorMessage);
            return "modifyDissertation";
        }
        return "redirect:/" + CLASS_REQUEST_PATH + "/dissertations";
    }

    @RequestMapping(value = "/dissertations/submission/add", method = RequestMethod.GET)
    public String getAddNewDissertationSubmissionForm(@RequestParam("id") int id,
            @RequestParam("pdfpages") int pdfpages,
            @ModelAttribute("pdf") byte[] pdf, Model model) {
        if (isAuthenticated()) {
            Dissertation dissertation = dissertationService.getDissertationById(id);
            Submission newSubmission = new Submission();
            newSubmission.setDissertationId(id);
            newSubmission.setSubmissionNo(dissertation.getLastSubmission().getSubmissionNo() + 1);
            newSubmission.setPdf(pdf);
            newSubmission.setPdf_pages(pdfpages);
            dissertation.setLastSubmission(newSubmission);
            model.addAttribute("newDissertation", dissertation);
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("errorMessage", "");
            return "addDissertation";
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/dissertations/submission/add", method = RequestMethod.POST)
    public String processAddNewDissertationSubmissionForm(@ModelAttribute("newDissertation") Dissertation newDissertation,
            Model model) {
        String errorMessage = "";
        try {
            submissionService.addSubmission(newDissertation.getLastSubmission());
        } catch (SQLException e) {
            errorMessage = e.getMessage();
            model.addAttribute("newDissertation", newDissertation);
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("errorMessage", errorMessage);
            return "addDissertation";
        }
        return "redirect:/" + CLASS_REQUEST_PATH + "/dissertations";
    }

    @RequestMapping(value = "/dissertations/modify", method = RequestMethod.GET)
    public String getModifyDissertationForm(@RequestParam("id") int id,
            Model model) {
        if (isAuthenticated()) {
            Dissertation modifyDissertation = dissertationService.getDissertationById(id);
            model.addAttribute("modifyDissertation", modifyDissertation);
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("backBthURL", "/" + CLASS_REQUEST_PATH + "/dissertations");
            model.addAttribute("issuingRole", ROLE_ALLOWED);
            return "modifyDissertation";
        } else
            return logout();
    }    
    
    @RequestMapping(value = "/dissertations/modify", method = RequestMethod.POST)
    public String processModifyDissertationForm(@ModelAttribute("modifyDissertation") Dissertation modifyDissertation,
            Model model) {
        String errorMessage = "";        
        try {
            submissionService.modifySubmission(modifyDissertation.getLastSubmission());
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
