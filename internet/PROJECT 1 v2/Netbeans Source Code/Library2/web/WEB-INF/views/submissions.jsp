<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet"
href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8">
        <title>LIBRARY 2 Application</title>
    </head>
    <body style="background-color: antiquewhite">
        <div class="col-sm-offset-1 col-sm-11">
            <h1>${issuingRole} Dissertations</h1>         
        </div>
        <!-- I create 1 boolean JSTL variable -->
        <c:set var="isLibrarian" scope="request" value="${issuingRole == 'Librarian'}"/>
        <hr>
        <table class="table table-bordered col-sm-12" style="background-color: aliceblue">
                <tr>
                    <th class="col-md-1" style="text-align: center">Dissertation ID</th>
                    <th class="col-md-1" style="text-align: center">User ID</th>
                    <th class="col-md-1" style="text-align: center">Student Name</th>
                    <th class="col-md-1" style="text-align: center">Title</th>
                    <th class="col-md-1" style="text-align: center">Supervisor</th>
                    <th class="col-md-1" style="text-align: center">Submission Status</th>
                    <th class="col-md-1" style="text-align: center">Pages</th>
                    <th class="col-md-1" style="text-align: center">PDF</th>
                    <th class="col-md-1" style="text-align: center">Director Status</th>
                    <th class="col-md-1" style="text-align: center">Manager Status</th>                    
                    <th class="col-md-1" style="text-align: center">Actions</th>
                </tr>
            <c:forEach items="${dissertations}" var="dissertation">
                <tr>
                    <td>${dissertation.dissertationId}</td>
                    <td >${dissertation.userId}</td>
                    <td>${dissertation.student_name}</td>
                    <td>${dissertation.title}</td>
                    <td >${dissertation.supervisor}</td>
                    <td >${dissertation.submission_status}</td>
                    <td>${dissertation.pdf_pages}</td>
                    <td>${dissertation.ispdfloaded}</td>
                    <td>${dissertation.getDirectorStatus()}</td>
                    <td>${dissertation.manager_status}</td>
                    <td> <a href=" <spring:url value= "/${classRequestPath}/dissertations/modify?dissertationId=${dissertation.dissertationId}"/>" class="btn btn-warning">
                       Modify</a>
                    </td>
                </tr>
            </c:forEach>
            </table>
        <div id="buttons" class="col-sm-offset-1 col-sm-8">
            <hr>
            <p>
                <c:choose>
                    <c:when test="${isLibrarian == false}"> 
                    </c:when>
                    <c:otherwise>
                        <a href="<spring:url value="/${classRequestPath}/dissertations/add"/>"  class="btn btn-primary">
                        <span></span>New</a>
                    </c:otherwise>
                </c:choose>
                <a href="<spring:url value="/${classRequestPath}"/>"  class="btn btn-primary">
                    <span></span>Back
                </a>
            </p>            
        </div>
        <div class="alert alert-warning col-sm-offset-1 col-sm-8">${errorMessage}</div>
    </body>
</html>
