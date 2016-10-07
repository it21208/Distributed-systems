/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hua.library.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author tioa
 */
@Controller
@RequestMapping("/filecontrol")
public class FileUploadController {
    @Autowired
    ServletContext context;
    
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public String uploadFile() {
        return "uploadfile";
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public String processUploadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ServletException {
        String message = "";
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            message = "Error: Form must has enctype=multipart/form-data.";
            /*
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush(); */
            return "message";
        }
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = context.getRealPath("") + 
                File.separator + UPLOAD_DIRECTORY;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // saves the file on disk
                        item.write(storeFile);
                        // count PDF pages
                        int count = PDDocument.load(storeFile).getNumberOfPages();
                        /*
                        request.setAttribute("message",
                                "Upload has been done successfully!"); */
                        message = "PDF upload has been done successfully in path " + uploadPath + "\nPDF has " + count + " pages";
                    }
                }
            }
        } catch (Exception ex) {
            /*
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage()); */
            message = "There was an error: " + ex.getMessage();
        }       
        /* context.getRequestDispatcher("/message.jsp").forward(request, response); */
        model.addAttribute("message", message);
        return "message";
    }
    
}
