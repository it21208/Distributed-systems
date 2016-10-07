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
            <h1>Modify Dissertation</h1>         
        </div>
        <!-- I create 3 boolean JSTL variables -->
        <c:set var="isLibrarian" scope="request" value="${issuingRole == 'Librarian'}"/>
        <c:set var="isDirector" scope="request" value="${issuingRole == 'Director'}"/>
        <c:set var="isManager" scope="request" value="${issuingRole == 'Manager'}"/>
        <c:set var="canNotifyStudent" scope="request" value="${modifyDissertation.canNotifyStudent() && isLibrarian}"/>
        <form:form id="frmModifyDissertation" modelAttribute="modifyDissertation" class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">#ID:</label>
            <div class="col-sm-1">
                <form:input id="id" path="id" type="text" class="form-control" readonly="true"/>
            </div>
            <label class="control-label col-sm-offset-3 col-sm-2" for="id">User ID:</label>
            <div class="col-sm-1">
                <form:input id="userId" path="userId" type="text" class="form-control" readonly="true" cssStyle="background-color: #f2f2f2; font-style: italic"/>
            </div>
            <form:hidden id="studentId" path="studentId"></form:hidden>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Dissertation ID:</label>
            <div class="col-sm-1">
            <form:input id="dissertationId" path="dissertationId" type="text" class="form-control" readonly="${isLibrarian == false}"/>
            </div>
            <label class="control-label col-sm-offset-3 col-sm-2" for="id">Student Name:</label>
            <div class="col-sm-3">
                <form:input id="student_name" path="student_name" type="text" class="form-control" readonly="true" cssStyle="background-color: #f2f2f2; font-style: italic"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Title:</label>
            <div class="col-sm-4">
                <form:input id="title" path="title" type="text" class="form-control" readonly="${isLibrarian == false}"/>
            </div>
            <label class="control-label col-sm-2" for="id">Supervisor:</label>
            <div class="col-sm-3">
                <form:input id="supervisor" path="supervisor" type="text" class="form-control" readonly="${isLibrarian == false}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Subject Areas:</label>
            <div class="col-sm-4">
                <form:textarea id="subject_areas" path="subject_areas" rows="4" class="form-control" readonly="${isDirector}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">PDF Loaded </label>
            <div class="checkbox col-sm-1">
                <c:choose>
                    <c:when test="${isLibrarian == false}">
                        <form:input path="ispdfloaded" readonly="true" cssStyle="background-color: #f2f2f2; font-style: italic"/>
                    </c:when>
                    <c:otherwise>
                            <form:checkbox path="ispdfloaded"/>
                    </c:otherwise>
                </c:choose>  
            </div>
            <label class="control-label col-sm-1" for="id">PDF Pages:</label>
            <div class="col-sm-1">
                <form:input id="pdf_pages" path="pdf_pages" type="text" class="form-control" readonly="${isLibrarian == false}"/>
            </div>
            <label class="control-label col-sm-offset-1 col-sm-2" for="id">Submission Status:</label>
            <div class="col-sm-2">
                <c:choose>
                    <c:when test="${isLibrarian == false}">
                        <form:hidden path="submissionstatusId" readonly="true"/>
                        <form:input path="submission_status" readonly="true" cssStyle="background-color: #f2f2f2; font-style: italic"/>
                    </c:when>
                    <c:otherwise>
                        <form:select id="submissionstatusId" path="submissionstatusId" class="form-control">
                            <form:options items="${allSubmissionStatus}" itemValue="id" itemLabel="status" />
                        </form:select>
                    </c:otherwise>
                </c:choose>  
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Director Approved </label>
            <div class="checkbox col-sm-1">
                <c:choose>
                    <c:when test="${isDirector == false}">
                        <form:input path="director_status" readonly="true" cssStyle="background-color: #f2f2f2; font-style: italic"/>
                    </c:when>
                    <c:otherwise>
                            <form:checkbox path="director_status"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <label class="control-label col-sm-1" for="id">Director Notes:</label>
            <div class="col-sm-6">
                <form:textarea id="director_notes" path="director_notes" rows="4" class="form-control" readonly="${isDirector == false}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Manager Approved Status</label>
            <div class="col-sm-2">
                <c:choose>
                    <c:when test="${isManager == false}">
                        <form:hidden path="managerstatusId" readonly="true"/>
                        <form:input path="manager_status" readonly="true" cssStyle="background-color: #f2f2f2; font-style: italic"/>
                    </c:when>
                    <c:otherwise>
                        <form:select id="managerstatusId" path="managerstatusId" class="form-control">
                            <form:options items="${allManagerStatus}" itemValue="id" itemLabel="status" />
                        </form:select>
                    </c:otherwise>
                </c:choose>                  
            </div>
            <label class="control-label col-sm-1" for="id">Manager Notes:</label>
            <div class="col-sm-6">
                <form:textarea id="manager_notes" path="manager_notes" rows="4" class="form-control" readonly="${isManager == false}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="id">Student Notification By:</label>
            <div class="col-sm-4">
                <c:choose>
                    <c:when test="${canNotifyStudent == false}">
                        <form:hidden path="studentinformmethod"/>
                        <input type="text" value="${modifyDissertation.getStudentinformmethodLong()}" readonly="true" style="background-color: #f2f2f2; font-style: italic"/>
                    </c:when>
                    <c:otherwise>
                        Email <form:radiobutton path="studentinformmethod" value="E"/>
                        Telephone <form:radiobutton path="studentinformmethod" value="T"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <label class="control-label col-sm-2" for="id">Notification Date:</label>
            <div class="col-sm-3">
                <form:input path="studentinformdate" type="text" class="form-control" readonly="${canNotifyStudent == false}"/>
            </div>
        </div>  
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-8">
                <input type="submit" id="btnModify" value ="Modify" class="btn btn-primary"/>
                <a href="<spring:url value="${backBthURL}"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </div>
        </div>        
    </form:form>
    <div class="alert alert-warning col-sm-offset-1 col-sm-8">${errorMessage}</div>
    </body>
</html>

