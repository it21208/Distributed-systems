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
        <c:set var="isDirector" scope="request" value="${issuingRole == 'Director'}"/>
        <c:set var="isManager" scope="request" value="${issuingRole == 'Manager'}"/>
        <hr>
        <span class=" col-sm-offset-1 col-sm-10">
            <table class="table table-bordered" style="background-color: aliceblue">
                <thead style="background-color: cornflowerblue; color: whitesmoke">
                    <tr>
                        <th class="col-md-1" style="text-align: center">Dissertation ID</th>
                        <th class="col-md-2" style="text-align: center">Title</th>
                        <th class="col-md-1" style="text-align: center">Supervisor</th>
                        <th class="col-md-1" style="text-align: center">Student UserID</th>
                        <th class="col-md-2" style="text-align: center">Student Name</th>
                        <th class="col-md-1" style="text-align: center">Can Submit Online</th>
                        <th class="col-md-1" style="text-align: center">Student Email</th>
                        <th class="col-md-1" style="text-align: center">Actions</th>
                    </tr>
                </thead>
                <c:forEach items="${dissertations}" var="dissertation">
                    <c:set var="managerDecided" scope="request" value="${dissertation.lastSubmission.getManagerstatus() != 'Not Checked'}"/>
                    <c:set var="managerRejected" scope="request" value="${dissertation.lastSubmission.getManagerstatus() == 'Rejected'}"/>
                    <c:set var="studentInformed" scope="request" value="${dissertation.lastSubmission.getStudentinformdate() != null}"/>
                    <tr>
                        <td>${dissertation.dissertationId}</td>
                        <td>${dissertation.title}</td>
                        <td >${dissertation.supervisor}</td>
                        <td >${dissertation.userId}</td>
                        <td>${dissertation.studentname}</td>
                        <td>${dissertation.studentcansubmitonline}</td>
                        <td>${dissertation.studentemail}</td>
                        <td>
                            <c:choose>
                                <c:when test="${isLibrarian && managerDecided && !studentInformed}">
                                    <a href=" <spring:url value= "/${classRequestPath}/dissertations/inform?dissertationId=${dissertation.dissertationId}"/>" class="btn btn-warning">
                                        Inform Student</a>
                                    </c:when>
                                    <c:otherwise>                        
                                    </c:otherwise>
                                </c:choose>  
                                <c:choose>
                                    <c:when test="${isLibrarian && managerRejected && studentInformed}">
                                    <a href=" <spring:url value= "/${classRequestPath}/dissertations/loadpdf?userId=${dissertation.userId}&id=${dissertation.id}"/>" class="btn btn-warning">
                                        New Submission</a>
                                    </c:when>
                                    <c:otherwise>                        
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${isDirector || isManager || isLibrarian}">
                                    <a href=" <spring:url value= "/${classRequestPath}/dissertations/modify?id=${dissertation.id}"/>" class="btn btn-warning">
                                        Modify</a>
                                    </c:when>
                                    <c:otherwise>                        
                                    </c:otherwise>
                                </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="buttons">
                <hr>
                <p>
                    <c:choose>
                        <c:when test="${isLibrarian == false}"> 
                        </c:when>
                        <c:otherwise>
                            <a href="<spring:url value="/${classRequestPath}/dissertations/searchstudent"/>"  class="btn btn-primary">
                                <span></span>New</a>
                            </c:otherwise>
                        </c:choose>
                    <a href="<spring:url value="/${classRequestPath}"/>"  class="btn btn-primary">
                        <span></span>Back
                    </a>
                </p>            
            </div>
            <div class="alert alert-warning">${errorMessage}</div>
        </span>
    </body>
</html>
