<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>     
        <link rel="stylesheet"            
              href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LIBRARY Application</title>
<script>
    function myFunction() {
        if (document.getElementById("librarianstatusId").value == 5)
            document.getElementById("btnAdd").disabled = false;
        else
            document.getElementById("btnAdd").disabled = true;
    }
</script>     </head>
    <body style="background-color: moccasin">
        <div class="col-sm-offset-2 col-sm-10">
            <h1>New Dissertation</h1>         
        </div>
                <!-- I create 1 boolean JSTL variable -->
        <c:set var="firstSubmission" scope="request" value="${newDissertation.lastSubmission.submissionNo == 1}"/>

    <form:form id="frmDissertation" method="post" enctype="multipart/form-data" modelAttribute="newDissertation" class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">#ID:</label>
            <div class="col-sm-1">
                <form:input id="id" path="id" type="text" class="form-control" readonly="true"/>
            </div>
            <label class="control-label col-sm-offset-3 col-sm-2" for="id">User ID:</label>
            <div class="col-sm-1">
                <form:input id="userId" path="userId" type="text" class="form-control" readonly="true"/>
            </div>
            <!-- Check if the following is necessary -->
            <form:hidden id="studentId" path="studentId"></form:hidden>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Dissertation ID:</label>
            <div class="col-sm-1">
            <form:input id="dissertationId" path="dissertationId" type="text" class="form-control" readonly="${firstSubmission == false}"/>
            </div>
            <label class="control-label col-sm-offset-3 col-sm-2" for="id">Student Name:</label>
            <div class="col-sm-3">
                <form:input id="studentname" path="studentname" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Can Submit Online:</label>
            <div class="col-sm-4">
                True <form:radiobutton path="studentcansubmitonline" value="true" disabled="true"/>
                False <form:radiobutton path="studentcansubmitonline" value="false" disabled="true"/>                
            </div>
            <label class="control-label col-sm-2" for="id">Student Email:</label>
            <div class="col-sm-3">
                <form:input id="studentemail" path="studentemail" type="text" class="form-control" readonly="true"/>
            </div>
        </div>  
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Title:</label>
            <div class="col-sm-4">
                <form:input id="title" path="title" type="text" class="form-control" readonly="${firstSubmission == false}"/>
            </div>
            <label class="control-label col-sm-2" for="id">Supervisor:</label>
            <div class="col-sm-3">
                <form:input id="supervisor" path="supervisor" type="text" class="form-control" readonly="${firstSubmission == false}"/>
            </div>
        </div>
        <div class="form-group">
            <!-- I have to add all Submission properties as hidden in order to get them by POST -->
            <form:hidden path="lastSubmission.dissertationId"></form:hidden>
            <label class="control-label col-sm-2" for="id">Submission No:</label>
            <div class="col-sm-1">
                <form:input id="submissionNo" path="lastSubmission.submissionNo" type="text" class="form-control" readonly="true"/>
            </div>
            <label class="control-label col-sm-offset-2 col-sm-2" for="id">Subject Areas:</label>
            <div class="col-sm-4">
                <form:textarea id="subject_areas" path="lastSubmission.subject_areas" rows="4" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">PDF Pages:</label>
            <div class="col-sm-1">
                <form:input type="hidden" id="pdf" path="lastSubmission.pdf" class="form-control"/>
                <form:input id="pdf_pages" path="lastSubmission.pdf_pages" type="text" class="form-control" readonly="true"/>
            </div>
            <label class="control-label col-sm-offset-2 col-sm-2" for="id">Submission Status:</label>
            <div class="col-sm-2">
                <form:select id="librarianstatusId" path="lastSubmission.librarianstatusId" class="form-control" onchange="myFunction()">
                    <form:options items="${allLibrarianStatus}" itemValue="id" itemLabel="status" />
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Director Approved:</label>
            <div class="checkbox col-sm-2">
                <form:checkbox path="lastSubmission.director_status" value="lastSubmission.director_status" disabled="true"/>
            </div>
            <label class="control-label col-sm-1" for="id">Director Notes:</label>
            <div class="col-sm-6">
                <form:textarea id="director_notes" path="lastSubmission.director_notes" rows="4" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Manager Approved:</label>
            <div class="col-sm-2">
                <form:select id="managerstatusId" path="lastSubmission.managerstatusId" class="form-control" disabled="true">
                            <form:options items="${allManagerStatus}" itemValue="id" itemLabel="status" />
                </form:select>
            </div>
            <label class="control-label col-sm-1" for="id">Manager Notes:</label>
            <div class="col-sm-6">
                <form:textarea id="manager_notes" path="lastSubmission.manager_notes" rows="4" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-8">
                <input type="submit" id="btnAdd" value ="Add" name="action" class="btn btn-primary"/>
                <a href="<spring:url value="/${classRequestPath}"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </div>
        </div>        
    </form:form>
    <div class="alert alert-warning col-sm-offset-1 col-sm-8">${errorMessage}</div>
    </body>
</html>

