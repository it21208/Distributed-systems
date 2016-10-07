package com.hua.library3.controller;

import com.hua.library3.domain.Dissertation;
import com.hua.library3.domain.ManagerStatus;
import com.hua.library3.domain.Role;
import com.hua.library3.domain.Submission;
import com.hua.library3.domain.User;
import com.hua.library3.service.DissertationService;
import com.hua.library3.service.LibrarianStatusService;
import com.hua.library3.service.ManagerStatusService;
import com.hua.library3.service.RoleService;
import com.hua.library3.service.SubmissionService;
import com.hua.library3.service.UserService;
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.ServletContext;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {
    private static final String ROLE_ALLOWED = "Student";
    private static final String CLASS_REQUEST_PATH = "student"; // same as view name
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
    public String roleConsole(Model model) {
        if (isAuthenticated()) {
            // retrieve this student's dissertation
            Dissertation dissertation = dissertationService.getDissertationByStudentId(logonUser.getId());
            boolean existsDissertation = (dissertation != null);
            boolean enableAddDissertation = false;
            boolean enableAddSubmission = false;
            String dissertationStatusMessage = "";
            if (existsDissertation) {
                // if dissertation exists build dissertation status message
                int managerStatusId = dissertation.getLastSubmission().getManagerstatusId();
                switch (managerStatusId) {
                    case ManagerStatus.ACCEPTED_ID:
                        dissertationStatusMessage = "Your dissertation has been approved!";
                        break;
                    case ManagerStatus.REJECTED_ID:
                        dissertationStatusMessage = "Your dissertation has been rejected";
                        if (!dissertation.isStudentcansubmitonline()) {
                            dissertationStatusMessage += "\nPlease be informed that you have not registered for using the Online Submit option";
                        } else
                            enableAddSubmission = true;
                        break;
                    case ManagerStatus.NOTCHECKED_ID:
                        dissertationStatusMessage = "Your dissertation is still being reviewed";
                        break;
                }
            } else {
                enableAddDissertation = true;
                dissertation = new Dissertation();
                dissertation.setStudentId(logonUser.getId());
                dissertation.setUserId(logonUser.getUserId());
            }
            model.addAttribute("dissertation", dissertation);
            model.addAttribute("dissertationStatusMessage", dissertationStatusMessage);
            model.addAttribute("enableAddDissertation", enableAddDissertation);
            model.addAttribute("enableAddSubmission", enableAddSubmission);
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            return CLASS_REQUEST_PATH;
        } else {
            return logout();
        }
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String validate(@ModelAttribute("logonUser") User logonUser,
            Model model) {
        this.logonUser = logonUser;
        this.logonRole = roleService.getRoleById(logonUser.getRoleId());
        return "redirect:/" + CLASS_REQUEST_PATH;
    }
 
//------------------- DISSERTATIONS -------------------------------------

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

            newDissertation.setStudentId(logonUser.getId());
            newDissertation.setUserId(userId);
            newDissertation.setStudentname(logonUser.getName());
            newDissertation.setStudentcansubmitonline(logonUser.isOnlinesubmit());
            newDissertation.setStudentemail(logonUser.getEmail());

            newDissertation.setLastSubmission(new Submission());
            newDissertation.getLastSubmission().setSubmissionNo(1);
            newDissertation.getLastSubmission().setPdf(pdf);
            newDissertation.getLastSubmission().setPdf_pages(pdfpages);
            model.addAttribute("newDissertation", newDissertation);
            model.addAttribute("allManagerStatus", managerStatusService.getAllManagerStatus());
            model.addAttribute("allLibrarianStatus", librarianStatusService.getAllLibrarianStatus());
            model.addAttribute("errorMessage", "");
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
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
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            return "addDissertation";
        }
        return "redirect:/" + CLASS_REQUEST_PATH;
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
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
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
            model.addAttribute("classRequestPath", CLASS_REQUEST_PATH);
            return "addDissertation";
        }
        return "redirect:/" + CLASS_REQUEST_PATH;
    }
    
    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat,
                true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }
}
