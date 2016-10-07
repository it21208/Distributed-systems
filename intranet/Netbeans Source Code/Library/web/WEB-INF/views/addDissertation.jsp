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
    </head>
    <body style="background-color: antiquewhite">
        <div class="col-sm-offset-2 col-sm-10">
            <h1>New Dissertation</h1>         
        </div>
    <form:form modelAttribute="newDissertation" class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">#ID:</label>
            <div class="col-sm-1">
                <form:input id="id" path="id" type="text" class="form-control" readonly="true"/>
            </div>
            <label class="control-label col-sm-offset-3 col-sm-2" for="id">User ID:</label>
            <div class="col-sm-1">
                <form:input id="userId" path="userId" type="text" class="form-control"/>
            </div>
            <input type="submit" id="btnSearch" value="Search" name="search" class="btn btn-primary"/>
            <form:hidden id="studentId" path="studentId"></form:hidden>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Dissertation ID:</label>
            <div class="col-sm-1">
                <form:input id="dissertationId" path="dissertationId" type="text" class="form-control"/>
            </div>
            <label class="control-label col-sm-offset-3 col-sm-2" for="id">Student Name:</label>
            <div class="col-sm-3">
                <form:input id="student_name" path="student_name" type="text" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Title:</label>
            <div class="col-sm-4">
                <form:input id="title" path="title" type="text" class="form-control"/>
            </div>
            <label class="control-label col-sm-2" for="id">Supervisor:</label>
            <div class="col-sm-3">
                <form:input id="supervisor" path="supervisor" type="text" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Subject Areas:</label>
            <div class="col-sm-4">
                <form:textarea id="subject_areas" path="subject_areas" rows="4" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">PDF Loaded </label>
            <div class="checkbox col-sm-1">
                <form:checkbox path="ispdfloaded" value="ispdfloaded"/>
            </div>
            <label class="control-label col-sm-1" for="id">PDF Pages:</label>
            <div class="col-sm-1">
                <form:input id="pdf_pages" path="pdf_pages" type="text" class="form-control"/>
            </div>
            <label class="control-label col-sm-offset-1 col-sm-2" for="id">Submission Status:</label>
            <div class="col-sm-2">
                <form:select id="submissionstatusId" path="submissionstatusId" class="form-control">
                    <form:options items="${allSubmissionStatus}" itemValue="id" itemLabel="status" />
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Director Approved:</label>
            <div class="checkbox col-sm-2">
                <form:checkbox path="director_status" value="director_status" disabled="true"/>
            </div>
            <label class="control-label col-sm-1" for="id">Director Notes:</label>
            <div class="col-sm-6">
                <form:textarea id="director_notes" path="director_notes" rows="4" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Manager Approved:</label>
            <div class="col-sm-2">
                <form:select id="managerstatusId" path="managerstatusId" class="form-control" disabled="true">
                            <form:options items="${allManagerStatus}" itemValue="id" itemLabel="status" />
                </form:select>
            </div>
            <label class="control-label col-sm-1" for="id">Manager Notes:</label>
            <div class="col-sm-6">
                <form:textarea id="manager_notes" path="manager_notes" rows="4" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-8">
                <input type="submit" id="btnAdd" value ="Add" name="add" class="btn btn-primary"/>
                <a href="<spring:url value="/librarian/dissertations"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </div>
        </div>        
    </form:form>
    <div class="alert alert-warning col-sm-offset-1 col-sm-8">${errorMessage}</div>
    </body>
</html>

